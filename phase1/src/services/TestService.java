package services;
import models.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;

public class TestService {
    static HashMap<Integer, Client> clients;
    ArrayList<ExchangeTransaction> exTransactions;
    ReaderService csvReader;
    static WriterService auditWriter;

    public TestService(){
        clients = new HashMap<>();
        exTransactions = new ArrayList<>();
        csvReader = ReaderService.getInstance();
        auditWriter = WriterService.getInstance();
    }

    public static void addClient() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter client details:");
        System.out.println("First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.println("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Birth date (DD-MM-YYYY):");
        String date = scanner.nextLine();
        Client client = new Client(Client.getMaxId()+1, firstName, lastName, phoneNumber, date);
        int clientId = client.getClientId();
        clients.put(clientId, client);
        auditWriter.audit("addClient");
    }

    public static void showClients() {
        System.out.println("Clients:");

        for(int id : clients.keySet())
            System.out.println(clients.get(id));
        auditWriter.audit("showClients");
    }

    public static void addAccount() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("For whom? (Enter client's id): ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter currency type: ");
        String currencyType = scanner.nextLine();
        System.out.println("Enter the balance: ");
        double balance = scanner.nextDouble();
        Client client = clients.get(clientId);
        Account account = new Account(Account.getMaxId()+1, balance, currencyType);
        client.addAccount(account);

        auditWriter.audit("createAccount");
    }

    public static void deleteAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter client's id: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter account id: ");
        int accountId = scanner.nextInt();
        Client client = clients.get(clientId);
        client.deleteAccount(accountId);

    }

    public static void addCard() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("For whom? (Enter client's id): ");
        int clientId = scanner.nextInt();
        System.out.println("Enter account id: ");
        int accountId = scanner.nextInt(); scanner.nextLine();
        System.out.println("Enter card number: ");
        String number = scanner.nextLine();
        System.out.println("Enter CVV: ");
        int CVV = scanner.nextInt(); scanner.nextLine();
        System.out.println("Enter card type: (credit/debit) ");
        String cardType = scanner.nextLine();

        Client client = clients.get(clientId);
        if(client == null) {
            System.out.println("Invalid client!");
        }

        Card card;
        boolean found = false;
        for(Account account : client.getAccounts()) {
            if(account.getId() == accountId) {
                found = true;

                if(cardType.equals("debit")) {
                    card = new DebitCard(Card.getMaxId()+1, number, CVV);
                    account.addCard(card);

                } else {
                    card = new CreditCard(Card.getMaxId()+1, number, CVV);
                    account.addCard(card);

                }
            }
        }
        if(!found) {
            System.out.println("Invalid account!");
        }

        auditWriter.audit("addCard");
    }

    public static void showCards() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Show cards");
        System.out.println("Enter Client id: ");
        int clientId = scanner.nextInt();
        System.out.println("Enter account id: ");
        int accountId = scanner.nextInt();

        Client client = clients.get(clientId);
        if(client == null) {
            System.out.println("Invalid client!");
            return;
        }
        boolean found = false;
        for(Account account : client.getAccounts()) {
            if(account.getId() == accountId) {
                found = true;
                for(Card card : account.getCards())
                    System.out.println(card);
            }
        }
        if(!found) {
            System.out.println("Invalid account!");
            return;
        }
        auditWriter.audit("showCards");
    }

    public static void addMoney() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("For whom? (Enter client's id): ");
        int clientId = scanner.nextInt();
        System.out.println("Enter account id: ");
        int accountId = scanner.nextInt();
        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();

        Client client = clients.get(clientId);
        if(client == null) {
            System.out.println("Invalid client!");
            return;
        }
        boolean found = false;
        for(Account account : client.getAccounts()) {
            if(account.getId() == accountId) {
                found = true;

                Transaction transaction = new Transaction(accountId, amount);

                account.addMoney(transaction);

            }
        }
        if(!found) {
            System.out.println("Invalid account!");
        }
    }

    public static void retrieveMoney() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("For whom? (Enter client's id): ");
        int clientId = scanner.nextInt();
        System.out.println("Enter account id: ");
        int accountId = scanner.nextInt();
        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();

        Client client = clients.get(clientId);
        if(client == null) {
            System.out.println("Invalid client!");
            return;
        }
        boolean found = false;
        for(Account account : client.getAccounts()) {
            if(account.getId() == accountId) {
                found = true;

                Transaction transaction = new Transaction(accountId, amount);

                account.retrieveMoney(transaction);

            }
        }
        if(!found) {
            System.out.println("Invalid account!");
        }
    }

    public static void exchange() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("From whom? (Enter client's id): ");
        int clientId1 = scanner.nextInt();
        System.out.println("Enter account id: ");
        int account1 = scanner.nextInt();
        System.out.println("To whom? (Enter client's id): ");
        int clientId2 = scanner.nextInt();
        System.out.println("Enter account id: ");
        int account2 = scanner.nextInt();
        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();

        Client client1 = clients.get(clientId1);
        Client client2 = clients.get(clientId2);

        boolean found = false;
        for(Account account : client1.getAccounts()) {
            if(account.getId() == account1) {
                found = true;

                Transaction transaction = new Transaction(account1, amount);
                account.retrieveMoney(transaction);

            }
        }
        if(!found) {
            System.out.println("Invalid account!");
            return;
        }

        found = false;
        for(Account account : client2.getAccounts()) {
            if(account.getId() == account2) {
                found = true;

                Transaction transaction = new Transaction(account1, amount);
                account.addMoney(transaction);

            }
        }
        if(!found) {
            System.out.println("Invalid account!");
            return;
        }

        ExchangeTransaction ext = new ExchangeTransaction(account1, account2, amount);

        auditWriter.audit("exchange");
    }

    public static void showAllTransactions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Client id: ");
        int clientId = scanner.nextInt();
        System.out.println("Enter account id: ");
        int accountId = scanner.nextInt();

        Client client = clients.get(clientId);
        if(client == null) {
            System.out.println("Invalid client!");
            return;
        }
        boolean found = false;
        for(Account account : client.getAccounts()) {
            if(account.getId() == accountId) {
                found = true;
                for(Transaction transaction : account.getTransactions())
                    System.out.println(transaction);
            }
        }
        if(!found) {
            System.out.println("Invalid account!");
            return;
        }
        auditWriter.audit("shoeAllTransactions");
    }

    public void showAllExchanges() {
        for(ExchangeTransaction ext : exTransactions) {
            System.out.println(ext);
        }
        auditWriter.audit("showAllExchanges");
    }
}
