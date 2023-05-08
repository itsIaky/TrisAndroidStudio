package com.example.tictactoetutorial;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.*;

public class BotActivity extends AppCompatActivity implements View.OnClickListener {

    //bottoni
    ImageView button1,button2,button3,button4,button5,button6,button7,button8,button9;
    //segno del giocatore che sta facendo la mossa
    private String startGame="X";
    //count vittorie
    int xCount=0,oCount=0,turnCounter=0;
    //label per gli score
    private TextView scorex,scoreo;
    private Button Reset;
    //array che contiene tutti i bottoni ordinati
    ImageView[] buttons;
    //array che contiene il valore di ogni casella del campo: 10 = vuota, 0 = O, 1 = X
    int[] buttonsValues = new int[] {10,10,10,10,10,10,10,10,10};


    int livello;
    Node head1;
    Node head2;
    Node attuale;
    Boolean primo;
    Random r;
    Kryo kryo;

    //funzione per la creazione del campo
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        kryo = new Kryo();
        kryo.register(Node.class);
        r = new Random();

        primo = true;
        livello = 0;

        Input input = null;
        try {
            input = new Input(new FileInputStream(new File(getFilesDir(), "head1.bin")));
            head1 = kryo.readObject(input, Node.class);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            input = new Input(new FileInputStream(new File(getFilesDir(), "head2.bin")));
            head2 = kryo.readObject(input, Node.class);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //collegamento tra variabili e oggetti grafici
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

        //set della funzione associata ai bottoni (function OnClick riga ...)
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        //riempio l'array di bottoni
        buttons = new ImageView[] {button1, button2, button3, button4, button5, button6, button7, button8, button9};
        if(r.nextBoolean()) {
            //pc parte con il cerchio
            attuale = head1;

            Node mossaMigliore = attuale.nodi.stream().filter(x -> x.vittorie == attuale.nodi.stream().max(Comparator.comparing(y -> y.vittorie)).get().vittorie).findFirst().get();
            int indexMossaMigliore = mossaMigliore.posizione;

            buttons[indexMossaMigliore - 1].setImageResource(R.drawable.circle);
            buttonsValues[indexMossaMigliore - 1] = 0;
            livello++;
            turnCounter++;
            attuale = mossaMigliore;

        }
        else attuale = head2;

        //collego le variabili alle label
        scorex = findViewById(R.id.ScoreX);
        scoreo = findViewById(R.id.ScoreY);

        //collego la variabile al bottone reset
        Reset = findViewById(R.id.Reset);

        //assegno la funzione reset al bottone
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValues();
                xCount=0;
                oCount=0;
                scorex.setText("Punti X : "+String.valueOf(xCount));
                scoreo.setText("Punti O : "+String.valueOf(oCount));
            }
        });
    }

    //funzione per returnare l'index del bottone premuto
    public int FindButton(int id){
        for(int i = 0; i < buttons.length; i++){
            if(buttons[i].getId() == id) return i;
        }
        return -1;
    }


    //funzione che imposta il segno nella posizione selezionata in campo
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        int indexPressedButton = FindButton(v.getId());

        //controllo che la casella sia vuota
        if(buttonsValues[indexPressedButton] == 10) {
            if(startGame.equals("X")) {
                buttons[indexPressedButton].setImageResource(R.drawable.cross);
                buttonsValues[indexPressedButton] = 1;
            } else {
                buttons[indexPressedButton].setImageResource(R.drawable.circle);
                buttonsValues[indexPressedButton] = 0;
            }

            livello++;
            turnCounter++;

            //check vittoria
            String vincitore = winningGame();
            if( vincitore != "" ){
                if( vincitore == "X" ){
                    xCount++;
                    scorex.setText("Punti X : "+String.valueOf(xCount));
                }
                else if( vincitore == "O"){
                    oCount++;
                    scoreo.setText("Punti O : "+String.valueOf(oCount));
                }
                else {
                    Toast.makeText(BotActivity.this,"Pareggio",Toast.LENGTH_SHORT).show();
                }
                if( vincitore == "X" || vincitore == "O") Toast.makeText(BotActivity.this,"Ha vinto " + vincitore,Toast.LENGTH_SHORT).show();
                resetValues();
                return;
            }
        }
        else {
            Toast.makeText(BotActivity.this,"Spazio occupato",Toast.LENGTH_SHORT).show();
        }

        attuale = attuale.nodi.stream().filter(x -> x.posizione == indexPressedButton + 1).findAny().get();

        Node mossaMigliore = attuale.nodi.stream().filter(x -> x.vittorie == attuale.nodi.stream().max(Comparator.comparing(y -> y.vittorie)).get().vittorie).findFirst().get();

        int indexMossaMigliore = mossaMigliore.posizione;

        buttons[indexMossaMigliore - 1].setImageResource(R.drawable.circle);
        buttonsValues[indexMossaMigliore - 1] = 0;
        livello++;
        attuale = mossaMigliore;
        turnCounter++;

        String vincitore = winningGame();
        if( vincitore != "" ){
            if( vincitore == "X" ){
                xCount++;
                scorex.setText("Punti X : "+String.valueOf(xCount));
            }
            else if( vincitore == "O"){
                oCount++;
                scoreo.setText("Punti O : "+String.valueOf(oCount));
            }
            else {
                Toast.makeText(BotActivity.this,"Pareggio",Toast.LENGTH_SHORT).show();
            }
            if( vincitore == "X" || vincitore == "O") Toast.makeText(BotActivity.this,"Ha vinto " + vincitore,Toast.LENGTH_SHORT).show();
            resetValues();
        }
    }

    private String winningGame() {
        String giocatoreVincente = "";
        for (int i = 0; i < 3; i++) {
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

    //funzione per il reset dei bottoni
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void resetValues() {
        turnCounter = 0;
        livello = 0;
        for(int i = 0; i < buttonsValues.length; i++ ){
            buttonsValues[i] = 10;
            buttons[i].setImageDrawable((null));
        }

        if(r.nextBoolean()) {
            //pc parte con il cerchio
            attuale = head1;

            Node mossaMigliore = attuale.nodi.stream().filter(x -> x.vittorie == attuale.nodi.stream().max(Comparator.comparing(y -> y.vittorie)).get().vittorie).findFirst().get();
            int indexMossaMigliore = mossaMigliore.posizione;

            buttons[indexMossaMigliore - 1].setImageResource(R.drawable.circle);
            buttonsValues[indexMossaMigliore - 1] = 0;
            livello++;
            turnCounter++;
            attuale = mossaMigliore;

        }
        else attuale = head2;
    }
}