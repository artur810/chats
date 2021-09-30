package com.example.chats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterChat extends RecyclerView.Adapter<RecyclerViewAdapterChat.ViewHolder> {

    private List<Message> list;
    private LayoutInflater mInflater;

    RecyclerViewAdapterChat(Context context, List<Message> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.message, parent, false);

        return new ViewHolder(view);
    }

    @Override @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message chat = list.get(position);

        holder.text.setText(chat.getText());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
        }
    }

}