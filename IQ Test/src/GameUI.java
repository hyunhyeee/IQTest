// 기본적인 메인 화면 구상 완료

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class GameUI extends JFrame {

	private JTextArea t_display;
	private JTextField t_input;
	private JTextField t_operand1, t_operator1, t_operand2, t_operator2, t_result; // 산술 문제 출제란


	public GameUI() {
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
		game1_CalcRandom();
	}


	private JPanel createDisplayPanel() {
		JPanel p = new JPanel(new BorderLayout());

		t_display = new JTextArea();
		t_display.setEditable(false);

		p.add(new JScrollPane(t_display), BorderLayout.CENTER);
		return p;
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
		Random rand = new Random();

		int num1 = rand.nextInt(20);
		int num2 = rand.nextInt(20);

		String[] operators = { "+", "-", "*", "/" };
		String operator1 = operators[rand.nextInt(operators.length)];

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator1);
		t_result.setText("?");
	}


	// 산술 문제 게임 구성
	public void game1_Calc() {

	}


	public static void main(String[] args) {
		new GameUI();
	}
}