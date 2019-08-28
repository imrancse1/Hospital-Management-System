package com.example.bubt.MedScape;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient implements Serializable {
    private String name;
    private long unixDateTime;
    private int id, category;
    private double amount;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date date = null;
    private byte[] photo;
    public Patient(int id, String name, int category, double amount, long unixDateTime, byte[] photo) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unixDateTime = unixDateTime;
        this.amount = amount;
        this.photo = photo;
    }

    public Patient(String name, long unixDateTime, int category, double amount, byte[] photo) {
        this.name = name;
        this.unixDateTime = unixDateTime;
        this.category = category;
        this.amount = amount;
        this.photo = photo;
    }

    public Patient(String name, int category, long unixDateTime, double amount) {
        this.name = name;
        this.category = category;
        this.unixDateTime = unixDateTime;
        this.amount = amount;
    }

    public String  getDateInString(){
        String dateStr = dateFormat.format(unixDateTime);
        return dateStr;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getUnixDateTime() {
        return unixDateTime;
    }

    public void setUnixDateTime(long unixDateTime) {
        this.unixDateTime = unixDateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
