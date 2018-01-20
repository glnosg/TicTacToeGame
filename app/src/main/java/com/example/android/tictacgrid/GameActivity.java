package com.example.android.tictacgrid;

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

import java.util.ArrayList;


/**
 * Created by pawel on 20.01.18.
 */

public class GameActivity extends AppCompatActivity {

    int numOfGridColumns = 3;
    int getNumOfGridRows = 3;

    ArrayList<Player> listOfPlayers;
    Player currentPlayer;
    ShapeEmpty[] emptyFields;
    private Toast mToast;

    LinearLayout gameLayout;
    TextView namePlayer1, namePlayer2;
    GridLayout gameGrid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = (LinearLayout) findViewById(R.id.ll_game_layout);
        gameGrid = (GridLayout) findViewById(R.id.gv_game_grid);

        namePlayer1 = (TextView) findViewById(R.id.tv_player1_name);
        namePlayer2 = (TextView) findViewById(R.id.tv_player2_name);

        setUpGame();
    }

    private void setUpGame() {

        listOfPlayers = new ArrayList<>();
        ArrayList<String> namesOfPlayers =
                (ArrayList<String>) getIntent().getSerializableExtra("namesOfPlayers");

        switch (namesOfPlayers.size()) {
            case 2:
                listOfPlayers.add(new Player1(this, namesOfPlayers.get(0)));
                listOfPlayers.add(new Player2(this, namesOfPlayers.get(1)));

                namePlayer1.setText(listOfPlayers.get(0).getPlayerName());
                namePlayer2.setText(listOfPlayers.get(1).getPlayerName());
//            case 3:
//                listOfPlayers.add(new Player3(this, namesOfPlayers.get(1)));
//            case 4:
//                listOfPlayers.add(new Player4(this, namesOfPlayers.get(1)));
        }

        if (currentPlayer == null)
            currentPlayer = listOfPlayers.get(0);

        gameGrid.setColumnCount(numOfGridColumns);
        gameGrid.setRowCount(getNumOfGridRows);
        emptyFields = new ShapeEmpty[numOfGridColumns*getNumOfGridRows];

        for(int yPos=0; yPos<getNumOfGridRows; yPos++){
            for(int xPos=0; xPos<numOfGridColumns; xPos++){
                final int[] coords = {xPos, yPos};
                final ShapeEmpty tView = new ShapeEmpty(this);
                emptyFields[yPos*numOfGridColumns + xPos] = tView;
                gameGrid.addView(tView);
                setFieldListener(coords);
            }
        }

        gameGrid.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {

                        final int MARGIN = 5;

                        int pWidth = gameGrid.getWidth();
                        int pHeight = gameGrid.getHeight();
                        int numOfCol = gameGrid.getColumnCount();
                        int numOfRow = gameGrid.getRowCount();
                        int w = pWidth/numOfCol;
                        int h = pHeight/numOfRow;

                        for(int yPos=0; yPos<numOfRow; yPos++){
                            for(int xPos=0; xPos<numOfCol; xPos++){
                                GridLayout.LayoutParams params =
                                        (GridLayout.LayoutParams)emptyFields[yPos*numOfCol + xPos].getLayoutParams();
                                params.width = w - 2*MARGIN;
                                params.height = h - 2*MARGIN;
                                params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                                emptyFields[yPos*numOfCol + xPos].setLayoutParams(params);
                            }
                        }

                    }});
    }

    private void setFieldListener(int[] coordis) {

        final int[] coords = coordis;
        final int clickedFieldIndex = (numOfGridColumns * coords[1]) + coords[0];

        gameGrid.getChildAt(clickedFieldIndex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameGrid.addView(currentPlayer.getShape(), clickedFieldIndex);
                currentPlayer.makeMove(coords);
                showToast("Clicked: " + coords[0] + ", " + coords[1]);
                changePlayer();
            }
        });

    }

    private void changePlayer() {
        int currentPlayerIndex = listOfPlayers.indexOf(currentPlayer);

        if (currentPlayerIndex == (listOfPlayers.size() - 1))
            currentPlayer = listOfPlayers.get(0);
        else
            currentPlayer = listOfPlayers.get(currentPlayerIndex + 1);

//        playerName.setText(currentPlayer.getPlayerName());
    }

    private void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
