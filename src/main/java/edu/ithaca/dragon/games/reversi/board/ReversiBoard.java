package edu.ithaca.dragon.games.reversi.board;

import java.util.List;

import org.javatuples.Pair;

import edu.ithaca.dragon.games.GameStatus;

public interface ReversiBoard {

    public GameStatus calcGameStatus();

    public GameStatus calcWinLoseStatus();

    //@returns the char found at the given square
    public char getSquareSymbol(Pair<Integer, Integer> square);

    
    public List<Pair<Integer, Integer>> calcAllValidMoves(char symbol);


    public int calcScore(char symbol);
    
    //@throws IllegalArgumentException if square is taken or symbol is invalid
    //@post places the given symbol in the given square, if both are valid
    public void makeMove(Pair<Integer, Integer> square, char symbol);
    
    //@returns a string representing the entire board for display, including coordinates
    public String displayString();
    
    //@returns a copy of the current board
    public ReversiBoard copyBoard();

    //@returns the count of the rows on the board (or columns, since it is sqaure)
    public int getSize();
}
