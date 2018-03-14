package com.example.android.tictacgrid.Menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.tictacgrid.R;


public class MainActivity extends AppCompatActivity {

    Button startSinglePlayerButton;
    Button startMultiplayerButton;
    Button exitAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtons();
    }

    private void setButtons() {
        setSinglePlayerButton();
        setMultiplayerButton();
        setExitButton();
    }

    private void setSinglePlayerButton() {

        startSinglePlayerButton = (Button) findViewById(R.id.button_single_player);
        startSinglePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SinglePlayerMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setMultiplayerButton() {

        startMultiplayerButton = (Button) findViewById(R.id.button_multiplayer);
        startMultiplayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MultiplayerMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setExitButton() {

        exitAppButton = (Button) findViewById(R.id.button_exit_game);
        exitAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}
