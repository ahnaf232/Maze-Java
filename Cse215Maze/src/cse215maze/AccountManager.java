/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cse215maze;

import java.io.*;
import java.util.*;

/**
 *
 * @author Rajin
 */
public class AccountManager {
    private static final String filePath = "accounts.txt";
    private Hashtable accounts;

    public AccountManager() {
        accounts = new Hashtable();
        loadAccounts();
    }

    private void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    accounts.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing accounts found. A new file will be created.");
        }
    }

    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Object key : accounts.keySet()) {
                String username = (String) key;
                String password = (String) accounts.get(key);
                writer.write("Username:  "+username + "   Password:  " + password);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public boolean createAccount(String username, String password) {
        if (accounts.containsKey(username)) {
            return false; // Account already exists
        }
        accounts.put(username, password);
        saveAccounts();
        return true;
    }

    public boolean login(String username, String password) {
        return password.equals(accounts.get(username));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountManager accountManager = new AccountManager();

        System.out.println("1. Create account\n2. Login");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (accountManager.createAccount(username, password)) {
                System.out.println("Account created successfully.");
            } else {
                System.out.println("Account creation failed. Username already exists.");
            }
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (accountManager.login(username, password)) {
                System.out.println("Login successful.");
            } else {
                System.out.println("Login failed. Incorrect username or password.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
