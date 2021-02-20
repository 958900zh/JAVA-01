import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {
 
	private static String url = "mysql:jdbc://localhost:3306/demo";
	private static String username = "root";
	private static String password = "123";

	private static HikariDataSource dataSource;

	static {
		Properties properties = new Properties();
		properties.put("url", url);
		properties.put("username", username);
		properties.put("password", password);
		HikariConfig hikariConfig = new HikariConfig(properties);
		dataSource = new HikariDataSource(hikariConfig);
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username,password);
	}

	// 从Hikari连接池中获取连接
	public static Connection getConnectionByHikari() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static void release(Connection conn, Statement st, ResultSet rs){
		
		if(rs!=null){
			try{
				rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
 
		}
		if(st!=null){
			try{
				st.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if(conn!=null){
			try{
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
