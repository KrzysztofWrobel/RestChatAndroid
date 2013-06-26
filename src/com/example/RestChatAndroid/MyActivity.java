package com.example.RestChatAndroid;

import android.app.Activity;
import android.os.Bundle;
import com.example.RestChatAndroid.rest.MainRestComponent;

public class MyActivity extends Activity {
    public static final int PORT = 8182;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MainRestComponent mainRestComponent = new MainRestComponent(PORT);
        try {
            mainRestComponent.start();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }




}
