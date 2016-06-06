package vcelearner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author J.Bleich
 */
public class BenutzerSitzung {

    // Verbindungsvariablen
    static Statement st = null;
    static PreparedStatement pst = null;
    static ResultSet rst = null;

    private int zeitVorgabe;
    private Benutzer benutzer;
    private ArrayList<SitzungsLernKarte> sLKs;
    private int aktuellerSLKIndex;
    private LernSitzung lernSitzung;

    public BenutzerSitzung(int zeitVorgabe, Benutzer benutzer,
            ArrayList<LernKarte> lKs) {
        this.zeitVorgabe = zeitVorgabe;
        this.benutzer = benutzer;
        sLKs = new ArrayList<>();
        ArrayList<Benutzer2LernKarte> b2LKs = Benutzer2LernKarte.getAllByBenutzer(benutzer);
        ArrayList<Integer> wiederVorlageLKIDs = new ArrayList<>();
        for (Benutzer2LernKarte b2LK : b2LKs) {
            if (b2LK.isWiedervorlage()) {
                wiederVorlageLKIDs.add(b2LK.getLernKarte_id());
            }
        }
        for (LernKarte lK : lKs) {
            this.sLKs.add(new SitzungsLernKarte(lK));
            if (wiederVorlageLKIDs.contains(lK.getId())) {
                this.sLKs.get(this.sLKs.size()-1).setWiederVorlage(true);
            }
        }
        lernSitzung= new LernSitzung("ungewertet",benutzer.getId());        
        LernSitzung.insert(lernSitzung);
    }

    public BenutzerSitzung(int zeitVorgabe, Benutzer benutzer,
            ArrayList<LernKarte> lKs, String lernSitzungsTyp) {
        this.zeitVorgabe = zeitVorgabe;
        this.benutzer = benutzer;
        sLKs = new ArrayList<>();
        ArrayList<Benutzer2LernKarte> b2LKs = Benutzer2LernKarte.getAllByBenutzer(benutzer);
        ArrayList<Integer> wiederVorlageLKIDs = new ArrayList<>();
        for (Benutzer2LernKarte b2LK : b2LKs) {
            if (b2LK.isWiedervorlage()) {
                wiederVorlageLKIDs.add(b2LK.getLernKarte_id());
            }
        }
        for (LernKarte lK : lKs) {
            this.sLKs.add(new SitzungsLernKarte(lK));
            if (wiederVorlageLKIDs.contains(lK.getId())) {
                this.sLKs.get(this.sLKs.size()-1).setWiederVorlage(true);
            }
        }
        lernSitzung= new LernSitzung(lernSitzungsTyp,benutzer.getId());        
        LernSitzung.insert(lernSitzung);
    }
    

    public static void insert(BenutzerSitzung benutzerSitzung) {

        for (int i = 0; i < benutzerSitzung.getsLKs().size(); i++) {

            // Wiedervorlage in Benutzer2Lernkarte speichern, nur wenn
            // Wiedervorlage = true und kein diesbezüglicher Eintrag in der DB
            // vorhaneden ist
            Benutzer2LernKarte b2lk = 
                        new Benutzer2LernKarte(benutzerSitzung.getBenutzer().getId(),
                        benutzerSitzung.getsLKs().get(i).getlK().getId(),
                        benutzerSitzung.getsLKs().get(i).isWiederVorlage());
            
            if (benutzerSitzung.getsLKs().get(i).isWiederVorlage() == true && 
                Benutzer2LernKarte.checkWiedervorlage(b2lk)== false) {
                
                
                Benutzer2LernKarte.insert(b2lk);
            }
            // Wiedervorlage in Benutzer2Lernkarte löschen, nur wenn
            // Wiedervorlage = false und allerdings ein diesbezüglicher Eintrag in der DB
            // vorhanden ist
            if (benutzerSitzung.getsLKs().get(i).isWiederVorlage() == false && 
                Benutzer2LernKarte.checkWiedervorlage(b2lk)== true) {
                
                Benutzer2LernKarte.delete(benutzerSitzung.getBenutzer(), 
                    benutzerSitzung.getsLKs().get(i).getlK()
                );
            }
            // ArrayList Gegebene Antworten (als PotentielleAntworten) in 
            // LernSitzung2PotentielleAntwort speichern
            for (int j = 0; j < benutzerSitzung.getsLKs().get(i).getGegebeneAntworten().size(); j++) {

                LernSitzung2PotentielleAntwort ls2pa
                        = new LernSitzung2PotentielleAntwort(benutzerSitzung.getLernSitzung().getId(),
                                benutzerSitzung.getsLKs().get(i).getGegebeneAntworten().get(j).getId());
                LernSitzung2PotentielleAntwort.insert(ls2pa);

            }

            // Gemogelt in LernSitzung2LernKarte speichern
            LernSitzung2LernKarte ls2lk
                    = new LernSitzung2LernKarte(benutzerSitzung.getLernSitzung().getId(),
                            benutzerSitzung.getsLKs().get(i).getlK().getId(),
                            benutzerSitzung.getsLKs().get(i).isGemogelt());
            LernSitzung2LernKarte.insert(ls2lk);

        }

    }

