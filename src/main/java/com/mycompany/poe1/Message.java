/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe1;

import java.util.Random;

/**
 *
 * @author Student
 */
public class Message {
    
    // Member fields tracking message state information
    private final String messageID;
    private final int messageCount;
    private String recipientCell;
    private String messageText;
    
    // Static tracker variable accumulated across application lifetime
    private static int totalMessagesAccumulated = 0;

    // Main parameterized constructor called inside project iteration loops
    public Message(int currentMessageNumber) {
        this.messageCount = currentMessageNumber;
        this.messageID = generateRandomMessageID();
        this.recipientCell = "";
        this.messageText = "";
    }

    // Helper generation function returning a unique 10-digit identity tracker
    private String generateRandomMessageID() {
        Random rand = new Random();
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            idBuilder.append(rand.nextInt(10));
        }
        return idBuilder.toString();
    }

    // Method Functionality: Ensures the message ID is not more than ten characters
    public boolean checkMessageID() {
        return getMessageID() != null && getMessageID().length() <= 10;
    }

    // Method Functionality: Adjusted to allow up to 12 characters to support international formats (+27...)
    public String checkRecipientCell() {
        if (recipientCell == null || recipientCell.length() > 12 || !recipientCell.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        return "Cell phone number successfully captured.";
    }

    // Utility Validation: Checks if message text conforms to length standards
    public String checkMessageLength() {
        if (messageText == null) {
            return "Message ready to send.";
        }
        if (messageText.length() > 250) {
            int overLimit = messageText.length() - 250;
            return "Message exceeds 250 characters by " + overLimit + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    // Method Functionality: Creates and returns the Message Hash in uppercase
    public String createMessageHash() {
        // FIXED: Now calls getMessageID() so test overrides work cleanly
        if (getMessageID() == null || getMessageID().length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            return "00:0:UNKNOWN";
        }
        
        String firstTwoDigits = getMessageID().substring(0, 2);
        
        // Split text cleanly by space structures to pull outer word boundaries
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        
        String combinedWords = (firstWord + lastWord).toUpperCase();
        
        return firstTwoDigits + ":" + messageCount + ":" + combinedWords;
    }

    // Method Functionality: Allows the user to choose if they want to send, store, or disregard the message
    public String SendMessage(int userSelection) {
        switch (userSelection) {
            case 1:
                totalMessagesAccumulated++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                totalMessagesAccumulated++;
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid selection.";
        }
    }

    // Method Functionality: Returns all the messages sent while the program is running sequentially
    public String printMessages() {
        return "Message ID: " + getMessageID() + "\n" +
               "Message Hash: " + createMessageHash() + "\n" +
               "Recipient: " + recipientCell + "\n" +
               "Message: " + messageText;
    }

    // Method Functionality: Returns the total number of messages sent
    public int returnTotalMessages() {
        return totalMessagesAccumulated;
    }

    // Custom defined storeMessage method: Researches how to create a method to store messages in JSON format
    public void storeMessage() {
        String jsonStructure = "{\n" +
                "  \"messageID\": \"" + getMessageID() + "\",\n" +
                "  \"recipient\": \"" + recipientCell + "\",\n" +
                "  \"content\": \"" + messageText.replace("\"", "\\\"") + "\"\n" +
                "}";
        System.out.println("-> Saving record to JSON storage structure layer...");
        System.out.println(jsonStructure);
    }

    // Static helper to reset testing context counters safely between separate evaluations
    public static void resetAccumulator() {
        totalMessagesAccumulated = 0;
    }

    // ==================== GETTERS AND SETTERS ====================
    public String getMessageID() { return messageID; }
    public int getMessageCount() { return messageCount; }
    public String getRecipientCell() { return recipientCell; }
    public void setRecipientCell(String recipientCell) { this.recipientCell = recipientCell; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
}
