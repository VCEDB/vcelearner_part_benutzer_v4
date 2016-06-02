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
public class Benutzer {
    
    // Verbindungsvariablen
    static Statement st = null;
    static PreparedStatement pst = null;
    static ResultSet rst = null;
    
    private int id;
    private String login, passwort, vorname, nachname;

    public Benutzer(String login, String passwort) {
        this.login = login;
        this.passwort = passwort;
    }

    public Benutzer(int id, String login, String passwort, String vorname, String nachname) {
        this.id = id;
        this.login = login;
        this.passwort = passwort;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public Benutzer(String login, String passwort, String vorname, String nachname) {
        this.login = login;
        this.passwort = passwort;
        this.vorname = vorname;
        this.nachname = nachname;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswort() {
        return passwort;
    }
    
    public static void insert(Benutzer user){
        try {
            // VERBINDUNG AUFBBAUEN:
            Connection con = MySQLConnection.getConnection();
            // STATEMENT
            String Sql = "INSERT INTO Benutzer VALUES (null, ?, ?, ?, ?)";
            pst = con.prepareStatement(Sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getPasswort());
            pst.setString(3, user.getVorname());
            pst.setString(4, user.getNachname());
            pst.executeUpdate();
            rst = pst.getGeneratedKeys();
            while (rst.next()) {
                user.setId(rst.getInt(1));
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
    
    public static Benutzer getBenutzer(Benutzer user){
        Benutzer newUser = null;
        try {
            Connection con = MySQLConnection.getConnection();
            String sql = "SELECT * FROM Benutzer WHERE login=? AND passwort=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getPasswort());
            rst = pst.executeQuery();
            while (rst.next()) {
               newUser = new Benutzer(rst.getInt("id"), 
                            rst.getString("login"), 
                            rst.getString("passwort"),
                            rst.getString("vorname"),
                            rst.getString("nachname"));              
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
        return newUser;        
    }   
    
    public static ArrayList<Benutzer> getAlleBenutzer(){        
        ArrayList<Benutzer> users = new ArrayList<>();        
        try {            
            Connection con = MySQLConnection.getConnection();
            String sql = "SELECT login, passwort FROM Benutzer";
            st = con.createStatement();
            rst = st.executeQuery(sql);            
            while (rst.next()) {
                Benutzer user = new Benutzer(rst.getString("login"), 
                                             rst.getString("passwort")
                );
                users.add(user);               
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
        return users;        
    }   
    
    public static Benutzer loginCheck(Benutzer user) {        
        Benutzer checkedUser = new Benutzer(0,"login",
                                             "passwort",
                                             "vorname",
                                             "nachname"
        );
        for (Benutzer alleBenutzer : Benutzer.getAlleBenutzer()) {
            if (user.getLogin().equals(alleBenutzer.getLogin()) && 
                user.getPasswort().equals(alleBenutzer.getPasswort())) {               
                checkedUser = Benutzer.getBenutzer(user);
                break;                
            } 
        }        
        return checkedUser;
    }

    @Override
    public String toString() {
        return "Benutzer{" + "id=" + id + ", login=" + login + ", passwort=" + passwort + ", vorname=" + vorname + ", nachname=" + nachname + '}';
    }            
}
