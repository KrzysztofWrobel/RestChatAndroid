package com.example.RestChatAndroid.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.RestChatAndroid.R;
import com.example.RestChatAndroid.model.Chatroom;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */
public class ChatroomAdapter extends ArrayAdapter<Chatroom> {
     private List<Chatroom> chatrooms;

    public ChatroomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public List<Chatroom> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(List<Chatroom> chatrooms) {
        this.chatrooms = chatrooms;
    }

    @Override
    public int getCount() {
        return chatrooms.size();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Chatroom getItem(int position) {
        return chatrooms.get(position);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatroom_list_item_view,
                    parent, false);

        }

        TextView chatNameTextView = (TextView) convertView.findViewById(R.id.tv_chatname);
        chatNameTextView.setText(chatrooms.get(position).getName());

        return convertView;    //To change body of overridden methods use File | Settings | File Templates.
    }


}
