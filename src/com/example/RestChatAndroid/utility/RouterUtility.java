package com.example.RestChatAndroid.utility;

import com.example.RestChatAndroid.model.ChatNode;

import java.util.ArrayList;
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
    private List<ChatNode> connectedNodes;
    private List<ChatNode> knownNodes;
    private int counter = 0;

    public RouterUtility(){
        connectedNodes = new ArrayList<ChatNode>();
        knownNodes = new ArrayList<ChatNode>();
    }

    public static RouterUtility getInstance(){
        if(instance == null){
            instance = new RouterUtility();
        }
        return instance;
    }

    public boolean checkAndAddConnectedNode(ChatNode newNode){
        addKnownNode(newNode);
        if(counter<MAX_CONNECTED_NODES){
            if(!connectedNodes.contains(newNode)) {
                connectedNodes.add(newNode);
                counter++;
            }
            return true;
        }
        else {

            return false;
        }
    }

    public void addKnownNode(ChatNode newNode){
        if(!knownNodes.contains(newNode))
            knownNodes.add(newNode);
    }

    public boolean removeConnectedNode(ChatNode connectedNode){
        removeKnownNode(connectedNode);
        if(connectedNodes.remove(connectedNode)){
            counter--;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean removeKnownNode(ChatNode knownNode){
        return knownNodes.remove(knownNode);
    }

    public List<ChatNode> getConnectedNodes() {
        return connectedNodes;
    }

    public List<ChatNode> getKnownNodes() {
        return knownNodes;
    }


}
