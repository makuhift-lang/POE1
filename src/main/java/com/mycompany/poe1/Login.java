package com.mycompany.poe1;

import java.util.regex.Pattern;

/**
 * Handles user registration validation and login authentication.
 * @author Student
 */
public class Login {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhone;

    // Initializes empty user credentials
    public Login() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.cellPhone = "";
    }

    // Validates username contains underscore and is max 5 characters
    public boolean checkUserName() {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Validates password length (8+), uppercase, digit, and special character
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) return false;
        boolean upper = false, digit = false, special = false;
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) upper = true;
            else if (Character.isDigit(c)) digit = true;
            else if (p.matcher(String.valueOf(c)).matches()) special = true;
        }
        return upper && digit && special;
    }

    // Validates South African cell number (+27 followed by 9 digits)
    public boolean checkCellPhoneNumber() {
        return cellPhone != null && cellPhone.matches("^\\+27\\d{9}$");
    }

    // Returns registration status message based on field validations
    public String registerUser() {
        if (!checkUserName()) return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        if (!checkPasswordComplexity()) return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        if (!checkCellPhoneNumber()) return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        return "The user has been registered successfully.";
    }

    // Authenticates entered credentials against stored user details
    public boolean loginUser(String u, String p) {
        return username.equals(u) && password.equals(p);
    }

    // Returns welcome or failure message after login attempt
    public String returnLoginStatus(boolean ok) {
        return ok ? "Welcome " + firstName + ", " + lastName + " it is great to see you again." : "Username or password incorrect, please try again.";
    }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
