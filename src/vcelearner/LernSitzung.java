package vcelearner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Isabel
 */
public class LernSitzung {

    // Verbindungsvariablen
    static Statement st = null;
    static PreparedStatement pst = null;
    static ResultSet rst = null;

    // Variablen/Eigenschaften:
    private int id;
    private String typ;
    private String datum;
    private int benutzer_id;

    //Konstruktoren:
    public LernSitzung(int id, String typ, String datum, int benutzer_id) {
        this.id = id;
        this.typ = typ;
        this.datum = datum;
        this.benutzer_id = benutzer_id;
    }

    public LernSitzung(String typ, int benutzer_id) {
        this.typ = typ;
        this.benutzer_id = benutzer_id;
    }

    //Getter:
    public int getId() {
        return id;
    }

    public String getTyp() {
        return typ;
    }

    public String getDatum() {
        return datum;
    }

    public int getBenutzer_id() {
        return benutzer_id;
    }

    // Setter:
    public void setId(int id) {
        this.id = id;
    }

    // Datensätze in die Datenbank schreiben
    public static void insert(LernSitzung lS) {
        try {
            // VERBINDUNG AUFBBAUEN:
            Connection con = MySQLConnection.getConnection();
            // STATEMENT
            String Sql = "INSERT INTO lernsitzung VALUES (null, ?, "
                    + "CURRENT_TIMESTAMP, ?)";
            pst = con.prepareStatement(Sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, "" + lS.getTyp());
            pst.setInt(2, lS.getBenutzer_id());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            while (rst.next()) {
                lS.setId(rst.getInt(1));
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
    }

    // Alle Datensätze aus der Tabelle lernsitzung in der Datenbank auslesen
    public static ArrayList<LernSitzung> getAll() {
        ArrayList<LernSitzung> lSs = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();
            String sql = "SELECT * FROM lernsitzung";
            st = con.createStatement();
            rst = st.executeQuery(sql);
            while (rst.next()) {
                LernSitzung lS = new LernSitzung(rst.getInt("id"),
                        rst.getString("typ"), rst.getString("datum"),
                        rst.getInt("benutzer_id"));
                lSs.add(lS);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return lSs;
    }

    // Datensätze aus der Datenbank auslesen anhand der benutzer_id
    public static ArrayList<LernSitzung> getAllByBenutzer(Benutzer benutzer) {
        ArrayList<LernSitzung> lSBs = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();
            String sql = "SELECT * FROM lernsitzung WHERE benutzer_id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, benutzer.getId());
            rst = pst.executeQuery(sql);
            while (rst.next()) {
                LernSitzung lS = new LernSitzung(rst.getInt("id"),
                        rst.getString("typ"), rst.getString("datum"),
                        rst.getInt("benutzer_id"));
                lSBs.add(lS);
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
        return lSBs;
    }

    @Override
    public String toString() {
        return "LernSitzung{" + "id=" + id + ", typ=" + typ + ", datum="
                + datum + ", benutzer_id=" + benutzer_id + '}';
    }
}