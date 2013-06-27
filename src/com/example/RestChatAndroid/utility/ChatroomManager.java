package com.example.RestChatAndroid.utility;

import com.example.RestChatAndroid.interfaces.OnInvitationAprovalRequest;
import com.example.RestChatAndroid.interfaces.OnInvitationApprovedInterface;
import com.example.RestChatAndroid.model.Chatroom;
import com.example.RestChatAndroid.model.ChatroomMessage;
import com.example.RestChatAndroid.views.ChatroomAdapter;

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
    private ChatroomAdapter chatroomAdapter;
    private OnInvitationApprovedInterface onInvitationApprovedInterface;
    private OnInvitationAprovalRequest onInvitationAprovalRequest;

    public ChatroomManager() {
        availableChatroomList = new ArrayList<Chatroom>();
    }

    public static ChatroomManager getInstance() {
        if (instance == null)
            instance = new ChatroomManager();

        return instance;
    }

    public ChatroomAdapter getChatroomAdapter() {
        return chatroomAdapter;
    }

    public void setChatroomAdapter(ChatroomAdapter chatroomAdapter) {
        this.chatroomAdapter = chatroomAdapter;
    }

    public void addChatroom(Chatroom chatroom){
        if(!availableChatroomList.contains(chatroom)){
            availableChatroomList.add(chatroom);
            //TODO trzeba wywołać w main wątku
            chatroomAdapter.notifyDataSetChanged();
        }
    }

    public boolean checkAndDeleteChatroom(String chatroomName) {
        Chatroom deletedChatroom = getChatroomByName(chatroomName);
        if (deletedChatroom != null && deletedChatroom.getConnectedClients() == 0) {
            availableChatroomList.remove(deletedChatroom);
            chatroomAdapter.notifyDataSetChanged();
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
        waitingForApproval = true;
        this.currentChatroom = currentChatroom;
    }

    public boolean isWaitingForApproval() {
        return waitingForApproval;
    }

    public void setWaitingForApproval(boolean waitingForApproval) {
        this.waitingForApproval = waitingForApproval;
    }

    public OnInvitationApprovedInterface getOnInvitationApprovedInterface() {
        return onInvitationApprovedInterface;
    }

    public void setOnInvitationApprovedInterface(OnInvitationApprovedInterface onInvitationApprovedInterface) {
        this.onInvitationApprovedInterface = onInvitationApprovedInterface;
    }

    public OnInvitationAprovalRequest getOnInvitationAprovalRequest() {
        return onInvitationAprovalRequest;
    }

    public void setOnInvitationAprovalRequest(OnInvitationAprovalRequest onInvitationAprovalRequest) {
        this.onInvitationAprovalRequest = onInvitationAprovalRequest;
    }

    public void newChatroomApproval(ChatroomMessage aprrovalMessage){
         onInvitationAprovalRequest.newUserRequest(aprrovalMessage);
    }

    public void invitationApproved(){
        onInvitationApprovedInterface.invitationApproved();
    }
}
