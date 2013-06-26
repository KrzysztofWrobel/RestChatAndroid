package com.example.RestChatAndroid.utility;

import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.model.ChatroomMessage;
import com.google.gson.Gson;
import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;

import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public class BroadcastManager {
    private RouterUtility routerUtility;
    Gson gson;

    public BroadcastManager() {
        gson = new Gson();
        routerUtility = RouterUtility.getInstance();
    }

    public void broadcastChatroomMessage(ChatroomMessage message){
         List<ChatNode> connectedNodes = routerUtility.getConnectedNodes();
        HashSet<String> visitedIpAddresses = message.getVisitedIpAddresses();
        String myIpAddress = routerUtility.getMyNode().getIpAddress();
        if(!visitedIpAddresses.contains(myIpAddress)){
            visitedIpAddresses.add(myIpAddress);
            for (int i = 0; i < connectedNodes.size(); i++) {
                ChatNode connectedNode = connectedNodes.get(i);
                if(!visitedIpAddresses.contains(connectedNode.getIpAddress())){
                    sendMessageTo(message,connectedNode);
                }
            }
        }
    }

    private void sendMessageTo(final ChatroomMessage message, final ChatNode connectedNode) {

        new Thread(){
            @Override
            public void run() {
                try{
                    ClientResource clientResource = new ClientResource(getUri(message, connectedNode));
                    clientResource.post(gson.toJson(message), MediaType.APPLICATION_JSON);
                } catch (Exception e){
                    //DO nothing better to avoid timeout exceptions
                }
            }
        }.start();

        //TODO chyba lepiej byłoby zrobić jakiś watchdog to sprawdzania tych Threadów
    }

    private String getUri(ChatroomMessage message, ChatNode connectedNode) {
        return "http://"+connectedNode.getIpAddress()+":8182/"+message.getUrlPath();
    }

}
