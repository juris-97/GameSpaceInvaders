package com.java.games.gameobjects;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.Objects;

public class Star extends GameObject{

    private static final String STAR_SIGN = "\u2605";

    public Star(double x, double y){
        super(x, y);
    }

    public void draw(Game game){
        game.setCellValueEx(
                (int)x, (int)y,
                Color.NONE,
                STAR_SIGN,
                Color.WHITE,
                100);
    }

    @Override
    public boolean equals(Object obj) {

        // self check
        if(this == obj)
            return true;

        // null check
        if (obj == null)
            return false;

        // type check and cast
        if (getClass() != obj.getClass())
            return false;

        Star star = (Star) obj;
        // field comparison
        return Objects.equals(x, star.x)
                && Objects.equals(y, star.y);
    }
}
