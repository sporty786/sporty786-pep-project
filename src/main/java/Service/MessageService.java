package Service;

import Model.Message;
import DAO.MessageDAO;
import Service.AccountService;
import Model.Account;

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
     * Create a new message in the database. If successful return the message,
     * otherwise return null if unsuccessful.
     * @param message
     * @return Message
    */
     public Message createMessage(Message message){
        String message_text = message.getMessage_text();
        AccountService accountService = new AccountService();
        // message_text cannot be blank
        if ((message_text == null) || message_text.isBlank()){return null;}
        // message_text must be under 255 characters
        else if (message_text.length() >= 255){return null;}
        // posted_by must refer to existing user
        if (accountService.getAccountByAccountId(message.getPosted_by()) == null){return null;}
        return messageDAO.createMessage(message);
     }

    /**
     * Search the database for a given message given it's message_id. Return Message
     * if successful, null if unsuccessful.
     * @param message_id
     * @return Message
     */
     public Message getMessageByMessageId(int message_id){
        return messageDAO.getMessageByMessageId(message_id);
     }

    /**
     * Remove a message from the database with it's message_id. Return the deleted message
     * if successful, null if unsuccessful.
     * @param message_id.
     * @return Message
     */
    public Message deleteMessageByMessageId(int message_id){
        Message message = messageDAO.getMessageByMessageId(message_id);
        if (messageDAO.deleteMessageByMessageId(message_id) > 0){return message;}
        return null;
    }

    /**
     * Modify a message body by the message id with the given message_text.
     * Return the message if succesful, null otherwise.
     * @param message_id
     * @param message_text
     * @return Message
     */
     public Message updateMessageByMessageId(int message_id, String message_text){
        return messageDAO.updateMessageByMessageId(message_id, message_text);
     }

    /**
     * Given an account_id, return a list of messages posted by that user,
     * blank if none.
     * @param account_id
     * @return List<Message>
     */
    public List<Message> getAllMessagesByAccountId(int account_id){
        return messageDAO.getAllMessagesByAccountId(account_id);
    }
}
