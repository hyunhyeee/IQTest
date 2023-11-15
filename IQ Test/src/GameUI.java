// 기본적인 메인 화면 구상 완료

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class GameUI extends JFrame {

	private JTextArea t_display;
	private JTextField t_input;
	private JTextField t_operand1, t_operator1, t_operand2, t_operator2, t_result; // 산술 문제 출제란
	private JProgressBar progressBar;
	private JButton levelCompleteButton;

	private int currentLevel = 0;
	private final int totalLevels = 10;


	public GameUI() {
		super("게임 메인 화면 구성");

		buildGUI();

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}


	private void buildGUI() {
		add(createDisplayPanel());
		add(createInputPanel(), BorderLayout.SOUTH);
		add(createQuestionPanel(), BorderLayout.CENTER);
		add(addProgressBar(), BorderLayout.NORTH);

	}


	private JPanel createDisplayPanel() {
		JPanel p = new JPanel(new BorderLayout());

		t_display = new JTextArea();
		t_display.setEditable(false);

		p.add(new JScrollPane(t_display), BorderLayout.CENTER);

		// JLabel levelLabel = new JLabel("Level 1");
		// levelLabel.setFont(new Font("굴림", Font.PLAIN, 25));
		// levelLabel.setBounds(26, 10, 130, 84);
		// add(levelLabel);
		return p;
	}


	private JPanel addProgressBar() {

		JPanel topPanel = new JPanel(new BorderLayout());
		JLabel levelLabel = new JLabel("Level 1");
		levelLabel.setFont(new Font("굴림", Font.PLAIN, 25));

//		progressBar = new JProgressBar(0, totalLevels);
//		progressBar.setStringPainted(true);
//		progressBar.setValue(currentLevel);
//
//		topPanel.add(levelLabel, BorderLayout.WEST);
//		topPanel.add(progressBar, BorderLayout.CENTER);
//		add(topPanel, BorderLayout.NORTH);

		return topPanel;
	}


	private JPanel createInputPanel() {
		JPanel p = new JPanel(new BorderLayout());

		t_input = new JTextField(30);
		JButton b_send = new JButton("보내기");

		b_send.setPreferredSize(new Dimension(b_send.getPreferredSize().width, 40));

		p.add(t_input, BorderLayout.CENTER);
		p.add(b_send, BorderLayout.EAST);

		t_input.setEnabled(true);
		b_send.setEnabled(true);

		return p;
	}


	private JPanel createQuestionPanel() {
		// 네프 계산 프로그램 참고
		JPanel p = new JPanel(null);

		t_operand1 = new JTextField(30);
		t_operator1 = new JTextField(30);
		t_operand2 = new JTextField(30);
		t_operator2 = new JTextField(30);
		t_result = new JTextField(30);

		t_operand1.setHorizontalAlignment(SwingConstants.CENTER);
		t_operator1.setHorizontalAlignment(SwingConstants.CENTER);
		t_operand2.setHorizontalAlignment(SwingConstants.CENTER);
		t_operator2.setHorizontalAlignment(SwingConstants.CENTER);
		t_result.setHorizontalAlignment(SwingConstants.CENTER);

		t_operand1.setBounds(85, 244, 90, 50);
		t_operator1.setBounds(225, 244, 60, 50);
		t_operand2.setBounds(340, 244, 90, 50);
		t_operator2.setBounds(485, 244, 60, 50);
		t_result.setBounds(600, 244, 90, 50);

		p.add(t_operand1);
		p.add(t_operator1);
		p.add(t_operand2);
		p.add(t_operator2);
		p.add(t_result);

		t_operator2.setText("=");

		t_operand1.setFont(new Font("굴림", Font.PLAIN, 30));
		t_operator1.setFont(new Font("굴림", Font.PLAIN, 30));
		t_operand2.setFont(new Font("굴림", Font.PLAIN, 30));
		t_operator2.setFont(new Font("굴림", Font.PLAIN, 30));
		t_result.setFont(new Font("굴림", Font.PLAIN, 30));

		t_operand1.setEditable(false);
		t_operator1.setEditable(false);
		t_operand2.setEditable(false);
		t_operator2.setEditable(false);
		t_result.setEditable(false);

		return p;

	}

	// 난이도 기준 설정
	// 대한민국 평균 아이큐 106
	// 일반인 아이큐 : 80 ~ 89
	// 테스트 시작시 기본 아이큐를 80으로 설정
	// 한 문제 맞출때 마다 5씩 증가
	// 난이도 별 랜덤 범위 설정


	// 산술 문제시 랜덤 값 설정
	public void game1_CalcRandom() {
		// 숫자 랜덤 범위 코드 예시 1
		Random random = new Random(System.nanoTime());
		List<Integer> result = new ArrayList<Integer>();
		int num;
		for (int i = 0; i < 6; i++) {
			while (true) {
				num = random.nextInt(20) + 1;
				if (result.contains(num)) {
					continue;
				} else {
					result.add(num);
					System.out.print(num + " ");
					break;
				}

			}

		}

		// 예시 2
		int score = 0;
		while (true) {

			double random1 = Math.random();
			double random2 = Math.random();

			int num1 = (int) (random1 * 20) + 1;
			int num2 = (int) (random1 * 20) + 1;
			int sign = (int) (random1 * 4) + 1;

			int result1 = 0;

			switch (sign) {
			case 1:
				result1 = num1 + num2;
				System.out.println(num1 + " + " + num2 + "=?");
				break;
			case 2:
				result1 = num1 - num2;
				System.out.println(num1 + " - " + num2 + "=?");
				break;
			case 3:
				result1 = num1 * num2;
				System.out.println(num1 + " * " + num2 + "=?");
				break;
			case 4:
				result1 = num1 / num2;
				System.out.println(num1 + " / " + num2 + "=?");
				break;
			}

			Scanner sc = new Scanner(System.in);
			int ans = sc.nextInt();

			if (ans == result1) {
				score = score + 1;
				System.out.println("correct");
			} else {
				System.out.println("Wrong");
			}

			if (score >= 10) {
				break;
			}

		}

	}


	// 산술 문제 게임 구성
	public void game1_Calc() {

	}


	public static void main(String[] args) {
		new GameUI();
	}
}