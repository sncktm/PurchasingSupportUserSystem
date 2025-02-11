package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public GoodsArrayBeans getAllGoods(boolean showOnlyAvailable) throws SQLException {
        GoodsArrayBeans goodsArray = new GoodsArrayBeans();
        LocalDateTime now = LocalDateTime.now();

        String sql = "SELECT g.JAN_code, g.Goods_Name, g.Goods_Maker, g.Classification, " +
                     "s.Sales_No, s.Sales_Price, s.Image, s.Sales_Flag, " +
                     "st.Store_No, st.Store_Name, st.Opening_Time, st.Closing_Time, " +
                     "st.Latitude, st.Longitude, " +
                     "ts.Time_Sale_No, ts.Start_Date, ts.Start_Time, ts.End_Date, ts.End_Time, " +
                     "ts.Repeat_Pattern, ts.Repeat_Value, ts.Timesale_Application_Flag, " +
                     "tsg.Time_Sales_Prise, tsg.Timesale_goods_Application_Flag " +
                     "FROM sales s " +
                     "INNER JOIN goods g ON g.JAN_code = s.JAN_Code " +
                     "INNER JOIN store st ON s.Store_No = st.Store_No " +
                     "LEFT JOIN time_sale_goods tsg ON s.Sales_No = tsg.Sales_No " +
                     "LEFT JOIN time_sale ts ON tsg.Time_Sale_No = ts.Time_Sale_No ";

        if (showOnlyAvailable) {
            sql += " WHERE s.Sales_Flag = '1' AND " +
                   "st.Opening_Time IS NOT NULL AND st.Closing_Time IS NOT NULL AND " +
                   "TIME(?) BETWEEN st.Opening_Time AND st.Closing_Time";
        }

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (showOnlyAvailable) {
                statement.setTime(1, java.sql.Time.valueOf(now.toLocalTime()));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    GoodsBeans goods = new GoodsBeans();
                    setGoodsBasicInfo(goods, resultSet);
                    setTimeSaleInfo(goods, resultSet, now);
                    goodsArray.addGoods(goods);
                }
            }
        }

        return filterDuplicateSalesNo(goodsArray);
    }

    public GoodsArrayBeans searchGoodsByKeywordAndAvailability(String keyword, boolean showOnlyAvailable) throws SQLException {
        GoodsArrayBeans goodsArray = new GoodsArrayBeans();
        LocalDateTime now = LocalDateTime.now();

        String sql = "SELECT g.JAN_code, g.Goods_Name, g.Goods_Maker, g.Classification, " +
                     "s.Sales_No, s.Sales_Price, s.Image, s.Sales_Flag, " +
                     "st.Store_No, st.Store_Name, st.Opening_Time, st.Closing_Time, " +
                     "st.Latitude, st.Longitude, " +
                     "ts.Time_Sale_No, ts.Start_Date, ts.Start_Time, ts.End_Date, ts.End_Time, " +
                     "ts.Repeat_Pattern, ts.Repeat_Value, ts.Timesale_Application_Flag, " +
                     "tsg.Time_Sales_Prise, tsg.Timesale_goods_Application_Flag " +
                     "FROM sales s " +
                     "INNER JOIN goods g ON g.JAN_code = s.JAN_Code " +
                     "INNER JOIN store st ON s.Store_No = st.Store_No " +
                     "LEFT JOIN time_sale_goods tsg ON s.Sales_No = tsg.Sales_No " +
                     "LEFT JOIN time_sale ts ON tsg.Time_Sale_No = ts.Time_Sale_No " +
                     "WHERE g.Goods_Name LIKE ?";

        if (showOnlyAvailable) {
            sql += " AND s.Sales_Flag = '1' AND " +
                   "st.Opening_Time IS NOT NULL AND st.Closing_Time IS NOT NULL AND " +
                   "TIME(?) BETWEEN st.Opening_Time AND st.Closing_Time";
        }

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");
            if (showOnlyAvailable) {
                statement.setTime(2, java.sql.Time.valueOf(now.toLocalTime()));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    GoodsBeans goods = new GoodsBeans();
                    setGoodsBasicInfo(goods, resultSet);
                    setTimeSaleInfo(goods, resultSet, now);
                    goodsArray.addGoods(goods);
                }
            }
        }

        return filterDuplicateSalesNo(goodsArray);
    }

    private void setGoodsBasicInfo(GoodsBeans goods, ResultSet resultSet) throws SQLException {
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
        
        java.sql.Time openingTime = resultSet.getTime("Opening_Time");
        goods.setOpening_Time(openingTime != null ? openingTime : null);
        
        java.sql.Time closingTime = resultSet.getTime("Closing_Time");
        goods.setClosing_Time(closingTime != null ? closingTime : null);
        
        goods.setLatitude(resultSet.getDouble("Latitude"));
        goods.setLongitude(resultSet.getDouble("Longitude"));
    }

    private void setTimeSaleInfo(GoodsBeans goods, ResultSet resultSet, LocalDateTime now) throws SQLException {
        goods.setTimeSaleNo(resultSet.getInt("Time_Sale_No"));
        
        java.sql.Date startDate = resultSet.getDate("Start_Date");
        if (startDate != null) {
            goods.setTimeSaleStartDate(startDate.toLocalDate());
        }
        
        java.sql.Time startTime = resultSet.getTime("Start_Time");
        if (startTime != null) {
            goods.setTimeSaleStartTime(startTime.toLocalTime());
        }
        
        java.sql.Date endDate = resultSet.getDate("End_Date");
        if (endDate != null) {
            goods.setTimeSaleEndDate(endDate.toLocalDate());
        }
        
        java.sql.Time endTime = resultSet.getTime("End_Time");
        if (endTime != null) {
            goods.setTimeSaleEndTime(endTime.toLocalTime());
        }
        
        goods.setTimeSalePrice(resultSet.getInt("Time_Sales_Prise"));
        goods.setTimesaleApplicationFlag(resultSet.getString("Timesale_Application_Flag"));
        goods.setTimesaleGoodsApplicationFlag(resultSet.getString("Timesale_goods_Application_Flag"));
        goods.setRepeatPattern(resultSet.getString("Repeat_Pattern"));
        goods.setRepeatValue(resultSet.getString("Repeat_Value"));

        boolean isTimeSale = isTimeSaleActive(goods, now);
        goods.setIsTimeSale(isTimeSale);
    }

    private boolean isTimeSaleActive(GoodsBeans goods, LocalDateTime now) {
        if (!"on".equals(goods.getTimesaleApplicationFlag()) || !"on".equals(goods.getTimesaleGoodsApplicationFlag())) {
            return false;
        }

        LocalDate startDate = goods.getTimeSaleStartDate();
        LocalTime startTime = goods.getTimeSaleStartTime();
        LocalDate endDate = goods.getTimeSaleEndDate();
        LocalTime endTime = goods.getTimeSaleEndTime();

        if (startDate == null || startTime == null || endDate == null || endTime == null) {
            return false;
        }

        LocalDate currentDate = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        // Check if current date is within the time sale period
        if (currentDate.isBefore(startDate) || currentDate.isAfter(endDate)) {
            return false;
        }

        // Check if current time is within the time sale hours
        if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
            return false;
        }

        String repeatPattern = goods.getRepeatPattern();
        String repeatValue = goods.getRepeatValue();

        if (repeatPattern == null) {
            return false;
        }

        switch (repeatPattern) {
            case "daily":
                return true; // daily の場合は常に true を返す
            case "weekly":
                if (repeatValue == null) return false;
                String[] activeDays = repeatValue.split(",");
                String currentDayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase();
                return Arrays.asList(activeDays).contains(currentDayOfWeek);
            case "monthly":
                if (repeatValue == null) return false;
                String[] activeDates = repeatValue.split(",");
                int currentDayOfMonth = now.getDayOfMonth();
                return Arrays.stream(activeDates)
                    .map(Integer::parseInt)
                    .anyMatch(date -> date == currentDayOfMonth);
            default:
                return false;
        }
    }

    private GoodsArrayBeans filterDuplicateSalesNo(GoodsArrayBeans goodsArray) {
        GoodsArrayBeans filteredArray = new GoodsArrayBeans();
        goodsArray.getGoodsArray().stream()
            .collect(Collectors.groupingBy(GoodsBeans::getSales_No))
            .forEach((salesNo, goods) -> {
                goods.stream()
                    .filter(GoodsBeans::isTimeSale)
                    .findFirst()
                    .ifPresentOrElse(
                        filteredArray::addGoods,
                        () -> filteredArray.addGoods(goods.get(0))
                    );
            });
        return filteredArray;
    }

    
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