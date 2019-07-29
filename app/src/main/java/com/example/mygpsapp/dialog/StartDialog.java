package com.example.mygpsapp.dialog;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mygpsapp.R;

public class StartDialog extends AppCompatDialogFragment {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private StartDialogListener listener;
    private TextView titleView;
    private static Context mContext;
    Typeface myriad;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.StartDialog);
        mContext = getContext();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        setCancelable(false);
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setCancelable(false)
                .setCustomTitle(titleView)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();
                        listener.applyTexts(username, password);
                    }
                });


        myriad=Typeface.createFromAsset(getAppContext().getAssets(), "fonts/Myriad_Pro_Condensed.ttf");


//        titleView.setText("Title");
//        titleView.setGravity(Gravity.CENTER);
//        titleView.setPadding(20, 20, 20, 20);
//        titleView.setTextSize(20F);
//        titleView.setTypeface(Typeface.DEFAULT_BOLD);
//        titleView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//        titleView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));

        titleView = view.findViewById(R.id.TitleTv);
        editTextUsername = view.findViewById(R.id.edit_username);
        editTextPassword = view.findViewById(R.id.edit_password);
        editTextUsername.setTypeface(myriad);
        editTextPassword.setTypeface(myriad);
        titleView.setTypeface(myriad);
        return builder.create();
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (StartDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface StartDialogListener {
        void applyTexts(String username, String password);
    }
}
