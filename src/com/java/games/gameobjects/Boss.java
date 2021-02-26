package com.java.games.gameobjects;

import com.java.games.Direction;
import com.java.games.ShapeMatrix;

public class Boss extends EnemyShip{

    private int frameCount = 0;

    public Boss(double x, double y){
        super(x, y);
        super.score = 100;
        setAnimatedView(true,
                        ShapeMatrix.BOSS_ANIMATION_FIRST,
                        ShapeMatrix.BOSS_ANIMATION_SECOND);
    }

    @Override
    public void nextFrame() {
        ++frameCount;
        if(frameCount % 10 == 0 || !isAlive)
            super.nextFrame();
    }

    @Override
    public Bullet fire() {

        if(!isAlive)
            return null;
        if(matrix == ShapeMatrix.BOSS_ANIMATION_FIRST)
            return new Bullet(x + 6, y + height, Direction.DOWN);
        if(matrix == ShapeMatrix.BOSS_ANIMATION_SECOND)
            return new Bullet(x, y + height, Direction.DOWN);
        return null;
    }


    @Override
    public void kill(){

        if(isAlive) {
            isAlive = false;
            super.setAnimatedView(
                    false,
                    ShapeMatrix.KILL_ENEMY_ANIMATION_FIRST,
                    ShapeMatrix.KILL_BOSS_ANIMATION_SECOND,
                    ShapeMatrix.KILL_BOSS_ANIMATION_THIRD);
        }
    }
}
