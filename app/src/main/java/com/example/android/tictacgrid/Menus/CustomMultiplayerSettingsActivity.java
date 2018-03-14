package com.example.android.tictacgrid.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.tictacgrid.Game.GameActivity;
import com.example.android.tictacgrid.R;

import java.util.ArrayList;


/**
 * Created by pawel on 27.01.18.
 */

public class CustomMultiplayerSettingsActivity extends AppCompatActivity {

    static final int[] DEFAULT_GAME_GRID_DIMENSIONS = {15, 20};
    static final int DEFAULT_WINNING_CONDITION = 5;
    int numberOfPlayers = 2;
    Toast mToast;

    ArrayList<EditText> listOfNamesEditTextFields;
    ArrayList<EditText> listOfGridDimensionsEditTexts;
    EditText winningConditionEditText;
    Spinner numberOfPlayersSpinner;
    Button startGameButton;
    LinearLayout settingsForPlayer3, settingsForPlayer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_multiplayer_settings);

        setLayoutElements();
    }

    private void setLayoutElements() {

        setEditTexts();
        setSpinner();
        setStartGameButton();
    }

    private void setEditTexts() {

        listOfNamesEditTextFields = new ArrayList<>();
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player1_name_custom_game));
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player2_name_custom_game));
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player3_name_custom_game));
        listOfNamesEditTextFields.add((EditText) findViewById(R.id.et_player4_name_custom_game));

        listOfGridDimensionsEditTexts = new ArrayList<>();
        listOfGridDimensionsEditTexts.add((EditText) findViewById(R.id.et_number_of_grid_columns));
        listOfGridDimensionsEditTexts.add((EditText) findViewById(R.id.et_number_of_grid_rows));

        winningConditionEditText = (EditText) findViewById(R.id.et_how_many_in_line_to_win);

        settingsForPlayer3 = (LinearLayout) findViewById(R.id.ll_player3_settings);
        settingsForPlayer4 = (LinearLayout) findViewById(R.id.ll_player4_settings);
    }

    private void setSpinner() {

        numberOfPlayersSpinner = (Spinner) findViewById(R.id.spinner_how_many_players);
        Integer[] spinnerItems = {2, 3, 4};
        ArrayAdapter<Integer> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        numberOfPlayersSpinner.setAdapter(adapter);

        numberOfPlayersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOfPlayers = (int) parent.getSelectedItem();

                switch (numberOfPlayers) {
                    case 2:
                        settingsForPlayer3.setVisibility(View.GONE);
                        settingsForPlayer4.setVisibility(View.GONE);
                        break;
                    case 3:
                        settingsForPlayer3.setVisibility(View.VISIBLE);
                        settingsForPlayer4.setVisibility(View.GONE);
                        break;
                    case 4:
                        settingsForPlayer3.setVisibility(View.VISIBLE);
                        settingsForPlayer4.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setStartGameButton() {

        startGameButton = (Button) findViewById(R.id.button_standard_game_start);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                ArrayList<String> listOfNames = new ArrayList<>();
                for (int i = 0; i < numberOfPlayers; ++i) {

                    String name = listOfNamesEditTextFields.get(i).getText().toString();
                    if (name.equals("")) {
                        name = "Player " + (i + 1);
                    }
                    listOfNames.add(name);
                }

                int[] gridDimens = DEFAULT_GAME_GRID_DIMENSIONS;
                String[] enteredDimens = {
                        listOfGridDimensionsEditTexts.get(0).getText().toString(),
                        listOfGridDimensionsEditTexts.get(1).getText().toString()};

                for (int i = 0; i < enteredDimens.length; ++i) {

                    if (!enteredDimens[i].equals("")) {
                        try {
                            gridDimens[i] = Integer.parseInt(enteredDimens[i]);
                        } catch (NumberFormatException e) {
                            showToast("Grid size: " + enteredDimens[i] + " is not a numerical value!");
                        }
                    }
                }

                int winningCondition = DEFAULT_WINNING_CONDITION;
                String enteredWinningCondition = winningConditionEditText.getText().toString();

                if (!enteredWinningCondition.equals("")) {
                    try {
                        winningCondition = Integer.parseInt(enteredWinningCondition);
                    } catch (NumberFormatException e) {
                        showToast("Winning condition: " + enteredWinningCondition + " is not a numerical value!");
                    }
                }

                intent.putExtra("namesOfPlayers", listOfNames);
                intent.putExtra("gridDimens", gridDimens);
                intent.putExtra("howManyInLineToWin", winningCondition);
                startActivity(intent);
            }
        });
    }

    private void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
