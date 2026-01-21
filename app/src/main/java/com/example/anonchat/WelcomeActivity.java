package com.example.anonchat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // Declare the Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout from activity_welcome.xml (or activity_chat.xml if you renamed it)
        setContentView(R.layout.activity_welcome);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Adjust padding for system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // This lambda is a modern way to handle screen insets (padding for system UI)
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        // Find the "Get Started" button
        Button getStartedButton = findViewById(R.id.get_started_button);

        // Set the action to perform when the button is clicked
        getStartedButton.setOnClickListener(v -> {
            // When clicked, start the anonymous sign-in process
            signInAnonymously();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is already signed in from a previous session
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // If they are, no need to show the welcome screen. Go to the main activity.
            navigateToMain();
        }
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // If sign-in is successful, navigate to the main activity
                        navigateToMain();
                    } else {
                        // If sign-in fails, show an error message to the user
                        Toast.makeText(WelcomeActivity.this, "Authentication failed. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToMain() {
        // Create an Intent to launch MainActivity
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        // Finish WelcomeActivity so the user cannot navigate back to it
        finish();
    }
}
