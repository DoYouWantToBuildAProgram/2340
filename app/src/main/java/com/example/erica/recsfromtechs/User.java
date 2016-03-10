package com.example.erica.recsfromtechs;

/**
 * Created by Erica on 2/23/2016.
 */
public class User {

    private String name;
    private String email;
    private String major;

    public User(String name, String email, String major) {
        this.name = name;
        this.email = email;
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMajor() {
        return major;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public void setMajor(String newMajor) {
        major = newMajor;
    }

    public String toString(){ return name +", "+ email+", "+major;}
}
