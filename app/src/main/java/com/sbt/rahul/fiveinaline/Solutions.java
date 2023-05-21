package com.sbt.rahul.fiveinaline;

import java.util.List;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rahul
 */
public class Solutions {



    public static List<byte[]> checkFiveInALine(byte[][] tilesColor, byte[] tile, byte nInaLine) {

        byte tileRow = tile[0];
        byte tileColumn = tile[1];
        byte color = tilesColor[tileRow][tileColumn];
        byte totalRows = (byte) tilesColor.length;
        byte totalColumns = (byte) tilesColor[0].length;
        byte pointIndexdiagonalArrayTopDown = 0;
        byte pointIndexdiagonalArrayBottomU = 0;
        byte top[] = new byte[2];
        byte down[] = new byte[2];
        byte bottom[] = new byte[2];
        byte up[] = new byte[2];
        List<byte[]> trueMatches = new ArrayList<byte[]>();
        byte horizontalArray[], verticalArray[], diagonalArrayTopDown[], diagonalArrayBottomUp[];
        //***get the top of first diagonal
        top[0] = tileRow;
        top[1] = tileColumn;
        while (true) {
            if (top[0] <= 0 || top[1] <= 0) {
                break;
            } else {
                top[0]--;
                top[1]--;
                pointIndexdiagonalArrayTopDown++;
            }

        }
        //first diagonal 1,14

        down[0] = tileRow;
        down[1] = tileColumn;
        while (true) {
            if (down[0] >= totalRows - 1 || down[1] >= totalColumns - 1) {
                break;
            } else {
                down[0]++;
                down[1]++;
            }

        }
        //sedond diagonal
        bottom[0] = tileRow;
        bottom[1] = tileColumn;
        while (true) {
            if (bottom[1] <= 0 || bottom[0] >= totalRows - 1) {
                break;
            } else {
                bottom[0]++;
                bottom[1]--;
                pointIndexdiagonalArrayBottomU++;
            }
        }
        //sedond diagonal

        up[0] = tileRow;
        up[1] = tileColumn;
        while (true) {
            if (up[0] <= 0 || up[1] >= totalColumns - 1) {
                break;
            } else {
                up[0]--;
                up[1]++;
            }
        }

       // System.out.println("Top:" + top[0] + "" + top[1]);
       // System.out.println("Down:" + down[0] + "" + down[1]);
       // System.out.println("Bottom:" + bottom[0] + "" + bottom[1]);
        //System.out.println("UP:" + up[0] + "" + up[1]);

        //size of horizontal array will be equal to the no of column in any row we have choosen row one
        horizontalArray = new byte[tilesColor[0].length];
        //size of verticale array will be equal to the no of rows in array
        verticalArray = new byte[tilesColor.length];
        //the height if the vertical top down array will depend on the selected point row coordinate and iw till be totalRows-RowCoordinateOfPoint since we have to always more down diagonally
        diagonalArrayTopDown = new byte[Math.abs(down[0] - top[0]) + 1];
        diagonalArrayBottomUp = new byte[Math.abs(up[0] - bottom[0]) + 1];

        //***********now we fill get these array filled******************
        System.arraycopy(tilesColor[tileRow], 0, horizontalArray, 0, horizontalArray.length);
        //System.arraycopy(tilesColor[tileColumn], 0, verticalArray, 0, verticalArray.length);
        for (int j = 0; j < verticalArray.length; j++) {
            verticalArray[j] = tilesColor[j][tileColumn];
        }
        for (byte i = top[0], j = top[1], k = 0; k < diagonalArrayTopDown.length; i++, j++, k++) {
            diagonalArrayTopDown[k] = tilesColor[i][j];
        }

        for (byte i = bottom[0], j = bottom[1], k = 0; k < diagonalArrayBottomUp.length; i--, j++, k++) {
            diagonalArrayBottomUp[k] = tilesColor[i][j];
        }

        //<end>***********now we fill get these array filled******************
        byte result[] = new byte[2];
        result = getHorizontalTilesColorsList(tileColumn, color, nInaLine, horizontalArray);
        if (result != null) {
            for (byte i = tileRow, j = result[0]; j <= result[1]; j++) {
                trueMatches.add(new byte[]{i, j});
            }
        }
        result = getHorizontalTilesColorsList(tileRow, color, nInaLine, verticalArray);
        if (result != null) {

            for (byte j = tileColumn, i = result[0]; i <= result[1]; i++) {
                trueMatches.add(new byte[]{i, j});
            }
        }
        result = getHorizontalTilesColorsList(pointIndexdiagonalArrayTopDown, color, nInaLine, diagonalArrayTopDown);
        if (result != null) {
            for (byte i = top[0], j = top[1], index = 0; i <= down[0]; i++, j++, index++) {
                if (index >= result[0] && index <= result[1]) {
                    trueMatches.add(new byte[]{i, j});
                }
            }
        }
        result = getHorizontalTilesColorsList(pointIndexdiagonalArrayBottomU, color, nInaLine, diagonalArrayBottomUp);
        if (result != null) {

            for (byte i = bottom[0], j = bottom[1], index = 0; j <= up[1]; i--, j++, index++) {
                if (index >= result[0] && index <= result[1]) {
                    trueMatches.add(new byte[]{i, j});
                }
            }
        }
        //trueMatches.add(getHorizontalTilesColorsList(tileRow, color, nInaLine, verticalArray));
        //trueMatches.add(getHorizontalTilesColorsList(pointIndexdiagonalArrayTopDown, color, nInaLine, diagonalArrayTopDown));
        //trueMatches.add(getHorizontalTilesColorsList(pointIndexdiagonalArrayBottomU, color, nInaLine, diagonalArrayBottomUp));
        return trueMatches;
    }

