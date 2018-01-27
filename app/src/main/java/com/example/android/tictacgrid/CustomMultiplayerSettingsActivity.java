package com.example.android.tictacgrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import static com.example.android.tictacgrid.StandardMultiplayerSettingsActivity.STANDARD_GAME_GRID_DIMENSIONS;

/**
 * Created by pawel on 27.01.18.
 */

public class CustomMultiplayerSettingsActivity extends AppCompatActivity {

    static final int[] DEFAULT_GAME_GRID_DIMENSIONS = {15, 20};
    static final int DEFAULT_WINNING_CONDITION = 5;
    ArrayList<String> listOfNames;

    ArrayList<EditText> listOfNamesEditTextFields;
    Spinner numberOfPlayersSpinner;
    Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_multiplayer_settings);

        listOfNamesEditTextFields = new ArrayList<>();
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player1_name_custom_game));
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player2_name_custom_game));
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player3_name_custom_game));
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player4_name_custom_game));

        numberOfPlayersSpinner = (Spinner) findViewById(R.id.spinner_how_many_players);
        Integer[] spinnerItems = {1, 2, 3, 4};
        ArrayAdapter<Integer> adapter =
                new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        numberOfPlayersSpinner.setAdapter(adapter);

        setButtons();
    }

    private void setButtons() {

        setStartGameButton();
    }

    private void setStartGameButton() {

        startGameButton = (Button) findViewById(R.id.button_standard_game_start);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                listOfNames = new ArrayList<>();

                for (int i = 0; i < listOfNamesEditTextFields.size(); ++i) {

                    String name = listOfNamesEditTextFields.get(i).getText().toString();
                    if (name.equals("")) {
                        name = "Player " + (i + 1);
                    }
                    listOfNames.add(name);
                }

                intent.putExtra("namesOfPlayers", listOfNames);
                intent.putExtra("gridDimens", DEFAULT_GAME_GRID_DIMENSIONS);
                intent.putExtra("howManyInLineToWin", DEFAULT_WINNING_CONDITION);
                startActivity(intent);
            }
        });
    }
}
