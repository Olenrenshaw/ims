import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import java.io.*;

public class MainMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//create connection object
	Connection conn = null;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnViewInventory;
	private JButton btnViewHistory;
	private JButton btnAddItem;
	private JButton btnRestockDestock;
	private JButton btnEditItem;
	private JButton btnDeleteItem;
	private JButton btnBack;
	private JMenuItem mntmExport;
	
	/**
	 * Create the frame.
	 */
	public MainMenu() {
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("Inventory Management System");
		conn = mysqlConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 843, 439);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//1. button view inventory
		btnViewInventory = new JButton("");
		Image img = new ImageIcon(this.getClass().getResource("/view-inventory.png")).getImage();
		btnViewInventory.setIcon(new ImageIcon(img));
		btnViewInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntmExport.setEnabled(true);
				openTable();
				try {
					String query = "SELECT itemID AS 'Item ID', itemName AS 'Item Name', itemBrand AS 'Brand', itemType AS 'Category',"
							+ "itemPrice AS 'Unit Price', itemQuantity AS 'Quantity' FROM Inventory";
					PreparedStatement pSt = conn.prepareStatement(query);
					ResultSet rs = pSt.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnViewInventory.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnViewInventory.setBounds(139, 68, 126, 126);
		contentPane.add(btnViewInventory);
		
		//2. button view history
		btnViewHistory = new JButton("");
		btnViewHistory.setBackground(Color.WHITE);
		Image img2 = new ImageIcon(this.getClass().getResource("/transaction-history.png")).getImage();
		btnViewHistory.setIcon(new ImageIcon(img2));
		btnViewHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openTable();
				try {
					String query = "SELECT transactionNumber AS 'Transaction No.', itemID as 'Item ID', itemName AS 'Item Name',"
							+ "transactionType AS 'Transaction Type', quantityInvolve AS 'Quantity Involved', date AS 'Date' FROM History";
					PreparedStatement pSt = conn.prepareStatement(query);
					ResultSet rs = pSt.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnViewHistory.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnViewHistory.setBounds(352, 68, 126, 126);
		contentPane.add(btnViewHistory);
		
		//3. button add item
		btnAddItem = new JButton("");
		Image img3 = new ImageIcon(this.getClass().getResource("/add-item.png")).getImage();
		btnAddItem.setIcon(new ImageIcon(img3));
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open add item windows
				AddNewItem addNewItem = new AddNewItem();
				addNewItem.setVisible(true);
			}
		});
		btnAddItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAddItem.setBounds(567, 68, 126, 126);
		contentPane.add(btnAddItem);
		
		//4. button re-stock item
		btnRestockDestock = new JButton("");
		Image img6 = new ImageIcon(this.getClass().getResource("/manage-stock.png")).getImage();
		btnRestockDestock.setIcon(new ImageIcon(img6));
		btnRestockDestock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open stock management panel
				ManageStock manageStock = new ManageStock();
				manageStock.setVisible(true);
			}
		});
		btnRestockDestock.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRestockDestock.setBounds(352, 229, 126, 126);
		contentPane.add(btnRestockDestock);
		
		//5. button edit item details
		btnEditItem = new JButton("");
		Image img5 = new ImageIcon(this.getClass().getResource("/update-detail.png")).getImage();
		btnEditItem.setIcon(new ImageIcon(img5));
		btnEditItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open edit item dialog
				EditItemDetail editItemDetail = new EditItemDetail();
				editItemDetail.setVisible(true);
			}
		});
		btnEditItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditItem.setBounds(139, 229, 126, 126);
		contentPane.add(btnEditItem);
		
		//6. button delete item
		btnDeleteItem = new JButton("");
		btnDeleteItem.setBackground(Color.WHITE);
		Image img4 = new ImageIcon(this.getClass().getResource("/remove-item.png")).getImage();
		btnDeleteItem.setIcon(new ImageIcon(img4));
		btnDeleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open remove item panel
				RemoveItem removeItem = new RemoveItem();
				removeItem.setVisible(true);
			}
		});
		btnDeleteItem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDeleteItem.setBounds(567, 229, 126, 126);
		contentPane.add(btnDeleteItem);
		
		JLabel lbl_BigSystemName = new JLabel("INVENTORY MANAGEMENT SYSTEM");
		lbl_BigSystemName.setForeground(new Color(30, 144, 255));
		lbl_BigSystemName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_BigSystemName.setFont(new Font("Tahoma", Font.BOLD, 22));
		lbl_BigSystemName.setBounds(193, 20, 426, 37);
		contentPane.add(lbl_BigSystemName);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(10, 68, 806, 287);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 861, 21);
		contentPane.add(menuBar);
		
		JMenu mnAdmin = new JMenu("Administration");
		menuBar.add(mnAdmin);
		
		JMenuItem mntmRegisterItemType = new JMenuItem("Register Item Type");
		mntmRegisterItemType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddItemType addItemType = new AddItemType();
				addItemType.setVisible(true);
			}
		});
		mnAdmin.add(mntmRegisterItemType);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int userConfirmation = JOptionPane.showConfirmDialog(null, "Logout?", "", JOptionPane.YES_NO_OPTION);
				if (userConfirmation == 0) {
					MainMenu.this.dispose();
					//get to the main JFrame//no return arguments
					Login.main(null);
				}
			}
		});
		
		JMenuItem mntmClearInventory = new JMenuItem("Clear Inventory");
		mntmClearInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfirmToClearInventory clearInventory = new ConfirmToClearInventory();
				clearInventory.setVisible(true);
			}
		});
		mnAdmin.add(mntmClearInventory);
		
		JMenuItem mntmClearTransactionRecords = new JMenuItem("Clear Transaction Records");
		mntmClearTransactionRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfirmToClearHistory clearHistory = new ConfirmToClearHistory();
				clearHistory.setVisible(true);
			}
		});
		mnAdmin.add(mntmClearTransactionRecords);
		
		mntmExport = new JMenuItem("Export Inventory");
		mntmExport.setEnabled(false);
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int userConfirmation = JOptionPane.showConfirmDialog(null, "Inventory will be exported to Inventory.txt", "", JOptionPane.YES_NO_OPTION);
				if(userConfirmation == 0) {
					saveTable(table);
				}
			}
		});
		mnAdmin.add(mntmExport);
		
		JSeparator separator = new JSeparator();
		mnAdmin.add(separator);
		mnAdmin.add(mntmLogout);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Group members:\n1). Alistair Ollenrenshaw BK14110062\n2). Dady Yangsa BK14110095", "About", 3);
			}
		});
		mnHelp.add(mntmAbout);
		
		//button back
		btnBack = new JButton("Back");
		btnBack.setVisible(false);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntmExport.setEnabled(false);
				scrollPane.setVisible(false);
				btnViewInventory.setVisible(true);
				btnViewHistory.setVisible(true);
				btnAddItem.setVisible(true);
				btnRestockDestock.setVisible(true);
				btnEditItem.setVisible(true);
				btnDeleteItem.setVisible(true);
				btnBack.setVisible(false);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBack.setBounds(728, 366, 89, 23);
		contentPane.add(btnBack);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(180, 43, 46, 14);
		contentPane.add(label_1);
	}
	
	public void openTable() {
		scrollPane.setVisible(true);
		btnViewInventory.setVisible(false);
		btnViewHistory.setVisible(false);
		btnAddItem.setVisible(false);
		btnRestockDestock.setVisible(false);
		btnEditItem.setVisible(false);
		btnDeleteItem.setVisible(false);
		btnBack.setVisible(true);
	}
	
	public void saveTable(JTable table){
		try {
			File file = new File("Inventory.txt");
			 
			if(!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
			for(int i = 0 ; i < table.getColumnCount() ; i++){
			    bfw.write(table.getColumnName(i));
			    bfw.write("\t");
			}
			bfw.newLine();
			for (int i = 0 ; i < table.getRowCount(); i++){
				for(int j = 0 ; j < table.getColumnCount();j++){
					bfw.write((String)(table.getValueAt(i, j)));
					bfw.write("\t");
			    }
				bfw.newLine();
			}
			JOptionPane.showMessageDialog(null, "Inventory successfully exported.");
			bfw.close();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
