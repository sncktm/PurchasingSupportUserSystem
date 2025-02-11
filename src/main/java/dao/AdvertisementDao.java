package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AdCommodityBeans;

public class AdvertisementDao {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";
    
    

    // すべての広告を取得するメソッド
    public List<AdCommodityBeans> findAll() {
        List<AdCommodityBeans> commodityInfoArray = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
        	
        	
            
            // 全ての広告を取得するSQL
            String sql = "SELECT * FROM advertisement ORDER BY Advertisement_No";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                System.out.println("findByTypenashi");
                
                
                while (rs.next()) {
                	System.out.println("1");
                    AdCommodityBeans infobean = new AdCommodityBeans(
                    		
                        rs.getString("Advertisement_No"),
                        rs.getString("Store_No"),
                        rs.getString("Advertisement_type"),
                        rs.getString("Advertisement_Image"),
                        rs.getString("Advertisement_Explanation"),
                        rs.getString("Advertisement_title"),
                        rs.getString("Advertisement_priority")
                    );
                    System.out.println("2");
                    commodityInfoArray.add(infobean);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("データベース接続エラー", e);
        }
        return commodityInfoArray;
    }

    // 特定の広告タイプの広告のみを取得するメソッド
    public List<AdCommodityBeans> findByType(String advertisementType) {
        List<AdCommodityBeans> commodityInfoArray = new ArrayList<>();
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
		}

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
        	System.out.println("findByType2");
            String sql = "SELECT * FROM advertisement WHERE Advertisement_type = ? ORDER BY Advertisement_No";
            
           PreparedStatement stmt = conn.prepareStatement(sql);
            	System.out.println("findByType1");
                stmt.setString(1, advertisementType);
                ResultSet rs = stmt.executeQuery();
                
                
                
                while (rs.next()) {
                	System.out.println("3");
                	System.out.println(rs.getString("Advertisement_No"));
                    AdCommodityBeans infobean = new AdCommodityBeans(
                        rs.getString("Advertisement_No"),
                        rs.getString("Store_No"),
                        rs.getString("Advertisement_type"),
                        rs.getString("Advertisement_Image"),
                        rs.getString("Advertisement_Explanation"),
                        rs.getString("Advertisement_title"),
                        rs.getString("Advertisement_priority")
                      
                    );
                    commodityInfoArray.add(infobean);
                    System.out.println("4");
                }
            
            System.out.println("5");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("6");
            throw new RuntimeException("データベース接続エラー", e);
        }
        return commodityInfoArray;
    }

    // 既存のメソッドの後に以下のメソッドを追加します

    public List<AdCommodityBeans> findByPriority(String advertisementPriority) {
        List<AdCommodityBeans> commodityInfoArray = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM advertisement WHERE advertisement_priority = ? ORDER BY Advertisement_No";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, advertisementPriority);
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    AdCommodityBeans infobean = new AdCommodityBeans(
                        rs.getString("Advertisement_No"),
                        rs.getString("Store_No"),
                        rs.getString("Advertisement_type"),
                        rs.getString("Advertisement_Image"),
                        rs.getString("Advertisement_Explanation"),
                        rs.getString("Advertisement_title"),
                        rs.getString("Advertisement_priority")
                    );
                    commodityInfoArray.add(infobean);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("データベース接続エラー", e);
        }
        return commodityInfoArray;
    }
}

