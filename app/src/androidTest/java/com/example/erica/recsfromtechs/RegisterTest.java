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
 * Created by Erica on 4/7/2016.
 */

@RunWith(AndroidJUnit4.class)
public class RegisterTest {

    @Rule
    public ActivityTestRule<Register> registerRule = new ActivityTestRule<>(Register.class);

    @Test
    public void addAdminTest(){
        onView(withId(R.id.username)).perform(typeText("testAdmin"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("email@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Computer Science"))).perform(click());
        onView(withId(R.id.adminPassword)).perform(typeText("CS2340"), closeSoftKeyboard());
        onView(withId(R.id.addUserButton)).perform(click());

        MyDBHandler testDb = registerRule.getActivity().getDb();

        User testUser = new User(testDb.getName("testAdmin"),
                testDb.getEmail("testAdmin"),testDb.getMajor("testAdmin"),
                "testAdmin", testDb.getPassword("testAdmin"),
                testDb.getIsBlocked("testAdmin"),testDb.getIsLocked("testAdmin"),
                testDb.getIsAdmin("testAdmin"));

        assertEquals("testAdmin", testUser.getUsername());
        assertEquals("password", testUser.getPassword());
        assertEquals("email@gmail.com", testUser.getEmail());
        assertEquals("Test", testUser.getName());
        assertEquals("Computer Science", testUser.getMajor());
        assertEquals(0, testUser.getIsLocked());
        assertEquals(0, testUser.getIsBanned());
        assertEquals(1, testUser.getIsAdmin());
        testDb.deleteUser("testUser");

    }

    @Test
    public void addUserTest() {
        onView(withId(R.id.username)).perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("email@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Other"))).perform(click());
        onView(withId(R.id.adminPassword)).perform(typeText("CS2330"), closeSoftKeyboard());
        onView(withId(R.id.addUserButton)).perform(click());

        MyDBHandler testDb = registerRule.getActivity().getDb();

        User testUser = new User(testDb.getName("testUser"),
                testDb.getEmail("testUser"),testDb.getMajor("testUser"),
                "testUser", testDb.getPassword("testUser"),
                testDb.getIsBlocked("testUser"),testDb.getIsLocked("testUser"),
                testDb.getIsAdmin("testUser"));

        assertEquals("testUser", testUser.getUsername());
        assertEquals("password", testUser.getPassword());
        assertEquals("email@gmail.com", testUser.getEmail());
        assertEquals("Test", testUser.getName());
        assertEquals("Other", testUser.getMajor());
        assertEquals(0, testUser.getIsLocked());
        assertEquals(0, testUser.getIsBanned());
        assertEquals(0, testUser.getIsAdmin());
        testDb.deleteUser("testUser");
    }
    @Test
    public void addTakenUsername() {
        MyDBHandler testDb = registerRule.getActivity().getDb();
        User dummy = new User("Erica", "echia3@gatech.edu","Computer Science", "e", "e", 0, 0, 0);
        testDb.addUser(dummy);

        onView(withId(R.id.username)).perform(typeText("e"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("email@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Other"))).perform(click());
        onView(withId(R.id.adminPassword)).perform(typeText("CS2330"), closeSoftKeyboard());
        onView(withId(R.id.addUserButton)).perform(click());

        User testUser = new User(testDb.getName("e"),
                testDb.getEmail("e"),testDb.getMajor("e"),
                "e", testDb.getPassword("e"),
                testDb.getIsBlocked("e"),testDb.getIsLocked("e"),
                testDb.getIsAdmin("e"));

        assertEquals("e", testUser.getUsername());
        assertEquals("e", testUser.getPassword());
        assertEquals("echia3@gatech.edu", testUser.getEmail());
        assertEquals("Erica", testUser.getName());
        assertEquals("Computer Science", testUser.getMajor());
        assertEquals(0, testUser.getIsLocked());
        assertEquals(0, testUser.getIsBanned());
        assertEquals(0, testUser.getIsAdmin());
        testDb.deleteUser("testUser");
        testDb.deleteUser("e");
    }
}