package imgMatching;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

import javax.swing.*;

public class GUImain_noImgReplaced extends JFrame {

	/**
	 * 1. 리스트 내용추가
	 * 2. 시간초 label로 띄우기
	 * 3. 저장 / 불러오기 다시 확인
	 * 4. 
	 */
	
	int MAX_COUNT = 100;

	// 테스트 이미지 경로
	String path1 = "D:\\img\\tree1.png";
	String path2 = "D:\\img\\tree2.png";

	String imgPath = "";

	String[] fileName = new String[MAX_COUNT]; // 파일 이름 저장할 수 있는 변수(list)
	int count = 0; // fileName count

	Container pane = getContentPane();

	JPanel insertImgJP = new JPanel(); // 이미지 path
	JTextField imgPathJT = new JTextField(200); // 파일 경로 입력
	JButton startMatchingJB = new JButton("start"); // 이미지 비교 시작

	JPanel listJP = new JPanel(); // 중복 파일명 보여주기
	JList listJL = new JList(new DefaultListModel()); // 리스트 컴포넌트 생성
	
	JScrollPane scrollPaneJS = new JScrollPane(listJL);

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

	GUImain_noImgReplaced() {

		setting();
		addDialog();
	}

	class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startMatchingJB) {
				progressJD.setVisible(true); /** */
				timeCounter();
				int i = 0;
				while (fileName[i] != null) {
					runImageMatching();
					i++;
				}
				finishProgressJB.setVisible(true);
			}
			if (e.getSource() == clearJB)
				clear();
			if (e.getSource() == removeJB)
				remove();
			if (e.getSource() == saveJB)
				save();
			if (e.getSource() == loadJB)
				load();
			if (e.getSource() == failLoadJB)
				failLoadJD.setVisible(false);
			if (e.getSource() == finishProgressJB)
				progressJD.setVisible(false);
			if (e.getSource() == cancleProgressJB)
				progressJD.setVisible(false);
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
		pane.add(insertImgJP, BorderLayout.NORTH);

		listJP.add(listJL);
		listJP.setBackground(Color.WHITE);
		listJL.setSelectionMode(0);	//리스트 첫번째 항목 선택
		pane.add(scrollPaneJS, BorderLayout.CENTER);
		
		clearJB.addActionListener(new MyActionListener());
		buttonJP.add(clearJB);
		removeJB.addActionListener(new MyActionListener());
		buttonJP.add(removeJB);
		saveJB.addActionListener(new MyActionListener());
		buttonJP.add(saveJB);
		loadJB.addActionListener(new MyActionListener());
		buttonJP.add(loadJB);
		pane.add(buttonJP, BorderLayout.SOUTH);
	}

	void addDialog() {

		progressJD.setTitle("Serching . . .");
		progressJD.setSize(300, 200);
		progressJD.setVisible(false);
		progressJD.setLayout(new FlowLayout());
		// insert JLabel
		finishProgressJB.setVisible(false);
		finishProgressJB.addActionListener(new MyActionListener());
		progressJD.add(finishProgressJB);
		cancleProgressJB.addActionListener(new MyActionListener());
		progressJD.add(cancleProgressJB);

		// 불러오기 실패
		failLoadJD.setTitle("Error");
		failLoadJD.setSize(300, 200);
		failLoadJD.setVisible(false);
		failLoadJD.setLayout(new FlowLayout());
		failLoadJD.add(failLoadJL);
		failLoadJB.addActionListener(new MyActionListener());
		failLoadJD.add(failLoadJB);
	}

	void runImageMatching() {
		// startMatchingJB 클릭시 실행됨.
		// 이미지 비교 시작
		/** 경로 참고 */
		/** 문제점 : 경로를 어떻게 넣어 주는가, 사진 이름을 바꾸지 않고 어떻게 불러 오는가가 문제.*/
		int result = ImageMatching.ifCompareFeature(imgPath, path1);
		
		/**
		 * 이미지가 같으면 1 반환, 이미지가 다르면 2를 반환한다. */
		if(result == 1) {
			// 이미지가 같으면 -> 리스트에 파일 명 올린다.
			fileName[count] = path1.toString();	/** */
			count++;	// 다음 배열 위치
		}
	}

	void timeCounter() {
		// long timeCheck = System.currentTimeMillis(); // - startTime
		// JLabel 에 timeCheck 값 넣기 // + 밀리초니까 1초당으로 바꿔도 됨
	}

	void clear() {

	}

	void remove() {
		/*
		 * clearJB : 0. listJP 의 내용을 전부 지운다. 
		 * 1. 비어 있을 경우 비어있다는 경고창 출력
		 * 1-1. 지우기 전에 한번 물어보는 것도 나쁘지 않을듯. (예 / 아니오)
		 */
	}

	void save() {
		try {
			BufferedWriter save = new BufferedWriter(new FileWriter("result_list.txt"));
			/** */
			save.write(listJL.getSelectedIndex()); // 선택된 jlist내용 넣기
			/** */
			save.close();
		} catch (Exception e) {

		}
	}

	void load() {
		try {
			File load = new File("result_list.txt");
			FileReader loadFR = new FileReader(load);
			BufferedReader loadBR = new BufferedReader(loadFR);
			String loading = loadBR.readLine();
			// String str[] = loadBR.split("@");

		} catch (Exception e) {
			failLoadJD.setVisible(true);
		}

	}

	public static void main(String[] args) {
		new GUImain_noImgReplaced();
	}
}
