//package com.example.chats;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.List;
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
//
//    private List<Chat> list;
//    private LayoutInflater mInflater;
//
//    RecyclerViewAdapter(Context context, List<Chat> list) {
//        this.mInflater = LayoutInflater.from(context);
//        this.list = list;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.caht, parent, false);
//
//        return new RecyclerView.ViewHolder(view);
//    }
//
//    @Override @SuppressLint("SetTextI18n")
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Chat chat = list.get(position);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        ViewHolder(View itemView) {
//            super(itemView);
//
//        }
//    }
//
//}
