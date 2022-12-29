package Memorandum;
import Game.ChessBoard;
import Game.GamePlayer;

import java.io.Serializable;
import java.util.ArrayList;

public class Memento implements Serializable
{
    protected String [][] chess;



    protected boolean canPlay = false;
    protected ArrayList chessInfo = new ArrayList();
    protected boolean isBlack = true;
    protected int regretChance = 0;
    protected int black_pass=0;
    protected int white_pass=0;
    protected String chessType;
    protected GamePlayer[] twoPlayers;
    public Memento(ChessBoard _chess_board)
    {

        int board_size=_chess_board.getChess().length;
        chess=new String[board_size][board_size];
        for (int i = 0; i < board_size; i++)
        {
            for (int j = 0; j < board_size; j++)
            {
                this.chess[i][j]=_chess_board.getChess()[i][j];
            }
        }
        this.canPlay=_chess_board.getCanPlay();
        this.black_pass=_chess_board.getBlack_pass();
        this.isBlack=_chess_board.getIsBlack();
        this.white_pass=_chess_board.getWhite_pass();
        this.regretChance=_chess_board.getRegretChance();
        this.chessType=_chess_board.getChessType();
        this.setTwoPlayers(_chess_board.getTwoplayers());

        this.setChessInfo(_chess_board.getChessInfo());



    }
    public String[][] getChess()
    {
        return chess;
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

    public int getWhite_pass(){
        return this.white_pass;
    }
    public int getBlack_pass(){
        return this.black_pass;
    }
    public void setChessInfo(ArrayList chessInfo)
    {
        this.chessInfo = (ArrayList)chessInfo.clone();;
    }

    public void setTwoPlayers(GamePlayer[] twoPlayers) {
        this.twoPlayers = twoPlayers.clone();
    }

    public GamePlayer[] getTwoPlayers() {
        return twoPlayers;
    }
}
