package be.kdg.bonsaiProjectApp.model;


import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;

import java.io.PrintWriter;
import java.util.ArrayList;

public class MainModel {
    public static final int ARDUINO_PORT = 1;
    private SerialPort arduinoPort;
    private ArrayList<Bonsai> bonsais = new ArrayList<>();

    public MainModel() {
        for (SerialPort port : SerialPort.getCommPorts()){
            System.out.println(port.toString());//check welke bij jou de Arduino port is...
        }
        arduinoPort = SerialPort.getCommPorts()[ARDUINO_PORT];
        boolean result = arduinoPort.openPort();
        System.out.println(result?"port opened!":"port NOT opened!");
        arduinoPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 1000);
    }

    public void sendString(String data) {
        System.out.printf("Sending %s to Arduino...\n", data);
        PrintWriter writer = new PrintWriter(arduinoPort.getOutputStream());
        writer.print(data);
        writer.flush();
    }

    public byte[] receiveBytes(){
        byte[] newData = new byte[arduinoPort.bytesAvailable()];
        arduinoPort.readBytes(newData, newData.length);
        System.out.print("Receiving data from Arduino:");
        for (byte newDatum : newData) {
            System.out.printf("%c",(char)newDatum);
        }
        System.out.println();
        return newData;
    }

    public String receiveString() {
        StringBuilder receivedData = new StringBuilder();
        while (true) {
            if (arduinoPort.bytesAvailable() > 0) {
                byte[] newData = new byte[arduinoPort.bytesAvailable()];
                arduinoPort.readBytes(newData, newData.length);
                for (byte newDatum : newData) {
                    receivedData.append((char) newDatum);
                }
            } else {
                // No more data available, return the received string
                if (receivedData.length() > 0) {
                    System.out.printf("Received from Arduino: \"%s\"\n", receivedData.toString());
                    return receivedData.toString();
                }
            }
        }
    }

    public void addDatalistener(SerialPortDataListener dataListener){
        arduinoPort.addDataListener(dataListener);
    }

    public ArrayList<Bonsai> getBonsais() {
        return bonsais;
    }

    public SerialPort getArduinoPort() {
        return arduinoPort;
    }

    public void addBonsai(String name, String location){
        Bonsai bonsai = new Bonsai(name,location);
        bonsais.add(bonsai);
    }

    public int countBonsais(){
        return bonsais.size();
    }

    public void removeBonsai(int index){
        bonsais.remove(index);
    }

    public void changeAlarm(int index,int alarm){
        bonsais.get(index).setAlarmLevel(alarm);
    }
}
