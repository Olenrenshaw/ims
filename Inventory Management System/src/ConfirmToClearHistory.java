import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ConfirmToClearHistory extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConfirmToClearHistory dialog = new ConfirmToClearHistory();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	Connection conn = null;
	
	public void truncateTable() {
		try {
			String query = "TRUNCATE history";
			PreparedStatement pSt = conn.prepareStatement(query);
			pSt.execute();
			pSt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * Create the dialog.
	 */
	public ConfirmToClearHistory() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("User verification");
		conn = mysqlConnection.dbConnector();
		setBounds(100, 100, 450, 171);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblInstruction = new JLabel("To confirm that you are admin, please enter your login password.\r\n");
		lblInstruction.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstruction.setBounds(20, 11, 387, 29);
		contentPanel.add(lblInstruction);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBounds(99, 51, 210, 25);
		contentPanel.add(passwordField);
		{
			JPanel buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(15);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (passwordField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please enter your password!");
						}
						else {
							int userConfirmation = JOptionPane.showConfirmDialog(null, "All the transaction record will be cleared. Are you sure?", "WARNING!", JOptionPane.YES_NO_OPTION);
							if (userConfirmation == 0) {
								int i = 0;
								try {
									String query = "SELECT * FROM Admin WHERE userPW = ?";
									PreparedStatement pSt = conn.prepareStatement(query);
									pSt.setString(1, passwordField.getText());
									ResultSet rs = pSt.executeQuery();
									while (rs.next()) {
										i++;
									}
									if (i == 1) {
										truncateTable();
										JOptionPane.showMessageDialog(null, "Inventory cleared!");
										ConfirmToClearHistory.this.dispose();
									}
									else {
										JOptionPane.showMessageDialog(null, "Invalid password!");
									}
									pSt.close();
									rs.close();
								}catch (Exception e) {
									e.printStackTrace();
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
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ConfirmToClearHistory.this.dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
