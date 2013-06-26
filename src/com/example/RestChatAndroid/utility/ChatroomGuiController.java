package com.example.RestChatAndroid.utility;

import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.model.Chatroom;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
public interface ChatroomGuiController {
    public void showRequestOfApproval(Chatroom chatroom, ChatNode chatNode);
}
