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

    public static int evalBoardMap(String[][] board , String player){
        String oplayer = ((player.equals("black")) ? "white" : "black");
        int[][] W = {
                {200 , -100, 100,  50,  50, 100, -100,  200},
                {-100, -200, -50, -50, -50, -50, -200, -100},
                {100 ,  -50, 100,   0,   0, 100,  -50,  100},
                {50  ,  -50,   0,   0,   0,   0,  -50,   50},
                {50  ,  -50,   0,   0,   0,   0,  -50,   50},
                {100 ,  -50, 100,   0,   0, 100,  -50,  100},
                {-100, -200, -50, -50, -50, -50, -200, -100},
                {200 , -100, 100,  50,  50, 100, -100,  200}};

        //if corners are taken W for that 1/4 loses effect
        if(board[0][0] .equals("null")){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j <= 3; j++) {
                    W[i][j] = 0;
                }
            }
        }

        if(board[0][7].equals("null")){
            for (int i = 0; i < 3; i++) {
                for (int j = 4; j <= 7; j++) {
                    W[i][j] = 0;
                }
            }
        }

        if(board[7][0].equals("null")){
            for (int i = 5; i < 8; i++) {
                for (int j = 0; j <= 3; j++) {
                    W[i][j] = 0;
                }
            }
        }

        if(board[7][7].equals("null")){
            for (int i = 5; i < 8; i++) {
                for (int j = 4; j <= 7; j++) {
                    W[i][j] = 0;
                }
            }
        }


        int myW = 0;
        int opW = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j]==player) myW += W[i][j];
                if(board[i][j]==oplayer) opW += W[i][j];
            }
        }

        return (myW - opW) / (myW + opW + 1);
    }

    public static int evalParity(ChessBoard   board){
        int remDiscs = 64 - board.getTotalStoneCount(board.getChess());
        return remDiscs % 2 == 0 ? -1 : 1;
    }


}
