package com.example.RestChatAndroid;

import android.app.Application;
import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.utility.RouterUtility;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 24.06.13
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public class RestChatApplication extends Application{
    private RouterUtility routerUtility;

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
        routerUtility = new RouterUtility();
    }

}
