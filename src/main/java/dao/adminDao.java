package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.admin;
import util.DBUtil;

public class adminDao {
	
	public admin findCode(String adminCode) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnecion();
			String sql = 
					  "select * from admin_info "
					+ "where admin_code=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, adminCode);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				admin admin = new admin();
				admin.setAdminId(rs.getInt("admin_id"));
				admin.setAdminCode(rs.getString("admin_code"));
				admin.setPassword(rs.getString("password"));
				admin.setName(rs.getString("name"));
				admin.setTelepone(rs.getString("telephone"));
				admin.setEmail(rs.getString("email"));
				admin.setEnrolldate(rs.getTimestamp("enrolldate"));
				return admin;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("≤È—Ø‘±π§ ß∞‹");
		}  finally {
			DBUtil.close(conn);;
		}	
		
		return null;
		
	}
	
	public static void main(String[] args) {
		adminDao a = new adminDao();
		admin admin = a.findCode("liubei");
		System.out.println(admin.getAdminCode());
	}
}
