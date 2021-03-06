package com.example.chats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;

public class chatsRecyclerview extends AppCompatActivity {

    int n=0;
    Toolbar toolbar;
    ExtendedFloatingActionButton add;

    private RecyclerView recyclerview;
    private RecyclerViewAllChats mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Chat> chatsPage;

    private static final String NUMBER = "number";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chats_recyclerview);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if(PrefConfigChats.readListFromPref(getApplicationContext()) != null && !PrefConfigChats.readListFromPref(getApplicationContext()).isEmpty()){
            chatsPage = PrefConfigChats.readListFromPref(getApplicationContext());
        }else {
            chatsPage = new ArrayList<>();
        }
        RecyclerViewAdapter();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        add = findViewById(R.id.add);

        add.setOnClickListener(v -> {
            int position = mAdapter.getItemCount();
            addItem(position);
            n++;
            SharedPreferences.Editor editor = getSharedPreferences(NUMBER, MODE_PRIVATE).edit();
            editor.putInt("num", n);
            editor.apply();
        });
    }

    public void addItem (int position){
        chatsPage.add(new Chat(R.drawable.ic_baseline_image_24, String.valueOf("?????? ?????? " + n)));
        mAdapter.notifyItemInserted(position);
        PrefConfigChats.writeListInPref(getApplicationContext(), chatsPage);
        System.out.println(chatsPage);
    }

    public void removeItem (int position){
        chatsPage.remove(position);
        mAdapter.notifyItemRemoved(position);
        PrefConfigChats.writeListInPref(getApplicationContext(), chatsPage);
    }

    private void RecyclerViewAdapter(){

        recyclerview = findViewById(R.id.recyclerview);

        recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAllChats (this, chatsPage);

        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> removeItem(position));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setQueryHint("???????? ????????????");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mAdapter != null){
                    mAdapter.getFilter().filter(newText);
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

}