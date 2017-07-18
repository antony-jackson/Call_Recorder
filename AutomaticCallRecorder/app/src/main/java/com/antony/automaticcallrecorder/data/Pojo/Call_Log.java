package com.antony.automaticcallrecorder.data.Pojo;

/**
 * Created by Antony on 7/2/2017.
 */

public class Call_Log {
    String name;
    String phno;
    int calltype;

    public Call_Log(String name, String phno, int calltype) {
        this.name = name;
        this.phno = phno;
        this.calltype = calltype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public int getCalltype() {
        return calltype;
    }

    public void setCalltype(int calltype) {
        this.calltype = calltype;
    }
}
