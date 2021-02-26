package com.java.games.gameobjects;

import com.java.games.Direction;
import com.java.games.ShapeMatrix;
import com.java.games.SpaceInvaders;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class EnemyFleet {

    private final static int ROWS_COUNT     = 3;
    private final static int COLUMNS_COUNT  = 10;
    private final static int STEP = ShapeMatrix.ENEMY.length + 1;

    private Direction direction = Direction.RIGHT;
    private List<EnemyShip> ships;

    public EnemyFleet(){
        createShips();
    }

    private void createShips(){
        ships = new ArrayList<>();

        for (int y = 0; y < ROWS_COUNT; y++) {
            for (int x = 0; x < COLUMNS_COUNT; x++) {
                ships.add(new EnemyShip(x * STEP, y * STEP + 12));
            }
        }
        ships.add(new Boss(STEP * COLUMNS_COUNT/2.0 - ShapeMatrix.BOSS_ANIMATION_FIRST.length/2.0 - 1, 5));
    }

    public void draw(Game game){
        for(EnemyShip enemyShip : ships){
            enemyShip.draw(game);
        }
    }

    public void move(){

        //check if direction was change
        boolean isChanged = false;

        if(ships.isEmpty())
            return;

        if(direction.equals(Direction.LEFT) && getLeftBorder() < 0){
            direction = Direction.RIGHT;
            isChanged = true;
        }

        if(direction.equals(Direction.RIGHT) && getRightBorder() > SpaceInvaders.WIDTH){
            direction = Direction.LEFT;
            isChanged = true;
        }

        for(EnemyShip ship : ships)
            ship.move(isChanged ? Direction.DOWN : direction, getSpeed());

    }

    public Bullet fire(Game game){

        if(ships.isEmpty())
            return null;
        if(game.getRandomNumber(100 / SpaceInvaders.COMPLEXITY) > 0)
            return null;
        int rand = game.getRandomNumber(ships.size());

        return ships.get(rand).fire();
    }

    public int verifyHit(List<Bullet> bullets){

        if(bullets.isEmpty())
            return 0;

        int scoreSum = 0;
        for(EnemyShip ship : ships){
            for(Bullet bullet : bullets){
                if(ship.isCollision(bullet) && ship.isAlive && bullet.isAlive){
                    ship.kill();
                    bullet.kill();
                    scoreSum += ship.score;
                }
            }
        }

        return scoreSum;
    }

    public void deleteHiddenShips(){
        ships.removeIf((EnemyShip s) -> !s.isVisible());
    }

    private double getSpeed(){
        return Double.min(2.0, 3.0 / ships.size());
    }

    public int getShipsCount(){
        return ships.size();
    }

    private double getLeftBorder(){
        double minX = ships.get(0).x;

        for(EnemyShip ship : ships){
            if(ship.x < minX)
                minX = ship.x;
        }

        return minX;
    }

    private double getRightBorder(){
        //get max X with ship width;
        double maxX = ships.get(0).x + ships.get(0).width;

        for(EnemyShip ship : ships){
            if(ship.x + ship.width > maxX)
                maxX = ship.x + ship.width;
        }

        return maxX;
    }

    public double getBottomBorder(){

        if(ships.size() < 1)
            return 0;

        // get max ship Y cord with height
        double maxY = ships.get(0).y + ships.get(0).height;

        for(EnemyShip ship : ships){
            if(ship.y + ship.height > maxY)
                maxY = ship.y + ship.height;
        }

        return maxY;
    }

}
