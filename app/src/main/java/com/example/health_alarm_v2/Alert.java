package com.example.health_alarm_v2;

import java.util.Date;

public class Alert {
    private int id;
    private String name;
    private Date time;
    private AlertFeedback feedback;

    public Alert(int id, String name, Date time, AlertFeedback feedback) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public AlertFeedback getFeedback() {
        return feedback;
    }

    public void setFeedback(AlertFeedback feedback) {
        this.feedback = feedback;
    }
}
