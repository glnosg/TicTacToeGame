package com.example.android.tictacgrid.Players;

import android.content.Context;
import android.content.IntentFilter;

import com.example.android.tictacgrid.Shapes.ShapeO;
import com.example.android.tictacgrid.Shapes.ShapeSquare;
import com.example.android.tictacgrid.Shapes.ShapeTriangle;
import com.example.android.tictacgrid.Shapes.ShapeView;
import com.example.android.tictacgrid.Shapes.ShapeX;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by pawel on 20.01.18.
 */

public class Player {

    // Holds activity's context - it needs to be sent to Shape object's constructor
    protected Context mContext;
    // Information about number of figures in row needed to win
    private int mHowManyInLineToWin;
    // Number of games won by player
    private int mScore = 0;
    // Player's name
    private String mPlayerName;
    private int mPlayerNumber;

    // List of fields clicked by player in current game
    // Every field is held as int[2] array, where: arr[0] = x coordinate, arr[1] = y coordinate;
    private ArrayList<int[]> mListOfClickedFields;
    // List of potentially winning fields (used in checkDiagonal() method)
    private ArrayList<int[]> mListOfPotentiallyWinningFields;
    // List of winnings fields - needed to mark an appropriate fields
    private ArrayList<int[]> mListOfWinningFields;

    /**
     * @param context - activity's context
     */
    public Player(Context context) {
        this.mContext = context;
        this.mListOfClickedFields = new ArrayList<>();
    }

    /**
     *
     * @param context - activity's context
     * @param name - player's name
     */
    public Player(Context context, String name, int playerNumber) {
        this.mContext = context;
        this.mPlayerName = name;
        this.mPlayerNumber = playerNumber;
        this.mListOfClickedFields = new ArrayList<>();
        this.mListOfWinningFields = new ArrayList<>();
    }

    // Method implemented in concrete player class,
    // it returns subtype of ShapeView class (shape bounded to a specific player)
    public ShapeView getShape(boolean hasWon) {
        ShapeView shape = null;

        switch (mPlayerNumber) {
            case 1:
                shape = new ShapeX(mContext, hasWon);
                break;
            case 2:
                shape = new ShapeO(mContext, hasWon);
                break;
            case 3:
                shape = new ShapeSquare(mContext, hasWon);
                break;
            case 4:
                shape = new ShapeTriangle(mContext, hasWon);
                break;
        }

        return shape;
    }

    // Returns player's name
    public String getPlayerName() {
        return mPlayerName;
    }

    // Returns list of winning fields
    public ArrayList<int[]> getListOfWinningFields() {
        return mListOfWinningFields;
    }

    // Returns number of games won by player
    public int getScore() {
        return mScore;
    }

    // Increments number of games won by player
    public void incrementScore() {
        mScore++;
    }

    // Sets value of mHowManyInLineToWin variable
    public void setHowManyInLineToWin (int howMany) {
        mHowManyInLineToWin = howMany;
    }

    // Cleans list of fields clicked by player
    public void resetClickedFieldsState() {
        mListOfClickedFields = new ArrayList<>();
        mListOfPotentiallyWinningFields = new ArrayList<>();
        mListOfWinningFields = new ArrayList<>();
    }

    // Method triggered when player makes a move
    public boolean checkMove(int[] coords) {
        // New coords are added to list of clicked fields
        mListOfClickedFields.add(coords);

        // Method which checks if user won after last move. There's no need to call it
        // when number of moves is lower than number of figures in row needed to win
        // It returns true if player won in that move and false if he didn't.
        if (mListOfClickedFields.size() >= mHowManyInLineToWin) {
            return checkIfWon();
        } else {
            return false;
        }
    }

    // Three methods are called:
    // checkVertical() - checks if there's enough shapes in row to won vertically
    // checkHorizontal() - checks if there's enough shapes in row to won horizontally
    // checkDiagonal() - checks if there's enough shapes in row to won in diagonal
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

        // Holds x coordinates of all clicked values
        ArrayList<Integer> allXValues = new ArrayList<>();

        // Traverses list of all clicked fields and assigns them to allXValues list
        for(int[] currentCoords : mListOfClickedFields) {
            allXValues.add(currentCoords[0]);
        }

        // values in allXValues list are sorted
        Collections.sort(allXValues);
        // If there's at least mHowManyInLineToWin fields with the same value of x coordinates
        // (what means they're in the same line), that value is saved in xValuesForWhichCheckYs list
        ArrayList<Integer> xValuesForWhichCheckYs = new ArrayList<>();

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

