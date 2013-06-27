package com.example.RestChatAndroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.RestChatAndroid.utility.BroadcastManager;

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

    public ChatroomConfigDialogFragment() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
        broadcastManager = BroadcastManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatroom_config_dialog, container);
        mEditText = (EditText) view.findViewById(R.id.et_chat_name);
        getDialog().setTitle("Chatroom configuration");
        getDialog().setCanceledOnTouchOutside(true);

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        broadcastManager.sendAddRoomMessage(mEditText.getText().toString());

    }
}
