package com.java.games.gameobjects;

import com.java.games.Direction;
import com.java.games.ShapeMatrix;

public class Bullet extends GameObject{

    private int dy;
    public boolean isAlive;

    public Bullet(double x, double y, Direction direction){
        super(x,y);
        isAlive = true;
        setMatrix(ShapeMatrix.BULLET);
        dy = direction.equals(Direction.UP) ? -1 : 1;
    }

    public void move(){
        y += dy;
    }

    public void kill(){
        isAlive = false;
    }

}
