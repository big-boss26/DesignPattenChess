package Game;

import java.awt.*;

public class Minimax {

    static int nodesExplored = 0;

    public  static  Point solve(String[][] board_piece,ChessBoard board, String player, int depth, Evaluator e){
        nodesExplored = 0;
        int bestScore = Integer.MIN_VALUE;
        Point bestMove = null;
        for(Point move : board.getAllPossibleMoves(board_piece,player)){
            //create new node
            String[][] newNode = heibaiqi.getNewBoardAfterMove(board_piece,move,player);
            //recursive call
            int childScore = MMAB(newNode,board,player,depth-1,false,Integer.MIN_VALUE,Integer.MAX_VALUE,e);
            if(childScore > bestScore) {
                bestScore = childScore;
                bestMove = move;
            }
        }
        System.out.println("已搜索节点数目 : " + nodesExplored);
        return bestMove;
    }

    private static  int MMAB(String[][] node,ChessBoard board,String player,int depth,boolean max,int alpha,int beta,Evaluator e){
        nodesExplored++;
        if(depth == 0 || board.isGameFinished(node)){
            return e.eval(node,board,player);
        }
        String oplayer = ((player.equals("black")) ? "white" : "black");
        if((max && !board.hasAnyMoves(node,player)) || (!max && !board.hasAnyMoves(node,oplayer))){
            return MMAB(node,board,player,depth-1,!max,alpha,beta,e);
        }
        int score;
        if(max){
            score = Integer.MIN_VALUE;
            for(Point move : board.getAllPossibleMoves(node,player)){ //my turn
                String[][] newNode = heibaiqi.getNewBoardAfterMove(node,move,player);
                int childScore = MMAB(newNode,board,player,depth-1,false,alpha,beta,e);
                if(childScore > score) score = childScore;
                if(score > alpha) alpha = score;
                if(beta <= alpha) break;
            }
        }else{
            score = Integer.MAX_VALUE;
            for(Point move : board.getAllPossibleMoves(node,oplayer)){ //opponent turn
                String[][] newNode = heibaiqi.getNewBoardAfterMove(node,move,oplayer);
                int childScore = MMAB(newNode,board,player,depth-1,true,alpha,beta,e);
                if(childScore < score) score = childScore;
                if(score < beta) beta = score;
                if(beta <= alpha) break; //Alpha Cutoff
            }
        }
        return score;
    }

}
