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
    private int mHowManyInLineToWin;
    protected int mScore = 0;
    private String mPlayerName;

    private ArrayList<int[]> mListOfClickedFields;
    private ArrayList<int[]> mListOfPotentiallyWinningFields;
    private ArrayList<int[]> mListOfWinningFields;

    public Player(Context context) {
        this.mContext = context;
        this.mListOfClickedFields = new ArrayList<>();
    }

    public Player(Context context, String name) {
        this.mContext = context;
        this.mPlayerName = name;
        this.mListOfClickedFields = new ArrayList<>();
        this.mListOfWinningFields = new ArrayList<>();
    }

    public abstract ShapeView getShape(boolean hasWon);

//    public abstract ShapeView getWinningShape();

    public String getPlayerName() {
        return mPlayerName;
    }

    public ArrayList<int[]> getListOfWinningFields() {
        return mListOfWinningFields;
    }

    public int getScore() {
        return mScore;
    }

    public void incrementScore() {
        mScore++;
    }

    public void setHowManyInLineToWin (int howMany) {
        mHowManyInLineToWin = howMany;
    }

    public void resetClickedFieldsState() {
        mListOfClickedFields = new ArrayList<>();
        mListOfPotentiallyWinningFields = new ArrayList<>();
        mListOfWinningFields = new ArrayList<>();
    }

    public boolean makeMove(int[] coords) {
        mListOfClickedFields.add(coords);

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

        for (int currentValueX : xValuesForWhichCheckYs) {

            ArrayList<Integer> yValuesToCheck = new ArrayList<>();

            for (int[] currentCoords : mListOfClickedFields) {
                if (currentCoords[0] == currentValueX && !xValuesAlreadyChecked.contains(currentValueX)) {
                    yValuesToCheck.add(currentCoords[1]);
                }
            }

            Collections.sort(yValuesToCheck);
            mListOfWinningFields = new ArrayList<>();
            int howManyConsecutiweYs = 0;
            for (int i = 1; i < yValuesToCheck.size(); ++i) {
                if (yValuesToCheck.get(i) == (yValuesToCheck.get(i - 1) + 1)) {

                    int[] potentiallyWinningPoint = {currentValueX, yValuesToCheck.get(i - 1)};
                    mListOfWinningFields.add(potentiallyWinningPoint);

                    howManyConsecutiweYs++;
                    if (howManyConsecutiweYs == (mHowManyInLineToWin - 1)) {

                        int[] lastWinningPoint = {currentValueX, yValuesToCheck.get(i)};
                        mListOfWinningFields.add(lastWinningPoint);
                        return true;
                    }
                } else {
                    howManyConsecutiweYs = 0;
                    mListOfWinningFields = new ArrayList<>();
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
            mListOfWinningFields = new ArrayList<>();
            int howManyConsecutiweXs = 0;
            for (int i = 1; i < xValuesToCheck.size(); ++i) {
                if (xValuesToCheck.get(i) == (xValuesToCheck.get(i - 1) + 1)) {

                    int[] potentiallyWinningPoint = {xValuesToCheck.get(i - 1), currentValueY};
                    mListOfWinningFields.add(potentiallyWinningPoint);

                    howManyConsecutiweXs++;
                    if (howManyConsecutiweXs == (mHowManyInLineToWin - 1)) {

                        int[] lastWinningPoint = {xValuesToCheck.get(i), currentValueY};
                        mListOfWinningFields.add(lastWinningPoint);
                        return true;
                    }
                } else {
                    howManyConsecutiweXs = 0;
                    mListOfWinningFields = new ArrayList<>();
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal() {

        ArrayList<Integer> listOfAllXs = new ArrayList<>();
        for (int[] currentCoords : mListOfClickedFields) {
            listOfAllXs.add(currentCoords[0]);
        }

        Collections.sort(listOfAllXs);

        ArrayList<Integer> listOfIndividualXs = new ArrayList<>();
        listOfIndividualXs.add(listOfAllXs.get(0));
        for (int i = 1; i < listOfAllXs.size(); ++i) {
            if (listOfAllXs.get(i) != listOfAllXs.get(i-1)) {
                listOfIndividualXs.add(listOfAllXs.get(i));
            }
        }

        ArrayList<Integer> xValuesForWhichCheckYs = new ArrayList<>();

        ArrayList<Integer> listOfConsecutiveXs = new ArrayList<>();
        listOfConsecutiveXs.add(listOfIndividualXs.get(0));
        for (int i = 1; i < listOfIndividualXs.size(); ++i) {
            if (listOfIndividualXs.get(i) == (listOfIndividualXs.get(i - 1) + 1)) {
                listOfConsecutiveXs.add(listOfIndividualXs.get(i));
                if (listOfConsecutiveXs.size() >= mHowManyInLineToWin) {
                    if (xValuesForWhichCheckYs.contains(listOfIndividualXs.get(i - 1))) {
                        xValuesForWhichCheckYs.add(listOfIndividualXs.get(i));
                    } else {
                        for (int currentX : listOfConsecutiveXs) {
                            xValuesForWhichCheckYs.add(currentX);
                        }
                    }
                }
            } else {
                listOfConsecutiveXs = new ArrayList<>();
                if (listOfIndividualXs.size() - 1 > i) {
                    listOfConsecutiveXs.add(listOfIndividualXs.get(i));
                }
            }
        }

        mListOfWinningFields = new ArrayList<>();

        for (int i = 0; i < xValuesForWhichCheckYs.size(); ++i) {

            ArrayList<Integer> listOfAllYsForCurrentX = new ArrayList<>();
            for (int[] currentCoords : mListOfClickedFields) {
                if (currentCoords[0] == xValuesForWhichCheckYs.get(i)) {
                    listOfAllYsForCurrentX.add(currentCoords[1]);
                }
            }

            for (int currentlyCheckedY : listOfAllYsForCurrentX) {

                int conditionCounter = 0;
                int[] firstPotentiallyWinningPoint = {xValuesForWhichCheckYs.get(i), currentlyCheckedY};

                mListOfPotentiallyWinningFields = new ArrayList<>();
                mListOfPotentiallyWinningFields.add(firstPotentiallyWinningPoint);

                if (checkForwardDiagonal(i, currentlyCheckedY, xValuesForWhichCheckYs, conditionCounter)) {
                    return true;
                }

                if (checkBackwardDiagonal(i, currentlyCheckedY, xValuesForWhichCheckYs, conditionCounter)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkForwardDiagonal(
            int currentIndex,
            int currentlyCheckedY,
            ArrayList<Integer> xValuesForWhichCheckYs,
            int conditionCounter) {
        ArrayList<Integer> listOfAllYsForNextX = new ArrayList<>();
        for (int[] currentCoords : mListOfClickedFields) {
            if ( currentIndex < (xValuesForWhichCheckYs.size() - 1) && currentCoords[0] == xValuesForWhichCheckYs.get(currentIndex + 1)) {
                listOfAllYsForNextX.add(currentCoords[1]);
            }
        }

        for (int currentlyCheckedNextY : listOfAllYsForNextX) {

            if (currentlyCheckedNextY == currentlyCheckedY + 1) {
                conditionCounter++;
                int[] potentiallyWinningField = {xValuesForWhichCheckYs.get(currentIndex + 1), currentlyCheckedNextY};
                mListOfPotentiallyWinningFields.add(potentiallyWinningField);
                if (conditionCounter == mHowManyInLineToWin - 1) {
                    mListOfWinningFields = mListOfPotentiallyWinningFields;
                    return true;
                }
                if (checkForwardDiagonal(currentIndex + 1, currentlyCheckedNextY, xValuesForWhichCheckYs, conditionCounter)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkBackwardDiagonal(
            int currentIndex,
            int currentlyCheckedY,
            ArrayList<Integer> xValuesForWhichCheckYs,
            int conditionCounter) {
        ArrayList<Integer> listOfAllYsForNextX = new ArrayList<>();
        for (int[] currentCoords : mListOfClickedFields) {
            if ( currentIndex < (xValuesForWhichCheckYs.size() - 1) && currentCoords[0] == xValuesForWhichCheckYs.get(currentIndex + 1)) {
                listOfAllYsForNextX.add(currentCoords[1]);
            }
        }

        for (int currentlyCheckedNextY : listOfAllYsForNextX) {

            if (currentlyCheckedNextY == currentlyCheckedY - 1) {
                conditionCounter++;
                int[] potentiallyWinningField = {xValuesForWhichCheckYs.get(currentIndex + 1), currentlyCheckedNextY};
                mListOfPotentiallyWinningFields.add(potentiallyWinningField);
                if (conditionCounter == mHowManyInLineToWin - 1) {
                    mListOfWinningFields = mListOfPotentiallyWinningFields;
                    return true;
                }
                if (checkBackwardDiagonal(currentIndex + 1, currentlyCheckedNextY, xValuesForWhichCheckYs, conditionCounter)) {
                    return true;
                }
            }
        }
        return false;
    }
}
