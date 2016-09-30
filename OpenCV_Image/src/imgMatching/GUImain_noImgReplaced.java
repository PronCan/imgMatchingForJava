package imgMatching;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

import javax.swing.*;

public class GUImain_noImgReplaced extends JFrame {

	String imgPath = "";	// textfield 저장 값
	
	StringBuffer insertPath = new StringBuffer("");

	String[] filePath = new String[] { "", }; // 파일 경로 저장할 수 있는 변수
	String[] fileName = new String[] { "", }; // 파일 이름 저장할 수 있는 변수
	int count = 0; // fileName count

	Container pane = getContentPane();

	JPanel insertImgJP = new JPanel(); // 이미지 path
	JTextField imgPathJT = new JTextField(200); // 파일 경로 입력
	JButton startMatchingJB = new JButton("start"); // 이미지 비교 시작
	
	JPanel listJP = new JPanel(); // 중복 파일명 보여주기
	//JList listJL = new JList(new DefaultListModel()); // 리스트 컴포넌트 생성
	JTextArea listJT = new JTextArea();
	JScrollPane scrollPaneJS = new JScrollPane(listJT);
	
	JPanel buttonJP = new JPanel(); // 버튼 : 전체삭제, 삭제, 저장, 불러오기
	JButton clearJB = new JButton("Clear"); // 전체 삭제
	JButton removeJB = new JButton("Delete"); // 선택된 하나 지우기
	JButton saveJB = new JButton("Save");
	JButton loadJB = new JButton("Load");

	JDialog progressJD = new JDialog(); // 시간초 띄우는 dialog
	// JLabel msTimeJL = new JLable(); // 시간초 띄우기
	JButton finishProgressJB = new JButton("OK"); // 초기 setVisable = false
	JButton cancleProgressJB = new JButton("Cancle"); // setVisable = true

	JDialog failLoadJD = new JDialog(new Frame(), true); // 데이터 불러올 때
	JLabel failLoadJL = new JLabel("불러올 데이터가 없습니다");
	JButton failLoadJB = new JButton("확인");

	JDialog pathErrorJD = new JDialog(new Frame(), true);
	JLabel pathErrorJL = new JLabel("경로가 비어 있습니다.");
	JButton pathErrorJB = new JButton("확인");

	JDialog errorJD = new JDialog(new Frame(), true);
	JLabel errorJL = new JLabel("검사 결과에 문제가 있습니다.");
	JButton errorJB = new JButton("확인");

