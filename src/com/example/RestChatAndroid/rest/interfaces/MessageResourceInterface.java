package com.example.RestChatAndroid.rest.interfaces;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 01:06
 * To change this template use File | Settings | File Templates.
 */
public interface MessageResourceInterface {
    @Get
    String returnBufferedMessageList();

    @Put
    void analizeAndResend(String representation);
}
