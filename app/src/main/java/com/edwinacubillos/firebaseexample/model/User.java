package com.edwinacubillos.firebaseexample.model;

/**
 * Created by edwin on 12/10/17.
 */

public class User {
    private String name, email,uid,phone;

    public User() {
    }

    public User(String uid, String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
