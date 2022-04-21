/**
 * @author Marvin P. Tagolimot Jr.
 * @section CPE-2A
 * @username diffident016
 */

package client_server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Dimension;

public class ClientUI{

	private static JPanel contentPane;
	private static JTextField textField;
	private static JFrame preFrame;
	private static JFrame chatFrame;
	private static JTextField textField_1;
	private static JLabel lblNewLabel_6;
	private static JLabel lblNewLabel_7;
	private static JLabel lblNewLabel_9;
	private static JButton btnNewButton_1;
	private static JTextArea textArea;
	private static Client client;
	private static JButton btnNewButton_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ClientUI.display1();
                
                client = new Client();
            }
        });
	}

	/**
	 * Create the frame.
	 */
	public static void display1() {		
		preFrame = new JFrame();
		preFrame.setTitle("Client");
		preFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		preFrame.setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		preFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter your username");
		lblNewLabel.setBounds(50, 57, 118, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(50, 74, 184, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Set username");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String username = textField.getText();
				if(username.isEmpty()) {
					JOptionPane.showMessageDialog(btnNewButton, "Please type your username.");
				}else {
					userName().setText(username);
					JOptionPane.showMessageDialog(btnNewButton, "Your username has been set.", "Username", JOptionPane.INFORMATION_MESSAGE);
					textField.setText("");
				}
			}
		});
		btnNewButton.setBounds(87, 105, 107, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Join the chatroom server.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(63, 139, 170, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnJoinServer = new JButton("Join server");
		btnJoinServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				if(userName().getText().toString() == "Not set") {
					JOptionPane.showMessageDialog(btnJoinServer, "Please set your username first.");
				}
				else {
					if(client.joinServer(userName().getText().toString())) {
						preFrame.setVisible(false);
						display2();
					}else {
						JOptionPane.showMessageDialog(btnJoinServer, "Cannot join the server.","Connection refused", JOptionPane.WARNING_MESSAGE);
					}
				}				
			}
		});
		
		btnJoinServer.setBounds(87, 164, 107, 23);
		contentPane.add(btnJoinServer);
		
		JLabel lblNewLabel_8 = new JLabel("Username:");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_8.setBounds(50, 26, 72, 20);
		contentPane.add(lblNewLabel_8);
		
		lblNewLabel_9 = new JLabel("Not set");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_9.setBounds(119, 28, 89, 16);
		contentPane.add(lblNewLabel_9);
		
		preFrame.setVisible(true);
	}
	
	public static void display2() {
				
		chatFrame = new JFrame();
		chatFrame.setLocationByPlatform(true);
		chatFrame.setResizable(false);
		chatFrame.setTitle("Chat Room");
		chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatFrame.setBounds(100, 100, 310, 360);
		chatFrame.getContentPane().setLayout(null);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 325, 294, 2);
		chatFrame.getContentPane().add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(0, 41, 294, 2);
		chatFrame.getContentPane().add(separator_1_1);
		
		JLabel lblNewLabel_5 = new JLabel("Server:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(21, 11, 55, 19);
		chatFrame.getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Status:");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5_1.setBounds(147, 11, 55, 19);
		chatFrame.getContentPane().add(lblNewLabel_5_1);
		
		lblNewLabel_6 = new JLabel("Unknown");
		lblNewLabel_6.setBounds(69, 12, 68, 19);
		chatFrame.getContentPane().add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("Unknown");
		lblNewLabel_7.setBounds(194, 13, 76, 17);
		chatFrame.getContentPane().add(lblNewLabel_7);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {			
				if(arg0.getKeyCode() == 10) {
					if(!inputField().getText().isEmpty()) {
						String input = inputField().getText();
						
						inputField().setText("");
						client.sendMessage(input);
					}
					inputField().requestFocusInWindow();
				}
			}
		});
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_1.setBounds(4, 277, 211, 40);
		chatFrame.getContentPane().add(textField_1);
		textField_1.requestFocusInWindow();
		textField_1.setColumns(10);
		
		btnNewButton_1 = new JButton("Send");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!inputField().getText().isEmpty()) {
					String input = inputField().getText();
					
					inputField().setText("");
									
					client.sendMessage(input);
				}
				inputField().requestFocusInWindow();
			}
		});
		btnNewButton_1.setBounds(221, 285, 65, 23);
		chatFrame.getContentPane().add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 41, 294, 232);
		chatFrame.getContentPane().add(scrollPane);
		
	    textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				client.joinServer(userName().getText());
			}
		});
		btnNewButton_2.setVisible(false);
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setMinimumSize(new Dimension(20, 7));
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setOpaque(false);
		btnNewButton_2.setToolTipText("Reconnect");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\User016\\Downloads\\reload.png"));
		btnNewButton_2.setBounds(264, 11, 22, 19);
		chatFrame.getContentPane().add(btnNewButton_2);
		
		chatFrame.setVisible(true);
		client.updateUI();
	}
		
	public static void setServerName(String serverName) {
		lblNewLabel_6.setText(serverName);
	}
	public static void setTitle(String userName) {
		chatFrame.setTitle("Chat Room - " + userName);
	}
	public static void setServerStatus(boolean connected) {
		
		if(connected) {
			serverStatus().setText("Connected");
		}else {
			serverStatus().setText("Disconnected");
		}
	}
	public static JLabel serverName() {
		return lblNewLabel_6;
	}
	public static JLabel userName() {
		return lblNewLabel_9;
	}
	public static JLabel serverStatus() {
		return lblNewLabel_7;
	}
	public static JTextArea chatArea() {
		return textArea;
	}
	public static JButton sendButton() {
		return btnNewButton_1;
	}
	public static JTextField inputField() {
		return textField_1;
	}
	public static JButton reconnectBtn() {
		return btnNewButton_2;
	}
}
