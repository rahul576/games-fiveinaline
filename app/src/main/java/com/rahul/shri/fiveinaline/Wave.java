package com.rahul.shri.fiveinaline;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Rahul.Shrivastava
 */
public class Wave {
//
//    public static void main(String args[]) {
//
//        int[][] paths = {
//            {0, 0, 0, 0, -1, 0,0,0,0,0},
//            {0, 0, -1, 0, 0, 0,0,0,0,0},
//            {0, -1, -1, -1, -1, 0,0,0,0,0},
//            {0, 0, -1, 0, 0, 0,0,0,0,0},
//            {0, 0, -1, 0, 0, 0,0,0,0,0},
//            {0, -1, -1, 0, 0, 0,0,0,0,0},
//            {0, 0, 0, 0, 0, 0,0,0,0,0}
//        };
//        int point1[] = {4, 1};
//        int point2[] = {4, 4};
//        System.out.print(checkPathExists(paths, point1, point2));
//
//    }

    public static boolean checkPathExists(int pathArray[][], int[] source, int[] destination) {


        int rows, columns;
        rows = pathArray.length;
        columns = pathArray[0].length;

        int pathArraycopy[][] = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                pathArraycopy[i][j] = pathArray[i][j];
            }
        }

        // System.out.println(rows + "  " + columns);
        //source indexes
        int sr = source[0];
        int sc = source[1];
        //set the source as the 
        //destination indexes
        int dr = destination[0];
        int dc = destination[1];
        int waveSpreadCount = 0;
        int wave = 1;
        //Initialize wave
        //printArray(pathArray);
        //System.out.println("*****" + wave + "******");
        spreadWave(wave, source, pathArraycopy);
        //printArray(pathArraycopy);

        for (wave = 1; ; wave++) {

            waveSpreadCount = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (pathArraycopy[i][j] == wave) {
                        if (spreadWave(wave + 1, new int[]{i, j}, pathArraycopy)) {
                            waveSpreadCount++;
                        }
                    }
                }
            }
           // System.out.println("*****" + (wave + 1) + "******");
            //printArray(pathArraycopy);
           // printArray(pathArray);
            if (pathArraycopy[dr][dc] != 0) {
                return true;
            } else if (waveSpreadCount == 0) {
                return false;
            }
        }
    }

    public static Boolean spreadWave(int waveNumber, int point[], int TempArray[][]) {

        int x = point[0];
        int y = point[1];
        boolean temp = false;
        //int pointValue=TempArray[x][y];
        try {
            if (TempArray[x][y] == 0) {
                TempArray[x][y] = waveNumber;
                temp = true;
            }
            //Up
            if (x - 1 >= 0 && TempArray[x - 1][y] == 0) {
                TempArray[x - 1][y] = waveNumber;
                temp = true;
            }
            //Down
            if (x + 1 < TempArray.length && TempArray[x + 1][y] == 0) {
                TempArray[x + 1][y] = waveNumber;
                temp = true;
            }
            //left 
            if (y - 1 >= 0 && TempArray[x][y - 1] == 0) {
                TempArray[x][y - 1] = waveNumber;
                temp = true;
            }
            //right
            if (y + 1 < TempArray[0].length && TempArray[x][y + 1] == 0) {
                TempArray[x][y + 1] = waveNumber;
                temp = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return temp;
    }

//    public static void printArray(int[][] pathArray) {
//
//        for (int i = 0; i < pathArray.length; i++) {
//            for (int j = 0; j < pathArray[0].length; j++) {
//                System.out.print(pathArray[i][j]);
//            }
//            System.out.println("");
//        }
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


}
