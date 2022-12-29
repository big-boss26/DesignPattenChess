package Game;



import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends GamePlayer {

    Random rnd = new Random();

    public RandomPlayer(String _pieseType) {super( _pieseType);

    }
    public boolean isUserPlayer() {
        return false;
    }

    public String playerName() {
        return "Random Player";
    }

    public Point play(ChessBoard board) {
        ArrayList<Point> myPossibleMoves = board.getAllPossibleMoves(board.getChess(),getPieseType());
        if(myPossibleMoves.size() > 0){
            return myPossibleMoves.get(rnd.nextInt(myPossibleMoves.size()));
        }else{
            return null;
        }

    }

}
