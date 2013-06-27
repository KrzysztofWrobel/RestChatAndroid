package com.example.RestChatAndroid.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 00:30
 * To change this template use File | Settings | File Templates.
 */
public class ChatNode {
    private String ipAddress;
    private String userName;
    private Date lastMessageTime;

    public ChatNode(String ipAddress, String userName){
        this.ipAddress = ipAddress;
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatNode chatNode = (ChatNode) o;

        if (!ipAddress.equals(chatNode.ipAddress)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ipAddress.hashCode();
    }
}
