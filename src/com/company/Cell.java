package com.company;

public class Cell {

    private int coordinateX;
    private int coordinateY;
    private char species;

    public Cell(int coordinateX, int coorindateY) {
        this(coordinateX, coorindateY, 'N');
    }

    public Cell(int coordinateX, int coorindateY, char species) {
        this.coordinateX = coordinateX;
        this.coordinateY = coorindateY;
        this.species = species;
    }

    public void setSpecies(char species) {
        this.species = species;
    }

    public char getSpecies() {
        return species;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }
}
