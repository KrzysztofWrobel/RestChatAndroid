package com.example.RestChatAndroid.rest;

import com.example.RestChatAndroid.rest.*;
import org.restlet.*;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public class MainRestComponent extends Component {

    public MainRestComponent(int port) {
        super();
        this.getServers().add(Protocol.HTTP, port);

        // Create a root router
        Router router = new Router(getContext());

        // Attach the handlers to the root router
        router.attach("/settings/{order}", ConnectionSettingsResource.class);
        router.attach("/chatrooms", ChatroomsListResource.class);
        router.attach("/chatrooms/{name}", ChatroomInfoResource.class);
        router.attach("/chatrooms/{name}/{order}", ChatroomSettingResource.class);
        router.attach("/message", MessageResource.class);

        // Then attach it to the local host
//        component.getDefaultHost().attach("/trace", FirstServerResource.class);
        this.getDefaultHost().attach(router);

    }

}
