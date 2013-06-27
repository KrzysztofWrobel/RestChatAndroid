package com.example.RestChatAndroid.views.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.RestChatAndroid.R;
import com.example.RestChatAndroid.model.ChatroomMessage;
import com.example.RestChatAndroid.utility.BroadcastManager;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 27.06.13
 * Time: 06:02
 * To change this template use File | Settings | File Templates.
 */
public class InvitationDialog extends DialogFragment {
    private final ChatroomMessage message;
    private Button acceptButton;
    private BroadcastManager broadcastManager;

    public InvitationDialog(ChatroomMessage message) {
        super();    //To change body of overridden methods use File | Settings | File Templates.
        broadcastManager = BroadcastManager.getInstance();
        this.message = message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invitation_dialog, container);

        TextView newUserTextView = (TextView) view.findViewById(R.id.tv_user_info_request);
        newUserTextView.setText("New user: "+message.getOwner().getUserName());
        acceptButton = (Button) view.findViewById(R.id.b_accept_user);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                broadcastManager.sendChatConfirmationMessage(message.getOwner());
                dismiss();
            }
        });

        view.findViewById(R.id.b_decline_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getDialog().setTitle("Incoming request");
        getDialog().setCanceledOnTouchOutside(true);

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }
}
