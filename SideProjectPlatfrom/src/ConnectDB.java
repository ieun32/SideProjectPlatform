import java.sql.*;

//*JDBC, mySQL DB연결*//
public class ConnectDB {
	static Connection con = null;
	static Statement stmt = null;
	static String url = "jdbc:mysql://localhost/sideproject?serverTimezone=Asia/Seoul&useSSL=false&user=root&password=SQLgksmf102356!";
	
	ConnectDB(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("ERROR: JDBC와 DB연결 실패");
		
		}
	}
}
                       