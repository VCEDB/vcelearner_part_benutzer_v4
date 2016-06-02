package vcelearner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author J.Weidehaas
 */
public class ThemenBereich {

    // Verbindungsvariablen
    //static Connection con = null;
    static Statement st = null;
    static PreparedStatement pst = null;
    static ResultSet rst = null;

    private int id;
    private String bezeichnung;

    public ThemenBereich(int id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }

    public int getId() {
        return id;
    }

    public static ThemenBereich getById(int tBid) {
        ThemenBereich tB = null;
        try {
            
            //con = DriverManager.getConnection("jdbc:mysql://192.168.2.3:3306/vcetrainer","Petra","Panke");
            Connection con = MySQLConnection.getConnection();
            String Sql = "SELECT * FROM themenbereich WHERE id=?";
            pst = con.prepareStatement(Sql);
            pst.setInt(1, tBid);
            rst = pst.executeQuery();
            while (rst.next()) {

                tB = new ThemenBereich(rst.getInt("id"), rst.getString("bezeichnung"));

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
        return tB;
    }

//    @Override
//    public String toString() {
//        return "ThemenBereich{" + "id=" + id + ", bezeichnung=" + bezeichnung + '}';
//    }
    @Override
    public String toString() {
        return  bezeichnung;
    }
}
