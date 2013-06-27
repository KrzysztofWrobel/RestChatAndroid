package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.model.ChatroomMessage;
import com.example.RestChatAndroid.rest.interfaces.MessageResourceInterface;
import com.example.RestChatAndroid.utility.BroadcastManager;
import com.example.RestChatAndroid.utility.ChatroomManager;
import com.example.RestChatAndroid.utility.MessageManager;
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
    private MessageManager messageManager;
    private Gson gson;


    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        broadcastManager = BroadcastManager.getInstance();
        chatroomManager = ChatroomManager.getInstance();
        messageManager = MessageManager.getInstance();
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
            messageManager.addMessage(message);
        }

        broadcastManager.broadcastUnreliableChatroomMessage(message);
        setStatus(Status.SUCCESS_OK);

        return null;
    }

}
