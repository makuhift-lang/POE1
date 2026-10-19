/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe1;

import java.util.Scanner;

/**
 *
 * @author Student
 */
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
            boolean isAuthenticated = false;
            while (!isAuthenticated) {
                System.out.println("====== SYSTEM LOGIN SELECTION ======");
                System.out.print("Enter registered username: ");
                String attemptUser = input.nextLine();
                
                System.out.print("Enter registered password: ");
                String attemptPass = input.nextLine();
                
                isAuthenticated = userAccount.loginUser(attemptUser, attemptPass);
                System.out.println("\nResult: " + userAccount.returnLoginStatus(isAuthenticated));
                
                if (!isAuthenticated) {
                    System.out.println("Authentication failed. You must log in successfully to access the application.\n");
                }
            }
            
            // ==================== PART 2: SENDING MESSAGES SYSTEM ====================
            System.out.println("\nWelcome to QuickChat.");
            
            boolean appRunning = true;
            while (appRunning) {
                System.out.println("\n----- QUICKCHAT OPTIONS MENU -----");
                System.out.println("Option 1) Send Messages");
                System.out.println("Option 2) Show recently sent messages");
                System.out.println("Option 3) Quit");
                System.out.print("Select an option: ");
                
                String selection = input.nextLine();
                
                switch (selection) {
                    case "1":
                        System.out.print("How many messages do you wish to enter? ");
                        int messageLimit = Integer.parseInt(input.nextLine());
                        
                        for (int i = 0; i < messageLimit; i++) {
                            System.out.println("\n--- Entering Message " + (i + 1) + " of " + messageLimit + " ---");
                            Message currentMessage = new Message(i);
                            
                            // Capture recipient cell data with integrated validation loop rules
                            while (true) {
                                System.out.print("Enter Recipient Cell Number: ");
                                currentMessage.setRecipientCell(input.nextLine());
                                String verificationStatus = currentMessage.checkRecipientCell();
                                System.out.println(verificationStatus);
                                
                                if (verificationStatus.equals("Cell phone number successfully captured.")) {
                                    break;
                                }
                            }
                            
                            // Capture text details ensuring character threshold limit restrictions pass
                            while (true) {
                                System.out.print("Enter Message Content (max 250 chars): ");
                                currentMessage.setMessageText(input.nextLine());
                                String lengthStatus = currentMessage.checkMessageLength();
                                System.out.println(lengthStatus);
                                
                                if (lengthStatus.equals("Message ready to send.")) {
                                    break;
                                }
                            }
                            
                            // Action processing submenu interaction
                            System.out.println("\nAvailable Messaging Choices:");
                            System.out.println("1 - Send Message");
                            System.out.println("2 - Disregard Message");
                            System.out.println("3 - Store Message to send later");
                            System.out.print("Choose action: ");
                            int actionChoice = Integer.parseInt(input.nextLine());
                            
                            String outcomeMessage = currentMessage.SendMessage(actionChoice);
                            System.out.println("Process Output: " + outcomeMessage);
                            
                            // Full details displayed sequentially if the message wasn't explicitly scrubbed
                            if (actionChoice == 1 || actionChoice == 3) {
                                System.out.println("\n--- TRANSACTION OVERVIEW ---");
                                System.out.println(currentMessage.printMessages());
                                System.out.println("----------------------------");
                            }
                        }
                        
                        // The total number of messages should be accumulated and displayed once all messages have been sent
                        Message totalCounterQuery = new Message(0);
                        System.out.println("\n[SUMMARY]: Total accumulated messages: " + totalCounterQuery.returnTotalMessages());
                        break;
                        
                    case "2":
                        System.out.println("Coming Soon.");
                        break;
                        
                    case "3":
                        System.out.println("Exiting Application. Goodbye!");
                        appRunning = false;
                        break;
                        
                    default:
                        System.out.println("Invalid choice. Please select option 1, 2, or 3.");
                        break;
                }
            }
            
        } catch (NumberFormatException exceptionContext) {
            System.out.println("System execution error evaluating structural numerical console parse conversions: " + exceptionContext.getMessage());
        }
    }
}
