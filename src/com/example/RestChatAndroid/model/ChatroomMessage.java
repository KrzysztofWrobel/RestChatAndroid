package com.example.RestChatAndroid.model;

import java.util.Date;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 05:05
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomMessage {
    private ChatNode owner;
    private String chatroomName;
    private String message;
    private int timestamp;
    private HashSet<String> visitedIpAddresses;

    public ChatNode getOwner() {
        return owner;
    }

    public void setOwner(ChatNode owner) {
        this.owner = owner;
    }

    public String getChatroomName() {
        return chatroomName;
    }

    public void setChatroomName(String chatroomName) {
        this.chatroomName = chatroomName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public HashSet<String> getVisitedIpAddresses() {
        return visitedIpAddresses;
    }

    public void setVisitedIpAddresses(HashSet<String> visitedIpAddresses) {
        this.visitedIpAddresses = visitedIpAddresses;
    }

    //TODO methods of adding node ip, setting current timestamp, construcotrs
}
