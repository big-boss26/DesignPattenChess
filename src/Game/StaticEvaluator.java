package Game;

public class StaticEvaluator implements Evaluator {

    public int eval(String [][]board_piece,ChessBoard board , String player){
        int mob = evalMobility(board_piece,board,player);
        int sc = evalDiscDiff(board_piece,board,player);
        return 2*mob + sc + 1000*evalCorner(board_piece,player);
    }

    public  static  int evalDiscDiff(String [][]board_piece,ChessBoard board , String player){
        String oplayer = ((player.equals("black")) ? "white" : "black");

        int mySC = board.getPlayerStoneCount(board_piece,player);
        int opSC = board.getPlayerStoneCount(board_piece,oplayer);

        return 100 * (mySC - opSC) / (mySC + opSC);
    }

    public static int evalMobility(String [][]board_piece,ChessBoard board , String player){
        String oplayer = ((player.equals("black")) ? "white" : "black");

        int myMoveCount = board.getAllPossibleMoves(board_piece,player).size();
        int opMoveCount = board.getAllPossibleMoves(board_piece,oplayer).size();

        return 100 * (myMoveCount - opMoveCount) / (myMoveCount + opMoveCount + 1);
    }

    public static int evalCorner(String[][] board , String player){
        String oplayer = ((player.equals("black")) ? "white" : "black");

        int myCorners = 0;
        int opCorners = 0;

        if(board[0][0]==player) myCorners++;
        if(board[7][0]==player) myCorners++;
        if(board[0][7]==player) myCorners++;
        if(board[7][7]==player) myCorners++;

        if(board[0][0]==oplayer) opCorners++;
        if(board[7][0]==oplayer) opCorners++;
        if(board[0][7]==oplayer) opCorners++;
        if(board[7][7]==oplayer) opCorners++;

        return 100 * (myCorners - opCorners) / (myCorners + opCorners + 1);
    }


}
