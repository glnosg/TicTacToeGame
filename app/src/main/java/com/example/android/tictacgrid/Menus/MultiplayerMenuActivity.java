package com.example.android.tictacgrid.Menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.tictacgrid.R;


/**
 * Created by pawel on 27.01.18.
 */

public class MultiplayerMenuActivity extends AppCompatActivity {

    Button startStandardGameButton;
    Button startCustomGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_menu);

        setButtons();
    }

    private void setButtons() {

        setStandardGameButton();
        setCustomGameButton();
    }

    private void setStandardGameButton() {

        startStandardGameButton = (Button) findViewById(R.id.button_standard_game);
        startStandardGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StandardMultiplayerSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setCustomGameButton() {

        startCustomGameButton = (Button) findViewById(R.id.button_custom_game);
        startCustomGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomMultiplayerSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
