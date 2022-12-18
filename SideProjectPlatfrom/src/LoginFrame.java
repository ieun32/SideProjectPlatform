//작성자명: 이은영
//작성일자: 2022-11-24

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.StyleConstants;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//*로그인 프레임*//
public class LoginFrame extends JFrame {
	//패널 및 컴포넌트 정의
	ImageIcon icon = new ImageIcon("rogo.png");
	JPanel panel = new JPanel();
	JLabel j1 = new JLabel("아이디");
	JLabel j2 = new JLabel("비밀번호");
	JLabel j3 = new JLabel();
	JLabel j4 = new JLabel(icon);
	JTextField t1 = new JTextField();
	TextField t2 = new TextField();
	JButton B1 = new JButton("로그인");
	JButton B2 = new JButton("회원가입");
	JButton B3 = new JButton("아이디 찾기");
	JButton B4 = new JButton("비밀번호 찾기");
	
	//*생성자*//
	LoginFrame(){
		//frame 설정
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		
		//panel 설정
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		add(panel);
		
		//컴포넌트 설정
		j1.setBounds(300, 230, 100, 40);
		j2.setBounds(300, 270, 100, 40);
		j3.setBounds(310, 420, 400, 30);
		j4.setBounds(380, 150, 150, 60);
		t1.setBounds(400, 230, 200, 40);
		t2.setBounds(400, 270, 200, 40);
		B1.setBounds(310, 330, 290, 37);
		B2.setBounds(505, 380, 120, 30);
		B3.setBounds(290, 380, 120, 30);
		B4.setBounds(400, 380, 120, 30);
		j1.setHorizontalAlignment(JLabel.CENTER);		//가운데 정렬
		j2.setHorizontalAlignment(JLabel.CENTER);
		j1.setFont(new Font("나눔바른고딕",Font.BOLD, 15));	//폰트 설정
		j2.setFont(new Font("나눔바른고딕",Font.BOLD, 15));
		j3.setFont(new Font("나눔바른고딕",Font.BOLD, 13));
		B1.setFont(new Font("나눔바른고딕",Font.BOLD, 15));
		B2.setFont(new Font("나눔바른고딕",Font.BOLD, 14));
		B3.setFont(new Font("나눔바른고딕",Font.BOLD, 14));
		B4.setFont(new Font("나눔바른고딕",Font.BOLD, 14));
		j3.setForeground(Color.red);		//폰트색 설정
		B1.setForeground(Color.white);
		B2.setForeground(new Color(87, 58, 180));
		B3.setForeground(new Color(87, 58, 180));
		B4.setForeground(new Color(87, 58, 180));
		B1.setBackground(new Color(87, 58, 180));		//배경색 설정
		B2.setBackground(Color.white);		
		B3.setBackground(Color.white);
		B4.setBackground(Color.white);
		B2.setBorderPainted(false);						//테두리 설정(false: 테두리 없앰)
		B3.setBorderPainted(false);
		B4.setBorderPainted(false);
		t2.setEchoChar('●');  						//비밀번호의 입력을 *모양으로 표시되도록 설정
		
		//panel에 컴포넌트 붙이기
		panel.add(j1);  panel.add(j2);  panel.add(j3);	panel.add(j4);		//라벨
		panel.add(t1);  panel.add(t2);										//텍스트 필드
		panel.add(B1);  panel.add(B2);  panel.add(B3);  panel.add(B4);		//버튼
		
		//컴포넌트에 액션 리스너 등록
		t1.addActionListener(new MyActionListener());
		t2.addActionListener(new MyActionListener());
		B1.addActionListener(new MyActionListener());
		B2.addActionListener(new MyActionListener());
		B3.addActionListener(new MyActionListener());
		B4.addActionListener(new MyActionListener());
		
		//프레임을 닫으면 table삭제
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("사용자 명령으로 종료합니다.");
				System.exit(0);
			}
		});
		
	}
	
	//액션 리스너
	public class MyActionListener implements ActionListener{
	public void actionPerformed(ActionEvent e)
    {
		//로그인 버튼을 누르면
        if (e.getSource() == B1) {
            String ID = t1.getText(); 
            String PW = t2.getText();
            DataSearch Search = new DataSearch();
            Search.MatchCheck(ID,PW);
            if(DataSearch.mode == 1) {setVisible(false);}										//ID,PW 일치하면 mode 1
            else if(DataSearch.mode == -1) {j3.setText("로그인 실패!  올바른 아이디와 비밀번호를 입력하세요");}	//ID,PW 불일치하면 mode -1
        }
        
        //회원가입 버튼을 누르면
        if (e.getSource() == B2) {
            setVisible(false);
            new SignUpFrame();	//회원가입 창으로 전환
        }
        
        //아이디 찾기 버튼을 누르면
        if (e.getSource() == B3) {
        	setVisible(false);
        	new FindAccount(); //아이디 찾기 창으로 전환
        	System.out.println("아이디 찾기");
        }
        
        //비밀번호 찾기 버튼을 누르면
        if (e.getSource() == B4) {
        	System.out.println("비밀번호 찾기");
        }
    }
	}
	
	
	
}
