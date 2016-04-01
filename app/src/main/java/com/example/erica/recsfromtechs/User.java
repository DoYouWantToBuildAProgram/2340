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
    private int isLocked;


    public User(String name, String email, String major, String username, String password, int isBanned, int isLocked) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.username = username;
        this.password = password;
        this.isBanned = isBanned;
        this.isLocked = isLocked;
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

    public String getUsername() { return username;}

    public String getPassword() { return password;}

    public int getIsBanned() { return isBanned;}

    public int getIsLocked() { return isLocked;}


    public void setName(String newName){
        name = newName;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public void setMajor(String newMajor) {
        major = newMajor;
    }

    public void setBan(int num) {
        if (num == 1 | num == 0) {
            isLocked = num;
        }
    }
    public void setLock(int num) {
        if (num == 1 | num == 0) {
            isLocked = num;
        }
    }

    public String toString() {
        return username +" "+password+" "+email+" "+name;
    }

}
