package model;

public class SalesDataBeans {
    private String Sales_No;
    private String JAN_Code;
    private String Store_No;
    private int Sales_Price;
    private String Image;
    private String Sales_Flag;
    private String Goods_Name;

    public SalesDataBeans(String salesNo, String JANCode, String storeNo, int salesPrice, String image, String salesFlag,String Goods_Name) {
        this.Sales_No = salesNo;
        this.JAN_Code = JANCode;
        this.Store_No = storeNo;
        this.Sales_Price = salesPrice;
        this.Image = image;
        this.Sales_Flag = salesFlag;
        this.Goods_Name = Goods_Name;
    }

    public String getSales_No() {
        return Sales_No;
    }

    public String getJAN_Code() {
        return JAN_Code;
    }

    public String getStore_No() {
        return Store_No;
    }

    public int getSales_Price() {
        return Sales_Price;
    }

    public String getImage() {
        return Image;
    }

    public String getSales_Flag() {
        return Sales_Flag;
    }

    public void getSales_No(String salesNo) {
        this.Sales_No = salesNo;
    }

    public void getJAN_Code(String janCode) {
        this.JAN_Code = janCode;
    }

    public void getStore_No(String storeNo) {
        this.Store_No = storeNo;
    }

    public void getSales_Price(int salesPrice) {
        this.Sales_Price = salesPrice;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public void getSales_Flag(String salesFlag) {
        this.Sales_Flag = salesFlag;
    }
    public String getGoods_Name() {
        return Goods_Name;
    }

    public void setGoods_Name(String Goods_Name) {
        this.Goods_Name = Goods_Name;
    }
}
