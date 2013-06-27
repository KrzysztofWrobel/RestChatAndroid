package com.example.RestChatAndroid;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.RestChatAndroid.model.ChatNode;
import com.example.RestChatAndroid.rest.MainRestComponent;
import com.example.RestChatAndroid.utility.BroadcastManager;
import com.example.RestChatAndroid.utility.RouterUtility;

public class ConnectActivity extends Activity implements ConnectedToNodeInterface {
    public static final int PORT = 8182;
    private RouterUtility routerUtility;
    private BroadcastManager broadcastManager;
    private MainRestComponent mainRestComponent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);

        routerUtility = RouterUtility.getInstance();
        broadcastManager = BroadcastManager.getInstance();
        ChatNode myNode = new ChatNode(ipAddress,"User Anonymous");
        routerUtility.setMyNode(myNode);
        routerUtility.setConnectedToNodeInterface(this);

        mainRestComponent = new MainRestComponent(PORT);
        try {
            mainRestComponent.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final EditText nodeIpAddressEditText = (EditText) findViewById(R.id.et_ip_address);
        TextView myIPTextView = (TextView) findViewById(R.id.tv_my_ip);
        myIPTextView.setText("My IP address id: "+ipAddress);

        Button connectToNodeButton = (Button) findViewById(R.id.b_connect_to_node);
        connectToNodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 routerUtility.connectToFirstNode(nodeIpAddressEditText.getText().toString());
            }
        });

    }


    @Override
    public void connectedToFirstNode() {
        Intent intent = new Intent(this,ChatroomListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        routerUtility.disconnectFromNodes();
        try {
            mainRestComponent.stop();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
