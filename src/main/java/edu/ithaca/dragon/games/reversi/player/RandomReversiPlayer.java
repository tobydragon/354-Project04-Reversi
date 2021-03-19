package edu.ithaca.dragon.games.reversi.player;

import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import edu.ithaca.dragon.games.reversi.board.ReversiBoard;

public class RandomReversiPlayer implements ReversiPlayer{
    public static Random random = new Random();

    @Override
    public Pair<Integer, Integer> chooseSquare(ReversiBoard curBoard, char yourSymbol) {
        List<Pair<Integer, Integer>> moves = curBoard.calcAllValidMoves(yourSymbol);
        if (moves.size() > 0){
            return moves.get(random.nextInt(moves.size()));
        }
        else {
            throw new IllegalArgumentException("No moves to make");
        }
    }
    
}
