package com.example.android.tictacgrid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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

    int numOfGridColumns;
    int numOfGridRows;
    int howManyFiguresInLineToWin;
    int clickedFieldsCounter = 0;
    boolean isGameFinished = false;

    ArrayList<Player> listOfPlayers;
    Player currentPlayer;
    ShapeView[] currentViewsInGameGrid;
    Toast mToast;

    LinearLayout gameLayout;
    ArrayList<TextView> listOfPlayersTextViews;
    ArrayList<TextView> listOfScoresTextViews;
    GridLayout gameGrid;
    GridLayout.LayoutParams gameGridLayoutParams;
    Button resetButton;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buildGrid();
        setUpGame();
    }

    private void buildGrid() {

        setGridParameters();

        gameLayout = (LinearLayout) findViewById(R.id.ll_game_layout);

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

    private void setGridParameters() {
        int[] gridSize = getIntent().getIntArrayExtra("gridDimens");
        numOfGridColumns = gridSize[0];
        numOfGridRows = gridSize[1];

        howManyFiguresInLineToWin = getIntent().getIntExtra("howManyInLineToWin", 3);
    }

    private void setUpGame() {

        listOfPlayersTextViews = new ArrayList<>();
        listOfScoresTextViews = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
        ArrayList<String> namesOfPlayers =
                (ArrayList<String>) getIntent().getSerializableExtra("namesOfPlayers");

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

        for (Player player : listOfPlayers) {
            player.setHowManyInLineToWin(howManyFiguresInLineToWin);
        }

        if (currentPlayer == null) {
            currentPlayer = listOfPlayers.get(0);
            listOfPlayersTextViews.get(0).
                    setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        resetButton = (Button) findViewById(R.id.button_next_game);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

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

    private void addThreePlayers(ArrayList<String> names) {

        addTwoPlayers(names);
        findViewById(R.id.ll_extra_players).setVisibility(View.VISIBLE);

        listOfPlayers.add(new Player3(this, names.get(2)));
        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player3_name));
        listOfPlayersTextViews.get(2).setText(listOfPlayers.get(2).getPlayerName());
        listOfScoresTextViews.add((TextView) findViewById(R.id.tv_player3_score));
        listOfScoresTextViews.get(2).setText(Integer.toString(listOfPlayers.get(2).getScore()));
    }

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

    private void resetGame() {
        resetButton.setVisibility(View.GONE);
        isGameFinished = false;
        clickedFieldsCounter = 0;
        for (Player player : listOfPlayers) {
            player.resetClickedFieldsState();
        }
        gameGrid.removeAllViews();
        buildGrid();
    }

    private void finishGame(boolean isWinner) {
        isGameFinished = true;
        resetButton.setVisibility(View.VISIBLE);

        if (isWinner) {
            currentPlayer.incrementScore();
            showToast(currentPlayer.getPlayerName() + " WON!");
            markWinningShapes();
            refreshScores();
        } else {
            showToast("DRAW!");
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
