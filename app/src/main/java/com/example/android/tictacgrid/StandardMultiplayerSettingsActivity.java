package com.example.android.tictacgrid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by pawel on 27.01.18.
 */

public class StandardMultiplayerSettingsActivity extends AppCompatActivity {

    static final int[] STANDARD_GAME_GRID_DIMENSIONS = {3, 3};
    ArrayList<String> listOfNames;

    ArrayList<EditText> listOfNamesEditTextFields;
    Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_multiplayer_settings);

        listOfNamesEditTextFields = new ArrayList<>();
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player1_name_standard_game));
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player2_name_standard_game));

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
                intent.putExtra("gridDimens", STANDARD_GAME_GRID_DIMENSIONS);
                intent.putExtra("howManyInLineToWin", 3);
                startActivity(intent);
            }
        });
    }
}
