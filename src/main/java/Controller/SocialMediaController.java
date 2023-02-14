package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        app.post("/message", this::postCreateNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getOneMessageGivenMessageIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageGivenMessageIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageGivenMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserGivenAccountIdHandler);

        app.start(8080);
        return app;
    }

    private void postUserRegistrationHandler(Context ctx) throws JsonProcessingException {}

    private void postLoginHandler(Context ctx) throws JsonProcessingException {}

    private void postCreateNewMessageHandler(Context ctx) throws JsonProcessingException {}

    private void getAllMessagesHandler(Context ctx){}
    
    private void getOneMessageGivenMessageIdHandler(Context ctx){}

    private void deleteMessageGivenMessageIdHandler(Context ctx){}

    private void patchMessageGivenMessageIdHandler(Context ctx){}

    private void getAllMessagesFromUserGivenAccountIdHandler(Context ctx){}



    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}