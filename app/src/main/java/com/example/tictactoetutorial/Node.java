package com.example.tictactoetutorial;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    @SerializedName("nodes")
    public List<Node> nodi;
    @SerializedName("pos")
    public int posizione;
    @SerializedName("vic")
    public int vittorie;

    public Node()
    {

    }
    public Node(int pos)
    {
        posizione = pos;
        this.nodi = new ArrayList<Node>();
        this.vittorie = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Node CreaAlberoM()
    {
        Node head = new Node(-1);

        List<Integer> initialPos = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++)
        {
            initialPos.add(i + 1);
        }

        for (int i = 0; i < 9; i++)
        {
            List<Integer> _initialPos = new ArrayList<Integer>(initialPos);
            head.nodi.add(CreaAlberoR(_initialPos.get(i), _initialPos));
        }

        return head;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Node CreaAlberoR(int pos, List<Integer> remaningPos)
    {
        Node node = new Node(pos);

        remaningPos.removeIf(x -> x == node.posizione);

        for(int i = 0; i < remaningPos.stream().count(); i++)
        {
            List<Integer> _remaningPos = new ArrayList<Integer>(remaningPos);
            node.nodi.add(CreaAlberoR(_remaningPos.get(i), _remaningPos));
        }

        return node;
    }

    public static Node MosseMigliori2M(Node head)
    {

        for(Node node : head.nodi)
        {
            int[] tab = new int[9];
            Arrays.fill(tab, -10);
            MosseMigliori2R(node, 1, tab);
        }
        return head;
    }

    public static int MosseMigliori2R(Node node, int livello, int[] tabella)
    {
        //segno = 0 pc; = 1 player
        int segno;
        if (livello % 2 == 0) segno = 0;
        else segno = 1;

        tabella[node.posizione - 1] = segno;

        if(livello != 9)
        {
            for(Node n : node.nodi)
            {
                int[] _tabella = (int[])tabella.clone();
                node.vittorie += MosseMigliori2R(n, livello + 1, _tabella);
            }
        }

        for (int i = 0; i < 3; i++)
        {
            if ((tabella[i * 3] + tabella[(i * 3) + 1] + tabella[(i * 3) + 2]) == 0) node.vittorie++;
            if ((tabella[i * 3] + tabella[(i * 3) + 1] + tabella[(i * 3) + 2]) == 3) node.vittorie--;

            if ((tabella[i] + tabella[3 + i] + tabella[6 + i]) == 0) node.vittorie++;
            if ((tabella[i] + tabella[3 + i] + tabella[6 + i]) == 3) node.vittorie--;

        }
        if ((tabella[0] + tabella[4] + tabella[8]) == 0) node.vittorie++;
        if ((tabella[0] + tabella[4] + tabella[8]) == 3) node.vittorie--;

        if ((tabella[2] + tabella[4] + tabella[6]) == 0) node.vittorie++;
        if ((tabella[2] + tabella[4] + tabella[6]) == 3) node.vittorie--;



        return node.vittorie;
    }

    public static Node MosseMigliori1M(Node head)
    {
        int[] tab = new int[9];
        for (Node node : head.nodi) {
            Arrays.fill(tab, -10);
            MosseMigliori1R(node, 1, tab);
        }
        return head;
    }

    public static int MosseMigliori1R(Node node, int livello, int[] tabella)
    {
        int segno;
        if (livello % 2 == 0) segno = 0;
        else segno = 1;

        tabella[node.posizione - 1] = segno;

        if (livello != 9)
        {
            for(Node n : node.nodi)
            {
                int[] _tabella = (int[])tabella.clone();
                node.vittorie += MosseMigliori1R(n, livello + 1, _tabella);
            }
        }

        for (int i = 0; i < 3; i++)
        {
            if ((tabella[i * 3] + tabella[(i * 3) + 1] + tabella[(i * 3) + 2]) == 3) node.vittorie++;
            if ((tabella[i * 3] + tabella[(i * 3) + 1] + tabella[(i * 3) + 2]) == 0) node.vittorie--;

            if ((tabella[i] + tabella[3 + i] + tabella[6 + i]) == 3) node.vittorie++;
            if ((tabella[i] + tabella[3 + i] + tabella[6 + i]) == 0) node.vittorie--;

        }
        if ((tabella[0] + tabella[4] + tabella[8]) == 3) node.vittorie++;
        if ((tabella[0] + tabella[4] + tabella[8]) == 0) node.vittorie--;

        if ((tabella[2] + tabella[4] + tabella[6]) == 3) node.vittorie++;
        if ((tabella[2] + tabella[4] + tabella[6]) == 0) node.vittorie--;



        return node.vittorie;
    }

    public static int NumeroNodiR(Node head)
    {
        int figli = 0;
        if (head.nodi != null)
        {
            for(Node nodo : head.nodi)
            {
                figli += NumeroNodiR(nodo);
            }
        }
        return 1 + figli;
    }
}