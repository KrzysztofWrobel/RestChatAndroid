package com.example.RestChatAndroid.utility;

import com.example.RestChatAndroid.model.Chatroom;
import org.restlet.ext.json.JsonRepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 04:36
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomManager {
    private static ChatroomManager instance;
    private List<Chatroom> availableChatroomList;
    private Chatroom currentChatroom;
    private boolean waitingForApproval = true;

    public ChatroomManager() {
        availableChatroomList = new ArrayList<Chatroom>();
    }

    public static ChatroomManager getInstance() {
        if (instance == null)
            instance = new ChatroomManager();

        return instance;
    }

    public void addChatroom(Chatroom chatroom){
        availableChatroomList.add(chatroom);
    }

    public boolean checkAndDeleteChatroom(String chatroomName) {
        Chatroom deletedChatroom = getChatroomByName(chatroomName);
        if (deletedChatroom != null && deletedChatroom.getConnectedClients() == 0) {
            availableChatroomList.remove(deletedChatroom);
            return true;
        } else {
            return false;
        }
    }

    private Chatroom getChatroomFromList(Chatroom chatroom) {
        return availableChatroomList.get(availableChatroomList.indexOf(chatroom));
    }

    public Chatroom getChatroomByName(String name){
        for (int i = 0; i < availableChatroomList.size(); i++) {
            Chatroom chatroom = availableChatroomList.get(i);
            if(chatroom.getName().equals(name))
                return chatroom;
        }
        return null;
    }

    public void addedOneUserToChatroom(Chatroom chatroom){
        Chatroom chatroomFromList = getChatroomFromList(chatroom);
        chatroomFromList.changeConnectedClients(1);
    }

    public void removeOneUserFromChatroom(Chatroom chatroom){
        Chatroom chatroomFromList = getChatroomFromList(chatroom);
        chatroomFromList.changeConnectedClients(-1);
    }

    public List<Chatroom> getAvailableChatroomList() {
        return availableChatroomList;
    }

    public void setAvailableChatroomList(List<Chatroom> availableChatroomList) {
        this.availableChatroomList = availableChatroomList;
    }

    public Chatroom getCurrentChatroom() {
        return currentChatroom;
    }

    public void setCurrentChatroom(Chatroom currentChatroom) {
        this.currentChatroom = currentChatroom;
    }

    public boolean isWaitingForApproval() {
        return waitingForApproval;
    }

    public void setWaitingForApproval(boolean waitingForApproval) {
        this.waitingForApproval = waitingForApproval;
    }
}
