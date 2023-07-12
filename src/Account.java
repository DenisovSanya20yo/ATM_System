import java.util.ArrayList;

public class Account {

    private String name; // Ім'я акаунту

    private String uuid; // ID акаунту

    private User holder; // Користувач, що володіє акаунтом

    private ArrayList<Transaction> transactions; // Список транзакцій

    public Account (String name, User holder, Bank theBank)
    {
        //встановлюємо ім'я та власника аккаунта
        this.name = name;
        this.holder = holder;

        //отримання нового ID акаунту
        this.uuid = theBank.getNewAccountUUID();

        //ініціалізація списку транзакцій
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID()
    {
        return this.uuid;
    }

    public String getSummaryLine ()
    {
        double balance = this.getBalance();

        if (balance >= 0)
        {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        }
        else
        {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }
    }

    public double getBalance ()
    {
        double balance = 0;

        for (Transaction t : this.transactions)
        {
            balance += t.getAmount();
        }

        return balance;
    }

    public void printTransHistory ()
    {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);

        for (int i = this.transactions.size()-1; i >= 0; i--)
        {
            System.out.printf(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction (double amount, String memo)
    {
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
