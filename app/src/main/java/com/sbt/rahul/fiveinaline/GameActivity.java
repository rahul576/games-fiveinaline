package com.sbt.rahul.fiveinaline;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener {

    int Score = 0;
    byte gridRows = 10;
    byte gridColumns = 15;
    byte initBlocksCount = 3, colorCount = 6;
    int tileExistMatrix[][];
    byte colorMatrix[][];
    customTile[][] tileaArray;
    MediaPlayer mp, mpGame;
    customTile sourceTile = null;
    customTile destinationTile = null;
    TextView tvHighestScore;
    TextView tvScore, tvLevel;
    ImageButton ibSound, ibResetGame, ibHowToPlay, ibGameSound,ibInformation;
    GridLayout gridLayout;
    ProgressBar progressBar;
    boolean gaveOverFlag = false;
    int level, levelScore, levelTopScore;

    String colorString;

    public void initializeLevel(int thislevel) {
        switch (thislevel) {
            case 1:
                gridRows = 10;
                gridColumns = 10;
                initBlocksCount = 3;
                colorCount = 3;
                levelScore = 4000;
                break;
            case 2:
                gridRows = 10;
                gridColumns = 12;
                initBlocksCount = 3;
                colorCount = 5;
                levelScore = 5000;
                break;
            case 3:
                gridRows = 10;
                gridColumns = 15;
                initBlocksCount = 3;
                colorCount = 4;
                levelScore = 8000;
                break;
            case 4:
                gridRows = 10;
                gridColumns = 15;
                initBlocksCount = 3;
                colorCount = 6;
                levelScore = 10000;
                break;
            case 5:
                gridRows = 10;
                gridColumns = 15;
                initBlocksCount = 5;
                colorCount = 6;
                levelScore = 6000;
                break;

            case 6:
                gridRows = 6;
                gridColumns = 15;
                initBlocksCount = 3;
                colorCount = 6;
                levelScore = 5000;
                break;
            case 7:
                gridRows = 10;
                gridColumns = 6;
                initBlocksCount = 3;
                colorCount = 6;
                levelScore = 2000;
                break;
            case 8:
                gridRows = 10;
                gridColumns = 15;
                initBlocksCount = 3;
                colorCount = 6;
                levelScore = 15000;
                break;
            default:
                gridRows = 10;
                gridColumns = 15;
                initBlocksCount = 3;
                colorCount = 6;
                levelScore = 20000;
        }
//reinitialize the arrays
        //  Score = 0;
        sourceTile = null;
        destinationTile = null;
        this.tileExistMatrix = new int[gridRows][gridColumns];
        colorMatrix = new byte[gridRows][gridColumns];
        tileaArray = new customTile[gridRows][gridColumns];
        //initArrays(gridRows,gridColumns);
    }

    public boolean isGameOver() {
        //Gave not over = false;
        //Game over = true
        int emptyTilesCount = 0;
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                if (tileExistMatrix[i][j] == 0) {
                    emptyTilesCount++;
                }
                if (emptyTilesCount > initBlocksCount) {
                    return false;
                }
            }
        }
       // Log.e("GameOver", "Game got over emptyTilesCount was:" + emptyTilesCount);
        return true;

    }

    public void loadLevel(int level, int maxPreviousSavedScore, byte savedColorsArray[][]) {
        initializeLevel(level);
        Score = maxPreviousSavedScore;
    //Initialize and Print the Grid View on the Screen
        initializeGridView(gridRows, gridColumns,savedColorsArray);
        tvScore.setText(Score+"");
        progressBar.setMax(levelScore);
        progressBar.setProgress(Score);

//Initialize and Print the initial Game Screen
       // this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
      //  this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
       // this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);

    }

    public void loadLevel(int level) {
        initializeLevel(level);
        Score = 0;
//Initialize and Print the Grid View on the Screen
        initializeGridView(gridRows, gridColumns);

        progressBar.setMax(levelScore);
        progressBar.setProgress(0);

//Initialize and Print the initial Game Screen
        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);

    }

    public void resetGame() {
        for (int i = 0; i < tileaArray.length; i++) {
            for (int j = 0; j < tileaArray[0].length; j++) {
                tileExistMatrix[i][j] = 0;
                tileaArray[i][j].setTileColor((byte) 7);
                tileaArray[i][j].setBackgroundResource(imageRefrence((byte) 7));
            }
        }
        Score=0;
        tvScore.setText("0");
        progressBar.setProgress(0);
        getSharedPreferences("settings",MODE_PRIVATE).edit().putInt("levelTopScore",0);
        updateColorMatrix();
        //startGame();

    }

    public void startGame() {
        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            if (hasFocus) {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    public void ImageButtonsClick(View view) {
        switch (view.getId()) {
            case R.id.ibHowToPlay:
                Dialog dialog = new Dialog(this);
                dialog.setTitle("How To Play");
                dialog.setContentView(R.layout.howtoplay);
                dialog.show();

                break;
            case R.id.ibExitGame:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Sure Want to Exit from Game ?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case R.id.ibGameSound:
                if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("SoundTile", true)) {
                    ibGameSound.setBackgroundResource(R.drawable.mikemute);
                    getSharedPreferences("settings", MODE_PRIVATE).edit().putBoolean("SoundTile", false).commit();
                } else {
                    getSharedPreferences("settings", MODE_PRIVATE).edit().putBoolean("SoundTile", true).commit();
                    ibGameSound.setBackgroundResource(R.drawable.mikeunmute);
                }
                break;
            case R.id.ibSound:
                if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("SoundBG", true)) {
                    // getSharedPreferences("settings",MODE_PRIVATE).getBoolean("SoundBG",false);
                    getSharedPreferences("settings", MODE_PRIVATE).edit().putBoolean("SoundBG", false).commit();
                    mp.pause();
                    ibSound.setBackgroundResource(R.drawable.mute);
                } else {
                    //getSharedPreferences("settings",MODE_PRIVATE).edit().putBoolean("SoundBG",true);
                    getSharedPreferences("settings", MODE_PRIVATE).edit().putBoolean("SoundBG", true).commit();
                    mp = MediaPlayer.create(this, R.raw.initialsound);
                    mp.start();
                    mp.setLooping(true);
                    ibSound.setBackgroundResource(R.drawable.unmute);
                }

                break;
            case R.id.ibResetGame:
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
                alertDialogBuilder1.setMessage("Are you sure,You wanted to Reset Game");
                alertDialogBuilder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        resetGame();
                        startGame();
                        Toast.makeText(GameActivity.this, "Game Reset :)", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                AlertDialog alertDialog1 = alertDialogBuilder1.create();
                alertDialog1.show();

                break;
            case R.id.ibInformationID:

                Dialog dialog2 = new Dialog(this);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.information);
                dialog2.show();

        }

    }



    public byte[][] getColorMatrixFromString(String colorStr) {
        byte tempColorArray[][] = new byte[gridRows][gridColumns];
        String temp[] = colorStr.split(",-");
        for (int i = 0; i < gridRows; i++) {
            String temp2[] = temp[i].split(",");
           // System.out.println("~~~strRow :" + temp[i]);
            for (int j = 0; j < gridColumns; j++) {
               // System.out.println("~~~strRow :" + temp2[j]);
                tempColorArray[i][j] = Byte.parseByte(temp2[j]);

            }

        }
        return tempColorArray;

    }
    @Override
    public void onClick(View v) {
        // Log.d("ViewClicked", "view was clicked ");
        customTile t = (customTile) v;
        byte row = t.getRowIndex();
        byte column = t.getColumnIndex();
        //Log.i("onClick", "View Row    :" + row);
        //Log.i("onClick", "View Column :" + column);
        //Log.i("onClick", "Tile Color  :" + t.getTileColor());


        if (sourceTile == null && tileExistMatrix[row][column] != 0) {
            //Log.i("onClick", "Inside Source Tile null");
            sourceTile = t;
            if (sourceTile != null)
                highlightTile(sourceTile, true);
        } else if (sourceTile != null && tileExistMatrix[row][column] == 0) {
            destinationTile = t;
            Boolean eligiblety = Wave.checkPathExists(tileExistMatrix, new int[]{sourceTile.getRowIndex(), sourceTile.getColumnIndex()}, new int[]{destinationTile.getRowIndex(), destinationTile.getColumnIndex()});
            //Log.e("check", "Waveeeeeeeeeeeeeeeeeeeeeee : " + eligiblety);
            if (eligiblety) {

                moveTile(sourceTile, destinationTile);
                //updateColorMatrix();
                List<byte[]> results = new ArrayList<byte[]>();
                results = Solutions.checkFiveInALine(colorMatrix, new byte[]{row, column}, (byte) 5);
                if (results.size() != 0) {
                    if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("SoundTile", true)) {

                        mpGame.start();
                    }
                    //update User Score
                    updateScore(results.size());
                    for (byte[] b : results) {
                        // customTile tile = ;
                        animateImage(tileaArray[b[0]][b[1]]);
                        fillTile(b, (byte) 7);
                    }
                } else {
//                    if (!isGameOver()) {
                        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
//                    } else {
//                        Toast.makeText(GameActivity.this, "Game Over ! Reset to Play Again", Toast.LENGTH_LONG).show();
//                    }

                }

                //this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
            } else {
                Toast.makeText(this, "Cant move there no direct path", Toast.LENGTH_SHORT).show();
            }

            if (sourceTile != null)
                highlightTile(sourceTile, false);
            if (destinationTile != null)
                highlightTile(destinationTile, false);
            sourceTile = null;
            destinationTile = null;
        } else {
            if (sourceTile != null)
                highlightTile(sourceTile, false);
            if (t != null)
                highlightTile(t, false);
            if (sourceTile != null && destinationTile == null) {
                Toast.makeText(this, "Ball can only move to blank location", Toast.LENGTH_SHORT).show();
            } else if (sourceTile == null) {
                if (t.getTileColor() == 7) {
                    Toast.makeText(this, "Select any ball to move", Toast.LENGTH_SHORT).show();

                }
            }
            sourceTile = null;
            destinationTile = null;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("GameActivity", "GameActivity*********************** : OnCreate");
        setContentView(R.layout.activity_game);
//Initialize the UI Elements
        tvScore = (TextView) findViewById(R.id.idTvScore);
        ibHowToPlay = (ImageButton) findViewById(R.id.ibHowToPlay);
        ibResetGame = (ImageButton) findViewById(R.id.ibResetGame);
        ibSound = (ImageButton) findViewById(R.id.ibSound);
        ibGameSound = (ImageButton) findViewById(R.id.ibGameSound);
        ibInformation=(ImageButton) findViewById(R.id.ibInformationID);
        tvHighestScore = (TextView) findViewById(R.id.tvHighestScore);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        gridLayout = (GridLayout) findViewById(R.id.idGridLayout);
        tvLevel = (TextView) findViewById(R.id.tvLevelNumber);

        tvHighestScore.setText(getSharedPreferences("settings", MODE_PRIVATE).getInt("HighestScore", 0) + "");
//Get the Shared Prefs Object
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        mpGame = MediaPlayer.create(this, R.raw.matchsound);
//Initialize Media Player Backdroud Sound one
        if (preferences.getBoolean("SoundBG", true)) {
            //Play the Background sound if the SoundBG is true in the Shared Preference File
            mp = MediaPlayer.create(this, R.raw.initialsound);
            mp.setLooping(true);
            mp.start();
            ibSound.setBackgroundResource(R.drawable.unmute);
        } else {
            //Do not play any sound just update the image of mute
            ibSound.setBackgroundResource(R.drawable.mute);
        }

//Update the Tile Collapse Icon if it is true in Shared Preference file
        if (preferences.getBoolean("SoundTile", true)) {
            ibGameSound.setBackgroundResource(R.drawable.mikeunmute);
        } else {
            ibGameSound.setBackgroundResource(R.drawable.mikemute);
        }
//Initialize the Game Grid and other Game Variables
        //gridRows = 10;
        //gridColumns = 15;

//        tileExistMatrix = new int[gridRows][gridColumns];
//        tileaArray = new customTile[gridRows][gridColumns];l
//        colorMatrix = new byte[gridRows][gridColumns];
//Initialize and Print the Grid View on the Screen
        //initializeGridView(gridRows, gridColumns);

//Initialize and Print the initial Game Screen
//        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
//        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
//        this.randonFillGame(initBlocksCount, gridRows, gridColumns, colorCount);
        level = preferences.getInt("Level", 1);
        colorString=preferences.getString("colorString","");
        tvLevel.setText(level + "");
        // loadLevel(preferences.getInt("Level", 1));

      //  Log.i("ScreenInformation", "Height :" + getResources().getDisplayMetrics().heightPixels + " Wigth:" + getResources().getDisplayMetrics().widthPixels);
       // Log.i("ScreenInformation", "Density :" + getResources().getDisplayMetrics().density);
       // Log.i("ScreenInformation", "Density :" + getResources().getDisplayMetrics().densityDpi);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("SoundBG", true)) {
            mp.start();
        }


        //Log.i("GameActivity", "GameActivity*********************** : OnResume");
        level = getSharedPreferences("settings", MODE_PRIVATE).getInt("Level", 1);
        levelTopScore = getSharedPreferences("settings", MODE_PRIVATE).getInt("levelTopScore", 0);
        colorString = getSharedPreferences("settings", MODE_PRIVATE).getString("colorString", "");
        if (levelTopScore != 0) {
            initializeLevel(level);
            loadLevel(level, levelTopScore, getColorMatrixFromString(colorString));
        } else {
            loadLevel(level);
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("SoundBG", false)) {
            mp.pause();

        }
        updateColorMatrix();
        getSharedPreferences("settings", MODE_PRIVATE).edit().putString("colorString", colorString).commit();
        getSharedPreferences("settings", MODE_PRIVATE).edit().putInt("levelTopScore", Score).commit();
      //  Log.i("teting", getSharedPreferences("settings",MODE_PRIVATE).getString("colorString",""));
       // Log.i("teting", colorString);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("userScore", Score);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Score = savedInstanceState.getInt("userScore");
    }

    public void randonFillGame(byte noOfSquaresToFill, byte rows, byte columns, byte colors) {
        for (int i = 0; i < noOfSquaresToFill; i++) {
            if(!isGameOver()){
                byte temp[] = getRandamBlankTile(rows, columns);
                fillTile(temp, getRandamColor(colors));

                List<byte[]> results = new ArrayList<byte[]>();
                results = Solutions.checkFiveInALine(colorMatrix, temp, (byte) 5);
                if (results.size() != 0) {
                    if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("SoundTile", true)) {

                        mpGame.start();
                    }
                    //update User Score
                    updateScore(results.size());
                    for (byte[] b : results) {
                        // customTile tile = ;
                        animateImage(tileaArray[b[0]][b[1]]);
                        fillTile(b, (byte) 7);
                    }
                }
            }else{
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
                alertDialogBuilder1.setMessage("Game Over! Try Again?");
                alertDialogBuilder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        resetGame();
                        startGame();
                        Toast.makeText(GameActivity.this, ":-)", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                AlertDialog alertDialog1 = alertDialogBuilder1.create();
                alertDialog1.show();
                break;
            }

        }

    }

    public byte getRandamColor(byte colorCount) {

        return (byte) new Random().nextInt(colorCount + 1);

    }

    public byte[] getRandamBlankTile(byte rows, byte columns) {
        Random rn = new Random();
        byte x = (byte) rn.nextInt(rows);
        byte y = (byte) rn.nextInt(columns);
        //Log.i("Random", " Randam X:" + x + " Y :" + y);
        if (tileExistMatrix[x][y] == 0)
            return new byte[]{x, y};
        else
            return getRandamBlankTile(rows, columns);

    }

    public byte[] getRandamBlankTileNew(byte rows, byte columns) {
        //this Function will take the tileExistMatrix to populate the random tile

        Random rn = new Random();
        byte x = (byte) rn.nextInt(rows);
        byte y = (byte) rn.nextInt(columns);
        //Log.i("Random", " Randam X:" + x + " Y :" + y);
        if (tileExistMatrix[x][y] == 0)
            return new byte[]{x, y};
        else
            return getRandamBlankTile(rows, columns);

    }


    public void fillTile(byte tile[], byte color) {

        byte row = tile[0];
        byte column = tile[1];
        // Log.d("fillTile", "Fill Tile X :" + row + " Column :" + column + " Color : " + color);
        // if (tileExistMatrix[row][column] == 0) {
        tileaArray[row][column].setBackgroundResource(imageRefrence(color));
        //tileaArray[row][column].setRowColumnIndex(row, column);
        tileaArray[row][column].setTileColor(color);

        if (color == 7)
            tileExistMatrix[row][column] = 0;
        else
            tileExistMatrix[row][column] = -1;

        updateColorMatrix();
    }

    public int imageRefrence(byte img) {
        switch (img) {
            case 0:
                return R.drawable.sky;
            case 1:
                return R.drawable.black;
            case 2:
                return R.drawable.voilet;
            case 3:
                return R.drawable.red;
            case 4:
                return R.drawable.green;
            case 5:
                return R.drawable.blue;
            case 6:
                return R.drawable.yellow;
            case 7:
                //blank tile
                return R.drawable.square;
            //hilighted Tile Colors are at a distance of 10  like 0 & 10
            case 10:
                return R.drawable.sky1;
            case 11:
                return R.drawable.black1;
            case 12:
                return R.drawable.voilet1;
            case 13:
                return R.drawable.red1;
            case 14:
                return R.drawable.green1;
            case 15:
                return R.drawable.blue1;
            case 16:
                return R.drawable.yellow1;
            case 17:
                return R.drawable.square;
            default:
                return R.drawable.square;

        }

    }

    public void initializeGridView(byte rows, byte columns) {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(columns);
        gridLayout.setRowCount(rows);


        for (byte i = 0; i < rows; i++) {

            for (byte j = 0; j < columns; j++) {

                customTile tile1 = new customTile(GameActivity.this);
                tile1.setRowColumnIndex(i, j);
                tile1.setTileColor((byte) 7);
                this.tileExistMatrix[i][j] = 0;
                tile1.setOnClickListener(this);

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                //System.out.println("*********** ***********************" + height + "  " + width);
                tile1.setLayoutParams(new android.view.ViewGroup.LayoutParams(height, width));

                //tile1.setBackgroundColor(Color.rgb(255, 255, 0));
                tile1.setBackgroundResource(R.drawable.square);

                tileaArray[i][j] = tile1;
                gridLayout.addView(tile1);
            }

        }
        // gridLayout.setBackgroundResource(R.drawable.gridviewbg);
    }

    public void initializeGridView(byte rows, byte columns, byte savedColorMatrix[][]) {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(columns);
        gridLayout.setRowCount(rows);


        for (byte i = 0; i < rows; i++) {

            for (byte j = 0; j < columns; j++) {

                customTile tile1 = new customTile(GameActivity.this);
                tile1.setRowColumnIndex(i, j);
                tile1.setTileColor(savedColorMatrix[i][j]);
                //if the Tile  color is blank then
                if (savedColorMatrix[i][j] == 7) {
                    this.tileExistMatrix[i][j] = 0;
                } else {
                    this.tileExistMatrix[i][j] = -1;
                }

                tile1.setOnClickListener(this);

                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                //System.out.println("*********** ***********************" + height + "  " + width);
                tile1.setLayoutParams(new android.view.ViewGroup.LayoutParams(height, width));

                //tile1.setBackgroundColor(Color.rgb(255, 255, 0));
                //tile1.setBackgroundResource(R.drawable.square);
                tile1.setBackgroundResource(imageRefrence(savedColorMatrix[i][j]));

                tileaArray[i][j] = tile1;
                gridLayout.addView(tile1);
            }

        }
        // gridLayout.setBackgroundResource(R.drawable.gridviewbg);
    }

    //set flag true to highlight or flase to un-highlight
    void highlightTile(customTile t, boolean flag) {
        byte color = t.getTileColor();
        if (flag) {
            t.setBackgroundResource(imageRefrence((byte) (color + 10)));
        } else {
            t.setBackgroundResource(imageRefrence(color));
        }

    }

    public void animateImage(customTile tile) {

        RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        // anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(300);
        // Start animating the image
        // final ImageView splash = (ImageView) findViewById(R.id.splash);
        tile.startAnimation(anim);

    }

    public void updateScore(int hitsCount) {
        Score = Score + hitsCount * hitsCount * 10;
        tvScore.setText(Score + "");
        getSharedPreferences("settings", MODE_PRIVATE).edit().putInt("levelTopScore", Score).commit();


        if (Score >= getSharedPreferences("settings", MODE_PRIVATE).getInt("HighestScore", 0)) {
            getSharedPreferences("settings", MODE_PRIVATE).edit().putInt("HighestScore", Score).commit();
            tvHighestScore.setText(Score + "");
        }
        progressBar.setProgress(Score);
        if (Score >= levelScore) {
            Toast.makeText(GameActivity.this, "Congratulations Level :" + level + " Completed", Toast.LENGTH_LONG).show();
            level++;
            Score=0;
            getSharedPreferences("settings", MODE_PRIVATE).edit().putInt("Level", level).commit();
            getSharedPreferences("settings", MODE_PRIVATE).edit().putInt("levelTopScore", 0).commit();
           // getSharedPreferences("settings", MODE_PRIVATE).edit().putString("colorString", "").commit();

            tvLevel.setText(level + "");
            //resetGame();
            loadLevel(level);
            //startGame();
            Score = Score;
        }


    }




    private void updateColorMatrix() {
        //Tile t = new Tile();
        colorString="";
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                colorString = colorString + tileaArray[i][j].getTileColor() + ",";
                colorMatrix[i][j] = tileaArray[i][j].getTileColor();
            }
            colorString = colorString + "-";
        }
    }

    private void moveTile(customTile sourceTile, customTile destinationTile) {

        byte row = sourceTile.getRowIndex();
        byte column = sourceTile.getColumnIndex();
        byte color = sourceTile.getTileColor();


//        //set source blank
        if (sourceTile != null && destinationTile != null) {
//            Log.e("moveTile : ", "Trying to more the tile");
//            Log.e("moveTile : ", "Row:" + sourceTile.getRowIndex());
//            Log.e("moveTile : ", "Column:" + sourceTile.getColumnIndex());
//            Log.e("moveTile : ", "color:" + sourceTile.getTileColor());
//            Log.e("moveTile : ", "Row:" + destinationTile.getRowIndex());
//            Log.e("moveTile : ", "Column:" + destinationTile.getColumnIndex());
//            Log.e("moveTile : ", "Color:" + destinationTile.getTileColor());
            fillTile(new byte[]{destinationTile.getRowIndex(), destinationTile.getColumnIndex()}, sourceTile.getTileColor());
            fillTile(new byte[]{sourceTile.getRowIndex(), sourceTile.getColumnIndex()}, (byte) 7);
//        //set destination to source color

            sourceTile = null;
            destinationTile = null;
        } else {
            // Log.e("Error : ", "Either source or destination is null so can not more the Tile");
        }
//        destinationTile.setBackgroundResource(imageRefrence(color));
//
//        //set the source empty
//        tileExistMatrix[row][column] = 0;

        //fillTile(byte[])

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


}
