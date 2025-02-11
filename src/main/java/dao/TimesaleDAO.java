package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TimesaleBeans;
import model.TimesaleGoodsBeans;

public class TimesaleDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";

    public List<TimesaleBeans> findAllTimesales() {
        List<TimesaleBeans> timesales = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM time_sale";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                	TimesaleBeans timesale = new TimesaleBeans(
                		    rs.getInt("Time_Sale_No"),
                		    rs.getString("Time_Sale_Name"),
                		    rs.getDate("Start_Date"),
                		    rs.getTime("Start_Time"),
                		    rs.getDate("End_Date"),
                		    rs.getTime("End_Time"),
                		    TimesaleBeans.RepeatPattern.valueOf(rs.getString("Repeat_Pattern").toLowerCase()), // 小文字に変換
                		    rs.getString("Repeat_Value"),
                		    rs.getString("Store_No"),
                		    rs.getString("Timesale_Application_Flag")
                		);
                    timesales.add(timesale);
                }
            }
        } catch (SQLException e) {
        	System.out.println(2);
            e.printStackTrace();
        }
        return timesales;
    }

    public List<TimesaleGoodsBeans> findTimesaleGoods(int timeSaleNo) {
        List<TimesaleGoodsBeans> timesaleGoods = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT time_sale_goods.Time_Sale_No,goods.Goods_Name,goods.Goods_Maker,time_sale_goods.Time_Sales_Price,time_sale_goods.Timesale_goods_Application_Flag,sales.Sales_Price FROM time_sale_goods INNER JOIN sales ON sales.Sales_No = time_sale_goods.Sales_No INNER JOIN goods ON goods.JAN_code = sales.JAN_Code WHERE time_sale_goods.Time_Sale_No = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, timeSaleNo);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    TimesaleGoodsBeans goods = new TimesaleGoodsBeans(
                        rs.getInt("Time_Sale_No"),
                        rs.getString("Goods_Name"),
                        rs.getString("Goods_Maker"),
                        rs.getInt("Time_Sales_Price"),
                        rs.getString("Timesale_goods_Application_Flag"),
                        rs.getInt("Sales_Price")
                    );
                    timesaleGoods.add(goods);
                }
            }
        } catch (SQLException e) {
        	System.out.println(1);
            e.printStackTrace();
            
        }
        
        return timesaleGoods;
    }
}