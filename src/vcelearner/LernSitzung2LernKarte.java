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
public class LernSitzung2LernKarte {

    // Verbindungsvariablen
    static PreparedStatement pst = null;
    static ResultSet rst = null;

    //Eigenschaften/Variablen:
    private int lernSitzung_id;
    private int lernkarte_id;
    private boolean gemogelt;

    //Konstruktor
    public LernSitzung2LernKarte(int lernSitzung_id, int lernkarte_id,
            boolean gemogelt) {
        this.lernSitzung_id = lernSitzung_id;
        this.lernkarte_id = lernkarte_id;
        this.gemogelt = gemogelt;
    }

    //Getter:
    public int getLernSitzung_id() {
        return lernSitzung_id;
    }

    public int getLernkarte_id() {
        return lernkarte_id;
    }

    public boolean isGemogelt() {
        return gemogelt;
    }

    // Datensätze in die Datenbank schreiben
    public static void insert(LernSitzung2LernKarte lS2lK) {
        try {
            // VERBINDUNG AUFBBAUEN:
            Connection con = MySQLConnection.getConnection();
            // STATEMENT
            String Sql = "INSERT INTO lernsitzung2lernkarte VALUES (?, ?, ?)";
            pst = con.prepareStatement(Sql);
            pst.setInt(1, lS2lK.getLernSitzung_id());
            pst.setInt(2, lS2lK.getLernkarte_id());
            pst.setString(3, String.valueOf(lS2lK.isGemogelt()));
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
    public static ArrayList<LernSitzung2LernKarte> getAllByLernSitzung(
            LernSitzung lernsitzung) {
        ArrayList<LernSitzung2LernKarte> lS2lKs = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();
            String Sql = "SELECT * FROM lernsitzung2lernkarte WHERE "
                    + "lernsitzung_id=?";
            pst = con.prepareStatement(Sql);
            pst.setInt(1, lernsitzung.getId());
            rst = pst.executeQuery();
            while (rst.next()) {
                LernSitzung2LernKarte lS2lK = new LernSitzung2LernKarte(
                        rst.getInt("lernsitzung_id"),
                        rst.getInt("lernkarte_id"), Boolean.parseBoolean(
                                rst.getString("gemogelt")));
                lS2lKs.add(lS2lK);
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
        return lS2lKs;
    }

    @Override
    public String toString() {
        return "LernSitzung2LernKarte{" + "lernSitzung_id=" + lernSitzung_id
                + ", lernkarte_id=" + lernkarte_id + ", gemogelt=" + gemogelt
                + '}';
    }
}
