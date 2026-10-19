/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poe1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class MessageTest {
    
    public MessageTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Clear global shared statistics counters before each unit test fires up
        Message.resetAccumulator();
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testMessageLengthSuccess() {
        Message checkInstance = new Message(0);
        checkInstance.setMessageText("Hi Mike, can you join us for dinner tonight?");
        
        assertEquals("Message ready to send.", checkInstance.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        Message checkInstance = new Message(0);
        
        // Dynamically build text breaking structural character size rules by exactly 10 points
        StringBuilder heavyPayload = new StringBuilder();
        for (int stepIndex = 0; stepIndex < 260; stepIndex++) {
            heavyPayload.append("X");
        }
        checkInstance.setMessageText(heavyPayload.toString());
        
        String validationExpectation = "Message exceeds 250 characters by 10; please reduce the size.";
        assertEquals(validationExpectation, checkInstance.checkMessageLength());
    }

    @Test
    public void testRecipientCellSuccess() {
        Message checkInstance = new Message(0);
        checkInstance.setRecipientCell("+27718693002");
        
        assertEquals("Cell phone number successfully captured.", checkInstance.checkRecipientCell());
    }

    @Test
    public void testRecipientCellFailure() {
        Message checkInstance = new Message(0);
        checkInstance.setRecipientCell("08575975889");
        
        String validationExpectation = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        assertEquals(validationExpectation, checkInstance.checkRecipientCell());
    }

    @Test
    public void testMessageHashCreation() {
        // Subclass overrides random sequence variable generation loops so tests reliably match prompt outputs
        Message checkInstance = new Message(0) {
            @Override
            public String getMessageID() {
                return "0088776655";
            }
        };
        
        checkInstance.setMessageText("Hi Mike, can you join us for dinner tonight?");
        
        // Expected Structure: First two identity characters ("00"), Message count ("0"), combined alpha words uppercase ("HITONIGHT")
        String validationExpectation = "00:0:HITONIGHT";
        assertEquals(validationExpectation, checkInstance.createMessageHash());
    }

    @Test
    public void testMessageIDFormatStructure() {
        Message checkInstance = new Message(1);
        
        assertTrue(checkInstance.checkMessageID());
        assertNotNull(checkInstance.getMessageID());
        assertEquals(10, checkInstance.getMessageID().length());
    }

    @Test
    public void testSendMessageActions() {
        Message checkInstance = new Message(0);
        
        assertEquals("Message successfully sent.", checkInstance.SendMessage(1));
        assertEquals("Press 0 to delete the message.", checkInstance.SendMessage(2));
        assertEquals("Message successfully stored.", checkInstance.SendMessage(3));
        
        // Final running metric count should accumulate exactly to 2 items
        assertEquals(2, checkInstance.returnTotalMessages());
    }
}
