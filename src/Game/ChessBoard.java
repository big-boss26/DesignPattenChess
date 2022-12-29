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
    String chessType;
    GamePlayer[] twoPlayers=new GamePlayer[2];

    public ChessBoard(){}
    public ChessBoard(int _line_num,String _chess_type) {
        this.grid_num = _line_num-1;
        this.line_num = _line_num;
        this.chess = new String[this.line_num][this.line_num];
        for (int i = 0; i < this.line_num; i++) {
            for (int j = 0; j < this.line_num; j++) {
                chess[i][j] = "null";
//                select[i][j] = false;
            }
        }
        this.chessType=_chess_type;

        //this.saveMemento();


    }

    //悔棋
    public String restoreMemento() {
        if (canPlay) {
            if (haveChess()) {
                if (regretChance == 1&&chessInfo.size() > 1) {
                    regretChance=0;
                    Memento memento;
                    memento = (Memento) chessInfo.get(chessInfo.size() - 2);
                    chessCopy(this.chess, memento.getChess());
                    //chessCopy(this.chess, memento.getChessBoard().chess);
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


        if(memento.getChess().length==this.chess[0].length&&
                memento.getChessType().equals(this.chessType)&&
                memento.getTwoPlayers()[0].getUserID().equals(this.twoPlayers[0].getUserID())&&
                memento.getTwoPlayers()[1].getUserID().equals(this.twoPlayers[1].getUserID())) {
            if (!canPlay) {
                canPlay = true;
            }
            this.chessCopy(this.chess,memento.getChess());
            this.isBlack=memento.getIsBlack();
            this.canPlay=memento.getCanPlay();
            this.regretChance=memento.getRegretChance();
            this.black_pass=memento.getBlack_pass();
            this.white_pass=memento.getWhite_pass();
            this.setChessInfo(memento.getChessInfo());
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
        Memento memento = new Memento(this); // 深复制
        this.chessInfo.add(memento);
        return memento;
    }

    public void chessCopy(String[][] str1, String[][] str2)
    {
        for (int i = 0; i < str2.length; i++)
        {
            for (int j = 0; j < str2.length; j++)
            {
                str1[i][j] = str2[i][j];
            }
        }
    }

    public boolean haveChess()
    {
        for (int i = 0; i < this.chess.length; i++)
        {
            for (int j = 0; j < this.chess.length; j++)
            {
                if (!chess[i][j].equals("null"))
                {
                    return true;
                }
            }
        }
        return false;
    }
    /*判断棋盘是否下满,是返回true*/
    public boolean haveAllChess()
    {
        int cnt=0;
        for (int i = 0; i < this.chess.length; i++)
        {
            for (int j = 0; j < this.chess.length; j++)
            {
                if (!chess[i][j].equals("null"))
                {
                    cnt++;
                }
            }
        }
        if(cnt==this.chess.length*this.chess.length){
            return true;
        }
        else {
            return false;
        }
    }

    public  boolean isGameFinished(String[][] board){
        return !(hasAnyMoves(board,"white") || hasAnyMoves(board,"black"));
    }

    public  int getTotalStoneCount(String[][] board){
        int c = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j].equals("null")) c++;
            }
        }
        return c;
    }

    public  int getPlayerStoneCount(String[][] board, String player){
        int score = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j] == player) score++;
            }
        }
        return score;
    }
    public abstract String pass();

    //之前没注意命名问题，这里统一
    public abstract String getDown(int x,  int y);


    public String[][] getChess(){
        return this.chess;
    }
    public void setChessInfo(ArrayList chessInfo)
    {
        this.chessInfo = (ArrayList)chessInfo.clone();;
    }
    public abstract   String checkWin(int x,int y);
    public void setChess(String[][] _chess)
    {
        this.grid_num = _chess.length-1;
        this.line_num = _chess.length;
        this.chess = new String[_chess.length][_chess.length];
        for (int i = 0; i < _chess.length; i++)
        {
            for (int j = 0; j < _chess.length; j++)
            {
                chess[i][j] = _chess[i][j];
            }
        }
    }
    public boolean hasAnyMoves(String[][] board,String players){
        return getAllPossibleMoves(board,players).size() > 0;
    }

    public  ArrayList<Point> getAllPossibleMoves(String[][] board,String players){
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j].equals("null")){
                    result.add(new Point(i,j));
                }
            }
        }
        return result;
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

    public void setRegretChanceValue(int _regretChance){
        this.regretChance=_regretChance;

    }

    public GamePlayer[] getTwoplayers(){
        return twoPlayers;
    }
    public void setTwoplayers(GamePlayer[] Twoplayers){

        twoPlayers=Twoplayers.clone();
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
    public  String getChessType(){
        return this.chessType;
    }
    public void clearChessBoard(){
        for (int i = 0; i < this.chess.length; i++) {
            for (int j = 0; j < this.chess.length; j++) {
                chess[i][j] = "null";
            }
        }
    }


}
