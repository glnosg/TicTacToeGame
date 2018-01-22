package com.example.android.tictacgrid.Players;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.example.android.tictacgrid.Shapes.ShapeView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by pawel on 20.01.18.
 */

public abstract class Player {

    protected Context mContext;
    private int mHowManyInLineToWin = 3;
    private String mPlayerName;
    private ArrayList<int[]> mListOfClickedFields;
    private ArrayList<int[]> mListOfWinningFields;

    public Player(Context context) {
        mContext = context;
        mListOfClickedFields = new ArrayList<>();
    }

    public Player(Context context, String name) {
        mContext = context;
        mPlayerName = name;
        mListOfClickedFields = new ArrayList<>();
        mListOfWinningFields = new ArrayList<>();
    }

    public abstract ShapeView getShape(boolean hasWon);

//    public abstract ShapeView getWinningShape();

    public String getPlayerName() {
        return mPlayerName;
    }

    public ArrayList<int[]> getListOfWinningFields() {
        return mListOfWinningFields;
    }

    public void setHowManyInLineToWin (int howMany) {
        mHowManyInLineToWin = howMany;
    }

    public boolean makeMove(int[] coords) {
        mListOfClickedFields.add(coords);
        Log.d(this.getPlayerName(), " made move");

        if (mListOfClickedFields.size() >= mHowManyInLineToWin) {
            return checkIfWon();
        } else {
            return false;
        }
    }

    private boolean checkIfWon() {

        if(checkVertical())
            return true;
        else if(checkHorizontal())
            return true;
        else if(checkDiagonal())
            return true;
        else
            return false;
    }

    private boolean checkVertical() {

        ArrayList<Integer> allXValues = new ArrayList<>();

        for(int[] currentCoords : mListOfClickedFields) {
            allXValues.add(currentCoords[0]);
        }

        ArrayList<Integer> xValuesForWhichCheckYs = new ArrayList<>();
        Collections.sort(allXValues);

        int howManySameXsInRow = 0;

        for (int i = 1; i < allXValues.size(); ++i){

            if (allXValues.get(i) == allXValues.get(i - 1)) {
                howManySameXsInRow++;
                if (howManySameXsInRow == (mHowManyInLineToWin - 1)) {
                    xValuesForWhichCheckYs.add(allXValues.get(i));
                    howManySameXsInRow = 0;
                }
            }
            else {
                howManySameXsInRow = 0;
            }

            if (i == (allXValues.size() - 1) && (xValuesForWhichCheckYs.isEmpty()))
                return false;
        }

        ArrayList<IntentFilter> xValuesAlreadyChecked = new ArrayList<>();

        for (int currentValueY : xValuesForWhichCheckYs) {

            ArrayList<Integer> yValuesToCheck = new ArrayList<>();

            for (int[] currentCoords : mListOfClickedFields) {
                if (currentCoords[0] == currentValueY && !xValuesAlreadyChecked.contains(currentValueY)) {
                    yValuesToCheck.add(currentCoords[1]);
                }
            }

            Collections.sort(yValuesToCheck);
            int howManyConsecutiweYs = 0;
            for (int i = 1; i < yValuesToCheck.size(); ++i) {
                if (yValuesToCheck.get(i) == (yValuesToCheck.get(i - 1) + 1)) {
                    howManyConsecutiweYs++;
                    if (howManyConsecutiweYs == (mHowManyInLineToWin - 1)) {
                        return true;
                    }
                } else {
                    howManyConsecutiweYs = 0;
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal() {

        ArrayList<Integer> allYvalues = new ArrayList<>();

        for(int[] currentCoords : mListOfClickedFields) {
            allYvalues.add(currentCoords[1]);
        }

        ArrayList<Integer> yValuesForWhichCheckXs = new ArrayList<>();
        Collections.sort(allYvalues);

        int howManySameYsInRow = 0;

        for (int i = 1; i < allYvalues.size(); ++i){

            if (allYvalues.get(i) == allYvalues.get(i - 1)) {
                howManySameYsInRow++;
                if (howManySameYsInRow == (mHowManyInLineToWin - 1)) {
                    yValuesForWhichCheckXs.add(allYvalues.get(i));
                    howManySameYsInRow = 0;
                }
            }
            else {
                howManySameYsInRow = 0;
            }

            if (i == (allYvalues.size() - 1) && (yValuesForWhichCheckXs.isEmpty()))
                return false;
        }

        ArrayList<IntentFilter> yValuesAlreadyChecked = new ArrayList<>();

        for (int currentValueY : yValuesForWhichCheckXs) {

            ArrayList<Integer> xValuesToCheck = new ArrayList<>();

            for (int[] currentCoords : mListOfClickedFields) {
                if (currentCoords[1] == currentValueY && !yValuesAlreadyChecked.contains(currentValueY)) {
                    xValuesToCheck.add(currentCoords[0]);
                }
            }

            Collections.sort(xValuesToCheck);
            int howManyConsecutiweXs = 0;
            for (int i = 1; i < xValuesToCheck.size(); ++i) {
                if (xValuesToCheck.get(i) == (xValuesToCheck.get(i - 1) + 1)) {
                    howManyConsecutiweXs++;
                    if (howManyConsecutiweXs == (mHowManyInLineToWin - 1)) {
                        return true;
                    }
                } else {
                    howManyConsecutiweXs = 0;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        return false;
    }
}
