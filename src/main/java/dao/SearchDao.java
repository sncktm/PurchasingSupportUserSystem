package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import model.GoodsArrayBeans;
import model.GoodsBeans;
import model.StoreArrayBeans;
import model.StoreBeans;


public class SearchDao {
    private final String URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private final String DB_USER = "root";
    private final String DB_PASS = "mysql";

    public SearchDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASS);
    }

    public GoodsArrayBeans getAllGoods(boolean showOnlyAvailable, String sortOption) throws SQLException {
        GoodsArrayBeans goodsArray = new GoodsArrayBeans();
        LocalTime now = LocalTime.now();

        String sql = "SELECT g.JAN_code, g.Goods_Name, g.Goods_Maker, g.Classification, " +
                     "s.Sales_No, s.Sales_Price, s.Image, s.Sales_Flag, " +
                     "st.Store_No, st.Store_Name, st.Opening_Time, st.Closing_Time, " +
                     "st.Latitude, st.Longitude " +
                     "FROM goods g " +
                     "LEFT JOIN sales s ON g.JAN_code = s.JAN_Code " +
                     "LEFT JOIN store st ON s.Store_No = st.Store_No";

        if (showOnlyAvailable) {
            sql += " WHERE s.Sales_Flag = 1 AND ? BETWEEN st.Opening_Time AND st.Closing_Time";
        }

        if ("price-asc".equals(sortOption)) {
            sql += " ORDER BY s.Sales_Price ASC";
        }

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (showOnlyAvailable) {
                statement.setTime(1, Time.valueOf(now));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    GoodsBeans goods = new GoodsBeans();
                    goods.setJAN_code(resultSet.getString("JAN_code"));
                    goods.setGoods_Name(resultSet.getString("Goods_Name"));
                    goods.setGoods_Maker(resultSet.getString("Goods_Maker"));
                    goods.setClassification(resultSet.getString("Classification"));
                    goods.setSales_No(resultSet.getString("Sales_No"));
                    goods.setSales_Price(resultSet.getString("Sales_Price"));
                    goods.setImage(resultSet.getString("Image"));
                    goods.setSales_Flag(resultSet.getString("Sales_Flag"));
                    goods.setStore_No(resultSet.getString("Store_No"));
                    goods.setStore_Name(resultSet.getString("Store_Name"));
                    goods.setOpening_Time(resultSet.getTime("Opening_Time"));
                    goods.setClosing_Time(resultSet.getTime("Closing_Time"));
                    goods.setLatitude(resultSet.getDouble("Latitude"));
                    goods.setLongitude(resultSet.getDouble("Longitude"));

                    goodsArray.addGoods(goods);
                }
            }
        }

        return goodsArray;
    }







    public GoodsArrayBeans searchGoodsByKeywordAndAvailability(String keyword, boolean showOnlyAvailable, String sortOption) throws SQLException {
        GoodsArrayBeans goodsArray = new GoodsArrayBeans();
        LocalTime now = LocalTime.now();

        String sql = "SELECT g.JAN_code, g.Goods_Name, g.Goods_Maker, g.Classification, " +
                     "s.Sales_No, s.Sales_Price, s.Image, s.Sales_Flag, " +
                     "st.Store_No, st.Store_Name, st.Opening_Time, st.Closing_Time, " +
                     "st.Latitude, st.Longitude " +
                     "FROM goods g " +
                     "LEFT JOIN sales s ON g.JAN_code = s.JAN_Code " +
                     "LEFT JOIN store st ON s.Store_No = st.Store_No " +
                     "WHERE g.Goods_Name LIKE ?";

        if (showOnlyAvailable) {
            sql += " AND s.Sales_Flag = 1 AND ? BETWEEN st.Opening_Time AND st.Closing_Time";
        }

        if ("price-asc".equals(sortOption)) {
            sql += " ORDER BY s.Sales_Price ASC";
        }

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");
            if (showOnlyAvailable) {
                statement.setTime(2, Time.valueOf(now));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    GoodsBeans goods = new GoodsBeans();
                    goods.setJAN_code(resultSet.getString("JAN_code"));
                    goods.setGoods_Name(resultSet.getString("Goods_Name"));
                    goods.setGoods_Maker(resultSet.getString("Goods_Maker"));
                    goods.setClassification(resultSet.getString("Classification"));
                    goods.setSales_No(resultSet.getString("Sales_No"));
                    goods.setSales_Price(resultSet.getString("Sales_Price"));
                    goods.setImage(resultSet.getString("Image"));
                    goods.setSales_Flag(resultSet.getString("Sales_Flag"));
                    goods.setStore_No(resultSet.getString("Store_No"));
                    goods.setStore_Name(resultSet.getString("Store_Name"));
                    goods.setOpening_Time(resultSet.getTime("Opening_Time"));
                    goods.setClosing_Time(resultSet.getTime("Closing_Time"));
                    goods.setLatitude(resultSet.getDouble("Latitude"));
                    goods.setLongitude(resultSet.getDouble("Longitude"));

                    goodsArray.addGoods(goods);
                }
            }
        }

        return goodsArray;
    }



