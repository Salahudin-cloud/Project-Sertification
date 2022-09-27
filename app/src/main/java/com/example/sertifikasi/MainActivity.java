package com.example.sertifikasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.util.ULocale;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sertifikasi.database.AppDatabase;
import com.example.sertifikasi.database.DataConverter;
import com.example.sertifikasi.database.Entity.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int SELECT_IMAGE = 1;
    Uri uri;
    ImageView imageView;
    Button btnChoose, cek, submit, back;
    EditText nama, username, password, alamat, nomorHp;
    Bitmap bitmap;
    private FusedLocationProviderClient fusedLocationClient;
    RadioGroup radioGroup;
    RadioButton laki;
    RadioButton perempuan;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisasi komponen
        btnChoose = findViewById(R.id.buttonUpload);
        imageView = findViewById(R.id.hasilPilih);
        nama = findViewById(R.id.inputNama);
        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        nomorHp = findViewById(R.id.inputHp);
        alamat = findViewById(R.id.inputAlamat);
        cek = findViewById(R.id.buttonCek);
        submit = findViewById(R.id.buttonSubmit);
        back = findViewById(R.id.buttonBack);

        //radio button
        radioGroup = findViewById(R.id.radioGrup);
        laki = findViewById(R.id.laki);
        perempuan = findViewById(R.id.perempuan);

        //inisialisasi database
        db = AppDatabase.getInstance(getApplicationContext());

        //handle back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , Activity_Login.class));
            }
        });

        //handle submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRegis();
            }
        });

        //handle alamat
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        cek.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        //handle image
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_IMAGE);
            }
        });


    }

    private void submitRegis() {
        //get data from all field

        User user = new User();
        String name = nama.getText().toString();
        String alamatData = alamat.getText().toString();
        String noHp = nomorHp.getText().toString();
        String usernameData =  username.getText().toString();
        String passwordData = password.getText().toString();
        byte[] image = DataConverter.imageToByte(bitmap);


        //handle radio button
        int pilih = radioGroup.getCheckedRadioButtonId();
        String getKelamin;
        if (pilih == laki.getId()) {
            getKelamin = laki.getText().toString();
        }else {
             getKelamin = perempuan.getText().toString();
        }
        db.userDao().insertData(name,alamatData,noHp,usernameData,passwordData,getKelamin,image);
        startActivity(new Intent(MainActivity.this , Activity_Login.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10 ) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Denied " , Toast.LENGTH_SHORT).show();
            }else {
                getLocation();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},44 );
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    try {
                        //inisialisasi geocode
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        //inisialisasi address
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        alamat.setText(addresses.get(0).getAddressLine(0));
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), " cant find location " , Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), " cant find location " , Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}