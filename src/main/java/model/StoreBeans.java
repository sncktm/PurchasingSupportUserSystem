package model;

import java.sql.Time;

public class StoreBeans {
    private String storeNo;        // 店舗番号
    private String storeName;      // 店舗名
    private Time openingTime;      // 開店時間
    private Time closingTime;      // 閉店時間
    private String storePhone;     // 店舗電話番号
    private String storeMailAddress; // 店舗メールアドレス
    private String storePostalCode; // 店舗郵便番号
    private String storePrefecture; // 店舗都道府県
    private String storeAddress1;  // 店舗住所1
    private String storeAddress2;  // 店舗住所2
    private double latitude;       // 緯度
    private double longitude;      // 経度
    private double distance;
    private String store_image;          // 店舗画像のURL
    private String mailAddress;

    // ゲッターとセッター
    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreMailAddress() {
        return storeMailAddress;
    }

    public void setStoreMailAddress(String storeMailAddress) {
        this.storeMailAddress = storeMailAddress;
    }

    public String getStorePostalCode() {
        return storePostalCode;
    }

    public void setStorePostalCode(String storePostalCode) {
        this.storePostalCode = storePostalCode;
    }

    public String getStorePrefecture() {
        return storePrefecture;
    }

    public void setStorePrefecture(String storePrefecture) {
        this.storePrefecture = storePrefecture;
    }

    public String getStoreAddress1() {
        return storeAddress1;
    }

    public void setStoreAddress1(String storeAddress1) {
        this.storeAddress1 = storeAddress1;
    }

    public String getStoreAddress2() {
        return storeAddress2;
    }

    public void setStoreAddress2(String storeAddress2) {
        this.storeAddress2 = storeAddress2;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFullAddress() {
        return storePrefecture + storeAddress1 + (storeAddress2 != null ? " " + storeAddress2 : "");
    }

    public void setFullAddress(String fullAddress) {
        // フルアドレスを分割して各フィールドに設定
        String[] parts = fullAddress.split(" ", 3);
        if (parts.length >= 1) this.storePrefecture = parts[0];
        if (parts.length >= 2) this.storeAddress1 = parts[1];
        if (parts.length >= 3) this.storeAddress2 = parts[2];
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    // 新しく追加するメソッド
    public String getAddress() {
        return getFullAddress();
    }

    public String getPhone() {
        return getStorePhone();
    }

    public String getImage() {
        return this.store_image;
    }

    public void setImage(String store_image) {
        this.store_image = store_image;
    }
    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}

