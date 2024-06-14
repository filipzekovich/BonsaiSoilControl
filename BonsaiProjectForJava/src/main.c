#include <util/delay.h>
#include <avr/io.h>
#include <usart.h>
#include <display.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <avr/interrupt.h>

#define __DELAY_BACKWARD_COMPATIBLE__
//DURATION IS IN MILLISECONDS
#define DURATION 150

//FREQUENCIES OF THE NOTES
#define C5 523.250

#define VCC 5 // VCC is 5V = source voltage
#define ALARM_PIN PD3 // Define pin for alarm (buzzer or LED)
#define MOISTURE_THRESHOLD 30 // Threshold for moisture level alarm
#define MAX_BONSAI 2
#define MAX_MEASUREMENTS 30

// Set and clear bit macros
#define sbi(register, bit) (register |= _BV(bit))
#define cbi(register, bit) (register &= ~_BV (bit))
#define write_mask(register, mask) (register = mask)

struct Bonsai {
    char name[20];
    char location[30];
    int moistureLevels[MAX_MEASUREMENTS];
    int currentIndex;
    int alarmLevel;
};

struct Bonsai bonsaiPlants[MAX_BONSAI];

int bonsaiCount = 0;
int measurementFrequency = 10000;
volatile int counter = 0;
volatile int delay = 30; //second * delay

void custom_delay_ms(uint32_t ms)
{
    while (ms--)
    {
        _delay_ms(1);
    }
}

void enableBuzzer()
{
   DDRD |= ( 1 << ALARM_PIN );
   PORTD |= (1 << ALARM_PIN);
}

void playTone(float frequency,uint32_t duration)
{
   uint32_t periodInMicro= (uint32_t) (1000000/frequency);
   uint32_t durationInMicro=duration*1000;
   for(uint32_t time=0;time<durationInMicro;time+=periodInMicro)
    {
    PORTD&= ~(1<<ALARM_PIN);//turn the buzzer on
    _delay_us(periodInMicro/2);//Wait for the half of the period
    PORTD|= (1<<ALARM_PIN);//Turn the buzzer off
    _delay_us(periodInMicro/2);//Wait again for half of the period
    }
}


void initADC() {
    sbi(ADMUX, REFS0); // choose internal 5V (VCC) as reference voltage
    write_mask(ADCSRA, _BV(ADPS0) | _BV(ADPS1) | _BV(ADPS2)); // set prescaling factor to 16 Mhz / 128 = 125 Khz
    sbi(ADCSRA, ADEN); // enable AD conversion
}

uint16_t readADC(uint8_t channel) {
    ADMUX = (ADMUX & 0xf0) | channel; // put the required channel in the lowest 4 bits of the ADMUX register
    sbi(ADCSRA, ADSC); // trigger conversion
    loop_until_bit_is_clear(ADCSRA, ADSC); // wait until ADSC is zero (cleared), then the conversion is done
    return ADC;
}

double to_voltage(int rawADC) {
    return rawADC / 1023.0 * VCC; // 0 = 0V and 1023 = 5V
}

double moistureLevel(int rawADC) {
    // Convert ADC value to a percentage (assuming 0-1023 maps to 0-100% moisture)
    return (rawADC / 1023.0) * 100;
}

void checkMoistureLevels() {
    stopTimer();
    for (int i = 0; i < bonsaiCount; i++) {
        uint16_t rawADC = readADC(4 + i); 
        double moisture = moistureLevel(rawADC);

        if (moisture < bonsaiPlants[i].alarmLevel) {
            // Trigger alarm if moisture level is below the threshold
            playTone(C5, DURATION);
            _delay_ms(DURATION); 
        }

        // Store the moisture level
        bonsaiPlants[i].moistureLevels[bonsaiPlants[i].currentIndex] = (int)moisture;
        bonsaiPlants[i].currentIndex = (bonsaiPlants[i].currentIndex + 1) % MAX_MEASUREMENTS;
 

        // Display moisture level on 7-segment display
        writeNumberAndWait((int)moisture,5000);
        restartTimer();
    }
}

void addBonsai(char* name, char* location) {
    if (bonsaiCount < MAX_BONSAI) {
        strncpy(bonsaiPlants[bonsaiCount].name, name, sizeof(bonsaiPlants[bonsaiCount].name) - 1);
        strncpy(bonsaiPlants[bonsaiCount].location, location, sizeof(bonsaiPlants[bonsaiCount].location) - 1);
        bonsaiPlants[bonsaiCount].currentIndex = 0;
        bonsaiPlants[bonsaiCount].alarmLevel = MOISTURE_THRESHOLD;
        bonsaiCount++;
    } else {
        printf("Cannot add more Bonsai plants. Max limit reached.\n");
    }
}

