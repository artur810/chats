package com.example.chats;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAllChats extends RecyclerView.Adapter<RecyclerViewAllChats.RecyclerViewAllChatsViewHolder> {

    private List<Chat> chat;
    private List<Chat> fullList; //for search option
    private onItemClickListener mListener;
    private final Context context;

    public interface onItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    @Override @NonNull
    public RecyclerViewAllChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_chats, parent, false);

        RecyclerViewAllChatsViewHolder evhac = new RecyclerViewAllChatsViewHolder(v, mListener);

        return evhac;
    }

    public RecyclerViewAllChats(Context context, ArrayList<Chat> chat){
        this.chat = chat;
        this.context = context;

        //for search method
        fullList = new ArrayList<>();
        fullList.addAll(chat);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAllChatsViewHolder holder, int position) {
        Chat currentItem = chat.get(position);

        holder.num.setText(currentItem.getText());

        holder.imageProfile.setOnClickListener(arg0 -> {
            Fragment fragment = new Fragment(holder.imageProfile);
            AppCompatActivity activity = ((AppCompatActivity)context);
            fragment.show(activity.getSupportFragmentManager(), "dialog fragment");
        });

        holder.RecyclerViewCard.setOnClickListener(v -> {
            Intent intent = new Intent(context, chatPage.class);
            intent.putExtra("num", String.valueOf(holder.num.getText()));

            holder.imageProfile.setDrawingCacheEnabled(true);
            Bitmap b = holder.imageProfile.getDrawingCache();
            intent.putExtra("Bitmap", b);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public static class RecyclerViewAllChatsViewHolder extends RecyclerView.ViewHolder{

        public TextView num;
        public ImageButton imageProfile, deleteChat;
        public CardView RecyclerViewCard;

        public RecyclerViewAllChatsViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
            imageProfile = itemView.findViewById(R.id.imageProfileChat);
            deleteChat = itemView.findViewById(R.id.delete);
            RecyclerViewCard = itemView.findViewById(R.id.RecyclerViewAllChats);

            deleteChat.setOnClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onDeleteClick(position);
                        PrefConfigMessage.deleteListInPref(itemView.getContext(), String.valueOf(num.getText()));
                    }
                }
            });
        }
    }

    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List <Chat> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {

                    filteredList.addAll(fullList);
                } else {

                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Chat chat : fullList) {
                        if (chat.getText().toLowerCase().contains(filterPattern)) {
                            filteredList.add(chat);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                chat.clear();
                chat.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };
    }

}