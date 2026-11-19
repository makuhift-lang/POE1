package com.mycompany.poe1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testCheckUserNameValid() {
        Login login = new Login();
        login.setUsername("kyl_1");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUserNameInvalid() {
        Login login = new Login();
        login.setUsername("kyle!!!!!!!");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexityValid() {
        Login login = new Login();
        login.setPassword("Ch&&sec@ke99!");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexityInvalid() {
        Login login = new Login();
        login.setPassword("password");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumberValid() {
        Login login = new Login();
        login.setCellPhone("+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumberInvalid() {
        Login login = new Login();
        login.setCellPhone("08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testLoginUserSuccess() {
        Login login = new Login();
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUserFailure() {
        Login login = new Login();
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        assertFalse(login.loginUser("wrong", "pass"));
    }
}
