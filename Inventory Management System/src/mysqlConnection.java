import java.sql.*;
import javax.swing.*;
//server must online
//create mysqlConnection class to handle connection with the database
public class mysqlConnection {
	//data type: Connection
	Connection conn = null;
	//method dbConnector with data type Connection
	public static Connection dbConnector() {
		//server IP address//Currently 127.0.0.1 is the address of this PC
		String hostAddress = "127.0.0.1";
		//perform connection to database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://"+hostAddress+":3306/oop", "root", "1234");
			//JOptionPane.showMessageDialog(null, "WELCOME TO INVENTORY MANAGEMENT SYSTEM");
			return conn;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to server.", "Warning", 2);
			return null;
		}
	}
}
