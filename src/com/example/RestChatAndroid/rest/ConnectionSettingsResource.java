package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.rest.interfaces.ConnectionSettingsInterface;
import com.example.RestChatAndroid.utility.RouterUtility;
import com.google.gson.Gson;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 00:26
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionSettingsResource extends ServerResource implements ConnectionSettingsInterface {
    private RouterUtility routerUtility;
    private Gson gson;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();
        routerUtility = RouterUtility.getInstance();
        gson = new Gson();
    }

    @Override
    @Post
    public String updateConnectionState(String representation){
        String orderType = (String) getRequest().getAttributes().get("order");
        if(orderType.equals("connect")) {
            ChatNode newNode = gson.fromJson(representation,ChatNode.class);
            boolean connected = routerUtility.checkAndAddConnectedNode(newNode);
            if(connected){
                if(routerUtility.getConnectedNodes().size()==1)
                    routerUtility.getConnectedToNodeInterface().connectedToFirstNode();
                setStatus(Status.SUCCESS_OK);
            }
            else {
                setStatus(Status.SERVER_ERROR_INSUFFICIENT_STORAGE);
            }
            return gson.toJson(routerUtility.getKnownNodes());
        }
        else if(orderType.equals("disconnect")) {
            ChatNode deleteNode = gson.fromJson(representation,ChatNode.class);
            routerUtility.removeConnectedNode(deleteNode);
            setStatus(Status.SUCCESS_OK);
        }
        else if(orderType.equals("beacon")) {
            setStatus(Status.SUCCESS_OK);
        }
        else {
            setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
        }

        return null;

    }
}
