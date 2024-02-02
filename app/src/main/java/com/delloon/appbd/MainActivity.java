package com.delloon.appbd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



public class MainActivity extends AppCompatActivity {

    private static final int SIGN_IN_CODE = 1;
    private Button submitButton;
    private FirebaseListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout activity_main = findViewById(R.id.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    SIGN_IN_CODE
            );
        } else {
            Snackbar.make(activity_main, "Вы авторизованы", Snackbar.LENGTH_SHORT).show();
            displayAllMessages();
        }
        submitButton = findViewById(R.id.btnSend);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textField = findViewById(R.id.messageField);
                if(textField.getText().toString() == "")
                    return;
                FirebaseDatabase.getInstance().getReference().push().setValue(new Message(
                        FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                        textField.getText().toString()
                ));
                textField.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        RelativeLayout activity_main = findViewById(R.id.activity_main);

        if (requestCode == SIGN_IN_CODE) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(activity_main, "Вы авторизованы", Snackbar.LENGTH_SHORT).show();
                displayAllMessages();
            } else {
                Snackbar.make(activity_main, "Вы не авторизованы",
                        Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void displayAllMessages() {
        ListView listOfMessages = findViewById(R.id.text_layout);
        Query query = FirebaseDatabase.getInstance().getReference().child("messages");
        FirebaseListOptions<Message> options =
                new FirebaseListOptions.Builder<Message>()
                        .setQuery(query, Message.class)
                        .setLayout(R.layout.list_item)
                        .build();
        adapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
                TextView mess_user, mess_time, mess_txt;
                mess_user = v.findViewById(R.id.message_user);
                mess_time = v.findViewById(R.id.message_time);
                mess_txt = v.findViewById(R.id.message_text);
                mess_user.setText(model.getUserName());
                mess_time.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss",
                        model.getMessageTime()));
                mess_txt.setText(model.getTextMessage());
            }
        };
        listOfMessages.setAdapter(adapter);
        adapter.startListening();
        }
    }

