package com.company.models;

/**
 * Created by Ovidiu on 3/28/2018.
 */
public class ListaReteteModel {

    private int coordXTranzitie;
    private int coordYTranzitie;
    private int CoordXLocatieIntrare;
    private int CoordYLocatieIntrare;
    private int CoordXLocatieIesire;
    private int CoordYLocatieIesire;
    private int durata;
    private String transitionName;
    private String locatieIesire;
    private String locatieIntrare;

    public ListaReteteModel(int coordXTranzitie, int coordYTranzitie, int coordXLocatieIntrare, int coordYLocatieIntrare, int coordXLocatieIesire, int coordYLocatieIesire, int durata, String transitionName, String locatieIesire, String locatieIntrare) {
        this.coordXTranzitie = coordXTranzitie;
        this.coordYTranzitie = coordYTranzitie;
        CoordXLocatieIntrare = coordXLocatieIntrare;
        CoordYLocatieIntrare = coordYLocatieIntrare;
        CoordXLocatieIesire = coordXLocatieIesire;
        CoordYLocatieIesire = coordYLocatieIesire;
        this.durata = durata;
        this.transitionName = transitionName;
        this.locatieIesire = locatieIesire;
        this.locatieIntrare = locatieIntrare;
    }

    public int getCoordXLocatieIntrare() {
        return CoordXLocatieIntrare;
    }

    public void setCoordXLocatieIntrare(int coordXLocatieIntrare) {
        CoordXLocatieIntrare = coordXLocatieIntrare;
    }

    public int getCoordYLocatieIntrare() {
        return CoordYLocatieIntrare;
    }

    public void setCoordYLocatieIntrare(int coordYLocatieIntrare) {
        CoordYLocatieIntrare = coordYLocatieIntrare;
    }

    public int getCoordXLocatieIesire() {
        return CoordXLocatieIesire;
    }

    public void setCoordXLocatieIesire(int coordXLocatieIesire) {
        CoordXLocatieIesire = coordXLocatieIesire;
    }

    public int getCoordYLocatieIesire() {
        return CoordYLocatieIesire;
    }

    public void setCoordYLocatieIesire(int coordYLocatieIesire) {
        CoordYLocatieIesire = coordYLocatieIesire;
    }

    public int getCoordXTranzitie() {
        return coordXTranzitie;
    }

    public void setCoordXTranzitie(int coordXTranzitie) {
        this.coordXTranzitie = coordXTranzitie;
    }

    public int getCoordYTranzitie() {
        return coordYTranzitie;
    }

    public void setCoordYTranzitie(int coordYTranzitie) {
        this.coordYTranzitie = coordYTranzitie;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    public String getLocatieIesire() {
        return locatieIesire;
    }

    public void setLocatieIesire(String locatieIesire) {
        this.locatieIesire = locatieIesire;
    }

    public String getLocatieIntrare() {
        return locatieIntrare;
    }

    public void setLocatieIntrare(String locatieIntrare) {
        this.locatieIntrare = locatieIntrare;
    }

}
