package com.mycompany.poe1;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        Message.sentMessages = new String[]{};
        Message.disregardedMessages = new String[]{};
        Message.storedMessages = new String[]{};
        Message.messageHashes = new String[]{};
        Message.messageIDs = new String[]{};
        Message.sentList.clear();
        Message.storedList.clear();
        Message.resetAccumulator();

        Message m1 = new Message(1);
        m1.setMessageID("0834557896");
        m1.setRecipientCell("+27834557896");
        m1.setMessageText("Did you get the cake?");
        Message.recordMessage(m1, 1);

        Message m2 = new Message(2);
        m2.setMessageID("0838884567");
        m2.setRecipientCell("+27838884567");
        m2.setMessageText("Where are you? You are late! I have asked you to be on time.");
        Message.recordMessage(m2, 3);

        Message m3 = new Message(3);
        m3.setMessageID("0834484567");
        m3.setRecipientCell("+27834484567");
        m3.setMessageText("Yohoooo, I am at your gate.");
        Message.recordMessage(m3, 2);

        Message m4 = new Message(4);
        m4.setMessageID("0838884567");
        m4.setRecipientCell("0838884567");
        m4.setMessageText("It is dinner time!");
        Message.recordMessage(m4, 1);

        Message m5 = new Message(5);
        m5.setMessageID("0838884567");
        m5.setRecipientCell("+27838884567");
        m5.setMessageText("Ok, I am leaving without you.");
        Message.recordMessage(m5, 3);
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        List<String> sent = Arrays.asList(Message.sentMessages);
        assertEquals(2, sent.size());
        assertEquals("Did you get the cake?", sent.get(0));
        assertEquals("It is dinner time!", sent.get(1));
    }

    @Test
    public void testDisplayLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.", Message.findLongestMessage());
    }

    @Test
    public void testSearchForMessageID() {
        assertTrue(Message.searchByMessageID("0838884567").contains("It is dinner time!"));
    }

    @Test
    public void testSearchAllMessagesForRecipient() {
        String result = Message.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        String hash = Message.storedList.get(0).createMessageHash();
        assertEquals("Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", Message.deleteByMessageHash(hash));
    }

    @Test
    public void testDisplayReport() {
        String report = Message.generateFullReport();
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
        assertTrue(report.contains("Message: It is dinner time!"));
    }
}
