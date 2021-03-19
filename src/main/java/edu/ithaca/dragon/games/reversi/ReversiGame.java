package edu.ithaca.dragon.games.reversi;

import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import edu.ithaca.dragon.games.GameStatus;
import edu.ithaca.dragon.games.reversi.board.ReversiBoard;
import edu.ithaca.dragon.games.reversi.board.TwoDArrayReversiBoard;
import edu.ithaca.dragon.games.reversi.player.ReversiPlayer;

public class ReversiGame {
    private static Random random = new Random();

    private ReversiBoard board;
    private ReversiPlayer xPlayer;
    private ReversiPlayer oPlayer;
    private char turn;

    public ReversiGame(ReversiBoard board, ReversiPlayer xPlayer, ReversiPlayer oPlayer){
        this.board = board.copyBoard();
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;
        if (random.nextInt(2) == 1){
            turn = 'X';
        }
        else {
            turn='O';
        }
    }

    public ReversiGame(int size, ReversiPlayer xPlayer, ReversiPlayer oPlayer){
        this(new TwoDArrayReversiBoard(size), xPlayer, oPlayer);
    }

    public void play(){
        int turnsInARowSkipped = 0;
        while (board.calcGameStatus() == GameStatus.PLAYING && turnsInARowSkipped < 2){
            System.out.println(board.displayString());
            System.out.println("Score: X: " + board.calcScore('X') + "\tO: " +  board.calcScore('O'));
            if (takeTurn()){
                turnsInARowSkipped = 0;
            }
            else {
                turnsInARowSkipped++;
            }
        }
        System.out.println(board.displayString());
        System.out.println("Score: X: " + board.calcScore('X') + "\tO: " +  board.calcScore('O'));
        System.out.println(board.calcWinLoseStatus());
    }

    public boolean takeTurn(){
        if (turn == 'X'){
            boolean turnTaken = makeMove(xPlayer, turn);
            turn = 'O';
            return turnTaken;
        }
        else if (turn == 'O'){
            boolean turnTaken = makeMove(oPlayer, turn);
            turn = 'X';
            return turnTaken;
        }
        else {
            throw new RuntimeException("Bad value for turn:"  + turn);
        }
    }

    private boolean makeMove(ReversiPlayer player, char symbol){
        List<Pair<Integer, Integer>> allValidMoves = board.calcAllValidMoves(symbol);
        if (allValidMoves.size() > 0){
            Pair<Integer, Integer> square = player.chooseSquare(makeBoardCopy(), symbol);
            if(allValidMoves.contains(square)){
                board.makeMove(square, symbol);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public ReversiBoard makeBoardCopy(){
        return board.copyBoard();
    }

    public GameStatus calcGameStatus(){
        return board.calcGameStatus();
    } 
}
