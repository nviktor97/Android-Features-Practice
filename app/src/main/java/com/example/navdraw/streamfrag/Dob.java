package com.example.navdraw.streamfrag;

public class Dob {
    private String date;
    private int age;

    public Dob(String date, int age) {
        this.date = date;
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
