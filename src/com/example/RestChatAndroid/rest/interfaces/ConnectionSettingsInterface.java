package com.example.RestChatAndroid.rest.interfaces;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 01:00
 * To change this template use File | Settings | File Templates.
 */
public interface ConnectionSettingsInterface {

    @Post
    String updateConnectionState(String representation);
}
