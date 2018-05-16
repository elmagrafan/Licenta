package com.company;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocatieInXML {

    int x ;
    int y;
    int numarJetoane;
    String nume;

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

    public int getNumarJetoane() {
        return numarJetoane;
    }

    public void setNumarJetoane(int numarJetoane) {
        this.numarJetoane = numarJetoane;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    }


