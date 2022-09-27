package com.example.sertifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.sertifikasi.adapter.ClientAdapter;
import com.example.sertifikasi.adapter.UserAdapter;
import com.example.sertifikasi.database.AppDatabase;
import com.example.sertifikasi.database.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class Activity_Client extends AppCompatActivity {

    private RecyclerView recClient;
    private List<User> listClient = new ArrayList<>();
    private ClientAdapter clientAdapter;
    private ImageButton logOutClient ;
    private AppDatabase dbClient;
    private Intent getdataIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        recClient = findViewById(R.id.recClient);
        logOutClient = findViewById(R.id.logOutClient);

        dbClient = AppDatabase.getInstance(getApplicationContext());

        logOutClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_Client.this , Activity_Login.class));
                finish();
            }
        });

        //get data from intent
         getdataIntent = getIntent();

        listClient.clear();
        listClient.add(dbClient.userDao().dataUser(getdataIntent.getStringExtra("username")));
        clientAdapter = new ClientAdapter(getApplicationContext() , listClient);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recClient.setLayoutManager(layoutManager);
        recClient.setAdapter(clientAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        listClient.clear();
        listClient.add(dbClient.userDao().dataUser(getdataIntent.getStringExtra("username")));
        clientAdapter.notifyDataSetChanged();
    }
}