package Game;

import Memorandum.Memento;
import Piece.ChessFactory;
import Piece.ChessPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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

    String chess_type;
    int regret_chance;

    BufferedImage bi = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_ARGB);
    Graphics g2 = bi.createGraphics();



    ChessBoard chessBoard;

    public TwoPlayersGame(int line_num,int grid,String chess_type)
    {
       // super(chessboard_size);
        this.chess_type=chess_type;
        if (chess_type.equals("围棋"))
            this.chessBoard=new weiqi(line_num);
        else if (chess_type.equals("五子棋")) {
            this.chessBoard=new wuziqi(line_num);
        }

       // this.chessBoard.chessboard_size=chessboard_size;

        this.grid=grid;

        icon = new ImageIcon("img/棋盘.jpg");
        img = icon.getImage();

        ChessFactory chessFactory = new ChessFactory();
        blackChessImg = chessFactory.getChess("黑棋").getChess();
        whiteChessImg = chessFactory.getChess("白棋").getChess();
        selectedBlackChessImg = chessFactory.getChess("选中黑棋").getChess();
        selectedWhiteChessImg = chessFactory.getChess("选中白棋").getChess();
//        selectImg = selectIcon.getImage();

        this.addMouseListener(this);
        this.chessBoard.saveMemento();

    }

//    public void setSelect(int i, int j, boolean flag)
//    {
//        select[i][j] = flag;
//    }

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
        for (int i = 0; i < this.chessBoard.line_num; i++)
        {
            for (int j = 0; j < this.chessBoard.line_num; j++)
            {
                if (this.chessBoard.chess[i][j].equals("black"))
                {
                    int tempX = 160 + this.grid * i;
                    int tempY = 30 + this.grid * j;
                    if (i == x && j == y)
                    {
                        g2.drawImage(selectedBlackChessImg, tempX -15, tempY -15, this);
                    } else
                    {
                        g2.drawImage(blackChessImg, tempX - 15, tempY - 15, this);
                    }
                }
                if (this.chessBoard.chess[i][j].equals("white"))
                {
                    int tempX = 160 + this.grid * i;
                    int tempY = 30 + this.grid * j;
                    if (i == x && j == y)
                    {
                        g2.drawImage(selectedWhiteChessImg, tempX - 15, tempY - 15, this);
                    } else
                    {
                        g2.drawImage(whiteChessImg, tempX - 15, tempY - 15, this);
                    }
                }
            }
        }
        g.drawImage(bi, 0, 0, this);
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
                String result=this.chessBoard.get_down(x,y);
                if(!result.equals("落子成功")){
                    JOptionPane.showMessageDialog(this, result);
                }
                this.repaint();
                String winRes=this.chessBoard.checkWin(x, y);
                if (!winRes.equals("胜负未分")) {
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



    public ChessBoard get_ChessBoard(){
        return  this.chessBoard;
    }

    public void setCanPlay(boolean canPlay)
    {
        this.chessBoard.canPlay = canPlay;
    }

    public String[][] getChess()
    {
        return this.chessBoard.chess;
    }
    public  String getChess_type(){
        return this.chess_type;
    }




}

