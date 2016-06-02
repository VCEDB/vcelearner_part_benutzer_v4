/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcelearner;

import java.util.ArrayList;

/**
 *
 * @author J.Bleich
 */
public class SitzungsLernKarte {
    private LernKarte lK;
    private ArrayList<PotentielleAntwort> gegebeneAntworten;
    private boolean wiederVorlage;
    private boolean gemogelt;
    private boolean mogelnAktiv;

    public SitzungsLernKarte(LernKarte lK) {
        this.lK = lK;
        gemogelt = false;
        mogelnAktiv = false;
        gegebeneAntworten = new ArrayList<>();
    }

    public ArrayList<PotentielleAntwort> getGegebeneAntworten() {
        return gegebeneAntworten;
    }

    public void setGegebeneAntworten(ArrayList<PotentielleAntwort> gegebeneAntworten) {
        this.gegebeneAntworten = gegebeneAntworten;
    }

    public boolean isWiederVorlage() {
        return wiederVorlage;
    }

    public void setWiederVorlage(boolean wiederVorlage) {
        this.wiederVorlage = wiederVorlage;
    }

    public boolean isGemogelt() {
        return gemogelt;
    }

    public void setGemogeltTrue() {
        this.gemogelt = true;
    }

    public LernKarte getlK() {
        return lK;
    }

    public boolean isMogelnAktiv() {
        return mogelnAktiv;
    }

    public void setMogelnAktiv(boolean mogelnAktiv) {
        this.mogelnAktiv = mogelnAktiv;
    }

}
