package chess.bots;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;
import cse332.exceptions.NotYetImplementedException;


public class ParallelSearcher<M extends Move<M>, B extends Board<M, B>> extends
        AbstractSearcher<M, B> {
	
	private static final ForkJoinPool POOL = new ForkJoinPool();
	protected static int divideCutoff = 5;
	
    public M getBestMove(B board, int myTime, int opTime) {
        return parallel(this.evaluator, board, ply).move;
    }
    
    public <M extends Move<M>, B extends Board<M, B>> BestMove<M> parallel(Evaluator<B> evaluator, B board, int depth) {
    	List<M> moves = board.generateMoves();
    	return POOL.invoke(new SearchTask(evaluator, board, depth, 0, moves.size(), moves));
    }
    
    private class SearchTask<M extends Move<M>, B extends Board<M, B>> extends RecursiveTask<BestMove<M>> {
    	
    	Evaluator<B> evaluator;
    	B board;
    	int depth, lo, hi;
    	List<M> moves;
    	// goes through list from lo(inclusive) to hi(exclusive)
    	public SearchTask(Evaluator<B> evaluator, B board, int depth, int lo, int hi, List<M> moves) {
    		this.evaluator = evaluator;
    		this.board = board;
    		this.depth = depth;
    		this.lo = lo;
    		this.hi = hi;
    		this.moves = moves;
    	}
    	
    	@Override
    	protected BestMove<M> compute() {
    		
	    	if (moves.isEmpty()) {
	    		if (board.inCheck() ) {
	    			return new BestMove<M>(-evaluator.mate() - depth);
	    		} else {
	    			return new BestMove<M>(-evaluator.stalemate());
	    		}
	    	}
    		
    		if (depth <= cutoff) {
    			return SimpleSearcher.minimax(evaluator, board, depth);
    		}
    		if (hi - lo <= divideCutoff) {
    			BestMove<M> max = new BestMove<M>(-evaluator.infty());
    			
    			for (int i = lo; i < hi; i++) {
    				board.applyMove(moves.get(i));
    				
    				BestMove<M> bMove = new SearchTask(evaluator, board, depth, lo, mid, moves);
    						
    				board.undoMove();
    				
    			}
    			
    			
    			
    		}
    		int mid = lo + (hi - lo) / 2;
    		SearchTask left = new SearchTask(evaluator, board, depth, lo, mid, moves);
    		SearchTask right = new SearchTask(evaluator, board, depth, mid, hi, moves);
    		
    		left.fork();
    		
    		BestMove<M> rightResult = right.compute();
    		BestMove<M> leftResult = (BestMove<M>) left.join();
    		
    		if (leftResult.value > rightResult.value) {
    			return leftResult;
    		} else {
    			return rightResult;
    		}
    	}
    	
    }
}