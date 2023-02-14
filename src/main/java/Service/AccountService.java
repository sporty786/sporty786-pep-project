package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    // no args constructor for creating an account service
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * Used for mock AccountDAO test cases to allow testing of AccountService independently of AccountDAO.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    /**
    * Retrieves all users from the user table.
    * @return all authors
    */
    public List<Account> getAllAccounts(){
        System.out.println("AccountService getAllAccounts accessed.");
        return accountDAO.getAllAccounts();
    }

    /**
    * Registers a new user account with the given account information.
    * Username must not be blank, password must be at least 4 characters long, and 
    * an account with the same username must not already exist.
    * Returns new account if successful, null if unsuccessful.
    * @param account.
    * @return Account
    */
    public Account userRegistration(Account account){
        System.out.println("AccountService userRegistration accessed.");
        String username = account.getUsername();
        // Username is not blank
        if ((username == null) || (username.isBlank())){return null;}
        // Password must be at least 4 letters long
        else if (account.getPassword().length() < 4){return null;}
        // Account with username does not already exist
        else if (accountDAO.getAccountByUsername(username) != null){return null;}
        return accountDAO.userRegistration(account);
    }

    /**
     * Check that the provided  username and password match an existing entry
     * in the database. Return the account if it matches, or null if no such account exists
     * or the passwords don't match.
     * @param username
     * @param password
     * @return Account
     */
    public Account login(String username, String password){
        System.out.println("AccountService login accessed.");
        Account account = accountDAO.getAccountByUsername(username);
        if (account == null){return null;}
        if (account.getPassword() == password){
            return account;
        }
        return null;
    }

    /**
     * Return the Account with the associated given account_id. Return null if none such exists.
     * @param account_id
     * @return Account
     */
    public Account getAccountByAccountId(int account_id){
        System.out.println("AccountService getAccountByAccountId accessed.");
        return accountDAO.getAccountByAccountId(account_id);
    }
}
