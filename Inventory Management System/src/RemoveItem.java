import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.*;

import javax.swing.border.EmptyBorder;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RemoveItem extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox comboBoxItemType;
	private JComboBox comboBoxItemName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RemoveItem dialog = new RemoveItem();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//create connection object
	Connection conn = null;
	private JTextField textFieldItemID;
	
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
				//textFieldItemType.setText(rs.getString("itemType"));
			}
			pSt.close();
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method: automatically fill itemType and itemName when user entered itemID
	public void setComboBoxesToValue() {
		try {
			String query = "SELECT * FROM Inventory WHERE itemID = ?";
			PreparedStatement pSt = conn.prepareStatement(query);
			pSt.setString(1, textFieldItemID.getText());
			ResultSet rs = pSt.executeQuery();
			while (rs.next()) {
				comboBoxItemType.setSelectedItem(rs.getString("itemType"));
				comboBoxItemName.setSelectedItem(rs.getString("itemName"));
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
	public RemoveItem() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Remove Item");
		setBounds(100, 100, 517, 212);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			comboBoxItemType = new JComboBox();
			comboBoxItemType.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
			comboBoxItemType.setBounds(108, 38, 137, 25);
			contentPanel.add(comboBoxItemType);
		}
		{
			comboBoxItemName = new JComboBox();
			comboBoxItemName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fillItemIDField();
				}
			});
			comboBoxItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			comboBoxItemName.setBounds(108, 84, 366, 25);
			contentPanel.add(comboBoxItemName);
		}
		{
			JLabel lblItemType = new JLabel("Item Type");
			lblItemType.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemType.setBounds(22, 38, 76, 24);
			contentPanel.add(lblItemType);
		}
		{
			JLabel lblItemName = new JLabel("Item Name");
			lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemName.setBounds(22, 86, 76, 23);
			contentPanel.add(lblItemName);
		}
		{
			JLabel lblItemId = new JLabel("Item ID");
			lblItemId.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblItemId.setBounds(267, 41, 60, 22);
			contentPanel.add(lblItemId);
		}
		{
			textFieldItemID = new JTextField();
			textFieldItemID.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent arg0) {
					//call method
					setComboBoxesToValue();
				}
			});
			textFieldItemID.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldItemID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			textFieldItemID.setBounds(337, 38, 137, 25);
			contentPanel.add(textFieldItemID);
			textFieldItemID.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.CENTER);
			fl_buttonPane.setHgap(15);
			buttonPane.setLayout(fl_buttonPane);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton removeButton = new JButton("REMOVE");
				removeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int userConfirmation = JOptionPane.showConfirmDialog(null, "You are about to remove "+(String)comboBoxItemName.getSelectedItem()+" from the inventory.\nConfirm your action.", "", JOptionPane.YES_NO_OPTION);
						if (userConfirmation == 0) {
							try {
								String query = "DELETE FROM Inventory WHERE itemID = ?";
								PreparedStatement pSt = conn.prepareStatement(query);
								pSt.setString(1, textFieldItemID.getText());
								pSt.execute();
								pSt.close();
								JOptionPane.showMessageDialog(null, "Item successfully deleted!");
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
				removeButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				removeButton.setActionCommand("OK");
				buttonPane.add(removeButton);
				getRootPane().setDefaultButton(removeButton);
			}
			{
				JButton cancelButton = new JButton("CANCEL");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						RemoveItem.this.dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		//call method
		fillComboBoxItemType();
	}

}
