package com.example.RestChatAndroid;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.RestChatAndroid.interfaces.ChatroomListInterface;
import com.example.RestChatAndroid.interfaces.OnInvitationApprovedInterface;
import com.example.RestChatAndroid.model.Chatroom;
import com.example.RestChatAndroid.utility.BroadcastManager;
import com.example.RestChatAndroid.utility.ChatroomManager;
import com.example.RestChatAndroid.views.ChatroomAdapter;
import com.example.RestChatAndroid.views.dialogs.ChatroomConfigDialogFragment;


/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 27.06.13
 * Time: 00:39
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomListActivity extends FragmentActivity implements ChatroomListInterface, OnInvitationApprovedInterface {
    private BroadcastManager broadcastManager;
    private ChatroomManager chatroomManager;
    private ChatroomAdapter chatroomAdapter;
    private Handler mHandler;
    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom_list_view);
        mHandler = new Handler();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Oczekuje potwierdzenia");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                chatroomManager.setCurrentChatroom(null);
                chatroomManager.setWaitingForApproval(false);
            }
        });
        broadcastManager = BroadcastManager.getInstance();
        broadcastManager.setChatroomListInterface(this);
        broadcastManager.initChatroomList();
        chatroomManager = ChatroomManager.getInstance();
        chatroomManager.setOnInvitationApprovedInterface(this);

        ListView chatroomsListView = (ListView) findViewById(R.id.lv_chatrooms);
        chatroomAdapter = new ChatroomAdapter(this, R.layout.chatroom_list_item_view);

        chatroomAdapter.setChatrooms(chatroomManager.getAvailableChatroomList());
        chatroomManager.setChatroomAdapter(chatroomAdapter);
        chatroomsListView.setAdapter(chatroomAdapter);
        chatroomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chatroomManager.setCurrentChatroom((Chatroom) adapterView.getItemAtPosition(i));
                broadcastManager.sendChatApprovalMessage();
                progressDialog.show();
            }
        });


        Button addNewRoomButton = (Button) findViewById(R.id.b_add_new_chat);
        addNewRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatroomConfigDialogFragment dialogFragment = new ChatroomConfigDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "DialogFragment");

            }
        });

    }

    @Override
    public void roomsChanged() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                chatroomAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void wasRoomAdded(final boolean added) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (added)
                    Toast.makeText(ChatroomListActivity.this, "Utworzono pokój", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ChatroomListActivity.this, "Nie utworzono pokoju", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void wasRoomRemoved(boolean removed) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                chatroomAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void invitationApproved() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Intent intent = new Intent(ChatroomListActivity.this, ChatroomActivity.class);
                startActivity(intent);
            }
        });
    }
}