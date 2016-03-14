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
    private int isBanned;

    public User(String name, String email, String major, String username, String password, int isBanned) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.username = username;
        this.password = password;
        this.isBanned = isBanned;
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

    public void ban() {
        isBanned = 1;
    }

    public String getUsername() { return username;}
    public String getPassword() { return password;}
    public int getIsBanned() { return isBanned;}

}
