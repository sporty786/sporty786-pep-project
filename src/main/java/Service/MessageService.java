package java.Service;

import java.Model.Account;
import java.DAO.AccountDAO;

import java.util.List;

public class MessageService {


    /**
    * @return List<Message> 
    * of all messages in database. Return empty list if no messages.
    */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        
    }

    /**
     * Create a new
     *  @param message
     * entry in the database and if successful,
     * @return Message
     * of the inserted message. If unsuccessful, return null.
    */
     public Message createMessage(Message message){}

    /**
     * Search the database for a given message given it's
     * @param message_id
     * and if successful, 
     * @return Message
     * If unsuccessful return null.
     */
     public Message getMessageByMessageId(){}

    /*
     * Remove a message from the database with the id
     * @param message_id.
     * If successful, 
     * @return Message
     * (the message now deleted), or if unsuccessful, return null.
     */
    public Message deleteMessageByMessageId(){}

    /**
     * Modify a message body by the message id with
     * @params message_id, message_text
     * If successful,
     * @return Message
     * (the new updated message)
     */
     public Message updateMessageByMessageId(){}

    /**
     * Given a 
     * @param user_id
     * foreign key for posted_by entries in the message table, 
     * @return List<Message>
     * a list of all messages posted by that user.
     */
    public List<Message> getAllMessagesByAccountId(){}
}
