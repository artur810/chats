package com.example.chats.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chats.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewChat extends RecyclerView.Adapter<RecyclerViewChat.RecyclerViewChatViewHolder>{

    private List<Message> list;
    private List<Message> fullList; //for search option
    private Context context;
    private onItemClickListener Listener;

    public interface onItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        Listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);

        RecyclerViewChatViewHolder rvc = new RecyclerViewChatViewHolder(v, Listener);

        return rvc;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewChatViewHolder holder, int position) {
        Message currentItem = list.get(position);

        holder.text.setText(currentItem.getText());
    }

    public RecyclerViewChat(Context context, ArrayList<Message> exampleList){
        this.list = exampleList;
        this.context = context;

        //for search method
        fullList = new ArrayList<>();
        fullList.addAll(list);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewChatViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        ConstraintLayout RecyclerViewMessage;

        public RecyclerViewChatViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            RecyclerViewMessage = itemView.findViewById(R.id.RecyclerViewMessage);

            RecyclerViewMessage.setOnLongClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onDeleteClick(position);
                    }
                }
                return true;
            });
        }
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Message> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {

                    filteredList.addAll(fullList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Message message : fullList) {
                        if (message.getText().toLowerCase().contains(filterPattern)) {
                            filteredList.add(message);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list.clear();
                list.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };
    }


}