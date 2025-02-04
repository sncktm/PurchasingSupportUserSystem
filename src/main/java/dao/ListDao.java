package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ListBeans;

public class ListDao {
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
	private final String DB_USER = "root";
	private final String DB_PASS = "mysql"; // MySQLのパスワードに変更してください

    // データベース接続の取得


    // 商品の追加
    public boolean ListRegistered(String List_No,String Sales_Namber) {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
		}
		//データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
        		
			String sql = "INSERT INTO List_Goods VALUES(?,?)";
			
             PreparedStatement statement = conn.prepareStatement(sql);
        	
            statement.setString(1, List_No);
            
            statement.setString(2, Sales_Namber);
            
            
            int result = statement.executeUpdate();
            
            if(result != 1) {
            	
            	return false;
            	
            }
            
        } catch (SQLException e) {
            
            System.err.println("SQLエラー発生: " + e.getMessage()); // 詳細なエラーメッセージを表示
            e.printStackTrace();
            return false;
        } 
        return true;
    }
    
    
    public ArrayList<ListBeans> ListView(String user_No) {
    	
    	ArrayList<ListBeans> ListInfoArray = new ArrayList<ListBeans>();
    	
    	System.out.println(user_No);
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
		}
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
        	String sql = "SELECT * FROM list WHERE Members_No = ?";
 
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	stmt.setString(1, user_No);
        	
        	ResultSet rs = stmt.executeQuery();
            
 
            while (rs.next()) {
            	
            	String List_No = rs.getString("List_No");
            	String List_Name = rs.getString("List_Name");
            	
            	ListBeans infobean = new ListBeans(List_No,List_Name);
            	ListInfoArray.add(infobean);

            }
            System.out.println("0");
        } catch (SQLException e) {
            System.err.println("SQLエラー発生: " + e.getMessage()); // 詳細なエラーメッセージを表示

            e.printStackTrace();
            System.out.println("2");
        }
        return ListInfoArray;
    
	}
}

