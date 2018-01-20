package com.example.android.tictacgrid.Players;

import android.content.Context;
import com.example.android.tictacgrid.Shapes.ShapeView;

import java.util.ArrayList;

/**
 * Created by pawel on 20.01.18.
 */

public abstract class Player {

    protected Context mContext;
    private int mHowManyInLineToWin = 3;
    private String mPlayerName;
    private ArrayList<int[][]> listOfClickedFields;

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

    public String getPlayerName() {
        return mPlayerName;
    }

    public boolean makeMove(int[][] coords) {
        listOfClickedFields.add(coords);

        if (listOfClickedFields.size() >= mHowManyInLineToWin) {
            return checkIfWon(coords);
        } else {
            return false;
        }
    }

    private boolean checkIfWon(int[][] coords) {

        if(checkVertical(coords))
            return true;
        else if(checkHorizontal(coords))
            return true;
        else if(checkDiagonal(coords))
            return true;
        else
            return false;
    }

    private boolean checkVertical(int[][] coords) {
        return false;
    }

    private boolean checkHorizontal(int[][] coords) {
        return false;
    }

    private boolean checkDiagonal(int[][] coords) {
        return false;
    }

    public void setHowManyInLineToWin (int howMany) {
        mHowManyInLineToWin = howMany;
    }

}
