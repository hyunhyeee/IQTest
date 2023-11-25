// 01. 게임 첫 시작 호면

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


// 시작화면
public class _01StartUI extends JFrame {
	private Clip backgroundClip;
	private boolean isMusicOn = true; // 음악 상태를 저장할 변수
	private long pausedTime = 0; // 멈춘 시간을 저장할 변수


	public _01StartUI() {
		setLayout(null); // 레이아웃 종류 설정
		playBackgroundMusic();
		// "당신의 지능은 몇살인가요?"라는 제목을 가진 라벨 생성
		JLabel title = new JLabel("두뇌 건강 테스트");
		title.setBounds(210, 100, 1000, 100); // 위치 크기 설정
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
		s_button.setBounds(530, 390, 165, 120); // 버튼 위치와 크기 설정
		s_button.setContentAreaFilled(false); // 배경색 제거
		s_button.setBorderPainted(false); // 테두리 없애기
		s_button.setFocusPainted(false); // 글씨 테두리 없애기

		add(s_button); // 프레임에 추가

		Font buttonFont = s_button.getFont();
		s_button.setFont(new Font(buttonFont.getName(), Font.BOLD, 20)); // 버튼 폰트 설정

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

		JButton musicButton = new JButton(scaledSoundOnIcon); // 초기 이미지 아이콘 설정
		musicButton.setBounds(720, 0, 65, 40);
		musicButton.setContentAreaFilled(false); // 배경색 제거
		musicButton.setBorderPainted(false); // 테두리 없애기
		musicButton.setFocusPainted(false); // 글씨 테두리 없애기
		add(musicButton);

		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isMusicOn) {
					stopBackgroundMusic();
					musicButton.setIcon(scaledSoundOffIcon); // 음악이 꺼진 상태의 이미지로 변경
				} else {
					playBackgroundMusic();
					musicButton.setIcon(scaledSoundOnIcon); // 음악이 켜진 상태의 이미지로 변경
				}

				isMusicOn = !isMusicOn;
			}
		});
		// 이미지를 표시할 패널 생성
		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/test_pic.jpg");
		backgroundPanel.setSize(new Dimension(800, 600)); // 크기 설정
		add(backgroundPanel); // 프레임에 추가

		setTitle("지능테스트 첫 화면"); // 타이틀 설정
		setSize(800, 600); // 크기 설정
		setLocationRelativeTo(null); // 호면 중앙에 배치
		setResizable(false); // 크기 조절 불가능하게 설정

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 설정
		setVisible(true); // 프레임이 보이도록 설정

	}


	private void playBackgroundMusic() {
		try {
			String audioFilePath = "/audioFile/b_sound.wav"; // 오디오 파일 경로
			AudioInputStream audioStream = AudioSystem
			    .getAudioInputStream(getClass().getResource(audioFilePath));
			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			backgroundClip = (Clip) AudioSystem.getLine(info);
			backgroundClip.open(audioStream);

			// 볼륨조절
			FloatControl gainControl = (FloatControl) backgroundClip
			    .getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-25.0f);

			// 일시정지된 시간을 빼서 원래 위치에서 재생
			backgroundClip.setMicrosecondPosition(
			    pausedTime);

			backgroundClip.start();
			backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			e.printStackTrace();
		}

	}


	private void stopBackgroundMusic() {
		if (backgroundClip != null && backgroundClip.isOpen()) {
			pausedTime = backgroundClip.getMicrosecondPosition(); // 일시정지된 시간 저장
			backgroundClip.stop();
			backgroundClip.close();
		}

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
		new _01StartUI(); // 시작화면 객체 설정
	}

}