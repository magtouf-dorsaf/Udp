package Udp;

import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Vector;


public class Server {
	static Vector<User> liste;
	static Vector<Message> listeMessage;
	static Vector<Salon> listeSalon;

	public static void main(String[] args) throws Exception {
		liste = new Vector<User>();
		listeMessage = new Vector<Message>();
		listeSalon = new Vector<Salon>();
		System.out.println("Env de chat creer");

		DatagramSocket sc = new DatagramSocket(5000);
		
		Date today = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");  

		String strDate = dateFormat.format(today);

		while (true) {
			try {
				// PrintWriter out_socket = new PrintWriter(sc.getOutputStream(),true);//true
				// pour fermer a chaque envoi

				DatagramPacket pk = new DatagramPacket(new byte[1024], 1024);
				sc.receive(pk);

				InetAddress adr = pk.getAddress();
				int port = pk.getPort();

				String msg = new String(pk.getData(), 0, pk.getLength()); // 2eme cons de String
				// String currentPseudo;
				// User currentUser = null;
				if (msg.startsWith("##")) {
					String pseudo = msg.substring(2, msg.length());

					boolean test = false;
					for (User u : Server.liste) {
						if (u.getPseudo().equals(pseudo)) {
							test = true;

						}
					}
					if (test == true) {
						String msg2 = "Ce nom Existe deja..tu dois le changer";
						DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
						sc.send(pk2);
					} else {
						Server.liste.add(new User(pseudo, adr, port, "en ligne"));
						
						String msg2 = strDate+ "  BIENVENUE ";
						DatagramPacket pk2 = new DatagramPacket(msg2.getBytes(), msg2.getBytes().length, adr, port);
						sc.send(pk2);
					}
				} else if (msg.startsWith("#LIST")) {
					String text = "";
					for (User u : Server.liste) {
						text += "Pseuso: " + u.getPseudo() + "//" + "Adress: " + u.getAdress() + "//" + "Port: "
								+ u.getPort() + "//" + "status: " + u.getStatus() + "  ****  ";
					}
					DatagramPacket pk2 = new DatagramPacket(text.getBytes(), text.getBytes().length, adr, port);
					sc.send(pk2);
				} else if (msg.startsWith("#STATUS#")) {
					String status = msg.substring(8, msg.length());
					for (User u : Server.liste) {
						if (u.getPort() == port) {
							u.setStatus(status);
						}
					}
				} else if (msg.startsWith("#HISTO")) {
					String text = "";
					for (Message m : Server.listeMessage) {
						text += "Contenue : " + m.getContenue() + "//" + " envoyée à: " + m.getReceive().getPseudo()
								+ "  ****  ";
					}
					DatagramPacket pk2 = new DatagramPacket(text.getBytes(), text.getBytes().length, adr, port);
					sc.send(pk2);
				} else if (msg.startsWith("#*")) {
					String newPseudo = msg.substring(2, msg.length());
					for (User u : Server.liste) {
						if (u.getPort() == port) {
							u.setPseudo(newPseudo);
						}
					}
				} else if (msg.startsWith("@#")) {
					String tab[] = msg.split("@#");

					User u2 = null;
					User send = new User();

					for (User u : Server.liste) {
						if (u.getPort() == port) {
							send = u;
						}
					}

					for (User u : Server.liste) {
						if (u.getPseudo().equals(tab[1])) {
							u2 = u;
						}
					}
					Message m = new Message(send, u2, tab[2]);
					Server.listeMessage.add(m);
					String msgFromUser = " msg From:  " + send.getPseudo() + " /msg:  " + tab[2] +" /à la date: "+ strDate;
					DatagramPacket pk2 = new DatagramPacket(msgFromUser.getBytes(), msgFromUser.getBytes().length,
							u2.getAdress(), u2.getPort());
					sc.send(pk2);
				} else if (msg.startsWith("#SALON#")) {
					String tab[] = msg.split("#");
					String salon = tab[2];
					Vector<User> vu = null;
					Vector<Message> mu = null;

					Salon s = new Salon(salon);
					Server.listeSalon.add(s);
				} else if (msg.startsWith("#SALONS")) {
					String text = "";
					for (Salon s : Server.listeSalon) {
						String urs = "";
						String msgg = "";

						for (User u : s.getUsers()) {
							urs += u.getPseudo() + "++";

						}

						for (Message m : s.getMessages()) {
							msgg += m.getContenue() + "++";

						}
						text += "Nom: " + s.getName() + ";" + "  Users: " + urs + ";" + "  Message: " + msgg + "****";
					}
					DatagramPacket pk2 = new DatagramPacket(text.getBytes(), text.getBytes().length, adr, port);
					sc.send(pk2);
				} else if (msg.startsWith("#>")) {
					String titreSalon = msg.substring(2, msg.length());
					for (Salon s : Server.listeSalon) {
						if (titreSalon.equals(s.getName())) {
							// Vector<User> vu=null;
							for (User u : Server.liste) {
								if (u.getPort() == port) {
									s.addUsers(u);
								}
							}
						}
					}
				} else if (msg.startsWith("#USERS#")) {
					String text = "";
					String tab[] = msg.split("#");
					String titreSalon = tab[2];
					for (Salon s : Server.listeSalon) {
						String urs = "";
						if (titreSalon.equals(s.getName())) {
							for (User u : s.getUsers()) {
								urs += u.getPseudo() + "++";
							}
						}
						text += "Nom de Salon: " + s.getName() + ";" + "  Users de Salon:: " + urs + ";" + "****";
					}
					DatagramPacket pk2 = new DatagramPacket(text.getBytes(), text.getBytes().length, adr, port);
					sc.send(pk2);
				} else if (msg.startsWith("@>")) {
					String tab[] = msg.split("@>");
					String titreSalon = tab[1];
					String contenuMsgSalon = tab[2];
					User us = new User();
					Message msgSalon = new Message();
					msgSalon.setContenue(contenuMsgSalon);
					for (Salon s : Server.listeSalon) {
						if (titreSalon.equals(s.getName())) {
							for (User u : s.getUsers()) {
								if (u.getPort() == port) {
									us = u;
								}
							}

							for (User u : s.getUsers()) {
								msgSalon.setReceive(u);
								DatagramPacket pk2 = new DatagramPacket(contenuMsgSalon.getBytes(),
										contenuMsgSalon.getBytes().length, u.getAdress(), u.getPort());
								sc.send(pk2);
							}
							s.addMessage(msgSalon);
						}

//						if (titreSalon.equals(s.getName())) {
//							s.addMessage(msgSalon);
//							for (User u : s.getUsers()) {
//								DatagramPacket pk2 = new DatagramPacket(contenuMsgSalon.getBytes(),
//										contenuMsgSalon.getBytes().length, u.getAdress(), u.getPort());
//								sc.send(pk2);
//							}
//						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}