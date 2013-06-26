package com.example.RestChatAndroid.utility;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 04:36
 * To change this template use File | Settings | File Templates.
 */
public class MessageManager {
    private static MessageManager instance;

    public MessageManager() {
    }

    public static MessageManager getInstance(){
        if(instance==null)
            instance = new MessageManager();

        return instance;
    }
}
