package com.example.erica.recsfromtechs;
import static org.junit.Assert.*;


import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;


/**
 * Tests for our Edit Profile class
 * Created by Erica on 4/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class EditProfileTest {

    @Rule
    public ActivityTestRule<EditProfile> editProfileRule = new ActivityTestRule<EditProfile>(EditProfile.class);


    @Test
    public void changePWSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);
        editProfileRule.getActivity().changePassword("e","password");

        assertEquals("password", testDb.getPassword("e"));
        testDb.deleteUser("e");
    }

    @Test
    public void changePWNoSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changePassword("e","p");

        assertEquals("mercedes9", testDb.getPassword("e"));
        testDb.deleteUser("e");
    }

    @Test
    public void changeUsernameSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);
        User dummy = new User("Courtney", "cbran@gatech.edu", "Business", "cb","password",0,0,0);
        testDb.addUser(dummy);

        editProfileRule.getActivity().changeUsername("e", "cb");

        assertEquals("mercedes9", testDb.getPassword("e"));
        assertEquals("password", testDb.getPassword("cb"));
        testDb.deleteUser("b");
        testDb.deleteUser("cb");
    }

    @Test
    public void changeUsernameNoSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);
        User dummy = new User("Courtney", "cbran@gatech.edu", "Business", "cb","password",0,0,0);
        testDb.addUser(dummy);

        editProfileRule.getActivity().changeUsername("e", "x");

        assertEquals("mercedes9", testDb.getPassword("x"));
        testDb.deleteUser("x");
    }

    @Test
    public void changeMajorScienceSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeMajor("e", "Science");

        assertEquals("Science", testDb.getMajor("e"));
        testDb.deleteUser("e");
    }
    @Test
    public void changeMajorEngineeringSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeMajor("e", "Engineering");

        assertEquals("Engineering", testDb.getMajor("e"));
        testDb.deleteUser("e");
    }
    @Test
    public void changeMajorBusinessSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeMajor("e", "Business");

        assertEquals("Business", testDb.getMajor("e"));
        testDb.deleteUser("e");
    }
    @Test
    public void changeMajorOtherSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeMajor("e", "Other");

        assertEquals("Other", testDb.getMajor("e"));
        testDb.deleteUser("e");
    }

    @Test
    public void changeMajorNoSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeMajor("e", "Mechanical");

        assertEquals("Computer Science", testDb.getMajor("e"));
        testDb.deleteUser("e");
    }

    @Test
    public void changeEmailSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Rachel", "rgolding3@gatech.edu", "Computer Science", "r", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeEmail("r", "r@gatech.edu");

        assertEquals("r@gatech.edu", testDb.getEmail("r"));
        testDb.deleteUser("r");
    }

    public void changeEmailNoSuccess (){
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Rachel", "rgolding3@gatech.edu", "Computer Science", "r", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeEmail("r", "julia");

        assertEquals("rgolding3@gatech.edu", testDb.getEmail("r"));
        testDb.deleteUser("e");
    }
    @Test
    public void changeEmailNoSuccess2() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Julia", "julia3@gatech.edu", "Computer Science", "j", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeEmail("j", "");

        assertEquals("julia3@gatech.edu", testDb.getEmail("j"));
        testDb.deleteUser("j");
    }

}