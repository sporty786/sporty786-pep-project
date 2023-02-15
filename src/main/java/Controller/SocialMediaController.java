package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postUserRegistrationHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postCreateNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getOneMessageGivenMessageIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageGivenMessageIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageGivenMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserGivenAccountIdHandler);
        // Debugging handler
        app.get("/accounts", this::getAllAccountsHandler);

        return app;
    }

    /**
     * Handler to register a new user. Returns in response body new Account as a JSON 
     * representation if successful. Returns status code 400 if unsuccessful.
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postUserRegistrationHandler(Context ctx) throws JsonProcessingException {
        // System.out.println("\nAccessed POST Registration Handler.");    // Debugging code
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.userRegistration(account);
        if (newAccount != null){
            ctx.json(om.writeValueAsString(newAccount));
            ctx.status(200);
        } else {ctx.status(400);}
    }

    /**
     * Handler to post a new login. Returns in response body the corresponding Account object as a JSON
     * representation if successful. Returns status code 400 if unsuccessful.
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        // System.out.println("\nAccessed postLoginHandler");      // Debugging code
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account checkAccount = accountService.login(account.getUsername(), account.getPassword());
        if (checkAccount != null){
            ctx.json(checkAccount);
            ctx.status(200);
        } else {ctx.status(401);}
    }

    /** 
     * Handler to post a new message. Returns status code 400 if unsuccessful, otherwise returns
     * new Message as a JSON representation in response body.
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postCreateNewMessageHandler(Context ctx) throws JsonProcessingException {
        // System.out.println("\nAccessed postCreateNewMessageHandler");       // Debugging code
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if (newMessage != null){
            ctx.json(newMessage);
            ctx.status(200);
        } else {ctx.status(400);}
    }

    /**
     * Handler to return a list of all messages in response body.
     * @param ctx
     */
    private void getAllMessagesHandler(Context ctx){
        // System.out.println("\nAccessed getAllMessagesHandler.");        // Debugging code
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }
    
    /**
     * Handler to get a message from the given message_id. Response body contains the Message if 
     * successful, or an empty response body if unsuccessful.
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting object into JSON.
     */
    private void getOneMessageGivenMessageIdHandler(Context ctx) throws JsonProcessingException{
        // System.out.println("\nAccessed getOneMessageGivenMessageIdHandler.");       // Debugging code
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByMessageId(message_id);
        if (message != null){ctx.json(message);}
        ctx.status(200);
    }

    /**
     * Handler to delete a message with the given message_id. Response body contains the deleted 
     * message if successful, or an empty response body if the message did not exist. 
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting object into JSON.
     */
    private void deleteMessageGivenMessageIdHandler(Context ctx) throws JsonProcessingException {
        // System.out.println("\nAccessed deleteMessageGivenMessageIdHandler.");       // Debugging code
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageByMessageId(message_id);
        if (message != null){ctx.json(message);}
        ctx.status(200);
    }

    /**
     * Handler to patch the message with the given message_id to update it's message_text.
     * If successful, response body contains full updated message. Else returns status code 400.
     * @param ctx
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void patchMessageGivenMessageIdHandler(Context ctx)throws JsonProcessingException {
        // System.out.println("\nAccessed patchMessageGivenMessageIdHandler.");        // Debugging code
        ObjectMapper om = new ObjectMapper();
        String message_text = om.readValue(ctx.body(), Message.class).getMessage_text();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.updateMessageByMessageId(message_id, message_text);
        if (message != null){
            ctx.json(message);
            ctx.status(200);
        } else {ctx.status(400);}
    }

    /**
     * Handler to get all messages posted by a user with the given account_id. Returns
     * list of those messages if successful, else returns an empty list if none exist.
     * @param ctx
     */
    private void getAllMessagesFromUserGivenAccountIdHandler(Context ctx){
        // System.out.println("\nAccessed getAllMessagesFromUserGivenAccountIdHandler.");      // Debugging code
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        ctx.json(messages);
        ctx.status(200);
    }

    /** 
     * Return all accounts in the database. Primarily a handler for debugging.
     * @param ctx
     */
    private void getAllAccountsHandler(Context ctx){
        // System.out.println("\nAccessed getAllAccountsHandler.");        // Debugging code
        List<Account> accounts = accountService.getAllAccounts();
        ctx.json(accounts);
        ctx.status(200);
    }



    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }


}