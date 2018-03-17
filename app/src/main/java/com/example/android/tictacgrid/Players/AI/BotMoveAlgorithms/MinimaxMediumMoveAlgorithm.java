package com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by pawel on 04.02.18.
 */

public class MinimaxMediumMoveAlgorithm implements MoveAlgorithm {

    private static final int MAX_DEPTH = 3;
    private static final int PERCENT_OF_CHANCE_FOR_RANDOM_MOVE = 10;

    @Override
    public int move(int[] currentStateOfGameGrid) {



        if ((Math.random() * 100) < PERCENT_OF_CHANCE_FOR_RANDOM_MOVE) {
            return performRandomMove(currentStateOfGameGrid);
        }

        return performMinimax(currentStateOfGameGrid, 1, 0);
    }


    private int performMinimax(int[] currentStateOfGameGrid, int currentPlayer, int depth) {

        int depthOfMinimax = depth;

        ArrayList<Integer> listOfScores = new ArrayList();
        ArrayList<Integer> listOfEmptyFields = new ArrayList<>();

        for (int i = 0; i < currentStateOfGameGrid.length; ++i) {
            if (currentStateOfGameGrid[i] == 0) {
                listOfEmptyFields.add(i);
                listOfScores.add(0);
            }
        }

        for (int i = 0; i < listOfEmptyFields.size(); ++i) {

            int checkedMoveIndex = listOfEmptyFields.get(i);

            currentStateOfGameGrid[checkedMoveIndex] = currentPlayer;

            for (int j = 0; j < currentStateOfGameGrid.length; ++j) {
            }

            if (checkIfWon(currentStateOfGameGrid, currentPlayer)) {
                if (currentPlayer == 1) {
                    listOfScores.add(i, (listOfScores.get(i) + 10));
                } else if (currentPlayer == -1) {
                    listOfScores.add(i, (listOfScores.get(i) - 10));
                }
            } else if (listOfEmptyFields.size() > 1 && depthOfMinimax <= MAX_DEPTH) {
                listOfScores.add(
                        i, ((listOfScores.get(i) + performMinimax(currentStateOfGameGrid, -currentPlayer, depthOfMinimax + 1))));
            } else {
                listOfScores.add(i, (listOfScores.get(i) - 0));
            }

            currentStateOfGameGrid[checkedMoveIndex] = 0;
        }

        while (listOfScores.size() > listOfEmptyFields.size()) {
            listOfScores.remove(listOfEmptyFields.size());
        }

        int highestScore = Collections.max(listOfScores);
        int lowestScore = Collections.min(listOfScores);

        if (currentPlayer == 1) {
            if (depthOfMinimax > 0) {
                return highestScore;
            } else {
                return listOfEmptyFields.get(listOfScores.indexOf(highestScore));
            }
        } else {
            return lowestScore;
        }
    }

    private boolean checkIfWon(int[] stateToCheck, int player) {

        if(
                (stateToCheck[0] == player && stateToCheck[1] == player && stateToCheck[2] == player) ||
                        (stateToCheck[3] == player && stateToCheck[4] == player && stateToCheck[5] == player) ||
                        (stateToCheck[6] == player && stateToCheck[7] == player && stateToCheck[8] == player) ||
                        (stateToCheck[0] == player && stateToCheck[3] == player && stateToCheck[6] == player) ||
                        (stateToCheck[1] == player && stateToCheck[4] == player && stateToCheck[7] == player) ||
                        (stateToCheck[2] == player && stateToCheck[5] == player && stateToCheck[8] == player) ||
                        (stateToCheck[0] == player && stateToCheck[4] == player && stateToCheck[8] == player) ||
                        (stateToCheck[2] == player && stateToCheck[4] == player && stateToCheck[6] == player)) {

            return true;
        } else {

            return false;
        }
    }

    private int performRandomMove(int[] currentStateOfGrid) {
        ArrayList<Integer> emptyFieldsForRandom = new ArrayList<>();

        for (int i = 0; i < currentStateOfGrid.length; ++i) {
            if (currentStateOfGrid[i] == 0) {
                emptyFieldsForRandom.add(i);
            }
        }

        int sizeOfList = emptyFieldsForRandom.size();
        Log.d("Medium Bot", "Random move performed");
        return emptyFieldsForRandom.get((int) Math.random() * sizeOfList);
    }
}