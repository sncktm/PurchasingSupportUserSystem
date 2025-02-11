package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AdvertisementManagementBeans;

public class ProductDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";

    public List<AdvertisementManagementBeans> findByAdvertisementNo(String advertisementNo) {
        List<AdvertisementManagementBeans> productArray = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("ProductDAO: JDBCドライバロード成功");
        } catch(ClassNotFoundException e) {
            System.out.println("ProductDAO: JDBCドライバロード失敗");
            e.printStackTrace();
            throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            System.out.println("ProductDAO: データベース接続成功");
            String sql = "SELECT advertisement.Advertisement_No, goods.Goods_Name, goods.Goods_Maker, goods.Classification,sales.Sales_Price,advertisement.Advertisement_priority FROM advertisement INNER JOIN advertisement_goods ON advertisement.Advertisement_No = advertisement_goods.Advertisement_No INNER JOIN sales ON advertisement_goods.Sales_No = sales.Sales_No INNER JOIN goods ON sales.JAN_code = goods.JAN_code WHERE advertisement.Advertisement_No = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, advertisementNo);
                System.out.println("ProductDAO: SQL実行 - " + stmt.toString());
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                	System.out.print(1);
                    AdvertisementManagementBeans infobean = new AdvertisementManagementBeans(
                    	rs.getString("Advertisement_No"),
                    	rs.getString("Goods_Name"),
                        rs.getString("Goods_Maker"),
                        rs.getString("Classification"),
                        rs.getInt("Sales_Price")
                    );
                    productArray.add(infobean);
                }
            }
            System.out.println("ProductDAO: 取得した商品数 - " + productArray.size());
        } catch (SQLException e) {
            System.out.println("ProductDAO: データベースエラー");
            e.printStackTrace();
            throw new RuntimeException("データベース接続エラー", e);
        }
        return productArray;
    }
}

