package model;

import java.sql.Date;
import java.sql.Time;

public class TimesaleBeans {

    // 列挙型の定義
	public enum RepeatPattern {
	    daily, weekly, monthly;
	}

    private int Time_Sale_No;
    private String Time_Sale_Name;
    private Date Start_Date;
    private Time Start_Time;
    private Date End_Date;
    private Time End_Time;
    private RepeatPattern Repeat_Pattern; // 修正: enum型を適切に定義
    private String Repeat_Value;
    private String Store_No;
    private String Timesale_Application_Flag;

    // コンストラクタ
    public TimesaleBeans(int Time_Sale_No, String Time_Sale_Name, Date Start_Date, Time Start_Time, 
                         Date End_Date, Time End_Time, RepeatPattern Repeat_Pattern,String Repeat_Value,
                         String Store_No,String Timesale_Application_Flag) {
        this.Time_Sale_No = Time_Sale_No;
        this.Time_Sale_Name = Time_Sale_Name;
        this.Start_Date = Start_Date;
        this.Start_Time = Start_Time;
        this.End_Date = End_Date;
        this.End_Time = End_Time;
        this.Repeat_Pattern = Repeat_Pattern; // 修正: 適切にセット
        this.Repeat_Value = Repeat_Value;
        this.Store_No = Store_No;
        this.Timesale_Application_Flag = Timesale_Application_Flag;
    }

    // ゲッターとセッター
    public int getTime_Sale_No() {
        return Time_Sale_No;
    }

    public void setTime_Sale_No(int Time_Sale_No) {
        this.Time_Sale_No = Time_Sale_No;
    }

    public String getTime_Sale_Name() {
        return Time_Sale_Name;
    }

    public void setTime_Sale_Name(String Time_Sale_Name) {
        this.Time_Sale_Name = Time_Sale_Name;
    }

    public Date getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(Date Start_Date) {
        this.Start_Date = Start_Date;
    }

    public Time getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(Time Start_Time) {
        this.Start_Time = Start_Time;
    }

    public Date getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(Date End_Date) {
        this.End_Date = End_Date;
    }

    public Time getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(Time End_Time) {
        this.End_Time = End_Time;
    }

    public RepeatPattern getRepeat_Pattern() {
        return Repeat_Pattern;
    }

    public void setRepeat_Pattern(RepeatPattern Repeat_Pattern) {
        this.Repeat_Pattern = Repeat_Pattern;
    }
    public String getRepeat_Value() {
        return Repeat_Value;
    }

    public void setRepeat_Value(String Repeat_Value) {
        this.Repeat_Value = Repeat_Value;
    }
    public String getStore_No() {
        return Store_No;
    }

    public void setStore_No(String Store_No) {
        this.Store_No = Store_No;
    }
    public String getTimesale_Application_Flag() {
        return Timesale_Application_Flag;
    }

    public void setTimesale_Application_Flag(String Timesale_Application_Flag) {
        this.Timesale_Application_Flag = Timesale_Application_Flag;
    }
}
