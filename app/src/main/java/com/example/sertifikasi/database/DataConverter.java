package com.example.sertifikasi.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DataConverter {

    public static  byte[] imageToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 , stream);
        return stream.toByteArray();
    }

    public static Bitmap byteToImage(byte[] array ) {
        return BitmapFactory.decodeByteArray(array, 0 , array.length);
    }


}
