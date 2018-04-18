package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;


/**
 * Created by Ovidiu on 1/19/2018.
 */
public class PasSimulare extends TimerTask{

       int count, numarPasSimulare = 0;
       Aplicatie a;
       Graphics g;
       public final static int ONE_SECOND = 1000;
      Timer t1;



       public PasSimulare(Aplicatie ap, Graphics gr, int count1, Timer t){
            this.a = ap;
            this.g = gr;
            this.count = count1;
            this.t1 = t;
       }


        public void run() {
            if (count > 0){
                    count--;
                    numarPasSimulare++;
                    inregistrareJetoane();
                    this.a.listaTranzitii.Simulare(1, g);
                    System.out.println("Afisare");
                    this.a.repaint();
                    if (count == 0) {
                        inregistrareJetoane();
                        t1.cancel();
                    }
            }


        }

    public void inregistrareJetoane()
    {
        ArrayList<Locatie> myLocationList = this.a.listaLocatii.listaLocatii;
        for (int i = 0; i <  myLocationList.size(); i++)
        {

            Locatie l = myLocationList.get(i);
            this.a.registru[i][numarPasSimulare] = l.numar;
        }

    }
    }