//    public SalesDetail getSalesDetail(String salesNo) throws SQLException {
//        SalesDetail salesDetail = null;
//
//        String sql = "SELECT g.Goods_Name, g.Goods_Maker, g.Classification, " +
//                     "s.Sales_Price, s.Image, s.Sales_No, st.Store_Name, st.Store_No, g.JAN_code " +
//                     "FROM goods g " +
//                     "LEFT JOIN sales s ON g.JAN_code = s.JAN_Code " +
//                     "LEFT JOIN store st ON s.Store_No = st.Store_No " +
//                     "WHERE s.Sales_No = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setString(1, salesNo);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    salesDetail = new SalesDetail();
//                    salesDetail.setGoods_Name(resultSet.getString("Goods_Name"));
//                    salesDetail.setGoods_Maker(resultSet.getString("Goods_Maker"));
//                    salesDetail.setClassification(resultSet.getString("Classification"));
//                    salesDetail.setSales_Price(resultSet.getString("Sales_Price"));
//                    salesDetail.setImage(resultSet.getString("Image"));
//                    salesDetail.setSales_No(resultSet.getString("Sales_No")); // Sales_No を設定
//                    salesDetail.setStore_Name(resultSet.getString("Store_Name"));
//                    salesDetail.setStore_No(resultSet.getString("Store_No")); // Store_No を設定
//                    salesDetail.setJAN_Code(resultSet.getString("JAN_code")); // JANコードを設定
//                }
//            }
//        }
//
//        return salesDetail;
//    }

    
    public StoreArrayBeans getAllStores() throws SQLException {
        StoreArrayBeans storeArray = new StoreArrayBeans();

        String sql = "SELECT Store_No, Store_Name, Store_Address1, Store_Address2, " +
                     "Opening_Time, Closing_Time, Store_Phone, Latitude, Longitude, Store_Image, Store_mail_Address " +
                     "FROM store";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                StoreBeans store = new StoreBeans();
                store.setStoreNo(resultSet.getString("Store_No"));
                store.setStoreName(resultSet.getString("Store_Name"));
                store.setFullAddress(resultSet.getString("Store_Address1") + " " + resultSet.getString("Store_Address2"));
                store.setOpeningTime(resultSet.getTime("Opening_Time"));
                store.setClosingTime(resultSet.getTime("Closing_Time"));
                store.setStorePhone(resultSet.getString("Store_Phone"));
                store.setLatitude(resultSet.getDouble("Latitude"));
                store.setLongitude(resultSet.getDouble("Longitude"));
                store.setImage(resultSet.getString("Store_Image"));
                store.setMailAddress(resultSet.getString("Store_mail_Address"));

                storeArray.addStore(store);
            }
        }

        return storeArray;
    }

    public StoreArrayBeans searchStoresByKeyword(String keyword) throws SQLException {
        StoreArrayBeans storeArray = new StoreArrayBeans();

        String sql = "SELECT Store_No, Store_Name, Store_Address1, Store_Address2, " +
                     "Opening_Time, Closing_Time, Store_Phone, Latitude, Longitude, Store_Image, Store_mail_Address " +
                     "FROM store WHERE Store_Name LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    StoreBeans store = new StoreBeans();
                    store.setStoreNo(resultSet.getString("Store_No"));
                    store.setStoreName(resultSet.getString("Store_Name"));
                    store.setFullAddress(resultSet.getString("Store_Address1") + " " + resultSet.getString("Store_Address2"));
                    store.setOpeningTime(resultSet.getTime("Opening_Time"));
                    store.setClosingTime(resultSet.getTime("Closing_Time"));
                    store.setStorePhone(resultSet.getString("Store_Phone"));
                    store.setLatitude(resultSet.getDouble("Latitude"));
                    store.setLongitude(resultSet.getDouble("Longitude"));
                    store.setImage(resultSet.getString("Store_Image"));
                    store.setMailAddress(resultSet.getString("Store_mail_Address"));

                    storeArray.addStore(store);
                }
            }
        }

        return storeArray;
    }
//    public StoreBeans getStoreDetail(String storeNo) throws SQLException {
//        StoreBeans store = null;
//
//        String sql = "SELECT Store_No, Store_Name, Opening_Time, Closing_Time, Store_Phone, " +
//                     "Store_mail_Address, Store_Postal_Code, Store_Prefecture, " +
//                     "Store_Address1, Store_Address2, Image " +
//                     "FROM store WHERE Store_No = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setString(1, storeNo);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    store = new StoreBeans();
//                    store.setStoreNo(resultSet.getString("Store_No"));
//                    store.setStoreName(resultSet.getString("Store_Name"));
//                    store.setOpeningTime(resultSet.getTime("Opening_Time"));
//                    store.setClosingTime(resultSet.getTime("Closing_Time"));
//                    store.setPhone(resultSet.getString("Store_Phone"));
//                    store.setMailAddress(resultSet.getString("Store_mail_Address"));
//                    store.setPostalCode(resultSet.getString("Store_Postal_Code"));
//                    store.setPrefecture(resultSet.getString("Store_Prefecture"));
//                    store.setAddress1(resultSet.getString("Store_Address1")); // 修正: Address1 を追加
//                    store.setAddress2(resultSet.getString("Store_Address2")); // 修正: Address2 を追加
//                    store.setImage(resultSet.getString("Image"));
//                }
//            }
//        }
//
//        return store;
//    }

}