	GUImain_noImgReplaced() {
		setting();
		addDialog();
	}

	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startMatchingJB) {
				// /** 타임카운터 JLabel에 넣고 출력.
				// *
				// * */
				// int i = 0;
				// while (fileName[i] != null) {
				// runImageMatching();
				// i++;
				// }

				/** 임시 코드 */
				try {
					if (imgPathJT.getText() != null) {
						imgPath = imgPathJT.getText();
					} else {
						// 이미지 경로가 비어있다.
						pathErrorJD.setVisible(true);
					}
				} catch (Exception exception) {
					System.out.println("imgPathJT is NULL");
				}
				ReadDirFileList(imgPath);

				progressJD.setVisible(true);
			}
			if (e.getSource() == clearJB)
				clear();
			if (e.getSource() == saveJB)
				save();
			if (e.getSource() == loadJB)
				load();
			if (e.getSource() == failLoadJB)
				failLoadJD.setVisible(false);
			if (e.getSource() == finishProgressJB)
				progressJD.setVisible(false);
			if (e.getSource() == cancleProgressJB) {
				progressJD.setVisible(false);
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			}
			if (e.getSource() == errorJB)
				errorJD.setVisible(false);
			if (e.getSource() == pathErrorJB)
				pathErrorJD.setVisible(false);
		}
	}

	void setting() {

		setTitle("ImageMatching");
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		insertImgJP.setLayout(new GridLayout(0, 2));
		insertImgJP.add(imgPathJT);
		startMatchingJB.addActionListener(new MyActionListener());
		insertImgJP.add(startMatchingJB);
		insertImgJP.setBackground(Color.BLACK);
		pane.add(insertImgJP, BorderLayout.NORTH);

		listJP.add(listJT);
		listJP.setBackground(Color.WHITE);
		scrollPaneJS.getHorizontalScrollBar().setValue(scrollPaneJS.getHorizontalScrollBar().getMaximum());	/** */
		pane.add(listJP);
		
		clearJB.addActionListener(new MyActionListener());
		buttonJP.add(clearJB);
		removeJB.addActionListener(new MyActionListener());
		buttonJP.add(removeJB);
		saveJB.addActionListener(new MyActionListener());
		buttonJP.add(saveJB);
		loadJB.addActionListener(new MyActionListener());
		buttonJP.add(loadJB);
		buttonJP.setBackground(Color.BLACK);
		pane.add(buttonJP, BorderLayout.SOUTH);
	}

	void addDialog() {

		progressJD.setTitle("Serching . . .");
		progressJD.setSize(300, 100);
		progressJD.setVisible(false);
		progressJD.setLayout(new FlowLayout());
		// insert JLabel
		finishProgressJB.setVisible(false);
		finishProgressJB.addActionListener(new MyActionListener());
		progressJD.add(finishProgressJB);
		cancleProgressJB.addActionListener(new MyActionListener());
		progressJD.add(cancleProgressJB);

		failLoadJD.setTitle("Error");
		failLoadJD.setSize(300, 100);
		failLoadJD.setVisible(false);
		failLoadJD.setLayout(new FlowLayout());
		failLoadJD.add(failLoadJL);
		failLoadJB.addActionListener(new MyActionListener());
		failLoadJD.add(failLoadJB);

		errorJD.setTitle("Error");
		errorJD.setSize(300, 100);
		errorJD.setVisible(false);
		errorJD.setLayout(new FlowLayout());
		errorJD.add(errorJL);
		errorJB.addActionListener(new MyActionListener());
		errorJD.add(errorJB);

		pathErrorJD.setTitle("Error");
		pathErrorJD.setSize(300, 100);
		pathErrorJD.setVisible(false);
		pathErrorJD.setLayout(new FlowLayout());
		pathErrorJD.add(pathErrorJL);
		pathErrorJB.addActionListener(new MyActionListener());
		pathErrorJD.add(pathErrorJB);
	}

	void runImageMatching() { // 주력 코드 위치
		// 이미지 경로 입력받은 것 버튼 누르면 텍스트로 저장해서 변수에 저장
		// 저장한 변수 ReadDirFileList 로 옮겨서 파일 있는지 확인.
	}

	void ReadDirFileList(String path) {
		/**
		 * 파일 경로에 .jpg .png 이미지 들을 불러서 변수에 파일 경로 또는 이미지 이름을 저장합니다!
		 */
		File dirFile = new File(path);
		File[] fileList = dirFile.listFiles();

		String tempFilelistName = fileList.toString(); // 파일 이름 String변환
		System.out.println("tempFilelistName is >> " + tempFilelistName);
		
		for (File tempFile : fileList) {
			// fileList 에 있는 값들을 하나씩 tempFile과 비교
			int cnt = 0; // 배열 카운트 변수
			if (tempFile.isFile()) { // 파일이 존재하면
				filePath[cnt] = tempFile.getParent();
				fileName[cnt] = tempFile.getName();
				if(cnt == 0) System.out.println("Path=" + filePath[cnt]);
				System.out.println("FileName=" + fileName[cnt]);
				// 파일 이름이 .jpg 또는 .png로 끝날 경우
				if ((fileName[cnt].contains(".jpg")) == true || (fileName[cnt].contains(".png")) == true) {
					System.out.println("truth");
					insertPath.append(fileName[cnt] + "\n");	// @ (\n) 로 파일 명 구분
					//insertPath.replace(0, insertPath.length(), fileName[cnt] + "\n"); /** TextArea에 내용 추가 */
				}
				cnt++;
			}
			/** png 파일 이면 끝에 "@" (또는 "\n") 를 붕이고 txt로 저장 한 후 이걸 다시 쪼갠다. */
		}
	}

	void clear() {
		insertPath.replace(0, 0, "Clear List");
	}

	void save() {

	}

	void load() {

	}

	public static void main(String[] args) {
		new GUImain_noImgReplaced();
	}
}
