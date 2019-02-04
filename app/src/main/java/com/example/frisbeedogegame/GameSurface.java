package com.example.frisbeedogegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private PlayerCharacter playerCharacter;
    private FrisbeeSpawner spawner;
    public final int viewportWidth;
    public final int viewportHeight;
    private boolean startScreen;
    private Bitmap startScreenBackground;
    private Bitmap gameBackground;
    public int lives;
    public long score;
    Paint textPaint;


    public GameSurface(Context context) {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
        viewportWidth = 800;
        viewportHeight = 400;
        startScreen = true;
        lives = 3;
        score = 0;
        spawner = new FrisbeeSpawner(this);
        textPaint = new Paint();
        textPaint.setTextSize(48f);
        textPaint.setColor(Color.WHITE);
    }

    public void update() {
        if(!startScreen) {
            this.playerCharacter.update();
            spawner.update(this.playerCharacter);
            if(lives <= 0) {
                startScreen = true;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(startScreen) {
            canvas.drawBitmap(startScreenBackground, 0, 0, null);
        } else {
            canvas.drawBitmap(gameBackground, 0, 0, null);
            this.playerCharacter.draw(canvas);
            spawner.draw(canvas);
            canvas.drawText("Score: " + Long.toString(score), 10, 50, textPaint);
            canvas.drawText("Lives: " + Integer.toString(lives), 10, 100, textPaint);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap playerCharacter1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.player);
        startScreenBackground = BitmapFactory.decodeResource(this.getResources(), R.drawable.start_screen);
        startScreenBackground = Bitmap.createScaledBitmap(startScreenBackground, holder.getSurfaceFrame().width(), holder.getSurfaceFrame().height(), true);
        gameBackground = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_background);
        gameBackground = Bitmap.createScaledBitmap(gameBackground, holder.getSurfaceFrame().width(), holder.getSurfaceFrame().height(), true);
        this.playerCharacter = new PlayerCharacter(this, playerCharacter1, 20, 20, 40, 30);
        this.gameThread = new GameThread(this, holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }





    private void start() {//Method to start the game.
        startScreen = false;
        lives = 3;
        score = 0;
        spawner.clear();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int)event.getX();
            int y = (int)event.getY();
            if(!startScreen) {
                if(y < getHeight() / 2) {
                    playerCharacter.up();
                } else {
                    playerCharacter.down();
                }
            } else {
                start();
            }


        }
        return false;
    }
}
