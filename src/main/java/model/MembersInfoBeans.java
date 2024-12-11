package model;

public class MembersInfoBeans {

	
	private String members_number;
	private String family_name;
	private String first_name;
	private String family_name_furigana;
	private String first_name_furigana;
	private String gender;
	private String members_Postal_Code;
	private String members_Prefecture;
	private String members_Address1;
	private String members_Address2;
	private String members_Phonenumber;
	private String members_Mail_Address;
	public MembersInfoBeans() {
		
	}
	
	public MembersInfoBeans(String members_number,String family_name,String first_name,String family_name_furigana,String first_name_furigana,String gender,String members_Postal_Code,String members_Prefecture,String members_Address1,String members_Address2, String members_Phonenumber, String members_Mail_Address) {
		this.members_number = members_number;
		this.family_name = family_name;
		this.first_name = first_name;
		this.family_name_furigana = family_name_furigana;
		this.first_name_furigana = first_name_furigana;
		this.gender = gender;
		this.members_Postal_Code = members_Postal_Code;
		this.members_Prefecture = members_Prefecture;
		this.members_Address1 = members_Address1;
		this.members_Address2 = members_Address2;
		this.members_Phonenumber = members_Phonenumber;
		this.members_Mail_Address = members_Mail_Address;
		
	}
	
	public String getmembers_number() {
        return members_number;
    }

    public void setmembers_number(String members_number) {
        this.members_number = members_number;
    }

    
    public String getfamily_name() {
        return family_name;
    }

    public void setfamily_name(String family_name) {
        this.family_name = family_name;
    }

    
    public String getfirst_name() {
        return first_name;
    }

    public void setfirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getfamily_name_furigana() {
        return family_name_furigana;
    }
    public void setfamily_name_furigana(String family_name_furigana) {
            this.family_name_furigana = family_name_furigana;
    }
    public String getfirst_name_furigana() {
        return first_name_furigana;
    }

    public void setfirst_name_furigana(String first_name_furigana) {
        this.first_name_furigana = first_name_furigana;
    }
    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }
    public String getmembers_Postal_Code() {
        return members_Postal_Code;
    }

    public void setmembers_Postal_Code(String members_Postal_Code) {
        this.members_Postal_Code = members_Postal_Code;
    }
    public String getmembers_Prefecture() {
        return members_Prefecture;
    }

    public void setmembers_Prefecture(String members_Prefecture) {
        this.members_Prefecture = members_Prefecture;
    }
    public String getmembers_Address1() {
        return members_Address1;
    }

    public void setmembers_Address1(String members_Address1) {
        this.members_Address1 = members_Address1;
    }
    public String getmembers_Address2() {
        return members_Address2;
    }

    public void setmembers_Address2(String members_Address2) {
        this.members_Address2 = members_Address2;
    }
    public String getmembers_Phonenumber() {
        return members_Phonenumber;
    }
    public void setmembers_Phonenumber(String members_Phonenumber) {
        this.members_Phonenumber = members_Phonenumber;
    }
    public String getmembers_Mail_Address() {
        return members_Mail_Address;
    }
    public void setmembers_Mail_Address(String members_Mail_Address) {
        this.members_Mail_Address = members_Mail_Address;
    }

	public void add(MembersInfoBeans mem) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
   
}
