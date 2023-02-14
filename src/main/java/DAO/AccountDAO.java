package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    /**
    * Retrieves all users from the user table.
    * @return all authors
    */
    public List<Account> getAllAccounts(){
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
     * @param account.
     */
    public Account userRegistration(Model.Account account){
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
     * Seach the database for the given
     * @param account_id
     * and if found,
     * @return Account
     * associated with that id.
     */
    public Account getAccountByAccountId(int account_id){
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
                // Close connection if account successfully found
                if (!connection.isClosed()){connection.close();}
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @return Account
     * by the
     * @param username
     * from the account table. Return null if it does not exist.
     */
    public Account getAccountByUsername(String username){
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
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                // Close connection if account successfully found
                if (!connection.isClosed()){connection.close();}
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
