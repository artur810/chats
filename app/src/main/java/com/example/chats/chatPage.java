package com.example.chats;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class chatPage extends AppCompatActivity {

    Button buttonSend;
    EditText textSend;
    RecyclerView recyclerview;
    public List<Message> chats;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caht_page);

        chats = new ArrayList<>();
        recyclerview = findViewById(R.id.recyclerview);

        buttonSend = findViewById(R.id.buttonSend);
        textSend = findViewById(R.id.textSend);

        buttonSend.setOnClickListener( v -> {

            if(!textSend.getText().toString().trim().isEmpty()){

                chats.add(new Message(String.valueOf(textSend.getText())));
                recyclerViewAdapter();
                textSend.setText(null);

            }

        });

    }

    private void recyclerViewAdapter(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(this, chats);
        recyclerview.setAdapter(adapter);

    }

}
