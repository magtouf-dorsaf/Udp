package Udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UserUDP {

	public static void main(String[] args) throws Exception {

		DatagramSocket sc = new DatagramSocket();
		InetAddress adr = InetAddress.getLocalHost();
		// InetAddress adr = InetAddress.getByName("192.168.137.1");
		System.out.println("Espace User .. Donner votre nom sous la forme :  ##---");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String pseudo = "";
		do {
			pseudo = in.readLine();
		} while (!pseudo.startsWith("##"));

		System.out.println("Liste des commandes disponible  :  " 
				+ "*** \n"
				+ "  ## : pour creer un nv user inexistant\n"
				+ "****\n"
				+ "  #LIST : pour voir liste des user en cours\n"
				+ "****\n"
				+ "  #Status# : pour changer le status d'un user\n "
				+ "****\n"
				+ "  #HISTO : afficher la liste des msg\n"
				+ "****\n"
				+ "  #*: modifier le pseudo"
				+ "****\n"
				+ "  @#___(1)@#___(2): env un msg(2) a un user(1)"
				+ "****"
				+ "  #SALON#___(1): creer un salon avec un titre(1)"
				+ "****"
				+ "  #SALONS : afficher la liste des salons"
				+ "****"
				+ "  #>___(1): joindre le salon dont le titre est (1)"
				+ "****"
				+ "  #USERS#___(1): afficher les users de la salon (1)"
				+ "****"
				+ "  @>___(1)@>___(2) : env le msg (2) a tous les users de la salon (1)" );
		DatagramPacket pk = new DatagramPacket(pseudo.getBytes(), pseudo.getBytes().length, adr, 5000);
		sc.send(pk);

		//pseudo = pseudo.substring(2, pseudo.length());

		SendUser send = new SendUser(sc, pseudo);
		send.start();

		RecUser rec = new RecUser(sc, pseudo);
		rec.start();

	}

}