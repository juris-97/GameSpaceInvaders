package com.java.games;

import com.java.games.gameobjects.Bullet;
import com.java.games.gameobjects.EnemyFleet;
import com.java.games.gameobjects.PlayerShip;
import com.java.games.gameobjects.Star;
import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceInvaders extends Game {

    public static final int WIDTH       = 64;
    public static final int HEIGHT      = 64;
    public static final int COMPLEXITY  = 5;

    private static final int PLAYER_BULLETS_MAX = 2;

    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    private List<Star>   stars;

    private EnemyFleet enemyFleet;
    private PlayerShip playerShip;

    private int score;
    private int animationCount;

    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame(){
        setTurnTimer(40);
        createStars();
        score = 0;

        enemyFleet = new EnemyFleet();
        playerShip = new PlayerShip();

        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();

        isGameStopped = false;
        animationCount = 0;
        drawScene();
    }
    private void createStars(){
        int randX, randY;
        int starsCount = 15;

        stars = new ArrayList<>();
        while(starsCount > 0){
            randX = new Random().nextInt(WIDTH);
            randY = new Random().nextInt(HEIGHT / 2);

            Star s = new Star(randX, randY);
            if(!stars.contains(s)){
                stars.add(s);
                --starsCount;
            }
        }
    }

    private void drawScene(){
        drawField();

        for(Bullet bullet : enemyBullets){
            bullet.draw(this);
        }

        for(Bullet bullet : playerBullets){
            bullet.draw(this);
        }

        enemyFleet.draw(this);
        playerShip.draw(this);
    }
    private void drawField(){

        for (int y = 0; y  < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellValueEx(x, y, Color.BLACK, "");
            }
        }

        for (Star s : stars)
            s.draw(this);
    }

    private void check(){
        playerShip.verifyHit(enemyBullets);
        score += enemyFleet.verifyHit(playerBullets);

        enemyFleet.deleteHiddenShips();

        if(enemyFleet.getShipsCount() == 0)
            playerShip.win();

        if(enemyFleet.getBottomBorder() >= playerShip.y)
            playerShip.kill();

        removeDeadBullets();

        if(!playerShip.isAlive)
            stopGameWithDelay();

    }
    private void moveSpaceObjects(){
        enemyFleet.move();
        playerShip.move();

        for(Bullet bullet : enemyBullets){
            bullet.move();
        }

        for(Bullet bullet : playerBullets){
            bullet.move();
        }

    }
    private void removeDeadBullets(){
        enemyBullets.removeIf((Bullet bullet) -> !bullet.isAlive || bullet.y >= HEIGHT - 1);
        playerBullets.removeIf((Bullet bullet) -> !bullet.isAlive || (bullet.y + bullet.height) < 0);
    }

    private void stopGame(boolean isWin){
        isGameStopped = true;
        if(isWin)
            showMessageDialog(Color.BLACK, "YOU WIN", Color.GREEN, 40);
        else
            showMessageDialog(Color.BLACK, "YOU DEAD", Color.RED, 40);
    }
    private void stopGameWithDelay(){

        ++animationCount;
        if(animationCount >= 10)
            stopGame(playerShip.isAlive);
    }

    @Override
    public void onTurn(int step) {
        setScore(score);
        moveSpaceObjects();
        check();

        Bullet b;
        if( (b = enemyFleet.fire(this)) != null)
            enemyBullets.add(b);

        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if(key == Key.SPACE && this.isGameStopped){
            createGame();
            return;
        }
        if(key == Key.LEFT)
            playerShip.setDirection(Direction.LEFT);
        if(key == Key.RIGHT)
            playerShip.setDirection(Direction.RIGHT);
        if(key == Key.SPACE){
            Bullet b = playerShip.fire();
            if(b != null && playerBullets.size() < PLAYER_BULLETS_MAX)
                playerBullets.add(b);
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if(key.equals(Key.LEFT) && playerShip.getDirection().equals(Direction.LEFT))
            playerShip.setDirection(Direction.UP);

        if(key.equals(Key.RIGHT) && playerShip.getDirection().equals(Direction.RIGHT))
            playerShip.setDirection(Direction.UP);
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        if(x >= WIDTH || x < 0)
            return;
        if(y >= HEIGHT || y < 0)
            return;

        super.setCellValueEx(x, y, cellColor, value);
    }
}

