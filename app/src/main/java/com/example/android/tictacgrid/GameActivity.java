package com.example.android.tictacgrid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    int numOfGridColumns = 20;
    int numOfGridRows = 15;

    ArrayList<Player> listOfPlayers;
    Player currentPlayer;
    ShapeView[] currentViewsInGameGrid;
    Toast mToast;

    LinearLayout gameLayout;
    ArrayList<TextView> listOfPlayersTextViews;
    GridLayout gameGrid;
    GridLayout.LayoutParams gameGridLayoutParams;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buildGrid();
        setUpGame();
    }

    private void buildGrid() {

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

    private void setUpGame() {

        listOfPlayersTextViews = new ArrayList<>();
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

        if (currentPlayer == null) {
            currentPlayer = listOfPlayers.get(0);
            listOfPlayersTextViews.get(0).
                    setBackgroundColor(getResources().getColor(R.color.colorActivePlayerBackground));
        }
    }

    private void addTwoPlayers(ArrayList<String> names) {

        listOfPlayers.add(new Player1(this, names.get(0)));
        listOfPlayers.add(new Player2(this, names.get(1)));

        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player1_name));
        listOfPlayersTextViews.get(0).setText(listOfPlayers.get(0).getPlayerName());

        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player2_name));
        listOfPlayersTextViews.get(1).setText(listOfPlayers.get(1).getPlayerName());
    }

    private void addThreePlayers(ArrayList<String> names) {

        addTwoPlayers(names);
        findViewById(R.id.ll_extra_players).setVisibility(View.VISIBLE);

        listOfPlayers.add(new Player3(this, names.get(2)));
        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player3_name));
        listOfPlayersTextViews.get(2).setText(listOfPlayers.get(2).getPlayerName());
    }

    private void addFourPlayers(ArrayList<String> names) {

        addThreePlayers(names);

        listOfPlayers.add(new Player4(this, names.get(3)));
        listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player4_name));
        listOfPlayersTextViews.get(3).setVisibility(View.VISIBLE);
        listOfPlayersTextViews.get(3).setText(listOfPlayers.get(3).getPlayerName());
    }

    private void setFieldListener(final int[] coordis) {

        final int[] coords = coordis;
        final int clickedFieldIndex = (numOfGridColumns * coords[1]) + coords[0];

        gameGrid.getChildAt(clickedFieldIndex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setLayoutParamsForNewShape(coords);
                ShapeView newShape = currentPlayer.getShape();
                newShape.setLayoutParams(gameGridLayoutParams);

                currentViewsInGameGrid[clickedFieldIndex] = newShape;

                gameGrid.removeView(gameGrid.getChildAt(clickedFieldIndex));
                gameGrid.addView(newShape, clickedFieldIndex, gameGridLayoutParams);

                currentPlayer.makeMove(coords);
                changePlayer();
            }
        });

    }

    private void setLayoutParamsForNewShape(int[] coords) {

        final int FRAME_WIDTH = 3;

        gameGridLayoutParams =
                (GridLayout.LayoutParams) currentViewsInGameGrid[
                        (coords[1] * numOfGridColumns) + coords[0]].getLayoutParams();

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
                .setBackgroundColor(getResources().getColor(R.color.colorGameGridCellBackground));

        if (currentPlayerIndex == (listOfPlayers.size() - 1)) {
            currentPlayer = listOfPlayers.get(0);
            listOfPlayersTextViews.get(0)
                    .setBackgroundColor(getResources().getColor(R.color.colorActivePlayerBackground));
        } else {
            currentPlayer = listOfPlayers.get(currentPlayerIndex + 1);
            listOfPlayersTextViews.get(currentPlayerIndex + 1)
                    .setBackgroundColor(getResources().getColor(R.color.colorActivePlayerBackground));
        }
    }

    private void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
