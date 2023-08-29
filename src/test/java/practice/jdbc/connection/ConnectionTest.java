package practice.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static practice.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {


    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        //커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10); //커넥션풀 안의 커넥션 개수 10개로 설정
        dataSource.setPoolName("MyPool"); //이름 MyPool 설정

        useDataSource(dataSource);

        //커넥션 풀에서 커넥션을 생성하는 작업은 어플리케이션 속도에 영향주지 않기 위해
        //별도의 스레드에서 작동한다.
        //그래서 커넥션 객체를 커넥션 풀에 추가하는 로그를 보기 위해서 사용한다.
        Thread.sleep(1000);
    }



    @Test
    void diverManager() throws SQLException {
        //DriverManager 는 커넥션 풀 아닌 커넥션 자체 획득
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        //DriverManagerDataSource : DriverManager + DataSource
        
        //위의 diverManager() 매서드에선 DataSource 인터페이스 없이 DriverManager 를 사용했었음.
        //여기선 DataSource 인터페이스 사용.
        //아래의 DriverManagerDataSource 클래스를 타고 올라가다 보면 DataSource 인터페이스가 나옴.
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }



    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());

    }
}
