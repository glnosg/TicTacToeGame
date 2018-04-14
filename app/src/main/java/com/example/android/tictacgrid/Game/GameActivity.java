package com.example.android.tictacgrid.Game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.android.tictacgrid.R;

import java.util.ArrayList;

/**
 * Created by pawel on 20.01.18.
 */

public class GameActivity extends AppCompatActivity {

    Grid gameGrid;
    Game gameLogic;

    // Number of game grid's columns
    int numOfGridColumns;
    // Number of game grid's rows
    int numOfGridRows;

    ArrayList<String> listOfPlayersNames;
    // Number of figures in row needed to win
    int howManyFiguresInLineToWin;

    // List of TextViews used to display players' names
    ArrayList<TextView> listOfPlayersTextViews;
    // List of TextViews used to display players' scores
    ArrayList<TextView> listOfScoresTextViews;
    GridLayout gameGridLayout;

    // TextView used to display number of played games
    TextView gamesPlayedTextView;
    // Button showed when game is finished (victory or draw)
    // Clicking it starts a new game
    Button nextGameButton;

    // activity_game is set as activity's layout
    // Methods buildGrid() and setUpGame() are called
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameGridLayout = (GridLayout) findViewById(R.id.gv_game_grid);

        listOfPlayersTextViews = new ArrayList<>();
        listOfScoresTextViews = new ArrayList<>();

        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player1_name));
        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player2_name));
        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player3_name));
        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player4_name));

        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player1_score));
        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player2_score));
        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player3_score));
        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player4_score));

        gamesPlayedTextView = (TextView) findViewById(R.id.tv_games_played);

        nextGameButton = (Button) findViewById(R.id.button_next_game);

        receiveGridParameters();

        gameLogic = new GameConcrete(
                this,
                listOfPlayersTextViews,
                listOfScoresTextViews,
                gamesPlayedTextView,
                nextGameButton,
                listOfPlayersNames,
                howManyFiguresInLineToWin);
        gameGrid = new GameGrid(this, gameGridLayout, numOfGridColumns, numOfGridRows);

        gameGrid.setGame(gameLogic);
    }

    // Receives from information about game grid dimensions from intent extra
    // Assigns received values to variables numOfGridColumns and numOfGridRows
    // Same with information about number of figures in row needed to win
    // That value is assigned to howManyFiguresInLineToWin variable
    private void receiveGridParameters() {
        int[] gridSize = getIntent().getIntArrayExtra("gridDimens");
        numOfGridColumns = gridSize[0];
        numOfGridRows = gridSize[1];

        listOfPlayersNames = (ArrayList<String>) getIntent().getSerializableExtra("namesOfPlayers");
        if (listOfPlayersNames.size() > 2) {
            findViewById(R.id.ll_extra_players).setVisibility(View.VISIBLE);
        }
        howManyFiguresInLineToWin = getIntent().getIntExtra("howManyInLineToWin", 3);
    }
}
