package Game;

import java.awt.*;
import java.util.ArrayList;

public class Minimax {

    static int nodesExplored = 0;

    //returns max score move
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
        System.out.println("Nodes Explored : " + nodesExplored);
        return bestMove;
    }

    //returns minimax value for a given node with A/B pruning
    private static  int MMAB(String[][] node,ChessBoard board,String player,int depth,boolean max,int alpha,int beta,Evaluator e){
        nodesExplored++;
        //if terminal reached or depth limit reached evaluate
        if(depth == 0 || board.isGameFinished(node)){
            //BoardPrinter bpe = new BoardPrinter(node,"Depth : " + depth);
            return e.eval(node,board,player);
        }
        String oplayer = ((player.equals("black")) ? "white" : "black");
        //if no moves available then forfeit turn
        if((max && !board.hasAnyMoves(node,player)) || (!max && !board.hasAnyMoves(node,oplayer))){
            //System.out.println("Forfeit State Reached !");
            return MMAB(node,board,player,depth-1,!max,alpha,beta,e);
        }
        int score;
        if(max){
            //maximizing
            score = Integer.MIN_VALUE;
            for(Point move : board.getAllPossibleMoves(node,player)){ //my turn
                //create new node
                String[][] newNode = heibaiqi.getNewBoardAfterMove(node,move,player);
                //recursive call
                int childScore = MMAB(newNode,board,player,depth-1,false,alpha,beta,e);
                if(childScore > score) score = childScore;
                //update alpha
                if(score > alpha) alpha = score;
                if(beta <= alpha) break; //Beta Cutoff
            }
        }else{
            //minimizing
            score = Integer.MAX_VALUE;
            for(Point move : board.getAllPossibleMoves(node,oplayer)){ //opponent turn
                //create new node
                String[][] newNode = heibaiqi.getNewBoardAfterMove(node,move,oplayer);
                //recursive call
                int childScore = MMAB(newNode,board,player,depth-1,true,alpha,beta,e);
                if(childScore < score) score = childScore;
                //update beta
                if(score < beta) beta = score;
                if(beta <= alpha) break; //Alpha Cutoff
            }
        }
        return score;
    }

}
