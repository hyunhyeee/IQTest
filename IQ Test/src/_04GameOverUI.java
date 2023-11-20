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

        buildGUI(_03GameUI.getCurrentLevel());

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setResizable(false);

        setVisible(true);
    }

    private void buildGUI(int level) {
        add(createDisplayPanel());
        add(createResultPanel(level));
        add(createInputPanel(), BorderLayout.SOUTH);
    }

    private JPanel createDisplayPanel() {
        JPanel p = new JPanel(null);
        t_display = new JTextArea();
        t_display.setEditable(false);
        return p;
    }

    private JPanel createInputPanel() {
        JPanel p = new JPanel(new GridLayout());
        JButton b_restart = new JButton("재시작하기");
        b_restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	_03GameUI newGameUI = new _03GameUI();
                newGameUI.setVisible(true);
                dispose();
            }
        });
        
        JButton b_exit = new JButton("나가기");
        b_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	_02MenuUI firstFrame = new _02MenuUI();
                firstFrame.setVisible(true);
                dispose();
            }
        });
        pack();
        b_restart.setPreferredSize(new Dimension(b_restart.getPreferredSize().width, 50));
        b_exit.setPreferredSize(new Dimension(b_exit.getPreferredSize().width, 50));
        
        p.add(b_restart);
        p.add(b_exit);
        
        Font buttonFont = b_restart.getFont();
        b_restart.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
        b_exit.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
        return p;
    }

    private JPanel createResultPanel(int level) {
        JPanel p = new JPanel(null);
        resultNum = new JLabel("최종 점수: " + level + " 점");
        resultStr = new JLabel(getResultMessage(level));

        resultNum.setFont(new Font("굴림", Font.BOLD, 35));
        resultStr.setFont(new Font("굴림", Font.BOLD, 23));

        resultNum.setHorizontalAlignment(SwingConstants.CENTER);
        resultStr.setHorizontalAlignment(SwingConstants.CENTER);

        resultNum.setBounds(149, 209, 471, 87);
        resultStr.setBounds(159, 288, 453, 63);

        p.add(resultNum);
        p.add(resultStr);

        return p;
    }

    private String getResultMessage(int level) {
        if (level == 1) {
            return "레벨 1: 혹시 당신은... 0살? ";
        } else if (level == 2) {
            return "레벨 2: 뇌가 순수하시네요";
        } else if (level == 3) {
            return "레벨 3: 혹시 몇짤이신가요??";
        } else if (level == 4) {
            return "레벨 4: 이걸 틀리셨어요???";
        } else if (level == 5) {
            return "레벨 5: 반타작은 하셨군요";
        } else if (level == 6) {
            return "레벨 6: 오 당신은 평균 이상이군요";
        } else if (level == 7) {
            return "레벨 7: 오 좀 치시는군요";
        } else if (level == 8) {
            return "레벨 8: HOXY 노력형 천재??";
        } else if (level == 9) {
            return "레벨 9: 천재??";
        } else {
            return "레벨 10: 맨사 가즈아~~";
        }  
    }

    public static void main(String[] args) {
        new _04GameOverUI();
    }
}