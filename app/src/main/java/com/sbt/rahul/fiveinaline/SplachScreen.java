package com.sbt.rahul.fiveinaline;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;


public class SplachScreen extends Activity {
    boolean sound_flag=false;
    ImageView imageView;
    ImageButton ibSound;
    MediaPlayer mediaPlayer;

    @Override
    protected void onPause() {
            super.onPause();
        if(getSharedPreferences("settings", MODE_PRIVATE).getBoolean("SoundBG", false)){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("SoundBG", true)) {
            mediaPlayer.start();
            ibSound.setBackgroundResource(R.drawable.unmute_home);
        }
        else{
            ibSound.setBackgroundResource(R.drawable.mute_home);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);
        imageView = (ImageView) findViewById(R.id.ivHowToPlay);
        ibSound = (ImageButton) findViewById(R.id.ibSound);
        mediaPlayer = MediaPlayer.create(this, R.raw.initialsound);
        mediaPlayer.setLooping(true);

        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getBoolean("FirstLaunchFlag", true)) {
            editor.putInt("Level",1);
            editor.putInt("levelTopScore",0);
            editor.putBoolean("FirstLaunchFlag", false);
            editor.putBoolean("SoundBG", true);
            editor.putBoolean("SoundTile", true);
            editor.putInt("HighestScore", 0);
            editor.putString("colorString", "");
            editor.commit();
        }
        if (preferences.getBoolean("SoundBG", true)) {
            sound_flag=true;
            mediaPlayer.start();
            ibSound.setBackgroundResource(R.drawable.unmute_home);
        } else {
            sound_flag=false;
            ibSound.setBackgroundResource(R.drawable.mute_home);
        }

        AnimationDrawable anim = new AnimationDrawable();
        anim.addFrame(getResources().getDrawable(R.drawable.animation1), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation2), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation3), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation4), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation5), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation6), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation7), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation8), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation9), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation10), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animation11), 2000);
        anim.addFrame(getResources().getDrawable(R.drawable.animationerror), 10000);

        imageView.setImageDrawable(anim);

        //if you want the animation to loop, set false
        anim.setOneShot(false);
        anim.start();
    }

    public void playGame(View view) {
        //finish();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

    public void soundControl(View view) {
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getBoolean("SoundBG", true)) {
            editor.putBoolean("SoundBG", false);
            editor.commit();
            mediaPlayer.pause();
            ibSound.setBackgroundResource(R.drawable.mute_home);

        } else {
            mediaPlayer.start();
            ibSound.setBackgroundResource(R.drawable.unmute_home);
            editor.putBoolean("SoundBG", true);
            editor.commit();

        }
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

}
