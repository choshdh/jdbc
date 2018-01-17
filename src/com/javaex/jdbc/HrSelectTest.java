package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HrSelectTest {

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
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // server가 현재 pc 내에 존재하기때문에 localhost 라고 표현한 상태고 서버가 다른
																// 곳에 있을때는 ip주소를 기입해야 한다. 1521은 포트 번호를 뜻하며 ,xe 는 오라클의
																// 무료버전을 뜻함
			conn = DriverManager.getConnection(url, "hr", "hr");
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select es1.employee_id, "
				                + "es1.last_name, "
				                + "es1.hire_date "
				         + "from employees es1, employees es2 "
				         + "where es1.manager_id = es2.employee_id "
				         + "and es1.hire_date < es2.hire_date";

			pstmt = conn.prepareStatement(query); // conn 객체가 쿼리를 받아서 pstmt 라는 객체로 만들어 뱉는다
			rs = pstmt.executeQuery(); // DB에 있는 정보를 가져와 ResultSet 객체로 만든다

			// 4.결과처리
			while (rs.next()) {
				int employee_id = rs.getInt("employee_id");
				String last_name = rs.getString("last_name");
				Date hire_date = rs.getDate("hire_date");
				System.out.printf("사번 : %5s | 이름 : %10s | 입사일 : %10s", employee_id, last_name, hire_date);
				System.out.println();
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
