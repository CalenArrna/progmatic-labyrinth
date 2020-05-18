package com.progmatic.labyrinthproject.Players;

import com.progmatic.labyrinthproject.Coordinate;
import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

public class WallFollowerPlayer implements Player {
    private Direction faceing = Direction.EAST;


    @Override
    public Direction nextMove(Labyrinth l) {
        while (!l.possibleMoves().contains(faceing)){
            if (l.possibleMoves().contains(faceing)) return faceing;
            if (getWallTypeToTheRight(l) == CellType.EMPTY || getWallTypeToTheRight(l) == CellType.END) {
               turnToDirection(true);
            } else{
                turnToDirection(false);
            }
        }
        return faceing;
    }

    private void turnToDirection(boolean toTheRight) {
        switch (faceing) {
            case EAST:
                faceing = toTheRight ? Direction.SOUTH : Direction.NORTH;
            case SOUTH:
                faceing = toTheRight ? Direction.WEST : Direction.EAST;
            case WEST:
                faceing = toTheRight ? Direction.NORTH : Direction.SOUTH;
            case NORTH:
               faceing = toTheRight ? Direction.EAST : Direction.WEST;
            default:
                throw new RuntimeException("Gond volt a WallFollower TurnToDirection metódusban!");
        }
    }

    private CellType getWallTypeToTheRight(Labyrinth l) {
        try {
            switch (faceing) {
                case EAST:
                    Coordinate south = new Coordinate(l.getPlayerPosition().getCol(), (l.getPlayerPosition().getRow() + 1));
                    return l.getCellType(south);
                case SOUTH:
                    Coordinate west = new Coordinate(l.getPlayerPosition().getCol() - 1, (l.getPlayerPosition().getRow()));
                    return l.getCellType(west);
                case WEST:
                    Coordinate north = new Coordinate(l.getPlayerPosition().getCol(), (l.getPlayerPosition().getRow() - 1));
                    return l.getCellType(north);
                case NORTH:
                    Coordinate east = new Coordinate(l.getPlayerPosition().getCol() + 1, (l.getPlayerPosition().getRow()));
                    return l.getCellType(east);
            }
        } catch (Exception e) {
            throw new RuntimeException("Hiba történt wallfollowerplayer getWallType metódusában. Valószínű, hogy a cella nem volt a labirintusban");
        }
        return null;
    }
}
