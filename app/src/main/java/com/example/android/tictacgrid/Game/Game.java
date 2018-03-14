package com.example.android.tictacgrid.Game;

import com.example.android.tictacgrid.Players.Player;

/**
 * Created by pawel on 11.03.18.
 */

public interface Game {

    void setUpGame();
    void changePlayer();
    void finishGame(boolean isWinner);
    Player getCurrentPlayer();
}
