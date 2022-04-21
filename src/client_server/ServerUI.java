/**
 * @author Marvin P. Tagolimot Jr.
 * @section CPE-2A
 * @username diffident016
 */

package client_server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JLabel lblNewLabel_1;
	private static JLabel lblNewLabel_3;
	private static JButton btnNewButton_1;
	private static JButton btnNewButton_2;
	private static JTextPane txtpnTypeServerName;
	private static JButton btnNewButton;
	
	public Server server;
	private static JLabel lblNewLabel_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI frame = new ServerUI();
					frame.setVisible(true);
															
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public ServerUI() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SERVER NAME: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(44, 32, 124, 17);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Server 0");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(145, 29, 72, 22);
		contentPane.add(lblNewLabel_1);
		
		txtpnTypeServerName = new JTextPane();
		txtpnTypeServerName.setBounds(44, 82, 180, 20);
		contentPane.add(txtpnTypeServerName);
		
		JLabel lblNewLabel_2 = new JLabel("Set server name");
		lblNewLabel_2.setBounds(44, 60, 124, 14);
		contentPane.add(lblNewLabel_2);
		
		btnNewButton = new JButton("Set");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String input = getServerName().getText();
				
				if(input.length() < 1) {
					JOptionPane.showMessageDialog(lblNewLabel, "Please input a server name.");
				}else {
					serverName().setText(input);
					getServerName().setText("");
					JOptionPane.showMessageDialog(lblNewLabel, "Server name has been changed.");
				}
			}
		});
		btnNewButton.setBounds(91, 113, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblServerInfo = new JLabel("STATUS: ");
		lblServerInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblServerInfo.setBounds(44, 155, 58, 17);
		contentPane.add(lblServerInfo);
		
		lblNewLabel_3 = new JLabel("Not running");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(110, 152, 83, 24);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblClients = new JLabel("CLIENTS: ");
		lblClients.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblClients.setBounds(44, 183, 62, 17);
		contentPane.add(lblClients);
		
		lblNewLabel_4 = new JLabel("0");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(110, 183, 46, 18);
		contentPane.add(lblNewLabel_4);
		
		btnNewButton_1 = new JButton("Start");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				server = new Server();
				
				System.out.print(serverName().getText());			
				Server.serverName = serverName().getText();
				server.serverStart();
				runServer().setEnabled(false);
				stopServer().setEnabled(true);
				
			}
		});
		btnNewButton_1.setBounds(44, 211, 83, 23);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Stop");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					server.serverStop();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}							
			}
		});
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.setBounds(145, 211, 83, 23);
		contentPane.add(btnNewButton_2);
	}
	
	
	public static JLabel serverName() {
		return lblNewLabel_1;
	}
	public static JLabel serverState() {
		return lblNewLabel_3;
	}
	public static JButton runServer() {
		return btnNewButton_1;
	}
	public static JButton stopServer() {
		return btnNewButton_2;
	}
	public static JTextPane getServerName() {
		return txtpnTypeServerName;
	}
	public static JButton setSNameBtn() {
		return btnNewButton;
	}
	public static JLabel clientCount() {
		return lblNewLabel_4;
	}
}
