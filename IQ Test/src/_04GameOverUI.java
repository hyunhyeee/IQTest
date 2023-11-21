// 04. 게임 종료시 최종화면

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class _04GameOverUI extends JFrame {

	private JTextArea t_display;
	private JLabel resultNum, resultStr;


	public _04GameOverUI() {
		super("게임 종료 화면 구성");

		// 메서드 호출
		buildGUI(_03GameUI.getCurrentLevel());

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}


	// UI 요소를 구성하는 메서드 호출
	private void buildGUI(int level) {
		add(createDisplayPanel()); // 텍스트 표시 패널 생성 및 추가
		add(createResultPanel(level)); // 최종 결과 표시 패널 생성 및 추가
		add(createInputPanel(), BorderLayout.SOUTH); // 입력 패널 생성 및 하단에 추가
	}


	// 텍스트 표시 패널 메서드
	private JPanel createDisplayPanel() {
		JPanel p = new JPanel(null);
		t_display = new JTextArea();
		t_display.setEditable(false);
		return p;
	}


	// 입력 버튼 메서드
	private JPanel createInputPanel() {
		JPanel p = new JPanel(new GridLayout());
		JButton b_restart = new JButton("재시작하기");
		// "재시작하기" 버튼 클릭 시 동작 기능 설정
		b_restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_03GameUI newGameUI = new _03GameUI();
				newGameUI.setVisible(true);
				dispose();
			}
		});

		JButton b_exit = new JButton("나가기");
		// "나가기" 버튼 클릭시 동작 기능 설정
		b_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_02MenuUI firstFrame = new _02MenuUI();
				firstFrame.setVisible(true);
				dispose();
			}
		});
		// 버튼 크기 설정, 패널 추가
		pack();
		b_restart.setPreferredSize(new Dimension(b_restart.getPreferredSize().width, 50));
		b_exit.setPreferredSize(new Dimension(b_exit.getPreferredSize().width, 50));
		p.add(b_restart);
		p.add(b_exit);

		// 버튼 폰트 설정
		Font buttonFont = b_restart.getFont();
		b_restart.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
		b_exit.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
		return p;
	}


	// 최종 결과를 보여주는 패널 생성
	private JPanel createResultPanel(int level) {
		JPanel p = new JPanel(null);
		resultNum = new JLabel("최종 점수: " + level + " 점");
		resultStr = new JLabel(getResultMessage(level));

		// 라벨 폰트, 위치 설정
		resultNum.setFont(new Font("굴림", Font.BOLD, 35));
		resultStr.setFont(new Font("굴림", Font.BOLD, 23));
		resultNum.setHorizontalAlignment(SwingConstants.CENTER);
		resultStr.setHorizontalAlignment(SwingConstants.CENTER);
		resultNum.setBounds(149, 209, 471, 87);
		resultStr.setBounds(159, 288, 453, 63);

		// 라벨 추가
		p.add(resultNum);
		p.add(resultStr);
		return p;
	}


	// 각 레벨에 따른 결과 메시지 반환
	private String getResultMessage(int level) {
		if (level == 1) {
			return "혹시 당신은... 0살? ";
		} else if (level == 2) {
			return "뇌가 순수하시네요";
		} else if (level == 3) {
			return "혹시 몇짤이신가요??";
		} else if (level == 4) {
			return "이걸 틀리셨어요???";
		} else if (level == 5) {
			return "반타작은 하셨군요";
		} else if (level == 6) {
			return "오 당신은 평균 이상이군요";
		} else if (level == 7) {
			return "레벨 7: 오 좀 치시는군요";
		} else if (level == 8) {
			return "HOXY 노력형 천재??";
		} else if (level == 9) {
			return "천재??";
		} else {
			return "맨사 가즈아~~";
		}

	}


	public static void main(String[] args) {
		new _04GameOverUI(); // 객체 호출
	}
}