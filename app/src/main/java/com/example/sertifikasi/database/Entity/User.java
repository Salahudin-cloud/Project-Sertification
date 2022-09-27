package com.example.sertifikasi.database.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    public int id;
    public String name;
    public String alamat;
    public String noHp;
    public String username;
    public String password;
    public String jenisKelamin;
    public  byte [] image;


}
