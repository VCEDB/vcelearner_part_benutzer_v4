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
public class LernUIDummy2 {

    public static void main(String[] args) {
        ArrayList<LernKarte> lKs = LernKarte.getAll();
        ArrayList<LernKarte> lKsToRemove = new ArrayList<>();
        for (LernKarte lK : lKs) {
            boolean keep=false;
            for (ThemenBereich tB : lK.gettBs()) {
                if (tB.getId() == 1) {
                    keep=true;
                }
            }
            if (!keep) {
                lKsToRemove.add(lK);
            }
        }
        for (LernKarte lK : lKsToRemove) {
            lKs.remove(lK);
        }
        BenutzerSitzung session = new BenutzerSitzung(0,
                new Benutzer("Petra", "Panke"), lKs);
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
    }
}
