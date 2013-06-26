package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.model.Chatroom;
import com.example.RestChatAndroid.rest.interfaces.ChatroomsListResourceInterface;
import com.example.RestChatAndroid.utility.ChatroomManager;
import com.example.RestChatAndroid.utility.RouterUtility;
import com.google.gson.Gson;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 25.06.13
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomsListResource extends ServerResource implements ChatroomsListResourceInterface {

    private ChatroomManager chatroomManager;
    private Gson gson;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();    //To change body of overridden methods use File | Settings | File Templates.
        chatroomManager = ChatroomManager.getInstance();
        gson = new Gson();
    }

    @Override
    @Get
    public String getAvailableChatroomsList(){
        Collection<Chatroom> nodes = chatroomManager.getAvailableChatroomList();

        return gson.toJson(nodes);
    }
}
