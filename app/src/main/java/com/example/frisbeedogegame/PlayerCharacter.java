package com.example.frisbeedogegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class PlayerCharacter extends GameObject {

    private GameSurface gameSurface;

    public PlayerCharacter(GameSurface gameSurface,
                           Bitmap image,
                           int x,
                           int y,
                           int width,
                           int height
    ) {
        super(gameSurface, image, x, y, width, height);
        this.gameSurface = gameSurface;
    }

    public void up() {
        if(this.y - this.getHeight() >= 0) {
            this.y = this.y - height;
        }
    }

    public void down() {
        if(this.y + this.getHeight() <= gameSurface.viewportHeight) {
            this.y = this.y + this.getHeight();
        }
    }

    public void update() {

    }
}
