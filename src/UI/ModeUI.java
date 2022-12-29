package UI;

import User.LoginEventListener;
import User.LoginValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class ModeUI extends JFrame
{

    JButton game3=new JButton("开始双人游戏");
    JButton exit=new JButton("退出游戏");
    JLabel label;
    int line_num;
    String chess_type;

    public ModeUI(int line_num, String chess_type) throws HeadlessException
    {
        initWindow();
        this.chess_type=chess_type;
        this.line_num=line_num;
        label=new JLabel("欢迎来到"+chess_type+"游戏");
        getContentPane().setLayout(new BorderLayout(0, 0));
        JPanel panel=new ImagePanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 20));
        label.setBounds(175,90,300,45);
        game3.setBounds(50,300,200,25);
        exit.setBounds(300,300,200,25);

        this.joinGame3();
        this.isExit();

        panel.add(label);
        panel.add(game3);
        panel.add(exit);
        this.setVisible(true);
    }

    private void initWindow()
    {
        this.setTitle("棋类游戏");
        int window_width = 537;
        int window_height = 380;
        this.setSize(window_width, window_height);
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((screen_width - window_width) / 2, (screen_height - window_height) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void isExit()
    {
        exit.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int result = JOptionPane.showConfirmDialog(ModeUI.this, "确认退出?", "确认", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION)
                {
                    System.exit(0);
                }
            }
        });
    }


    private void joinGame3()
    {
        game3.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ModeUI.this.dispose();
                {
                    new LoginValidator(line_num,chess_type);
                }
            }
        });
    }

    class ImagePanel extends JPanel
    {
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ImageIcon icon = new ImageIcon("img/背景图片.jpeg");
            g.drawImage(icon.getImage(), 0, 0, null);

        }
    }

}