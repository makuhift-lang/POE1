/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe1;

import java.util.regex.Pattern;

public class Login {
    // Member variables to track registration information
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhone;

    // Default constructor to initialize blank fields
    public Login() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.cellPhone = "";
    }

    // Parameterized constructor for easy handling
    public Login(String username, String password, String firstName, String lastName, String cellPhone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhone = cellPhone;
    }

    // Task Rule: Must contain an underscore (_) and be 5 characters or fewer
    public boolean checkUserName() {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    // Task Rule: Minimum 8 characters, 1 capital letter, 1 number, 1 special character
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasNum = false;
        boolean hasSpecial = false;

        // Human-written loops instead of complex framework calls
        for (int i = 0; i < password.length(); i++) {
            char current = password.charAt(i);
            
            if (Character.isUpperCase(current)) {
                hasUpper = true;
            } else if (Character.isDigit(current)) {
                hasNum = true;
            } else if (!Character.isLetterOrDigit(current)) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasNum && hasSpecial;
    }

    // Task Rule: Regex string validation pattern checking for +27 and up to 10 digits
    public boolean checkCellPhoneNumber() {
        if (cellPhone == null) {
            return false;
        }
        String cellPattern = "^\\+27\\d{1,10}$";
        return Pattern.matches(cellPattern, cellPhone);
    }

    // Returns the exact validation error texts or success text specified in the brief
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        return "The user has been registered successfully.";
    }

    // Verifies input login criteria against values inside fields
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (this.username == null || this.password == null) {
            return false;
        }
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    // Generates output text string welcoming user or showing error status
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Classic getters and setters for main pipeline routing
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCellPhone() { return cellPhone; }
    public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }
}

