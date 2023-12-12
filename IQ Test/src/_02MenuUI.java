
// 02. 게임 메뉴 화면

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


// 응애
public class _02MenuUI extends JFrame {
	private BackgroundMusicPlayer musicPlayer;
	private JButton musicButton;


	public _02MenuUI() {

		buildGUI(); // GUI를 구성한 메소드 호출
		
		// 라벨 생성 및 설정
		JLabel title = new JLabel("게임을 선택하세요");
		title.setBounds(100, 20, 300, 100);
		title.setForeground(Color.BLACK);

		Font labelFont = title.getFont();
		title.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		add(title);

		musicPlayer = new BackgroundMusicPlayer();
		musicPlayer.playBackgroundMusic();
		
		
		ImageIcon soundOnIcon = new ImageIcon(getClass().getResource("/Game_pic/soundOn.png"));
		ImageIcon soundOffIcon = new ImageIcon(getClass().getResource("/Game_pic/soundOff.png"));

		// 이미지 크기 고정
		Image imgOn = soundOnIcon.getImage();
		Image imgOff = soundOffIcon.getImage();
		Image scaledImgOn = imgOn.getScaledInstance(65, 40, Image.SCALE_SMOOTH);
		Image scaledImgOff = imgOff.getScaledInstance(65, 40, Image.SCALE_SMOOTH);

		// 이미지 아이콘 크기에 맞춰 버튼 크기 조정
		ImageIcon scaledSoundOnIcon = new ImageIcon(scaledImgOn);
		ImageIcon scaledSoundOffIcon = new ImageIcon(scaledImgOff);

		musicButton = new JButton(scaledSoundOnIcon); // 초기 이미지 아이콘 설정
		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				musicPlayer.toggleMusic();
				if (musicPlayer.isMusicOn()) {

					musicButton.setIcon(scaledSoundOnIcon); // 음악이 꺼진 상태의 이미지로 변경
				} else {

					musicButton.setIcon(scaledSoundOffIcon); // 음악이 켜진 상태의 이미지로 변경
				}

			}
		});
		musicButton.setBounds(720, 0, 65, 40);
		musicButton.setContentAreaFilled(false); // 배경색 제거
		musicButton.setBorderPainted(false); // 테두리 없애기
		musicButton.setFocusPainted(false); // 글씨 테두리 없애기

		add(musicButton);
		
		// 이미지 표시하는 패널 설정
		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/menupic.png");
		backgroundPanel.setSize(new Dimension(800, 600));
		add(backgroundPanel);
		
		
		setTitle("메뉴 화면 구성"); // 타이틀 설정
		setSize(800, 600); // 크기 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 설정
		setLayout(null); // 레이아웃 null값 설정
		setLocationRelativeTo(null); // 중앙에 배치
		setResizable(false); // 크기 변경 불가능하게 설정
		setVisible(true); // 보이도록 설정


	}


	// 구성 GUI 메소드, 테스트 문제 버튼
	private void buildGUI() {
		JButton btn1 = new JButton("산술문제"); // 버튼 생성
		btn1.addActionListener(new ActionListener() { // 버튼 클릭 시 동작 설정
			@Override
			public void actionPerformed(ActionEvent e) {
				_03_0GameUI T_Frame = new _03_0GameUI();
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
		btn1.setBounds(46, 128, 188, 130);
		btn1.setContentAreaFilled(false); // 배경색 제거
		btn1.setBorderPainted(false); // 테두리 없애기
		btn1.setFocusPainted(false); // 글씨 테두리 없애기
		add(btn1);
		
		btn2.setBounds(280, 135, 200, 140);
		btn2.setContentAreaFilled(false); // 배경색 제거
		btn2.setBorderPainted(false); // 테두리 없애기
		btn2.setFocusPainted(false); // 글씨 테두리 없애기
		add(btn2);
		
		btn3.setBounds(530, 135, 210, 125);
		btn3.setContentAreaFilled(false); // 배경색 제거
		btn3.setBorderPainted(false); // 테두리 없애기
		btn3.setFocusPainted(false); // 글씨 테두리 없애기
		add(btn3);

		// 버튼 폰트 설정
		btn1.setFont(new Font("굴림", Font.BOLD, 20));
		btn2.setFont(new Font("굴림", Font.BOLD, 20));
		btn3.setFont(new Font("굴림", Font.BOLD, 20));
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