package model;



public class ListDetailsBeans {
    private String List_No;
    private String Sales_No;
    
   

    public ListDetailsBeans(String List_No, String Sales_No) {
        this.List_No = List_No;
        this.Sales_No = Sales_No;
        
       
    }

//    public ListdetailsBeans() {
//		// TODO 自動生成されたコンストラクター・スタブ
//	}

	public String getList_No() {
        return List_No;
    }

    public String getSales_No() {
        return Sales_No;
    }

    public void setList_No(String List_No) {
        this.List_No = List_No;
    }

    public void setSales_Namber(String Sales_No) {
        this.Sales_No = Sales_No;
    }
   
   
}