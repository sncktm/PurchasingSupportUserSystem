package model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class GoodsBeans implements Serializable {
    private String JAN_code;
    private String Goods_Name;
    private String Goods_Maker;
    private String Classification;
    private String Sales_Price;
    private String Image;
    private String Sales_Flag;
    private String Store_Name;
    private String Store_No;
    private Time Opening_Time;
    private Time Closing_Time;
    private String Sales_No; // Sales_No を追加
    private double Latitude; // 緯度を追加
    private double Longitude; // 経度を追加
    private int timeSaleNo;
    private LocalDate timeSaleEndDate;
    private LocalTime timeSaleEndTime;
    private int timeSalePrice;
    private boolean isTimeSale;
    private LocalDate timeSaleStartDate;
    private LocalTime timeSaleStartTime;
    private String timesaleApplicationFlag;
    private String timesaleGoodsApplicationFlag;
    private String repeatPattern;
    private String repeatValue;

    // GetterとSetter
    public String getSales_No() {
        return Sales_No;
    }

    public void setSales_No(String Sales_No) {
        this.Sales_No = Sales_No;
    }

    public String getJAN_code() {
        return JAN_code;
    }

    public void setJAN_code(String JAN_code) {
        this.JAN_code = JAN_code;
    }

    public String getGoods_Name() {
        return Goods_Name;
    }

    public void setGoods_Name(String Goods_Name) {
        this.Goods_Name = Goods_Name;
    }

    public String getGoods_Maker() {
        return Goods_Maker;
    }

    public void setGoods_Maker(String Goods_Maker) {
        this.Goods_Maker = Goods_Maker;
    }

    public String getClassification() {
        return Classification;
    }

    public void setClassification(String Classification) {
        this.Classification = Classification;
    }

    public String getSales_Price() {
        return Sales_Price;
    }

    public void setSales_Price(String Sales_Price) {
        this.Sales_Price = Sales_Price;
    }

    public String getImage() {
        return Image != null ? Image : "img/defaultImage.png";
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getSales_Flag() {
        return Sales_Flag;
    }

    public void setSales_Flag(String Sales_Flag) {
        this.Sales_Flag = Sales_Flag;
    }

    public String getStore_Name() {
        return Store_Name;
    }

    public void setStore_Name(String Store_Name) {
        this.Store_Name = Store_Name;
    }

    public Time getOpening_Time() {
        return Opening_Time;
    }

    public void setOpening_Time(Time Opening_Time) {
        this.Opening_Time = Opening_Time;
    }

    public Time getClosing_Time() {
        return Closing_Time;
    }

    public void setClosing_Time(Time Closing_Time) {
        this.Closing_Time = Closing_Time;
    }
    
    public String getStore_No() {
        return Store_No;
    }
    
    public void setStore_No(String Store_No) {
        this.Store_No = Store_No;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public int getTimeSaleNo() {
        return timeSaleNo;
    }

    public void setTimeSaleNo(int timeSaleNo) {
        this.timeSaleNo = timeSaleNo;
    }

    public LocalDate getTimeSaleEndDate() {
        return timeSaleEndDate;
    }

    public void setTimeSaleEndDate(LocalDate timeSaleEndDate) {
        this.timeSaleEndDate = timeSaleEndDate;
    }

    public LocalTime getTimeSaleEndTime() {
        return timeSaleEndTime;
    }

    public void setTimeSaleEndTime(LocalTime timeSaleEndTime) {
        this.timeSaleEndTime = timeSaleEndTime;
    }

    public int getTimeSalePrice() {
        return timeSalePrice;
    }

    public void setTimeSalePrice(int timeSalePrice) {
        this.timeSalePrice = timeSalePrice;
    }

    public boolean isTimeSale() {
        return isTimeSale;
    }

    public void setIsTimeSale(boolean isTimeSale) {
        this.isTimeSale = isTimeSale;
    }

    public LocalDate getTimeSaleStartDate() {
        return timeSaleStartDate;
    }

    public void setTimeSaleStartDate(LocalDate timeSaleStartDate) {
        this.timeSaleStartDate = timeSaleStartDate;
    }

    public LocalTime getTimeSaleStartTime() {
        return timeSaleStartTime;
    }

    public void setTimeSaleStartTime(LocalTime timeSaleStartTime) {
        this.timeSaleStartTime = timeSaleStartTime;
    }

    public String getTimesaleApplicationFlag() {
        return timesaleApplicationFlag;
    }

    public void setTimesaleApplicationFlag(String timesaleApplicationFlag) {
        this.timesaleApplicationFlag = timesaleApplicationFlag;
    }

    public String getTimesaleGoodsApplicationFlag() {
        return timesaleGoodsApplicationFlag;
    }

    public void setTimesaleGoodsApplicationFlag(String timesaleGoodsApplicationFlag) {
        this.timesaleGoodsApplicationFlag = timesaleGoodsApplicationFlag;
    }

    public String getRepeatPattern() {
        return repeatPattern;
    }

    public void setRepeatPattern(String repeatPattern) {
        this.repeatPattern = repeatPattern;
    }

    public String getRepeatValue() {
        return repeatValue;
    }

    public void setRepeatValue(String repeatValue) {
        this.repeatValue = repeatValue;
    }
}

