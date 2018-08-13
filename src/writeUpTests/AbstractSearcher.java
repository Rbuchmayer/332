package writeUpTests;

import java.util.Observable;
import java.util.Observer;

import chess.game.SimpleTimer;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;
import cse332.chess.interfaces.Searcher;
import cse332.chess.interfaces.Timer;

public abstract class AbstractSearcher<M extends Move<M>, B extends Board<M, B>>
        implements Searcher<M, B> {
    protected Evaluator<B> evaluator;
    protected Timer timer;
    protected int ply;
    protected int cutoff;
    private BestMovePublisher<M> bestMovePublisher = new BestMovePublisher<M>();

    public AbstractSearcher() {
        setTimer(new SimpleTimer(180000, 2000));
    }

    public void setEvaluator(Evaluator<B> e) {
        evaluator = e;
    }

    public void setDepth(int depth) {
        this.ply = depth;
    }

    public void setCutoff(int cutoff) {
        this.cutoff = cutoff;
    }

    public void setTimer(Timer t) {
        this.timer = t;
    }

    public void addBestMoveObserver(Observer o) {
        bestMovePublisher.addObserver(o);
    }

    protected void reportNewBestMove(M move) {
        bestMovePublisher.updateBestMove(move);
    }

    private static class BestMovePublisher<M extends Move<M>> extends
            Observable {
        public void updateBestMove(M move) {
            setChanged();
            notifyObservers(move);
        }
    }
}
