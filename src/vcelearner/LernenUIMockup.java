/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcelearner;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.ActionListener;

/**
 *
 * @author J.Weidehaas
 */
public class LernenUIMockup extends javax.swing.JFrame {

    BenutzerSitzung session;

    /**
     * Creates new form LernenUIMockup
     */
    public LernenUIMockup() {
        initComponents();

        textAreasAntwort = new javax.swing.JTextArea[]{textAreaAntwortA,
            textAreaAntwortB, textAreaAntwortC, textAreaAntwortD, textAreaAntwortE,
            textAreaAntwortF, textAreaAntwortG, textAreaAntwortH};

        checkBoxesAntwort = new javax.swing.JCheckBox[]{checkBoxA, checkBoxB,
            checkBoxC, checkBoxD, checkBoxE, checkBoxF, checkBoxG, checkBoxH};

        timerDauer = session.getZeitVorgabe();
        timerZaehlt();
        fillWithValues();

    }

    public LernenUIMockup(BenutzerSitzung session) {
        this.session = session;
        initComponents();

        textAreasAntwort = new javax.swing.JTextArea[]{textAreaAntwortA,
            textAreaAntwortB, textAreaAntwortC, textAreaAntwortD, textAreaAntwortE,
            textAreaAntwortF, textAreaAntwortG, textAreaAntwortH};

        checkBoxesAntwort = new javax.swing.JCheckBox[]{checkBoxA, checkBoxB,
            checkBoxC, checkBoxD, checkBoxE, checkBoxF, checkBoxG, checkBoxH};

        timerDauer = session.getZeitVorgabe();
        timerZaehlt();
        fillWithValues();
    }

    public void fillWithValues() {

        if (session.getAktuellerSLKIndex() < session.getsLKs().size() - 1) {
            buttonVor.setEnabled(true);
        } else {
            buttonVor.setEnabled(false);
        }

        if (session.getAktuellerSLKIndex() > 0) {
            buttonZurueck.setEnabled(true);
        } else {
            buttonZurueck.setEnabled(false);
        }
        
//        toggleButtonMogeln.setSelected(false);                              // Mogel-Button-reset
//        session.getAktuelleSitzungsLernKarte().setMogelnAktiv(false);

        textAreaFrage.setText(session.getAktuelleSitzungsLernKarte().getlK().getFrage());

        labelTitel.setText(session.getTitelString(0) + " höehe: " + calcStringHoehe(textAreaFrage.getText(), textAreaFrage.getFont())+" zeile: "+calcStringHoehe("pew", textAreaFrage.getFont()));

        toggleButtonMogeln.setSelected(session.getAktuelleSitzungsLernKarte().isMogelnAktiv());

        toggleButtonWiedervorlage.setSelected(session.getAktuelleSitzungsLernKarte().isWiederVorlage());

        for (int i = 0; i < 8; i++) {
            if (i < session.getAktuelleSitzungsLernKarte().getlK().getpAs().size()) {
                textAreasAntwort[i].setText(session.getAktuelleSitzungsLernKarte().getlK().getpAs().get(i).getAntwort());
                if (modus == 0) { //wenn Lernmodus
                    checkBoxesAntwort[i].setEnabled(true); // Checkbox aktivieren
                } else { // wenn nicht Lernmodus, also Lesemodus
                    //hier Code zur hervorhebung der richtigen Antworten
                }
                if (session.getAktuelleSitzungsLernKarte().getGegebeneAntworten().contains(
                        session.getAktuelleSitzungsLernKarte().getlK().getpAs().get(i))) {
                    // wenn Antwort unter den gegebenen Antworten
                    checkBoxesAntwort[i].setSelected(true);
                } else {
                    // wenn und Antwort nicht unter den gegebenen Antworten
                    checkBoxesAntwort[i].setSelected(false);
                }
            } else {
                textAreasAntwort[i].setText("");
                checkBoxesAntwort[i].setSelected(false);
                checkBoxesAntwort[i].setEnabled(false);
            }
        }

    }

    private void leseModus() {
        modus = 1;
        for (javax.swing.JCheckBox cb : checkBoxesAntwort) {
            cb.setEnabled(false);
        }
        session.geheZu(1);
        fillWithValues();
    }

