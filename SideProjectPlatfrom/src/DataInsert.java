//작성자명: 이은영
//작성일자: 2022-12-17

import java.sql.*;

//*데이터 삽입*//
public class DataInsert {
	
	//오버로딩, user데이터 추가
	DataInsert(String id, String password, String nickname){
		String commend = "INSERT INTO user VALUES ('"+ id +"', '" + password + "', '" + nickname + "')";
		
		try {
			new ConnectDB();
			ConnectDB.stmt = ConnectDB.con.createStatement();
			ConnectDB.stmt.executeUpdate(commend);
			System.out.println("새로운 데이터 추가 성공(user)");
		} catch (SQLException e) {
			System.out.println("ERROR: 새로운 데이터 추가 실패(user)");
			System.out.println(e);
		}
	}
}
