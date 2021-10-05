package com.example.chats;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatPage extends AppCompatActivity {

    Button buttonSend;
    EditText textSend;
    Toolbar toolbar;
    CircleImageView imageProfile;
    public static String num;

    private RecyclerView recyclerview;
    private RecyclerViewChat adapter;
    private RecyclerView.LayoutManager LayoutManager;
    private ArrayList<Message> chats;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Intent intent = getIntent();
        num = intent.getStringExtra("num");
        Bitmap bitmap = intent.getParcelableExtra("Bitmap");

        if(PrefConfigMessage.readListFromPref(getApplicationContext(), num) != null && !PrefConfigMessage.readListFromPref(getApplicationContext(), num).isEmpty()){
            chats = PrefConfigMessage.readListFromPref(getApplicationContext(), num);
        }else {
            chats = new ArrayList<>();
        }
        RecyclerViewAdapter();

        buttonSend = findViewById(R.id.buttonSend);
        textSend = findViewById(R.id.textSend);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(num);
        imageProfile = findViewById(R.id.imageProfile);
        imageProfile.setImageBitmap(bitmap);


        buttonSend.setOnClickListener( v -> {

            if(!textSend.getText().toString().trim().isEmpty()){

                int position = adapter.getItemCount();
                addItem(position);
                textSend.setText(null);

            }
        });

        imageProfile.setOnClickListener(arg0 -> {
            Fragment fragment = new Fragment(imageProfile);
            fragment.show(getSupportFragmentManager(), "dialog fragment");
        });
    }

    public void addItem (int position){
        chats.add(new Message(String.valueOf(textSend.getText())));
        adapter.notifyItemInserted(position);
        PrefConfigMessage.writeListInPref(getApplicationContext(), chats, num);
    }

    public void removeItem (int position){
        chats.remove(position);
        adapter.notifyItemRemoved(position);
        PrefConfigMessage.writeListInPref(getApplicationContext(), chats, num);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search_chat);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter != null){
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.delete:
                chats = new ArrayList<>();
                RecyclerViewAdapter();
                break;
            case R.id.set_background:
                this.getWindow().getDecorView().setBackgroundColor(Color.RED);
                break;
            case R.id.settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void RecyclerViewAdapter(){

        recyclerview = findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewChat(this, chats);

        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> removeItem(position));

    }

}