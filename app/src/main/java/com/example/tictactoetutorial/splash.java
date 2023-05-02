package com.example.tictactoetutorial;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.*;


public class splash extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String jsonPlayer = gson.toJson(nodePlayer);
        String jsonPC = gson.toJson(nodePC);
        prefsEditor.putString("nodePlayer", jsonPlayer);
        prefsEditor.putString("nodePC", jsonPC);
        prefsEditor.apply();

         */

        Node head1 = Node.CreaAlberoM();
        Node.MosseMigliori1M(head1);
        Node head2 = Node.CreaAlberoM();
        Node.MosseMigliori2M(head2);

        Kryo kryo = new Kryo();
        kryo.register(Node.class);

        Output output = null;
        try {
            output = new Output(new FileOutputStream(new File(getFilesDir(), "head1.bin")));
            kryo.writeObject(output, head1);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            output = new Output(new FileOutputStream(new File(getFilesDir(),"head2.bin")));
            kryo.writeObject(output, head2);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nNNNNNNNNNNNNNNNNNNN");
        }



        /*
        FileOutputStream fos = null;
        try {
            fos = getApplicationContext().openFileOutput("head1", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(head1);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos = getApplicationContext().openFileOutput("head2", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(head2);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, MenuActivity.class);
                //intent.putExtra("nodePlayer", nodePlayer);
                //intent.putExtra("nodePC", nodePC);
                startActivity(intent);
                finish();
            }
        },4000);

    }
}