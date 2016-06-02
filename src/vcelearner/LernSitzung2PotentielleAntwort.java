package vcelearner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Isabel
 */
public class LernSitzung2PotentielleAntwort {

    // Verbindungsvariablen
    static PreparedStatement pst = null;
    static ResultSet rst = null;

    //Variablen/Eigenschaften
    private int lernSitzung_id;
    private int potentielleAntwort_id;

    //Konstruktor:
    public LernSitzung2PotentielleAntwort(int lernSitzung_id,
            int potentielleAntwort_id) {
        this.lernSitzung_id = lernSitzung_id;
        this.potentielleAntwort_id = potentielleAntwort_id;
    }

    //Getter:
    public int getLernSitzung_id() {
        return lernSitzung_id;
    }

    public int getPotentielleAntwort_id() {
        return potentielleAntwort_id;
    }
    
    // Datensätze in die Datenbank schreiben
    public static void insert(LernSitzung2PotentielleAntwort lS2pA) {
        try {
            // VERBINDUNG AUFBBAUEN:
            Connection con = MySQLConnection.getConnection();
            // STATEMENT
            String Sql = "INSERT INTO lernsitzung2potentielleantwort VALUES "
                    + "(?, ?)";
            pst = con.prepareStatement(Sql);
            pst.setInt(1, lS2pA.getLernSitzung_id());
            pst.setInt(2, lS2pA.getPotentielleAntwort_id());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // Datensätze aus der Datenbank auslesen anhand der lernsitzung_id
    public static ArrayList<LernSitzung2PotentielleAntwort>
            getAllByLernSitzung(LernSitzung lernsitzung) {
        ArrayList<LernSitzung2PotentielleAntwort> lS2pAs = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();
            String Sql = "SELECT * FROM lernsitzung2potentielleantwort WHERE "
                    + "lernsitzung_id=?";
            pst = con.prepareStatement(Sql);
            pst.setInt(1, lernsitzung.getId());
            rst = pst.executeQuery();
            while (rst.next()) {
                LernSitzung2PotentielleAntwort lS2pA
                        = new LernSitzung2PotentielleAntwort(rst.getInt(
                                "lernsitzung_id"),
                                rst.getInt("potentielleantwort_id"));
                lS2pAs.add(lS2pA);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return lS2pAs;
    }

    @Override
    public String toString() {
        return "LernSitzung2PotentielleAntwort{" + "lernSitzung_id="
                + lernSitzung_id + ", potentielleAntwort_id="
                + potentielleAntwort_id + '}';
    }
}
