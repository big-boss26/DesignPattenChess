package Game;

import Piece.ChessFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class TwoPlayersGame extends JPanel implements MouseListener
{
    // 背景图片
    ImageIcon icon;
    Image img, blackChessImg, whiteChessImg, selectedBlackChessImg, selectedWhiteChessImg, selectImg;
    //消息
    String message = "黑方先行";
    //棋子位置
    int x = 0;
    int y = 0;
    int grid;
    int piece_offset=0;

    BufferedImage bi = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_ARGB);
    Graphics g2 = bi.createGraphics();

    private  ChessBoard chessBoard;
    Timer blackPlayerHandlerTimer;
    Timer whitePlayerHandlerTimer;
    public TwoPlayersGame(GamePlayer[] twoplayers,int line_num,int grid,String chess_type)
    {
       // super(chessboard_size);
        //twoPlayers[0]=new GamePlayer("黑方");
        //twoPlayers[1]=new GamePlayer("白方");
        if (chess_type.equals("围棋"))
            this.chessBoard=new weiqi(line_num,chess_type);
        else if (chess_type.equals("五子棋")) {
            this.chessBoard=new wuziqi(line_num,chess_type);
        }else if(chess_type.equals("黑白棋")) {
            this.chessBoard = new heibaiqi(line_num,chess_type);
            piece_offset=grid/2;
        }
        chessBoard.setTwoplayers(twoplayers);



        this.grid=grid;

        icon = new ImageIcon("img/棋盘.jpg");
        img = icon.getImage();

        ChessFactory chessFactory = new ChessFactory();
        blackChessImg = chessFactory.getChess("黑棋").getChess();
        whiteChessImg = chessFactory.getChess("白棋").getChess();
        selectedBlackChessImg = chessFactory.getChess("选中黑棋").getChess();
        selectedWhiteChessImg = chessFactory.getChess("选中白棋").getChess();

        this.addMouseListener(this);

        //AI Handler Timer (to unfreeze gui)
        blackPlayerHandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(chessBoard.getTwoplayers()[0]);
            blackPlayerHandlerTimer.stop();
            manageTurn();
        });

        whitePlayerHandlerTimer = new Timer(1000,(ActionEvent e) -> {
            handleAI(chessBoard.getTwoplayers()[1]);
            whitePlayerHandlerTimer.stop();
            manageTurn();
        });

        manageTurn();

        this.chessBoard.saveMemento();

    }
    public void manageTurn(){
        if(chessBoard.hasAnyMoves(chessBoard.chess,chessBoard.getTwoplayers()[0].getPieseType())||chessBoard.hasAnyMoves(chessBoard.chess,chessBoard.getTwoplayers()[1].getPieseType())) {
            if (chessBoard.getIsBlack()) {
                if(chessBoard.hasAnyMoves(chessBoard.chess,chessBoard.getTwoplayers()[0].getPieseType())) {
                    if (chessBoard.getTwoplayers()[0].getUserType().equals("用户")||chessBoard.getTwoplayers()[0].getUserType().equals("游客")) {
                        //awaitForClick = true;
                        chessBoard.setCanPlay(true);

                        //after click this function should be call backed
                    } else {
                        blackPlayerHandlerTimer.start();
                    }
                }
                else {
                    chessBoard.setIsBlack(false);
                    manageTurn();
                }
            } else {
                if(chessBoard.hasAnyMoves(chessBoard.chess,chessBoard.getTwoplayers()[1].getPieseType())) {
                    if (chessBoard.getTwoplayers()[1].getUserType().equals("用户")||chessBoard.getTwoplayers()[1].getUserType().equals("游客")) {
                        //awaitForClick = true;
                        chessBoard.setCanPlay(true);
                        //after click this function should be call backed
                    } else {
                        whitePlayerHandlerTimer.start();
                    }
                }else {
                    chessBoard.setIsBlack(true);
                    manageTurn();
                }
            }
        }else{
            //game finished
            System.out.println("Game Finished !");

            String winRes=this.chessBoard.checkWin(x, y);
            switch (winRes){
                case "黑方获胜，游戏结束!":
                    chessBoard.getTwoplayers()[0].setWinTimes(chessBoard.getTwoplayers()[0].getWinTimes()+1);

                    break;
                case"白方获胜，游戏结束!":
                    chessBoard.getTwoplayers()[1].setWinTimes(chessBoard.getTwoplayers()[1].getWinTimes()+1);
                    break;
                default:
                    break;
            }
            if (!winRes.equals("胜负未分")) {
                chessBoard.getTwoplayers()[0].setPlayTimes(chessBoard.getTwoplayers()[0].getPlayTimes()+1);
                chessBoard.getTwoplayers()[1].setPlayTimes(chessBoard.getTwoplayers()[1].getPlayTimes()+1);
                chessBoard.getTwoplayers()[0].savePlayerData();
                chessBoard.getTwoplayers()[1].savePlayerData();
                JOptionPane.showMessageDialog(this, winRes);
            }
        }
    }


    public void paint(Graphics g)
    {
        //双缓冲技术
        g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        g2.setColor(Color.BLACK);
        float width = 2.0f;//你需要的宽度
        Graphics2D g3 = (Graphics2D) g2;//之前有一个Graphics的对象为g
        g3.setStroke(new BasicStroke(width));
        for (int i = 0; i < this.chessBoard.grid_num; i++)
        {
            g2.drawLine(160, 30 + this.grid * i, 160+(this.chessBoard.grid_num)*this.grid, 30 + this.grid * i);
            g2.drawLine(160 + this.grid * i, 30, 160 + this.grid * i, 30+(this.chessBoard.grid_num)*this.grid);
        }
        g2.drawLine(160, 30 + this.grid * this.chessBoard.grid_num, 160+(this.chessBoard.grid_num)*this.grid, 30 + this.grid * this.chessBoard.grid_num);
        g2.drawLine(160 + this.grid *this.chessBoard.grid_num, 30, 160 + this.grid * this.chessBoard.grid_num, 30+(this.chessBoard.grid_num)*this.grid);
        for (int i = 0; i < this.chessBoard.chess.length; i++)
        {
            for (int j = 0; j < this.chessBoard.chess.length; j++)
            {
                if (this.chessBoard.chess[i][j].equals("black"))
                {
                    int tempX = 160 + this.grid * i;
                    int tempY = 30 + this.grid * j;
                    if (i == x && j == y)
                    {
                        g2.drawImage(selectedBlackChessImg, tempX -15+piece_offset, tempY -15+piece_offset, this);
                    } else
                    {
                        g2.drawImage(blackChessImg, tempX - 15+piece_offset, tempY - 15+piece_offset, this);
                    }
                }
                if (this.chessBoard.chess[i][j].equals("white"))
                {
                    int tempX = 160 + this.grid * i;
                    int tempY = 30 + this.grid * j;
                    if (i == x && j == y)
                    {
                        g2.drawImage(selectedWhiteChessImg, tempX - 15+piece_offset, tempY - 15+piece_offset, this);
                    } else
                    {
                        g2.drawImage(whiteChessImg, tempX - 15+piece_offset, tempY - 15+piece_offset, this);
                    }

                }
            }
        }
        g2.setColor(this.chessBoard.isBlack?Color.BLACK:Color.WHITE);
        g2.fillOval(500,650,30,30);
        Font f = new Font("楷体", Font.BOLD, 20);
        g2.setFont(f);
        if(chessBoard.getTwoplayers()[0].getUserType().equals("用户")){
            g2.drawString("黑棋昵称："+chessBoard.getTwoplayers()[0].getUserName(),5, 50);
            g2.drawString("黑棋ID："+chessBoard.getTwoplayers()[0].getUserID(),5, 70);

            g2.drawString("总对局数："+String.valueOf(chessBoard.getTwoplayers()[0].getPlayTimes()), 5,90);
            g2.drawString("总胜利数："+String.valueOf(chessBoard.getTwoplayers()[0].getWinTimes()), 5,110);

        }else{
            g2.drawString("黑棋身份："+chessBoard.getTwoplayers()[0].getUserType(),5, 50);
            g2.drawString("总对局数："+String.valueOf(chessBoard.getTwoplayers()[0].getPlayTimes()), 5,70);
            g2.drawString("总胜利数："+String.valueOf(chessBoard.getTwoplayers()[0].getWinTimes()), 5,90);

        }

        if(chessBoard.getTwoplayers()[1].getUserType().equals("用户")) {
            g2.drawString("白棋昵称：" + chessBoard.getTwoplayers()[1].getUserName(), 810, 50);
            g2.drawString("白棋ID："+chessBoard.getTwoplayers()[1].getUserID(),810, 70);
            g2.drawString("总对局数：" + String.valueOf(chessBoard.getTwoplayers()[1].getPlayTimes()), 810, 90);
            g2.drawString("总胜利数：" + String.valueOf(chessBoard.getTwoplayers()[1].getWinTimes()), 810, 110);

        }else{
            g2.drawString("白棋身份：" + chessBoard.getTwoplayers()[1].getUserType(), 810, 50);
            g2.drawString("总对局数：" + String.valueOf(chessBoard.getTwoplayers()[1].getPlayTimes()), 810, 70);
            g2.drawString("总胜利数：" + String.valueOf(chessBoard.getTwoplayers()[1].getWinTimes()), 810, 90);

        }
        g.drawImage(bi, 0, 0, this);

    }

    public void handleAI(GamePlayer ai){
        if (this.chessBoard.canPlay) {

            Point aiPlayPoint = ai.play(chessBoard);
            int i = aiPlayPoint.x;
            int j = aiPlayPoint.y;

            String result=this.chessBoard.getDown(i,j);

            if(!result.equals("落子成功")){
                JOptionPane.showMessageDialog(this, result);
            }
            //advance turn
            //chessBoard.isBlack = (chessBoard.isBlack == true) ? false : true;

            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (this.chessBoard.canPlay)
        {
            this.chessBoard.setRegretChance();
            x = e.getX();
            y = e.getY();
            if (x >= 155 && x <= 850 && y >= 20 && y <= 750)
            {
                x = (int) Math.rint((x - 155) / this.grid);
                y = (int) Math.rint((y - 20) /this.grid);
                String result=this.chessBoard.getDown(x,y);
                chessBoard.setCanPlay(false);

                if(!result.equals("落子成功")){
                    JOptionPane.showMessageDialog(this, result);
                }
                this.repaint();
                //awaitForClick = false;
                //callback
                manageTurn();
                String winRes=this.chessBoard.checkWin(x, y);
                switch (winRes){
                    case "黑方获胜，游戏结束!":
                        chessBoard.getTwoplayers()[0].setWinTimes(chessBoard.getTwoplayers()[0].getWinTimes()+1);

                        break;
                    case"白方获胜，游戏结束!":
                        chessBoard.getTwoplayers()[1].setWinTimes(chessBoard.getTwoplayers()[1].getWinTimes()+1);
                        break;
                    default:
                        break;
                }
                if (!winRes.equals("胜负未分")) {
                    chessBoard.getTwoplayers()[0].setPlayTimes(chessBoard.getTwoplayers()[0].getPlayTimes()+1);
                    chessBoard.getTwoplayers()[1].setPlayTimes(chessBoard.getTwoplayers()[1].getPlayTimes()+1);
                    chessBoard.getTwoplayers()[0].savePlayerData();
                    chessBoard.getTwoplayers()[1].savePlayerData();
                    JOptionPane.showMessageDialog(this, winRes);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "请点击开始新游戏");
        }
    }

    public String getMessage()
    {
        return message;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }



    public ChessBoard getChessBoard(){
        return  this.chessBoard;
    }



    /*
    public GamePlayer[] getTwoplays(){
        return twoPlayers;
    }
*/



}

