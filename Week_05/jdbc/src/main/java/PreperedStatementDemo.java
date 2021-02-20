import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreperedStatementDemo {

    public void testSelect(String username, String password) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, st, null);

        }

    }
}
