package com.example.RestChatAndroid.utility;

import com.example.RestChatAndroid.ChatroomListInterface;
import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.model.Chatroom;
import com.example.RestChatAndroid.model.ChatroomMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
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
    private static BroadcastManager instance;
    private RouterUtility routerUtility;
    private ChatroomManager chatroomManager;
    private ChatroomListInterface chatroomListInterface;
    Gson gson;

    //TODO koniecznie THreadExecutor

    public BroadcastManager() {
        gson = new Gson();
        routerUtility = RouterUtility.getInstance();
        chatroomManager = ChatroomManager.getInstance();
    }

    public static BroadcastManager getInstance() {
        if(instance == null)
            instance = new BroadcastManager();

        return instance;
    }

    public void initChatroomList() {
        new Thread() {
            @Override
            public void run() {
                List<ChatNode> nodes = routerUtility.getConnectedNodes();
                ClientResource clientResource = new ClientResource("http://" + nodes.get(0).getIpAddress() + ":8182/chatrooms");
                Representation representation = clientResource.get();
                String json = null;
                try {
                    json = representation.getText();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                Type collectionType = new TypeToken<Collection<Chatroom>>() {
                }.getType();
                Collection<Chatroom> collection = null;
                try {
                    collection = (Collection<Chatroom>) gson.fromJson(json, collectionType);
                } catch (JsonSyntaxException ex) {
                    ex.printStackTrace();
                }

                if(collection!=null){
                    chatroomManager.getAvailableChatroomList().addAll(collection);
                    roomsChangedFromRequest();
                }
            }
        }.start();

    }

    public void roomsChangedFromRequest() {
        chatroomListInterface.roomsChanged();
    }

    public ChatroomMessage sendSimpleMessage(String message) {
        ChatroomMessage chatroomMessage = new ChatroomMessage();
        ChatNode myNode = routerUtility.getMyNode();
        chatroomMessage.setOwner(myNode);
        chatroomMessage.setChatroomName(chatroomManager.getCurrentChatroom().getName());
        chatroomMessage.setUrlPath("/message");
        chatroomMessage.setMessage(message);
        //TODO setTimeStamp

        broadcastUnreliableChatroomMessage(chatroomMessage);

        return chatroomMessage;
    }

    public void sendAddRoomMessage(String name) {
        Chatroom temp = new Chatroom(name);
        if (!chatroomManager.getAvailableChatroomList().contains(temp)) {
            chatroomListInterface.wasRoomAdded(true);
            chatroomManager.addChatroom(temp);
            roomsChangedFromRequest();

            ChatroomMessage addChatroomMessage = new ChatroomMessage();
            ChatNode myNode = routerUtility.getMyNode();
            addChatroomMessage.setOwner(myNode);
            addChatroomMessage.setChatroomName(name);
            addChatroomMessage.setUrlPath("/chatrooms/" + name + "/add");
            addChatroomMessage.setMessage("");


            broadcastUnreliableChatroomMessage(addChatroomMessage);
        }
        else {
            chatroomListInterface.wasRoomAdded(false);
        }
    }

    public boolean broadcastUnreliableChatroomMessage(ChatroomMessage message) {
        List<ChatNode> connectedNodes = routerUtility.getConnectedNodes();
        HashSet<String> visitedIpAddresses = message.getVisitedIpAddresses();
        String myIpAddress = routerUtility.getMyNode().getIpAddress();
        if (!visitedIpAddresses.contains(myIpAddress)) {
            visitedIpAddresses.add(myIpAddress);
            for (int i = 0; i < connectedNodes.size(); i++) {
                ChatNode connectedNode = connectedNodes.get(i);
                if (!visitedIpAddresses.contains(connectedNode.getIpAddress())) {
                    sendUnReliableChatMessageTo(message, connectedNode);
                }
            }
            return true;
        }
        else
            return false;
    }

    public void broadcastReliableChatroomMessage(ChatroomMessage message) {
        List<ChatNode> connectedNodes = routerUtility.getConnectedNodes();
        HashSet<String> visitedIpAddresses = message.getVisitedIpAddresses();
        String myIpAddress = routerUtility.getMyNode().getIpAddress();
        if (!visitedIpAddresses.contains(myIpAddress)) {
            visitedIpAddresses.add(myIpAddress);
            for (int i = 0; i < connectedNodes.size(); i++) {
                ChatNode connectedNode = connectedNodes.get(i);
                if (!visitedIpAddresses.contains(connectedNode.getIpAddress())) {
                    sendReliableChatMessageTo(message, connectedNode);
                }
            }
        }
    }

    private void sendReliableChatMessageTo(final ChatroomMessage message, final ChatNode connectedNode) {

        try {
            ClientResource clientResource = new ClientResource(getUri(message, connectedNode));
            clientResource.setOnResponse(new Uniform() {
                @Override
                public void handle(Request request, Response response) {
                    if (response.getStatus().isSuccess())
                        chatroomManager.addChatroom(new Chatroom(message.getChatroomName()));
                }
            });
            clientResource.post(gson.toJson(message), MediaType.APPLICATION_JSON);
        } catch (Exception e) {
            //DO nothing better to avoid timeout exceptions
        }
    }


    private void sendUnReliableChatMessageTo(final ChatroomMessage message, final ChatNode connectedNode) {

        new Thread() {
            @Override
            public void run() {
                try {
                    ClientResource clientResource = new ClientResource(getUri(message, connectedNode));
                    clientResource.post(gson.toJson(message), MediaType.APPLICATION_JSON);
                } catch (Exception e) {
                    //DO nothing better to avoid timeout exceptions
                }
            }
        }.start();

        //TODO chyba lepiej byłoby zrobić jakiś watchdog to sprawdzania tych Threadów
    }

    private String getUri(ChatroomMessage message, ChatNode connectedNode) {
        return "http://" + connectedNode.getIpAddress() + ":8182" + message.getUrlPath();
    }

    public void setChatroomListInterface(ChatroomListInterface chatroomListInterface) {
        this.chatroomListInterface = chatroomListInterface;
    }

    public void setChatroomAprovalRequest() {
        //To change body of created methods use File | Settings | File Templates.
    }
}
