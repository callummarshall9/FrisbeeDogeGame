package com.example.frisbeedogegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

public class FrisbeeSpawner {

    private LinkedList<Frisbee> frisbeeArrayList;
    private final int MAX_FRISBEES;

    private GameSurface gameSurface;
    private Bitmap frisbeeImage;
    public FrisbeeSpawner(GameSurface gameSurface) {
        this.frisbeeArrayList = new LinkedList<>();
        this.gameSurface = gameSurface;
        this.MAX_FRISBEES = 15;
        this.frisbeeImage = BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.frisbee);
    }

    public void clear() {
        frisbeeArrayList.clear();
    }

    public void destroyFrisbee(Frisbee frisbee) {
        frisbeeArrayList.remove(frisbee);
        frisbee = null;
        System.gc();
    }

    private void spawn() {
        int y = (int)Math.floor((Math.random() * 400) + 1);
        int randomSpeed = (int)Math.floor((Math.random() * 10) + 1);
        Frisbee newFrisbee = new Frisbee(gameSurface, frisbeeImage, 770, y, 50, 20);
        newFrisbee.velocityX = -randomSpeed;
        frisbeeArrayList.add(newFrisbee);
    }

    public void draw(Canvas canvas) {
        for(int i = 0; i < frisbeeArrayList.size(); i++) {
            frisbeeArrayList.get(i).draw(canvas);
        }
    }

    public void update(PlayerCharacter playerCharacter) {
        for(int i = 0 ; i < frisbeeArrayList.size(); i++) {
            frisbeeArrayList.get(i).update(this, playerCharacter);
        }
        if(frisbeeArrayList.size() < MAX_FRISBEES) {
            spawn();
        }
    }
}
