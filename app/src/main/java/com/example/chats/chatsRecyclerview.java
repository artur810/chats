package com.example.chats;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class chatsRecyclerview extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    public List <Chat> chatsPage;
    int n = 0;
    RecyclerView recyclerview;
    Toolbar toolbar;
    ExtendedFloatingActionButton add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_recyclerview);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        chatsPage = new ArrayList<>();
        recyclerview = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        add = findViewById(R.id.add);

        add.setOnClickListener(v -> {
            chatsPage.add(new Chat(String.valueOf(n)));
            RecyclerViewAdapter();
            n++;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(this, chatsPage);
        recyclerview.setAdapter(adapter);

    }

}
