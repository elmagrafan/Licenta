package com.company;

import java.awt.*;

/**
 * Created by Ovidiu on 12/21/2017.
 */
public class Locatie {

    int x, y, numar;
    String nume;

    public Locatie(int xx, int yy, String numeLocatie, int numarJetoane)
    {
        x = xx;
        y = yy;
        nume = numeLocatie;
        numar = numarJetoane;
    }

    public void desenare(Graphics g)
    {
        Graphics2D grafica = (Graphics2D) g;
        grafica.drawOval(x, y,25,10);
        grafica.drawString(nume,x+27,y);
        String parseNumar = Integer.toString(numar);
        grafica.drawString(parseNumar, x+27, y+10);
    }
}
