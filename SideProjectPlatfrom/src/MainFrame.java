//작성자명: 이은영
//작성일자: 2022-11-24

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;

import com.mysql.cj.xdevapi.AddResult;


//////////////////
//*학생정보관리 프레임*//
//////////////////

public class MainFrame extends JFrame {
//*필드 영역*//
	//데이터 검색 객체 생성
	DataSearch Search = new DataSearch();
	
	//필터기능 구현을 위한 배열
	Object[][] sortedStudents = new Object[120][5];
	Object[][] sortedStudents2 = new Object[120][5];
	Object[][] students = new Object[120][5];
	String[] comm;
	
	//필터기능 구현을 위한 변수
	String Ucheck, Gcheck, gradeCheck, departCheck;
	int mode = 0;	//체크박스와 콤보박스 멀티 컨트롤을 위한 변수
	
	//공간제어용 컨테이너들..
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel westPanel = new JPanel();
	JPanel centerPanel = new JPanel();

	//메뉴용
	JMenuBar mb = new JMenuBar();
	JMenu homeMenu = new JMenu("Main");
	JMenuItem openMI = new JMenuItem("학생등록");
	JMenuItem newMI = new JMenuItem("학생삭제");
	JMenuItem exitMI = new JMenuItem("종료");

	//웨스트패널상의 컴포넌트들....
	JLabel titleLabel = new JLabel("Select Student Type");
	JCheckBox usCheck = new JCheckBox("학부생");
	JCheckBox gsCheck = new JCheckBox("대학원생");
	JComboBox comboBox;
	JTree tree;
	DefaultMutableTreeNode root;
	DefaultTreeModel treeModel;
	JButton searchBtn = new JButton("선택한 필터로 검색");

	//센터패널상의 컴포넌트들...
	JTable table;
	DefaultTableModel tableModel;
	
	String columNames[] = {"학과", "학년", "이름", "구분", "학번"};
	String grade[] = {"1학년", "2학년", "3학년", "4학년"};
	

//*생성자 영역*//
	MainFrame(){
		//DB에 데이터가 잘 들어갔는지 확인하는 작업
		Search.departmentsTable();	//departments테이블 전체 검색
		Search.userTable();			//user테이블 전체 검색
		Search.studentsTable();		//students테이블 전체 검색
		
		//comboBox에 추가될 departments 배열 생성
		String[] departments = Search.Dlist1.toArray(new String[Search.Dlist1.size()]);
		
		//students 배열 초기화
		findStudent();
		
        //프레임 정보
		setTitle("학생정보관리시스템_이은영");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//메뉴추가
		homeMenu.add(openMI);
		homeMenu.add(newMI);
		homeMenu.add(exitMI);
		mb.add(homeMenu);
		setJMenuBar(mb);

		// 패널 추가 작업
		westPanel.setPreferredSize(new Dimension(160, basePanel.getHeight()));
		setContentPane(basePanel);
		basePanel.add(westPanel, BorderLayout.WEST);
		basePanel.add(centerPanel, BorderLayout.CENTER);
		westPanel.setLayout(new FlowLayout());
		//westPanel.setBackground(Color.RED);

		//웨스트패널 컴포넌트 작업
		comboBox = new JComboBox(departments);
		comboBox.setPreferredSize(new Dimension(160, 20));
		titleLabel.setPreferredSize(new Dimension(160, 20));
		
		//JTree 초기화 메소드 호출
		setTree("학과 선택");
			
		westPanel.add(searchBtn);
		westPanel.add(titleLabel);
		westPanel.add(usCheck);
		westPanel.add(gsCheck);
		westPanel.add(comboBox);
		westPanel.add(tree);
		
		
		//센터패널 작업
		tableModel = new DefaultTableModel(students, columNames);
		table = new JTable(tableModel);

		JScrollPane sp = new JScrollPane(table);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(sp, BorderLayout.CENTER);
		setSize(900, 300);
		setVisible(true);
		
		//table rowSorter
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());  //rowSorter 객체 생성
		table.setRowSorter(rowSorter);													//rowSorter 적용
		
