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
    	
    	@SuppressWarnings("unchecked")
		@Override
    	protected BestMove<M> compute() {
    		
    		if (depth <= cutoff || moves.isEmpty()) {
    			return SimpleSearcher.minimax(evaluator, board, depth);
    		} else if (hi - lo <= divideCutoff) {
    			BestMove<M> max = new BestMove<M>(-evaluator.infty());
				SearchTask<M, B>[] tasks = new SearchTask[hi - lo];
    			
    			for (int i = lo; i < hi; i++) {
    				board.applyMove(moves.get(i));
    				List<M> newMoves = board.generateMoves();
    				
    				tasks[i - lo] = new SearchTask<M, B>(evaluator, board.copy(), depth - 1, 0, newMoves.size(), newMoves);
    				tasks[i - lo].fork();
    						
    				board.undoMove();
    				
    			}
    			
    			for (int i = lo; i < hi; i++) {
    				
    				BestMove<M> bMove = tasks[i - lo].join().negate();
    	    		if (bMove.value > max.value) {
    	    			max = bMove;
    	    			max.move = moves.get(i);
    	    		}
    				
    			}
    			return max;
    			
    			
    		}
    		int mid = lo + (hi - lo) / 2;
    		SearchTask<M, B> left = new SearchTask<M, B>(evaluator, board.copy(), depth, lo, mid, moves);
    		SearchTask<M, B> right = new SearchTask<M, B>(evaluator, board.copy(), depth, mid, hi, moves);
    		
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