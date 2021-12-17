package Udp;

import java.util.Vector;

public class Salon {
	private String name;
	private Vector<User> users;
	private Vector<Message> messages;

	public Salon() {
		super();
		users = new Vector<User>();
		messages = new Vector<Message>();
	}

	public Salon(String name) {
		super();
		this.name = name;
		users = new Vector<User>();
		messages = new Vector<Message>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<User> getUsers() {
		return users;
	}

	public void setUsers(Vector<User> users) {
		this.users = users;
	}

	public Vector<Message> getMessages() {
		return messages;
	}

	public void setMessages(Vector<Message> messages) {
		this.messages = messages;
	}

	public void addUsers(User u) {
		this.users.add(u);
	}

	public void addMessage(Message m) {
		this.messages.add(m);
	}

}