    public int getAktuellerSLKIndex() {
        return aktuellerSLKIndex;
    }

    public void setAktuellerSLKIndex(int aktuellerSLKIndex) {
        this.aktuellerSLKIndex = aktuellerSLKIndex;
    }

    public LernSitzung getLernSitzung() {
        return lernSitzung;
    }

    public void setLernSitzung(LernSitzung lernSitzung) {
        this.lernSitzung = lernSitzung;
    }

    public int getZeitVorgabe() {
        return zeitVorgabe;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public ArrayList<SitzungsLernKarte> getsLKs() {
        return sLKs;
    }

    public SitzungsLernKarte getAktuelleSitzungsLernKarte() {
        return sLKs.get(aktuellerSLKIndex);
    }

    public SitzungsLernKarte geheZu(int nummer) {
        aktuellerSLKIndex = nummer - 1;
        return getAktuelleSitzungsLernKarte();
    }

    public String getTitelString(int modus) {
        // modus 0 : Frage x / y (ID xxx) Schwierigkeit xxx
        // modus 1 : Themengebiete
        String rueckgabe = "";
        if (modus == 1) {
            rueckgabe += "Themenbereich(e): " + getAktuelleSitzungsLernKarte().getlK().gettBs().toString();
        } else {
            rueckgabe += "Frage " + (aktuellerSLKIndex + 1) + " / " + sLKs.size();
            rueckgabe += "           (ID = " + getAktuelleSitzungsLernKarte().getlK().getId() + ")";
            rueckgabe += "          Schwierigkeit: " + sLKs.get(aktuellerSLKIndex).getlK().getSchwierigkeitsGrad();
        }
        return rueckgabe;
    }

    public void speichereInDB() {
        // Dummy-Code
//        String ausgabe = "\nBenutzer : " + benutzer.getLogin();
//        ausgabe += "\nZeitlimit : " + zeitVorgabe;
//        for (SitzungsLernKarte sLK : sLKs) {
//            ausgabe += "\nFrage-ID " + sLK.getlK().getId() + " gegebene Antworten : ";
//            for (PotentielleAntwort pA : sLK.getlK().getpAs()) {
//                if (sLK.getGegebeneAntworten().contains(pA)) {
//                    ausgabe += pA.getId() + "(" + (sLK.getlK().getpAs().indexOf(pA) + 1) + "), ";
//                }
//            }
//            ausgabe += "Gemogelt = " + sLK.isGemogelt() + ", Wiedervorlage = "
//                    + sLK.isWiederVorlage() + "\n";
//        }
//        System.out.println(ausgabe);
        insert(this);
    }

    public SitzungsLernKarte getNextSitzungsLernKarte() {
        if (aktuellerSLKIndex < sLKs.size() - 1) {
            aktuellerSLKIndex++;
        }
        return getAktuelleSitzungsLernKarte();
    }

    public SitzungsLernKarte getPrevSitzungsLernKarte() {
        if (aktuellerSLKIndex > 0) {
            aktuellerSLKIndex--;
        }
        return getAktuelleSitzungsLernKarte();
    }

}
