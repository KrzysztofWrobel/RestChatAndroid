package com.example.RestChatAndroid.views.dialogs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.RestChatAndroid.ChatroomActivity;
import com.example.RestChatAndroid.R;
import com.example.RestChatAndroid.model.Chatroom;
import com.example.RestChatAndroid.utility.BroadcastManager;
import com.example.RestChatAndroid.utility.ChatroomManager;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 27.06.13
 * Time: 03:34
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomConfigDialogFragment extends DialogFragment {

    private EditText mEditText;
    private BroadcastManager broadcastManager;
    private ChatroomManager chatroomManager;

    public ChatroomConfigDialogFragment() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
        broadcastManager = BroadcastManager.getInstance();
        chatroomManager = ChatroomManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatroom_config_dialog, container);
        mEditText = (EditText) view.findViewById(R.id.et_chat_name);
        getDialog().setTitle("Chatroom configuration");
        getDialog().setCanceledOnTouchOutside(true);

        view.findViewById(R.id.b_chat_name_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Chatroom myChatroom = broadcastManager.sendAddRoomMessage(mEditText.getText().toString());
        chatroomManager.setCurrentChatroom(myChatroom);
        Intent intent = new Intent(getActivity(), ChatroomActivity.class);
        startActivity(intent);

    }
}
