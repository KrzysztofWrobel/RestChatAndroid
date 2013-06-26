package com.example.RestChatAndroid.rest.interfaces;

import org.restlet.resource.Get;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 04:21
 * To change this template use File | Settings | File Templates.
 */
public interface ChatroomsListResourceInterface {
    @Get
    String getAvailableChatroomsList();
}
