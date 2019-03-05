package util;
/**
 * ���ӳع�����
 * @author lcd
 *
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtil {
	
	//���ӳض���-�� DBCP�ṩ
	private static BasicDataSource ds;
	
	static {
		Properties p = new Properties();
		try {
			p.load(DBUtil.class.getClassLoader()
					.getResourceAsStream("db.properties"));
			//��ȡ����
			String dirver = p.getProperty("dirver");
			String url = p.getProperty("url");
			String user = p.getProperty("user");
			String pwb = p.getProperty("pwd");
			String initsize = p.getProperty("initsize");
			String maxsize = p.getProperty("maxsize");
			//�������ӳ�
			ds = new BasicDataSource();
			//���ò���
			//�����������ע������
			ds.setDriverClassName(dirver);
			//ʹ��������������������
			ds.setUrl(url);
			ds.setUsername(user);
			ds.setPassword(pwb);
			//ʹ������������������
			ds.setInitialSize(Integer.valueOf(initsize));
			ds.setMaxActive(Integer.valueOf(maxsize));
			
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("����db.propertiesʧ��",e);
			
		}
			
	}
	
	public static Connection getConnecion() 
			throws SQLException{
			return ds.getConnection();
		
	}
	/**
	 * �����ӳش��������ӣ����ӵ�cloes����
	 * �����ӳ���д�ˣ���Ϊ�黹���ӵ��߼���
	 * �������ӳػὫ���ӵ�״̬����Ϊ���У�
	 * ����������ӵ���������
	 * @param conn
	 */
	
	public static void close(Connection conn) {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("�黹����ʧ��",e);
				}
			}
	}
	/**
	 * �����ع�����
	 */
	public static void rollback(Connection conn) {
		if(conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(
						"�ع�����ʧ��",e);
			}
		}
	}
	public static void main(String[] args) throws SQLException {
		Connection c = DBUtil.getConnecion();
		System.out.println(c);
		
	}
}
