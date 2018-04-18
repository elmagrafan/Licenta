package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.awt.Color.*;

public class Tranzitie extends JFrame
        {
            int x, y, time;
            String nume;
            int contor = 0;
            //contor = 0 e tranzitie inactiva

            public ArrayList<Locatie> listaLocatiiIntrare = new ArrayList<Locatie>();
            public ArrayList<Locatie> listaLocatiiIesire = new ArrayList<Locatie>();

            public Tranzitie(int xx, int yy, String numeTranzitie, int timeOfTransition)
            {
                x = xx;
                y = yy;
                nume = numeTranzitie;
                time = timeOfTransition;

            }

            public void desenare(Graphics g)
            {

                    Graphics2D grafica = (Graphics2D) g;
                    grafica.setPaint(RED);
                for (int i = 0; i < listaLocatiiIntrare.size(); i++) {
                    Locatie l = listaLocatiiIntrare.get(i);
                grafica.drawLine(l.x + 10,l.y + 5,this.x + 10,this.y + 5);
                }
                    grafica.setPaint(BLUE);
                for (int i = 0; i < listaLocatiiIesire.size(); i++) {
                    Locatie l = listaLocatiiIesire.get(i);
                    grafica.drawLine(l.x + 15,l.y + 5,this.x + 15,this.y + 5);
                }
                if (contor == 0) {
                    grafica.setPaint(BLACK);
                }else {
                    grafica.setPaint(GREEN);
                }
                grafica.drawRect(x, y, 25, 10);
                grafica.setPaint(BLACK);
                grafica.drawString(nume, x+27, y+10);
                String parseTime = Integer.toString(time);
                String parseContor = Integer.toString(contor);
                grafica.drawString(parseTime, x+27, y+20);
                grafica.drawString(parseContor, x+27, y+30);

            }

            public void Simulare()
            {
                if(contor > 0){
                    contor--;
                    if (contor == 0){
                        FinalizareTranzitie();
                    }
                }else {
                   VerificareLansareTranzitie();
                }
            }

            public void FinalizareTranzitie()
            {
                for (int i = 0; i < listaLocatiiIesire.size(); i++)
                {
                    listaLocatiiIesire.get(i).numar++;
                }
            }

            public void VerificareLansareTranzitie()
            {
                boolean conditieLansareOK = true;

                for (int i = 0; i < listaLocatiiIntrare.size(); i++)
                {
                    if (listaLocatiiIntrare.get(i).numar > 0) {
                        conditieLansareOK = conditieLansareOK && true;
                    }else {
                        conditieLansareOK = false;
                    }
                }
                if(conditieLansareOK)
                {
                    for (int i = 0; i < listaLocatiiIntrare.size(); i++)
                    {
                        listaLocatiiIntrare.get(i).numar--;

                    }

                    contor = time;
                }
            }
        }
