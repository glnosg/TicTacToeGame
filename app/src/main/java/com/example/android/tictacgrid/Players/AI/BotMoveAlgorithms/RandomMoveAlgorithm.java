package com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms;


import java.util.ArrayList;

/**
 * Created by pawel on 04.02.18.
 */

public class RandomMoveAlgorithm implements MoveAlgorithm {

    @Override
    public int move(int[] currentStateOfGameGrid) {

        ArrayList<Integer> listOfEmptyFields = new ArrayList();

        for (int i = 0; i < currentStateOfGameGrid.length; ++i) {
            if (currentStateOfGameGrid[i] == 0) {
                listOfEmptyFields.add(i);
            }
        }

        int randomIndex = (int) (Math.random() * listOfEmptyFields.size());
        int randomField = listOfEmptyFields.get(randomIndex);

        return randomField;
    }
}
