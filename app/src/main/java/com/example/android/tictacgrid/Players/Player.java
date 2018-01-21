package com.example.android.tictacgrid.Players;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.example.android.tictacgrid.Shapes.ShapeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by pawel on 20.01.18.
 */

public abstract class Player {

    protected Context mContext;
    private int mHowManyInLineToWin = 3;
    private String mPlayerName;
    private ArrayList<int[]> listOfClickedFields;

    public Player(Context context) {
        mContext = context;
        listOfClickedFields = new ArrayList<>();
    }

    public Player(Context context, String name) {
        mContext = context;
        mPlayerName = name;
        listOfClickedFields = new ArrayList<>();
    }

    public abstract ShapeView getShape();

    public abstract ShapeView getWinningShape();

    public String getPlayerName() {
        return mPlayerName;
    }

    public boolean makeMove(int[] coords) {
        listOfClickedFields.add(coords);
        Log.d(this.getPlayerName(), " made move");

        if (listOfClickedFields.size() >= mHowManyInLineToWin) {
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

        ArrayList<Integer> allYvalues = new ArrayList<>();

        for(int[] currentCoords : listOfClickedFields) {
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

            for (int[] currentCoords : listOfClickedFields) {
                if (currentCoords[1] == currentValueY && !yValuesAlreadyChecked.contains(currentValueY)) {
                    xValuesToCheck.add(currentCoords[0]);
                }
            }

            Collections.sort(xValuesToCheck);
            int howManyConsecutiweXs = 0;
            for (int i = 1; i < xValuesToCheck.size(); ++i) {
                if (xValuesToCheck.get(i) == (xValuesToCheck.get(i - 1) + 1)) {
                    howManyConsecutiweXs++;
                    if (howManyConsecutiweXs == (howManyConsecutiweXs - 1)) {
                        return true;
                    }
                } else {
                    howManyConsecutiweXs = 0;
                }
            }
        }

        return false;
    }

    private boolean checkHorizontal() {
        return false;
    }

    private boolean checkDiagonal() {
        return false;
    }

    public void setHowManyInLineToWin (int howMany) {
        mHowManyInLineToWin = howMany;
    }

}
