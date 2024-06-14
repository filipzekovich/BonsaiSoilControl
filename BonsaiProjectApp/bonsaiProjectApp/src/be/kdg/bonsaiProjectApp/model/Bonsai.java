package be.kdg.bonsaiProjectApp.model;

import java.util.ArrayList;

public class Bonsai {
    private String name;
    private String location;
    private Integer alarmLevel;
    private ArrayList<Integer> moistureLevels = new ArrayList<>();

    public Bonsai(String name, String location) {
        this.name = name;
        this.location = location;
        this.alarmLevel = 30;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public ArrayList<Integer> getMoistureLevels() {
        return moistureLevels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public void setMoistureLevels(ArrayList<Integer> moistureLevels) {
        this.moistureLevels = moistureLevels;
    }

    public void addMoistureLevel(int level){
        moistureLevels.add(level);
    }
}
