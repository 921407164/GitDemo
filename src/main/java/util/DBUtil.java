package util;
/**
 * 连接池工具类
 * @author lcd
 *
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtil {
	
	//连接池对象-由 DBCP提供
	private static BasicDataSource ds;
	
	static {
		Properties p = new Properties();
		try {
			p.load(DBUtil.class.getClassLoader()
					.getResourceAsStream("db.properties"));
			//读取参数
			String dirver = p.getProperty("dirver");
			String url = p.getProperty("url");
			String user = p.getProperty("user");
			String pwb = p.getProperty("pwd");
			String initsize = p.getProperty("initsize");
			String maxsize = p.getProperty("maxsize");
			//创建连接池
			ds = new BasicDataSource();
			//设置参数
			//设置这个参数注册驱动
			ds.setDriverClassName(dirver);
			//使用这三个参数创建连接
			ds.setUrl(url);
			ds.setUsername(user);
			ds.setPassword(pwb);
			//使用其他参数管理连接
			ds.setInitialSize(Integer.valueOf(initsize));
			ds.setMaxActive(Integer.valueOf(maxsize));
			
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("加载db.properties失败",e);
			
		}
			
	}
	
	public static Connection getConnecion() 
			throws SQLException{
			return ds.getConnection();
		
	}
	/**
	 * 由连接池创建的连接，连接的cloes方法
	 * 被连接池重写了，变为归还连接的逻辑，
	 * 即：连接池会将连接的状态设置为空闲，
	 * 并并清空连接的所有数据
	 * @param conn
	 */
	
	public static void close(Connection conn) {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("归还连接失败",e);
				}
			}
	}
	/**
	 * 帮助回滚事务
	 */
	public static void rollback(Connection conn) {
		if(conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(
						"回滚事物失败",e);
			}
		}
	}
	public static void main(String[] args) throws SQLException {
		Connection c = DBUtil.getConnecion();
		System.out.println(c);
		
	}
}
