package com.example.android.tictacgrid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.Players.Player1;
import com.example.android.tictacgrid.Players.Player2;
import com.example.android.tictacgrid.Players.Player3;
import com.example.android.tictacgrid.Players.Player4;
import com.example.android.tictacgrid.Shapes.ShapeEmpty;
import com.example.android.tictacgrid.Shapes.ShapeView;

import java.util.ArrayList;

/**
 * Created by pawel on 20.01.18.
 */

public class GameActivity extends AppCompatActivity {

    // Number of game grid's columns
    int numOfGridColumns;
    // Number of game grid's rows
    int numOfGridRows;
    // Number of figures in row needed to win
    int howManyFiguresInLineToWin;
    // Number of fields clicked in current game
    int clickedFieldsCounter = 0;
    // Number of played games
    int gamesPlayed = 0;
    // Value is switched to true when somebody wins,
    // or when all fields are filled with figures (draw).
    //After clicking "next game" button its value is set back to false
    boolean isGameFinished = false;
    // Index of player who begins current game
    int indexOfPlayerStartingTheGame = 0;

    // List of players playing in current game
    ArrayList<Player> listOfPlayers;
    // Player which is currently moving
    Player currentPlayer;
    // Holds information about current state of game grid
    // (which fields are empty and which are filled with specific figures)
    ShapeView[] currentViewsInGameGrid;
    // Toast class object used by helper method showToast()
    Toast mToast;

    // List of TextViews used to display players' names
    ArrayList<TextView> listOfPlayersTextViews;
    // List of TextViews used to display players' scores
    ArrayList<TextView> listOfScoresTextViews;
    GridLayout gameGrid;
    // Params of game grid
    GridLayout.LayoutParams gameGridLayoutParams;
    // TextView used to display number of played games
    TextView gamesPlayedTextView;
    // Button showed when game is finished (victory or draw)
    // Clicking it starts a new game
    Button nextGameButton;

