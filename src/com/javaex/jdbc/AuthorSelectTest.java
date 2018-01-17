package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorSelectTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";  //server가 현재 pc 내에 존재하기때문에 localhost 라고 표현한 상태고 서버가 다른 곳에 있을때는 ip주소를 기입해야 한다. 1521은 포트 번호를 뜻하며 ,xe 는 오라클의 무료버전을 뜻함
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select * from author";
			pstmt = conn.prepareStatement(query); //conn 객체가 쿼리를 받아서 pstmt 라는 객체로 만들어 뱉는다
			rs = pstmt.executeQuery(); //DB에 있는 정보를 가져와 ResultSet 객체로 만든다
			
			// 4.결과처리
			while(rs.next()) {
				int author_id = rs.getInt("author_id");
				String author_name = rs.getString("author_name");
				String author_desc = rs.getString("author_desc");
				System.out.printf("저자 번호 : %6s  |  저자명 : %7s  |  저자 정보 : %20s",author_id,author_name, author_desc);
				System.out.println();
			}

		} catch (ClassNotFoundException e) {

			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {

			System.out.println("error:" + e);

		} finally {

			// 5. 자원정리

			try {
//				if (rs != null) {
//					rs.close();
//				}

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
