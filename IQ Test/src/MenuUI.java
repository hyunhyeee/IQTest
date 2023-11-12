// 메뉴 화면 구성

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuUI extends JFrame {
	
    public MenuUI() {
    	
    	JFrame f = new JFrame();
    	
    	JButton btn1 = new JButton();
    	JButton btn2 = new JButton();
    	JButton btn3 = new JButton();
    	Container c = f.getContentPane();
    	
    	f.setTitle("메뉴 화면 구성");
    	f.setSize(1000, 600);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	c.setLayout(null);
    	
    	btn1.setText("산술문제");
    	btn2.setText("속담 맞추기");
    	btn3.setText("상식 테스트");
    	
    	btn1.setBounds(200, 50, 500, 60);
    	btn2.setBounds(200, 120,500, 60);
    	btn3.setBounds(200, 190, 500, 60);
    	
    	c.add(btn1);
    	c.add(btn2);
    	c.add(btn3);
    	
    	f.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuUI();
    }
}