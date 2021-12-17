package Udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RecUser extends Thread {
	DatagramSocket ac;
	String pseudo;
	
	public RecUser(DatagramSocket ac, String pseudo) {
		super();
		this.ac = ac;
		this.pseudo = pseudo;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				DatagramPacket pk = new DatagramPacket(new byte[1024], 1024);
				ac.receive(pk);
				String message = new String (pk.getData());
				System.out.println(message);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

}