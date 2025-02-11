package model;

public class TimesaleGoodsBeans {
	private int Time_Sale_No;
	private String Goods_Name;
	private String Goods_Maker;
	private int Time_Sales_Price;
	private String Timesale_goods_Application_Flag;
	private int Sales_Price;
	
	public TimesaleGoodsBeans(int Time_Sale_No,String Goods_Name,String Goods_Maker,int Time_Sales_Price,String Timesale_goods_Application_Flag,int Sales_Price) {
		this.Time_Sale_No = Time_Sale_No;
		this.Goods_Name = Goods_Name;
		this.Goods_Maker = Goods_Maker;
		this.Time_Sales_Price = Time_Sales_Price;
		this.Timesale_goods_Application_Flag = Timesale_goods_Application_Flag;
		this.Sales_Price = Sales_Price;
	}
	public int getTime_Sale_No() {
        return Time_Sale_No;
    }

    public void setTime_Sale_No(int Time_Sale_No) {
        this.Time_Sale_No = Time_Sale_No;
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
    public int getTime_Sales_Price() {
        return Time_Sales_Price;
    }

    public void setTime_Sales_Price(int Time_Sales_Price) {
        this.Time_Sales_Price = Time_Sales_Price;
    }
    public String getTimesale_goods_Application_Flag() {
        return Timesale_goods_Application_Flag;
    }

    public void setTimesale_goods_Application_Flag(String Timesale_goods_Application_Flag) {
        this.Timesale_goods_Application_Flag = Timesale_goods_Application_Flag;
    }
    public int getSales_Price() {
        return Sales_Price;
    }

    public void setSales_Price(int Sales_Price) {
        this.Sales_Price = Sales_Price;
    }

}
