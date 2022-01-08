package runningevents1;
/*EXECUTAR
java -classpath build/classes runningevents1.Client localhost 9000
*/
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String args[]) {
	String regHost = "localhost";
	String regPort = "9000";  // porto do binder
	regHost= args[0];
	regPort= args[1];

	try {
	    // objeto que fica associado ao proxy para objeto remoto
	    RunningEvents obj =
	      (RunningEvents) java.rmi.Naming.lookup("rmi://" 
                      + regHost +":"+regPort +"/runningevents");
            
           Scanner sc = new Scanner(System.in);
           
           String evento = null;
           String genero = null;
           String escalao = null;
           String nome = null;
           String data = null;
           String aux = null;
           int tempo;
           int d;
           int horas;
           int minutos;
           int segundos;
           int dorsal;
           String o = null;
           int option = 0;
           LinkedList<String> l = new LinkedList();
           LinkedList<Integer> l1 = new LinkedList();
           
            while (true) {

                System.out.println("------MENU------");
                System.out.println("1- Ver lista de eventos");
                System.out.println("2- Registar novo evento");
                System.out.println("3- Ver lista de participantes de um evento");
                System.out.println("4- Registar um novo participante");
                System.out.println("5- Registar tempo de prova de um participante");
                System.out.println("6- Ver classificação geral");
                System.out.println("7- Ver o top3");
                System.out.println("8- Sair");
                System.out.println("----------------");

                System.out.println("Inserir opção: ");
                o = sc.nextLine();
                option = Integer.parseInt(o);
                System.out.println(" ");

                if (option == 1) { //Ver lista de eventos
                    System.out.println("Lista de eventos: ");
                    l = obj.obterListadeEventos();
                    for (int i = 0; i < l. size(); i++) {
                        System. out. println(l. get(i));
                    }
                    System.out.println(" ");
                    
                }else if(option == 2) {  //Registar novo evento

                    System.out.println("Insira o nome do evento: ");
                    evento = sc.nextLine();
                    System.out.println("Insira a data do evento: ");
                    data = sc.nextLine();
                    obj.registarEvento(evento, data);
                    
                }else if(option == 3) {  //Ver lista de participantes de um evento
                    System.out.println("Insira o nome do evento que quer consultar: ");
                    evento = sc.nextLine();
                    l = obj.listarParticipantes(evento);
                    for (int i = 0; i < l. size(); i++) {
                        System. out. println(l. get(i));
                    }
                    System.out.println(" ");
                    
                }else if(option == 4) {  //Registar um novo participante
                    System.out.print("Insira o nome: ");
                    nome = sc.nextLine();
                    System.out.print("Insira o género: ");
                    genero = sc.nextLine();
                    System.out.print("Insira o escalão: ");
                    escalao = sc.nextLine();
                    System.out.print("Insira o nome do evento: ");
                    evento = sc.nextLine();
                    d = obj.inscreverParticipante(nome, genero, escalao, evento);
                    System.out.println("Ficou com a dorsal nº " + d + " !");
                    System.out.println(" ");
                    
                }else if(option == 5) { //registar tempo
                    l1.clear();
                    System.out.print("Insira a dorsal: ");
                    aux = sc.nextLine();
                    dorsal = Integer.parseInt(aux);
                    System.out.print("Insira o evento: ");
                    evento = sc.nextLine();
                    System.out.print("Insira o género: ");
                    genero = sc.nextLine();
                    System.out.print("Insira o escalão: ");
                    escalao = sc.nextLine();
                    System.out.print("Horas: ");
                    aux = sc.nextLine();
                    horas = Integer.parseInt(aux);
                    l1.add(horas);
                    System.out.print("Minutos: ");
                    aux = sc.nextLine();
                    minutos = Integer.parseInt(aux);
                    l1.add(minutos);
                    System.out.print("Segundos: ");
                    aux = sc.nextLine();
                    segundos = Integer.parseInt(aux);
                    l1.add(segundos);
                    tempo=0;
                    for (int i = 0; i < l1. size(); i++) {
                        if(i == 0){
                            tempo+= (l1. get(i)*60*60);
                        } else if(i == 1){
                            tempo+= (l1. get(i)*60);
                        } else if(i ==2){
                            tempo+= l1. get(i);
                        }
                    }
                    //System.out.println(tempo);
                    obj.registarTempo(dorsal, evento, genero, escalao, tempo);
                    System.out.println(" ");

                }else if(option == 6) {  //Ver classificação geral
                    System.out.print("Insira o evento: ");
                    evento = sc.nextLine();
                    System.out.print("Insira o género: ");
                    genero = sc.nextLine();
                    System.out.print("Insira o escalão: ");
                    escalao = sc.nextLine();
                    l = obj.obterClassificacao(evento,genero, escalao);
                    for (int i = 0; i < l. size(); i++) {
                        System. out. println(l. get(i));
                    }
                    System.out.println(" ");
                    
                }else if(option == 7) {  //Ver o top3
                    System.out.print("Insira o evento: ");
                    evento = sc.nextLine();
                    System.out.print("Insira o género: ");
                    genero = sc.nextLine();
                    System.out.print("Insira o escalão: ");
                    escalao = sc.nextLine();
                    l = obj.obterTop3(evento, genero, escalao);
                    for (int i = 0; i < l. size(); i++) {
                        System. out. println(l. get(i));
                    }
                    System.out.println(" ");

                }else if(option == 8) {
                    sc.close();
                    System.exit(0);   

                }else {

                    System.out.println("Invalid Option");
                }
               System.out.println("Deseja continuar? (y/n)");
               aux = sc.nextLine();
               if(aux.equals("y")){
                   continue;
               } else if(aux.equals("n")){
                   sc.close();
                   System.exit(0); 
               } else{
                   System.out.println("Invalid Option");
               }
            }
	} 
	catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
