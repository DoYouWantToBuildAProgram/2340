package com.example.erica.recsfromtechs;

/**
 * Creates a user with the info below
 * Created by Erica on 2/23/2016.
 */
class User {

    private String name;
    private String email;
    private String major;
    private final String username;
    private final String password;
    private final int isBanned;
    private int isLocked;
    private final int isAdmin;

    /**
     * Creates an object to represent a user profile
     * @param name the name of the user
     * @param email the email of the user
     * @param major the major of the user
     * @param username the username of the user
     * @param password the user's password
     * @param isBanned whether or not they are banned (1 or 0)
     * @param isLocked whether or not they are locked (1 or 0)
     * @param isAdmin whether or not they are admin (1 or 0)
     */
    public User(String name, String email, String major, String username, String password, int isBanned, int isLocked,int isAdmin) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.username = username;
        this.password = password;
        this.isBanned = isBanned;
        this.isLocked = isLocked;
        this.isAdmin = isAdmin;
    }

    /**
     * Gives the name of the user
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     *  Gives the email of the user
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gives the major of the user
     * @return the major of the user
     */
    public String getMajor() {
        return major;
    }

    /**
     * Gives the user's username
     * @return the user's username
     */
    public String getUsername() { return username;}

    /**
     * Gives the password of the user
     * @return the user's password
     */
    public String getPassword() { return password;}

    /**
     * Whether or not the user is banned
     * @return 1 for yes, 0 for no
     */
    public int getIsBanned() { return isBanned;}

    /**
     * Whether or not the account has been locked
     * @return 1 for yes, 0 for no
     */
    public int getIsLocked() { return isLocked;}

    /**
     * Whether or not the user is admin, meaning they have control over other user accounts
     * @return 1 for yes, 0 for no
     */
    public int getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets the name of the user to the new name given
     * @param newName the new name of the user
     */
    public void setName(String newName){
        name = newName;
    }

    /**
     * Sets the email of the user to the new email given
     * @param newEmail the new email of the user
     */
    public void setEmail(String newEmail) {
        email = newEmail;
    }

    /**
     * Sets the major of the user to the new major
     * @param newMajor The new major of the user
     */
    public void setMajor(String newMajor) {
        major = newMajor;
    }

    /**
     * Sets the status of whether or not the user is banned
     * @param num 1 for banned 0 for not banned
     */
    public void setBan(int num) {
        if (num == 1 | num == 0) {
            isLocked = num;
        }
    }

    /**
     * Sets the locked status of the user
     * @param num 1 for locked 0 for not locked
     */
    public void setLock(int num) {
        if (num == 1 | num == 0) {
            isLocked = num;
        }
    }


    @Override
    public String toString() {
        return username +" "+password+" "+email+" "+name;
    }

}
