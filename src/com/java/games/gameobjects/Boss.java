package com.java.games.gameobjects;

import com.java.games.Direction;
import com.java.games.ShapeMatrix;

public class Boss extends EnemyShip{

    private int frameCount = 0;
    private int hp;

    public Boss(double x, double y){
        super(x, y);
        super.score = 100;
        hp = 100;
        setAnimatedView(true,
                        ShapeMatrix.BOSS_ANIMATION_FIRST,
                        ShapeMatrix.BOSS_ANIMATION_SECOND);
    }

    @Override
    public void nextFrame() {
        ++frameCount;
        if(frameCount % 10 == 0 || !isAlive)
            super.nextFrame();

        if(matrix == ShapeMatrix.BOSS_ANIMATION_DAMAGED && frameCount > 10)
            setAnimatedView(true,
                    ShapeMatrix.BOSS_ANIMATION_FIRST,
                    ShapeMatrix.BOSS_ANIMATION_SECOND);
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

    public int damage(){
        hp = hp > 0 ? hp -= 25 : hp;
        super.setAnimatedView(false, ShapeMatrix.BOSS_ANIMATION_DAMAGED);
        return hp;
    }

    @Override
    public void kill(){

        if(isAlive && damage() <= 0) {
            isAlive = false;
            super.setAnimatedView(
                    false,
                    ShapeMatrix.KILL_BOSS_ANIMATION_FIRST,
                    ShapeMatrix.KILL_BOSS_ANIMATION_SECOND,
                    ShapeMatrix.KILL_BOSS_ANIMATION_THIRD);
        }
    }
}
