package com.example.RestChatAndroid.utility;

import com.example.RestChatAndroid.interfaces.ConnectedToNodeInterface;
import com.example.RestChatAndroid.model.ChatNode;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import static com.example.RestChatAndroid.utility.BroadcastManager.CHAT_PORT;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 00:29
 * To change this template use File | Settings | File Templates.
 */
public class RouterUtility {
    private static final int MAX_CONNECTED_NODES = 3;
    private static RouterUtility instance;
    private Gson gson;
    private List<ChatNode> connectedNodes;
    private List<ChatNode> knownNodes;
    private ChatNode myNode;
    private int counter = 0;
    private ConnectedToNodeInterface connectedToNodeInterface;

    public RouterUtility() {
        connectedNodes = new ArrayList<ChatNode>();
        knownNodes = new ArrayList<ChatNode>();
        gson = new Gson();
    }

    public static RouterUtility getInstance() {
        if (instance == null) {
            instance = new RouterUtility();
        }
        return instance;
    }

    public void connectToFirstNode(String ipAddress) {
        counter = 0;
        ClientResource clientResource = new ClientResource("http://" + ipAddress + ":"+CHAT_PORT+"/settings/connect");
        Representation representation = clientResource.post(gson.toJson(myNode), MediaType.APPLICATION_JSON);
        String responseJson = representation.toString();
        Type collectionType = new TypeToken<Collection<ChatNode>>() {
        }.getType();
        Collection<ChatNode> collection = null;
        try{
            collection = (Collection<ChatNode>) gson.fromJson(responseJson, collectionType);
        }
        catch (JsonSyntaxException ex){
            ex.printStackTrace();
        }
        if(collection != null)
            knownNodes.addAll(collection);

        if (clientResource.getResponse().getStatus().isSuccess()) {
            checkAndAddConnectedNode(new ChatNode(ipAddress, "User Anonymous"));
            connectedToNodeInterface.connectedToFirstNode();
        }

        if (knownNodes.size() > 0) {
            connectToNextNode(knownNodes.get(counter++).getIpAddress());
        }

    }


    public void connectToNextNode(String ipAddress) {
        ClientResource clientResource = new ClientResource("http://" + ipAddress + ":"+CHAT_PORT+"/settings/connect");
        Representation representation = clientResource.post(gson.toJson(myNode), MediaType.APPLICATION_JSON);
        String responseJson = representation.toString();

        if (clientResource.getResponse().getStatus().isSuccess()) {
            checkAndAddConnectedNode(new ChatNode(ipAddress, "User Anonymous"));
        }

        if (connectedNodes.size() < MAX_CONNECTED_NODES && counter < knownNodes.size()) {
            connectToNextNode(knownNodes.get(counter++).getIpAddress());
        }

    }

    public boolean checkAndAddConnectedNode(ChatNode newNode) {
        addKnownNode(newNode);
        if (connectedNodes.size() < MAX_CONNECTED_NODES) {
            if (!connectedNodes.contains(newNode)) {
                connectedNodes.add(newNode);
            }
            return true;
        } else {

            return false;
        }
    }

    public void addKnownNode(ChatNode newNode) {
        if (!knownNodes.contains(newNode))
            knownNodes.add(newNode);
    }

    public boolean removeConnectedNode(ChatNode connectedNode) {
        removeKnownNode(connectedNode);
        if (connectedNodes.remove(connectedNode)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean removeKnownNode(ChatNode knownNode) {
        return knownNodes.remove(knownNode);
    }

    public List<ChatNode> getConnectedNodes() {
        return connectedNodes;
    }

    public List<ChatNode> getKnownNodes() {
        return knownNodes;
    }

    public ChatNode getMyNode() {
        return myNode;
    }

    public void setMyNode(ChatNode myNode) {
        this.myNode = myNode;
    }

    public void setUserName(String userName){
        myNode.setUserName(userName);
    }

    public ConnectedToNodeInterface getConnectedToNodeInterface() {
        return connectedToNodeInterface;
    }

    public void setConnectedToNodeInterface(ConnectedToNodeInterface connectedToNodeInterface) {
        this.connectedToNodeInterface = connectedToNodeInterface;
    }

    public void disconnectFromNodes() {
        //TODO moze lepiej dać tu wyjątek
        if (connectedNodes.size() > 0) {
            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < connectedNodes.size(); i++) {

                        ChatNode node = connectedNodes.get(i);
                        String ipAddress = node.getIpAddress();

                        ClientResource clientResource = new ClientResource("http://" + ipAddress + ":"+CHAT_PORT+"/settings/disconnect");
                        Representation representation = clientResource.post(gson.toJson(myNode), MediaType.APPLICATION_JSON);
                    }
                }
            }.start();
        }


    }
}
