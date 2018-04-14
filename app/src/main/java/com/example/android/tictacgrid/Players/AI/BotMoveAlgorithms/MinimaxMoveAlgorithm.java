package com.example.android.tictacgrid.Players.AI.BotMoveAlgorithms;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by pawel on 04.02.18.
 */

public class MinimaxMoveAlgorithm implements MoveAlgorithm {

    private static final int MAX_DEPTH = 7;

    private static final int VALUE_OF_BOT = 1;
    private static final int VALUE_OF_PLAYER = -1;

    private static final int INITIAL_VALUE_OF_ALPHA = (int) Float.NEGATIVE_INFINITY;
    private static final int INITIAL_VALUE_OF_BETA = (int) Float.POSITIVE_INFINITY;

    @Override
    public int move(int[] currentStateOfGameGrid) {
//        return botMove(currentStateOfGameGrid, 0, INITIAL_VALUE_OF_ALPHA, INITIAL_VALUE_OF_BETA);
        return performMinimax(currentStateOfGameGrid, 1, 0, INITIAL_VALUE_OF_ALPHA, INITIAL_VALUE_OF_BETA);
    }

    private int botMove(int[] stateOfGameGrid, int depth, int alpha, int beta) {

        int a = alpha;
        int b = beta;

        // List of empty fields (possible moves)
        ArrayList<Integer> listOfEmptyFields = new ArrayList<>();
        ArrayList<Integer> listOfScores = new ArrayList<>();

        // Traverses through current state of game grid (sent as a parameter)
        // and looks for empty fields (fields with value 0)
        for (int i = 0; i < stateOfGameGrid.length; ++i) {
            if (stateOfGameGrid[i] == 0) {
                listOfEmptyFields.add(i);
                listOfScores.add((int) Float.NEGATIVE_INFINITY);
            }
        }

        // Checks every possible move
        for (int i = 0; i < listOfEmptyFields.size(); ++i) {
            int checkedMoveIndex = listOfEmptyFields.get(i);
            stateOfGameGrid[checkedMoveIndex] = VALUE_OF_BOT;

            if (checkIfWon(stateOfGameGrid, VALUE_OF_BOT)) {
                int score = (VALUE_OF_BOT * 10) - depth;
                listOfScores.add(i, (score + listOfScores.get(i)));
                listOfScores.remove(i + 1);

                if (depth > 0) {
                    return listOfScores.get(i);
                } else {
                    return listOfEmptyFields.get(i);
                }
            } else if (listOfEmptyFields.size() > 1) {

                int bestEnemysMove = playerMove(stateOfGameGrid, depth + 1, a, b);

                if (bestEnemysMove > listOfScores.get(i)) {
                    listOfScores.add(i, (bestEnemysMove + listOfScores.get(i)));
                    listOfScores.remove(i + 1);
                }

                if (bestEnemysMove >= b) {
                    if (depth > 0) {
                        return listOfScores.get(i);
                    } else {
                        return listOfEmptyFields.get(i);
                    }
                }

                if (bestEnemysMove > a) {
                    a = bestEnemysMove;
                }
            } else {
                listOfScores.add(i, 0 + listOfScores.get(i));
                listOfScores.remove(i + 1);
            }

            stateOfGameGrid[checkedMoveIndex] = 0;
        }

        int bestScore = 0;
        if (!listOfScores.isEmpty()) {
            bestScore = Collections.max(listOfScores);
        }

        if (depth > 0) {
            return bestScore;
        } else {
            Log.e("HARD BOT", "Returned field: " + listOfEmptyFields.get(listOfScores.indexOf(bestScore)));
            Log.e("HARD BOT", "Field's best score: " + bestScore);
            Log.e("HARD BOT", "From list of scores: ");
            for (int i = 0; i < listOfEmptyFields.size(); ++i) {
                Log.e("HARD BOT", "Field: " + listOfEmptyFields.get(i) + ", score: " + listOfScores.get(i));

            }

            return listOfEmptyFields.get(listOfScores.indexOf(bestScore));
        }
    }

