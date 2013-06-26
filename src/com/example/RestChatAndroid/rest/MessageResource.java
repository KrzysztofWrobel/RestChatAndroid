package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.model.ChatroomMessage;
import com.example.RestChatAndroid.rest.interfaces.MessageResourceInterface;
import com.example.RestChatAndroid.utility.BroadcastManager;
import com.example.RestChatAndroid.utility.ChatroomManager;
import com.example.RestChatAndroid.utility.GuiManager;
import com.google.gson.Gson;
import org.restlet.data.Status;
import org.restlet.resource.*;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 00:49
 * To change this template use File | Settings | File Templates.
 */
public class MessageResource extends ServerResource implements MessageResourceInterface {
    private BroadcastManager broadcastManager;
    private ChatroomManager chatroomManager;
    private GuiManager guiManager;
    private Gson gson;


    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        broadcastManager = new BroadcastManager();
        chatroomManager = ChatroomManager.getInstance();
        gson = new Gson();
    }

    @Override
    @Get
    public String returnBufferedMessageList(){
        //TODO return list of buffered messages to new client
        return null;
    }

    @Override
    @Post
    public String analizeAndResend(String representation){

        ChatroomMessage message = gson.fromJson(representation,ChatroomMessage.class);
        if(chatroomManager.getCurrentChatroom().getName().equals(message.getChatroomName())){
            //TODO sprawdzenie w MessageManager czy nie mamy tej wiadomości
            guiManager.handleNewChatMessage(message);
        }

        broadcastManager.broadcastChatroomMessage(message);
        setStatus(Status.SUCCESS_OK);

        return null;
    }

}
