package com.example.android.tictacgrid.Game;

import com.example.android.tictacgrid.Shapes.ShapeView;

/**
 * Created by pawel on 11.03.18.
 */

public interface Grid {

    void buildGrid();
    void simulateClick(int fieldIndex);
    void setIsGameFinished (boolean isGameFinished);
    void setClickedFieldsCounter (int newCounterValue);
    void clearGrid();
    void markWinningShapes();
    ShapeView[] getCurrentViewsInGameGrid();
}
