package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ListDetailsBeans;
import model.ListInfoBeans;
import model.SalesDataBeans;

public class MyListDao {

	private  final String URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private  final String DB_USER = "root";
    private  final String DB_PASS = "mysql";
//    private  final String DB_PASS = "srei31003100";

    // リスト情報を取得するメソッド
	    public List<ListInfoBeans> getListArray(String list_No) {
	    	
	    	
	        List<ListInfoBeans> listArray = new ArrayList<ListInfoBeans>();
	        System.out.println(list_No);
	        try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch(ClassNotFoundException e) {
				throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
			}
	        	try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)){
	        	String sql = "SELECT * FROM list WHERE Members_No = ?";
	
	        	
	        	PreparedStatement stmt = conn.prepareStatement(sql);
	        	stmt.setString(1, list_No);
	        	
	        	
	        	try(ResultSet rs = stmt.executeQuery()){
	
	        		while (rs.next()) {
	            	
		            	String List_No = rs.getString("List_No");
		            	String Members_No = rs.getString("Members_No");
		            	String List_Name = rs.getString("List_Name");
		            	Date List_Date = rs.getDate("List_Date");
		
		            	ListInfoBeans infobean = new ListInfoBeans(List_No,Members_No,List_Name,List_Date);
		            	listArray.add(infobean);
	        		}
	            }
	
	        } catch (SQLException e) {
	            e.printStackTrace(); // デバッグ用にログ出力
	            throw new RuntimeException("リスト情報の取得中にエラーが発生しました: " + e.getMessage());
	        }
	
	        return listArray;
	    }
	    
	
//	     リスト項目ごとの詳細情報を取得するメソッド
	    public List<ListDetailsBeans> getListdetailsArray(String d_list_No) {
	        List<ListDetailsBeans> listdetailsArray = new ArrayList<ListDetailsBeans>();
	        System.out.println(d_list_No);
	        try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch(ClassNotFoundException e) {
				throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
			}
	        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)){
	        	String sql = "SELECT * FROM list_goods WHERE List_No = ?";
	        		
	        	
	
	        	
	        	PreparedStatement stmt = conn.prepareStatement(sql);
	        	stmt.setString(1, d_list_No);
	        	
	        	
	        	ResultSet rs = stmt.executeQuery();
	        		while (rs.next()) {
	            	
		            	String List_No = rs.getString("List_No");
		            	String Sales_Namber = rs.getString("Sales_No");
		            	
		            	
		
		            	ListDetailsBeans infobean1 = new ListDetailsBeans(List_No,Sales_Namber);
		            	listdetailsArray.add(infobean1);
	            }
	            
	
	        } catch (SQLException e) {
	            e.printStackTrace(); // デバッグ用にログ出力
	            throw new RuntimeException("詳細情報の取得中にエラーが発生しました: " + e.getMessage());
	       
	        }
	
	        return listdetailsArray;
	    }
	    
	    public List<SalesDataBeans> getSalesDataByListNo(String listNo) {
	        List<SalesDataBeans> salesDataList = new ArrayList<SalesDataBeans>();
	        System.out.println(listNo);
	        try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch(ClassNotFoundException e) {
				throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
			}
	        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)){
	        	String sql = "SELECT * FROM sales WHERE Sales_No = ?";
	        		
	        	
	
	        	
	        	PreparedStatement stmt = conn.prepareStatement(sql);
	        	stmt.setString(1, listNo);
	        	
	        	
	        	ResultSet rs = stmt.executeQuery();
	        		while (rs.next()) {
	            	
		            	String Sales_No = rs.getString("Sales_No");
		            	String JAN_Code = rs.getString("JAN_Code");
		            	String Store_No = rs.getString("Store_No");
		            	int Sales_Price = rs.getInt("Sales_Price");
		            	String Image = rs.getString("Image");
		            	String Sales_Flag = rs.getString("Sales_Flag");
		            	
		            	String sqlGoods = "SELECT Goods_Name FROM goods WHERE JAN_Code = ?";
		                PreparedStatement stmtGoods = conn.prepareStatement(sqlGoods);
		                stmtGoods.setString(1, JAN_Code);

		                ResultSet rsGoods = stmtGoods.executeQuery();
		                String Goods_Name = null;
		                if (rsGoods.next()) {
		                    Goods_Name = rsGoods.getString("Goods_Name");
		                }
		
		            	SalesDataBeans infobean1 = new SalesDataBeans(Sales_No,JAN_Code,Store_No,Sales_Price,Image,Sales_Flag,Goods_Name);
		            	salesDataList.add(infobean1);
	            }
	            
	
	        } catch (SQLException e) {
	            e.printStackTrace(); // デバッグ用にログ出力
	            throw new RuntimeException("詳細情報の取得中にエラーが発生しました: " + e.getMessage());
	       
	        }
	
	        return salesDataList;
	    }
}