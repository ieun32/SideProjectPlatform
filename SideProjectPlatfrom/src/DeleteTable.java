//작성자명: 이은영
//작성일자: 2022-11-24

import java.sql.*;

//*모든 테이블 삭제*//
public class DeleteTable {
	
	//기본생성자, 테이블 삭제 
	DeleteTable(){
		try {
			new ConnectDB();
			ConnectDB.con = DriverManager.getConnection(ConnectDB.url); 
			ConnectDB.stmt = ConnectDB.con.createStatement();
			int result = ConnectDB.stmt.executeUpdate("DROP TABLE user, students, departments"); //user ���̺� ���� ���
			
			System.out.println("모든 테이블 삭제 완료");
		}
		catch(Exception e) {
			System.out.println("ERROR: 테이블 삭제 실패");
			System.out.println(e);
			return;
		}	
	}
}
