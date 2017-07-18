package com.antony.automaticcallrecorder.data.Pojo;

/**
 * Created by Antony on 7/2/2017.
 */

public class Blockednumber {

    int id;
    String phno;

    String name;

    public Blockednumber(int id, String phno, String name) {
        this.id = id;
        this.phno = phno;
        this.name = name;
    }

    public Blockednumber() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
