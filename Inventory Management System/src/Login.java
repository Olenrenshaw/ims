import java.awt.EventQueue;
import javax.swing.JFrame;
import java.sql.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Login {

	private JFrame Login;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.Login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//create Connection variable
	//default value to null
	Connection conn = null;
	private JTextField textField_LoginUsername;
	private JPasswordField passwordField_LoginPswd;
	/**
	 * Create the application.
	 */
	//constructor
	public Login() {
		initialize();
		//call method dbConnector from mysqlConnection class
		conn = mysqlConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Login = new JFrame();
		Login.getContentPane().setBackground(SystemColor.text);
		Login.setTitle("Login");
		Login.setBounds(100, 100, 456, 363);
		Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login.getContentPane().setLayout(null);
		
		JLabel lbl_LoginTitle = new JLabel("ADMIN LOGIN");
		lbl_LoginTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
		lbl_LoginTitle.setBounds(158, 45, 137, 34);
		Login.getContentPane().add(lbl_LoginTitle);
		
		JLabel lbl_LoginUsername = new JLabel("USERNAME");
		lbl_LoginUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_LoginUsername.setBounds(60, 104, 88, 34);
		Login.getContentPane().add(lbl_LoginUsername);
		
		JLabel lbl_LoginPassword = new JLabel("PASSWORD");
		lbl_LoginPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_LoginPassword.setBounds(60, 149, 88, 27);
		Login.getContentPane().add(lbl_LoginPassword);
		
		textField_LoginUsername = new JTextField();
		textField_LoginUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_LoginUsername.setBounds(158, 104, 219, 29);
		Login.getContentPane().add(textField_LoginUsername);
		textField_LoginUsername.setColumns(10);
		
		passwordField_LoginPswd = new JPasswordField();
		passwordField_LoginPswd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_LoginPswd.setBounds(158, 147, 219, 27);
		Login.getContentPane().add(passwordField_LoginPswd);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			//when the button LOGIN clicked
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "SELECT * FROM Admin WHERE userID = ? AND userPW = ?";
					PreparedStatement pSt = conn.prepareStatement(query);
					pSt.setString(1, textField_LoginUsername.getText());
					pSt.setString(2, passwordField_LoginPswd.getText());
					
					//create rs object to store the result
					//execute the query
					ResultSet rs = pSt.executeQuery();
					int count = 0;
					while(rs.next()) {
						count++;
					}
					//check validity inside database//
					if (count == 1) {
						Login.setVisible(false);
						MainMenu mainMenu = new MainMenu();
						mainMenu.setVisible(true);
					}
					else if (count > 1) {
						JOptionPane.showMessageDialog(null, "Username and password are duplicate");
					}
					else {
						JOptionPane.showMessageDialog(null, "Username and password are invalid!");
					}
					//close the connection to database
					rs.close();
					pSt.close();
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Server offline or unavailable.", "Warning", 2);
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setBounds(176, 207, 89, 27);
		Login.getContentPane().add(btnLogin);
		
		JLabel lblOopProject = new JLabel("2017 OOP Project");
		lblOopProject.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblOopProject.setBounds(177, 299, 88, 14);
		Login.getContentPane().add(lblOopProject);
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Register register = new Register();
				register.setVisible(true);
				Login.setVisible(false);
			}
		});
		lblRegister.setForeground(Color.BLUE);
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblRegister.setBounds(186, 245, 68, 15);
		Login.getContentPane().add(lblRegister);
	}
}
