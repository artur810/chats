package com.example.chats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Chat> list;
    private List<Chat> fullList; //for search option
    private LayoutInflater mInflater;
    private Context context;

    RecyclerViewAdapter(Context context, List<Chat> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;

        //for search method
        fullList = new ArrayList<>();
        fullList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.chat, parent, false);

        return new ViewHolder(view);
    }

    @Override @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat chat = list.get(position);

        holder.num.setText(chat.getText());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView num;

        ViewHolder(View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, chatPage.class);
                intent.putExtra("num", String.valueOf(getAdapterPosition()));
                context.startActivity(intent);
            });
            itemView.setOnLongClickListener(v -> {
                Toast.makeText(itemView.getContext(), "לחצת ארוך", Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Chat> filteredList = new ArrayList<>();

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

                list.clear();
                list.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };
    }

}