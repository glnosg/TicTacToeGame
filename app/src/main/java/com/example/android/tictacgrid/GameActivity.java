package com.example.android.tictacgrid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.Players.Player1;
import com.example.android.tictacgrid.Players.Player2;
import com.example.android.tictacgrid.Shapes.ShapeEmpty;
import com.example.android.tictacgrid.Shapes.ShapeView;

import java.util.ArrayList;

import static android.R.attr.x;


/**
 * Created by pawel on 20.01.18.
 */

public class GameActivity extends AppCompatActivity {

    int numOfGridColumns = 20;
    int numOfGridRows = 15;

    ArrayList<Player> listOfPlayers;
    Player currentPlayer;
    ShapeView[] currentViewsInGameGrid;
    private Toast mToast;

    LinearLayout gameLayout;
    ArrayList<TextView> listOfPlayersTextViews;
    GridLayout gameGrid;
    GridLayout.LayoutParams gameGridLayoutParams;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = (LinearLayout) findViewById(R.id.ll_game_layout);
        gameGrid = (GridLayout) findViewById(R.id.gv_game_grid);

        buildGrid();
        setUpGame();
    }

    private void buildGrid() {
        gameGrid.setColumnCount(numOfGridColumns);
        gameGrid.setRowCount(numOfGridRows);
        currentViewsInGameGrid = new ShapeView[numOfGridColumns* numOfGridRows];

        for(int yPos = 0; yPos< numOfGridRows; yPos++){
            for(int xPos=0; xPos<numOfGridColumns; xPos++){
                final int[] coords = {xPos, yPos};
                ShapeView tView = new ShapeEmpty(this);
                currentViewsInGameGrid[yPos*numOfGridColumns + xPos] = tView;
                gameGrid.addView(tView);
                setFieldListener(coords);
            }
        }

        gameGrid.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {

                        final int MARGIN = 3;

                        int pWidth = gameGrid.getWidth();
                        int pHeight = gameGrid.getHeight();
                        int w = pWidth/numOfGridColumns;
                        int h = pHeight/numOfGridRows;

                        for(int yPos=0; yPos<numOfGridRows; yPos++){
                            for(int xPos=0; xPos<numOfGridColumns; xPos++){
                                GridLayout.LayoutParams params =
                                        (GridLayout.LayoutParams) currentViewsInGameGrid[yPos*numOfGridColumns + xPos].getLayoutParams();
                                params.width = w - 2*MARGIN;
                                params.height = h - 2*MARGIN;
                                params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                                currentViewsInGameGrid[yPos*numOfGridColumns + xPos].setLayoutParams(params);
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
                listOfPlayers.add(new Player1(this, namesOfPlayers.get(0)));
                listOfPlayers.add(new Player2(this, namesOfPlayers.get(1)));

                listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player1_name));
                listOfPlayersTextViews.get(0).setText(listOfPlayers.get(0).getPlayerName());

                listOfPlayersTextViews.add((TextView) findViewById(R.id.tv_player2_name));
                listOfPlayersTextViews.get(1).setText(listOfPlayers.get(1).getPlayerName());
//            case 3:
//                listOfPlayers.add(new Player3(this, namesOfPlayers.get(1)));
//            case 4:
//                listOfPlayers.add(new Player4(this, namesOfPlayers.get(1)));
        }

        if (currentPlayer == null) {
            currentPlayer = listOfPlayers.get(0);
            listOfPlayersTextViews.get(0).setBackgroundColor(Color.parseColor("#F06292"));
        }

    }

    private void setFieldListener(final int[] coordis) {

        final int[] coords = coordis;
        final int clickedFieldIndex = (numOfGridColumns * coords[1]) + coords[0];

        gameGrid.getChildAt(clickedFieldIndex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int MARGIN = 3;

                gameGridLayoutParams =
                        (GridLayout.LayoutParams) currentViewsInGameGrid[coords[1]*numOfGridColumns + coords[0]].getLayoutParams();
                int pWidth = gameGrid.getWidth();
                int pHeight = gameGrid.getHeight();
                int w = pWidth/numOfGridColumns;
                int h = pHeight/numOfGridRows;

                gameGridLayoutParams.width = w - 2*MARGIN;
                gameGridLayoutParams.height = h - 2*MARGIN;
                gameGridLayoutParams.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);


                ShapeView newShape = currentPlayer.getShape();
                newShape.setLayoutParams(gameGridLayoutParams);
                currentViewsInGameGrid[clickedFieldIndex] = newShape;
                gameGrid.removeView(gameGrid.getChildAt(clickedFieldIndex));
                gameGrid.addView(newShape, clickedFieldIndex, gameGridLayoutParams);
                currentPlayer.makeMove(coords);
                showToast("Clicked: " + coords[0] + ", " + coords[1] + "; index: " + clickedFieldIndex);
                changePlayer();
            }
        });

    }

    private void changePlayer() {
        int currentPlayerIndex = listOfPlayers.indexOf(currentPlayer);
        listOfPlayersTextViews.get(currentPlayerIndex).setBackgroundColor(Color.parseColor("#FAFAFA"));

        if (currentPlayerIndex == (listOfPlayers.size() - 1)) {
            currentPlayer = listOfPlayers.get(0);
            listOfPlayersTextViews.get(0).setBackgroundColor(Color.parseColor("#F06292"));
        } else {
            currentPlayer = listOfPlayers.get(currentPlayerIndex + 1);
            listOfPlayersTextViews.get(currentPlayerIndex + 1).setBackgroundColor(Color.parseColor("#F06292"));
        }
//        playerName.setText(currentPlayer.getPlayerName());
    }

    private void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
