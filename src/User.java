import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String FirstName; // Ім'я користувача

    private String LastName; // Прізвище користувача

    private String uuid; // ID користувача

    private byte pinHash[]; // MD5 хеш PIN-коду користувача

    private ArrayList<Account> accounts; // Список акаунтів користувача

    public User (String FirstName, String LastName, String pin, Bank theBank)
    {
        //встановлюємо ім'я користувача
        this.FirstName = FirstName;
        this.LastName = LastName;

        //зберігаємо хеш пина, а не вхідне значення, з міркувань безпеки
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        //отримання нового id користувача
        this.uuid = theBank.getNewUserUUID();

        //створюєму порожній список акаунтів
        this.accounts = new ArrayList<Account>();

        System.out.println("New user " + FirstName + " " + LastName + " with ID " + this.uuid + " was created.\n");
    }

    public void addAccount(Account anAcct)
    {
        this.accounts.add(anAcct);
    }

    public String getUUID()
    {
        return this.uuid;
    }

    public boolean validatePIN (String aPIN)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPIN.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, NonSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName ()
    {
        return this.FirstName;
    }

    public void printAccountSummary()
    {
        System.out.printf("\n\n%s's account summary", this.FirstName);
        for(int a = 0; a < this.accounts.size(); a++)
        {
            System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts ()
    {
        return this.accounts.size();
    }

    public void printAccTransHistory (int theAcIndx)
    {
        this.accounts.get(theAcIndx).printTransHistory();
    }

    public double getAccountBalance(int theAccIndx)
    {
        return  this.accounts.get(theAccIndx).getBalance();
    }

    public String getAcUUID (int theAccIndx)
    {
        return this.accounts.get(theAccIndx).getUUID();
    }

    public void addAcctTransaction (int theAccIndx, double amount, String memo)
    {
        this.accounts.get(theAccIndx).addTransaction(amount, memo);
    }
}
