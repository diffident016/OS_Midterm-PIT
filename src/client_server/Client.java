/**
 * @author Marvin P. Tagolimot Jr.
 * @section CPE-2A
 * @username diffident016
 */

package client_server;

import java.net.*;
import java.io.*;

class ClientPresence extends Thread{ //ClientPresence - a thread to signal the server about client's presence.
	public void run(){
		while(true){
			
			try{
				Client.out.writeUTF("/00001234/");
				
				if(ClientUI.serverStatus().getText().equals("Disconnected")) {
					ClientUI.setServerStatus(true);
					ClientUI.reconnectBtn().setVisible(false);
				}
				
				Thread.sleep(100);
			}catch(IOException i){
				try {
					ClientUI.setServerStatus(false);
					ClientUI.reconnectBtn().setVisible(true);
					ClientPresence.interrupted();
					break;
				}catch(Exception e) {
					
				}
			}catch(Exception e) {
				
			}
		}
	}
}

class ReceiveMessage extends Thread{ //ReceiveMessage - a thread for receiving real-time message from the server.	
	public void run(){
		while(true){
			try{
				
				if(!Client.connected) {
					ReceiveMessage.interrupted();
					break;
				}
				
				String message = Client.serverInput.readUTF();
						
				if(message.length() < 1) {
					message = "";
				}else {
					message += "\n";				
				}
				
				Client.serverConvo += message;
				
				ClientUI.chatArea().setText(Client.serverConvo);
				Thread.sleep(20);
			}catch(Exception e){
	
			}
		}
	}
}

public class Client{ //A main client main class responsible for starting threads and sending the message to the server.
	
	private Socket socket = null;
	public static DataInputStream serverInput = null;
	public static DataOutputStream out = null;
	
	private static String serverName = null;
	private static String userName = null;
	
	public static String serverConvo = "";
	public static boolean connected = false;
	
	public boolean joinServer(String username) {
		
		boolean confirm = false;
		
		try{
			socket = new Socket("127.0.0.1", 4321); //Connect to local server via port "4321".

			out = new DataOutputStream(socket.getOutputStream()); //Output stream for sending a data to the server.

			out.writeUTF(username);
			
			userName = username;
			
			serverInput = new DataInputStream(
					new BufferedInputStream(socket.getInputStream())); //Input stream for receiving data from the server.

			while(true) {
				if(serverInput != null) {
					serverName = serverInput.readUTF();
					connected = true;
					break;
				}
			}
			
			ClientPresence cp = new ClientPresence();
			cp.start();
			confirm = true;
			
			ReceiveMessage rm = new ReceiveMessage();
			rm.start();
			
		}catch(UnknownHostException u){
			System.out.println(u);
		}catch(IOException i){
			System.out.println(i);
		}
		
		return confirm;
		
	}
	
	public void sendMessage(String message){ // Method for getting the user input and sending to server.
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUI() { // Method to update some information inside the client UI.
		ClientUI.setServerName(serverName);
		ClientUI.setServerStatus(true);
		ClientUI.setTitle(userName);
		ClientUI.chatArea().setText(serverConvo);
	}
	
}
