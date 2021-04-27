import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;
import java.awt.FlowLayout;

import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddNewItem extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddNewItem dialog = new AddNewItem();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	Connection conn = null;
	private JTextField textFieldItemID;
	private JTextField textFieldItemName;
	private JTextField textFieldItemBrand;
	private JComboBox comboBoxItemType;
	private JTextField textFieldItemPrice;
	
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
			}
			pSt.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//set item ID
	public int getRegisteredNum() {
		int idNum = 1000;
		try {
			String query = "SELECT count(itemID) FROM registeredItem";
			PreparedStatement pSt = conn.prepareStatement(query);
			ResultSet rs = pSt.executeQuery();
			if(rs.next()) {
				String rowsCount = rs.getString("count(itemID)");
				int i = Integer.parseInt(rowsCount);
				idNum += i;
			}
			pSt.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return idNum;
	}
	
	public void setItemID() {
		int num = getRegisteredNum();
		try {
			String query = "SELECT typeSform FROM itemType WHERE itemType = ?";
			PreparedStatement pSt = conn.prepareStatement(query);
			pSt.setString(1, (String)comboBoxItemType.getSelectedItem());
			ResultSet rs = pSt.executeQuery();
			
			while(rs.next()) {
				String initial = rs.getString("typeSform");
				String itemID = initial+num;
				textFieldItemID.setText(itemID);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the dialog.
	 */
	public AddNewItem() {
		conn = mysqlConnection.dbConnector();
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setForeground(Color.BLACK);
		setTitle("Add New Item");
		setBounds(100, 100, 589, 265);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblItemType = new JLabel("Item Type");
			lblItemType.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemType.setBounds(35, 37, 83, 24);
			contentPanel.add(lblItemType);
		}
		{
			JLabel lblItemName = new JLabel("Item Name");
			lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemName.setBounds(35, 72, 83, 24);
			contentPanel.add(lblItemName);
		}
		{
			JLabel lblItemBrand = new JLabel("Item Brand");
			lblItemBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemBrand.setBounds(35, 107, 83, 24);
			contentPanel.add(lblItemBrand);
		}
		{
			JLabel lblItemId = new JLabel("Item ID");
			lblItemId.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemId.setBounds(342, 37, 65, 24);
			contentPanel.add(lblItemId);
		}
		{
			comboBoxItemType = new JComboBox();
			comboBoxItemType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setItemID();
				}
			});
			comboBoxItemType.setFont(new Font("Tahoma", Font.PLAIN, 15));
			comboBoxItemType.setBounds(128, 37, 193, 24);
			contentPanel.add(comboBoxItemType);
		}
		{
			textFieldItemID = new JTextField();
			textFieldItemID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			textFieldItemID.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldItemID.setEditable(false);
			textFieldItemID.setBounds(417, 37, 115, 24);
			contentPanel.add(textFieldItemID);
			textFieldItemID.setColumns(10);
		}
		{
			textFieldItemName = new JTextField();
			textFieldItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			textFieldItemName.setBounds(128, 72, 404, 24);
			contentPanel.add(textFieldItemName);
			textFieldItemName.setColumns(10);
		}
		{
			textFieldItemBrand = new JTextField();
			textFieldItemBrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
			textFieldItemBrand.setBounds(128, 107, 404, 24);
			contentPanel.add(textFieldItemBrand);
			textFieldItemBrand.setColumns(10);
		}
		{
			JLabel lblItemPriceper = new JLabel("Item Price (Per unit) :");
			lblItemPriceper.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemPriceper.setBounds(35, 142, 143, 24);
			contentPanel.add(lblItemPriceper);
		}
		{
			JLabel lblRm = new JLabel("RM");
			lblRm.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblRm.setBounds(188, 142, 24, 24);
			contentPanel.add(lblRm);
		}
		{
			textFieldItemPrice = new JTextField();
			textFieldItemPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
			textFieldItemPrice.setBounds(222, 142, 86, 24);
			contentPanel.add(textFieldItemPrice);
			textFieldItemPrice.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(15);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton confirmButton = new JButton("CONFIRM");
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String name = textFieldItemName.getText();
						String brand = textFieldItemBrand.getText();
						String price = textFieldItemPrice.getText();
						//check if field is empty/not empty
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
								int userConfirmation = JOptionPane.showConfirmDialog(null, "Add "+textFieldItemName.getText()+" to the inventory?", "", JOptionPane.YES_NO_OPTION);
								if (userConfirmation == 0) {
									try {
										String query = "INSERT INTO Inventory (itemID, itemName, itemBrand, itemType, itemPrice, itemQuantity) VALUES (?, ?, ?, ?, ?, ?)";
										PreparedStatement pSt = conn.prepareStatement(query);
										pSt.setString(1, textFieldItemID.getText());
										pSt.setString(2, name);
										pSt.setString(3, brand);
										pSt.setString(4, (String)comboBoxItemType.getSelectedItem());
										pSt.setString(5, "RM"+price);
										pSt.setInt(6, 0);
										pSt.execute();
										JOptionPane.showMessageDialog(null, "Item successfully added!");
										pSt.close();
									}catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										String query = "INSERT INTO registeredItem (itemID, itemName, itemType, dateRegistered) VALUES (?, ?, ?, NOW())";
										PreparedStatement pSt = conn.prepareStatement(query);
										pSt.setString(1, textFieldItemID.getText());
										pSt.setString(2, name);
										pSt.setString(3, (String)comboBoxItemType.getSelectedItem());
										pSt.execute();
										pSt.close();
									}catch (Exception e) {
										e.printStackTrace();
									}
									setItemID();
									textFieldItemName.setText("");
									textFieldItemBrand.setText("");
								}
							}
						}
					}
				});
				confirmButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				confirmButton.setActionCommand("OK");
				buttonPane.add(confirmButton);
				getRootPane().setDefaultButton(confirmButton);
			}
			{
				JButton cancelButton = new JButton("CANCEL");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						AddNewItem.this.dispose();
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
