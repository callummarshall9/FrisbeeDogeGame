package com.example.frisbeedogegame;

import android.graphics.Bitmap;

public class Frisbee extends GameObject {
    public Frisbee(GameSurface gameSurface, Bitmap image, int x, int y, int width, int height) {
        super(gameSurface, image, x, y, width, height);
    }

    public void update(FrisbeeSpawner spawner, PlayerCharacter playerCharacter) {
        this.x = this.x + velocityX * (int)sfWidth;
        this.y = this.y + velocityY * (int)sfHeight;
        if(this.x < 0) {
            this.gameSurface.score = this.gameSurface.score + 1;
            spawner.destroyFrisbee(this);
        } else {
            if(this.collides(playerCharacter)) {
                this.gameSurface.lives = this.gameSurface.lives - 1;
                spawner.destroyFrisbee(this);
            }
        }
    }
}
