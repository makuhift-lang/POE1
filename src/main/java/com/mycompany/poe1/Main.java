package com.mycompany.poe1;

import java.util.Scanner;

// Main application class handling registration, login, and QuickChat Part 2/3 menus
public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            Login acc = new Login();

            // Registration validation loops
            System.out.print("First Name: "); acc.setFirstName(in.nextLine());
            System.out.print("Last Name: "); acc.setLastName(in.nextLine());
            String sender = acc.getFirstName() + " " + acc.getLastName();

            while (true) {
                System.out.print("Username (_ & max 5 chars): "); acc.setUsername(in.nextLine());
                if (acc.checkUserName()) break;
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
            while (true) {
                System.out.print("Password (8+ chars, upper, num, symbol): "); acc.setPassword(in.nextLine());
                if (acc.checkPasswordComplexity()) break;
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
            while (true) {
                System.out.print("Cell (+27 and 9 digits): "); acc.setCellPhone(in.nextLine());
                if (acc.checkCellPhoneNumber()) break;
                System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
            }
            System.out.println(acc.registerUser());

            // User login verification
            boolean loggedIn = false;
            while (!loggedIn) {
                System.out.print("Login Username: "); String u = in.nextLine();
                System.out.print("Login Password: "); String p = in.nextLine();
                loggedIn = acc.loginUser(u, p);
                System.out.println(acc.returnLoginStatus(loggedIn));
            }

            // Load JSON messages and launch main QuickChat menu
            Message.loadStoredMessagesFromJSON();
            System.out.println("Welcome to QuickChat.");

            boolean run = true;
            while (run) {
                System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit\n4) Stored Messages");
                String opt = in.nextLine().trim();
                if (opt.equals("1")) {
                    System.out.print("How many messages? ");
                    int n = Integer.parseInt(in.nextLine().trim());
                    for (int i = 0; i < n; i++) {
                        Message m = new Message(i + 1);
                        while (true) {
                            System.out.print("Recipient Cell: "); m.setRecipientCell(in.nextLine().trim());
                            if (m.checkRecipientCell().equals("Cell phone number successfully captured.")) break;
                            System.out.println(m.checkRecipientCell());
                        }
                        while (true) {
                            System.out.print("Message: "); m.setMessageText(in.nextLine());
                            if (m.checkMessageLength().equals("Message ready to send.")) break;
                            System.out.println(m.checkMessageLength());
                        }
                        System.out.println("1 - Send | 2 - Disregard | 3 - Store");
                        int act = Integer.parseInt(in.nextLine().trim());
                        System.out.println(m.SendMessage(act));
                        Message.recordMessage(m, act);
                        if (act == 1 || act == 3) System.out.println(m.printMessages());
                    }
                } else if (opt.equals("2")) {
                    for (String s : Message.sentMessages) System.out.println("- " + s);
                } else if (opt.equals("3")) {
                    run = false;
                } else if (opt.equals("4")) {
                    // Part 3 Stored Messages submenu (a through f)
                    Message.loadStoredMessagesFromJSON();
                    System.out.println("a) Sender/Recipient | b) Longest | c) Search ID | d) Search Recipient | e) Delete Hash | f) Report");
                    String sub = in.nextLine().trim().toLowerCase();
                    if (sub.equals("a")) for (Message m : Message.storedList) System.out.println("Sender: " + sender + " | Recipient: " + m.getRecipientCell());
                    else if (sub.equals("b")) System.out.println(Message.findLongestMessage());
                    else if (sub.equals("c")) { System.out.print("ID: "); System.out.println(Message.searchByMessageID(in.nextLine().trim())); }
                    else if (sub.equals("d")) { System.out.print("Recipient: "); System.out.println(Message.searchByRecipient(in.nextLine().trim())); }
                    else if (sub.equals("e")) { System.out.print("Hash: "); System.out.println(Message.deleteByMessageHash(in.nextLine().trim())); }
                    else if (sub.equals("f")) System.out.println(Message.generateFullReport());
                }
            }
        }
    }
}
