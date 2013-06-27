package com.example.RestChatAndroid.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.RestChatAndroid.model.ChatroomMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 26.06.13
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public class ChatmessageAdapter extends ArrayAdapter<ChatroomMessage>{


    public ChatmessageAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return super.getCount();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public ChatroomMessage getItem(int position) {
        return super.getItem(position);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);    //To change body of overridden methods use File | Settings | File Templates.
    }
}