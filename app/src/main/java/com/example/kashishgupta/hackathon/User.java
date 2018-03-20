package com.example.kashishgupta.hackathon;

/**
 * Created by muskan on 19/3/18.
 */

public class User {
    String type;
    String name;
    String email;
    String password;
    String id;

    public User(String type, String name, String email, String password, String id, String uid) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.uid = uid;
    }

    String uid;



    public User() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String phone) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
