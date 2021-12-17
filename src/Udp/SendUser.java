package Udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUser extends Thread {

	DatagramSocket ac;
	String pseudo;

	public SendUser(DatagramSocket ac, String pseudo) {
		super();
		this.ac = ac;
		this.pseudo = pseudo;
	}

	public void run() {
		try {
			while (true) {
				InetAddress adr = InetAddress.getLocalHost();
				// InetAddress adr = InetAddress.getByName("192.168.137.1");  bch tconnecti a distance "en tant que client" 

				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				// System.out.println("fl SendETD");
				String msg = in.readLine();

				DatagramPacket pk = new DatagramPacket(msg.getBytes(), msg.getBytes().length, adr, 5000);
				ac.send(pk);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}