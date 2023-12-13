// 04. 게임 종료시 최종화면

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class _04_Memory_Gameover extends JFrame {

	private JTextArea t_display;
	private JLabel resultNum, resultStr;

	public BackgroundMusicPlayer musicPlayer;
	public JButton musicButton;


	public _04_Memory_Gameover() {
		super("게임 종료 화면 구성");

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
		// 메서드 호출
		buildGUI(_03_Memory_Game.getCurrentLevel());

		// 이미지를 표시할 패널 생성
		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/memory_Gameover.png");
		backgroundPanel.setSize(new Dimension(800, 600)); // 크기 설정
		add(backgroundPanel); // 프레임에 추가

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}


	// UI 요소를 구성하는 메서드 호출
	private void buildGUI(int level) {
		createResultPanel(level); // 최종 결과 표시 패널 생성 및 추가
		add(createInputPanel(), BorderLayout.SOUTH); // 입력 패널 생성 및 하단에 추가
	}


	// 입력 버튼 메서드
	private JPanel createInputPanel() {
		JPanel p = new JPanel(new GridLayout());
		JButton b_restart = new JButton("재시작하기");
		// "재시작하기" 버튼 클릭 시 동작 기능 설정
		b_restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_03_Memory_Game newGameUI = new _03_Memory_Game();
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
	private void createResultPanel(int level) {
		resultNum = new JLabel("최종 점수: " + level + " 점");
		resultStr = new JLabel(getResultMessage(level));

		// 라벨 폰트 설정
		resultNum.setFont(new Font("굴림", Font.BOLD, 35));
		resultStr.setFont(new Font("굴림", Font.BOLD, 23));

		// 라벨 위치 설정 (using setBounds)
		resultNum.setBounds(480, 100, 500, 50);
		resultStr.setBounds(480, 150, 500, 50);

		// 라벨 추가
		add(resultNum);
		add(resultStr);
	}


	// 각 레벨에 따른 결과 메시지 반환
	private String getResultMessage(int level) {
		if (level == 1) {
			return "재검진이 시급합니다";
		} else if (level == 2) {
			return "재검진이 필요합니다";
		} else if (level == 3) {
			return "추가 검진이 시급합니다";
		} else if (level == 4) {
			return "추가 검진을 권장합니다";
		} else if (level == 5) {
			return "주기적 검진이 필요합니다";
		} else if (level == 6) {
			return "주기적 검진을 권장합니다";
		} else if (level == 7) {
			return "정상입니다";
		} else if (level == 8) {
			return "정상입니다";
		} else if (level == 9) {
			return "정상입니다";
		} else {
			return "정상입니다";
		}

	}


	// 프로그램 이미지 바탕화면 클래스
	public class ImagePanel extends JPanel {
		private ImageIcon imageIcon;


		public ImagePanel(String imagePath) {
			imageIcon = new ImageIcon(getClass().getResource(imagePath));
			// 이미지 크기에 맞게 Panel 크기 설정
			setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight())); // 설정
		}


		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}


	public static void main(String[] args) {
		new _04_Memory_Gameover(); // 객체 호출
	}
}