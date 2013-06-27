package com.example.RestChatAndroid.utility;

import com.example.RestChatAndroid.interfaces.OnNewMessageInterface;
import com.example.RestChatAndroid.model.ChatroomMessage;
import com.example.RestChatAndroid.views.ChatmessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 04:36
 * To change this template use File | Settings | File Templates.
 */
public class MessageManager {
    private List<ChatroomMessage> messages;
    private ChatmessageAdapter chatmessageAdapter;
    private OnNewMessageInterface onNewMessageInterface;

    private static MessageManager instance;

    public MessageManager() {
        messages = new ArrayList<ChatroomMessage>();
    }

    public static MessageManager getInstance(){
        if(instance==null)
            instance = new MessageManager();

        return instance;
    }

    public List<ChatroomMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatroomMessage> messages) {
        this.messages = messages;
    }

    public void setChatmessageAdapter(ChatmessageAdapter chatmessageAdapter) {
        this.chatmessageAdapter = chatmessageAdapter;
    }

    public void addMessage(ChatroomMessage message){
        if(!messages.contains(message)){
            messages.add(message);
            onNewMessageInterface.OnNewMessage();
        }
    }

    public void cleanList(){
        messages.clear();
    }

    public OnNewMessageInterface getOnNewMessageInterface() {
        return onNewMessageInterface;
    }

    public void setOnNewMessageInterface(OnNewMessageInterface onNewMessageInterface) {
        this.onNewMessageInterface = onNewMessageInterface;
    }
}
