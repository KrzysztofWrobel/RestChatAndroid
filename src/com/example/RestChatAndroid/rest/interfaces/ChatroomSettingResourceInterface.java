package com.example.RestChatAndroid.rest.interfaces;

import org.restlet.resource.Post;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 04:35
 * To change this template use File | Settings | File Templates.
 */
public interface ChatroomSettingResourceInterface {
    @Post
    String updateChatroomState(String representation);
}
