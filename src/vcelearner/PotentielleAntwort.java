package vcelearner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author J.Weidehaas
 */
public class PotentielleAntwort {

    // Verbindungsvariablen
 
    static Statement st = null;
    static PreparedStatement pst = null;
    static ResultSet rst = null;

    private int id;
    private boolean richtigkeit;
    private String antwort;
    private int lernKarte_id;

    public PotentielleAntwort(boolean richtigkeit, String antwort, int lernKarte_id) {
        this.antwort = antwort;
        this.richtigkeit = richtigkeit;
        this.lernKarte_id = lernKarte_id;
    }

    public PotentielleAntwort(boolean richtigkeit, String antwort) {
        this.richtigkeit = richtigkeit;
        this.antwort = antwort;
    }

    public int getId() {
        return id;
    }

    public String getAntwort() {
        return antwort;
    }

    public boolean isRichtigkeit() {
        return richtigkeit;
    }

    public int getLernKarte_id() {
        return lernKarte_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLernKarte_id(int lernKarte_id) {
        this.lernKarte_id = lernKarte_id;
    }
    

    public static void insert(PotentielleAntwort pA) {
        try {
            // VERBINDUNG AUFBBAUEN:
            Connection con = MySQLConnection.getConnection();
            // STATEMENT
            String Sql = "INSERT INTO potentielleantwort VALUES (null, ?, ?, ?)";
            pst = con.prepareStatement(Sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, String.valueOf(pA.isRichtigkeit())); //db erwartet String in ENUM-Feld
            pst.setString(2, pA.getAntwort());
            pst.setInt(3, pA.getLernKarte_id());

            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            while (rst.next()) {
                pA.setId(rst.getInt(1));
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

    public static void delete(int lKid) {

        try {

            Connection con = MySQLConnection.getConnection();
            String Sql = "DELETE FROM potentielleantwort WHERE lernkarte_id=?";
            pst = con.prepareStatement(Sql);
            pst.setInt(1, lKid);

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
// public static ArrayList<PotentielleAntwort> getAll() {
//        ArrayList<PotentielleAntwort> pAs = new ArrayList<>();
//        try {
//            // VERBINDUNG AUFBBAUEN:
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vcelearner", "root", "");
//            // STATEMENT
//            String Sql = "SELECT * FROM potentielleantwort";
//            pst = con.prepareStatement(Sql);
//         
//            rst = st.executeQuery(Sql);
//            while (rst.next()) {
//
//                PotentielleAntwort pA = new PotentielleAntwort(rst.getBoolean("richtigkeit"), rst.getString("antwort"), rst.getInt("lernkarte_id"));
//                pAs.add(pA);
//
//            }
//
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//                if (pst != null) {
//                    pst.close();
//                }
//                if (rst != null) {
//                    rst.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//
//        }
//        return pAs;
//    } 

    public static ArrayList<PotentielleAntwort> getAllByLernKarte_id(int lKid) {
        ArrayList<PotentielleAntwort> pAs = new ArrayList<>();
        try {
            Connection con = MySQLConnection.getConnection();
            String sql = "SELECT * FROM potentielleantwort WHERE lernkarte_id=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, lKid);
            rst = pst.executeQuery();
            while (rst.next()) {

                PotentielleAntwort pA = new PotentielleAntwort(
                        Boolean.parseBoolean(rst.getString("richtigkeit")),
                        rst.getString("antwort"),
                        rst.getInt("lernkarte_id"));
                pA.setId(rst.getInt("id"));
                pAs.add(pA);

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
        return pAs;
    }

//    @Override
//    public String toString() {
//        return "PotentielleAntwort{" + "id=" + id + ", richtigkeit=" + richtigkeit + ", antwort=" + antwort + ", lernKarte_id=" + lernKarte_id + '}';
//    }
    @Override
    public String toString() {
        return antwort + "/" + richtigkeit;
    }

}
