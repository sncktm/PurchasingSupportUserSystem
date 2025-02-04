package model;

public class ListBeans {
	
	private String List_No;
	private String List_Name;
	public ListBeans() {
		
	}

	public ListBeans(String List_No, String List_Name) {
		this.List_No = List_No;
		this.List_Name = List_Name;
		
	}
	
	public String getList_No() {
		return List_No;
	}

	public void setList_No(String list_No) {
		List_No = list_No;
	}



	public String getList_Name() {
		return List_Name;
	}

	public void setList_Name(String list_Name) {
		List_Name = list_Name;
	}


}
