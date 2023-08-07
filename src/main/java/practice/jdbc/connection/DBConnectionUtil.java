package practice.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static practice.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //아까 만든 변수들로 커넥션 객체 얻기 (DriverManager객체가 DB 드라이버 관리, 커넥션 획득 기능)
            log.info("get connection={}, class={}", connection, connection.getClass());
            //잘 얻어졌는지 확인하기
            return connection;
            //해당 객체 반환
        } catch (SQLException e) { //예외처리
            throw new IllegalStateException(e);
        }

    }
}
