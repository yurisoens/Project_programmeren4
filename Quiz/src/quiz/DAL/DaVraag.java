/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import quiz.BO.Vraag;

/**
 *
 * @author yuri
 */
public class DaVraag {
    
    public static Connection GetConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:mysql://localhost:3307/QuizDB","root","usbw");
    }
    
    public List<Vraag> GetAll()
    {
        ArrayList<Vraag> antwoord = new ArrayList<>();
        
        try 
        {
            Connection conn = GetConnection();
            String sql="SELECT * FROM tbl_vraag";
                              
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Vraag v = new Vraag(
                        rs.getInt("ID"), 
                        rs.getString("vraag"), 
                        rs.getString("antwoord"), 
                        rs.getString("categorie"),  
                        rs.getInt("juisteAntwoorden"),
                        rs.getInt("aantalGesteld"),
                        rs.getDouble("moeilijkheidsgraad"),
                        rs.getString("extraInfo"),
                        rs.getString("media"),
                        rs.getString("type"));
                
                antwoord.add(v);
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return antwoord;
    }
    
    public static void delete(Vraag v)
    {
            try
        {
            Connection conn = GetConnection();
            String sql="DELETE FROM tbl_vraag WHERE ID=?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, v.getID());
            
            int result = stmt.executeUpdate();
            
            if (result==0) 
            {
                System.out.println("geen update/insert gebeurd");
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public static void save (Vraag v)
    {
        try 
        {
            Connection conn = GetConnection();
            String sql="";
            if (v.getID()==0) sql = "INSERT INTO tbl_vraag(id,vraag,antwoord,categorie,juisteAntwoorden,aantalGesteld,moeilijkheidsgraad,extraInfo, media, type) VALUES (?,?,?,?,?,?,?,?,?,?)";
            else    sql="UPDATE tbl_vraag SET ID=?,vraag=?,antwoord=?,categorie=?,juisteAntwoorden=?,aantalGesteld=?, moeilijkheidsgraad=?, extraInfo=?, media=?, type=? WHERE ID=?";
                                
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, v.getID());
            stmt.setString(2, v.getVraag());
            stmt.setString(3, v.getAntwoord());
            stmt.setString(4, v.getCategorie());
            stmt.setInt(5, v.getJuisteAntwoorden());
            stmt.setInt(6, v.getAantalGesteld());
            stmt.setDouble(7, v.getMoeilijkheidsgraad());
            stmt.setString(8, v.getExtraInfo());
            stmt.setString(9, v.getMedia());
            stmt.setString(10, v.getType());
            
            int result = stmt.executeUpdate();
            
            if (result==0) 
            {
                System.out.println("geen update/insert gebeurd");
            }         
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
}
