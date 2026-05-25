package com.mycompany.part1;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Part1 {

    // === LOGIN CLASS VARIABLES ===
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    // ==== MESSAGE CLASS VARIABLES=====
    private String messageID;
    private String messageHash;
    private String recipientNumber;
    private String messageContent;

    private static int totalMessagesSent = 0;
    private static String allMessages = "";
    

    // == CONSTRUCTOR ==
    public Part1() {}

    public Part1(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // == LOGIN CLASS METHODS ==
    
    // USERNAME VALIDATION
    public boolean checkUserName() {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // PASSWORD VALIDATION
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) return false;

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            if (Character.isDigit(c)) hasNumber = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // FIXED LOGIN METHOD (checks BOTH username and password)
    public boolean loginUser(String userName, String password) {
        return this.username.equals(userName) && this.password.equals(password);
    }
    // user information
        public String getUserLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " " + lastName;
        } else {
            return "Username or password incorrect";
        }
    }

    // GETTERS
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    // == cell number validation ==
    public static boolean isValidCellNumber(String cellNumber) {
        return cellNumber != null && cellNumber.matches("\\+27[0-9]{9}");
    }

    // == MESSAGE CLASS METHODS ==
    public static void startQuickChat(Scanner input, String loggedInUsername) {
     
     Part1 msgHandler = new Part1();
        
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
                        "  \"message\": \"" + msgHandler.messageContent + "\",\n" +
                        "  \"sender\": \"" + loggedInUsername + "\"\n" +
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
    }

    // === MAIN METHOD  ===
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("\n--- REGISTRATION ---");

        // USER DETAILS
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        // USERNAME
        System.out.print("Enter username (must contain '_' and max 5 characters): ");
        String username = input.nextLine();

        Part1 tempLogin = new Part1(username, "", firstName, lastName);

        while (!tempLogin.checkUserName()) {
            System.out.println("Username is not correctly formatted.");
            System.out.print("Enter username again: ");
            username = input.nextLine();
            tempLogin = new Part1(username, "", firstName, lastName);
        }
        System.out.println("Username successfully captured!");

        // PASSWORD
        System.out.print("Enter password (min 8 chars, 1 capital, 1 number, 1 special char): ");
        String password = input.nextLine();

        tempLogin = new Part1(username, password, firstName, lastName);

        while (!tempLogin.checkPasswordComplexity()) {
            System.out.println("Password is not correctly formatted.");
            System.out.print("Enter password again: ");
            password = input.nextLine();
            tempLogin = new Part1(username, password, firstName, lastName);
        }
        System.out.println("Password successfully captured!");

        // CELL NUMBER
        System.out.print("Enter cell phone number (e.g., +27838968976): ");
        String cellNumber = input.nextLine();

        while (!isValidCellNumber(cellNumber)) {
            System.out.println("Cell phone number is incorrectly formatted.");
            System.out.print("Enter again (+27XXXXXXXXX): ");
            cellNumber = input.nextLine();
        }
        System.out.println("Cell number successfully captured!");

        // FINAL USER OBJECT
        Part1 user = new Part1(username, password, firstName, lastName);

        System.out.println("\n REGISTRATION COMPLETE ");

        // LOGIN
        System.out.println("\nLOGIN ");

        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        if (user.loginUser(loginUsername, loginPassword)) {
            System.out.println(user.getUserLoginStatus(true));
            
        // LINK TO MESSAGE SYSTEM 
            startQuickChat(input, loginUsername);
        } else {
            System.out.println(user.getUserLoginStatus(false));
        }

        input.close();
    }
}