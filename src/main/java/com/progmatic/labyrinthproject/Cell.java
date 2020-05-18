package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;

public class Cell {
    private final Coordinate coordinate;
    private CellType type;

    public Cell(Coordinate coordinate, CellType type) {
        this.coordinate = coordinate;
        this.type = type;
    }

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.type = CellType.EMPTY;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }
}
