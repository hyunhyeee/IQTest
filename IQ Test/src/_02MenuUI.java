// 객지 메뉴 화면 구성 완료

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class _02MenuUI extends JFrame {

	public _02MenuUI() {

		buildGUI(); // GUI를 구성한 메소드 호출

		setTitle("메뉴 화면 구성"); // 타이틀 설정
		setSize(800, 600); // 크기 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 설정
		setLayout(null); // 레이아웃 null값 설정
		setLocationRelativeTo(null); // 중앙에 배치
		setResizable(false); // 크기 변경 불가능하게 설정
		setVisible(true); // 보이도록 설정

		// 라벨 생성 및 설정
		JLabel title = new JLabel("게임을 선택하세요");
		title.setBounds(100, 50, 300, 100);
		title.setForeground(Color.BLACK);

		Font labelFont = title.getFont();
		title.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		add(title);

		// 이미지 표시하는 패널 설정
		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/java_pic.jpg");
		backgroundPanel.setSize(new Dimension(800, 600));
		add(backgroundPanel);

	}


	// 구성 GUI 메소드, 테스트 문제 버튼
	private void buildGUI() {
		JButton btn1 = new JButton("산술문제"); // 버튼 생성
		btn1.addActionListener(new ActionListener() { // 버튼 클릭 시 동작 설정
			@Override
			public void actionPerformed(ActionEvent e) {
				_03GameUI T_Frame = new _03GameUI();
				T_Frame.setVisible(true); // _03GameUI 보이도록 설정
				dispose(); // 현재 창 닫기
			}
		});

		// 위에 동일한 설정
		JButton btn2 = new JButton("속담 맞추기");
		// btn2.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// _03GameUI T_Frame = new _03GameUI();
		// T_Frame.setVisible(true);
		// dispose();
		// }
		// });

		// 위에 동일한 설정
		JButton btn3 = new JButton("상식 테스트");
		// btn3.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// _03GameUI T_Frame = new _03GameUI();
		// T_Frame.setVisible(true);
		// dispose();
		// }
		// });

		// 버튼 위치 설정
		btn1.setBounds(150, 150, 500, 60);
		add(btn1);
		btn2.setBounds(150, 220, 500, 60);
		add(btn2);
		btn3.setBounds(150, 290, 500, 60);
		add(btn3);

		// 버튼 폰트 설정
		Font buttonFont = btn1.getFont();
		btn1.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
		btn2.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
		btn3.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
	}


	// 이미지를 표시하는 패널을 위한 클래스 =
	class ImagePanel extends JPanel {
		private ImageIcon imageIcon;


		public ImagePanel(String imagePath) {
			imageIcon = new ImageIcon(getClass().getResource(imagePath)); // 이미지 아이콘 설정
		}


		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// 패널에 이미지 그리기
			g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}


	public static void main(String[] args) {
		new _02MenuUI(); // 객체 생성
	}
}
