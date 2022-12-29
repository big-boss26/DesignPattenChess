package Game;

import java.awt.*;
import Game.Evaluator;


public class AIPlayer extends GamePlayer {

    private int searchDepth;
    private Evaluator evaluator;

    public AIPlayer(String mark, int depth) {
        super(mark);
        searchDepth = depth;
        evaluator = new StaticEvaluator();
    }

    @Override
    public Point play(ChessBoard board) {
        return Minimax.solve(board.getChess(),board,pieseType,searchDepth,evaluator);
    }
}
