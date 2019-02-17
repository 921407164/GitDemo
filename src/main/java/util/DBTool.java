package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBTool {

	private static String driver;
	private static String url;
	private static String user;
	private static String pwd;
	
	static {
		Properties p = new Properties();
		try {
			//���������ļ�
			p.load(DBTool.class.getClassLoader()
				.getResourceAsStream("db.properties"));
			//��ȡ���Ӳ���
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			user = p.getProperty("user");
			pwd = p.getProperty("pwd");
			//ע������
			Class.forName(driver);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(
				"����db.propertiesʧ��", e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(
				"�Ҳ����������", e);
		}
	}
	
	public static Connection getConnection() 
		throws SQLException {
		return DriverManager
			.getConnection(url, user, pwd);
	}
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(
					"�ر�����ʧ��", e);
			}
		}
	}
	
}









