package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ListInfoBeans;
import model.SalesDataBeans;

public class MyListDao {

	private  final String URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private  final String DB_USER = "root";
    private  final String DB_PASS = "mysql";

    // リスト情報を取得するメソッド
    
    public List<ListInfoBeans> FindAll(String list_No) {
    	
    	List<ListInfoBeans> listArray = new ArrayList<ListInfoBeans>();
    	
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
	            	
	            	List<SalesDataBeans> listgoods = getSalesDataByListNo(List_No);
	            	
	            	ListInfoBeans infobean = new ListInfoBeans(List_No,Members_No,List_Name,List_Date, listgoods);
	            	listArray.add(infobean);
        		}
            }

        } catch (SQLException e) {
            e.printStackTrace(); // デバッグ用にログ出力
            throw new RuntimeException("リスト情報の取得中にエラーが発生しました: " + e.getMessage());
        }

        return listArray;
  
    }
    
	    public List<SalesDataBeans> getSalesDataByListNo(String listNo) {
	        List<SalesDataBeans> salesDataList = new ArrayList<SalesDataBeans>();
	        System.out.println(listNo);
	        System.out.println("詳細dao");
	        try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch(ClassNotFoundException e) {
				throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
			}
	        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)){
	        	String sql = "SELECT sales.Sales_No, sales.JAN_Code, sales.Store_No, sales.Sales_Price, sales.Image, sales.Sales_Flag, goods.Goods_Name  FROM sales, list_goods, goods  WHERE list_goods.Sales_No = sales.Sales_No AND sales.JAN_Code = goods.JAN_Code AND list_goods.List_No = ?";
	
	        	PreparedStatement stmt = conn.prepareStatement(sql);
	        	stmt.setString(1, listNo);
	        	
	        	
	        	ResultSet rs = stmt.executeQuery();
	        		while (rs.next()) {
	        			System.out.println("うぃぇ");
		            	String Sales_No = rs.getString("Sales_No");
		            	String JAN_Code = rs.getString("JAN_Code");
		            	String Store_No = rs.getString("Store_No");
		            	int Sales_Price = rs.getInt("Sales_Price");
		            	String Image = rs.getString("Image");
		            	String Sales_Flag = rs.getString("Sales_Flag");
		            	String Goods_Name = rs.getString("Goods_Name");
		            	System.out.println(Goods_Name);
		     
		
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