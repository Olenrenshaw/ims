import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddItemType extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldName;
	private JTextField textFieldSform;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddItemType dialog = new AddItemType();
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
	public AddItemType() {
		setTitle("Add Item Type");
		conn = mysqlConnection.dbConnector();
		setBounds(100, 100, 356, 211);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(25, 39, 65, 24);
		contentPanel.add(lblName);
		
		JLabel lblShortform = new JLabel("Shortform");
		lblShortform.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblShortform.setBounds(25, 84, 65, 24);
		contentPanel.add(lblShortform);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldName.setBounds(100, 39, 215, 24);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSform = new JTextField();
		textFieldSform.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldSform.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldSform.setBounds(100, 84, 65, 24);
		contentPanel.add(textFieldSform);
		textFieldSform.setColumns(10);
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
						String tn = textFieldName.getText();
						String sf = textFieldSform.getText().toUpperCase();
						boolean valid = false;
						//check if empty
						if (tn.isEmpty() || sf.isEmpty()) {
							JOptionPane.showMessageDialog(null, "All fields cannot be empty!");
							valid = false;
						}else {
							valid = true;
						}
						//check length of shortForm
						if (!sf.isEmpty() && sf.length() != 2) {
							JOptionPane.showMessageDialog(null, "Shortform must consist of 2 alphabet characters!");
						}else {
							valid = true;
						}
						//check if shortForm not contain alphabet
						if (sf.contains("0") || sf.contains("1") || sf.contains("2") || sf.contains("3") || sf.contains("4") ||
								sf.contains("5") || sf.contains("6") || sf.contains("7") || sf.contains("8") || sf.contains("9") ||
								sf.contains(",") || sf.contains(".") || sf.contains("/") || sf.contains(";") || sf.contains("'") ||
								sf.contains("[") || sf.contains("]") || sf.contains("'\'") || sf.contains("=") || sf.contains("-") ||
								sf.contains("<") || sf.contains(">") || sf.contains("?") || sf.contains(":") || sf.contains("`") ||
								sf.contains("{") || sf.contains("}") || sf.contains("|") || sf.contains("+") || sf.contains("_") || 
								sf.contains(")") || sf.contains("(") || sf.contains("*") || sf.contains("&") || sf.contains("^") ||
								sf.contains("%") || sf.contains("$") || sf.contains("#") || sf.contains("@") || sf.contains("@") ||
								sf.contains("~")) {
							JOptionPane.showMessageDialog(null, "Shortform must consist of 2 alphabet characters!");
						}else {
							valid = true;
						}
						//check if name not contain alphabet
						if (tn.contains("0") || tn.contains("1") || tn.contains("2") || tn.contains("3") || tn.contains("4") ||
								tn.contains("5") || tn.contains("6") || tn.contains("7") || tn.contains("8") || tn.contains("9") ||
								tn.contains(",") || tn.contains(".") || tn.contains("/") || tn.contains(";") || tn.contains("'") ||
								tn.contains("[") || tn.contains("]") || tn.contains("'\'") || tn.contains("=") || tn.contains("-") ||
								tn.contains("<") || tn.contains(">") || tn.contains("?") || tn.contains(":") || tn.contains("`") ||
								tn.contains("{") || tn.contains("}") || tn.contains("|") || tn.contains("+") || tn.contains("_") || 
								tn.contains(")") || tn.contains("(") || tn.contains("*") || tn.contains("&") || tn.contains("^") ||
								tn.contains("%") || tn.contains("$") || tn.contains("#") || tn.contains("@") || tn.contains("@") ||
								tn.contains("~")) {
							JOptionPane.showMessageDialog(null, "Name must contains alphabet only!");
						}else {
							valid = true;
						}
						
						if (valid == true) {
							int userConfirmation = JOptionPane.showConfirmDialog(null, "Add item type '"+tn+"' to the item list?", "", JOptionPane.YES_NO_OPTION);
							if (userConfirmation == 0) {
								try {
									String query = "INSERT INTO itemType (itemType, typeSform) VALUES (?, ?)";
									PreparedStatement pSt = conn.prepareStatement(query);
									pSt.setString(1, tn);
									pSt.setString(2, sf);
									pSt.execute();
									JOptionPane.showMessageDialog(null, "Item type added!");
									pSt.close();
								}catch (Exception e) {
									e.printStackTrace();
								}
								textFieldName.setText("");
								textFieldSform.setText("");
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
						AddItemType.this.dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
