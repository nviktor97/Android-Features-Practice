package com.example.navdraw.streamfrag;


public class User {

    private int id;
    private String gender;

    private Name name;

    private Dob dob;

    private String nat;

    public User(String gender, Name name, Dob dob, String nat) {
        this.gender = gender;
        this.name = name;
        this.dob = dob;
        this.nat = nat;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Dob getDob() {
        return dob;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    @Override
    public String toString() {
        return name.getFirst();
    }
}
