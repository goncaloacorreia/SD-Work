package runningevents1;

import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Impl extends UnicastRemoteObject implements RunningEvents, java.io.Serializable {
    static Statement stmt;
    // deve existir um construtor
    public Impl(Statement stmt) throws java.rmi.RemoteException {
        this.stmt = stmt;
        
    }
    LinkedList<String> list = new LinkedList();
    int x;
    int dorsal;
    
    public LinkedList<String> obterListadeEventos() throws java.rmi.RemoteException {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nome, data from evento");
            list.clear();
            while (rs.next())
                {
                list.add(String.format("Nome: %s | Data: %s",rs.getString(1), rs.getString(2)));
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void registarEvento(String nome, String data) throws java.rmi.RemoteException {
        try {
            stmt.executeUpdate("INSERT INTO evento VALUES('" + nome + "','"+ data +"')"); 
        } catch (SQLException ex) {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public LinkedList<String> listarParticipantes(String evento) throws java.rmi.RemoteException {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nome, dorsal from participante WHERE evento ='"+ evento +"'");
            list.clear();
            while (rs.next())
                {
                list.add(String.format("Dorsal: %d | Nome: %s",rs.getInt(2), rs.getString(1)));
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public int inscreverParticipante(String nome, String genero, String escalao, String evento) throws java.rmi.RemoteException {
        try {
            stmt.executeUpdate("INSERT INTO participante VALUES(DEFAULT, '" + nome + "','"+ genero +"','"+ escalao +"','"+ evento +"')");
            ResultSet rs = stmt.executeQuery("SELECT dorsal from participante WHERE(nome = '"+ nome +"')");
            while (rs.next())
                {
                dorsal = rs.getInt(1);
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dorsal;
    }
    
    public void registarTempo(int dorsal, String evento, String genero, String escalao, int tempo) throws java.rmi.RemoteException {
        try {
            stmt.executeUpdate("INSERT INTO tempo VALUES('" + dorsal + "','"+ evento +"','"+ tempo +"','"+ genero +"','"+ escalao +"')"); 
        } catch (SQLException ex) {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public LinkedList<String> obterClassificacao(String evento, String genero, String escalao) throws java.rmi.RemoteException {
        int h;
        int m;
        int s;
        int d;
        list.clear();
        try {
            ResultSet rs = stmt.executeQuery("SELECT dorsal, tempo from tempo WHERE (evento ='"+ evento +"' AND genero ='"+ genero +"' AND escalao ='"+ escalao +"') ORDER BY tempo ASC"); 
           
            while (rs.next())
                {
                d = rs.getInt(1);
                x = rs.getInt(2);
                h = x / 3600;
                m = (x % 3600) / 60;
                s = x % 60;

                list.add(String.format("Dorsal: %d | Tempo: %02d:%02d:%02d",d, h, m, s));
                }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public LinkedList<String> obterTop3(String evento, String genero, String escalao) throws java.rmi.RemoteException {
        int h;
        int m;
        int s;
        int d;
        list.clear();
        try {
            ResultSet rs = stmt.executeQuery("SELECT dorsal, tempo from tempo WHERE (evento ='"+ evento +"' AND genero ='"+ genero +"' AND escalao ='"+ escalao +"') ORDER BY tempo ASC"); 
            int count=0;
            while (count <3)
                {
                rs.next();
                d = rs.getInt(1);
                x = rs.getInt(2);
                h = x / 3600;
                m = (x % 3600) / 60;
                s = x % 60;
                list.add(String.format("Dorsal: %d | Tempo: %02d:%02d:%02d",d, h, m, s));
                count++;
                }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
}

