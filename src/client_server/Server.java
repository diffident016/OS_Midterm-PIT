/**
 * @author Marvin P. Tagolimot Jr.
 * @section CPE-2A
 * @username diffident016
 */

package client_server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

class ServerThread extends Thread { //ServerThread - a thread that accepts any client that connects to the server.

	private Socket socket = null;
	private ServerSocket server = null;
	
	public boolean sStop = false;
		
    public void run(){
		try {

			server = new ServerSocket(4321); //Starting the server at port: "4321"
						
			while(!sStop){
								
				socket = server.accept();

				DataInputStream input = new DataInputStream(
					new BufferedInputStream(socket.getInputStream())); //Create input stream for the client.
				
				DataOutputStream output = new DataOutputStream(socket.getOutputStream()); //Create output stream for the client.
				
				output.writeUTF(Server.serverName);
								
				String clientName = input.readUTF();
				
				Server.clients[Server.counter] = new ClientMessage(clientName,input, output);
				Server.clients[Server.counter].start();
				Server.counter++;
				
				Server.clientConnect(clientName, output);
			}	
		}
		catch(SocketException se) {
			System.out.println(se);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
    
    public ServerSocket socketServer() {
    	return server;
    }
    
    public Socket socket() {
    	return socket;
    }
    
    public void serverStart() {
    	ServerThread.this.notify();
    }
}

class ClientCheck extends Thread{ //ClientCheck - a thread that detects if client has been disconnected to the server.
	
	public boolean ccStop = false;
	
	public void run() {
		while(!ccStop){
			try{
				if (Server.counter > 0){
					for(int a = 0; a < Server.counter; a++){
						if(Server.clients[a].alive){
							Server.clients[a].alive = false;
						}else{
							Server.clientDisconnect(Server.clients[a].getClient());
							for(int b = a; b < Server.counter; b++){
								Server.clients[b] = Server.clients[b+1];

								if(b == Server.counter-1){
									break;
								}
							}
							Server.counter -= 1;
							break;
						}
					}
				}
				Thread.sleep(1000);
			}catch(Exception e){
				System.out.println(e);
			}
			
		}
	}	
}

class ClientMessage extends Thread{ //ClientMessage - A thread that is receives any client message in real-time.

	private DataInputStream in;
	private DataOutputStream out;
	private String client = null;
	
	public boolean alive = false;
	public boolean cmStop = false;
	
	ClientMessage(String client, DataInputStream in, DataOutputStream out){
		this.client = client;
		this.in = in;
		this.out = out;
		cmStop = false;
	}

	public String getClient(){
		return this.client;
	}
	
	public void sendClient(String message){
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		
		while(!cmStop){
			try{
				String msg = in.readUTF();

				if(msg.equals("/00001234/")){
					alive = true;
				}else{
					Server.echoMessage(client, msg);
					alive = true;
				}
				
			}
			catch(Exception e){
				
			}
		}
	}
	
	public DataInputStream inputStream() {
		return in;
	}
	
	public DataOutputStream outputStream() {
		return out;
	}
}

public class Server{ //Server - A main class that is responsible for starting all the treads and server.

	public static int counter = 0;
	public static String serverName = null;
	
	private static ServerThread serverT = null;
	private static ClientCheck cc = null;
	
	public static ClientMessage clients[] = new ClientMessage[30];
	public static String serverConvo = "";
	
	public Server() {
		serverT = new ServerThread();
		cc = new ClientCheck();
	}


	static void clientConnect(String user, DataOutputStream output){ //This method will be called by Client thread when a client connects to the server.
		try {
			output.writeUTF(Server.serverConvo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerUI.clientCount().setText(String.valueOf(counter));
		
		String msg = user + " has joined the chat.";
		
		echoMessage("Server", msg);
	}

	static void clientDisconnect(String user){ //This method will be called by Client thread when a client disconnects to the server.
		ServerUI.clientCount().setText(String.valueOf(counter-1));
		
		String msg = user + " left the chat.";
		
		echoMessage("Server", msg);
	}

	static void echoMessage(String clientName, String message){ //Send client messages to all online clients including the sender client.
			
		serverConvo += clientName + ": " + message + "\n"; // User: Hello!
		
		for(int a = 0; a < counter; a++) {
			
			try {
				String client = clients[a].getClient();
				
				if(client.equals(clientName)) {
					clients[a].sendClient("You: " + message); // You: Hello!
				}else {
					clients[a].sendClient(clientName + ": " + message); 
				}
			}
			catch(Exception e) {
				
			}			
		}
	}
	
	
	public void serverStop() throws InterruptedException { //A method to stop all threads and close the server.
		
		try {
			serverT.socket().close();
			serverT.socketServer().close();
			serverT.sStop = true;
		} catch (IOException e) {
			
		}
		
		try {
			
			for(int a = 0; a < counter; a++) {
				try {
					clients[a].inputStream().close();
					clients[a].outputStream().close();
					clients[a].interrupt();
					clients[a].cmStop = true;
					clients[a] = null;
				}
				catch(Exception e) {
				
				}			
			}
			
			counter = 0;
			ServerUI.clientCount().setText(String.valueOf(counter));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
		
		serverT.interrupt();	
		cc.ccStop = true;
		cc.interrupt();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		ServerUI.runServer().setEnabled(true);
		ServerUI.stopServer().setEnabled(false);
		
		try {
			ServerUI.serverState().setText("Stopping");
			Thread.sleep(3000);
			ServerUI.serverState().setText("Stopped");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void serverStart() { //A method that is responsible for starting the server.
		
		if(serverT.getState().toString() == "NEW") {
			serverT.start();
			cc.start();		
		}else {
			serverT = new ServerThread();
			cc = new ClientCheck();
			
			serverT.sStop = false;
			cc.ccStop = false;
			serverT.start();
			cc.start();		
		}
		
		try {
			ServerUI.serverState().setText("Starting");
			Thread.sleep(3000);
			ServerUI.serverState().setText("Running");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
}