		//아이템, 액션 리스너 등록
		usCheck.addItemListener(new MyItemListener());
		gsCheck.addItemListener(new MyItemListener());
		comboBox.addItemListener(new MyItemListener());
		exitMI.addActionListener(new MyActionListener());
		searchBtn.addActionListener(new MyActionListener());
		
		//프레임에 윈도우 리스너 등록: 프레임을 닫으면 table삭제
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new DeleteTable();
				System.out.println("사용자 명령으로 종료합니다.");
				System.exit(0);
			}
		});
	}
	
//*오버라이딩*//
	//JCheckbox와 JComboBox를 위한 아이템 리스너 클래스 생성
	public class MyItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			Object item = e.getSource();	
			
			//학부생을 체크했을 때
			if(item == usCheck){
				boolean state = usCheck.isSelected();
				
				if(state == true) {
					Ucheck = "학부생"; 
					System.out.println("학부생 선택");
				}else if(state == false) {
					Ucheck = null;
					System.out.println("학부생 취소");
				}
						
				
			//대학원생을 체크했을 때
			}else if(item == gsCheck){
				boolean state = gsCheck.isSelected();
				
				if(state == true) {
					Gcheck = "대학원생";
					System.out.println("대학원생 선택");
				}else if(state == false) {
					Gcheck = null;
					System.out.println("대학원생 취소");
				}
			
			//콤보박스(학과)를 선택했을 때
			}else if(item == comboBox){
				System.out.print("콤보박스 체크");
				String combo = (String)comboBox.getSelectedItem();
				
				switch(combo) {
				
				case "컴퓨터공학부": 
					System.out.print(":컴공\n");
					changeTree(combo);
					departCheck = combo; 
					break;
		         
				case "전자공학과": 
			    	System.out.println(":전자\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				case "기계공학과": 
			    	System.out.println(":기계\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				case "건축공학과": 
			    	System.out.println(":건축\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				case "간호학과": 
			    	System.out.println(":간호\n");
			    	changeTree(combo);
			    	departCheck = combo; 
			         break;
			         
				case "재료공학과": 
			    	System.out.println(":재료\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				case "경영학과": 
			    	System.out.println(":경영\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				case "일어일문학과": 
			    	System.out.println(":일어일문\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				case "산업경영공학과": 
			    	System.out.println(":산업경영\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				case "체육학과": 
			    	System.out.println(":체육\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				case "교육학과": 
			    	System.out.println(":교육\n");
			    	changeTree(combo);
			    	departCheck = combo;
			         break;
			         
				default: System.out.println("처리하지 않는 아이템이벤트");
		         	break;
				}
			}
				
		}
	}	
	
	//JButton과 JMenuitem을 위한 아이템 리스너 클래스 생성
	public class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object item = e.getSource();
			
			//검색버튼을 눌렀을 때
			if(item == searchBtn) {
				Search.Command = "SELECT * FROM students WHERE";	//구문 초기화
				String Grade = null;										//대학원생, 학부생 선택을 위한 변수
				
				//학부생과 대학원생 구문은 동시에 선택할 경우 (학부생 OR 대학원생) 으로 묶어야 하므로 따로 관리
				if(Ucheck!=null && Gcheck!=null) {
					Grade = "(grade= \"학부생\" OR grade= \"대학원생\")";
				}else if(Ucheck!=null && Gcheck==null){
					Grade = "grade= \"학부생\"";
				}else if(Ucheck==null && Gcheck!=null){
					Grade = "grade= \"대학원생\"";
				}
				
				//값이 들어가 있는 경우(조건을 선택하면 값이 들어감) 구문에 추가
				if(departCheck!=null && !Search.Command.equals("SELECT * FROM students WHERE")) {	//구문에 이미 다른 값이 들어간 경우 AND 구문과 함께 추가
					Search.Command += "AND department= "+'"'+departCheck+'"' + " ";
				}else if(departCheck!=null) {														//처음 구문에 넣는 경우 그냥 구문에 추가
					Search.Command += " department= "+'"'+departCheck+'"' + " ";
				}
				
				if(gradeCheck!=null && !Search.Command.equals("SELECT * FROM students WHERE")) {
					Search.Command += "AND year= "+'"'+gradeCheck+'"' + " ";
				}else if(gradeCheck!=null) {													
					Search.Command += " year= "+'"'+gradeCheck+'"' + " ";
				}
			
				if(Grade!=null && !Search.Command.equals("SELECT * FROM students WHERE")) {
					Search.Command += "AND "+ Grade + " ";
				}else if(Ucheck!=null) {													
					Search.Command += Grade + " ";
				}
				
				
				//콘솔창 출력(확인용)
				System.out.println("검색버튼 누름");
				System.out.println(Search.Command);
				
				//1. DataSearch의 SelectstData메소드 사용해 DB로부터 조건구문으로 데이터 불러오기
				//2. 이차원 리스트에 불러온 데이터를 이차원 배열(students)에 넣기
				//3. 바뀐 데이터를 참조하여 JTable 수정해서 보여주기
				Search.SelectstData(Search.Command);
				findStudent();
				ShowTable(table, tableModel, students);
			
				
			//종료 메뉴를 선택했을 때
			}else if(item == exitMI){
				try {
				new DeleteTable();
				System.out.println("사용자 명령으로 종료합니다.");
				System.exit(0);
				
				}catch(Exception E) {
					System.out.println("종료 오류");
					System.out.println(E);
				}
			}
		}
	}	
	
	//JTree 이벤트 리스너 (출처: https://movefast.tistory.com/66)
	public class Selector implements TreeSelectionListener {
		 public void valueChanged(TreeSelectionEvent event) {
			 	DefaultMutableTreeNode node; 
				node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				if(node == null) return;																	//선택하지 않으면 아무 행동하지 않기
				gradeCheck = (String) node.getUserObject();													//선택한 노드 정보 가져오기
				if(gradeCheck!="1학년" && gradeCheck!="2학년" && gradeCheck!="3학년" && gradeCheck!="4학년") {	//학년이 아닌 루트노드(학과)를 선택할 경우 아무 행동하지 않기
					gradeCheck=null;
					return;
				}
				
				//콘솔창 출력(확인용)
				System.out.println("선택된 노드 : " + gradeCheck);
		 }
	}
	
	
	
//*메소드 영역*//
	//새로 만든 테이블을 덮어씌우는 메소드
	public void ShowTable(JTable sort_table,DefaultTableModel table_model, Object[][] arr) {
		table_model.setDataVector(arr, columNames);
		sort_table.updateUI();
	}
	
	
	//1차원 배열을 콘솔창에 출력하는 메소드
	public void showArr1(Object[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+ " ");
		}
	}
	
	//2차원 배열을 콘솔창에 출력하는 메소드
	public void showArr(Object[][] arr) {
		for (int i = 0; i < arr.length; i++) {
            Object[] inArr = arr[i];
            for (int j = 0; j < inArr.length; j++) {
                System.out.print(inArr[j] + " ");
            }
            System.out.println();
        }
	}
	
	//2차원 배열의 모든 요소를 삭제하는 메소드 
	public void deleteArr(Object[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				arr[i][j] = null;
			}
		}
	}
	
	//students배열 초기화: 이차원 리스트를 이차원 배열에 넣기(최초실행되어야함)
	public Object[][] findStudent() {
		deleteArr(students);
		Iterator<String[]> iterator = Search.Slist2.iterator();
		
		int Count=0;
		while(iterator.hasNext()){
			String[] str = iterator.next();
			for(int i = 0; i < str.length; i++) {
				students[Count][i] = str[i];
			}
			Count++;
		}
		showArr(students);
		return(students);
	}
	
	//JTree 초기화 메소드
	public JTree setTree(String Dname) {
		root = new DefaultMutableTreeNode(Dname);
		tree = new JTree(root);
		tree.getSelectionModel().addTreeSelectionListener(new Selector());
	    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setPreferredSize(new Dimension(160, 100));

		return tree;
	}
	
	//학과 선택에 의해 바뀌는 JTree 메소드
	public JTree changeTree(String Dname) {
		root = new DefaultMutableTreeNode(Dname);
		
		for(int i = 0; i<grade.length;i++ ) {
			DefaultMutableTreeNode node =  new DefaultMutableTreeNode(grade[i]);
			root.add(node);
			treeModel = (DefaultTreeModel)tree.getModel();
			treeModel.setRoot(root);
		}
		
		return tree;
	}
}

