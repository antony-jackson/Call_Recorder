package com.antony.automaticcallrecorder.data.Pojo;

/**
 * Created by Antony on 7/1/2017.
 */

public class Record {

    int id;
    String filepath;
    String name;
    String phonenumber;
    int callstatus;
    int favourite;

    String time;
    String Date;

    public Record() {
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getCallstatus() {
        return callstatus;
    }

    public void setCallstatus(int callstatus) {
        this.callstatus = callstatus;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
