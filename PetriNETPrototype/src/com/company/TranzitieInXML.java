package com.company;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
@XmlRootElement
public class TranzitieInXML {

    ArrayList<LocatieInXML> listaLocatiiIntrare, listaLocatiiIesire;

    int x, y, durata;
    String nume;

    public ArrayList<LocatieInXML> getListaLocatiiIntrare() {
        return listaLocatiiIntrare;
    }

    public void setListaLocatiiIntrare(ArrayList<LocatieInXML> listaLocatiiIntrare) {
        this.listaLocatiiIntrare = listaLocatiiIntrare;
    }

    public ArrayList<LocatieInXML> getListaLocatiiIesire() {
        return listaLocatiiIesire;
    }

    public void setListaLocatiiIesire(ArrayList<LocatieInXML> listaLocatiiIesire) {
        this.listaLocatiiIesire = listaLocatiiIesire;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
