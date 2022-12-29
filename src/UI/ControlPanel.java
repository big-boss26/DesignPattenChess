package UI;

import Game.TwoPlayersGame;
import Memorandum.Memento;
import Observer.Obs;
import Observer.Subject;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ControlPanel extends JPanel
{

    JLabel gameInfo = new JLabel("游戏模式:", JLabel.CENTER);
    JButton startGame = new JButton("开始新游戏");
    JButton regretChess = new JButton("悔棋");
    JButton stopGame = new JButton("投子认负");
    JButton pass_weiqi = new JButton("不落子");
    JButton restoreGame = new JButton("打开文件");
    JButton saveGame = new JButton("保存文件");
    JButton playVedioGame = new JButton("播放录像");

    Frame f = new Frame("测试窗口");

    FileDialog load_fd = new FileDialog(f, "选择需要加载的文件", FileDialog.LOAD);
    FileDialog save_fd = new FileDialog(f, "选择需要保存的文件", FileDialog.SAVE);


    JButton exit = new JButton("返回主页");




    public ControlPanel(TwoPlayersGame chessboardPanel)
    {



        gameInfo.setFont(new Font(gameInfo.getFont().getName(), gameInfo.getFont().getStyle(), 20));



        saveGame.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打印用户选择的文件路径和名称


                save_fd.setDirectory("./userManage");
                save_fd.setVisible(true);
                String directory = save_fd.getDirectory();
                String file = save_fd.getFile();
                try {
                    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(directory+file));
                    // 按照题目要求储存
                    output.writeObject(chessboardPanel.getChessBoard().saveMemento());

                    output.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        System.out.println("-------------------------------");
        restoreGame.addActionListener(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                load_fd.setVisible(true);

                String directory = load_fd.getDirectory();
                String file = load_fd.getFile();
                try {
                    ObjectInputStream  input = new ObjectInputStream(new FileInputStream(directory+file));
                    // 按照题目要求储存
                     Object memento=input.readObject();
                     //chessboardPanel.get_ChessBoard().setChessInfo((ArrayList)_chessInfo);
                    if(memento!=null) {
                        JOptionPane.showMessageDialog(chessboardPanel, chessboardPanel.getChessBoard().loadMemento((Memento) memento));
                        chessboardPanel.repaint();
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                catch (ClassNotFoundException e1){
                    e1.printStackTrace();
                }

            }
        });


        stopGame.addActionListener(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chessboardPanel.getChessBoard().getCanPlay()) {
                    if (chessboardPanel.getChessBoard().getIsBlack()){

                        JOptionPane.showMessageDialog(chessboardPanel, "白方获胜，游戏结束!");

                        chessboardPanel.getChessBoard().getTwoplayers()[1].setWinTimes(chessboardPanel.getChessBoard().getTwoplayers()[1].getWinTimes()+1);

                        chessboardPanel.getChessBoard().setCanPlay(false);
                    } else if (!(chessboardPanel.getChessBoard().getIsBlack())) {
                        JOptionPane.showMessageDialog(chessboardPanel, "黑方获胜，游戏结束!");
                        chessboardPanel.getChessBoard().getTwoplayers()[0].setWinTimes(chessboardPanel.getChessBoard().getTwoplayers()[0].getWinTimes()+1);
                        chessboardPanel.getChessBoard().setCanPlay(false);
                    }
                    chessboardPanel.getChessBoard().getTwoplayers()[0].setPlayTimes(chessboardPanel.getChessBoard().getTwoplayers()[0].getPlayTimes()+1);
                    chessboardPanel.getChessBoard().getTwoplayers()[1].setPlayTimes(chessboardPanel.getChessBoard().getTwoplayers()[1].getPlayTimes()+1);
                    chessboardPanel.getChessBoard().getTwoplayers()[0].savePlayerData();
                    chessboardPanel.getChessBoard().getTwoplayers()[1].savePlayerData();
                }
                else {
                    JOptionPane.showMessageDialog(chessboardPanel, "请点击开始新游戏");
                }
            }
        });

        playVedioGame.addActionListener(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                load_fd.setVisible(true);
                String directory = load_fd.getDirectory();
                String file = load_fd.getFile();
                try {
                    ObjectInputStream  input = new ObjectInputStream(new FileInputStream(directory+file));
                    // 按照题目要求储存
                    Object memento=input.readObject();
                    if(memento!=null) {

                        //chessboardPanel.get_ChessBoard().setChessInfo((ArrayList)_chessInfo);
                        JOptionPane.showMessageDialog(chessboardPanel, chessboardPanel.getChessBoard().loadMemento((Memento) memento));
                        new Thread(new Runnable() {
                            public void run() {
                                for (int i = 0; i < ((Memento) memento).getChessInfo().size(); i++) {
                                    chessboardPanel.repaint();
                                    chessboardPanel.getChessBoard().loadMemento((Memento) ((Memento) memento).getChessInfo().get(i));
                                    try {
                                        Thread.sleep(1000);//帧率
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }).start();
                        chessboardPanel.getChessBoard().loadMemento((Memento) memento);
                        chessboardPanel.repaint();
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                catch (ClassNotFoundException e1){
                    e1.printStackTrace();
                }



            }
        });


        this.newGame(chessboardPanel);
        this.backPrevious(chessboardPanel);
        this.isExit();

        this.add(startGame);
        this.add(stopGame);
        if(chessboardPanel.getChessBoard().getChessType().equals("围棋")){
            pass_weiqi.addActionListener(new AbstractAction(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(chessboardPanel, chessboardPanel.getChessBoard().pass());
                    String winRes=chessboardPanel.getChessBoard().checkWin(0, 0);
                    switch (winRes){
                        case "黑方获胜，游戏结束!":
                            chessboardPanel.getChessBoard().getTwoplayers()[0].setWinTimes(chessboardPanel.getChessBoard().getTwoplayers()[0].getWinTimes()+1);

                            break;
                        case"白方获胜，游戏结束!":
                            chessboardPanel.getChessBoard().getTwoplayers()[1].setWinTimes(chessboardPanel.getChessBoard().getTwoplayers()[1].getWinTimes()+1);
                            break;
                        default:
                            break;
                    }
                    if (!winRes.equals("胜负未分")) {
                        chessboardPanel.getChessBoard().getTwoplayers()[0].setPlayTimes(chessboardPanel.getChessBoard().getTwoplayers()[0].getPlayTimes()+1);
                        chessboardPanel.getChessBoard().getTwoplayers()[1].setPlayTimes(chessboardPanel.getChessBoard().getTwoplayers()[1].getPlayTimes()+1);
                        chessboardPanel.getChessBoard().getTwoplayers()[0].savePlayerData();
                        chessboardPanel.getChessBoard().getTwoplayers()[1].savePlayerData();
                        JOptionPane.showMessageDialog(chessboardPanel, winRes);
                    }
                }
            });
            this.add(pass_weiqi);
        }
        this.add(regretChess);
        this.add(saveGame);
        this.add(restoreGame);
        this.add(playVedioGame);
        this.add(exit);

    }

    private void isExit()
    {
        exit.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    new BackHome().back();
                }
                catch (InterruptedException interruptedException)
                {
                    interruptedException.printStackTrace();
                }
            }
        });
    }


    private void newGame(TwoPlayersGame _chessboardPanel)
    {
        startGame.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _chessboardPanel.getChessBoard().setCanPlay(true);
                _chessboardPanel.getChessBoard().clearChessBoard();
                _chessboardPanel.getChessBoard().getChessInfo().clear();
                _chessboardPanel.getChessBoard().saveMemento();
                _chessboardPanel.repaint();
                JOptionPane.showMessageDialog(ControlPanel.this, "游戏已开始");
            }
        });
    }



    private void backPrevious(TwoPlayersGame _chessboardPanel)
    {
        regretChess.addActionListener(new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {

                System.out.println("开始悔棋");
                //_chessboardPanel.get_ChessBoard().restoreMemento();
                JOptionPane.showMessageDialog(_chessboardPanel, _chessboardPanel.getChessBoard().restoreMemento());
                _chessboardPanel.repaint();

            }

        });
    }

    class BackHome extends Subject
    {
        @Override
        public void back() throws InterruptedException
        {
            for (Object obs:observers)
            {
                ((Obs)obs).response();
            }
        }
    }

}
