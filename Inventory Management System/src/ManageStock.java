import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class ManageStock extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldItemID;
	private JTextField textFieldQuantity;
	private JComboBox comboBoxItemType;
	private JComboBox comboBoxItemName;
	private JLabel labelTranscNum;
	private JLabel labelCurrQuantity;
	private JComboBox comboBoxTranscType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ManageStock dialog = new ManageStock();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//create connection object
	Connection conn = null;
	
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
				labelCurrQuantity.setText(rs.getString("itemQuantity"));
			}
			pSt.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method: show transaction number
	public void getTranscNum() {
		try {
			String query = "SELECT count(itemID) FROM History";
			PreparedStatement pSt = conn.prepareStatement(query);
			ResultSet rs = pSt.executeQuery();
			if(rs.next()) {
				String rowsCount = rs.getString("count(itemID)");
				int i = Integer.parseInt(rowsCount);
				int x = 10000 + i;
				labelTranscNum.setText(Integer.toString(x));
			}
			pSt.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the dialog.
	 */
	public ManageStock() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Stock Management");
		setBounds(100, 100, 562, 256);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblItemType = new JLabel("Item Type");
			lblItemType.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemType.setBounds(29, 43, 82, 19);
			contentPanel.add(lblItemType);
		}
		{
			comboBoxItemType = new JComboBox();
			comboBoxItemType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
			comboBoxItemType.setBounds(121, 40, 149, 25);
			contentPanel.add(comboBoxItemType);
		}
		{
			JLabel lblItemName = new JLabel("Item Name");
			lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemName.setBounds(29, 78, 82, 19);
			contentPanel.add(lblItemName);
		}
		{
			comboBoxItemName = new JComboBox();
			comboBoxItemName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fillItemIDField();
				}
			});
			comboBoxItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			comboBoxItemName.setBounds(121, 77, 396, 25);
			contentPanel.add(comboBoxItemName);
		}
		{
			JLabel lblItemId = new JLabel("Item ID");
			lblItemId.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemId.setBounds(309, 43, 60, 19);
			contentPanel.add(lblItemId);
		}
		{
			textFieldItemID = new JTextField();
			textFieldItemID.setEditable(false);
			textFieldItemID.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldItemID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			textFieldItemID.setBounds(379, 40, 138, 25);
			contentPanel.add(textFieldItemID);
			textFieldItemID.setColumns(10);
		}
		{
			JLabel lblQuantity = new JLabel("Quantity");
			lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblQuantity.setBounds(29, 116, 82, 19);
			contentPanel.add(lblQuantity);
		}
		{
			textFieldQuantity = new JTextField();
			textFieldQuantity.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldQuantity.setFont(new Font("Tahoma", Font.PLAIN, 15));
			textFieldQuantity.setBounds(121, 113, 117, 25);
			contentPanel.add(textFieldQuantity);
			textFieldQuantity.setColumns(10);
		}
		{
			JLabel lblPleaseSelect = new JLabel("Please Select");
			lblPleaseSelect.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblPleaseSelect.setBounds(284, 116, 85, 19);
			contentPanel.add(lblPleaseSelect);
		}
		{
			comboBoxTranscType = new JComboBox();
			comboBoxTranscType.setModel(new DefaultComboBoxModel(new String[] {"Restock", "Destock"}));
			comboBoxTranscType.setFont(new Font("Tahoma", Font.PLAIN, 15));
			comboBoxTranscType.setBounds(379, 113, 138, 25);
			contentPanel.add(comboBoxTranscType);
		}
		{
			JLabel lblTransactionNo = new JLabel("Transaction No. : ");
			lblTransactionNo.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTransactionNo.setForeground(new Color(255, 140, 0));
			lblTransactionNo.setBounds(352, 11, 105, 14);
			contentPanel.add(lblTransactionNo);
		}
		{
			labelTranscNum = new JLabel("");
			labelTranscNum.setForeground(new Color(255, 140, 0));
			labelTranscNum.setHorizontalAlignment(SwingConstants.RIGHT);
			labelTranscNum.setBounds(467, 11, 50, 14);
			contentPanel.add(labelTranscNum);
		}
		{
			JLabel lblAvailableQuantity = new JLabel("Available Quantity: ");
			lblAvailableQuantity.setForeground(new Color(34, 139, 34));
			lblAvailableQuantity.setFont(new Font("Tahoma", Font.ITALIC, 11));
			lblAvailableQuantity.setBounds(29, 146, 95, 14);
			contentPanel.add(lblAvailableQuantity);
		}
		{
			labelCurrQuantity = new JLabel("");
			labelCurrQuantity.setForeground(new Color(34, 139, 34));
			labelCurrQuantity.setHorizontalAlignment(SwingConstants.LEFT);
			labelCurrQuantity.setFont(new Font("Tahoma", Font.ITALIC, 11));
			labelCurrQuantity.setBounds(131, 146, 82, 14);
			contentPanel.add(labelCurrQuantity);
		}
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
						//check empty fields
						if (textFieldQuantity.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please specified the quantity to restock/destock!");
						}
						//check if value entered is 0 or negative integer
						else if (Integer.parseInt(textFieldQuantity.getText()) <= 0) {
							JOptionPane.showMessageDialog(null, "Ivalid value!");
						}
						//update item quantity
						else {
							int userConfirmation = JOptionPane.showConfirmDialog(null, "Confirm this transaction?", "", JOptionPane.YES_NO_OPTION);
							if (userConfirmation == 0) {
								int toUpdate = Integer.parseInt(textFieldQuantity.getText());
								int curr = Integer.parseInt(labelCurrQuantity.getText());
								
								if (comboBoxTranscType.getSelectedItem().equals("Restock")) {
		
									try {
										String query = "UPDATE Inventory SET itemQuantity = ? WHERE itemID = ?";
										PreparedStatement pSt = conn.prepareStatement(query);
										pSt.setInt(1, curr + toUpdate);
										pSt.setString(2, textFieldItemID.getText());
										pSt.execute();
										JOptionPane.showMessageDialog(null, "Item updated!");
										pSt.close();
									}catch (Exception e) {
										e.printStackTrace();
									}
									
									try {
										String query = "INSERT INTO history (transactionNumber, itemID, itemName, transactionType, quantityInvolve, date) VALUES (?, ?, ?, ?, ?, NOW())";
										PreparedStatement pSt = conn.prepareStatement(query);
										pSt.setString(1, labelTranscNum.getText());
										pSt.setString(2, textFieldItemID.getText());
										pSt.setString(3, (String)comboBoxItemName.getSelectedItem());
										pSt.setString(4, (String)comboBoxTranscType.getSelectedItem());
										pSt.setString(5, textFieldQuantity.getText());
										pSt.execute();
										pSt.close();
									}catch (Exception e) {
										e.printStackTrace();
									}
								}
								
								else {
									if (toUpdate > curr) {
										JOptionPane.showMessageDialog(null, "Item quantity not enough!");
									}
									else {
										try {
											String query = "UPDATE Inventory SET itemQuantity = ? WHERE itemID = ?";
											PreparedStatement pSt = conn.prepareStatement(query);
											pSt.setInt(1, curr - toUpdate);
											pSt.setString(2, textFieldItemID.getText());
											pSt.execute();
											JOptionPane.showMessageDialog(null, "Item updated!");
											pSt.close();
										}catch (Exception e) {
											e.printStackTrace();
										}
										
										try {
											String query = "INSERT INTO history (transactionNumber, itemID, itemName, transactionType, quantityInvolve, date) VALUES (?, ?, ?, ?, ?, NOW())";
											PreparedStatement pSt = conn.prepareStatement(query);
											pSt.setString(1, labelTranscNum.getText());
											pSt.setString(2, textFieldItemID.getText());
											pSt.setString(3, (String)comboBoxItemName.getSelectedItem());
											pSt.setString(4, (String)comboBoxTranscType.getSelectedItem());
											pSt.setString(5, textFieldQuantity.getText());
											pSt.execute();
											pSt.close();
										}catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}	
						}
						textFieldQuantity.setText("");
						//refresh label
						getTranscNum();
						fillItemIDField();
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
					public void actionPerformed(ActionEvent e) {
						ManageStock.this.dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		//initialize label
		fillComboBoxItemType();
		getTranscNum();
	}

}
