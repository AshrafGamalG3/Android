package com.example.whatsapp;

public class MassageData {
    String userId,massage,massageId,photo;
    long timALong;

    public MassageData(String userId, String massage, long timALong) {
        this.userId = userId;
        this.massage = massage;
        this.timALong = timALong;
    }

    public MassageData(String userId, String massage) {
        this.userId = userId;
        this.massage = massage;
    }
    public  MassageData(){

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getMassageId() {
        return massageId;
    }

    public void setMassageId(String massageId) {
        this.massageId = massageId;
    }

    public long getTimALong() {
        return timALong;
    }

    public void setTimALong(long timALong) {
        this.timALong = timALong;
    }
}
