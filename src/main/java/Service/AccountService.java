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
        return accountDAO.getAllAccounts();
    }

    /**
    * Registers a new user account with the given
    * @param account.
    * If successful
    * @return Account
    * If unsuccessful returns null.
    */
    public Account userRegistration(Model.Account account){
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
     * Check that the provided 
     * @param username
     * @param password
     * match an existing corresponding entry in the database.
     * @return Account
     * that it matches.
     * Return null if no such account exists, or the password doesn't match.
     */
    public Account login(String username, String password){
        Account account = accountDAO.getAccountByUsername(username);
        if (account == null){return null;}
        if (account.getPassword() == password){
            return account;
        }
        return null;
    }

}
