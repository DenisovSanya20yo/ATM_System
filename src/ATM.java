import java.util.Scanner;

public class ATM
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("National Bank");

        //додаємо користувача, який також створює рахунок
        User aUser = theBank.addUser("Oleh", "Palaganuk", "1234");

        //додаємо перевірений рахунок для користувача
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;

        while (true)
        {
            curUser =ATM.mainMenuPrompt(theBank, sc);

            ATM.printUserMenu(curUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc)
    {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to \n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            authUser = theBank.UserLoging(userID, pin);
            if (authUser == null)
            {
                System.out.println("Incorrect user ID/PIN, try again");
            }
        } while (authUser == null);

        return authUser;
    }

    public static void printUserMenu (User theUser, Scanner sc)
    {
        theUser.printAccountSummary();

        int choice;

        do {
            System.out.printf("Welcome %s, what would you like to do?", theUser.getFirstName());
            System.out.println("   1)Show account transaction history");
            System.out.println("   2)Withdrawl");
            System.out.println("   3)Deposit");
            System.out.println("   4)Transfer");
            System.out.println("   5)Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5)
            {
                System.out.println("Invalid");
            }
        }while(choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawlFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }

        if (choice != 5)
        {
            ATM.printUserMenu(theUser, sc);
        }
    }

    public static void showTransHistory (User theUser, Scanner sc)
    {
        int theAc;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + " whose transactions you want to see: ",
                    theUser.numAccounts());
            theAc = sc.nextInt()-1;
            if (theAc < 0 || theAc >= theUser.numAccounts())
            {
                System.out.println("Error. Try again");
            }
        } while (theAc < 0 || theAc >= theUser.numAccounts());

        theUser.printAccTransHistory(theAc);
    }

    public static void transferFunds (User theUser, Scanner sc)
    {
        int fromAc;
        int toAc;
        double amount;
        double acBal;

        do {
            System.out.printf("Enter the number (1-%d) of account\n" +
                    "to transfer from: ", theUser.numAccounts());
            fromAc = sc.nextInt()-1;

            if (fromAc < 0 || fromAc >= theUser.numAccounts())
            {
                System.out.println("Error. Try again.");
            }
        } while (fromAc < 0 || fromAc >= theUser.numAccounts());

        acBal = theUser.getAccountBalance(fromAc);

        do {
            System.out.printf("Enter the number (1-%d) of account\n" +
                    "to transfer to: ", theUser.numAccounts());
            toAc = sc.nextInt()-1;

            if (toAc < 0 || toAc >= theUser.numAccounts())
            {
                System.out.println("Error. Try again.");
            }
        } while (toAc < 0 || toAc >= theUser.numAccounts());

        do {
            System.out.printf("Enter the amount of transfer (max %s.02f) : $", acBal);
            amount = sc.nextDouble();

            if (amount < 0)
            {
                System.out.println("Invalid. Try again.");
            }
            else if (amount > acBal)
            {
                System.out.println("Invalid. Try again.");
            }
        } while (amount < 0 || amount > acBal);

        theUser.addAcctTransaction(fromAc, -1*amount, String.format("Transfer to account %s", theUser.getAcUUID(toAc)));
        theUser.addAcctTransaction(toAc, amount, String.format("Transfer to account %s", theUser.getAcUUID(fromAc)));
    }

    public static void withdrawlFunds (User theUser, Scanner sc)
    {
        int fromAc;
        double amount;
        double acBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of account\n" +
                    "to withdrawl from: ", theUser.numAccounts());
            fromAc = sc.nextInt()-1;

            if (fromAc < 0 || fromAc >= theUser.numAccounts())
            {
                System.out.println("Error. Try again.");
            }
        } while (fromAc < 0 || fromAc >= theUser.numAccounts());

        acBal = theUser.getAccountBalance(fromAc);

        do {
            System.out.printf("Enter the amount of transfer (max %s.02f) : $", acBal);
            amount = sc.nextDouble();

            if (amount < 0)
            {
                System.out.println("Invalid. Try again.");
            }
            else if (amount > acBal)
            {
                System.out.println("Invalid. Try again.");
            }
        } while (amount < 0 || amount > acBal);

        sc.nextLine();

        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(fromAc, -1*amount, memo);
    }

    public static void depositFunds(User theUser, Scanner sc)
    {
        int toAc;
        double amount;
        double acBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of account\n" +
                    "to deposit in: ", theUser.numAccounts());
            toAc = sc.nextInt()-1;

            if (toAc < 0 || toAc >= theUser.numAccounts())
            {
                System.out.println("Error. Try again.");
            }
        } while (toAc < 0 || toAc >= theUser.numAccounts());

        acBal = theUser.getAccountBalance(toAc);

        do {
            System.out.printf("Enter the amount of transfer (max %s.02f) : $", acBal);
            amount = sc.nextDouble();

            if (amount < 0)
            {
                System.out.println("Invalid. Dont have enough money.");
            }
        } while (amount < 0);

        sc.nextLine();

        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(toAc, amount, memo);
    }
}
