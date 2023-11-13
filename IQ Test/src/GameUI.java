// 기본적인 메인 화면 구상 완료

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GameUI extends JFrame {

	private JTextArea t_display;
	private JTextField t_input;
	private JTextField t_operand1, t_operator, t_operand2, t_result;


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
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));

		t_operand1 = new JTextField(5);
		t_operator = new JTextField(3);
		t_operand2 = new JTextField(5);
		t_result = new JTextField(5);

		t_operand1.setHorizontalAlignment(JTextField.RIGHT);
		t_operator.setHorizontalAlignment(JTextField.CENTER);
		t_operand2.setHorizontalAlignment(JTextField.RIGHT);
		t_result.setHorizontalAlignment(JTextField.RIGHT);
		t_result.setEnabled(false);

		p.add(t_operand1);
		p.add(t_operator);
		p.add(t_operand2);
		p.add(new JLabel("="));
		p.add(t_result);

		t_operand1.setEnabled(false);
		t_operator.setEnabled(false);
		t_operand2.setEnabled(false);

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