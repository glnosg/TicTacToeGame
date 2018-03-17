package com.example.android.tictacgrid.Menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.tictacgrid.Game.GameActivity;
import com.example.android.tictacgrid.R;

import java.util.ArrayList;


/**
 * Created by pawel on 27.01.18.
 */

public class SinglePlayerMenuActivity extends AppCompatActivity {

    static final int[] STANDARD_GAME_GRID_DIMENSIONS = {3, 3};
    static final int STANDARD_WINNING_CONDITION = 3;

    Button startEasyGameButton;
    Button startMediumGameButton;
    Button startHardGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_menu);

        setButtons();
    }

    private void setButtons() {

        setEasyGameButton();
        setMediumGameButton();
        setHardGameButton();
    }

    private void setEasyGameButton() {

        startEasyGameButton = (Button) findViewById(R.id.button_easy_mode);
        startEasyGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame("Easy Bot");
            }
        });
    }

    private void setMediumGameButton() {

        startMediumGameButton = (Button) findViewById(R.id.button_medium_mode);
        startMediumGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame("Medium Bot");
            }
        });
    }

    private void setHardGameButton() {

        startHardGameButton = (Button) findViewById(R.id.button_hard_mode);
        startHardGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGame("Hard Bot");
            }
        });
    }

    private void openGame(String nameOfBotPlayer) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);

        ArrayList listOfNames = new ArrayList<>();
        listOfNames.add(nameOfBotPlayer);

        intent.putExtra("namesOfPlayers", listOfNames);
        intent.putExtra("gridDimens", STANDARD_GAME_GRID_DIMENSIONS);
        intent.putExtra("howManyInLineToWin", STANDARD_WINNING_CONDITION);
        startActivity(intent);
    }
}