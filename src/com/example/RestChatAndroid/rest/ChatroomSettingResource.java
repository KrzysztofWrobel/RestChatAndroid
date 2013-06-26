package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.model.Chatroom;
import com.example.RestChatAndroid.model.ChatroomMessage;
import com.example.RestChatAndroid.rest.interfaces.ChatroomSettingResourceInterface;
import com.example.RestChatAndroid.utility.BroadcastManager;
import com.example.RestChatAndroid.utility.ChatroomManager;
import com.example.RestChatAndroid.utility.GuiManager;
import com.example.RestChatAndroid.utility.RouterUtility;
import com.google.gson.Gson;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Route;

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
    private BroadcastManager broadcastManager;
    private RouterUtility routerUtility;
    private GuiManager guiManager;


    @Override
    protected void doInit() throws ResourceException {
        super.doInit();    //To change body of overridden methods use File | Settings | File Templates.
        gson = new Gson();
        chatroomManager = ChatroomManager.getInstance();
        routerUtility = RouterUtility.getInstance();
        broadcastManager = new BroadcastManager();
    }

    @Override
    @Post
    public String updateChatroomState(String representation) {
        String orderType = (String) getRequest().getAttributes().get("order");
        String chatroomName = (String) getRequest().getAttributes().get("name");

        //TODO check you have this client on connected List
        ChatroomMessage message = gson.fromJson(representation,ChatroomMessage.class);
        if(orderType.equals("add")) {

            Chatroom newChatroom = chatroomManager.getChatroomByName(chatroomName);

            if(newChatroom==null){
                newChatroom = new Chatroom(chatroomName);
                chatroomManager.addChatroom(newChatroom);
                broadcastManager.broadcastChatroomMessage(message);
                setStatus(Status.SUCCESS_OK);
            }
            else {
                setStatus(Status.CLIENT_ERROR_CONFLICT);
            }
        }
        else if(orderType.equals("delete")) {

            boolean deleted = chatroomManager.checkAndDeleteChatroom(chatroomName);
            if(deleted){
                broadcastManager.broadcastChatroomMessage(message);
                setStatus(Status.SUCCESS_OK);
            }
            else {
                setStatus(Status.CLIENT_ERROR_CONFLICT);
            }
        }
        else if(orderType.equals("connect")) {

            if(message.getChatroomName().equals(chatroomManager.getCurrentChatroom().getName()))
                guiManager.showUserConnectionApprovalMessage(message);

            broadcastManager.broadcastChatroomMessage(message);
            setStatus(Status.SUCCESS_OK);
        }
        else if(orderType.equals("connected")) {
            if(chatroomManager.isWaitingForApproval()){
                if(message.getOwner().equals(routerUtility.getMyNode())){
                    chatroomManager.setWaitingForApproval(false);
                    guiManager.connectionApproved();
                }
            }
            else {
                chatroomManager.addedOneUserToChatroom(chatroomManager.getChatroomByName(message.getChatroomName()));
            }

            broadcastManager.broadcastChatroomMessage(message);
            setStatus(Status.SUCCESS_OK);
        }
        else if(orderType.equals("disconnect")){
            chatroomManager.removeOneUserFromChatroom(chatroomManager.getChatroomByName(message.getChatroomName()));

            broadcastManager.broadcastChatroomMessage(message);
            setStatus(Status.SUCCESS_OK);

        }
        else if(orderType.equals("disconnected")){
            chatroomManager.removeOneUserFromChatroom(chatroomManager.getChatroomByName(message.getChatroomName()));

            setStatus(Status.SUCCESS_OK);
        }
        else {
            setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
        }

        return null;
    }

}
