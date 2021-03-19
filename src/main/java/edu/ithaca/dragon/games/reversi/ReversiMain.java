package edu.ithaca.dragon.games.reversi;

import edu.ithaca.dragon.games.reversi.player.HumanReversiPlayer;
import edu.ithaca.dragon.games.reversi.player.RandomReversiPlayer;

public class ReversiMain {

    public static void main(String[] args){
        new ReversiGame(8,  new HumanReversiPlayer(), new RandomReversiPlayer()).play();
    }
    
}
