package com.example.RestChatAndroid.views.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.RestChatAndroid.R;
import com.example.RestChatAndroid.utility.BroadcastManager;
import com.example.RestChatAndroid.utility.RouterUtility;

/**
 * Created with IntelliJ IDEA.
 * User: Krzysztof
 * Date: 27.06.13
 * Time: 04:53
 * To change this template use File | Settings | File Templates.
 */
public class ChooseNameDialogFragment extends DialogFragment {
    private EditText mEditText;
    private RouterUtility routerUtility;

    public ChooseNameDialogFragment() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
        routerUtility = RouterUtility.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_name_dialog_fragment, container);
        mEditText = (EditText) view.findViewById(R.id.et_user_name);

        view.findViewById(R.id.b_user_name_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getDialog().setTitle("Choose your Name");
        getDialog().setCanceledOnTouchOutside(true);

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        routerUtility.setUserName(mEditText.getText().toString());

    }
}
