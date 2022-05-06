package main;
import models.Account;
import models.Card;
import models.Client;
import services.TestService;
import java.util.Scanner;
import services.TestService;
public class Main {
    public static void main(String[] args) {
        /*boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        TestService service = new TestService();
        while(!exit) {
            System.out.println("1.Add client\n2.Show Clients\n3.Add Account\n4.Add card\n5.Show Cards\n6.Add Transactions\n7.Show All Exchanges\n8.Exchange\n9.Exit\n");
            int var = scanner.nextInt();
            if (var == 1) service.addClients();
            else if (var == 2) service.showClients();
            else if (var == 3) service.addAccounts();
            else if (var == 4) service.addCards();
            else if (var == 5) service.showCards();
            else if (var == 6) service.addTransactions();
            else if (var == 7) service.showAllExchanges();
            else if (var == 8) service.showAllTransactions();
            else if (var == 9) exit = true;
            else System.out.println("Invalid command");
        }*/
        TestService service = new TestService();
        service.addClients();
        service.showClients();
        service.addAccounts();
        service.addCards();
        service.showCards();
        service.addTransactions();
        service.showAllExchanges();
        service.showAllTransactions();

    }
}
