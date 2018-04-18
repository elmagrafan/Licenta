package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;

/**
 * Created by Ovidiu on 12/14/2017.
 */
public class MyTranzitionList extends ArrayList {

    ArrayList<Tranzitie> listaTranzitii = new ArrayList<Tranzitie>();
    Tranzitie tranzitie;

    public void desenareTranzitie(Graphics g)
    {
        for (int i = 0; i < listaTranzitii.size(); i++)
           {
            tranzitie = listaTranzitii.get(i);
            tranzitie.desenare(g);
        }

    }

    public void Simulare(int pasiSimulare, Graphics g)
    {
        for (int i = 0; i < pasiSimulare; i++)
        {
            for (int j = 0; j < listaTranzitii.size(); j++)
            {
                listaTranzitii.get(j).Simulare();
            }
        }
        this.desenareTranzitie(g);
    }
}
