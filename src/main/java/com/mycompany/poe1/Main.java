/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Login userAccount = new Login();
            
            System.out.println("====== POE1 CHAT APP REGISTRATION ======");
            
            System.out.print("Enter First Name: ");
            userAccount.setFirstName(input.nextLine());
            
            System.out.print("Enter Last Name: ");
            userAccount.setLastName(input.nextLine());
            
            // Loop runs until username passes criteria rules
            while (true) {
                System.out.print("Create Username (must contain '_' & max 5 chars): ");
                userAccount.setUsername(input.nextLine());
                
                if (userAccount.checkUserName()) {
                    System.out.println("-> Username saved successfully.");
                    break;
                } else {
                    System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
                    System.out.println("Try again.\n");
                }
            }
            
            // Loop runs until password passes complex criteria rules
            while (true) {
                System.out.print("Create Password (min 8 chars, uppercase, number, symbol): ");
                userAccount.setPassword(input.nextLine());
                
                if (userAccount.checkPasswordComplexity()) {
                    System.out.println("-> Password saved successfully.");
                    break;
                } else {
                    System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
                    System.out.println("Try again.\n");
                }
            }
            
            // Loop runs until cell phone matches regular expression rules
            while (true) {
                System.out.print("Enter Cell Number (e.g., +27838968976): ");
                userAccount.setCellPhone(input.nextLine());
                
                if (userAccount.checkCellPhoneNumber()) {
                    System.out.println("-> Cell phone number saved successfully.");
                    break;
                } else {
                    System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
                    System.out.println("Try again.\n");
                }
            }
            
            // Print final confirm output string verification
            System.out.println("\n*** PROCESSING SYSTEM REGISTRATION ***");
            System.out.println(userAccount.registerUser());
            System.out.println("***************************************\n");
            
            // Phase 2 execution entry: Login matching console simulation
            System.out.println("====== SYSTEM LOGIN SELECTION ======");
            System.out.print("Enter registered username: ");
            String attemptUser = input.nextLine();
            
            System.out.print("Enter registered password: ");
            String attemptPass = input.nextLine();
            
            boolean checkAuth = userAccount.loginUser(attemptUser, attemptPass);
            System.out.println("\nResult: " + userAccount.returnLoginStatus(checkAuth));
        }
    }
}
