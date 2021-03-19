package edu.ithaca.dragon.games.reversi.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;

import edu.ithaca.dragon.games.GameStatus;

public class TwoDArrayReversiBoard implements ReversiBoard {
    private static final List<Pair<Integer, Integer>> DIRECTIONS = Arrays.asList(new Pair<>(0,1), new Pair<>(1,1), new Pair<>(1,0), new Pair<>(1,-1), new Pair<>(0,-1), new Pair<>(-1,-1), new Pair<>(-1,0), new Pair<>(-1,1) );

    private char[][] board;

    public TwoDArrayReversiBoard(int size){
        board = createBoard(size);
    }

    private TwoDArrayReversiBoard(char[][] board){
        this.board = board;
    }

    @Override
    public int getSize(){
        return board.length;
    }

    @Override
    public ReversiBoard copyBoard(){
        int size = getSize();
        char[][] squares = new char[size][size];
        for (int y=0;y<size; y++){
            for (int x=0;x<size; x++){
                squares[x][y] = board[x][y];
            }
        }
        return new TwoDArrayReversiBoard(squares);
    }

    @Override
    public char getSquareSymbol(Pair<Integer, Integer> square){
        return board[square.getValue0()][square.getValue1()];
    }

    @Override
    public void makeMove(Pair<Integer, Integer> square, char symbol){
        List<Pair<Integer, Integer>> squaresToFlip = calcPiecesFlippedByMove(square, symbol);
        if (squaresToFlip.size() > 0){
            board[square.getValue0()][square.getValue1()] = symbol;
            for (Pair<Integer, Integer> squareToFlip : squaresToFlip){
                board[squareToFlip.getValue0()][squareToFlip.getValue1()] = symbol;
            }
        }
        else {
            throw new IllegalArgumentException("Can't move where nothing flips");
        }
    }

    @Override
    public GameStatus calcGameStatus(){
        if (calcAllValidMoves('X').size() > 0 || calcAllValidMoves('O').size() > 0){
            return GameStatus.PLAYING;
        }
        else {
            return calcWinLoseStatus();
        }
    }

    public GameStatus calcWinLoseStatus(){
        int xScore = calcScore('X');
        int oScore = calcScore('O');
        if (xScore == oScore){
            return GameStatus.TIE;
        }
        else if (xScore > oScore){
            return GameStatus.XWIN;
        }
        else {
            return GameStatus.OWIN;
        }
    }

    @Override
    public int calcScore(char symbol){
        int score = 0;
        for (int y=0;y<board.length; y++){
            for (int x=0;x<board.length; x++){
                if( board[x][y] == symbol){
                    score++;
                }
            }
        }
        return score;
    }

    @Override
    public String displayString(){
        return buildBoardString(board);
    }

    public boolean isSquareOnBoard(Pair<Integer, Integer> squareToCheck){
        return squareToCheck.getValue0() >= 0 
            && squareToCheck.getValue0() <= board.length-1 
            && squareToCheck.getValue1() >= 0
            && squareToCheck.getValue1() <= board.length-1;
    }

    public List<Pair<Integer, Integer>> calcAllValidMoves(char symbol){
        List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
        for (int y=0;y<board.length; y++){
            for (int x=0;x<board.length; x++){
                Pair<Integer, Integer> squareToCheck = new Pair<>(x,y);
                if (calcPiecesFlippedByMove(squareToCheck, symbol).size() > 0){
                    validMoves.add(squareToCheck);
                }
            }
        }
        return validMoves;
    }

    private List<Pair<Integer, Integer>> calcPiecesFlippedByMove(Pair<Integer, Integer> square, char symbol){
        if (board[square.getValue0()][square.getValue1()] != ' ' || !isSquareOnBoard(square)){
            return new ArrayList<>();
        }
        else {
            List<Pair<Integer, Integer>> piecesFlipped = new ArrayList<>();
            for (Pair<Integer, Integer> direction : DIRECTIONS){
               piecesFlipped.addAll(calcPiecesFlippedInDirection(square, symbol, direction));
            }
            return piecesFlipped;
        }
    }

    private List<Pair<Integer, Integer>> calcPiecesFlippedInDirection(Pair<Integer, Integer> moveSquare, char symbol, Pair<Integer, Integer> change){
        char otherSymbol = getOpposingSymbol(symbol);
        Pair<Integer, Integer> curSquare = addChangeToSquare(moveSquare, change);
        List<Pair<Integer, Integer>> tilesToFlip = new ArrayList<>();

        while (isSquareOnBoard(curSquare) && getSquareSymbol(curSquare) == otherSymbol){
            tilesToFlip.add(curSquare);
            curSquare = addChangeToSquare(curSquare, change);
        }
        if (!isSquareOnBoard(curSquare)){
            return new ArrayList<>();
        }
        else if  (getSquareSymbol(curSquare) == ' '){
            return new ArrayList<>();
        }
        else if  (getSquareSymbol(curSquare) == symbol){
            return tilesToFlip;
        }
        else {
            throw new RuntimeException("Shouldn't reach here");
        }
    }
    
    public static Pair<Integer, Integer> addChangeToSquare(Pair<Integer, Integer> square, Pair<Integer, Integer> change){
        return new Pair<>(square.getValue0()+change.getValue0(), square.getValue1()+change.getValue1());
    }

    public static String buildBoardString(char[][] board){
        int size = board.length;
        String column_label = " ";
        for (int i=0; i<size; i++){
            column_label += "   " + i;
        }
        String divider = "  ";
        for (int i=0; i<size; i++){
            divider += "+---";
        } 

        String boardString = column_label + "\n" + divider + "\n";
        for (int y=0;y<size; y++){
            boardString += (y) + " "; 
            for (int x=0;x<size; x++){
                boardString += "| " + board[x][y]+" ";
            }
            boardString += "|\n"+divider+"\n";
        }
        return boardString;
    }

    public static char[][] createBoard(int size){
        char[][] squares = new char[size][size];
        for (int y=0;y<size; y++){
            for (int x=0;x<size; x++){
                squares[x][y] = ' ';
            }
        }
        int mid = size/2;
        squares[mid-1][mid-1] = 'X';
        squares[mid-1][mid] = 'O';
        squares[mid][mid-1] = 'O';
        squares[mid][mid] = 'X';
        return squares;
    }

    public static char getOpposingSymbol(char aSymbol){
        if(aSymbol == 'X'){
            return 'O';
        }
        else if (aSymbol == 'O'){
            return 'X';
        }
        else {
            throw new IllegalArgumentException("Bad symbol given: " + aSymbol);
        }
    }
}
