package model;

public class AdCommodityBeans {

	private String Advertisement_No;
	private String Store_No;
	private String Advertisement_type;
	private String Advertisement_Image;
	private String Advertisement_Explanation;
	private String Advertisement_title;
	private String Advertisement_priority;
	
	public AdCommodityBeans (String Advertisement_No ,String Store_No, String Advertisement_type, String Advertisement_Image, String Advertisement_Explanation, String Advertisement_title,String Advertisement_priority) {
		
		this.Advertisement_No = Advertisement_No;
		this.Store_No = Store_No;
		this.Advertisement_type = Advertisement_type;
		this.Advertisement_Image = Advertisement_Image;
		this.Advertisement_Explanation = Advertisement_Explanation;
		this.Advertisement_title = Advertisement_title;
		this.Advertisement_priority = Advertisement_priority;
	}

	public String getAdvertisement_No() {
		return Advertisement_No;
	}

	public void setAdvertisement_No(String advertisement_No) {
		Advertisement_No = advertisement_No;
	}


	public String getAdvertisement_type() {
		return Advertisement_type;
	}

	public void setAdvertisement_type(String advertisement_type) {
		Advertisement_type = advertisement_type;
	}

	public String getAdvertisement_Image() {
		return Advertisement_Image;
	}

	public void setAdvertisement_Image(String advertisement_Image) {
		Advertisement_Image = advertisement_Image;
	}

	public String getAdvertisement_Explanation() {
		return Advertisement_Explanation;
	}

	public void setAdvertisement_Explanation(String advertisement_Explanation) {
		Advertisement_Explanation = advertisement_Explanation;
	}

	public String getAdvertisement_title() {
		return Advertisement_title;
	}

	public void setAdvertisement_title(String advertisement_title) {
		Advertisement_title = advertisement_title;
	}

	public String getStore_No() {
		return Store_No;
	}

	public void setStore_No(String store_No) {
		Store_No = store_No;
	}
	
	public String getAdvertisement_priority() {
		return Advertisement_priority;
	}

	public void setAdvertisement_priority(String Advertisement_priority) {
		this.Advertisement_priority = Advertisement_priority;
	}
	
}
