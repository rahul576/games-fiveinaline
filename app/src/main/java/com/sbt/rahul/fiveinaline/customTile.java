package com.sbt.rahul.fiveinaline;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

/**
 * Created by Rahul on 30-Mar-16.
 */
public class customTile extends ImageView {
    private byte rowIndex = -1;
    private byte columnIndex = -1;
    private byte tileColor = -1;

    public customTile(Context context) {
        super(context);

    }

    public byte getRowIndex()

    {
        return rowIndex;
    }

    public byte getColumnIndex()

    {
        return columnIndex;
    }

    public void setRowColumnIndex(byte x, byte y) {
       rowIndex=x;
        columnIndex=y;
    }

    public byte getTileColor() {
        return tileColor;
    }

    public void setTileColor(byte color) {

        tileColor = color;
    }
}