    public static byte[] getHorizontalTilesColorsList(byte pointIndexInArray, byte tileColor, byte checkForNConsicutiveSameTiles, byte[] tilesColorsMatrix) {
      // byte row = point[0];
        //byte column = point[1];
        //  byte[][] resultListArray;

        //ArrayList horizontalTilesList = new ArrayList();
        byte firstMatchIndex = -1, lastMatchIndex = -1;
        //get the tile color
        //byte tileColor = tilesColorsMatrix[row][column];
        byte sameColorTilesCount = 0;
        //get the how many tiles are of the same color in a row
        for (byte j = 0; j < tilesColorsMatrix.length; j++) {
            //traverse in that line from index 0-N 
            if (tilesColorsMatrix[j] == tileColor) {
                sameColorTilesCount++;
                //if we got the fist tile of the same color then we will store it because if we get the more then five(N) consicutive tiles then we can return the index of that
                //sequence like tile number 1-6 are of the same color
                if (sameColorTilesCount == 1) {
                    firstMatchIndex = j;
                } else if (sameColorTilesCount >= checkForNConsicutiveSameTiles) {
                    lastMatchIndex = j;
                }

            } else {
                //check if we have a range a-b where total no of elements in the range will be more then 5 or checkForNConsicutiveSameTiles
                //also check that tile we want should be in this range a-b because there may be a case if computer prints the five consicutive lines so last condition below if statement
                //checks that
                if (firstMatchIndex != -1 && (lastMatchIndex - firstMatchIndex + 1) >= checkForNConsicutiveSameTiles && (pointIndexInArray >= firstMatchIndex && pointIndexInArray <= lastMatchIndex)) {
                    //if we have got such a range we can return it 
                   // System.out.println("Range : " + firstMatchIndex + " " + lastMatchIndex);
                    return new byte[]{firstMatchIndex, lastMatchIndex};
                } else {
                    //we have to reset the counters 
                    firstMatchIndex = -1;
                    lastMatchIndex = -1;
                    sameColorTilesCount = 0;
                }
            }
        }
        //below if statement in checks that if in any array we get the match will the last index 
        if (firstMatchIndex != -1 && (lastMatchIndex - firstMatchIndex + 1) >= checkForNConsicutiveSameTiles && (pointIndexInArray >= firstMatchIndex && pointIndexInArray <= lastMatchIndex)) {
            //if we have got such a range we can return it 
           // System.out.println("Range : " + firstMatchIndex + " " + lastMatchIndex);
            return new byte[]{firstMatchIndex, lastMatchIndex};
        }
       // System.out.println("Range Not Found !!!!!!!!!!!!!!!!!!!!!!!!1");
        return null;
    }
}
