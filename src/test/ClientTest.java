package test;

import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.rest.interfaces.ConnectionSettingsInterface;
import com.google.gson.Gson;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 03:25
 * To change this template use File | Settings | File Templates.
 */
public class ClientTest {
    public static void main(String... args){
        // Create the client resource
        ClientResource resource = new ClientResource("http://192.168.1.24:8182/settings/connect");

        Gson gson = new Gson();
        String json = gson.toJson(new ChatNode("asd","Asd"));
        Representation rep = new StringRepresentation(json,MediaType.APPLICATION_JSON);
//        resource.wrap(ConnectionSettingsInterface.class).updateConnectionState(rep.toString());
//        resource.get(MediaType.APPLICATION_JSON);
        resource.post(json,MediaType.APPLICATION_JSON);
    }
}
