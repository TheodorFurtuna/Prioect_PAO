package main;
import services.TestService;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while(!exit) {
            System.out.println("1.Add client\n2.Show Clients\n3.Add Account\n4.Add card\n5.Show Cards\n6.Add money\n7.Retrieve money\n8.Exchange\n9.Show all transactions\n10.Close account\n11.Exit");
            int var = scanner.nextInt();
            if(var == 1) services.TestService.addClient();
            else if(var == 2) services.TestService.showClients();
            else if(var == 3) services.TestService.addAccount();
            else if(var == 4) services.TestService.addCard();
            else if(var == 5) services.TestService.showCards();
            else if(var == 6) services.TestService.addMoney();
            else if(var == 7) services.TestService.retrieveMoney();
            else if(var == 8) services.TestService.exchange();
            else if(var == 9) services.TestService.showAllTransactions();
            else if(var == 10) services.TestService.deleteAccount();
            else if(var == 11) exit = true;
            else System.out.println("Invalid command");
        }
    }
}
