package runningevents1;

import java.util.LinkedList;
import java.util.List;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface RunningEvents extends java.rmi.Remote{
    
    public void registarEvento(String nome, String data) throws java.rmi.RemoteException;
    
    public LinkedList<String> obterListadeEventos() throws java.rmi.RemoteException;

    public int inscreverParticipante(String nome, String genero, String escalao, String evento) throws java.rmi.RemoteException;
    
    public LinkedList<String> listarParticipantes(String evento) throws java.rmi.RemoteException;
    
    public void registarTempo(int dorsal, String evento, String genero, String escalao, int tempo) throws java.rmi.RemoteException;   
    
    public LinkedList<String> obterClassificacao(String evento, String genero, String escalao) throws java.rmi.RemoteException;
    
    public LinkedList<String> obterTop3(String evento, String genero, String escalao) throws java.rmi.RemoteException;
    
}
