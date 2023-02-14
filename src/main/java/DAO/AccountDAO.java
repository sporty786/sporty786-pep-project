package DAO;

import java.Model.Account;
import java.Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // Retrieves all users from the user table.
    // @return all authors
    public List<Account> getAllAccounts(){
        // Establish connection to database
        Connection connection = ConnectionUtil.getConnection();
        // Create array list to hold list of accounts
        List<Account> accounts = new ArrayList<>();
        try{
            // SQL logic
            String sql = "SELECT * FROM account;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        } catch(SQLException e){
            // Close connection to avoid leak if catch triggered
            connection.close();
            System.out.println(e.getMessage());
        }
        // Close connection to avoid leak if catch not triggered
        connection.close();
        // Return list of all user accounts
        return accounts;
    }

    // Registers a new user account with the given
    // @param account
    public Account userRegistration(Model.Account account){
        // Set up connection to database
        Connection connection = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Set parameter values in SQL prepared statement
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                // Close connection if account successfully created to prevent leak
                connection.close();
                return new Account(generated_account_id, account.getUsername());
            }
        }catch(SQLEXception e){
            // Close connection to prevent leak if error triggered
            connection.close();
            System.out.println(e.getMessage());
        }
        // Close connection to prevent leak if try statement successful but new account not created
        connection.close();
        return null;
    }

    // Search the database for the given 
    // @param account_id
    // and if found,
    // @return Account
    // associated with that id.
    public Account getAccountByAccountId(int account_id){
        // Create new connection
        Connection connection = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Set prepared statement values by given parameters
            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            // Return account from result set that matches given query
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                // Close connection if account successfully found
                connection.close();
                return account;
            }
        }catch(SQLException e){
            // Close the connection if error thrown to prevent leak
            connection.close();
            System.out.println(e.getMessage());
        }
        // Close connction if account not correctly found from database to prevent leak
        connection.close();
        return null;
    }

}
