import java.awt.BorderLayout;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.FlowLayout;

import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditItemDetail extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditItemDetail dialog = new EditItemDetail();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	Connection conn = null;
	private JTextField textFieldItemID;
	private JTextField textFieldNewName;
	private JTextField textFieldNewBrand;
	private JComboBox comboBoxItemType;
	private JComboBox comboBoxItemName;
	private JComboBox comboBoxNewType;
	private JTextField textFieldNewPrice;
	
	//method: fill comboBoxItemType with data from database
	public void fillComboBoxItemType() {
		FetchData fD = new FetchData();
		conn = mysqlConnection.dbConnector();
		
		try {
			String  query = "SELECT * FROM itemType";
			PreparedStatement pSt = conn.prepareStatement(query);
			ResultSet rs = pSt.executeQuery();
			
			while (rs.next()) {
				comboBoxItemType.addItem(rs.getString(1));
				comboBoxNewType.addItem(rs.getString(1));
			}
			pSt.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method: assign with value from database
	public void fillItemIDField() {
		//String x = String.valueOf(comboBoxItemList.getSelectedItem());
		try {
			String query = "SELECT * FROM Inventory WHERE itemName = ?";
			PreparedStatement pSt = conn.prepareStatement(query);
			pSt.setString(1, (String)comboBoxItemName.getSelectedItem());
			ResultSet rs = pSt.executeQuery();
			while (rs.next()) {
				textFieldItemID.setText(rs.getString("itemID"));
			}
			pSt.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method: fill editable fields
	public void fillNewDetailsField() {
		try {
			String query = "SELECT * FROM Inventory WHERE itemID = ?";
			PreparedStatement pSt = conn.prepareStatement(query);
			pSt.setString(1, textFieldItemID.getText());
			ResultSet rs = pSt.executeQuery();
			while (rs.next()) {
				textFieldNewName.setText(rs.getString("itemName"));
				textFieldNewBrand.setText(rs.getString("itemBrand"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the dialog.
	 */
	public EditItemDetail() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Edit Item Detail");
		setBounds(100, 100, 565, 296);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblItemType = new JLabel("Item Type");
		lblItemType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblItemType.setBounds(24, 30, 76, 24);
		contentPanel.add(lblItemType);
		
		JLabel lblItemName = new JLabel("Item Name");
		lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblItemName.setBounds(24, 65, 76, 24);
		contentPanel.add(lblItemName);
		
		JLabel lblItemId = new JLabel("Item ID");
		lblItemId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblItemId.setBounds(329, 30, 64, 24);
		contentPanel.add(lblItemId);
		
		JLabel lblNewName = new JLabel("New Name");
		lblNewName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewName.setBounds(24, 100, 76, 24);
		contentPanel.add(lblNewName);
		
		JLabel lblNewBrand = new JLabel("New Brand");
		lblNewBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewBrand.setBounds(24, 135, 76, 24);
		contentPanel.add(lblNewBrand);
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblType.setBounds(317, 170, 40, 24);
		contentPanel.add(lblType);
		
		comboBoxItemType = new JComboBox();
		comboBoxItemType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//refresh comboBox list every time action performed 
				comboBoxItemName.removeAllItems();
				//create new object fD
				FetchData fD = new FetchData();
				//get list
				ArrayList<Item> list = fD.getData((String)comboBoxItemType.getSelectedItem());
				//fill comboBoxItemName
				for (int i=0; i<list.size(); i++) {
					comboBoxItemName.addItem(list.get(i).getItemName());
				}
			}
		});
		comboBoxItemType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxItemType.setBounds(110, 30, 150, 24);
		contentPanel.add(comboBoxItemType);
		
		comboBoxItemName = new JComboBox();
		comboBoxItemName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fillItemIDField();
				fillNewDetailsField();
			}
		});
		comboBoxItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxItemName.setBounds(110, 65, 407, 24);
		contentPanel.add(comboBoxItemName);
		
		textFieldItemID = new JTextField();
		textFieldItemID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldItemID.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldItemID.setEditable(false);
		textFieldItemID.setBounds(403, 30, 114, 24);
		contentPanel.add(textFieldItemID);
		textFieldItemID.setColumns(10);
		
		textFieldNewName = new JTextField();
		textFieldNewName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldNewName.setBounds(110, 100, 407, 24);
		contentPanel.add(textFieldNewName);
		textFieldNewName.setColumns(10);
		
		textFieldNewBrand = new JTextField();
		textFieldNewBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldNewBrand.setBounds(110, 135, 407, 24);
		contentPanel.add(textFieldNewBrand);
		textFieldNewBrand.setColumns(10);
		
		comboBoxNewType = new JComboBox();
		comboBoxNewType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxNewType.setBounds(367, 170, 150, 24);
		contentPanel.add(comboBoxNewType);
		
		JLabel lblUnitPrice = new JLabel("Unit Price :");
		lblUnitPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUnitPrice.setBounds(24, 170, 76, 24);
		contentPanel.add(lblUnitPrice);
		
		JLabel lblRm = new JLabel("RM");
		lblRm.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRm.setBounds(110, 170, 21, 24);
		contentPanel.add(lblRm);
		
		textFieldNewPrice = new JTextField();
		textFieldNewPrice.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNewPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldNewPrice.setBounds(138, 170, 150, 24);
		contentPanel.add(textFieldNewPrice);
		textFieldNewPrice.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(15);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton updateButton = new JButton("UPDATE");
				updateButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String name = textFieldNewName.getText();
						String brand = textFieldNewBrand.getText();
						String price = textFieldNewPrice.getText();
						//check if empty
						if (name.isEmpty() || brand.isEmpty() || price.isEmpty()) {
							JOptionPane.showMessageDialog(null, "All fields cannot be empty!");
						}else {
							//check price field for valid input
							if (price.contains(",") || price.contains(".") || price.contains("/") || price.contains(";") || price.contains("'") ||
								price.contains("[") || price.contains("]") || price.contains("'\'") || price.contains("=") || price.contains("-") ||
								price.contains("<") || price.contains(">") || price.contains("?") || price.contains(":") || price.contains("`") ||
								price.contains("{") || price.contains("}") || price.contains("|") || price.contains("+") || price.contains("_") || 
								price.contains(")") || price.contains("(") || price.contains("*") || price.contains("&") || price.contains("^") ||
								price.contains("%") || price.contains("$") || price.contains("#") || price.contains("@") || price.contains("@") ||
								price.contains("~")) {
								JOptionPane.showMessageDialog(null, "Invalid value for price!");
							}else {
								int userConfirmation = JOptionPane.showConfirmDialog(null, "Update item?", "", JOptionPane.YES_NO_OPTION);
								if (userConfirmation == 0) {
									try {
										String query = "UPDATE Inventory SET itemName = ?, itemBrand = ?, itemPrice = ?, itemType = ? WHERE itemID = ?";
										PreparedStatement pSt = conn.prepareStatement(query);
										pSt.setString(1, name);
										pSt.setString(2, brand);
										pSt.setString(3, "RM"+price);
										pSt.setString(4, (String)comboBoxNewType.getSelectedItem());
										pSt.setString(5, textFieldItemID.getText());
										pSt.execute();
										pSt.close();
										JOptionPane.showMessageDialog(null, "Item updated!");
									}catch (Exception e) {
										JOptionPane.showMessageDialog(null, e);
									}
								}
							}
						}
					}
				});
				updateButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				updateButton.setActionCommand("OK");
				buttonPane.add(updateButton);
				getRootPane().setDefaultButton(updateButton);
			}
			{
				JButton cancelButton = new JButton("CANCEL");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						EditItemDetail.this.dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		fillComboBoxItemType();
	}
}
