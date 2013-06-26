package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.model.Chatroom;
import com.example.RestChatAndroid.rest.interfaces.ChatroomSettingResourceInterface;
import com.example.RestChatAndroid.utility.ChatroomManager;
import com.google.gson.Gson;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 01:10
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomSettingResource extends ServerResource implements ChatroomSettingResourceInterface {
    private Gson gson;
    private ChatroomManager chatroomManager;


    @Override
    protected void doInit() throws ResourceException {
        super.doInit();    //To change body of overridden methods use File | Settings | File Templates.
        gson = new Gson();
        chatroomManager = ChatroomManager.getInstance();
    }

    @Override
    @Post
    public String updateChatroomState(String representation) {
        String orderType = (String) getRequest().getAttributes().get("order");
        String chatroomName = (String) getRequest().getAttributes().get("name");
        //TODO check you have this client on connected List
        if(orderType.equals("add")) {
            //TODO broadcast
            Chatroom newChatroom = chatroomManager.getChatroomByName(chatroomName);

            if(newChatroom==null){
                newChatroom = new Chatroom(chatroomName);
                chatroomManager.addChatroom(newChatroom);
                setStatus(Status.SUCCESS_OK);
            }
            else {
                setStatus(Status.CLIENT_ERROR_CONFLICT);
            }
        }
        else if(orderType.equals("delete")) {
            //TODO broadcast
            boolean deleted = chatroomManager.checkAndDeleteChatroom(chatroomName);
            if(deleted){
                setStatus(Status.SUCCESS_OK);
            }
            else {
                setStatus(Status.CLIENT_ERROR_CONFLICT);
            }
        }
        else if(orderType.equals("connect")) {
            //TODO check you have this room - ask for info
            //TODO when client node wants to connect - BroadCast
            setStatus(Status.SUCCESS_OK);
        }
        else if(orderType.equals("connected")) {
            //TODO check you have this room
            //TODO when smb accepted request of room connection - BroadCast
            // subscribe known node to chatroom
            setStatus(Status.SUCCESS_OK);
        }
        else if(orderType.equals("disconnect")){
            //TODO check you have this room - ask for info
            //TODO when client leaves the room - broadCast disconnect
            setStatus(Status.SUCCESS_OK);

        }
        else if(orderType.equals("disconnected")){
            //TODO check you have this room - ask for info
            //TODO when node discovers node is dead and previously was alive - broadCast
            setStatus(Status.SUCCESS_OK);
        }
        else {
            setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
        }

        return null;
    }

}
