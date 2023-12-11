package com.example.health_alarm_v2;

import java.util.Arrays;
import java.util.List;

public class Medicine {
    private static final List<String> VALID_TYPES = Arrays.asList("injections", "eye drops", "oral pills");
    private String Id;
    private String name;
    private String photo;
    private String type;
    private int quantity;
    private int dose;
    private String frequency;
    private int remaining_amount;

    //private Alert alert;
    public Medicine(){};


    public Medicine(String id, String name, String photo,String type, int quantity, int dose, String frequency, int remaining_amount, Alert alert) {
        Id = id;
        this.name = name;
        this.photo= photo;
        if (!VALID_TYPES.contains(type.toLowerCase())) {
            throw new IllegalArgumentException("Invalid medicine type");
        }
        this.type = type.toLowerCase();
        this.quantity = quantity;
        this.dose = dose;
        this.frequency = frequency;
        this.remaining_amount = remaining_amount;
        //this.alert = alert;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getRemaining_amount() {
        return remaining_amount;
    }

    public void setRemaining_amount(int remaining_amount) {
        this.remaining_amount = remaining_amount;
    }


}
