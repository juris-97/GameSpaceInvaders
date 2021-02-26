package com.java.games.gameobjects;


import com.java.games.Direction;
import com.java.games.ShapeMatrix;
import com.java.games.SpaceInvaders;

import java.util.List;

public class PlayerShip extends Ship{

    private Direction direction = Direction.UP;

    public PlayerShip(){
        super(SpaceInvaders.WIDTH / 2.0, SpaceInvaders.HEIGHT - ShapeMatrix.PLAYER.length - 1);
        setStaticView(ShapeMatrix.PLAYER);
    }

    public void verifyHit(List<Bullet> bullets){
        if(isAlive){
            for(Bullet b : bullets){
                if(b.isAlive && isCollision(b)){
                    b.kill();
                    kill();
                }
            }
        }
    }

    @Override
    public void kill(){
        if(!isAlive)
            return;
        isAlive = false;
        setAnimatedView(false,
                ShapeMatrix.KILL_PLAYER_ANIMATION_FIRST,
                ShapeMatrix.KILL_PLAYER_ANIMATION_SECOND,
                ShapeMatrix.KILL_PLAYER_ANIMATION_THIRD);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction newDirection) {
        if(newDirection != Direction.DOWN)
            this.direction = newDirection;
    }

    public void move(){
        if(!isAlive)
            return;

        if(direction.equals(Direction.LEFT))
            --x;
        if(direction.equals(Direction.RIGHT))
            ++x;

        // checking left border
        x = x < 0 ? 0 : x;
        //checking right border
        x = x + width > SpaceInvaders.WIDTH ? SpaceInvaders.WIDTH - width : x;
    }

    public void win(){
        setStaticView(ShapeMatrix.WIN_PLAYER);
    }

    @Override
    public Bullet fire(){
        if(!isAlive)
            return null;
        return new Bullet(x + 2, y - ShapeMatrix.BULLET.length, Direction.UP);
    }
}
