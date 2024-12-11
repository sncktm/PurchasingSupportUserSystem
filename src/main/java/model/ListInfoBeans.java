//リスト閲覧

package model;

import java.sql.Date;

public class ListInfoBeans {
	private String List_No;
    private String Members_No;
    private String List_Name;
	private Date List_Date;
	

    public ListInfoBeans(String List_No, String Members_No,String List_Name,Date List_Date) {
        this.List_No = List_No;
        this.Members_No = Members_No;
        this.List_Name = List_Name;
        this.List_Date = List_Date;
    }

    
//	public ListInfoBeans() {
//		// TODO 自動生成されたコンストラクター・スタブ
//	}


	public String getList_No() {
        return List_No;
    }

    public String getMembers_No() {
        return Members_No;
    }
    public String getList_Name() {
        return List_Name;
    }

    public Date getList_Date() {
        return List_Date;
    }

    public void setList_No(String List_No) {
        this.List_No = List_No;
    }

    public void setMembers_No(String Member_No) {
        this.Members_No = Member_No;
    }
    public void setList_Name(String List_Name) {
        this.List_Name = List_Name;
    }

    public void setList_Date(Date List_Date) {
        this.List_Date = List_Date;
    }

}
