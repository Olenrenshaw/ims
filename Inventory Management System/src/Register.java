import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUsername;
	private JPasswordField passwordFieldPswd;
	private JPasswordField passwordFieldRePswd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Register dialog = new Register();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 Connection conn = null;
	/**
	 * Create the dialog.
	 */
	public Register() {
		setTitle("System Administrator Registeration");
		conn = mysqlConnection.dbConnector();
		setBounds(100, 100, 489, 232);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(0, 0, 0));
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(38, 34, 112, 25);
		contentPanel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(38, 70, 112, 25);
		contentPanel.add(lblPassword);
		
		JLabel lblRetypePassword = new JLabel("Retype Password");
		lblRetypePassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRetypePassword.setBounds(38, 106, 112, 25);
		contentPanel.add(lblRetypePassword);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldUsername.setBounds(160, 34, 267, 25);
		contentPanel.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		passwordFieldPswd = new JPasswordField();
		passwordFieldPswd.setHorizontalAlignment(SwingConstants.CENTER);
		passwordFieldPswd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordFieldPswd.setBounds(160, 70, 267, 25);
		contentPanel.add(passwordFieldPswd);
		
		passwordFieldRePswd = new JPasswordField();
		passwordFieldRePswd.setHorizontalAlignment(SwingConstants.CENTER);
		passwordFieldRePswd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordFieldRePswd.setBounds(160, 106, 267, 25);
		contentPanel.add(passwordFieldRePswd);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(15);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("REGISTER");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String username = textFieldUsername.getText().toUpperCase();
						String pswd = passwordFieldPswd.getText();
						String rePswd = passwordFieldRePswd.getText();
						//check if empty
						if (username.isEmpty() || pswd.isEmpty() || rePswd.isEmpty()) {
							JOptionPane.showMessageDialog(null, "All field cannot be empty!");
						}else {
							//check for username length
							if (username.length() != 10) {
								JOptionPane.showMessageDialog(null, "Username must be 10 charasters of alphanumerical!");
							}else {
								//check username contents
								if (username.contains(",") || username.contains(".") || username.contains("/") || username.contains(";") || username.contains("'") ||
								username.contains("[") || username.contains("]") || username.contains("'\'") || username.contains("=") || username.contains("-") ||
								username.contains("<") || username.contains(">") || username.contains("?") || username.contains(":") || username.contains("`") ||
								username.contains("{") || username.contains("}") || username.contains("|") || username.contains("+") || username.contains("_") || 
								username.contains(")") || username.contains("(") || username.contains("*") || username.contains("&") || username.contains("^") ||
								username.contains("%") || username.contains("$") || username.contains("#") || username.contains("@") || username.contains("@") ||
								username.contains("~")) {
									JOptionPane.showMessageDialog(null, "Username must be 10 charasters of alphanumerical!");
								}else {
									//check password length
									if (pswd.length() < 8) {
										JOptionPane.showMessageDialog(null, "Password must at least consist of 8 characters!");
									}else {
										//check if password match
										if (!rePswd.equals(pswd)) {
											JOptionPane.showMessageDialog(null, "Password not matching!");
										}else {
											//all details correct //ask user confirmation
											int userConfirmation = JOptionPane.showConfirmDialog(null, "Confirm registeration?", "", JOptionPane.YES_NO_OPTION);
											if (userConfirmation == 0) {
												//insert details into database
												try {
													String query = "INSERT INTO Admin (userID, userPW) VALUES (?, ?)";
													PreparedStatement pSt =conn.prepareStatement(query);
													pSt.setString(1, username);
													pSt.setString(2, pswd);
													pSt.execute();
													JOptionPane.showMessageDialog(null, "User registered.");
													pSt.close();
													
													Register.this.dispose();
													MainMenu mainMenu = new MainMenu();
													mainMenu.setVisible(true);
												}catch (Exception e) {
													e.printStackTrace();
													JOptionPane.showMessageDialog(null, "Server offline or unavailable.", "Warning", 2);
												}
											}
										}
									}
								}
							}
						}
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton registerButton = new JButton("CANCEL");
				registerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Register.this.dispose();
						Login.main(null);
					}
				});
				registerButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				registerButton.setActionCommand("Cancel");
				buttonPane.add(registerButton);
			}
		}
	}
}
