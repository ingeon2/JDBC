package practice.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

@Slf4j
public class DBConnectionUtilTest {

    @Test
    void connectionTest() {
        Connection connection = DBConnectionUtil.getConnection();
        Assertions.assertThat(connection).isNotNull();
        log.info("커넥션 객체 얻는 테스트 완료, 각각의 DB마다 커넥션 클래스명은 다르다. 여기서는 org.h2.jdbc.JdbcConnection");
    }
}
