package com.example.firsttest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {


        private static final String LOGIN_MESSAGE = "Login was successful";
    private static final String FAIL_MESSAGE = "Login failed";

        // This test validates the Username and Password of the Log In Page
        @Test
        public void validateUsernameAndPassword() {

            LogInPage myObjectUnderTest = mock(LogInPage.class);
            when(myObjectUnderTest.validate("user", "user")).thenReturn("Login was successful");

            // ...when the string is returned from the object under test...
            String result = myObjectUnderTest.validate("user","user");

            System.out.print("result is:" + result);

            // ...then the result should be the expected one.
            assertEquals(result, LOGIN_MESSAGE);
        }

    // This test invalidates the Username and Password of the Log In Page
    @Test
    public void invalidateUsernameAndPassword() {

        LogInPage myObjectUnderTest = Mockito.mock(LogInPage.class);
        Mockito.when(myObjectUnderTest.validate("user", "user")).thenReturn("Login was successful");

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("user","user");

        System.out.print("result is:" + result);

        // ...then the result should be the expected one.
        Assert.assertNotEquals(result, FAIL_MESSAGE);
    }

    // This test validates the Username and Password does not validate the wrong username and password
    @Test
    public void invalidateWrongUsernameAndPassword() {

        LogInPage myObjectUnderTest = mock(LogInPage.class);
        when(myObjectUnderTest.validate("user", "user")).thenReturn("Login failed");

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("user","user");

        System.out.print("result is:" + result);

        // ...then the result should be the expected one.
        Assert.assertNotEquals(result, LOGIN_MESSAGE);
    }
    
    @Test
    public void testUserInformation() {
        //Test checks if mockito returns the correct value when validateUserInformation is called
        LogInPage lip = mock(LogInPage.class);
        when(lip.validateUserInformation("Charles Taylor")).thenReturn("Hello");

        //result expected: "Hello"
        String result = lip.validateUserInformation("Charles Taylor");
        Assert.assertNotEquals("Hi", result);
    }

    // This test invalidates the Username and Password does not validate the wrong username and password
    @Test
    public void validateWrongUsernameAndPassword() {

        LogInPage myObjectUnderTest = Mockito.mock(LogInPage.class);
        Mockito.when(myObjectUnderTest.validate("user", "user")).thenReturn("Login failed");

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("user","user");

        System.out.print("result is:" + result);

        // ...then the result should be the expected one.
        Assert.assertEquals(result, FAIL_MESSAGE);
    }

    // This test validates the Dark Mode Setting
    @Test
    public void darkModeSetting() {
        SettingsPage myObjectUnderTest = mock(SettingsPage.class);
        Switch switchA = myObjectUnderTest.getSwitch();
        //Return the boolean on status of dark/light mode
        Mockito.when(myObjectUnderTest.darkModeEnabled(switchA)).thenReturn(true);
        myObjectUnderTest.onCreate(null);
        myObjectUnderTest.enableDarkMode(switchA);

        //Asserts the boolean on status of dark/light mode
        assertEquals(myObjectUnderTest.darkModeEnabled(switchA), true);

    }

    // This test validates the Light Mode Setting
    @Test
    public void lightModeSetting() {
        SettingsPage myObjectUnderTest = mock(SettingsPage.class);
        Switch switchA = myObjectUnderTest.getSwitch();
        //Return the boolean on status of dark/light mode
        Mockito.when(myObjectUnderTest.darkModeEnabled(switchA)).thenReturn(false);
        myObjectUnderTest.onCreate(null);

        //Asserts the boolean on status of dark/light mode
        assertEquals(myObjectUnderTest.darkModeEnabled(switchA), false);

    }

    // Test the Objects of recyclerviews
    @Test
    public void checkGettersAndSetters() {

        Member member = Mockito.mock(Member.class);
        member.setName("Ryan Derrick");
        member.setUsername("rderrick16");
        member.setEmail("derrick2@iastate.edu");
        member.setPhoto(R.drawable.profile_circle_vector);
        Mockito.when(member.getName()).thenReturn("Ryan Derrick");

        // ...when the string is returned from the object under test...
        String result = member.getName();

        System.out.print("result is:" + result);

        // ...then the result should be the expected one.
        Assert.assertEquals("Ryan Derrick", result);

        Club club = Mockito.mock(Club.class);
        club.setName("Random Club");
        club.setNumOfMembers(23);
        club.setId(673);
        Mockito.when(club.getName()).thenReturn("Random Club");

        // ...when the string is returned from the object under test...
        String result1 = club.getName();

        System.out.print("result is:" + result1);

        // ...then the result should be the expected one.
        Assert.assertEquals("Random Club", result1);
    }

    /*@Test
    public void testCreateAccountCheckAreWorking() {

        CreateAccountPage myObjectUnderTest = Mockito.mock(CreateAccountPage.class);
        Bundle bundle;

        Mockito.when(myObjectUnderTest.onCreate(bundle).EnterFirstName.getText().toString()).thenReturn("Login failed");

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("user","user");

        System.out.print("result is:" + result);

        // ...then the result should be the expected one.
        Assert.assertNotEquals(result, LOGIN_MESSAGE);
    }*/



    }