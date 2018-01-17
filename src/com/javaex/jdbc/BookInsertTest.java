package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookInsertTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // server가 현재 pc 내에 존재하기때문에 localhost 라고 표현한 상태고
																// 서버가 다른 곳에 있을때는 ip주소를 기입해야 한다. 1521은 포트 번호를 뜻하며 ,
																// xe 는 오라클의 무료버전을 뜻함
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			int insertNum = 8;
			int count;
			String[] book_name = { "우리들의 일그러진 영웅", "삼국지", "토지", "유시민의 글쓰기 특강", "패션왕", "순정만화", "오직두사람", "26년" };
			String[] book_pub = { "다림", "민음사", "마로니에북스", "생각의 길", "중앙북스", "재미주의", "문학동네", "재미주의" };
			String[] book_pub_date = { "1988-02-22", "2002-03-01", "2012-08-15", "2015-04-01", "2012-02-22",
					"2011-08-03", "2017-05-04", "2012-02-04" };
			int[] book_author_id = { 1, 1, 2, 3, 4, 5, 6, 5 };
			// 3. SQL문 준비 / 바인딩 / 실행
			for (int i = 0; i < insertNum; i++) {
				String query = "insert into book values(seq_book_id.nextval, ? , ? , ? , ?)"; // pstmt 객체를 사용하면 변수명 대신
																								// ? 를 사용하여 삽입 할 수 있다.
				pstmt = conn.prepareStatement(query); // conn 객체가 쿼리를 받아서 pstmt 라는 객체로 만들어 뱉는다
				pstmt.setString(1, book_name[i]); // 쿼리의 물음표 인덱스에 값을 채워주는 객체
				pstmt.setString(2, book_pub[i]);
				pstmt.setString(3, book_pub_date[i]);
				pstmt.setInt(4, book_author_id[i]);

				// 4.결과처리
				count = pstmt.executeUpdate(); // insert,update,delete 실행시 사용하는 함수 executeUpdate() 함수
				System.out.println(count + "처리완료");
			}

		} catch (ClassNotFoundException e) {

			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {

			System.out.println("error:" + e);

		} finally {

			// 5. 자원정리

			try {
				// if (rs != null) {
				// rs.close();
				// }

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
	}

}
