package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    /**
    * @return List<Message> 
    * of all messages in database. Return empty list if no messages.
    */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            // SQL logic
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
    * Create a new
    * @param message
    * entry in the database and if successful,
    * @return Message
    * of the inserted message. If unsuccessful, return null.
    */
    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL logic
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES ( ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set Prepared Statement's parameter values
            ps.setInt(2, message.getPosted_by());
            ps.setString(3, message.getMessage_text());
            ps.setLong(4, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet pkeyRS = ps.getGeneratedKeys();
            if (pkeyRS.next()){
                int generated_message_id = (int) pkeyRS.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), 
                    message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
    * Search the database for a given message given it's
    * @param message_id
    * and if successful, 
    * @return Message
    * If unsuccessful return null.
    */
    public Message getMessageByMessageId(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set prepared statements values from params
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
    * Remove a message from the database with the id
    * @param message_id.
    * If successful, 
    * @return Message
    * (the message now deleted), returning the number of updated rows.
    */
    public int deleteMessageByMessageId(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set prepared statement's values with parameter
            preparedStatement.setInt(1, message_id);
            int affectedRows = preparedStatement.executeUpdate();
            // Close the connection and return updated rows if successful
            if(!connection.isClosed()){connection.close();}
            return affectedRows;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        // Return 0 if unsuccessful for some reason
        return 0;
    }

    /**
    * Modify a message body by the message id with
    * @param message_id
    * @param message_text
    * If successful,
    * @return Message
    * (the new updated message)
    */
    public Message updateMessageByMessageId(int message_id, String message_text){
        Connection connection = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String sql = "UPDATE message SET message_text = '?' WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set prepared statements parameter values
            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);

            preparedStatement.executeUpdate();
            return getMessageByMessageId(message_id);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
    * Given a 
    * @param user_id
    * foreign key for posted_by entries in the message table, 
    * @return List<Message>
    * a list of all messages posted by that user.
    */
    public List<Message> getAllMessagesByAccountId(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            // SQL logic
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set prepared statements parameters
            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                    messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
