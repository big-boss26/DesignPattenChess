package UI;

import Game.TwoPlayersGame;
import Observer.Obs;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game extends JFrame implements Obs
{
    int window_width=1000;
    int window_height=800;
    int line_num;
    int grid;
    String chess_type;
    public Game(int line_num, String chess_type) throws HeadlessException, IOException, InterruptedException
    {

        this.line_num=line_num;
        this.chess_type=chess_type;
        this.grid = (this.window_height-200) / (line_num - 1) ;
        //this.grid=40;
        this.initWindow();
        this.setLayout(new BorderLayout());

//        InfoPanel infoPanel = new InfoPanel();
        TwoPlayersGame chessboardPanel = new TwoPlayersGame(line_num,grid,this.chess_type);
        ControlPanel controlPanel = new ControlPanel(chessboardPanel);

        ControlPanel.BackHome.attach(this);
        this.add(chessboardPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.NORTH);


        this.setVisible(true);
    }


    private void initWindow()
    {
        this.setTitle(chess_type+"游戏");
        this.setSize(window_width, window_height);
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((screen_width - window_width) / 2, (screen_height - window_height) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    @Override
    public void response() throws InterruptedException
    {
        System.out.println("----");
        this.dispose();
        new StartUI();
    }
}
