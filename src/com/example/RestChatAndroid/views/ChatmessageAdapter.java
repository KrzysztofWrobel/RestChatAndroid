package com.example.RestChatAndroid.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.RestChatAndroid.R;
import com.example.RestChatAndroid.model.ChatroomMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public class ChatmessageAdapter extends ArrayAdapter<ChatroomMessage>{
    private List<ChatroomMessage> messages;

    public ChatmessageAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public List<ChatroomMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatroomMessage> messages) {
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public ChatroomMessage getItem(int position) {
        return messages.get(position);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatroom_item_view,
                    parent, false);

        }

        TextView whoseTextView = (TextView) convertView.findViewById(R.id.tv_whose_message);
        whoseTextView.setText("From: "+messages.get(position).getOwner().getUserName());

        TextView whatTextView = (TextView) convertView.findViewById(R.id.tv_what_message);
        whatTextView.setText(messages.get(position).getMessage());

        return convertView;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
