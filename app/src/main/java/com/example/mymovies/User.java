package com.example.mymovies;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String email;
    private  String password;
    private  String firstname;
    private  String lastname;
    private  String gender;
    private String origin;

    public User() {
    }

    public User(String email, String password, String firstname, String lastname, String gender, String origin) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender=gender;
        this.origin=origin;
    }

    public User(String email, String password, String firstname, String lastname, String gender, String origin, String uid) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender=gender;
        this.origin=origin;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getOrigin() {
        return origin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
