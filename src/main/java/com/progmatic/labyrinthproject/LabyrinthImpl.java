package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private int width;
    private int height;
    private ArrayList<Cell> cellListOfLabyrinth = new ArrayList<>();
    private Cell playerPosition;

    public LabyrinthImpl() {
    }

    @Override
    public int getWidth() {
        if (this.width == 0) {
            return -1;
        }
        return width;
    }

    @Override
    public int getHeight() {
        if (this.height == 0) {
            return -1;
        }
        return height;
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    Coordinate pos = new Coordinate(ww, hh);
                    Cell c = new Cell(pos);
                    switch (line.charAt(ww)) {
                        case 'W':
                            c.setType(CellType.WALL);
                            break;
                        case 'E':
                            c.setType(CellType.END);
                            break;
                        case 'S':
                            c.setType(CellType.START);
                            playerPosition = c;
                            break;
                    }
                    cellListOfLabyrinth.add(c);
                }
            }
            this.height = height;
            this.width = width;
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if ((c.getCol() > this.width-1 || c.getRow() > this.height-1) 
                ||( c.getCol() < 0 || c.getRow() < 0)) {
            throw new CellException(c.getRow(), c.getCol(), "Ezen a koordinátán nem szerepel cella a labirintusban!");
        }
        int idx = c.getRow() * this.width + c.getCol();
        return cellListOfLabyrinth.get(idx).getType();
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        cellListOfLabyrinth.clear();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Coordinate c = new Coordinate(j, i);
                cellListOfLabyrinth.add(new Cell(c));
            }
        }
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (c.getCol() > this.width || c.getRow() > this.height) {
            throw new CellException(c.getRow(), c.getCol(), "Ezen a koordinátán nem szerepel cella a labirintusban!");
        }
        int idx = c.getRow() * this.width + c.getCol();
        cellListOfLabyrinth.get(idx).setType(type);
    }

    @Override
    public Coordinate getPlayerPosition() {
        return this.playerPosition.getCoordinate();
    }

    @Override
    public boolean hasPlayerFinished() {
        if (playerPosition.getType() == CellType.END) {
            return true;
        }
        return false;
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> posMoves = new ArrayList<>();
        Coordinate player = playerPosition.getCoordinate();

        int idxPlayer = player.getRow() * this.width + player.getCol();

        Coordinate north = idxPlayer - this.width >= 0 ?
                cellListOfLabyrinth.get(idxPlayer - this.width).getCoordinate() : null;

        Coordinate south = idxPlayer + this.width <= this.height * this.width - 1 ?
                cellListOfLabyrinth.get(idxPlayer + this.width).getCoordinate() : null;

        Coordinate west = idxPlayer - 1 >= 0 ?
                cellListOfLabyrinth.get(idxPlayer - 1).getCoordinate() : null;

        Coordinate east = idxPlayer + 1 <= this.width ?
                cellListOfLabyrinth.get(idxPlayer + 1).getCoordinate() : null;


        try {
            if (north != null && (getCellType(north) == CellType.EMPTY || getCellType(north) == CellType.END)) {
                posMoves.add(Direction.NORTH);
            }
            if (south != null && (getCellType(south) == CellType.EMPTY || getCellType(south) == CellType.END)) {
                posMoves.add(Direction.SOUTH);
            }
            if (east != null && (getCellType(east) == CellType.EMPTY || getCellType(east) == CellType.END)) {
                posMoves.add(Direction.EAST);
            }
            if (west != null && (getCellType(west) == CellType.EMPTY || getCellType(west) == CellType.END)) {
                posMoves.add(Direction.WEST);
            }
        } catch (CellException e) {
            throw new RuntimeException("Valami nem stimmelt a possible movesban!");
        }
        return posMoves;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        List<Direction> possibleMoves = possibleMoves();
        int idxPlayer = playerPosition.getCoordinate().getRow() * this.width + playerPosition.getCoordinate().getCol();
        if (!possibleMoves.contains(direction)) {
            throw new InvalidMoveException();
        } else {
            switch (direction) {
                case EAST:
                    setPlayerPosition(cellListOfLabyrinth.get(idxPlayer + 1));
                    break;
                case SOUTH:
                    setPlayerPosition(cellListOfLabyrinth.get(idxPlayer + this.width));
                    break;
                case NORTH:
                    setPlayerPosition(cellListOfLabyrinth.get(idxPlayer - this.width));
                    break;
                case WEST:
                    setPlayerPosition(cellListOfLabyrinth.get(idxPlayer - 1));
                    break;
            }
        }
    }

    public void setPlayerPosition(Cell playerPosition) {
        this.playerPosition = playerPosition;
    }
}
