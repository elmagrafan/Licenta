package com.company;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ovidiu on 12/21/2017.
 */
public class MyLocationList {

    ArrayList<Locatie> listaLocatii = new ArrayList<Locatie>();
    Locatie locatie;

    public void desenareLocatie(Graphics g)
    {
        for (int i = 0; i < listaLocatii.size(); i++)
        {
            locatie = listaLocatii.get(i);
            locatie.desenare(g);
        }

    }

    public Locatie cautareLocatie(String numeLocatie)
    {
        for (int i = 0; i <  listaLocatii.size(); i++)
        {
            Locatie l = listaLocatii.get(i);
            String s = l.nume;
            boolean exista = s.equals(numeLocatie);
            if(exista)
                return l;
        }

        return null;
    }
}