            // If there's not at least mHowManyInLineToWin clicked fields
            // located in one vertical line of grid, method returns false
            if (i == (allXValues.size() - 1) && (xValuesForWhichCheckYs.isEmpty()))
                return false;
        }

        ArrayList<IntentFilter> xValuesAlreadyChecked = new ArrayList<>();

        for (int currentValueX : xValuesForWhichCheckYs) {

            // Holds all values of y coordinates for fields with currently checked x coordinate
            ArrayList<Integer> yValuesToCheck = new ArrayList<>();

            for (int[] currentCoords : mListOfClickedFields) {
                if (currentCoords[0] == currentValueX && !xValuesAlreadyChecked.contains(currentValueX)) {
                    yValuesToCheck.add(currentCoords[1]);
                }
            }

            // Values in the list are sorted
            Collections.sort(yValuesToCheck);
            mListOfWinningFields = new ArrayList<>();
            // Holds number of consecutive y coordinates for currently checked x coordinate
            int howManyConsecutiweYs = 0;
            for (int i = 1; i < yValuesToCheck.size(); ++i) {
                if (yValuesToCheck.get(i) == (yValuesToCheck.get(i - 1) + 1)) {

                    int[] potentiallyWinningPoint = {currentValueX, yValuesToCheck.get(i - 1)};
                    mListOfWinningFields.add(potentiallyWinningPoint);

                    howManyConsecutiweYs++;
                    if (howManyConsecutiweYs == (mHowManyInLineToWin - 1)) {

                        // If there's at least mHowmanyInLineToWin consecutive y coordinates
                        // for a single x coordinate method returns true.
                        int[] lastWinningPoint = {currentValueX, yValuesToCheck.get(i)};
                        mListOfWinningFields.add(lastWinningPoint);
                        return true;
                    }
                } else {
                    // If sequence of at least mHowManyInLineToWin consecutive y coordinates
                    // for currently checked y is not found,
                    // value of howManyConsecutiveYs is reseted and method starts checking next x
                    howManyConsecutiweYs = 0;
                    mListOfWinningFields = new ArrayList<>();
                }
            }
        }
        return false;
    }

    // It works like checkVertical method (x and y values are changed)
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

        // First x coordinates od all clicked fields are gathered in listOfAllXs and sorted
        ArrayList<Integer> listOfAllXs = new ArrayList<>();
        for (int[] currentCoords : mListOfClickedFields) {
            listOfAllXs.add(currentCoords[0]);
        }

        Collections.sort(listOfAllXs);

        // listOfIndividualXs is filled with individual values of x coordinates
        ArrayList<Integer> listOfIndividualXs = new ArrayList<>();
        listOfIndividualXs.add(listOfAllXs.get(0));
        for (int i = 1; i < listOfAllXs.size(); ++i) {
            if (listOfAllXs.get(i) != listOfAllXs.get(i-1)) {
                listOfIndividualXs.add(listOfAllXs.get(i));
            }
        }


        // All sequences of consecutive x coordinates with at least mHowManyInLineToWin elements
        // are assigned to xValuesForWhichCheckYs list
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

            // Holds all values of y coordinates for fields with currently checked x coordinate
            ArrayList<Integer> listOfAllYsForCurrentX = new ArrayList<>();
            for (int[] currentCoords : mListOfClickedFields) {
                if (currentCoords[0] == xValuesForWhichCheckYs.get(i)) {
                    listOfAllYsForCurrentX.add(currentCoords[1]);
                }
            }

            for (int currentlyCheckedY : listOfAllYsForCurrentX) {


                // Each y coordinate for currently checked x is checked with recursive methods
                // checkForwardDiagonal() and checkBackwardDiagonal()
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

        // Holds list of all y coordinates for fields
        // with x coordinate which is next on list of all x coordinates to check
        ArrayList<Integer> listOfAllYsForNextX = new ArrayList<>();
        for (int[] currentCoords : mListOfClickedFields) {
            if ( currentIndex < (xValuesForWhichCheckYs.size() - 1) && currentCoords[0] == xValuesForWhichCheckYs.get(currentIndex + 1)) {
                listOfAllYsForNextX.add(currentCoords[1]);
            }
        }

        // Method checks each y coordinate, and when it finds a field
        // for which exists nieghboring field whith x value increased by one and y value decreased by one
        // it increments conditionCounterValue.
        // if conditionCounter value is still less than needed, that next field is checked with the same method.
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

    // Principle of operation is the same like in checkForwardDiagonal() method
    // (it checks if there's next field with y increased bo one instead of decreased by one)
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
