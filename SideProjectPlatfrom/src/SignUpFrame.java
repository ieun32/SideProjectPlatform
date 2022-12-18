//작성자명: 이은영
//작성일자: 2022-11-24

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//*회원가입 프레임*//
public class SignUpFrame extends JFrame {
	//패널 및 컴포넌트들
	ImageIcon icon = new ImageIcon("left-arrow.png");
	ImageIcon rogo = new ImageIcon("rogo.png");
	JPanel panel = new JPanel();
	JLabel j1 = new JLabel("아이디");
	JLabel j2 = new JLabel("비밀번호");
	JLabel j3 = new JLabel("비밀번호 확인");
	JLabel j4 = new JLabel("닉네임");
	JLabel j5 = new JLabel("회원가입을 성공하였습니다.");
	JLabel j6 = new JLabel("");
	JLabel j7 = new JLabel(rogo);
	JTextField t1 = new JTextField();
	TextField t2 = new TextField();
	TextField t3 = new TextField();
	JTextField t4 = new JTextField();
	JButton B1 = new JButton("확인");	
	JButton B2 = new JButton();	
	JButton B3 = new JButton("확인");	
	
	
	//*생성자*//
	SignUpFrame(){
		//프레임 설정
		setTitle("SignUp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		
		//패널 설정
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		add(panel);
		
		//컴포넌트 설정
		j1.setBounds(300, 200, 100, 40);	//아이디 라벨
		j2.setBounds(300, 280, 100, 40);	//비밀번호 라벨
		j3.setBounds(300, 320, 100, 40);	//비밀번호확인 라벨
		j4.setBounds(300, 240, 100, 40);	//닉네임 라벨
		j6.setBounds(300, 430, 300, 40); 	//회원가입 실패 라벨
		j7.setBounds(380, 120, 150, 60);
		
		t1.setBounds(400, 200, 200, 40);	//아이디 입력
		t2.setBounds(400, 280, 200, 40);	//비밀번호 입력
		t3.setBounds(400, 320, 200, 40);	//비밀번호 확인 입력
		t4.setBounds(400, 240, 200, 40);	//닉네임 입력
		
		B1.setBounds(300, 380, 300, 37);	//확인 버튼
		B2.setBounds(10,10,50,50);		//뒤로가기 버튼
		
		j1.setHorizontalAlignment(JLabel.CENTER);		//가운데 정렬
		j2.setHorizontalAlignment(JLabel.CENTER);
		j3.setHorizontalAlignment(JLabel.CENTER);
		j4.setHorizontalAlignment(JLabel.CENTER);
		j6.setHorizontalAlignment(JLabel.CENTER);
		
		j1.setFont(new Font("나눔바른고딕",Font.BOLD, 15));	//폰트 설정
		j2.setFont(new Font("나눔바른고딕",Font.BOLD, 15));
		j3.setFont(new Font("나눔바른고딕",Font.BOLD, 15));
		j4.setFont(new Font("나눔바른고딕",Font.BOLD, 15));
		j6.setFont(new Font("나눔바른고딕",Font.BOLD, 13));
		B1.setFont(new Font("나눔바른고딕",Font.BOLD, 15));
		
		B1.setForeground(Color.white);					//폰트색 설정
		j6.setForeground(Color.red);
		B1.setBackground(new Color(87, 58, 180));		//배경색 설정
		//B2.setOpaque(false); 							//버튼 배경 투명 설정
		B2.setContentAreaFilled(false);					//버튼 채우기 설정
		B2.setBorderPainted(false);						//버튼 테두리 설정
		B2.setIcon(icon);		   						//버튼 아이콘 설정							
		
		t2.setEchoChar('●');  						//비밀번호의 입력을 ●모양으로 표시되도록 설정
		t3.setEchoChar('●'); 						//비밀번호 확인의 입력을 ●모양으로 표시되도록 설정
		
		
		//패널에 컴포넌트 붙이기
		panel.add(j1); panel.add(j2); panel.add(j3); panel.add(j4); panel.add(j6); panel.add(j7);	//라벨
		panel.add(t1); panel.add(t2); panel.add(t3); panel.add(t4); 								//텍스트 필드
		panel.add(B1); panel.add(B2);																//버튼
		
		//액션 리스너 등록
		B1.addActionListener(new MyActionListener());
		B2.addActionListener(new MyActionListener());
		
		//창을 닫으면 table삭제
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("사용자 명령으로 종료합니다.");
				System.exit(0);
			}
		});
	}
	
	//오버로딩, 회원가입 성공창
	SignUpFrame(boolean T){
		//프레임 설정
		setTitle("회원가입 성공");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(340, 150);
		setLocationRelativeTo(null);
		setVisible(true);
		
		//패널 설정
		panel.setLayout(null);
		panel.setBackground(Color.white);
		add(panel);
		
		//컴포넌트 설정
		j5.setBounds(45, 20, 230, 30);
		B3.setBounds(70, 60, 180, 30);
		j5.setHorizontalAlignment(JLabel.CENTER);
		j5.setFont(new Font("나눔바른고딕",Font.BOLD, 15));
		B3.setFont(new Font("나눔바른고딕",Font.BOLD, 15));
		
		
		//패널에 컴포넌트 붙이기
		panel.add(j5);
		panel.add(B3);
		
		//액션리스너 등록
		B3.addActionListener(new MyActionListener());
		
		//창을 닫으면 table삭제
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("사용자 명령으로 종료합니다.");
				System.exit(0);
			}
		});
	}
	
	//액션리스너
	public class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e)
	    {
			//회원가입창_확인 버튼을 누르면
	        if (e.getSource() == B1) {
	        	String ID = t1.getText(); 
	            String PW = t2.getText();
	            String PW2 = t3.getText();
	            String NICK = t4.getText();
	            
	            if(ID.equals("")|| PW.equals("") || PW2.equals("") || NICK.equals("")) {	//어느하나라도 입력하지 않으면
	            	j6.setText("회원가입 실패!  모두 입력해주세요.");
	            
	            }else if(!PW.equals(PW2)) {									//비밀번호 확인이 일치하지 않으면
	            	j6.setText("회원가입 실패!  비밀번호가 일치하지 않습니다.");
	            	
	            }else {													//회원가입 성공시
	            	new DataInsert(ID, PW, NICK);
	            	System.out.println("회원가입 성공");
	            	setVisible(false);
	            	new SignUpFrame(true); //회원가입 성공창으로 전환
	            }
	        }
	        
	        //회원가입창_뒤로가기 버튼
	        if (e.getSource() == B2) {
	            setVisible(false);
	            new LoginFrame();	   //로그인 창으로 전환
	        }
	        
	        //회원가입 성공창_확인 버튼을 누르면
	        if(e.getSource() == B3) {
	        	setVisible(false);
	        	new LoginFrame();	   //로그인 창으로 전환
	        }
	    }
		}
	
	
//	
//	public static void main(String[] args) {
//		new SignUpFrame();
//	}
}
