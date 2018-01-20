package com.example.android.tictacgrid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.tictacgrid.Players.Player;
import com.example.android.tictacgrid.Players.Player1;
import com.example.android.tictacgrid.Players.Player2;
import com.example.android.tictacgrid.Shapes.ShapeView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    LinearLayout myLayout;
    TextView playerName;
    ArrayList<Player> listOfPlayers;
    Player currentPlayer;

    View lastShape;

    private Button startNewGameButton;
    private Button drawShapeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfPlayers = new ArrayList<>();
        listOfPlayers.add(new Player1(this, "Paweł"));
        listOfPlayers.add(new Player2(this, "Radek"));

        currentPlayer = listOfPlayers.get(0);

        myLayout = (LinearLayout) findViewById(R.id.ll_main_layout);

        playerName = (TextView) findViewById(R.id.tv_current_player_name);
        playerName.setText(currentPlayer.getPlayerName());

        drawShapeButton = (Button) findViewById(R.id.button_draw_shape);
        drawShapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawShape(currentPlayer);
                changePlayer();
            }
        });

        startNewGameButton = (Button) findViewById(R.id.button_start_game);
        startNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                ArrayList<String> names = new ArrayList<String>();
                for(Player player : listOfPlayers)
                    names.add(player.getPlayerName());

                intent.putExtra("namesOfPlayers", names);
                startActivity(intent);
            }
        });
    }

    private void drawShape(Player player) {

            ShapeView newShape = player.getShape();

            newShape.setLayoutParams(new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            myLayout.addView(newShape);

            myLayout.removeView(lastShape);
            lastShape = newShape;
    }

    private void changePlayer() {
        int currentPlayerIndex = listOfPlayers.indexOf(currentPlayer);

        if (currentPlayerIndex == (listOfPlayers.size() - 1))
            currentPlayer = listOfPlayers.get(0);
        else
            currentPlayer = listOfPlayers.get(currentPlayerIndex + 1);

        playerName.setText(currentPlayer.getPlayerName());
    }
}
