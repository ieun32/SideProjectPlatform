//작성자명: 이은영
//작성일자: 2022-11-24

import java.sql.*;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Iterator;

//*데이터 검색 관리*//
public class DataSearch {
	static int mode;											//사용자인터페이스 관리용 static필드
	static String Command;										//DB검색시 필요한 구문을 저장하는 문자열 변수
	ArrayList<String[]> Ulist2 = new ArrayList<String[]>(); 	//DB에서 받아온 데이터를 저장하기 위한 2차원 리스트  
	ArrayList<String[]> Slist2 = new ArrayList<String[]>();		//""
	ArrayList<String> Dlist1 = new ArrayList<String>(); 		//DB에서 받아온 데이터를 저장하기 위한 1차원 리스트
	ArrayList<String> Comm = new ArrayList<String>(); 			//이벤트발생시 구문을 삽입하여 저장, 관리하는 1차원 리스트
	
//*메소드*//
//전체 테이블 검색//
	//userTable 검색, 이차원리스트 반환
	public ArrayList<String[]> userTable(){
		try {
			new ConnectDB();
			ResultSet result = ConnectDB.stmt.executeQuery("SELECT * FROM user"); 
			
			int count = 0;
			while(result.next()) { 
				String id = result.getString("id");
				String password = result.getString("password");
				Ulist2.add(new String[] {id, password});
				count++;
			}
		
		}catch(Exception e) {
			System.out.println("ERROR: user테이블 검색 실패");
			System.out.println(e);
		}
		PrintArray(Ulist2);
		return Ulist2;
	}
	
	//students테이블 검색, 이차원리스트 반환
	public ArrayList<String[]> studentsTable(){
		try {
			new ConnectDB();
			ResultSet result = ConnectDB.stmt.executeQuery("SELECT * FROM students"); 
							
			int count = 0;
			while(result.next()) { 
				String sname = result.getString("sname") + "\t";
				String snum = result.getString("snum")+ "\t";
				String year = Integer.toString(result.getInt("year"))+ "\t";
				String grade = result.getString("grade")+ "\t";
				String department = result.getString("department");
				Slist2.add(new String[] {sname, snum, year, grade, department});
				count++; 
			}
		
		}catch(Exception e) {
			System.out.println("ERROR: students테이블 검색 실패");
			System.out.println(e);
		}	
		PrintArray(Slist2);
		return Slist2;
	}
		
		
	//departmentsTable 검색
	public ArrayList<String> departmentsTable(){
		try {
			new ConnectDB();
			ResultSet result = ConnectDB.stmt.executeQuery("SELECT * FROM departments"); 
							
			int count = 0;
			while(result.next()) { 
				String table = result.getString("dname");
				Dlist1.add(table);
				count++; 
			}
		
		}
		catch(Exception e) {
			System.out.println("ERROR: departments테이블 검색 실패");
			System.out.println(e);
		}
		PrintSArray(Dlist1);
		return Dlist1;	
	}		
		

//일치 확인(아이디, 비밀번호: userTable)//
	//아이디 비밀번호 일치 확인
	public void MatchCheck(String id, String password){
		
		String command = "SELECT * FROM user WHERE id='"+id+"' AND password='"+password+"'";
		try {
			new ConnectDB();
		    ResultSet rs = ConnectDB.stmt.executeQuery(command);
		   
		    //다음 행이 있다면 아이디, 비밀번호 비교 후 로그인 실행 
		    if(rs.next()) {
			    if(id.equals(rs.getString("id")) && password.equals(rs.getString("password"))) {
				   System.out.println("로그인 성공");
				   new MainFrame();
				   mode = 1;
			    }else {
				   System.out.println("로그인 실패");
				   mode = -1;
			    }
		    }else {System.out.println("로그인 실패");
		    	   mode = -1;					
		    }
		    
		} catch (SQLException e) {
			System.out.println("ERROR: 로그인 오류");
			System.out.println(e);
		}
	}
	


//원하는 데이터 검색 (studentsTable)//
	//원하는 Query구문을 받아 DB의 studentsTable에서 가져옴(매개변수: 문자열)
	public ArrayList<String[]> SelectstData(String command) {
		try {
			new ConnectDB();
			ResultSet result = ConnectDB.stmt.executeQuery(command); 
			Slist2.clear();
			
			int count = 0;
			while(result.next()) { 
				String sname = result.getString("sname") + "\t";
				String snum = result.getString("snum")+ "\t";
				String year = Integer.toString(result.getInt("year"))+ "\t";
				String grade = result.getString("grade")+ "\t";
				String department = result.getString("department");
				Slist2.add(new String[] {sname, snum, year, grade, department});
				count++; 
			}
			System.out.println("students테이블 필터 검색 완료");
		
		}catch(Exception e) {
			System.out.println("ERROR: students테이블 필터 검색 실패");
			System.out.println(e);
		}	
		PrintArray(Slist2);
		return Slist2;
	}
	
	
//ArrayList 관리//
	//2차원 ArrayList값 모두 출력(매개변수: 2차원 ArrayList)
	public void PrintArray(ArrayList<String[]> list) {
		Iterator<String[]> iterator = list.iterator();

        while(iterator.hasNext()){
            String[] str = iterator.next();
            for(int i = 0; i < str.length; i++) {
            System.out.print(str[i]+"\t");
            }System.out.println("");
        }
	}
	
	//1차원 ArrayList값 모두 출력(매개변수: 1차원 ArrayList)
	public void PrintSArray(ArrayList<String> list) {
		Iterator<String> iterator = list.iterator();

        while(iterator.hasNext()){
            String str = iterator.next();
            System.out.println(str);
        }
	}
	
}
