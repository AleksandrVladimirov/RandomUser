package com.example.asvladimirov.randomuser.Model;

import com.google.gson.annotations.SerializedName;

public class DOB {

    @SerializedName("date")
    private String date;
    @SerializedName("age")
    private String age;

    public DOB() {
    }

    public DOB(String date, String age) {
        this.date = date;
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

