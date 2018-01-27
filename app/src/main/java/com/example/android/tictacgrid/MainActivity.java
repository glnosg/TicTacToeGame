package com.example.android.tictacgrid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.tictacgrid.Players.Player;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<Player> listOfPlayers;
    Toast mToast;

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
                showToast("Opens single player mode");
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

    private void showToast(String text) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
