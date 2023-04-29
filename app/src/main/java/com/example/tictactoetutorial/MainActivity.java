package com.example.tictactoetutorial;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView button1,button2,button3,button4,button5,button6,button7,button8,button9;
    private String startGame="X";
    int xCount=0,oCount=0,turnCounter=0;
    private TextView scorex,scoreo;
    private Button Reset;

    ImageView[] buttons;
    int[] buttonsValues = new int[] {10,10,10,10,10,10,10,10,10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=findViewById(R.id.buttonImage1);
        button2=findViewById(R.id.buttonImage2);
        button3=findViewById(R.id.buttonImage3);
        button4=findViewById(R.id.buttonImage4);
        button5=findViewById(R.id.buttonImage5);
        button6=findViewById(R.id.buttonImage6);
        button7=findViewById(R.id.buttonImage7);
        button8=findViewById(R.id.buttonImage8);
        button9=findViewById(R.id.buttonImage9);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        buttons = new ImageView[] {button1, button2, button3, button4, button5, button6, button7, button8, button9};

        scorex = findViewById(R.id.ScoreX);
        scoreo = findViewById(R.id.ScoreY);

        Reset=findViewById(R.id.Reset);

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValues();
                xCount=0;
                oCount=0;
                scorex.setText("Score X :- "+String.valueOf(xCount));
                scoreo.setText("Score O :- "+String.valueOf(oCount));
            }
        });
    }

    public int FindButton(int id){
        for(int i = 0; i < buttons.length; i++){
            if(buttons[i].getId() == id) return i;
        }
        return -1;
    }


    @Override
    public void onClick(View v) {
        int indexPressedButton = FindButton(v.getId());

        if(buttonsValues[indexPressedButton] == 10) {
            if(startGame.equals("X")) {
                buttons[indexPressedButton].setImageResource(R.drawable.cross);
                buttonsValues[indexPressedButton] = 1;
            } else {
                buttons[indexPressedButton].setImageResource(R.drawable.circle);
                buttonsValues[indexPressedButton] = 0;
            }
            turnCounter++;
            choosePlayer();
            String vincitore = winningGame();
            if( vincitore != "" ){
                if( vincitore == "X" ){
                    xCount++;
                    scorex.setText("Score X :- "+String.valueOf(xCount));
                }
                else if( vincitore == "O"){
                    oCount++;
                    scoreo.setText("Score O :- "+String.valueOf(oCount));
                }
                else {
                    Toast.makeText(MainActivity.this,"Pareggio",Toast.LENGTH_SHORT).show();
                }
                if( vincitore == "X" || vincitore == "O") Toast.makeText(MainActivity.this,"Ha vinto " + vincitore,Toast.LENGTH_SHORT).show();
                resetValues();
            }
        }
        else
        {
            Toast.makeText(MainActivity.this,"Button Already Pressed",Toast.LENGTH_SHORT).show();
        }
    }


    private String winningGame()
    {
        String giocatoreVincente = "";
        for (int i = 0; i < 3; i++)
        {
            //controllo vitoria righe
            if ((buttonsValues[i * 3] + buttonsValues[(i * 3) + 1] + buttonsValues[(i * 3) + 2]) == 3) { giocatoreVincente = "X"; }
            if ((buttonsValues[i * 3] + buttonsValues[(i * 3) + 1] + buttonsValues[(i * 3) + 2]) == 0) { giocatoreVincente = "O"; }
            //controllo vittoria colonne
            if ((buttonsValues[i] + buttonsValues[3 + i] + buttonsValues[6 + i]) == 3) { giocatoreVincente = "X"; }
            if ((buttonsValues[i] + buttonsValues[3 + i] + buttonsValues[6 + i]) == 0) { giocatoreVincente = "O"; }
        }
        //controllo vittoria diagonali
        if ((buttonsValues[0] + buttonsValues[4] + buttonsValues[8]) == 3) { giocatoreVincente = "X"; }
        if ((buttonsValues[0] + buttonsValues[4] + buttonsValues[8]) == 0) { giocatoreVincente = "O"; }
        if ((buttonsValues[2] + buttonsValues[4] + buttonsValues[6]) == 3) { giocatoreVincente = "X"; }
        if ((buttonsValues[2] + buttonsValues[4] + buttonsValues[6]) == 0) { giocatoreVincente = "O"; }
        if (giocatoreVincente == "" && turnCounter == 9) giocatoreVincente = "P";
        return giocatoreVincente;
    }


    private void choosePlayer()
    {
        if(startGame.equals("X"))
        {
            startGame="O";
        }
        else
        {
            startGame="X";
        }
    }



    private void resetValues() {
        turnCounter=0;
        for(int i = 0; i < buttonsValues.length; i++ ){
            buttonsValues[i] = 10;
            buttons[i].setImageDrawable((null));
        }
    }
}