package Udp;

public class Message {

	private User envoie;
	private User receive;
	private String contenue;

	public Message(String contenu) {
		super();
		this.contenue = contenue;
	}

	public Message() {
		super();
	}

	public Message(User envoie, User receive, String contenue) {
		super();
		this.envoie = envoie;
		this.receive = receive;
		this.contenue = contenue;
	}

	public User getEnvoie() {
		return envoie;
	}

	public void setEnvoie(User envoie) {
		this.envoie = envoie;
	}

	public User getReceive() {
		return receive;
	}

	public void setReceive(User receive) {
		this.receive = receive;
	}

	public String getContenue() {
		return contenue;
	}

	public void setContenue(String contenue) {
		this.contenue = contenue;
	}

}