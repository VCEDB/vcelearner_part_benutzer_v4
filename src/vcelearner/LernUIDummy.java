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
public class LernUIDummy {

    public static void main(String[] args) {
        ArrayList<LernKarte> lKs = new ArrayList<>();
        lKs.add(LernKarte.getById(1));
        lKs.add(LernKarte.getById(2));
        lKs.add(LernKarte.getById(3));
        lKs.add(LernKarte.getById(4));
        BenutzerSitzung session = new BenutzerSitzung(20,
                new Benutzer("Petra", "Panke"), lKs);
        session.getsLKs().get(1).setGemogeltTrue();
        session.getsLKs().get(2).setWiederVorlage(true);
        session.getsLKs().get(3).setGemogeltTrue();
        session.getsLKs().get(3).setWiederVorlage(true);
        int alternator = 1;
        for (SitzungsLernKarte sLK : session.getsLKs()) {
            alternator = 1 - alternator;
            ArrayList<PotentielleAntwort> gegeben = new ArrayList<>();
            gegeben.add(sLK.getlK().getpAs().get(alternator));
            System.out.println(gegeben);
//falsch:            session.getsLKs().get(1).setGegebeneAntworten(gegeben);
            sLK.setGegebeneAntworten(gegeben);
        }
/* abgeänderte Kopie aus UI-main */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LernenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LernenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LernenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LernenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LernenUI(session).setVisible(true); // hier konstruktor geändert
            }
        });
/* ende abgeänderte kopie */
        System.out.println("Timer = " + session.getZeitVorgabe());
        System.out.println("Benutzer: Login = " + session.getBenutzer().getLogin()
                + "; Passwort = " + session.getBenutzer().getPasswort());
        for (SitzungsLernKarte sLK : session.getsLKs()) {
            String ausgabe = "Lernkarte: " + sLK.getlK().toString();
            for (PotentielleAntwort ggA : sLK.getGegebeneAntworten()) {
                ausgabe += "\ngegebene Antwort(en):" + ggA.toString();
            }
            ausgabe += "\nWiedervorlage : " + sLK.isWiederVorlage();
            ausgabe += "\ngemogelt : " + sLK.isGemogelt();
            System.out.println(ausgabe);
        }
    }
}
