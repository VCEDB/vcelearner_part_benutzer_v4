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
public class Benutzer2LernKarte {

    // Verbindungsvariablen
    static Statement st = null;
    static PreparedStatement pst = null;
    static ResultSet rst = null;

    //Variablen/Eigenschaften
    private int benutzer_id;
    private int lernkarte_id;
    private boolean wiedervorlage;

    //Konstruktor:
    public Benutzer2LernKarte(int benutzer_id, int lernkarte_id, boolean wiedervorlage) {
        this.benutzer_id = benutzer_id;
        this.lernkarte_id = lernkarte_id;
        this.wiedervorlage = wiedervorlage;
    }

    public Benutzer2LernKarte(int benutzer_id, int lernkarte_id) {
        this.benutzer_id = benutzer_id;
        this.lernkarte_id = lernkarte_id;
    }

    // Methoden
    public static void insert(Benutzer2LernKarte B2LK) {
        try {
            // VERBINDUNG AUFBBAUEN:
            Connection con = MySQLConnection.getConnection();
            // STATEMENT
            String Sql = "INSERT INTO Benutzer2LernKarte VALUES (?, ?, ?)";
            pst = con.prepareStatement(Sql);
            pst.setInt(1, B2LK.getBenutzer_id());
            pst.setInt(2, B2LK.getLernKarte_id());
            pst.setString(3, String.valueOf(B2LK.isWiedervorlage()));
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

    public static ArrayList<Benutzer2LernKarte> getAllByBenutzer(Benutzer user) {
        ArrayList<Benutzer2LernKarte> users = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();
            String sql = "SELECT * FROM Benutzer2LernKarte WHERE benutzer_id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, user.getId());
            rst = pst.executeQuery();
            while (rst.next()) {
                Benutzer2LernKarte B2LK = new Benutzer2LernKarte(
                        rst.getInt("benutzer_id"),
                        rst.getInt("lernkarte_id"),
                        Boolean.parseBoolean(rst.getString("wiedervorlage"))
                );
                users.add(B2LK);
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
        return users;
    }

    public static void delete(Benutzer user, LernKarte lK) {
        // Prüfung, ob Wiedervorlage zur LernKarte vorhanden ist
        if (checkWiedervorlage(new Benutzer2LernKarte(user.getId(), lK.getId()))) {
            try {
                Connection con = MySQLConnection.getConnection();
                String Sql = "DELETE FROM Benutzer2LernKarte WHERE benutzer_id=? AND lernkarte_id=?";
                pst = con.prepareStatement(Sql);
                pst.setInt(1, user.getId());
                pst.setInt(2, lK.getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
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
    }

    public static boolean checkWiedervorlage(Benutzer2LernKarte b2lk) {
        boolean chkWiedevorlage = false;
        for (Benutzer2LernKarte alleB2lk : Benutzer2LernKarte.getAll()) {
            if (b2lk.getBenutzer_id() == alleB2lk.getBenutzer_id()
                    && b2lk.getLernKarte_id() == alleB2lk.getLernKarte_id()) {
                chkWiedevorlage = true;
                break;
            }
        }
        return chkWiedevorlage;
    }

    public static ArrayList<Benutzer2LernKarte> getAll() {
        ArrayList<Benutzer2LernKarte> b2lkList = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();
            String sql = "SELECT * FROM Benutzer2LernKarte";
            st = con.createStatement();
            rst = st.executeQuery(sql);
            while (rst.next()) {
                Benutzer2LernKarte b2lk = new Benutzer2LernKarte(rst.getInt("benutzer_id"),
                        rst.getInt("lernkarte_id"),
                        Boolean.parseBoolean(rst.getString("wiedervorlage"))
                );
                b2lkList.add(b2lk);
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
        return b2lkList;
    }

    //Getter:
    public int getBenutzer_id() {
        return benutzer_id;
    }

    public int getLernKarte_id() {
        return lernkarte_id;
    }

    public boolean isWiedervorlage() {
        return wiedervorlage;
    }

    @Override
    public String toString() {
        return "Benutzer2LernKarte{" + "benutzer_id=" + benutzer_id + ", lernkarte_id=" + lernkarte_id + ", wiedervorlage=" + wiedervorlage + '}';
    }

    /**
     * public static Benutzer2LernKarte getBenutzer2LernKarte(Benutzer2LernKarte
     * b2lk){ Benutzer2LernKarte neub2lk = null; try { Connection con =
     * MySQLConnection.getConnection(); String sql = "SELECT * FROM
     * Benutzer2LernKarte WHERE benutzer_id=? AND lernkarte_id=?"; pst =
     * con.prepareStatement(sql); pst.setInt(1, b2lk.benutzer_id); pst.setInt(2,
     * b2lk.lernkarte_id); rst = pst.executeQuery(); while (rst.next()) {
     * neub2lk = new Benutzer2LernKarte(rst.getInt("benutzer_id"),
     * rst.getInt("lernkarte_id"),
     * Boolean.parseBoolean(rst.getString("wiedervorlage"))); } } catch
     * (SQLException ex) { System.out.println(ex.getMessage());
     * ex.printStackTrace(); } finally { try { if (st != null) { st.close(); }
     * if (rst != null) { rst.close(); } } catch (SQLException ex) {
     * System.out.println(ex.getMessage()); } }
     *
     * return neub2lk; }
     */
}