    private void timerZaehlt() {

        zaehlerLaeuft = true;

        final long start = System.currentTimeMillis();
        final long end = start + timerDauer * 60 * 1000;
        // Setzen Eingabe für Dauer auf 19min fest , Variable ist timerDauer

        final Timer timer = new Timer(1000, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //long zwischen = 0;  
                long now = System.currentTimeMillis();
                if (now >= end) {
                    //remainingMinLabel.setText( "" );
                    labelTimer.setText("");
                    //startButton.setEnabled( true );
                    //JOptionPane.showMessageDialog( null, "BING!" );
                    timer.stop();
                    zaehlerLaeuft = false;

                } else //zwischen = (end-now)/1000; 
                {
                    String countdown = ((end - now) / 60000) + ":";
                    if ((((end - now) / 1000) % 60) > 9) {
                        countdown += (((end - now) / 1000) % 60);
                    } else {
                        countdown += "0" + (((end - now) / 1000) % 60);
                    }
                    labelTimer.setText(countdown);
                }
            }
        });
        timer.start();

    }

    public void cache() {

        for (int i = 0; i < checkBoxesAntwort.length; i++) {
            if (i < session.getAktuelleSitzungsLernKarte().getlK().getpAs().size()) {

                if ((checkBoxesAntwort[i].isSelected() == true)
                        && !session.getAktuelleSitzungsLernKarte().getGegebeneAntworten().contains(session.getAktuelleSitzungsLernKarte().getlK().getpAs().get(i))) {
                    session.getAktuelleSitzungsLernKarte().getGegebeneAntworten().add(session.getAktuelleSitzungsLernKarte().getlK().getpAs().get(i));
                } else if ((checkBoxesAntwort[i].isSelected() == false)
                        && session.getAktuelleSitzungsLernKarte().getGegebeneAntworten().contains(session.getAktuelleSitzungsLernKarte().getlK().getpAs().get(i))) {
                    session.getAktuelleSitzungsLernKarte().getGegebeneAntworten().remove(session.getAktuelleSitzungsLernKarte().getlK().getpAs().get(i));
                }
            }
        }

        session.getAktuelleSitzungsLernKarte().setWiederVorlage((toggleButtonWiedervorlage.isSelected()));

    }

    private static int calcZeilenZahl(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        int zeilen = 1;
        int pos = 0;
        while ((pos = text.indexOf("\n", pos) + 1) != 0) {
            zeilen++;
        }
        return zeilen;
    }

    private static int calcStringHoehe(String text, java.awt.Font font) {
        int hoehe = 0;
        java.awt.FontMetrics metrik = new java.awt.FontMetrics(font) {
        };
        hoehe = metrik.getHeight();
        hoehe *= calcZeilenZahl(text);
        return hoehe;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitel = new javax.swing.JLabel();
        paneFrage = new javax.swing.JScrollPane();
        textAreaFrage = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaAntwortA = new javax.swing.JTextArea();
        labelTimer = new javax.swing.JLabel();
        buttonVor = new javax.swing.JButton();
        buttonZurueck = new javax.swing.JButton();
        buttonGeheZu = new javax.swing.JButton();
        textFieldGeheZu = new javax.swing.JTextField();
        toggleButtonWiedervorlage = new javax.swing.JToggleButton();
        toggleButtonMogeln = new javax.swing.JToggleButton();
        ButtonEnde = new javax.swing.JButton();
        checkBoxA = new javax.swing.JCheckBox();
        jScrollPane9 = new javax.swing.JScrollPane();
        textAreaAntwortB = new javax.swing.JTextArea();
        checkBoxB = new javax.swing.JCheckBox();
        checkBoxC = new javax.swing.JCheckBox();
        jScrollPane10 = new javax.swing.JScrollPane();
        textAreaAntwortC = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        textAreaAntwortD = new javax.swing.JTextArea();
        checkBoxD = new javax.swing.JCheckBox();
        checkBoxE = new javax.swing.JCheckBox();
        jScrollPane12 = new javax.swing.JScrollPane();
        textAreaAntwortE = new javax.swing.JTextArea();
        checkBoxF = new javax.swing.JCheckBox();
        jScrollPane13 = new javax.swing.JScrollPane();
        textAreaAntwortF = new javax.swing.JTextArea();
        checkBoxG = new javax.swing.JCheckBox();
        jScrollPane14 = new javax.swing.JScrollPane();
        textAreaAntwortG = new javax.swing.JTextArea();
        jScrollPane15 = new javax.swing.JScrollPane();
        textAreaAntwortH = new javax.swing.JTextArea();
        checkBoxH = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelTitel.setText("Frage X von Y (ID=Z) Schwierigkeitsgrad=0");
        labelTitel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelTitelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelTitelMouseExited(evt);
            }
        });

        textAreaFrage.setColumns(20);
        textAreaFrage.setRows(5);
        paneFrage.setViewportView(textAreaFrage);

        textAreaAntwortA.setColumns(20);
        textAreaAntwortA.setRows(5);
        jScrollPane1.setViewportView(textAreaAntwortA);

        labelTimer.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelTimer.setText("00:00");

        buttonVor.setText(">>");
        buttonVor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVorActionPerformed(evt);
            }
        });

        buttonZurueck.setText("<<");
        buttonZurueck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonZurueckActionPerformed(evt);
            }
        });

        buttonGeheZu.setText("gehe zu:");
        buttonGeheZu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGeheZuActionPerformed(evt);
            }
        });

        textFieldGeheZu.setText("ID");

        toggleButtonWiedervorlage.setText("Wiedervorlage");
        toggleButtonWiedervorlage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonWiedervorlageActionPerformed(evt);
            }
        });

        toggleButtonMogeln.setText("Antwort anzeigen");
        toggleButtonMogeln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonMogelnActionPerformed(evt);
            }
        });

        ButtonEnde.setText("Beenden");
        ButtonEnde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEndeActionPerformed(evt);
            }
        });

        checkBoxA.setText("A:");

        textAreaAntwortB.setColumns(20);
        textAreaAntwortB.setRows(5);
        jScrollPane9.setViewportView(textAreaAntwortB);

        checkBoxB.setText("B:");

        checkBoxC.setText("C:");

        textAreaAntwortC.setColumns(20);
        textAreaAntwortC.setRows(5);
        jScrollPane10.setViewportView(textAreaAntwortC);

        textAreaAntwortD.setColumns(20);
        textAreaAntwortD.setRows(5);
        jScrollPane11.setViewportView(textAreaAntwortD);

        checkBoxD.setText("D:");

        checkBoxE.setText("E:");

        textAreaAntwortE.setColumns(20);
        textAreaAntwortE.setRows(5);
        jScrollPane12.setViewportView(textAreaAntwortE);

        checkBoxF.setText("F:");

        textAreaAntwortF.setColumns(20);
        textAreaAntwortF.setRows(5);
        jScrollPane13.setViewportView(textAreaAntwortF);

        checkBoxG.setText("G:");

        textAreaAntwortG.setColumns(20);
        textAreaAntwortG.setRows(5);
        jScrollPane14.setViewportView(textAreaAntwortG);

        textAreaAntwortH.setBackground(new java.awt.Color(240, 240, 240));
        textAreaAntwortH.setColumns(20);
        textAreaAntwortH.setRows(5);
        textAreaAntwortH.setBorder(null);
        jScrollPane15.setViewportView(textAreaAntwortH);

        checkBoxH.setText("H:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(labelTitel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelTimer)
                .addGap(77, 77, 77))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(paneFrage, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxD)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxG)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonVor)
                            .addComponent(buttonZurueck))
                        .addGap(64, 64, 64))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(textFieldGeheZu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonGeheZu)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(toggleButtonWiedervorlage, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(toggleButtonMogeln, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(ButtonEnde)
                                .addGap(25, 25, 25)))
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTitel)
                            .addComponent(labelTimer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(paneFrage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(checkBoxA))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(buttonVor)
                        .addGap(18, 18, 18)
                        .addComponent(buttonZurueck)
                        .addGap(18, 18, 18)
                        .addComponent(buttonGeheZu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldGeheZu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(checkBoxB))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(checkBoxC)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(checkBoxD))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(toggleButtonWiedervorlage)
                        .addGap(18, 18, 18)
                        .addComponent(toggleButtonMogeln)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonEnde)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(checkBoxE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(checkBoxF)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(checkBoxG)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(checkBoxH)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGeheZuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGeheZuActionPerformed
        cache();
        session.geheZu(Integer.parseInt(textFieldGeheZu.getText()));
        fillWithValues();
    }//GEN-LAST:event_buttonGeheZuActionPerformed

    private void buttonVorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVorActionPerformed
        cache();
        session.getNextSitzungsLernKarte();
        fillWithValues();
    }//GEN-LAST:event_buttonVorActionPerformed

    private void buttonZurueckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonZurueckActionPerformed
        cache();
        session.getPrevSitzungsLernKarte();
        fillWithValues();
    }//GEN-LAST:event_buttonZurueckActionPerformed

    private void toggleButtonWiedervorlageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonWiedervorlageActionPerformed
        session.getAktuelleSitzungsLernKarte().setWiederVorlage(true);
    }//GEN-LAST:event_toggleButtonWiedervorlageActionPerformed

    private void labelTitelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTitelMouseEntered
        labelTitel.setText(session.getTitelString(1));
    }//GEN-LAST:event_labelTitelMouseEntered

    private void labelTitelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTitelMouseExited
        labelTitel.setText(session.getTitelString(0));
    }//GEN-LAST:event_labelTitelMouseExited

    private void ButtonEndeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEndeActionPerformed
        if (modus == 0) {
            session.speichereInDB();
            leseModus();
        }
    }//GEN-LAST:event_ButtonEndeActionPerformed

    private void toggleButtonMogelnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonMogelnActionPerformed
