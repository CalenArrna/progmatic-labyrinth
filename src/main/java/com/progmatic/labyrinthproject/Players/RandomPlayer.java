package com.progmatic.labyrinthproject.Players;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.Random;

public class RandomPlayer implements Player {
    @Override
    public Direction nextMove(Labyrinth l) {
        Random rIdx = new Random();
        int directionsQuantity = l.possibleMoves().size();
        if (directionsQuantity != 0) {
            return l.possibleMoves().get(rIdx.nextInt(directionsQuantity));
        }else {
           int way = rIdx.nextInt(4);
           switch (way){
               case 0 : 
                   return Direction.SOUTH;
               case 1 : 
                   return  Direction.EAST;
               case 2 : 
                   return Direction.WEST;
               case 3 : 
                   return Direction.NORTH;
           }
        }
        return null;
    }
}
