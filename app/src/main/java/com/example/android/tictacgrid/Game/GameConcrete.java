package com.example.android.tictacgrid.Game;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tictacgrid.Players.AI.BotPlayer;
import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.R;

import java.util.ArrayList;


/**
 * Created by pawel on 11.03.18.
 */

public class GameConcrete implements Game {

    private int mHowManyFiguresInLineToWin;
    private int mGamesPlayedCounter = 0;
    private Player mCurrentPlayer;
    private int mIndexOfPlayerStartingTheGame = 0;
    private ArrayList<Player> mListOfPlayers;
    private ArrayList<String> mListOfPlayersNames;
    private Context mContext;
    private Toast mToast;

    private ArrayList<TextView> mListOfPlayersNamesTVs;
    private ArrayList<TextView> mListOfPlayersScoresTVs;
    private TextView mGamesPlayedTV;
    private Button mNextGameButton;

    private Grid mGrid;

    public GameConcrete (
            Context context,
            ArrayList<TextView> namesTVs,
            ArrayList<TextView> scoresTVs,
            TextView gamesPlayedTV,
            Button nextGameButton,
            ArrayList<String> listOfNames,
            int howManyInLineToWin) {

        this.mContext = context;
        this.mListOfPlayersNamesTVs = namesTVs;
        this.mListOfPlayersScoresTVs = scoresTVs;
        this.mGamesPlayedTV = gamesPlayedTV;
        this.mNextGameButton = nextGameButton;
        this.mListOfPlayersNames = listOfNames;
        this.mHowManyFiguresInLineToWin = howManyInLineToWin;

        setUpGame();
    }

