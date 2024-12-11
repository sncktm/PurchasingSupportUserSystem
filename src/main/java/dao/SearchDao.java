package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.GoodsArrayBeans;
import model.GoodsBeans;

public class SearchDao {
    private final String URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";

    public GoodsArrayBeans getAllGoods() throws SQLException {
        GoodsArrayBeans goodsArrayBeans = new GoodsArrayBeans();
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
		}

        String sql = "SELECT " +
                     "g.JAN_code, g.Goods_Name, s.Sales_Price, s.Image, s.Sales_Flag, " +
                     "st.Store_Name, " +
                     "CONCAT(st.Store_Address1, ' ', IFNULL(st.Store_Address2, '')) AS Store_Address, " +
                     "st.Opening_Time, st.Closing_Time, st.coordinate " +
                     "FROM goods g " +
                     "LEFT JOIN ( " +
                     "    SELECT s1.JAN_Code, s1.Store_No, s1.Sales_Price, s1.Image, s1.Sales_Flag, s1.Update_Date " +
                     "    FROM sales s1 " +
                     "    INNER JOIN ( " +
                     "        SELECT JAN_Code, Store_No, MAX(Update_Date) AS LatestUpdate " +
                     "        FROM sales " +
                     "        GROUP BY JAN_Code, Store_No " +
                     "    ) s2 " +
                     "    ON s1.JAN_Code = s2.JAN_Code AND s1.Store_No = s2.Store_No AND s1.Update_Date = s2.LatestUpdate " +
                     ") s ON g.JAN_code = s.JAN_Code " +
                     "LEFT JOIN store st ON s.Store_No = st.Store_No";

        try (Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASS);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                GoodsBeans goods = new GoodsBeans(
                        resultSet.getString("JAN_code"),
                        resultSet.getString("Goods_Name"),
                        null, // Goods_Maker（今回は取得しないためnull）
                        null, // Classification（今回は取得しないためnull）
                        null, // Sales_No（今回は取得しないためnull）
                        resultSet.getString("Store_Name"),
                        String.valueOf(resultSet.getInt("Sales_Price")),
                        resultSet.getString("Image"),
                        resultSet.getString("Sales_Flag"),
                        null // Update_Date（今回は取得しないためnull）
                );
                goods.setStore_Address(resultSet.getString("Store_Address"));
                goods.setOpening_Time(resultSet.getTime("Opening_Time"));
                goods.setClosing_Time(resultSet.getTime("Closing_Time"));
                goods.setCoordinate(resultSet.getString("coordinate"));
                System.out.println("JANコード: " + goods.getJAN_code());
                System.out.println("商品名: " + goods.getGoods_Name());

                goodsArrayBeans.addGoods(goods);
            }
        }
        return goodsArrayBeans;
    }
}
