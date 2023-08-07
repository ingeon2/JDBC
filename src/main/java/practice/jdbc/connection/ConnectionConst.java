package practice.jdbc.connection;

public abstract class ConnectionConst {

    //간단한 CRUD와 JDBC를 설명하기 위해, 어플리케이션.yml 설정파일이 아닌
    //클래스에 변수를 생성해서 연결시켜주기 위해 작성한 클래스입니다!
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
