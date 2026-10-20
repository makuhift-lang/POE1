package com.mycompany.poe1;

import java.util.Random;

/**
 * Message class managing metadata, validation, tracking hashes, and JSON serialization.
 * @author Student
 */
public class Message {
    
    private String messageID;
    private final int messageCount;
    private String recipientCell;
    private String messageText;
    
    // Tracks accumulated valid messages across the application lifetime
    private static int totalMessagesAccumulated = 0;

    public Message(int currentMessageNumber) {
        this.messageCount = currentMessageNumber;
        this.messageID = generateRandomMessageID();
        this.recipientCell = "";
        this.messageText = "";
    }

    // Auto-generates a unique tracking 10-digit identity value
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

    // Method Functionality: Ensures recipient cell contains an international code and fits length rules
    public String checkRecipientCell() {
        if (recipientCell == null || recipientCell.length() < 7 || recipientCell.length() > 14 || !recipientCell.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        return "Cell phone number successfully captured.";
    }

    // Utility Validation: Checks if message text conforms to the 250 character cap rules
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

    // Method Functionality: Creates and returns the Message Hash in ALL CAPS
    public String createMessageHash() {
        if (getMessageID() == null || getMessageID().length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            return "00:0:UNKNOWN";
        }
        
        String firstTwoDigits = getMessageID().substring(0, 2);
        
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        
        String combinedWords = (firstWord + lastWord).toUpperCase();
        
        return firstTwoDigits + ":" + messageCount + ":" + combinedWords;
    }

    // Method Functionality: Allows the user to choose to send, store, or disregard the message
    public String SendMessage(int userSelection) {
        switch (userSelection) {
            case 1:
                totalMessagesAccumulated++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                totalMessagesAccumulated++;
                return "Message successfully stored.";
            default:
                return "Invalid selection.";
        }
    }

    // Method Functionality: Returns all structural message info sequentially in the correct display order
    public String printMessages() {
        return "Message ID: " + getMessageID() + "\n" +
               "Message Hash: " + createMessageHash() + "\n" +
               "Recipient: " + recipientCell + "\n" +
               "Message: " + messageText;
    }

    // Method Functionality: Returns the total number of valid messages sent/stored
    public int returnTotalMessages() {
        return totalMessagesAccumulated;
    }

    // Research Method: Builds a valid JSON layout out of the message object properties
    public void storeMessage() {
        String jsonStructure = "{\n" +
                "  \"messageID\": \"" + getMessageID() + "\",\n" +
                "  \"recipient\": \"" + recipientCell + "\",\n" +
                "  \"content\": \"" + messageText.replace("\"", "\\\"") + "\"\n" +
                "}";
        System.out.println("-> Saving record to JSON storage structure layer...");
        System.out.println(jsonStructure);
    }

    // Helper to safely reset metrics between distinct automated unit tests
    public static void resetAccumulator() {
        totalMessagesAccumulated = 0;
    }

    // ==================== GETTERS AND SETTERS ====================
    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; } 
    public int getMessageCount() { return messageCount; }
    public String getRecipientCell() { return recipientCell; }
    public void setRecipientCell(String recipientCell) { this.recipientCell = recipientCell; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
}
