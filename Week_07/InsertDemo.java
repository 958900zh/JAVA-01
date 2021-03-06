import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertDemo {

    public static void main(String[] args) throws Exception {
        Connection connection = JdbcUtils.getConnection();

        String sql = "INSERT INTO order (id,order_no,user_id,goods_id,address,phone,payment,payment_time,status,send_time) VALUES(?,?,?,?,1,1,1,now(),1,now())";

        long start = System.currentTimeMillis();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <= 1000000; i++) {
            preparedStatement.setString(1, i+"");
            preparedStatement.setString(2, i+"");
            preparedStatement.setString(3, i+"");
            preparedStatement.setString(4, i+"");
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

        System.out.println(System.currentTimeMillis() - start);
    }
}
