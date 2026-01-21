package com.example.anonchat;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class ChatThread {
    private String threadId;
    private String initialMessage;
    private String creatorId;
    private Date timestamp;

    // IMPORTANT: A no-argument constructor is required for Firestore deserialization
    public ChatThread() {}

    public ChatThread(String initialMessage, String creatorId) {
        this.initialMessage = initialMessage;
        this.creatorId = creatorId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getInitialMessage() {
        return initialMessage;
    }

    public String getCreatorId() {
        return creatorId;
    }

    @ServerTimestamp // This annotation tells Firestore to automatically set the server time
    public Date getTimestamp() {
        return timestamp;
    }
}