//        if (toggleButtonMogeln.isSelected()) {
//            session.getAktuelleSitzungsLernKarte().setGemogeltTrue();
//            session.getAktuelleSitzungsLernKarte().setMogelnAktiv(true);
//        } else {
//            session.getAktuelleSitzungsLernKarte().setMogelnAktiv(false);
//        }
        
        checkBoxA.setSelected(true);
        textAreaFrage.setText("Geschummelt!");
        System.out.println("testing");
 

//        session.getAktuelleSitzungsLernKarte().setGemogeltTrue();
//        
//        if (session.getAktuelleSitzungsLernKarte().isMogelnAktiv() == false) {
//            session.getAktuelleSitzungsLernKarte().setMogelnAktiv(true);
//            
//            for (int i = 0; i < session.getAktuelleSitzungsLernKarte().getlK().getpAs().size(); i++) {
//                if (session.getAktuelleSitzungsLernKarte().getlK().getpAs().get(i).isRichtigkeit() == true) {
//                    checkBoxesAntwort[i].setSelected(true);
//                    checkBoxesAntwort[i].setText("Richtig");
//                    textAreasAntwort[i].setBackground(Color.GREEN.brighter());
//                } else {
//                    checkBoxesAntwort[i].setSelected(false);
//                    checkBoxesAntwort[i].setText("Falsch");
//                    textAreasAntwort[i].setBackground(Color.pink);
//                }
//            }
//
//        } else {
//            fillWithValues();
//            for (int i = 0; i < session.getAktuelleSitzungsLernKarte().getlK().getpAs().size(); i++) {
//                checkBoxesAntwort[i].setSelected(false);
//                textAreasAntwort[i].setBackground(Color.white);
//            }
//        }

        
    }//GEN-LAST:event_toggleButtonMogelnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LernenUIMockup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LernenUIMockup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LernenUIMockup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LernenUIMockup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LernenUIMockup().setVisible(true);
            }
        });
    }

    private int modus = 0; //LernModus = 0, LeseModus = 1
    private javax.swing.JCheckBox[] checkBoxesAntwort;
    Boolean zaehlerLaeuft = false;  // gibt an ob der Timr noch läuft
    int timerDauer; // max. Laufzeit des Timers (aus der Vorauswahlmodul in Minuten)
    private javax.swing.JTextArea[] textAreasAntwort;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonEnde;
    private javax.swing.JButton buttonGeheZu;
    private javax.swing.JButton buttonVor;
    private javax.swing.JButton buttonZurueck;
    private javax.swing.JCheckBox checkBoxA;
    private javax.swing.JCheckBox checkBoxB;
    private javax.swing.JCheckBox checkBoxC;
    private javax.swing.JCheckBox checkBoxD;
    private javax.swing.JCheckBox checkBoxE;
    private javax.swing.JCheckBox checkBoxF;
    private javax.swing.JCheckBox checkBoxG;
    private javax.swing.JCheckBox checkBoxH;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel labelTimer;
    private javax.swing.JLabel labelTitel;
    private javax.swing.JScrollPane paneFrage;
    private javax.swing.JTextArea textAreaAntwortA;
    private javax.swing.JTextArea textAreaAntwortB;
    private javax.swing.JTextArea textAreaAntwortC;
    private javax.swing.JTextArea textAreaAntwortD;
    private javax.swing.JTextArea textAreaAntwortE;
    private javax.swing.JTextArea textAreaAntwortF;
    private javax.swing.JTextArea textAreaAntwortG;
    private javax.swing.JTextArea textAreaAntwortH;
    private javax.swing.JTextArea textAreaFrage;
    private javax.swing.JTextField textFieldGeheZu;
    private javax.swing.JToggleButton toggleButtonMogeln;
    private javax.swing.JToggleButton toggleButtonWiedervorlage;
    // End of variables declaration//GEN-END:variables
}
