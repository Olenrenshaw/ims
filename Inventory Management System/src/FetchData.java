import java.util.*;
import java.sql.*;
import javax.swing.*;

public class FetchData {
	
	Connection conn = null;
	
	public ArrayList<Item> getData(String itemID){
		
		ArrayList<Item> list = new ArrayList<Item>();
		conn = mysqlConnection.dbConnector();
		
		try {
			String query = "SELECT itemID, itemName, itemBrand, itemType, itemQuantity FROM Inventory WHERE itemType = ?";
			PreparedStatement pSt = conn.prepareStatement(query);
			pSt.setString(1, itemID);
			ResultSet rs = pSt.executeQuery();
			
			Item i;
			
			while(rs.next()) {
				i = new Item(rs.getString("itemID"), rs.getString("itemName"), rs.getString("itemBrand"), rs.getString("itemType"), rs.getInt("itemQuantity"));
				list.add(i);
			}
			
			pSt.close();
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
