package writeUpTests;

import java.util.List;

import chess.board.ArrayBoard;
import chess.game.SimpleEvaluator;

public class TestChess {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		TestGame chess = new TestGame();
		chess.play();
		List<String> list = chess.list;
		System.out.println(list.toString());
		SimpleSearcher s = new SimpleSearcher();
		ParallelSearcher l = new ParallelSearcher();
		AlphaBetaSearcher a = new AlphaBetaSearcher();
		
		for (int k = 0; k < 3; k++) {
		
			for (int j = 1; j <= 5; j++) {
				System.out.println("Ply: " + j);
				for (int i = 0; i < 3/*list.size()*/; i++) {
					//TestStartingPosition.getBestMove(list.get(i), searchers[k], j, j/2);  	
					s.setDepth(j);
					s.setCutoff(j/2);
					s.setEvaluator(new SimpleEvaluator());
					
					l.setDepth(j);
					l.setCutoff(j/2);
					l.setEvaluator(new SimpleEvaluator());
					
					a.setDepth(j);
					a.setCutoff(j/2);
					a.setEvaluator(new SimpleEvaluator());
					
					a.nodes = 0;
					s.nodes = 0;
					l.nodes = 0;
					switch (k) {
					case 0: 
						s.getBestMove(ArrayBoard.FACTORY.create().init(list.get(i)), 0, 0);
						System.out.println(s.nodes);
						break;
					case 1:
						l.getBestMove(ArrayBoard.FACTORY.create().init(list.get(i)), 0, 0);
						System.out.println(l.nodes);
						break;
					case 2:
						a.getBestMove(ArrayBoard.FACTORY.create().init(list.get(i)), 0, 0);
						System.out.println(a.nodes);
						break;
					}
				}
				System.out.println();
			}
			System.out.println();System.out.println();System.out.println();
		}
	}
}
