package vcelearner;

import java.util.ArrayList;

/**
 *
 * @author J.Weidehaas
 */
public class Sitzung {

    private ArrayList<LernKarte> lKs = LernKarte.getAll();
    //private ArrayList<LernKarte> lKs = new ArrayList<>();
    private int aktuellerLKIndex = 0;

    public Sitzung() {
//        for (int i = 1; i < 236; i++) {
//
//            LernKarte lK = new LernKarte(i, "Wie schnell bin ich", 1);
//            PotentielleAntwort pA = new PotentielleAntwort(true, "schau mal oben", i);
//            ArrayList<PotentielleAntwort> pAs = new ArrayList<>();
//            pAs.add(pA);
//            Themenbereich tB = new Themenbereich(1, "Java basics");
//            ArrayList<Themenbereich> tBs = new ArrayList<>();
//            tBs.add(tB);
//            lK.setpAs(pAs);
//            lK.settBs(tBs);
//            lKs.add(lK);
//        }
    }

    @Override
    public String toString() {
        return "Sitzung{" + "lKs=" + lKs + ", aktuellerLKIndex=" + aktuellerLKIndex + '}';
    }

    public LernKarte getAktuelleLernKarte() {
        return lKs.get(aktuellerLKIndex);
    }

    public void setAktuelleLernKarte(LernKarte lK) {
        lKs.set(aktuellerLKIndex, lK);
        LernKarte.update(lK);
    }

    public void addLernKarte(LernKarte lK) {
        LernKarte.insert(lK);
        lKs.add(lK);
        aktuellerLKIndex = lKs.size() - 1;
    }

    public void removeLernKarte() {
        LernKarte.delete(lKs.get(aktuellerLKIndex));
        lKs.remove(lKs.get(aktuellerLKIndex));
        if (aktuellerLKIndex >= lKs.size()) {
            aktuellerLKIndex = lKs.size() - 1;
        }
    }

    public LernKarte getNextLernKarte() {
        if (aktuellerLKIndex == lKs.size() - 1) {
            aktuellerLKIndex = 0;
        } else {
            aktuellerLKIndex++;
        }
        return getAktuelleLernKarte();
    }

    public LernKarte getPrevLernKarte() {
        if (aktuellerLKIndex == 0) {
            aktuellerLKIndex = lKs.size() - 1;
        } else {
            aktuellerLKIndex--;
        }
        return getAktuelleLernKarte();
    }

    public LernKarte getById(int id) {
        for (int i = 0; i < lKs.size(); i++) {
            if (lKs.get(i).getId() == id) {
                aktuellerLKIndex = i;
                break;
            }
        }
        return getAktuelleLernKarte();
    }

//    public void addAktuelleLernKarte (LernKarte lK){
//        LernKarte.insert(lK);
//        lKs.add(lK);
//    }
//    public void removeLernKarte (LernKarte lK){
//        lKs.remove(lK);
//        LernKarte.delete(lK);
//    }
//    public LernKarte geheZu(int id) {
//        for (LernKarte lK : lKs) {
//            if (lK.getId() == id) {
//                aktuellerLKIndex = lKs.indexOf(lK);
//            }
//            break;
//        }
//        return getAktuelleLernKarte();
//    }
}
