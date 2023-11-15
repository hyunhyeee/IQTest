// 기본적인 메인 화면 구상 완료

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class _03GameUI extends JFrame {

	private JTextArea t_display;
	private JTextField t_input;
	private JTextField t_operand1, t_operator1, t_operand2, t_operator2, t_result; // 산술 문제 출제란
	private JProgressBar progressBar;
	private JButton levelCompleteButton;

	private int currentLevel = 0;
	private final int totalLevels = 10;

	private JLabel timerLabel;
	private Timer timer;
	private int count = 5; // 초기 카운트 값


	public _03GameUI() {
		super("게임 메인 화면 구성");

		buildGUI();

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setResizable(false);

		setVisible(true);
	}


	private void buildGUI() {
		JButton levelCompleteButton = new JButton("다음->");
		levelCompleteButton.setBounds(530, 380, 150, 60);
		add(levelCompleteButton);
		levelCompleteButton.setEnabled(false);

		Font buttonFont = levelCompleteButton.getFont();
		levelCompleteButton.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));

		add(createDisplayPanel());
		add(createInputPanel(), BorderLayout.SOUTH);
		add(createQuestionPanel(), BorderLayout.CENTER);
		add(addProgressBar(), BorderLayout.NORTH);
		game9_10_CalcRandom();

	}


	private JPanel createDisplayPanel() {
		JPanel p = new JPanel(new BorderLayout());

		t_display = new JTextArea();
		t_display.setEditable(false);

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

		// progressBar = new JProgressBar(0, totalLevels);
		// progressBar.setStringPainted(true);
		// progressBar.setValue(currentLevel);
		//
		// topPanel.add(levelLabel, BorderLayout.WEST);
		// topPanel.add(progressBar, BorderLayout.CENTER);
		// add(topPanel, BorderLayout.NORTH);

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

	// 1~3, 4~5, 6~8, 9~10, 4구역으로 나누기
	// 1~3은 한자리수의 + -
	// 4~5는 한자리수의 *
	// 6~8은 두자리수의 + -
	// 9~10은 두자리수의 * /


	// 레벨 1~3
	public void game1_3_CalcRandom() {
		Random rand = new Random();

		int num1 = rand.nextInt(9) + 1;
		int num2 = rand.nextInt(9) + 1;

		String[] operators = { "+", "-" };
		String operator1 = operators[rand.nextInt(operators.length)];

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator1);
		t_result.setText("?");
	}


	// 레벨 4~5
	public void game4_5_CalcRandom() {
		Random rand = new Random();

		int num1 = rand.nextInt(9) + 1;
		int num2 = rand.nextInt(9) + 1;

		String operators = "*";
		String operator1 = operators;

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator1);
		t_result.setText("?");
	}


	// 레벨 6~8
	public void game6_8_CalcRandom() {
		Random rand = new Random();

		int num1 = rand.nextInt(20) + 1;
		int num2 = rand.nextInt(20) + 1;

		String[] operators = { "+", "-" };
		String operator1 = operators[rand.nextInt(operators.length)];

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator1);
		t_result.setText("?");
	}


	// 레벨 9~10
	public void game9_10_CalcRandom() {
		Random rand = new Random();
		int num1 = rand.nextInt(35) + 1;
		int num2 = rand.nextInt(35) + 1;

		String[] operators = { "*", "/" };
		String operator = operators[rand.nextInt(operators.length)];

		int result = 0;
		if (operator.equals("/")) {
			while (num1 <= num2) {
				num1 = rand.nextInt(150) + 1;
				num2 = rand.nextInt(15) + 2;
			}

			if (num1 % num2 != 0) {
				num1 -= num1 % num2;
			}

			result = num1 / num2;
		} else {
			result = num1 * num2;
		}

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator);
		t_result.setText("?");
	}


	// 산술 문제 게임 구성
	public void game1_Calc() {

	}


	public static void main(String[] args) {
		new _03GameUI();
	}
}