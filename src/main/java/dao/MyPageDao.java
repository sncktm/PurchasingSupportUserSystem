package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyPageDao {

    // データベース接続情報
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/purchasing_support_system_DB";
    private final String DB_USER = "root";
    private  final String DB_PASS = "mysql";

    // 合計ポイントを取得
    public int getTotalPoints(String member_No) {
        int totalPoints = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバを読み込めませんでした");
		}
	
		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "SELECT p.Member_No, p.Point_size - IFNULL(SUM(ph.Point_size), 0) AS available_points FROM Point p LEFT JOIN Point_history ph ON p.Member_No = ph.Member_No AND Member_No = ? GROUP BY  p.member_id";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, member_No);

			ResultSet rs = pStmt.executeQuery();

			if(rs.next()) {
				totalPoints = rs.getInt("available_points");
					
			} else {
				totalPoints = 9999;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 9999;
		}
		return totalPoints;
		
    }
}
