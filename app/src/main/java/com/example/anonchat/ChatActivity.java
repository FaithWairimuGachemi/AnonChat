package com.example.anonchat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Retrieve the thread ID passed from MainActivity
        String threadId = getIntent().getStringExtra("THREAD_ID");

        // TODO: Use the threadId to fetch and display messages for this chat
    }
}
