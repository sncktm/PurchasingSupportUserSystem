package model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class GoodsBeans implements Serializable {
    private String JAN_code;          // 商品コード
    private String Goods_Name;        // 商品名
    private String Goods_Maker;       // メーカー
    private String Classification;    // 分類
    private String Sales_No;          // 販売番号
    private String Store_No;          // 店舗番号
    private String Sales_Price;       // 販売価格
    private String Image;             // 画像パス
    private String Sales_Flag;        // 販売状況
    private Date Update_Date;         // 更新日時

    // 追加されたフィールド
    private String Store_Name;        // 店舗名
    private String Store_Address;     // 店舗住所
    private Time Opening_Time;        // 開店時間
    private Time Closing_Time;        // 閉店時間
    private String coordinate;        // 座標
    private double Latitude; // 緯度を追加
    private double Longitude; // 経度を追加

    // コンストラクタ（全フィールド初期化）
    public GoodsBeans(String JAN_code, String Goods_Name, String Goods_Maker, String Classification,
                      String Sales_No, String Store_No, String Sales_Price, String Image,
                      String Sales_Flag, Date Update_Date) {
        this.JAN_code = JAN_code;
        this.Goods_Name = Goods_Name;
        this.Goods_Maker = Goods_Maker;
        this.Classification = Classification;
        this.Sales_No = Sales_No;
        this.Store_No = Store_No;
        this.Sales_Price = Sales_Price;
        this.Image = Image;
        this.Sales_Flag = Sales_Flag;
        this.Update_Date = Update_Date;
    }

    // デフォルトコンストラクタ
    public GoodsBeans() {}

    // GetterとSetter（基本フィールド）
    public String getJAN_code() { return JAN_code; }
    public void setJAN_code(String JAN_code) { this.JAN_code = JAN_code; }

    public String getGoods_Name() { return Goods_Name; }
    public void setGoods_Name(String Goods_Name) { this.Goods_Name = Goods_Name; }

    public String getGoods_Maker() { return Goods_Maker; }
    public void setGoods_Maker(String Goods_Maker) { this.Goods_Maker = Goods_Maker; }

    public String getClassification() { return Classification; }
    public void setClassification(String Classification) { this.Classification = Classification; }

    public String getSales_No() { return Sales_No; }
    public void setSales_No(String Sales_No) { this.Sales_No = Sales_No; }

    public String getStore_No() { return Store_No; }
    public void setStore_No(String Store_No) { this.Store_No = Store_No; }

    public String getSales_Price() { return Sales_Price; }
    public void setSales_Price(String Sales_Price) { this.Sales_Price = Sales_Price; }

    public String getImage() { return Image; }
    public void setImage(String Image) { this.Image = Image; }

    public String getSales_Flag() { return Sales_Flag; }
    public void setSales_Flag(String Sales_Flag) { this.Sales_Flag = Sales_Flag; }

    public Date getUpdate_Date() { return Update_Date; }
    public void setUpdate_Date(Date Update_Date) { this.Update_Date = Update_Date; }

    // GetterとSetter（追加フィールド）
    public String getStore_Name() { return Store_Name; }
    public void setStore_Name(String Store_Name) { this.Store_Name = Store_Name; }

    public String getStore_Address() { return Store_Address; }
    public void setStore_Address(String Store_Address) { this.Store_Address = Store_Address; }

    public Time getOpening_Time() { return Opening_Time; }
    public void setOpening_Time(Time Opening_Time) { this.Opening_Time = Opening_Time; }

    public Time getClosing_Time() { return Closing_Time; }
    public void setClosing_Time(Time Closing_Time) { this.Closing_Time = Closing_Time; }

    public String getCoordinate() { return coordinate; }
    public void setCoordinate(String coordinate) { this.coordinate = coordinate; }

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
}

