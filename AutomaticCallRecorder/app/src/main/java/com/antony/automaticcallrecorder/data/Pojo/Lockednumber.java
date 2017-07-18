package com.antony.automaticcallrecorder.data.Pojo;

/**
 * Created by Antony on 7/6/2017.
 */

public class Lockednumber {
    int id=0;
    String name="";
    String number="";
    String password="";

    public Lockednumber() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
