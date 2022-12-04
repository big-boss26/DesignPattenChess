package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
public class StartUI extends JFrame
{



    JButton game_wuziqi=new JButton("开始五子棋游戏");
    JButton game_weiqi=new JButton("开始围棋游戏");

    JLabel boardInfo = new JLabel("棋盘大小:", JLabel.CENTER);
    JComboBox chooseBoardSize = new JComboBox();
    JButton exit=new JButton("退出游戏");
    JLabel label=new JLabel("欢迎来到五子棋游戏");
    int line_num=0;
    String chess_type;
    public StartUI() throws HeadlessException
    {
        initWindow();
        getContentPane().setLayout(new BorderLayout(0, 0));
        JPanel panel=new ImagePanel();
        getContentPane().add(panel);
        panel.setLayout(null);
        for(int i=8;i<20;i++)
            chooseBoardSize.addItem(i);
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 20));
        label.setBounds(175,90,300,45);
        game_wuziqi.setBounds(50,150,200,25);
        game_weiqi.setBounds(300,150,200,25);
        boardInfo.setBounds(50,180,100,25);
        chooseBoardSize.setBounds(150,180,100,25);

        exit.setBounds(300,180,200,25);

        this.joinGame_wuziqi();
        this.isExit();
        this.joinGame_weiqi();
        label=new JLabel("欢迎来到"+chess_type+"游戏");
        panel.add(label);
        panel.add(game_wuziqi);
        panel.add(game_weiqi);
        panel.add(boardInfo);
        panel.add(chooseBoardSize);
        panel.add(exit);
        this.setVisible(true);


        chooseBoardSize.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String _board_size = chooseBoardSize.getSelectedItem().toString();
                line_num=Integer.parseInt(String.valueOf(_board_size));
            }
        });
    }

    private void initWindow()
    {
        this.setTitle("棋类游戏");
        int window_width = 537;
        int window_height = 357;
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
                int result = JOptionPane.showConfirmDialog(StartUI.this, "确认退出?", "确认", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION)
                {
                    System.exit(0);
                }
            }
        });
    }

    private void joinGame_wuziqi()
    {
        game_wuziqi.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chess_type="五子棋";
                ArrayList<Integer> lineChoose=new ArrayList<>();
                for(int i=8;i<20;i++)
                    lineChoose.add(i);

                if (lineChoose.contains(line_num)) {
                        StartUI.this.dispose();
                        new ModeUI(line_num, chess_type);
                }else {
                    JOptionPane.showMessageDialog(StartUI.this, "请选择棋盘大小!");
                }
            }

        });
    }
    private void joinGame_weiqi()
    {
        game_weiqi.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chess_type="围棋";
                if(line_num==9||line_num==13||line_num==19) {
                    StartUI.this.dispose();
                    new ModeUI(line_num,chess_type);
                }else {
                    JOptionPane.showMessageDialog(StartUI.this, "请选择棋盘大小!(仅支持9，13，19)");

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