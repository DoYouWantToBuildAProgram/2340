package com.example.erica.recsfromtechs;
import static org.junit.Assert.*;

import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
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
        testDb.deleteUser("e");
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
    public void changeMajorSuccess() {
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeMajor("e", "Science");

        assertEquals("Science", testDb.getMajor("e"));
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

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeEmail("e", "e@gatech.edu");

        assertEquals("e@gatech.edu", testDb.getEmail("e"));
        testDb.deleteUser("e");
    }

    public void changeEmailNoSuccess (){
        MyDBHandler testDb = editProfileRule.getActivity().getDb();

        User testUser = new User("Erica", "echia3@gatech.edu", "Computer Science", "e", "mercedes9", 0, 0, 0);
        testDb.addUser(testUser);

        editProfileRule.getActivity().changeEmail("e", "erica");

        assertEquals("echia3@gatech.edu", testDb.getEmail("e"));
        testDb.deleteUser("e");
    }

}