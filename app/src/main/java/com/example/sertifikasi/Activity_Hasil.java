package com.example.sertifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sertifikasi.adapter.UserAdapter;
import com.example.sertifikasi.database.AppDatabase;
import com.example.sertifikasi.database.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class Activity_Hasil extends AppCompatActivity {

    private RecyclerView rec;
    private AppDatabase database;
    private List<User> list = new ArrayList<>();
    private UserAdapter userAdapter;
    private ImageButton logOut, delete ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        rec = findViewById(R.id.rec);
        database = AppDatabase.getInstance(getApplicationContext());
        logOut = findViewById(R.id.logOut);
        delete = findViewById(R.id.deleteBtn);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_Hasil.this, Activity_Login.class));
                finish();
            }
        });





        list.clear();
        list.addAll(database.userDao().getAll());
        userAdapter = new UserAdapter(getApplicationContext() , list);

        userAdapter.setRecyclerViewInterface(new UserAdapter.RecyclerViewInterface() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getApplicationContext(), "Data " + list.get(position).username + " Berhasil di hapus !", Toast.LENGTH_SHORT).show();
                database.userDao().delete(list.get(position).id);
                onStart();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rec.setLayoutManager(layoutManager);
        rec.setAdapter(userAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(database.userDao().getAll());
        userAdapter.notifyDataSetChanged();
    }
}