    @Override
    public void setUpGame() {

        mListOfPlayers = new ArrayList<>();

        switch (mListOfPlayersNames.size()) {
            case 1:
                startGameWithBot(mListOfPlayersNames.get(0));

                mListOfPlayersNamesTVs.remove(3);
                mListOfPlayersNamesTVs.remove(2);

                mListOfPlayersScoresTVs.remove(3);
                mListOfPlayersScoresTVs.remove(2);

                break;
            case 2:
                addTwoPlayers(mListOfPlayersNames);

                mListOfPlayersNamesTVs.remove(3);
                mListOfPlayersNamesTVs.remove(2);

                mListOfPlayersScoresTVs.remove(3);
                mListOfPlayersScoresTVs.remove(2);

                break;
            case 3:
                addThreePlayers(mListOfPlayersNames);

                mListOfPlayersNamesTVs.remove(3);
                mListOfPlayersScoresTVs.remove(3);

                break;
            case 4:
                addFourPlayers(mListOfPlayersNames);
        }

        for (Player player : mListOfPlayers) {
            player.setHowManyInLineToWin(mHowManyFiguresInLineToWin);
        }

        if (mCurrentPlayer == null) {
            mCurrentPlayer = mListOfPlayers.get(0);
            mListOfPlayersNamesTVs.get(0)
                    .setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        mNextGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void setGrid(Grid grid) {
        this.mGrid = grid;
    }

    private void startGameWithBot(String botName) {
        mListOfPlayers.add(new Player(mContext, "You", 1));
        mListOfPlayers.add(new BotPlayer(mContext, botName, 2));


        mListOfPlayersNamesTVs.get(0).setText(mListOfPlayers.get(0).getPlayerName());
        mListOfPlayersScoresTVs.get(0).setText(Integer.toString(mListOfPlayers.get(0).getScore()));

        mListOfPlayersNamesTVs.get(1).setText(mListOfPlayers.get(1).getPlayerName());
        mListOfPlayersScoresTVs.get(1).setText(Integer.toString(mListOfPlayers.get(1).getScore()));
    }

    // Adds new player objects to list of players
    // Each player object is initialized with name received in extra
    // text views with names and scores are bonded with players and their values are set
    private void addTwoPlayers(ArrayList<String> names) {

        mListOfPlayers.add(new Player(mContext, names.get(0), 1));
        mListOfPlayers.add(new Player(mContext, names.get(1), 2));

        mListOfPlayersNamesTVs.get(0).setText(mListOfPlayers.get(0).getPlayerName());
        mListOfPlayersScoresTVs.get(0).setText(Integer.toString(mListOfPlayers.get(0).getScore()));

        mListOfPlayersNamesTVs.get(1).setText(mListOfPlayers.get(1).getPlayerName());
        mListOfPlayersScoresTVs.get(1).setText(Integer.toString(mListOfPlayers.get(1).getScore()));
    }

    // Calls addTwoPlayers() method and then adds third player
    private void addThreePlayers(ArrayList<String> names) {

        addTwoPlayers(names);

        mListOfPlayers.add(new Player(mContext, names.get(2), 3));

        mListOfPlayersNamesTVs.get(2).setText(mListOfPlayers.get(2).getPlayerName());
        mListOfPlayersScoresTVs.get(2).setText(Integer.toString(mListOfPlayers.get(2).getScore()));
    }

    // Calls addThreePlayers() method and then adds fourth player
    private void addFourPlayers(ArrayList<String> names) {

        addThreePlayers(names);

        mListOfPlayers.add(new Player(mContext, names.get(3), 4));

        mListOfPlayersNamesTVs.get(3).setText(mListOfPlayers.get(3).getPlayerName());
        mListOfPlayersScoresTVs.get(3).setText(Integer.toString(mListOfPlayers.get(3).getScore()));

        mListOfPlayersNamesTVs.get(3).setVisibility(View.VISIBLE);
        mListOfPlayersScoresTVs.get(3).setVisibility(View.VISIBLE);
    }

    @Override
    public void changePlayer() {

        int currentPlayerIndex = mListOfPlayers.indexOf(getCurrentPlayer());

        mListOfPlayersNamesTVs.get(currentPlayerIndex)
                .setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

        if (currentPlayerIndex == (mListOfPlayers.size() - 1)) {
            mCurrentPlayer = mListOfPlayers.get(0);
            mListOfPlayersNamesTVs.get(0)
                    .setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            mCurrentPlayer = mListOfPlayers.get(currentPlayerIndex + 1);
            mListOfPlayersNamesTVs.get(currentPlayerIndex + 1)
                    .setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        if (mCurrentPlayer instanceof BotPlayer) {
            triggerBot();
        }
    }

    public void resetGame() {
        mNextGameButton.setVisibility(View.GONE);
        for (Player player : mListOfPlayers) {
            player.resetClickedFieldsState();
        }
        mGrid.clearGrid();
        setStartingPlayer();
    }

    private void triggerBot() {
        BotPlayer bot = (BotPlayer) mCurrentPlayer;
        int fieldChosenByBot = bot.makeMove(mGrid.getCurrentViewsInGameGrid());
        mGrid.simulateClick(fieldChosenByBot);
    }

    private void setStartingPlayer() {
        if (mIndexOfPlayerStartingTheGame == mListOfPlayers.size() - 1) {
            mIndexOfPlayerStartingTheGame = 0;
        } else {
            mIndexOfPlayerStartingTheGame++;
        }

        int currentPlayerIndex = mListOfPlayers.indexOf(mCurrentPlayer);
        mListOfPlayersNamesTVs.get(currentPlayerIndex)
                .setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

        mCurrentPlayer = mListOfPlayers.get(mIndexOfPlayerStartingTheGame);

        currentPlayerIndex = mListOfPlayers.indexOf(mCurrentPlayer);
        mListOfPlayersNamesTVs.get(currentPlayerIndex)
                .setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));

        if (mCurrentPlayer instanceof BotPlayer) {
            triggerBot();
        }

    }

    @Override
    public void finishGame(boolean isWinner) {
        mGrid.setIsGameFinished(true);
        mGamesPlayedCounter++;
        mGamesPlayedTV.setText(mContext.getString(R.string.string_gameplay_games_played) + " " + mGamesPlayedCounter);
        mNextGameButton.setVisibility(View.VISIBLE);

        if (isWinner) {
            mCurrentPlayer.incrementScore();
            showToast(mCurrentPlayer.getPlayerName() + " " + mContext.getString(R.string.string_gameplay_won));
            mGrid.markWinningShapes();
            refreshScores();
        } else {
            showToast(mContext.getString(R.string.string_gameplay_draw));
        }
    }

    @Override
    public Player getCurrentPlayer() {
        return mCurrentPlayer;
    }

    private void refreshScores() {
        for (int i = 0; i < mListOfPlayers.size(); ++i) {
            mListOfPlayersScoresTVs.get(i).setText(Integer.toString(mListOfPlayers.get(i).getScore()));
        }
    }

    private void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
