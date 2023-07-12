import java.util.Date;

public class Transaction {

    private double amount; // Сума транзакції

    private String memo; // Очікуване зменшення залишку готівки на банківському рахунку

    private Account inAccount; // Акаунт в якому пройшла ця транзакція

    public Transaction (double amount, Account inAccount)
    {
        this.amount = amount;
        this.inAccount = inAccount;
        this.memo = "";
    }
    public Transaction (double amount, String memo, Account inAccount)
    {
        this.amount = amount;
        this.memo = memo;
        this.inAccount = inAccount;

    }

    public double getAmount ()
    {
        return this.amount;
    }

    public String getSummaryLine ()
    {
        if(this.amount >= 0)
        {
            return String.format("%s : $%.02f : %s", 0,
                    this.amount, this.memo);
        }
        else
        {
            return String.format("%s : $(%.02f) : %s", 0,
                    -this.amount, this.memo);
        }
    }
}
