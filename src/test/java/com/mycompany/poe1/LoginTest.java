package com.mycompany.poe1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login("kyle!!!!!!", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertFalse(login.checkUserName());
        
        String expectedMessage = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(expectedMessage, login.registerUser());
    }

    @Test
    public void testPasswordMeetsComplexity() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login login = new Login("kyl_1", "password", "Kyle", "Smith", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
        
        String expectedMessage = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expectedMessage, login.registerUser());
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "0838968976");
        assertFalse(login.checkCellPhoneNumber());
        
        String expectedMessage = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(expectedMessage, login.registerUser());
    }

    @Test
    public void testLoginSuccessful() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        boolean loginResult = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        
        assertTrue(loginResult);
        
        String expectedWelcome = "Welcome Kyle, Smith it is great to see you again.";
        assertEquals(expectedWelcome, login.returnLoginStatus(loginResult));
    }

    @Test
    public void testLoginFailed() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith", "+27838968976");
        boolean loginResult = login.loginUser("wrong_user", "wrong_pass");
        
        assertFalse(loginResult);
        
        String expectedFailure = "Username or password incorrect, please try again.";
        assertEquals(expectedFailure, login.returnLoginStatus(loginResult));
    }
}
