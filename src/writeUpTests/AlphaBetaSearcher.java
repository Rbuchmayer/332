package writeUpTests;

import java.util.List;

import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;


public class AlphaBetaSearcher<M extends Move<M>, B extends Board<M, B>> extends AbstractSearcher<M, B> {
	public static int nodes = 0;
    public M getBestMove(B board, int myTime, int opTime) {
        /* Calculate the best move */
        BestMove<M> best = alphaBeta(this.evaluator, board, new BestMove<M>(-evaluator.infty()), evaluator.infty(), this.ply);
        return best.move;
    }
    
    static <M extends Move<M>, B extends Board<M, B>> BestMove<M> alphaBeta(Evaluator<B> evaluator, B board, BestMove<M> move, int beta, int depth) {
    	if (depth <= 0) {
    		return new BestMove<M>(move.move, evaluator.eval(board));
    	}
	    	List<M> moves = board.generateMoves();
	    	
	    	
	    	if (moves.isEmpty()) {
	    		if (board.inCheck() ) {
	    			return new BestMove<M>(move.move, -evaluator.mate() - depth);
	    		} else {
	    			return new BestMove<M>(move.move, -evaluator.stalemate());
	    		}
	    	}
	    	
    	for(M m : moves) {
    		board.applyMove(m);
    		nodes++;
    		int value = -alphaBeta(evaluator, board, new BestMove<M>(m, -beta), -move.value, depth - 1).value;
    		board.undoMove();
    	
    		if(value > move.value) {
    			 	move.value = value;
    			 	move.move = m;
    		}
    		if(move.value >= beta) {
    			return move;
    		}
    		
    	}
    	return move;
    }
    
}
