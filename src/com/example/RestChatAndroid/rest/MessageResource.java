package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.rest.interfaces.MessageResourceInterface;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 00:49
 * To change this template use File | Settings | File Templates.
 */
public class MessageResource extends ServerResource implements MessageResourceInterface {

    //TODO check you have this client on connected list
    @Override
    @Get
    public String returnBufferedMessageList(){
        //TODO return list of buffered messages to new client
        return null;
    }

    @Override
    @Put
    public void analizeAndResend(String representation){
        //TODO broadcast message if its new -> add yours node info to visitedNodes
        setStatus(Status.SUCCESS_OK);
    }

}
