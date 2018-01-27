package com.example.android.tictacgrid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.Players.Player1;
import com.example.android.tictacgrid.Players.Player2;
import com.example.android.tictacgrid.Players.Player3;
import com.example.android.tictacgrid.Players.Player4;

import java.util.ArrayList;

/**
 * Created by pawel on 27.01.18.
 */

public class MultiplayerMenuActivity extends AppCompatActivity {

    ArrayList<Player> listOfPlayers;

    Button startStandardGameButton;
    Button startCustomGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_menu);

        setButtons();
    }

    private void setButtons() {

        listOfPlayers = new ArrayList<>();
        listOfPlayers.add(new Player1(this, "Paweł"));
        listOfPlayers.add(new Player2(this, "Radek"));
        listOfPlayers.add(new Player3(this, "Ania"));
        listOfPlayers.add(new Player4(this, "Agnieszka"));

        setStandardGameButton();
        setCustomGameButton();
    }

    private void setStandardGameButton() {

        startStandardGameButton = (Button) findViewById(R.id.button_standard_game);
        startStandardGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                ArrayList<String> names = new ArrayList<>();
                for(int i = 0; i < 2; ++i)
                    names.add(listOfPlayers.get(i).getPlayerName());

                int[] dimens = {3, 3};

                intent.putExtra("namesOfPlayers", names);
                intent.putExtra("gridDimens", dimens);
                intent.putExtra("howManyInLineToWin", 3);
                startActivity(intent);
            }
        });
    }

    private void setCustomGameButton() {

        startCustomGameButton = (Button) findViewById(R.id.button_custom_game);
        startCustomGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                ArrayList<String> names = new ArrayList<>();
                for(Player player : listOfPlayers)
                    names.add(player.getPlayerName());

                int[] dimens = {15, 20};

                intent.putExtra("namesOfPlayers", names);
                intent.putExtra("gridDimens", dimens);
                intent.putExtra("howManyInLineToWin", 5);
                startActivity(intent);
            }
        });
    }
}
