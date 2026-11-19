package com.mycompany.poe1;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

// Manages message creation, validation, JSON storage, and Part 3 array operations
public class Message {
    private String messageID;
    private final int messageCount;
    private String recipientCell;
    private String messageText;

    public static final String JSON_FILE = "stored_messages.json";
    private static int totalMessages = 0;

    // Part 3 dynamic arrays
    public static String[] sentMessages = {};
    public static String[] disregardedMessages = {};
    public static String[] storedMessages = {};
    public static String[] messageHashes = {};
    public static String[] messageIDs = {};

    public static List<Message> sentList = new ArrayList<>();
    public static List<Message> storedList = new ArrayList<>();

    // Generates a 10-digit ID using String manipulation
    public Message(int count) {
        this.messageCount = count;
        long num = Math.abs(new Random().nextLong());
        this.messageID = String.format("%010d", num).substring(0, 10);
        this.recipientCell = "";
        this.messageText = "";
    }

    public boolean checkMessageID() { return messageID != null && messageID.length() <= 10; }

    // Checks for +27 followed by 9 digits
    public String checkRecipientCell() {
        if (recipientCell == null || !recipientCell.matches("^\\+27\\d{9}$")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        return "Cell phone number successfully captured.";
    }

    public String checkMessageLength() {
        if (messageText != null && messageText.length() > 250) {
            return "Message exceeds 250 characters by " + (messageText.length() - 250) + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    // Creates uppercase hash from ID prefix, count, and first/last words
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageText == null || messageText.trim().isEmpty()) return "00:0:UNKNOWN";
        List<String> words = Arrays.asList(messageText.trim().split("\\s+"));
        String first = words.get(0).replaceAll("[^a-zA-Z0-9]", "");
        String last = words.get(words.size() - 1).replaceAll("[^a-zA-Z0-9]", "");
        return messageID.substring(0, 2) + ":" + messageCount + ":" + (first + last).toUpperCase();
    }

    public String SendMessage(int choice) {
        if (choice == 1) { totalMessages++; return "Message successfully sent."; }
        if (choice == 2) { return "Press 0 to delete the message."; }
        if (choice == 3) { totalMessages++; storeMessage(); return "Message successfully stored."; }
        return "Invalid selection.";
    }

    public String printMessages() {
        return "Message ID: " + messageID + "\nMessage Hash: " + createMessageHash() + "\nRecipient: " + recipientCell + "\nMessage: " + messageText;
    }

    public int returnTotalMessages() { return totalMessages; }

    // Attribution: JSON file writing adapted from Java NIO documentation and AI assistance
    public void storeMessage() {
        String clean = (messageText == null) ? "" : messageText.replace("\"", "\\\"");
        String entry = "  {\"messageID\": \"" + messageID + "\", \"messageHash\": \"" + createMessageHash() + "\", \"recipient\": \"" + recipientCell + "\", \"content\": \"" + clean + "\"}";
        try {
            Path path = Paths.get(JSON_FILE);
            List<String> items = new ArrayList<>();
            if (Files.exists(path)) {
                String body = Files.readString(path).trim();
                if (body.length() > 2) items.add(body.substring(1, body.length() - 1).trim());
            }
            items.add(entry);
            Files.writeString(path, "[\n" + String.join(",\n", items) + "\n]", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) { System.out.println("JSON save error: " + e.getMessage()); }
    }

    public static void resetAccumulator() { totalMessages = 0; }

    // Populates Part 3 arrays dynamically
    public static void recordMessage(Message msg, int choice) {
        if (choice == 1) {
            sentMessages = add(sentMessages, msg.getMessageText());
            messageHashes = add(messageHashes, msg.createMessageHash());
            messageIDs = add(messageIDs, msg.getMessageID());
            sentList.add(msg);
        } else if (choice == 2) {
            disregardedMessages = add(disregardedMessages, msg.getMessageText());
        } else if (choice == 3) {
            storedMessages = add(storedMessages, msg.getMessageText());
            messageHashes = add(messageHashes, msg.createMessageHash());
            messageIDs = add(messageIDs, msg.getMessageID());
            storedList.add(msg);
        }
    }

    private static String[] add(String[] arr, String val) {
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        list.add(val);
        return list.toArray(new String[]{});
    }

    // Attribution: JSON file reading into array adapted from Java NIO/Regex documentation and AI assistance
    public static void loadStoredMessagesFromJSON() {
        List<String> texts = new ArrayList<>();
        storedList.clear();
        try {
            Path path = Paths.get(JSON_FILE);
            if (!Files.exists(path)) { storedMessages = new String[]{}; return; }
            Matcher m = Pattern.compile("\\{([^}]*)\\}").matcher(Files.readString(path));
            int i = 1;
            while (m.find()) {
                String b = m.group(1);
                Message msg = new Message(i++);
                msg.setMessageID(read(b, "messageID"));
                msg.setRecipientCell(read(b, "recipient"));
                msg.setMessageText(read(b, "content"));
                storedList.add(msg);
                texts.add(msg.getMessageText());
            }
        } catch (IOException e) {}
        storedMessages = texts.toArray(new String[]{});
    }

    private static String read(String block, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*\"(.*?)\"").matcher(block);
        return m.find() ? m.group(1).replace("\\\"", "\"") : "";
    }

    // Returns longest message across sent and stored arrays
    public static String findLongestMessage() {
        String longest = "";
        for (String s : sentMessages) if (s != null && s.length() > longest.length()) longest = s;
        for (String s : storedMessages) if (s != null && s.length() > longest.length()) longest = s;
        return longest;
    }

    // Searches message by ID
    public static String searchByMessageID(String id) {
        for (Message m : sentList) if (m.getMessageID().equals(id)) return "Recipient: " + m.getRecipientCell() + "\nMessage: " + m.getMessageText();
        for (Message m : storedList) if (m.getMessageID().equals(id)) return "Recipient: " + m.getRecipientCell() + "\nMessage: " + m.getMessageText();
        return "Message ID not found.";
    }

    // Searches all messages for a specific recipient
    public static String searchByRecipient(String rec) {
        List<String> found = new ArrayList<>();
        for (Message m : sentList) if (m.getRecipientCell().equals(rec)) found.add(m.getMessageText());
        for (Message m : storedList) if (m.getRecipientCell().equals(rec)) found.add(m.getMessageText());
        return String.join("\n", found);
    }

    // Deletes message by hash and updates arrays
    public static String deleteByMessageHash(String hash) {
        for (int i = 0; i < storedList.size(); i++) {
            if (storedList.get(i).createMessageHash().equalsIgnoreCase(hash)) {
                String del = storedList.remove(i).getMessageText();
                storedMessages = storedList.stream().map(Message::getMessageText).toArray(String[]::new);
                return "Message \"" + del + "\" successfully deleted.";
            }
        }
        for (int i = 0; i < sentList.size(); i++) {
            if (sentList.get(i).createMessageHash().equalsIgnoreCase(hash)) {
                String del = sentList.remove(i).getMessageText();
                sentMessages = sentList.stream().map(Message::getMessageText).toArray(String[]::new);
                return "Message \"" + del + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // Generates full report of all sent messages
    public static String generateFullReport() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentList) {
            sb.append("Message Hash: ").append(m.createMessageHash()).append("\nRecipient: ").append(m.getRecipientCell()).append("\nMessage: ").append(m.getMessageText()).append("\n");
        }
        return sb.toString();
    }

    public String getMessageID() { return messageID; }
    public void setMessageID(String id) { this.messageID = id; }
    public int getMessageCount() { return messageCount; }
    public String getRecipientCell() { return recipientCell; }
    public void setRecipientCell(String cell) { this.recipientCell = cell; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String text) { this.messageText = text; }
}
