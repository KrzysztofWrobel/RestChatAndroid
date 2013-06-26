package com.example.RestChatAndroid.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 04:43
 * To change this template use File | Settings | File Templates.
 */
public class Chatroom {
    private String name;
    private int connectedClients;
    private List<ChatNode> chatroomNodes;

    public Chatroom(String name) {
        this.name = name;
        this.connectedClients = 0;
        this.chatroomNodes = new ArrayList<ChatNode>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConnectedClients() {
        return connectedClients;
    }

    public void setConnectedClients(int connectedClients) {
        this.connectedClients = connectedClients;
    }

    public List<ChatNode> getChatroomNodes() {
        return chatroomNodes;
    }

    public void setChatroomNodes(List<ChatNode> chatroomNodes) {
        this.chatroomNodes = chatroomNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chatroom chatroom = (Chatroom) o;

        if (!name.equals(chatroom.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void changeConnectedClients(int i) {
        connectedClients+=i;
    }
}
