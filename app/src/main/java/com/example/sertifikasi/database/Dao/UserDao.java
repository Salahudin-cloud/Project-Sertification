package com.example.sertifikasi.database.Dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sertifikasi.database.Entity.User;

import java.util.List;

@Dao
public interface UserDao {

    //get all data
    @Query("SELECT * FROM user ")
    List<User> getAll();

    //get all data by id
    @Query("SELECT * FROM user WHERE id = :id")
    User getById(int id);

    @Insert
    void insertAll(User... users);


    @Query("SELECT * FROM user WHERE username= :username")
    User dataUser(String username);

    //if data return null
    @Query("SELECT  * FROM user WHERE username = :username ")
    boolean check(String username);

    @Query("DELETE FROM user WHERE id =:id")
    void delete(int id);

    @Query("INSERT INTO user(name,alamat,noHp,username,password,jenisKelamin,image) VALUES(:name,:alamat,:noHp,:username,:password,:jenisKelamin,:image)")
    void insertData(String name, String alamat , String noHp, String username, String password, String jenisKelamin, byte[] image);

}
