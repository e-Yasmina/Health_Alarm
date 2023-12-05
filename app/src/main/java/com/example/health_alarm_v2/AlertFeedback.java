package com.example.health_alarm_v2;

import java.util.Date;

public class AlertFeedback {
    private int id;
    private Medicine medicine;
    private Date time;
    private State state;
    public enum State {
        TAKEN,
        MISSED
    }

    public AlertFeedback(int id, Medicine medicine, Date time, State state) {
        this.id = id;
        this.medicine = medicine;
        this.time = time;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
