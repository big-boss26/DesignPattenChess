package Game;

import Memorandum.Memento;

import java.awt.*;
import java.util.ArrayList;

public class heibaiqi extends ChessBoard{
    public heibaiqi(int line_num,String _chess_type)
    {
        this.grid_num = line_num-1;
        this.line_num = line_num;
        this.chessType=_chess_type;
        this.chess = new String[this.grid_num][this.grid_num];
        for (int i = 0; i < this.grid_num; i++) {
            for (int j = 0; j < this.grid_num; j++) {
                chess[i][j] = "null";
//                select[i][j] = false;
            }
        }
        this.chess[3][3]="white";
        this.chess[3][4]="black";
        this.chess[4][3]="black";
        this.chess[4][4]="white";
    }

    @Override
    public String getDown(int x,  int y){
        String piece;
        if(isBlack){
            piece="black";
        }
        else {
            piece="white";
        }
        if(canPlay(chess,piece,x,y)){
            chess = getNewBoardAfterMove(chess,new Point(x,y),piece);
            isBlack=!isBlack;
            this.saveMemento();

            return "落子成功";
        }
        else {
            return "非法落子";
        }

    }



    public  ArrayList<Point> getAllPossibleMoves(String[][] board, String player){
        ArrayList<Point> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(canPlay(board,player,i,j)){
                    result.add(new Point(i,j));
                }
            }
        }
        return result;
    }

    public static boolean canPlay(String[][] board,String player,int i,int j){

        if(!board[i][j].equals("null")) return false;

        int mi , mj , c;
        String oplayer = ((player.equals("black")) ? "white" : "black");

        //move up
        mi = i - 1;
        mj = j;
        c = 0;
        while(mi>0 && board[mi][mj].equals(oplayer)){
            mi--;
            c++;
        }
        if(mi>=0 && board[mi][mj].equals(player) && c>0) return true;


        //move down
        mi = i + 1;
        mj = j;
        c = 0;
        while(mi<7 && board[mi][mj].equals(oplayer)){
            mi++;
            c++;
        }
        if(mi<=7 && board[mi][mj].equals(player)  && c>0) return true;

        //move left
        mi = i;
        mj = j - 1;
        c = 0;
        while(mj>0 && board[mi][mj].equals(oplayer)){
            mj--;
            c++;
        }
        if(mj>=0 && board[mi][mj].equals(player)  && c>0) return true;

        //move right
        mi = i;
        mj = j + 1;
        c = 0;
        while(mj<7 && board[mi][mj].equals(oplayer)){
            mj++;
            c++;
        }
        if(mj<=7 && board[mi][mj].equals(player)  && c>0) return true;

        //move up left
        mi = i - 1;
        mj = j - 1;
        c = 0;
        while(mi>0 && mj>0 && board[mi][mj].equals(oplayer)){
            mi--;
            mj--;
            c++;
        }
        if(mi>=0 && mj>=0 && board[mi][mj] .equals(player)  && c>0) return true;

        //move up right
        mi = i - 1;
        mj = j + 1;
        c = 0;
        while(mi>0 && mj<7 && board[mi][mj].equals(oplayer)){
            mi--;
            mj++;
            c++;
        }
        if(mi>=0 && mj<=7 && board[mi][mj].equals(player)  && c>0) return true;

        //move down left
        mi = i + 1;
        mj = j - 1;
        c = 0;
        while(mi<7 && mj>0 && board[mi][mj].equals(oplayer)){
            mi++;
            mj--;
            c++;
        }
        if(mi<=7 && mj>=0 && board[mi][mj] .equals(player)  && c>0) return true;

        //move down right
        mi = i + 1;
        mj = j + 1;
        c = 0;
        while(mi<7 && mj<7 && board[mi][mj].equals(oplayer)){
            mi++;
            mj++;
            c++;
        }
        if(mi<=7 && mj<=7 && board[mi][mj] .equals(player)  && c>0) return true;

        //when all hopes fade away
        return false;
    }
    public static ArrayList<Point> getReversePoints(String[][] board,String player,int i,int j){

        ArrayList<Point> allReversePoints = new ArrayList<>();

        int mi , mj , c;
        String oplayer = ((player.equals("black")) ? "white" : "black");

        //move up
        ArrayList<Point> mupts = new ArrayList<>();
        mi = i - 1;
        mj = j;
        while(mi>0 && board[mi][mj] == oplayer){
            mupts.add(new Point(mi,mj));
            mi--;
        }
        if(mi>=0 && board[mi][mj] == player && mupts.size()>0){
            allReversePoints.addAll(mupts);
        }


        //move down
        ArrayList<Point> mdpts = new ArrayList<>();
        mi = i + 1;
        mj = j;
        while(mi<7 && board[mi][mj] == oplayer){
            mdpts.add(new Point(mi,mj));
            mi++;
        }
        if(mi<=7 && board[mi][mj] == player && mdpts.size()>0){
            allReversePoints.addAll(mdpts);
        }

        //move left
        ArrayList<Point> mlpts = new ArrayList<>();
        mi = i;
        mj = j - 1;
        while(mj>0 && board[mi][mj] == oplayer){
            mlpts.add(new Point(mi,mj));
            mj--;
        }
        if(mj>=0 && board[mi][mj] == player && mlpts.size()>0){
            allReversePoints.addAll(mlpts);
        }

        //move right
        ArrayList<Point> mrpts = new ArrayList<>();
        mi = i;
        mj = j + 1;
        while(mj<7 && board[mi][mj] == oplayer){
            mrpts.add(new Point(mi,mj));
            mj++;
        }
        if(mj<=7 && board[mi][mj] == player && mrpts.size()>0){
            allReversePoints.addAll(mrpts);
        }

        //move up left
        ArrayList<Point> mulpts = new ArrayList<>();
        mi = i - 1;
        mj = j - 1;
        while(mi>0 && mj>0 && board[mi][mj] == oplayer){
            mulpts.add(new Point(mi,mj));
            mi--;
            mj--;
        }
        if(mi>=0 && mj>=0 && board[mi][mj] == player && mulpts.size()>0){
            allReversePoints.addAll(mulpts);
        }

        //move up right
        ArrayList<Point> murpts = new ArrayList<>();
        mi = i - 1;
        mj = j + 1;
        while(mi>0 && mj<7 && board[mi][mj] == oplayer){
            murpts.add(new Point(mi,mj));
            mi--;
            mj++;
        }
        if(mi>=0 && mj<=7 && board[mi][mj] == player && murpts.size()>0){
            allReversePoints.addAll(murpts);
        }

        //move down left
        ArrayList<Point> mdlpts = new ArrayList<>();
        mi = i + 1;
        mj = j - 1;
        while(mi<7 && mj>0 && board[mi][mj] == oplayer){
            mdlpts.add(new Point(mi,mj));
            mi++;
            mj--;
        }
        if(mi<=7 && mj>=0 && board[mi][mj] == player && mdlpts.size()>0){
            allReversePoints.addAll(mdlpts);
        }

        //move down right
        ArrayList<Point> mdrpts = new ArrayList<>();
        mi = i + 1;
        mj = j + 1;
        while(mi<7 && mj<7 && board[mi][mj] == oplayer){
            mdrpts.add(new Point(mi,mj));
            mi++;
            mj++;
        }
        if(mi<=7 && mj<=7 && board[mi][mj] == player && mdrpts.size()>0){
            allReversePoints.addAll(mdrpts);
        }

        return allReversePoints;
    }

    public static String[][] getNewBoardAfterMove(String[][] board, Point move , String player){
        //get clone of old board
        String[][] newboard = new String[8][8];
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                newboard[k][l] = board[k][l];
            }
        }

        //place piece
        newboard[move.x][move.y] = player;
        //reverse pieces
        ArrayList<Point> rev = getReversePoints(newboard,player,move.x,move.y);
        for(Point pt : rev){
            newboard[pt.x][pt.y] = player;
        }

        return newboard;
    }
    public String pass(){
        return "五子棋不能不落子!!!";
    }
    public  boolean isGameFinished(String[][] board){
        return !(hasAnyMoves(board,"white") || hasAnyMoves(board,"black"));
    }
    public  String getWinner(String[][] board){
        if(!isGameFinished(board))
            //game not finished
            return "胜负未分";
        else{
            canPlay = false;
            //count stones
            int p1s = getPlayerStoneCount(board,"black");
            int p2s = getPlayerStoneCount(board,"white");

            if(p1s == p2s){
                //tie
                return "平局，游戏结束!";
            }else if(p1s > p2s){
                //p1 wins
                return "黑方获胜，游戏结束!";
            }else{
                //p2 wins
                return "白方获胜，游戏结束!";
            }
        }
    }


    @Override
    public String checkWin(int x, int y) {
        return getWinner(chess);
    }
    @Override
    public void clearChessBoard(){
        for (int i = 0; i < this.chess.length; i++) {
            for (int j = 0; j < this.chess.length; j++) {
                chess[i][j] = "null";
            }
        }
        this.chess[3][3]="white";
        this.chess[3][4]="black";
        this.chess[4][3]="black";
        this.chess[4][4]="white";
    }
}
