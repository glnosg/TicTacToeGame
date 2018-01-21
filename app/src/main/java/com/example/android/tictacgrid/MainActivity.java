package com.example.android.tictacgrid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.Players.Player1;
import com.example.android.tictacgrid.Players.Player2;
import com.example.android.tictacgrid.Players.Player3;
import com.example.android.tictacgrid.Players.Player4;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    LinearLayout myLayout;
    ArrayList<Player> listOfPlayers;

    private Button startNewGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfPlayers = new ArrayList<>();
        listOfPlayers.add(new Player1(this, "Paweł"));
        listOfPlayers.add(new Player2(this, "Radek"));
//        listOfPlayers.add(new Player3(this, "Ania"));
//        listOfPlayers.add(new Player4(this, "Agnieszka"));

        myLayout = (LinearLayout) findViewById(R.id.ll_main_layout);

        startNewGameButton = (Button) findViewById(R.id.button_start_game);
        startNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                ArrayList<String> names = new ArrayList<>();
                for(Player player : listOfPlayers)
                    names.add(player.getPlayerName());

                intent.putExtra("namesOfPlayers", names);
                startActivity(intent);
            }
        });
    }
}
