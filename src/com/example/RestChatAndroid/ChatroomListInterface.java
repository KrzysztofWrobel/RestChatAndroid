package com.example.RestChatAndroid;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 27.06.13
 * Time: 03:23
 * To change this template use File | Settings | File Templates.
 */
public interface ChatroomListInterface {
    public void roomsChanged();
    public void wasRoomAdded(boolean added);
    public void wasRoomRemoved(boolean removed);

}
