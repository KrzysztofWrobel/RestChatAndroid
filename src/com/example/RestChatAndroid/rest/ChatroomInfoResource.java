package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.rest.interfaces.ChatroomInfoResourceInterface;
import com.example.RestChatAndroid.utility.ChatroomManager;
import com.google.gson.Gson;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 25.06.13
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomInfoResource extends ServerResource implements ChatroomInfoResourceInterface {
    private ChatroomManager chatroomManager;
    private Gson gson;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        chatroomManager = ChatroomManager.getInstance();
        gson = new Gson();
    }

    @Override
    @Get
    public String getChatroomInfo(){
        String chatroomName = (String) getRequest().getAttributes().get("name");

        return gson.toJson(chatroomManager.getChatroomByName(chatroomName));
    }
}
