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
		setLayout(null);

		JLabel title = new JLabel("당신의 지능은 몇살인가요?");
		title.setBounds(110, 150, 1000, 100);
		title.setForeground(Color.BLACK);

		Font labelFont = title.getFont();
		title.setFont(new Font(labelFont.getName(), Font.PLAIN, 50));
		add(title);

		JButton s_button = new JButton("시작하기");
		s_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_02MenuUI secondFrame = new _02MenuUI();
				secondFrame.setVisible(true);
				dispose();
			}
		});
		pack();
		s_button.setBounds(585, 370, 150, 50);
		add(s_button);

		Font buttonFont = s_button.getFont();
		s_button.setFont(new Font(buttonFont.getName(), Font.BOLD, 20));

		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/java_pic.jpg");
		backgroundPanel.setSize(new Dimension(800, 600));
		add(backgroundPanel);

		setTitle("지능테스트 첫 화면");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}


	class ImagePanel extends JPanel {
		private ImageIcon imageIcon;


		public ImagePanel(String imagePath) {
			imageIcon = new ImageIcon(getClass().getResource(imagePath));
		}


		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}


	public static void main(String[] args) {
		new _01StartUI();
	}

}
