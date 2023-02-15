package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    /**
    * Retrieves all users from the user table.
    * @return all Acounts
    */
    public List<Account> getAllAccounts(){
        // System.out.println("AccountDAO getAllAccounts accessed.");      // Debugging code.
        // Establish connection to database
        Connection connection = ConnectionUtil.getConnection();
        // Create array list to hold list of accounts
        List<Account> accounts = new ArrayList<>();
        try{
            // SQL logic
            String sql = "SELECT * FROM account;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        // Return list of all user accounts
        return accounts;
    }

    /**
     * Registers a new user account with the given
     * @param account
     * @return Account
     */
    public Account userRegistration(Model.Account account){
        // System.out.println("AccountDAO userRegistration accessed.");        // Debugging code
        // Set up connection to database
        Connection connection = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Set parameter values in SQL prepared statement
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
            ResultSet pkeyRS = ps.getGeneratedKeys();
            if(pkeyRS.next()){
                int generated_account_id = (int) pkeyRS.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Seach the database for the account with the given account_it, returning
     * the associated account, null if none exists.
     * @param account_id
     * @return Account
     */
    public Account getAccountByAccountId(int account_id){
        // System.out.println("AccountDAO getAccountByAccountId accessed.");       // Debugging code
        // Create new connection
        Connection connection = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            // Set prepared statement values by given parameters
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            // Return account from result set that matches given query
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Return the account associated with the given username, null if
     * none exists.
     * @param username
     * @return Account
     */
    public Account getAccountByUsername(String username){
        // System.out.println("AccountDAO getAccountByUsername accessed.");        // Debugging code
        // Create new connection
        Connection connection = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            // Set prepared statement values by given parameters
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            // Return account from result set that matches given query
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), 
                    rs.getString("username"), rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
