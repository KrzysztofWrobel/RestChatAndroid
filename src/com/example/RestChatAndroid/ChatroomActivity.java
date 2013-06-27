package com.example.RestChatAndroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.RestChatAndroid.model.ChatroomMessage;
import com.example.RestChatAndroid.utility.BroadcastManager;
import com.example.RestChatAndroid.utility.MessageManager;
import com.example.RestChatAndroid.views.ChatmessageAdapter;


/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 27.06.13
 * Time: 04:52
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomActivity extends Activity implements OnNewMessageInterface {
    private BroadcastManager broadcastManager;
    private MessageManager messageManager;
    private ChatmessageAdapter chatmessageAdapter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.chatroom_view);
        broadcastManager = BroadcastManager.getInstance();
        mHandler = new Handler();

        chatmessageAdapter = new ChatmessageAdapter(this,R.layout.chatroom_item_view);
        messageManager = MessageManager.getInstance();
        messageManager.setOnNewMessageInterface(this);
        chatmessageAdapter.setMessages(messageManager.getMessages());

        ListView messagesView = (ListView) findViewById(R.id.lv_chatroom_messages);
        messagesView.setAdapter(chatmessageAdapter);

        final EditText messageEditText = (EditText) findViewById(R.id.et_messsage);

        Button sendButton = (Button) findViewById(R.id.b_send_message);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEditText.getText().toString();
                if(message!=null&&!message.equals("")){
                    messageEditText.setText("");
                    ChatroomMessage myMessage = broadcastManager.sendSimpleMessage(message);
                    messageManager.addMessage(myMessage);
                }
            }
        });

        //TODO disconnect and thema close
        Button disconnectButton = (Button) findViewById(R.id.b_disconnect_from_chat);

        Button closeButton = (Button) findViewById(R.id.b_close_chat);
    }

    @Override
    public void OnNewMessage() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                chatmessageAdapter.notifyDataSetChanged();
            }
        });
    }
}
