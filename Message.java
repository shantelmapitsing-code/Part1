package com.mycompany.message;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private String messageID;
    private String messageHash;
    private String recipientNumber;
    private String messageContent;

    private static int totalMessagesSent = 0;
    private static String allMessages = "";

    public static void main(String[] args) {

        Message msgHandler = new Message();
        Scanner input = new Scanner(System.in);

        // WELCOME MESSAGE
        System.out.println("Welcome to QuickChat.");

        int option = 0;

        // Use a loop
        while (option != 3) {

            // MENU
            System.out.println("\n1) Send Message");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            System.out.print("Choose an option: ");

            try {

                option = Integer.parseInt(input.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
                continue;
            }

            // OPTION 1
            if (option == 1) {

                // RECIPIENT
                System.out.print("Enter recipient number: ");
                String recipient = input.nextLine();

                // MESSAGE
                System.out.print("Enter message content: ");
                String content = input.nextLine();

                // CREATE MESSAGE OBJECT
                msgHandler = new Message();

                msgHandler.recipientNumber = recipient;
                msgHandler.messageContent = content;

                msgHandler.messageID = "MSG" + (totalMessagesSent + 1);

                msgHandler.messageHash =
                        Integer.toHexString(msgHandler.messageID.hashCode());

                totalMessagesSent++;

                // JSON FORMAT
                String jsonMessage =
                        "{\n" +
                        "  \"messageID\": \"" + msgHandler.messageID + "\",\n" +
                        "  \"messageHash\": \"" + msgHandler.messageHash + "\",\n" +
                        "  \"recipient\": \"" + msgHandler.recipientNumber + "\",\n" +
                        "  \"message\": \"" + msgHandler.messageContent + "\"\n" +
                        "}\n";

                // STORE IN VARIABLE
                allMessages += jsonMessage;

                // SAVE TO JSON FILE
                try {

                    FileWriter writer =
                            new FileWriter("messages.json", true);

                    writer.write(jsonMessage);
                    writer.close();

                    System.out.println("Message stored in JSON file successfully.");

                } catch (IOException e) {

                    System.out.println("Error saving message.");
                }

                System.out.println("Message sent successfully.");
            }

            // OPTION 2
            else if (option == 2) {

                System.out.println("Coming Soon.");
            }

            // OPTION 3
            else if (option == 3) {

                System.out.println("Goodbye!");
            }

            // INVALID OPTION
            else {

                System.out.println("Invalid choice.");
            }
        }

        input.close();
    }
}