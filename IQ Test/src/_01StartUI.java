// 객지 게임 시작화면 구현 완료

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

//setLocation((int)((dmen.width - getSize().width)/2), 
//(int)((dmen.height - getSize().height))/2);
// 어떤 환경이든 위치를 정중앙에 배치


// 시작화면
public class _01StartUI extends JFrame {

	public _01StartUI() {
		setLayout(null); // 레이아웃 종류 설정

		// "당신의 지능은 몇살인가요?"라는 제목을 가진 라벨 생성
		JLabel title = new JLabel("당신의 지능은 몇살인가요?");
		title.setBounds(110, 150, 1000, 100); // 위치 크기 설정
		title.setForeground(Color.BLACK); // 글자색 설정

		Font labelFont = title.getFont();
		title.setFont(new Font(labelFont.getName(), Font.PLAIN, 50)); // 폰트 설정
		add(title); // 프레임에 추가

		// "시작하기" 텍스트를 가진 버튼 생성
		JButton s_button = new JButton("시작하기");
		s_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 버튼 클릭하면 _02MenuUI로 넘어감
				_02MenuUI secondFrame = new _02MenuUI();
				secondFrame.setVisible(true); // _02MenuUI 보이게 설정
				dispose(); // 프레임에 추가
			}
		});
		s_button.setBounds(585, 370, 150, 50); // 버튼 위치와 크기 설정

		add(s_button); // 프레임에 추가

		Font buttonFont = s_button.getFont();
		s_button.setFont(new Font(buttonFont.getName(), Font.BOLD, 20)); // 버튼 폰트 설정

		// 이미지를 표시할 패널 생성
		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/java_pic.jpg");
		backgroundPanel.setSize(new Dimension(800, 600)); // 크기 설정
		add(backgroundPanel); // 프레임에 추가

		setTitle("지능테스트 첫 화면"); // 타이틀 설정
		setSize(800, 600); // 크기 설정
		setLocationRelativeTo(null); // 호면 중앙에 배치
		setResizable(false); // 크기 조절 불가능하게 설정

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 설정
		setVisible(true); // 프레임이 보이도록 설정

	}


	// 프로그램 이미지 바탕화면 클래스
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
		new _01StartUI(); // 시작화묜 객체 설정
	}

}
