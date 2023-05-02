package com.example.tictactoetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button playButton;
    Button playAI;

    //Node nodePC;
    //Node nodePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        playButton = findViewById(R.id.button3);
        playAI = findViewById(R.id.buttonAI);

        //nodePC = (Node) getIntent().getSerializableExtra("nodePC");
        //nodePlayer = (Node) getIntent().getSerializableExtra("nodePlayer");

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        playAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, BotActivity.class);
                //intent.putExtra("nodePlayer", nodePlayer);
                //intent.putExtra("nodePC", nodePC);
                startActivity(intent);
            }
        });
    }
}