package com.example.RestChatAndroid.interfaces;

import com.example.RestChatAndroid.model.ChatroomMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 27.06.13
 * Time: 06:01
 * To change this template use File | Settings | File Templates.
 */
public interface OnInvitationAprovalRequest {
    public void newUserRequest(ChatroomMessage message);
}
