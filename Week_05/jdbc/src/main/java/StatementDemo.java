import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementDemo {

    public void testSelect(String username, String password) {

        Connection conn = null;
        Statement st = null;
        try {
            conn = JdbcUtils.getConnection();
            st = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username = " + username + " AND password = " + password;
            st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, st, null);
        }
    }
}
