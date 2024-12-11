package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.MembersInfoBeans;


public class MembersDAO {
	private  final String URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private  final String DB_USER = "root";
    private  final String DB_PASS = "mysql";
    
    public MembersInfoBeans findAll(String user_No) {
    	MembersInfoBeans member = new MembersInfoBeans();
    	
    	System.out.println(user_No);
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
		}
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)){
        	String sql = "SELECT * FROM Members WHERE Member_No = ?";

        	
        	PreparedStatement stmt = conn.prepareStatement(sql);
        	stmt.setString(1, user_No);
        	
        	
        	ResultSet rs = stmt.executeQuery();
            
            

            if (rs.next()) {
            	
            	member.setmembers_number(rs.getString("member_no"));
            	member.setfamily_name(rs.getString("family_name"));
            	member.setfirst_name(rs.getString("first_name"));
            	member.setfamily_name_furigana(rs.getString("family_name_furigana"));
            	member.setfirst_name_furigana(rs.getString("first_name_furigana"));
            	member.setgender(rs.getString("gender"));
                member.setmembers_Postal_Code(rs.getString("member_Postal_Code"));
                member.setmembers_Prefecture(rs.getString("member_Prefecture"));
                member.setmembers_Address1(rs.getString("member_Address1"));
                member.setmembers_Address2(rs.getString("member_Address2"));
                member.setmembers_Phonenumber(rs.getString("member_Phonenumber"));
                member.setmembers_Mail_Address(rs.getString("member_Mail_Address"));
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return member;
    
	}
}
