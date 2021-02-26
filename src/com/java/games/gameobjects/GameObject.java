package com.java.games.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

public class GameObject {

    public double x;
    public double y;

    public int width;
    public int height;

    public int[][] matrix;

    public GameObject(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setMatrix(int[][] matrix){
        this.matrix = matrix;

        width = matrix[0].length;
        height = matrix.length;
    }

    public void draw(Game game){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int colorIndex = matrix[i][j];
                game.setCellValueEx((int)x + j, (int)y + i, Color.values()[colorIndex], "");
            }
        }
    }

    private boolean isCollision(double x, double y) {
        for (int matrixX = 0; matrixX < width; matrixX++) {
            for (int matrixY = 0; matrixY < height; matrixY++) {
                if (matrix[matrixY][matrixX] > 0
                        && matrixX + (int) this.x == (int) x
                        && matrixY + (int) this.y == (int) y) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollision(GameObject other){
        for(int y = 0; y < other.height; y++){
            for(int x = 0; x < other.width; x++){
                if(other.matrix[y][x] > 0){
                    if(isCollision(x + other.x, y + other.y))
                        return true;
                }
            }
        }
        return false;
    }
}
