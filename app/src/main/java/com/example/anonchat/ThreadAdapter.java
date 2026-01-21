package com.example.anonchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ThreadAdapter extends FirestoreRecyclerAdapter<ChatThread, ThreadAdapter.ThreadViewHolder> {

    private final OnThreadClickListener listener;

    public ThreadAdapter(@NonNull FirestoreRecyclerOptions<ChatThread> options, OnThreadClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ThreadViewHolder holder, int position, @NonNull ChatThread model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ThreadViewHolder(view);
    }

    class ThreadViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ThreadViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    // Pass the document ID (which is our threadId) to the listener
                    listener.onThreadClick(getSnapshots().getSnapshot(position).getId());
                }
            });
        }

        void bind(ChatThread thread) {
            textView.setText(thread.getInitialMessage());
        }
    }

    public interface OnThreadClickListener {
        void onThreadClick(String threadId);
    }
}
