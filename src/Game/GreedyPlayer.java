
package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GreedyPlayer extends GamePlayer {

    public GreedyPlayer(String mark) {
        super(mark);
    }



    @Override
    public Point play(ChessBoard board) {

        ArrayList<Point> myPossibleMoves = board.getAllPossibleMoves(board.getChess(),pieseType);

        Point bestMove = null;
        int bestValue = 0;

        for(Point move : myPossibleMoves) {
            int val = heibaiqi.getReversePoints(board.getChess(),pieseType, move.x,move.y).size();
            if(val > bestValue){
                bestValue = val;
                bestMove = move;
            }
        }

        return bestMove;

    }

}