void removeBonsai(int index) {
    if (index < bonsaiCount && index >= 0) {
        for (int i = index; i < bonsaiCount - 1; i++) {
            bonsaiPlants[i] = bonsaiPlants[i + 1];
        }
        bonsaiCount--;
    } else {
        printf("Invalid Bonsai index.\n");
    }
}

int numberOfBonsai() {
    return bonsaiCount;
}

void setAlarmLevel(int index, int level) {
    if (index < bonsaiCount && index >= 0) {
        bonsaiPlants[index].alarmLevel = level;
    } else {
        printf("Invalid Bonsai index.\n");
    }
}

void listBonsai() {
    for (int i = 0; i < bonsaiCount; i++) {
        printf("Bonsai #%d: %s, Location: %s, Alarm Level: %d%%\n", i, bonsaiPlants[i].name, bonsaiPlants[i].location, bonsaiPlants[i].alarmLevel);
    }
}

void configureMeasurementFrequency(int frequency) {
    delay = frequency;
}

void displayMenu() {
    printf("Menu:\n");
    printf("1. Add Bonsai\n");
    printf("2. Remove Bonsai\n");
    printf("3. List Bonsai\n");
    printf("4. Set Alarm Level\n");
    printf("5. Set Measurement Frequency\n");
    printf("6. Exit\n");
}

void readCharacter(char *input) {
    *input = receiveByte();
    transmitByte(*input); // Echo the character
}

void readInteger(int *input) {
    char buffer[10];
    readString(buffer, sizeof(buffer));
    *input = atoi(buffer);
}

void stopTimer() {
    // Stop the timer
    cbi(TCCR1B, CS12);
    cbi(TCCR1B, CS10);
}

void restartTimer() {
    // Restart the timer
    sbi(TCCR1B, CS12); // Set the prescaler to 256
}


void processInput(char input) {
    char name[20], location[30];
    int index, level, frequency;

    stopTimer();

    switch (input) {
        case '1':
            printf("Enter Bonsai Name: ");
            readString(name, sizeof(name));
            printf("\n");
            printf("Enter Location: ");
            readString(location, sizeof(location));
            printf("\n");
            addBonsai(name, location);
            break;
        case '2':
            printf("Enter Bonsai Index to Remove: ");
            readInteger(&index);
            printf("\n");
            removeBonsai(index);
            break;
        case '3':
            listBonsai();
            break;
        case '4':
            printf("Enter Bonsai Index to Set Alarm Level: ");
            readInteger(&index);
            printf("\n");
            printf("Enter Alarm Level: ");
            readInteger(&level);
            printf("\n");
            setAlarmLevel(index, level);
            break;
        case '5':
            printf("Enter Measurement Frequency (in ms): ");
            readInteger(&frequency);
            printf("\n");
            configureMeasurementFrequency(frequency);
            break;
        case '6':
            checkMoistureLevels();
            break;
        case '7':
            printf(&bonsaiCount);
            break;
        default:
            printf("Invalid option. Try again.\n");
    }

    restartTimer();
}

void menuLoop() {
    char charInput;
    while (1) {
        displayMenu();
        readCharacter(&charInput);
        printf("\n");
        if (charInput == '6') {
            return;
        }
        processInput(charInput);
        _delay_ms(100); // Small delay to debounce
    }
}

void initTimer() {
    TCCR1A = 0;
    TCCR1B = _BV(WGM12) | _BV(CS12); 
    OCR1A = 62500;
    TIMSK1 = _BV(OCIE1A); 
    sei(); 
}

ISR(TIMER1_COMPA_vect) {
    counter++;
    if (counter >= delay) {
        counter = 0;
        checkMoistureLevels();
    }
}

int main() 
{ 
    initUSART();
    initDisplay();
    enableBuzzer();
    initADC();
    initTimer();

    while (1) {
        char charInput;
        char name[20], location[30];
        int index, level, frequency;

        readCharacter(&charInput);

        switch (charInput) {
        case '1':
            addBonsai("", "");
            break;
        case '2':
            printf("Enter Bonsai Index to Remove: ");
            readInteger(&index);
            removeBonsai(index);
            break;
        case '3':
            listBonsai();
            break;
        case '4':
            printf("Enter Bonsai Index to Set Alarm Level: ");
            readInteger(&index);
            printf("Enter Alarm Level: ");
            readInteger(&level);
            setAlarmLevel(index, level);
            break;
        case '5':
            printf("Enter Measurement Frequency (in ms): ");
            readInteger(&frequency);
            configureMeasurementFrequency(frequency);
            break;
        case '6':
            checkMoistureLevels();
            break;
        case '7':
            printf("\n");
            printf("%d",bonsaiCount);
            break;
        default:
            break;
        }
    }
    return 0;
}