package com.example.android.tictacgrid.Game;

/**
 * Created by pawel on 11.03.18.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;

import com.example.android.tictacgrid.Shapes.ShapeEmpty;
import com.example.android.tictacgrid.Shapes.ShapeView;

import java.util.ArrayList;

/**
 * Created by pawel on 11.03.18.
 */

public class GameGrid implements Grid {

    final int GRID_FRAME_WIDTH = 3;

    private int mNumOfGridColumns;
    private int mNumOfGridRows;
    private int mClickedFieldsCounter = 0;
    private boolean mIsGameFinished = false;
    private ShapeView[] mCurrentViewsInGameGrid;

    private Context mContext;
    private GridLayout mGameGridLayout;
    private GridLayout.LayoutParams mGameGridLayoutParams;

    private Game mGame;

    public GameGrid (Context context, GridLayout gameGridLayout, int numOfCols, int numOfRows) {
        this.mContext = context;
        this.mNumOfGridColumns = numOfCols;
        this.mNumOfGridRows = numOfRows;
        this.mGameGridLayout = gameGridLayout;

        buildGrid();
    }

    public void buildGrid() {
        mGameGridLayout.setColumnCount(mNumOfGridColumns);
        mGameGridLayout.setRowCount(mNumOfGridRows);

        mCurrentViewsInGameGrid = new ShapeView[mNumOfGridColumns * mNumOfGridRows];

        for (int coordY= 0; coordY < mNumOfGridRows; coordY++) {
            for (int coordX = 0; coordX < mNumOfGridColumns; coordX++) {

                final int[] coords = {coordX, coordY};

                ShapeView currentView = new ShapeEmpty(mContext);
                mCurrentViewsInGameGrid[(coordY * mNumOfGridColumns) + coordX] = currentView;
                mGameGridLayout.addView(currentView);
                setFieldListener(coords);
            }
        }

        mGameGridLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        int gameGridWidth = mGameGridLayout.getWidth();
                        int gameGridHeight = mGameGridLayout.getHeight();
                        int gameGridCellWidth = gameGridWidth / mNumOfGridColumns;
                        int gameGridCellHeight = gameGridHeight / mNumOfGridRows;

                        for (int coordY = 0; coordY < mNumOfGridRows; coordY++) {
                            for (int coordX = 0; coordX < mNumOfGridColumns; coordX++) {

                                GridLayout.LayoutParams params =
                                        (GridLayout.LayoutParams) mCurrentViewsInGameGrid[
                                                (coordY * mNumOfGridColumns) + coordX].getLayoutParams();

                                params.width = gameGridCellWidth - (2 * GRID_FRAME_WIDTH);
                                params.height = gameGridCellHeight - (2 * GRID_FRAME_WIDTH);
                                params.setMargins(
                                        GRID_FRAME_WIDTH, GRID_FRAME_WIDTH, GRID_FRAME_WIDTH, GRID_FRAME_WIDTH);

                                mCurrentViewsInGameGrid[
                                        (coordY * mNumOfGridColumns) + coordX].setLayoutParams(params);
                            }
                        }
                    }
                }
        );
    }

    private void setFieldListener(final int[] coords) {
        mGameGridLayout.getChildAt((mNumOfGridColumns * coords[1]) + coords[0]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mIsGameFinished) {
                    addNewShape(coords);
                    if (mGame.getCurrentPlayer().checkMove(coords)) {
                        mGame.finishGame(true);
                    } else {
                        if (mClickedFieldsCounter == mNumOfGridColumns * mNumOfGridRows) {
                            mGame.finishGame(false);
                        } else {
                            mGame.changePlayer();
                        }
                    }
                }
            }
        });
    }

    private void addNewShape(int[] coords) {

        int clickedFieldIndex = (mNumOfGridColumns * coords[1]) + coords[0];
        setLayoutParamsForNewShape(clickedFieldIndex);

        ShapeView newShape = mGame.getCurrentPlayer().getShape(mIsGameFinished);
        newShape.setLayoutParams(mGameGridLayoutParams);

        mCurrentViewsInGameGrid[clickedFieldIndex] = newShape;

        mGameGridLayout.removeView(mGameGridLayout.getChildAt(clickedFieldIndex));
        mGameGridLayout.addView(newShape, clickedFieldIndex, mGameGridLayoutParams);

        mClickedFieldsCounter++;
    }

    private void setLayoutParamsForNewShape(int fieldIndex) {

        mGameGridLayoutParams =
                (GridLayout.LayoutParams) mCurrentViewsInGameGrid[fieldIndex].getLayoutParams();

        int gameGridWidth = mGameGridLayout.getWidth();
        int gameGridHeight = mGameGridLayout.getHeight();
        int gameGridCellWidth = gameGridWidth / mNumOfGridColumns;
        int gameGridCellHeight = gameGridHeight / mNumOfGridRows;

        mGameGridLayoutParams.width = gameGridCellWidth - (2 * GRID_FRAME_WIDTH);
        mGameGridLayoutParams.height = gameGridCellHeight - (2 * GRID_FRAME_WIDTH);
        mGameGridLayoutParams.setMargins(
                GRID_FRAME_WIDTH, GRID_FRAME_WIDTH, GRID_FRAME_WIDTH, GRID_FRAME_WIDTH);
    }

    public void setGame(Game game) {
        this.mGame = game;
        mGame.setGrid(this);
    }

    @Override
    public void simulateClick(int fieldIndex) {
        mGameGridLayout.getChildAt(fieldIndex).callOnClick();
    }

    @Override
    public void clearGrid() {
        setIsGameFinished(false);
        setClickedFieldsCounter(0);
        mGameGridLayout.removeAllViews();
        buildGrid();
    }

    @Override
    public void setIsGameFinished(boolean isGameFinished) {
        this.mIsGameFinished = isGameFinished;
    }

    @Override
    public void setClickedFieldsCounter(int newCounterValue) {
        this.mClickedFieldsCounter = newCounterValue;
    }

    public ShapeView[] getCurrentViewsInGameGrid() {
        return mCurrentViewsInGameGrid;
    }

    @Override
    public void markWinningShapes() {
        ArrayList<int[]> listOfWinningFields = mGame.getCurrentPlayer().getListOfWinningFields();

        for (int[] currentCoords : listOfWinningFields) {
            addNewShape(currentCoords);
        }
    }
}
