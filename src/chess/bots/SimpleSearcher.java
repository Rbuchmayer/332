package chess.bots;

import java.util.List;

import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;

/**
 * This class should implement the minimax algorithm as described in the
 * assignment handouts.
 */
public class SimpleSearcher<M extends Move<M>, B extends Board<M, B>> extends
        AbstractSearcher<M, B> {

    public M getBestMove(B board, int myTime, int opTime) {
        /* Calculate the best move */
        BestMove<M> best = minimax(this.evaluator, board, ply);
        return best.move;
    }
    
    static <M extends Move<M>, B extends Board<M, B>> BestMove<M> minimax(Evaluator<B> evaluator, B board, int depth) {
    	if (depth == 0) {
    		return new BestMove<M>(evaluator.eval(board));
    	} else {
    	
	    	List<M> moves = board.generateMoves();
	    	BestMove<M> max = new BestMove<M>(-evaluator.infty());
	    	
	    	if (moves.isEmpty()) {
	    		if (board.inCheck() ) {
	    			return new BestMove<M>(-evaluator.mate() - depth);
	    		} else {
	    			return new BestMove<M>(-evaluator.stalemate());
	    		}
	    	}
	    	
	    	for (M move: moves) {
	    		board.applyMove(move);
	    		BestMove<M> bMove = minimax(evaluator, board, depth - 1).negate();
	    		
	    		if (bMove.value > max.value) {
	    			max.value = bMove.value;
	    			max.move = move;
	    		}
	    		
	    		board.undoMove();
	    	}
	    	return max;
    	}
    }
}
    