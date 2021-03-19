package edu.ithaca.dragon.games.reversi.board;

import org.junit.jupiter.api.Test;

public class TwoDArrayReversiBoardTest {
    @Test
    public void createAndBuildStringTest(){
        System.out.println(TwoDArrayReversiBoard.buildBoardString(TwoDArrayReversiBoard.createBoard(4)));
        System.out.println(TwoDArrayReversiBoard.buildBoardString(TwoDArrayReversiBoard.createBoard(8)));
    }
}
