package Game;

import Memorandum.Memento;
import Piece.ChessFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract  class ChessBoard {

    int grid_num;
    int line_num;
    String[][] chess;

    boolean canPlay = false;
    ArrayList chessInfo = new ArrayList();
    boolean isBlack = true;
    int regretChance = 0;
    int black_pass=0;
    int white_pass=0;

    public ChessBoard(int line_num) {
        this.grid_num = line_num-1;
        this.line_num = line_num;
        this.chess = new String[this.line_num][this.line_num];
        for (int i = 0; i < this.line_num; i++) {
            for (int j = 0; j < this.line_num; j++) {
                chess[i][j] = "null";
//                select[i][j] = false;
            }
        }


        //this.saveMemento();


    }

    //悔棋
    public String restoreMemento() {
        if (canPlay) {
            if (haveChess()) {
                if (regretChance == 1&&chessInfo.size() > 1) {
                    regretChance=0;
                    Memento memento1;
                    memento1 = (Memento) chessInfo.get(chessInfo.size() - 2);
                    chessCopy(this.chess, memento1.getChess());
                    chessInfo.remove(chessInfo.size() - 1);
                    this.isBlack = !this.isBlack;

                    if (!canPlay) {
                        canPlay = true;
                    }
                        //this.repaint();
                    return "悔棋成功";
                } else {
                    //JOptionPane.showMessageDialog(this, "棋盘上没有棋子无法悔棋");
                    return "无法连续悔棋";
                }
            }
//           showArray();
            else {
                //JOptionPane.showMessageDialog(this, "棋盘上没有棋子无法悔棋");
                return "棋盘上没有棋子无法悔棋";
            }
        } else {
            //JOptionPane.showMessageDialog(this, "游戏尚未开始，无法悔棋");
            return "游戏尚未开始，无法悔棋";
        }
    }


    public String loadMemento(Memento memento) {

        chessCopy(this.chess, memento.getChess());
        if(memento.getChess()[0].length==this.chess[0].length) {
            if (!canPlay) {
                canPlay = true;
            }
            //this.repaint();
            return  "加载存档文件成功";
        }
        else
        {
            //JOptionPane.showMessageDialog(this, "存档不符合，无法读取");
            return  "存档不符合，无法读取";
        }
}

    public Memento saveMemento()
    {
        Memento memento1 = new Memento(chess,this.line_num); // 深复制
        this.chessInfo.add(new Memento(chess,this.line_num));
        return memento1;
    }

    public void chessCopy(String[][] str1, String[][] str2)
    {
        for (int i = 0; i < this.line_num; i++)
        {
            for (int j = 0; j < this.line_num; j++)
            {
                str1[i][j] = str2[i][j];
            }
        }
    }

    public boolean haveChess()
    {
        for (int i = 0; i < this.line_num; i++)
        {
            for (int j = 0; j < this.line_num; j++)
            {
                if (!chess[i][j].equals("null"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean haveAllChess()
    {
        int cnt=0;
        for (int i = 0; i < this.line_num; i++)
        {
            for (int j = 0; j < this.line_num; j++)
            {
                if (!chess[i][j].equals("null"))
                {
                    cnt++;
                }
            }
        }
        if(cnt==line_num*line_num){
            return true;
        }
        else {
            return false;
        }
    }
    public abstract String pass();

    public abstract String get_down(int x,  int y);

    public void setChessInfo(ArrayList chessInfo)
    {
        this.chessInfo = chessInfo;
    }
    public abstract   String checkWin(int x,int y);
    public void setChess(String[][] chess)
    {
        this.chess = chess;
    }
    public void setCanPlay(boolean canPlay)
    {
        this.canPlay = canPlay;
    }
    public void setIsBlack(boolean IsBlack)
    {
        this.isBlack = IsBlack;
    }
    public void setBlack_pass(int black_pass){
        this.black_pass=black_pass;
    }
    public void setWhite_pass(int black_pass){
        this.white_pass=black_pass;
    }
    public int getWhite_pass(){
        return this.white_pass;
    }
    public int getBlack_pass(){
        return this.black_pass;
    }
    public void setRegretChance(){
        if(this.regretChance==0)
            this.regretChance+=1;
    }
    public int getRegretChance(){
        return  this.regretChance;
    }
    public boolean getCanPlay()
    {
        return this.canPlay;
    }
    public boolean getIsBlack()
    {
        return isBlack;
    }
    public ArrayList getChessInfo()
    {
        return chessInfo;
    }
    public void clearChessBoard(){
        for (int i = 0; i < this.line_num; i++) {
            for (int j = 0; j < this.line_num; j++) {
                chess[i][j] = "null";
            }
        }
    }


}
