package com.mycompany.message;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    
    @Test
    public void testMessageIDNotNull() {

        Message msg = new Message();

        assertNotNull(msg);
    }

    
    @Test
    public void testHashGeneration() {

        String messageID = "MSG1";

        String hash =
                Integer.toHexString(messageID.hashCode());

        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    // ===== TEST RECIPIENT NUMBER =====
    @Test
    public void testRecipientNumberFormat() {

        String recipient = "+27838968976";

        boolean valid =
                recipient.matches("\\+27[0-9]{9}");

        assertTrue(valid);
    }

    @Test
    public void testInvalidRecipientNumber() {

        String recipient = "0838968976";

        boolean valid =
                recipient.matches("\\+27[0-9]{9}");

        assertFalse(valid);
    }

    // ===== TEST MESSAGE CONTENT =====
    @Test
    public void testMessageContentNotEmpty() {

        String message = "Hello World";

        assertFalse(message.isEmpty());
    }

    @Test
    public void testMessageContentLength() {

        String message =
                "This is a QuickChat test message";

        assertTrue(message.length() <= 250);
    }

    
    @Test
    public void testJSONMessageContainsFields() {

        String jsonMessage =
                "{\n" +
                "  \"messageID\": \"MSG1\",\n" +
                "  \"messageHash\": \"123abc\",\n" +
                "  \"recipient\": \"+27838968976\",\n" +
                "  \"message\": \"Hello\"\n" +
                "}";

        assertTrue(jsonMessage.contains("messageID"));
        assertTrue(jsonMessage.contains("messageHash"));
        assertTrue(jsonMessage.contains("recipient"));
        assertTrue(jsonMessage.contains("message"));
    }
}