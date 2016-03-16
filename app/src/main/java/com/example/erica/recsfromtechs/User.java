package com.example.erica.recsfromtechs;


/**
 * Created by Erica on 2/23/2016.
 */
public class User {

    private String name;
    private String email;
    private String major;
    private String username;
    private String password;

    public User(String name, String email, String major, String username, String password) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public String toString(){ return name +", "+ email+", "+major;}
}
