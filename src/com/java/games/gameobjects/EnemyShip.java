package com.java.games.gameobjects;

import com.java.games.Direction;
import com.java.games.ShapeMatrix;

public class EnemyShip extends Ship{

    public int score = 15;

    public EnemyShip(double x, double y){
        super(x,y);
        setStaticView(ShapeMatrix.ENEMY);
    }

    public void move(Direction direction, double speed){
        if(direction.equals(Direction.RIGHT))
            x += speed;
        if(direction.equals(Direction.LEFT))
            x -= speed;
        if(direction.equals(Direction.DOWN))
            y += 2;
    }

    @Override
    public Bullet fire(){
        return new Bullet(x + 1, y + height, Direction.DOWN);
    }

    @Override
    public void kill() {
        if(isAlive){
            isAlive = false;
            setAnimatedView(false,
                    ShapeMatrix.KILL_ENEMY_ANIMATION_FIRST,
                    ShapeMatrix.KILL_ENEMY_ANIMATION_SECOND,
                    ShapeMatrix.KILL_ENEMY_ANIMATION_THIRD);
        }
    }
}
