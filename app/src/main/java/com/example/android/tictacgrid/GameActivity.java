package com.example.android.tictacgrid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.Players.Player1;
import com.example.android.tictacgrid.Players.Player2;
import com.example.android.tictacgrid.Shapes.ShapeEmpty;

import java.util.ArrayList;

/**
 * Created by pawel on 20.01.18.
 */

public class GameActivity extends AppCompatActivity
        implements ShapeEmpty.OnFieldClickListener {

    int sizeOfGameGrid = 3;
    ArrayList<Player> listOfPlayers;
    ShapeEmpty[] emptyFields;
    private Toast mToast;

    LinearLayout gameLayout;
    TextView namePlayer1, namePlayer2;
    GridView gameGrid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = (LinearLayout) findViewById(R.id.ll_game_layout);
        gameGrid = (GridView) findViewById(R.id.gv_game_grid);

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

        gameGrid.setNumColumns(sizeOfGameGrid);
    }

    @Override
    public void onFieldClicked(Player player) {

    }

    private void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
