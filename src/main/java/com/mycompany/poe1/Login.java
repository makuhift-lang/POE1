/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe1;

import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class Login {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String cellPhone;

    // MUST HAVE: Blank default constructor for "new Login()"
    public Login() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.cellPhone = "";
    }

    public Login(String username, String password, String firstName, String lastName, String cellPhone) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhone = cellPhone;
    }

    public boolean checkUserName() {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) return false;
        
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        Pattern specialChars = Pattern.compile("[^a-zA-Z0-9]");
        
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if (specialChars.matcher(String.valueOf(ch)).matches()) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    public boolean checkCellPhoneNumber() {
        if (cellPhone == null) return false;
        return cellPhone.matches("^\\+\\d{11}$");
    }

    public String registerUser() {
        if (!checkUserName()) return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        if (!checkPasswordComplexity()) return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        if (!checkCellPhoneNumber()) return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        return "The user has been registered successfully.";
    }

    public boolean loginUser(String attemptUser, String attemptPass) {
        if (this.username == null || this.password == null) return false;
        return this.username.equals(attemptUser) && this.password.equals(attemptPass);
    }

    public String returnLoginStatus(boolean loginResult) {
        if (loginResult) return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        return "Username or password incorrect, please try again.";
    }

    // Setters and Getters used by Main.java
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
