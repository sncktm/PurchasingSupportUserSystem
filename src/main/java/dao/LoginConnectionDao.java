package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.MemberBeans;

public class LoginConnectionDao {
	
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
	private final String DB_USER = "root";
	private final String DB_PASS = "mysql";

	public MemberBeans StoreLoginSearch(String member_email, String member_password) {
		
		MemberBeans member = new MemberBeans();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
		}
	
		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "SELECT * FROM Members_Login, Members WHERE Members_Login.Member_No = Members.Member_No AND Member_Mail_Address = ? AND Member_password = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, member_email);
			pStmt.setString(2, member_password);

			ResultSet rs = pStmt.executeQuery();

			if(rs.next()) {
				member.setMember_no(rs.getString("MEMBER_NO"));
				member.setFamily_name(rs.getString("Family_name"));
				member.setFirst_name(rs.getString("First_name"));			
			} else {
				member = null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("1");
			return null;
		}
		return member;
		
	}

}