    private int playerMove(int[] stateOfGameGrid, int depth, int alpha, int beta) {
        int a = alpha;
        int b = beta;

        // List of empty fields (possible moves)
        ArrayList<Integer> listOfEmptyFields = new ArrayList<>();
        ArrayList<Integer> listOfScores = new ArrayList<>();

        // Traverses through current state of game grid (sent as a parameter)
        // and looks for empty fields (fields with value 0)
        for (int i = 0; i < stateOfGameGrid.length; ++i) {
            if (stateOfGameGrid[i] == 0) {
                listOfEmptyFields.add(i);
                listOfScores.add((int) Float.POSITIVE_INFINITY);
            }
        }

        // Checks every possible move
        for (int i = 0; i < listOfEmptyFields.size(); ++i) {
            int checkedMoveIndex = listOfEmptyFields.get(i);
            stateOfGameGrid[checkedMoveIndex] = VALUE_OF_PLAYER;

            if (checkIfWon(stateOfGameGrid, VALUE_OF_PLAYER)) {
                int score = (VALUE_OF_PLAYER * 10) + depth;
                listOfScores.add(i, (score + listOfScores.get(i)));
                listOfScores.remove(i + 1);

//                Log.e("HARD BOT", " [ifWon] Player returned: " + listOfScores.get(i) + ", depth: " + depth);
                return listOfScores.get(i);
            } else if (listOfEmptyFields.size() > 1) {

                int bestEnemysMove = botMove(stateOfGameGrid, depth + 1, a, b);

                if (bestEnemysMove < listOfScores.get(i)) {
                    listOfScores.add(i, (bestEnemysMove + listOfScores.get(i)));
                    listOfScores.remove(i + 1);
                }

                if (bestEnemysMove <= a) {
//                    Log.e("HARD BOT", " [bestEnemysMove <= a] Player returned: " + listOfScores.get(i) + ", depth: " + depth);
                    return listOfScores.get(i);
                }

                if (bestEnemysMove < b) {
                    b = bestEnemysMove;
                }
            } else {
                listOfScores.add(i, (0 + listOfScores.get(i)));
                listOfScores.remove(i + 1);
            }
            stateOfGameGrid[checkedMoveIndex] = 0;
        }

        int bestScore = 0;
        if (!listOfScores.isEmpty()) {
            bestScore = Collections.min(listOfScores);
        }

//        Log.e("HARD BOT", " [finalReturn] Player returned: " + bestScore + ", depth: " + depth);
        return bestScore;
    }

    private int performMinimax(
            int[] currentStateOfGameGrid, int currentPlayer, int depth, int alpha, int beta) {

        int a = alpha;
        int b = beta;

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


            if (checkIfWon(currentStateOfGameGrid, currentPlayer)) {
                if (currentPlayer == 1) {
                    listOfScores.add(i, (listOfScores.get(i) + 10) - depth);
                } else if (currentPlayer == -1) {
                    listOfScores.add(i, (listOfScores.get(i) - 10) + depth);
                }
            } else if (listOfEmptyFields.size() > 1 && depthOfMinimax <= MAX_DEPTH) {
                listOfScores.add(
                        i, ((listOfScores.get(i) + performMinimax(
                                        currentStateOfGameGrid,
                                        -currentPlayer,
                                        depthOfMinimax + 1,
                                        alpha,
                                        beta))));
            } else {
                listOfScores.add(i, (listOfScores.get(i) - 0));
            }

            currentStateOfGameGrid[checkedMoveIndex] = 0;
        }

        if (listOfScores.isEmpty()) {
            return 0;
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

                Log.e("HARD BOT", "Returned field: " + listOfEmptyFields.get(listOfScores.indexOf(highestScore)));
                Log.e("HARD BOT", "Field's best score: " + highestScore);
                Log.e("HARD BOT", "From list of scores: ");
                for (int i = 0; i < listOfEmptyFields.size(); ++i) {
                    Log.e("HARD BOT", "Field: " + listOfEmptyFields.get(i) + ", score: " + listOfScores.get(i));

                }

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
}
