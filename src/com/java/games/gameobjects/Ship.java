package com.java.games.gameobjects;

import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends GameObject{

    private int frameIndex;

    private List<int[][]> frames;

    public boolean isAlive;
    public boolean loopAnimation = false;

    public Ship(double x, double y){
        super(x,y);
        isAlive = true;
    }

    public void draw(Game game){
        super.draw(game);
        nextFrame();
    }

    public boolean isVisible(){
        // if not alive and last animation frame statement is TRUE, then return FALSE (not visible)
        return !(!isAlive && frameIndex >= frames.size());
    }

    public void kill(){
        isAlive = false;
    }

    // this method will be overridden
    public Bullet fire(){
        return null;
    }

    public void nextFrame(){
        ++frameIndex;
        if(frameIndex >= frames.size()){
            if(!loopAnimation)
                return;
            else
                frameIndex = 0;
        }
        matrix = frames.get(frameIndex);
    }

    public void setStaticView(int [][] viewFrame){
        setMatrix(viewFrame);
        frames = new ArrayList<>();
        frames.add(viewFrame);
        frameIndex = 0;
    }

    public void setAnimatedView(boolean isLoopAnimation, int [][]... viewFrames){
        loopAnimation = isLoopAnimation;
        setMatrix(viewFrames[0]);
        frames = Arrays.asList(viewFrames);
        frameIndex = 0;
    }
}