    // activity_game is set as activitys layout
    // Methods buildGrid() and setUpGame() are called
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buildGrid();
        setUpGame();
    }

    // At first, receiveGridParameters() method to receive size of grid
    // after receiving parameters it builds game grid and fills it with objects of ShapeEmpty class
    // ShapeEmpty, just like other shape-classes, is a subclass of ShapeView class
    // The only difference is, that it draws only background, without any shape on top of it
    private void buildGrid() {

       receiveGridParameters();

        gameGrid = (GridLayout) findViewById(R.id.gv_game_grid);
        gameGrid.setColumnCount(numOfGridColumns);
        gameGrid.setRowCount(numOfGridRows);

        currentViewsInGameGrid = new ShapeView[numOfGridColumns * numOfGridRows];

        for(int coordY = 0; coordY < numOfGridRows; coordY++) {
            for(int coordX = 0; coordX < numOfGridColumns; coordX++) {

                final int[] coords = {coordX, coordY};

                ShapeView currentView = new ShapeEmpty(this);
                currentViewsInGameGrid[(coordY * numOfGridColumns) + coordX] = currentView;
                gameGrid.addView(currentView);
                setFieldListener(coords);
            }
        }

        gameGrid.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {


                        final int FRAME_WIDTH = 3;

                        int gameGridWidth = gameGrid.getWidth();
                        int gameGridHeight = gameGrid.getHeight();
                        int gameGridCellWidth = gameGridWidth / numOfGridColumns;
                        int gameGridCellHeight = gameGridHeight / numOfGridRows;

                        for(int coordY = 0; coordY < numOfGridRows; coordY++){
                            for(int coordX = 0; coordX < numOfGridColumns; coordX++) {

                                GridLayout.LayoutParams params =
                                        (GridLayout.LayoutParams) currentViewsInGameGrid[
                                                (coordY * numOfGridColumns) + coordX].getLayoutParams();

                                params.width = gameGridCellWidth - (2 * FRAME_WIDTH);
                                params.height = gameGridCellHeight - (2 * FRAME_WIDTH);
                                params.setMargins(FRAME_WIDTH, FRAME_WIDTH, FRAME_WIDTH, FRAME_WIDTH);

                                currentViewsInGameGrid[
                                        (coordY * numOfGridColumns) + coordX].setLayoutParams(params);
                            }
                        }

                    }});
    }

    // Receives from information about game grid dimensions from intent extra
    // Assigns received values to variables numOfGridColumns and numOfGridRows
    // Same with information about number of figures in row needed to win
    // That value is assigned to howManyFiguresInLineToWin variable
    private void receiveGridParameters() {
        int[] gridSize = getIntent().getIntArrayExtra("gridDimens");
        numOfGridColumns = gridSize[0];
        numOfGridRows = gridSize[1];

        howManyFiguresInLineToWin = getIntent().getIntExtra("howManyInLineToWin", 3);
    }

    private void setUpGame() {

        // Assigns layout's TextViews to an appropriate objects
        gamesPlayedTextView = (TextView) findViewById(R.id.tv_games_played);
        gamesPlayedTextView.setText(getString(R.string.string_gameplay_games_played) + " " + gamesPlayed);

        listOfPlayersTextViews = new ArrayList<>();
        listOfScoresTextViews = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        // Initialisation of list of players' names with values received from intent extra
        ArrayList<String> namesOfPlayers =
                (ArrayList<String>) getIntent().getSerializableExtra("namesOfPlayers");

        // An appropriate 'addPlayers' method is called
        // (depending on how many names was provided in intent extra)
        switch (namesOfPlayers.size()) {

            case 2:
                addTwoPlayers(namesOfPlayers);
                break;
            case 3:
                addThreePlayers(namesOfPlayers);
                break;
            case 4:
                addFourPlayers(namesOfPlayers);
        }

        // Victory condition received in extra is set in each player object
        for (Player player : listOfPlayers) {
            player.setHowManyInLineToWin(howManyFiguresInLineToWin);
        }

        // First player is set as current player
        // Background color of its TextView is changed
        // (That's how current player is marked in UI)
        if (currentPlayer == null) {
            currentPlayer = listOfPlayers.get(0);
            listOfPlayersTextViews.get(0).
                    setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        // next game button from layout is assigned to nextGameButton object
        // OnClickListener is set - it triggers resetGame() method when clicked
        nextGameButton = (Button) findViewById(R.id.button_next_game);
        nextGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }


    // Adds new player objects to list of players
    // Each player object is initialized with name received in extra
    // text views with names and scores are bonded with players and their values are set
    private void addTwoPlayers(ArrayList<String> names) {

        listOfPlayers.add(new Player1(this, names.get(0)));
        listOfPlayers.add(new Player2(this, names.get(1)));

        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player1_name));
        listOfPlayersTextViews.get(0).setText(listOfPlayers.get(0).getPlayerName());
        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player1_score));
        listOfScoresTextViews.get(0).setText(Integer.toString(listOfPlayers.get(0).getScore()));

        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player2_name));
        listOfPlayersTextViews.get(1).setText(listOfPlayers.get(1).getPlayerName());
        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player2_score));
        listOfScoresTextViews.get(1).setText(Integer.toString(listOfPlayers.get(0).getScore()));
    }

    // Calls addTwoPlayers() method and then adds third player
    private void addThreePlayers(ArrayList<String> names) {

        addTwoPlayers(names);
        findViewById(R.id.ll_extra_players).setVisibility(View.VISIBLE);

        listOfPlayers.add(new Player3(this, names.get(2)));
        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player3_name));
        listOfPlayersTextViews.get(2).setText(listOfPlayers.get(2).getPlayerName());
        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player3_score));
        listOfScoresTextViews.get(2).setText(Integer.toString(listOfPlayers.get(2).getScore()));
    }

    // Calls addThreePlayers() method and then adds fourth player
    private void addFourPlayers(ArrayList<String> names) {

        addThreePlayers(names);

        listOfPlayers.add(new Player4(this, names.get(3)));
        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player4_name));
        listOfPlayersTextViews.get(3).setVisibility(View.VISIBLE);
        listOfPlayersTextViews.get(3).setText(listOfPlayers.get(3).getPlayerName());
        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player4_score));
        listOfScoresTextViews.get(3).setVisibility(View.VISIBLE);
        listOfScoresTextViews.get(3).setText(Integer.toString(listOfPlayers.get(3).getScore()));
    }

    // Sets onClickListener of field with coords sent as a parameter
    // if game is not finished yet it addNewShape() method is called with current coordinates
    // after move current player's makeMove() method is called (also with these coordinates)
    // if makeMove returns true (player won), game is finished
    // if it returns false (player didn't win), changePlayer() method is called
    private void setFieldListener(final int[] coords) {

        gameGrid.getChildAt((numOfGridColumns * coords[1]) + coords[0]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isGameFinished) {
                    addNewShape(coords);
                    if (currentPlayer.makeMove(coords)) {
                        finishGame(true);
                    } else {
                        if (clickedFieldsCounter == numOfGridRows * numOfGridColumns) {
                            finishGame(false);
                        } else {
                            changePlayer();
                        }
                    }
                }
            }
        });

    }

    // Method counts fields index and sends it to setLayoutParamsForNewShape method
    // It calls getShape() method on current player
    //
    // /*(Each Player subclass has its own implementation of getShape() method,
    //    simply each player with that method returns new object of different ShapeView's subclass) */
    //
    // Current view is removed from fields with coordinates sent to the method
    // and it's replaced with view from newShape
    // (already without OnClickListener - any field can't be clicked more than one time)
    // In the end clicked fields counter's value is incremented
    private void addNewShape(int[] coords) {

        int clickedFieldIndex = (numOfGridColumns * coords[1]) + coords[0];
        setLayoutParamsForNewShape(clickedFieldIndex);

        ShapeView newShape = currentPlayer.getShape(isGameFinished);
        newShape.setLayoutParams(gameGridLayoutParams);

        currentViewsInGameGrid[clickedFieldIndex] = newShape;

        gameGrid.removeView(gameGrid.getChildAt(clickedFieldIndex));
        gameGrid.addView(newShape, clickedFieldIndex, gameGridLayoutParams);

        clickedFieldsCounter++;
    }

    // Sets parameters of a single game grid's cell (with index sent as fieldIndex parameter)
    private void setLayoutParamsForNewShape(int fieldIndex) {

        final int FRAME_WIDTH = 3;

        gameGridLayoutParams =
                (GridLayout.LayoutParams) currentViewsInGameGrid[fieldIndex].getLayoutParams();

        int gameGridWidth = gameGrid.getWidth();
        int gameGridHeight = gameGrid.getHeight();
        int gameGridCellWidth = gameGridWidth / numOfGridColumns;
        int gameGridCellHeight = gameGridHeight / numOfGridRows;

        gameGridLayoutParams.width = gameGridCellWidth - (2 * FRAME_WIDTH);
        gameGridLayoutParams.height = gameGridCellHeight - (2 * FRAME_WIDTH);
        gameGridLayoutParams.setMargins(FRAME_WIDTH, FRAME_WIDTH, FRAME_WIDTH, FRAME_WIDTH);
    }

    // Method changes current player's value to next player on list of players
    // (if current player is last in the list, first from list is set as next current player)
    // it sets background of an old current player to a regular one
    // and sets background of a new current player to e distinguished one
    private void changePlayer() {

        int currentPlayerIndex = listOfPlayers.indexOf(currentPlayer);

        listOfPlayersTextViews.get(currentPlayerIndex)
                .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        if (currentPlayerIndex == (listOfPlayers.size() - 1)) {
            currentPlayer = listOfPlayers.get(0);
            listOfPlayersTextViews.get(0)
                    .setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            currentPlayer = listOfPlayers.get(currentPlayerIndex + 1);
            listOfPlayersTextViews.get(currentPlayerIndex + 1)
                    .setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    // Button view is removed from layout (View.GONE)
    // value of isGameFinished flag is set back to false
    // counter of clicked fields is reset
    // list of clicked fields for each player is reset
    // game grid is cleaned and build again
    // Sets next player as player starting game
    private void resetGame() {
        nextGameButton.setVisibility(View.GONE);
        isGameFinished = false;
        clickedFieldsCounter = 0;
        for (Player player : listOfPlayers) {
            player.resetClickedFieldsState();
        }
        gameGrid.removeAllViews();
        buildGrid();
        setStartingPlayer();
    }

    // Sets index of player who makes first move in current game
    private void setStartingPlayer() {

        if (indexOfPlayerStartingTheGame == listOfPlayers.size() - 1) {
            indexOfPlayerStartingTheGame = 0;
        } else {
            indexOfPlayerStartingTheGame++;
        }

        int currentPlayerIndex = listOfPlayers.indexOf(currentPlayer);
        listOfPlayersTextViews.get(currentPlayerIndex)
                .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        currentPlayer = listOfPlayers.get(indexOfPlayerStartingTheGame);

        currentPlayerIndex = listOfPlayers.indexOf(currentPlayer);
        listOfPlayersTextViews.get(currentPlayerIndex)
                .setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    // Switches value of isGameFinished flag to true
    // Increments number of played games (and refreshes its TextView)
    // sets visibility of 'next game' button to value View.VISIBLE
    private void finishGame(boolean isWinner) {
        isGameFinished = true;
        gamesPlayed++;
        gamesPlayedTextView.setText(getString(R.string.string_gameplay_games_played) + " " + gamesPlayed);
        nextGameButton.setVisibility(View.VISIBLE);

        if (isWinner) {
            currentPlayer.incrementScore();
            showToast(currentPlayer.getPlayerName() + " " + getString(R.string.string_gameplay_won));
            markWinningShapes();
            refreshScores();
        } else {
            showToast(getString(R.string.string_gameplay_draw));
        }
    }

    private void markWinningShapes() {
        ArrayList<int[]> listOfWinningFields = currentPlayer.getListOfWinningFields();

        for (int[] currentCoords : listOfWinningFields) {
            addNewShape(currentCoords);
            Log.d("GameActivity", "Painted winning field: " + currentCoords[0] + ", " + currentCoords[1]);
        }
    }

    private void refreshScores() {
        for (int i = 0; i < listOfPlayers.size(); ++i) {
            listOfScoresTextViews.get(i).setText(Integer.toString(listOfPlayers.get(i).getScore()));
        }
    }

    private void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
