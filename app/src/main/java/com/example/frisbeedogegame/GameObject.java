package com.example.frisbeedogegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public abstract class GameObject {
    protected Bitmap image;
    protected final int width;
    protected final int height;
    protected int x;
    protected int y;
    protected int velocityX;
    protected int velocityY;
    protected GameSurface gameSurface;
    protected double sfWidth = -1;
    protected  double sfHeight = -1;

    public GameObject(GameSurface gameSurface, Bitmap image, int x, int y,int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameSurface = gameSurface;
    }

    public boolean collides (GameObject r) {
        return x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y;
    }

    public void draw(Canvas canvas) {

        if(sfWidth == -1 ) {
            sfWidth = (canvas.getWidth() / 800.00);
            sfHeight = (canvas.getHeight() / 400.00);
            int canvasWidth = (int)(width * sfWidth);
            int canvasHeight = (int) (height * sfHeight);

            this.image = Bitmap.createScaledBitmap(this.image, canvasWidth, canvasHeight, true);
        }
        int canvasX = (int)(x * sfWidth);
        int canvasY = (int)(y * sfHeight);

        canvas.drawBitmap(this.image, canvasX, canvasY, null);
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
