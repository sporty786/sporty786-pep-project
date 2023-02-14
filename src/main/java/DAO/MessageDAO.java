package DAO;

import java.Model.Message;
import java.Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    // @return List<Message> 
    // of all messages in database. Return empty list if no messages.
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            // SQL logic
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            // If SQL exception triggers, close connection to prevent leak
            connection.close();
            System.out.println(e.getMessage());
        }
        // Close connection if error not triggered, to prevent leak
        connection.close();
        return messages;
    }

    // Create a new
    // @param message
    // entry in the database and if successful,
    // @return Message
    // of the inserted message. If unsuccessful, return null.
    public Message createMessage(Message message){}

    // Search the database for a given message given it's
    // @param message_id
    // and if successful, 
    // @return Message
    // If unsuccessful return null.
    public Message getMessageByMessageId(){}

    // Remove a message from the database with the id
    // @param message_id.
    // If successful, 
    // @return Message
    // (the message now deleted), or if unsuccessful, return null.
    public Message deleteMessageByMessageId(){}

    // Modify a message body by the message id with
    // @params message_id, message_text
    // If successful,
    // @return Message
    // (the new updated message)
    public Message updateMessageByMessageId(){}

    // Given a 
    // @param user_id
    // foreign key for posted_by entries in the message table, 
    // @return List<Message>
    // a list of all messages posted by that user.
    public List<Message> getAllMessagesByAccountId(){}
}
