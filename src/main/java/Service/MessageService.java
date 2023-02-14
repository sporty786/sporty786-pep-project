package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    // no args constructor for creating an account service
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * Used for mock AccountDAO test cases to allow testing of AccountService independently of AccountDAO.
     * @param accountDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    /**
    * @return List<Message> 
    * of all messages in database. Return empty list if no messages.
    */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * Create a new
     *  @param message
     * entry in the database and if successful,
     * @return Message
     * of the inserted message. If unsuccessful, return null.
    */
     public Message createMessage(Message message){
        String message_text = message.getMessage_text();
        // message_text cannot be blank
        if ((message_text == null) || message_text.isBlank()){return null;}
        // message_text must be under 255 characters
        else if (message_text.length() >= 255){return null;}
        // posted_by must refer to existing user
     }

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
