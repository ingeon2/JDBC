package practice.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import practice.jdbc.connection.DBConnectionUtil;
import practice.jdbc.domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepository {

    public Member save(Member member) throws SQLException{ //생성
        
        String sql = "insert into member(member_id, money) values (?, ?)";
        //물음표만 바인딩해줄 String sql

        Connection con = null; //커넥션
        PreparedStatement statement = null; //DB에 쿼리 날아갈 객체

        try {
            con = getConnection();
            //DBConnectionUtil 클래스에서 가져온 커넥션 객체 얻기.
            //해당 커넥션은, 정해진 URL, USERNAME, PASSWORD를 기반으로 DriverManager이 얻어온 H2 커넥션 객체이다.

            statement = con.prepareStatement(sql);
            //해당 sql문을 커넥션에 장착하여 PreparedStatement에 넘김
            
            statement.setString(1, member.getMemberId());
            statement.setInt(2, member.getMoney());
            //각각 PreparedStatement 객체에 sql문의 (?, ?) 에 바인딩해줌
            
            statement.executeUpdate();
            //statement를 DB에 날리기

            return member;
        }
        catch (SQLException e) {
            log.error("db 에러", e);
            throw e;
        }
        finally {
            close(con, statement, null);
            //커넥션과 PreparedStatement 객체는 닫아줘야 한다. 닫아주지 않으면 계속해서 열려있게됨.
            //그렇게 된다면 커넥션 부족으로 장애 발생 가능.
        }
    }

    public Member findById(String memberId) throws SQLException { //조회
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            statement.setString(1, memberId);

            rs = statement.executeQuery();
            //위의 sql쿼리문을 실행했을때의 결과, 말 그대로 ResultSet, 결과 Set

            if(rs.next()) { //결과 rs에 데이터가 있다면 찾아온 member 객체를 반환해준다 + next를 사용해준 횟수에 따라 해당 횟수 번째의 객체 반환
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }
            else {
                throw new NoSuchElementException("너가 준 " + memberId + " 이 멤버아이디 DB에 없어!");
            }
        }
        catch (SQLException e) {
            log.error("db error, e");
            throw e;
        }
        finally {
            close(con, statement, rs);
        }
    }


    public void update(String memberId, int money) throws SQLException { //수정
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            statement.setInt(1, money);
            statement.setString(2, memberId);
            int resultSize = statement.executeUpdate(); //수행해준 행의 수, 즉 1이 나와야함
            log.info("resultSize={}", resultSize);
        }
        catch (SQLException e) {
            log.error("db 에러", e);
            throw e;
        }
        finally {
            close(con, statement, null);
        }
    }


    public void delete(String memberId) throws SQLException {
        String sql = "delete member where member_id=?";

        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = getConnection();
            statement = con.prepareStatement(sql);
            statement.setString(1, memberId);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            log.error("db 에러", e);
            throw e;
        }
        finally {
            close(con, statement, null);
        }
    }



    //여기 아래부턴 private 매서드
    private void close(Connection con, PreparedStatement statement, ResultSet rs) { 
        //열어준 객체들을 닫아줄 매서드

        if(rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                log.info("error", e);
            }
        }


        if(statement != null) {
            try {
                statement.close();
            }
            catch (SQLException e) {
                log.info("error", e);
            }
        }

        if(con != null) {
            try {
                con.close();
            }
            catch (SQLException e) {
                log.info("error", e);
            }
        }
    }


    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

}
