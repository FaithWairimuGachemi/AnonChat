package com.example.anonchat;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements ThreadAdapter.OnThreadClickListener {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private CollectionReference threadsRef;
    private ThreadAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        threadsRef = db.collection("threads");

        setupRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab_new_chat);
        fab.setOnClickListener(v -> createNewThreadDialog());
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.threads_recycler_view);
        Query query = threadsRef.orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatThread> options = new FirestoreRecyclerOptions.Builder<ChatThread>()
                .setQuery(query, ChatThread.class)
                .build();

        adapter = new ThreadAdapter(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void createNewThreadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Start a New Chat");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        input.setHint("What's on your mind?");
        builder.setView(input);

        builder.setPositiveButton("Post", (dialog, which) -> {
            String initialMessage = input.getText().toString().trim();
            if (!initialMessage.isEmpty()) {
                postNewThread(initialMessage);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void postNewThread(String message) {
        String userId = mAuth.getCurrentUser().getUid();
        ChatThread newThread = new ChatThread(message, userId);

        // Firestore will automatically generate a document ID
        threadsRef.add(newThread);
    }

    @Override
    public void onThreadClick(String threadId) {
        // This is where we will navigate to the ChatActivity
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("THREAD_ID", threadId);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
