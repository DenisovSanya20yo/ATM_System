import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name; // Назва банку

    private ArrayList<User> users; // Список користквачів

    private ArrayList<Account> accounts; // Список акаунтів користувачів

    public Bank (String name)
    {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID()
    {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        do{
          uuid = "";
          for (int i = 0; i < len; i++)
          {
              uuid += ((Integer)rng.nextInt(10)).toString();
          }
          nonUnique = false;

          for (User u : this.users)
          {
              if(uuid.compareTo(u.getUUID()) == 0)
              {
                  nonUnique = true;
                  break;
              }
          }
        } while (nonUnique);

        return uuid;
    }

    public String getNewAccountUUID()
    {
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        do{
            uuid = "";
            for (int i = 0; i < len; i++)
            {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;

            for (Account a : this.accounts)
            {
                if(uuid.compareTo(a.getUUID()) == 0)
                {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    public User addUser (String FirstName, String LastName, String pin)
    {
        //створюємо нового користувача та додаємо його до списку
        User NewUser = new User(FirstName, LastName, pin, this);
        this.users.add(NewUser);

        //створюємо ощадний рахунок для користувача та додаємо до списку рахунків Банка та Користувача
        Account NewAccount = new Account("Savings", NewUser, this);
        NewUser.addAccount(NewAccount);
        this.accounts.add(NewAccount);

        return NewUser;
    }

    public void addAccount(Account anAcct)
    {
        this.accounts.add(anAcct);
    }

    public User UserLoging (String userID, String pin)
    {
        //пошук за списком користувачів
        for (User u : this.users)
        {
            // перевірка ID
            if(u.getUUID().compareTo(userID) == 0 && u.validatePIN(pin))
            {
                return u;
            }
        }
        //якщо користувача не знайдено або не правильний пін
        return null;
    }

    public String getName()
    {
        return this.name;
    }
}
