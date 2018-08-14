package writeUpTests;

import java.util.List;

import chess.board.ArrayBoard;
import chess.game.SimpleEvaluator;

public class TestChess {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		TestGame chess = new TestGame();
//		chess.play();
//		List<String> list = chess.list;
//		System.out.println(list.toString());
		SimpleSearcher s = new SimpleSearcher();
		ParallelSearcher l = new ParallelSearcher();
		AlphaBetaSearcher a = new AlphaBetaSearcher();
		
//		for (int k = 0; k < 3; k++) {
//		
//			for (int j = 1; j <= 5; j++) {
//				System.out.println("Ply: " + j);
//				for (int i = 0; i < list.size(); i++) {
//					//TestStartingPosition.getBestMove(list.get(i), searchers[k], j, j/2);  	
//					s.setDepth(j);
//					s.setCutoff(j/2);
//					s.setEvaluator(new SimpleEvaluator());
//					
//					l.setDepth(j);
//					l.setCutoff(j/2);
//					l.setEvaluator(new SimpleEvaluator());
//					
//					a.setDepth(j);
//					a.setCutoff(j/2);
//					a.setEvaluator(new SimpleEvaluator());
//					
//					a.nodes = 0;
//					s.nodes = 0;
//					l.nodes = 0;
//					switch (k) {
//					case 0: 
//						s.getBestMove(ArrayBoard.FACTORY.create().init(list.get(i)), 0, 0);
//						System.out.println(s.nodes);
//						break;
//					case 1:
//						l.getBestMove(ArrayBoard.FACTORY.create().init(list.get(i)), 0, 0);
//						System.out.println(l.nodes);
//						break;
//					case 2:
//						a.getBestMove(ArrayBoard.FACTORY.create().init(list.get(i)), 0, 0);
//						System.out.println(a.nodes);
//						break;
//					}
//				}
//				System.out.println();
//			}
//			System.out.println();System.out.println();System.out.println();
//		}
//		
//		String[] threeMoves = {	"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -",
//								"r3k2r/pp4bp/2n1p1p1/q1pp1p2/5B2/2NP1Q2/PPP2PPP/R4RK1 b Hkq -",
//								"2k3r1/p6p/2n5/3pp3/1pp1P3/2qP4/P1P1K2P/R1R5 b Hh -"};
//		
//		
//		l.setDepth(5);
//		l.setEvaluator(new SimpleEvaluator());
//		
//		for (int state = 0; state < 3; state++) {
//			System.out.println("state: " + state);
//			for (int cutoff = 0; cutoff < 10; cutoff++) {
//				System.out.println("cutoff: " + cutoff);
//				for (int i = 0; i < 4; i++) {
//					l.setCutoff(cutoff);
//					long startTime = System.nanoTime();
//					
//					l.getBestMove(ArrayBoard.FACTORY.create().init(threeMoves[state]), 0, 0);
//					
//					long endTime = System.nanoTime();
//					long duration = (endTime - startTime);
//					System.out.println(duration);
//				}
//			}
//		}
		
		String[] list = {	"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -",
				"r3k2r/pp4bp/2n1p1p1/q1pp1p2/5B2/2NP1Q2/PPP2PPP/R4RK1 b Hkq -",
				"2k3r1/p6p/2n5/3pp3/1pp1P3/2qP4/P1P1K2P/R1R5 b Hh -"};
		a.setDepth(5);
		s.setDepth(5);
		l.setDepth(5);
		
		a.setCutoff(3);
		s.setCutoff(3);
		l.setCutoff(3);
		
		a.setEvaluator(new SimpleEvaluator());
		s.setEvaluator(new SimpleEvaluator());
		l.setEvaluator(new SimpleEvaluator());
		
		AbstractSearcher[] searchers = {s, l, a};
		for (int eval = 0; eval < 3; eval++) {
			for (int k = 0; k < 3; k++) {
				for (int trials = 0; trials < 5; trials++) {
					long startTime = System.nanoTime();
					searchers[eval].getBestMove(ArrayBoard.FACTORY.create().init(list[k]), 0, 0);
					long endTime = System.nanoTime();
					long duration = (endTime - startTime);
					System.out.println(duration);
				}
			}
			System.out.println();
		}
	}
		
}

