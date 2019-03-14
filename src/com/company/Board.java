package com.company;

import java.util.ArrayList;

public class Board {

    private ArrayList<Cell> cells;
    private ArrayList<Cell> cellsForNewborns;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    private int endOfX;
    private int endOfY;
    private int round;

    public int getRound() {
        return round;
    }

    public Board(int endOfX, int endOfY) {
        this.endOfX = endOfX;
        this.endOfY = endOfY;
        this.xMax = 0;
        this.yMax = 0;
        this.xMin = endOfX;
        this.xMin = endOfY;
        this.cells = new ArrayList<Cell>();
        this.cellsForNewborns = new ArrayList<Cell>();
        this.round = 0;
    }

    public int getxMin() {
        return xMin;
    }


    public int getxMax() {
        return xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public void addCells(Cell cell) {
        cells.add(cell);
        //beállítani a táblán szereplő cellák minimum és maximum koordinátáinak értékét;
        if (this.xMax < cell.getCoordinateX())
            this.xMax = cell.getCoordinateX();
        if (this.xMin > cell.getCoordinateX())
            this.xMin = cell.getCoordinateY();
        if (this.yMax < cell.getCoordinateY())
            this.yMax = cell.getCoordinateY();
        if (this.yMin > cell.getCoordinateY())
            this.yMin = cell.getCoordinateY();


    }


    public void generateStart(Cell startingCell, int numberOfStartingCells) {

        int alreadyCreatedCells = 0;
        char sign = startingCell.getSpecies();

        while (alreadyCreatedCells < numberOfStartingCells) {

            int randX = (int) (Math.random() * 10) - 5 + startingCell.getCoordinateX();
            int randY = (int) (Math.random() * 10) - 5 + startingCell.getCoordinateY();

            if (!cellIsOccupied(randX, randY)) {
                addCells(new Cell(randX, randY, sign));
            }

            alreadyCreatedCells = countCellsOfSpecies(sign);

        }


    }

    private int countCellsOfSpecies(char sign) {

        int result = 0;
        for (Cell cell : cells)
            if (cell.getSpecies() == sign)
                result++;

        return result;
    }

    private boolean cellIsOccupied(int coorX, int coorY) {
        for (Cell cell : cells)
            if (cell.getCoordinateX() == coorX && cell.getCoordinateY() == coorY)
                return true;

        return false;
    }

    public void printMatrix() {
        setMinAndMax();
        for (int i = this.xMin; i <= this.xMax; i++) {
            for (int j = this.yMin; j <= this.yMax; j++) {
                if (cellIsOccupied(i, j)) {
                    System.out.print(getCellSign(i, j) + " ");
                } else
                    System.out.print(". ");

            }
            System.out.println();
        }
        System.out.println();
        System.out.println("-----------------------------------------");

    }

    private void setMinAndMax() {
        this.xMax = 0;
        this.yMax = 0;
        this.xMin = endOfX;
        this.xMin = endOfY;
        for (Cell cell : cells) {
            if (this.xMax < cell.getCoordinateX())
                this.xMax = cell.getCoordinateX();
            if (this.xMin > cell.getCoordinateX())
                this.xMin = cell.getCoordinateX();
            if (this.yMax < cell.getCoordinateY())
                this.yMax = cell.getCoordinateY();
            if (this.yMin > cell.getCoordinateY())
                this.yMin = cell.getCoordinateY();
        }
    }

    private ArrayList<Cell> measureSizeOfGroup(ArrayList<Cell> sackToMeasure, int coorX, int coorY) {

        //végignézzük a szomszédokat és ha látunk olyat, aki nincs benne a zsákban, átadjuk a számolást
        for (Cell cell : cells) {
            if (!sackToMeasure.contains(cell) && neighbourSameSpecie(cell, coorX, coorY)) {

                sackToMeasure.add(cell);
                measureSizeOfGroup(sackToMeasure, cell.getCoordinateX(), cell.getCoordinateY());
            }
        }

        return sackToMeasure;
    }


    private int numberOfNeighbours(int coorX, int coorY) {
        int result = 0;
        for (Cell cell : cells)
            if (!theSameCell(cell, coorX, coorY) && neighbourSameSpecie(cell, coorX, coorY)) {
                result++;
            }


        return result;
    }

    private boolean neighbourSameSpecie(Cell cell, int coorX, int coorY) {
        return cell.getSpecies() == getCellSign(coorX, coorY) &&
                cell.getCoordinateX() + 1 >= coorX && cell.getCoordinateX() - 1 <= coorX &&
                cell.getCoordinateY() + 1 >= coorY && cell.getCoordinateY() - 1 <= coorY;

    }

    private boolean theSameCell(Cell cell, int coorX, int coorY) {
        return cell.getCoordinateX() == coorX && cell.getCoordinateY() == coorY;
    }

    private char getCellSign(int coorX, int coorY) {
        for (Cell cell : cells)
            if (cell.getCoordinateX() == coorX && cell.getCoordinateY() == coorY)
                return cell.getSpecies();

        return 'N';
    }

    public void warOfSpecies() {

        boolean noEnemy = false;
        this.round++;


        while (!noEnemy) {
            noEnemy = true;
            for (Cell cellI : cells) {
                for (Cell cellJ : cells) {
                    if (neighbouredEnemies(cellI, cellJ)) {
                        noEnemy = false;
                        ArrayList<Cell> sackToMeasureI = new ArrayList<Cell>();
                        int sizeOfGroupI = measureSizeOfGroup(sackToMeasureI, cellI.getCoordinateX(), cellI.getCoordinateY()).size();
                        ArrayList<Cell> sackToMeasureJ = new ArrayList<Cell>();
                        int sizeOfGroupJ = measureSizeOfGroup(sackToMeasureJ, cellJ.getCoordinateX(), cellJ.getCoordinateY()).size();
                        if (sizeOfGroupI == sizeOfGroupJ) {
                            cells.remove(cellI);
                            cells.remove(cellJ);
                        } else if (sizeOfGroupI > sizeOfGroupJ) {
                            cellJ.setSpecies(cellI.getSpecies());
                        } else {
                            cellI.setSpecies(cellJ.getSpecies());
                        }
                        break;
                    }

                }
                if (!noEnemy)
                    break;
            }
        }


    }

    private boolean neighbouredEnemies(Cell cellI, Cell cellJ) {
        return cellI.getSpecies() != cellJ.getSpecies() &&
                cellI.getCoordinateX() + 1 >= cellJ.getCoordinateX() &&
                cellI.getCoordinateX() - 1 <= cellJ.getCoordinateX() &&
                cellI.getCoordinateY() + 1 >= cellJ.getCoordinateY() &&
                cellI.getCoordinateY() - 1 <= cellJ.getCoordinateY();
    }

    public void placesToNewBorn(ArrayList<Species> species) {
        this.cellsForNewborns = new ArrayList<Cell>();
        for (int i = Math.max(this.xMin - 1, 0); i <= Math.min(this.xMax + 1, this.endOfX); i++) {
            for (int j = Math.max(this.yMin - 1, 0); j < Math.min(this.yMax + 1, this.endOfY); j++) {
                // ha ez egy üres cella
                if (!cellIsOccupied(i, j)) {
                    int numberOfOk = 0;
                    char charOfOk = 'N';
                    for (Species spec : species) {
                        if (enoughNeighbour(i, j, spec.getSign(), spec.getNewBornNumber())) {
                            numberOfOk++;
                            charOfOk = spec.getSign();
                        }
                    }
                    //csak akkor adom hozzá az újszülöttekhez, ha egy fajtól vannak a gyerekek
                    if (numberOfOk == 1) {
                        this.cellsForNewborns.add(new Cell(i, j, charOfOk));
                    }
                }


            }
        }

    }

    private boolean enoughNeighbour(int coorX, int coorY, char sign, int newBornNumber) {
        int result = 0;
        for (Cell cell : cells) {
            if (cell.getCoordinateX() + 1 >= coorX && cell.getCoordinateX() - 1 <= coorX &&
                    cell.getCoordinateY() + 1 >= coorY && cell.getCoordinateY() - 1 <= coorY &&
                    cell.getSpecies() == sign)
                result++;
        }

        return result == newBornNumber;
    }

    public void nextGeneration(ArrayList<Species> species) {
        ArrayList<Cell> cellsToDelete = new ArrayList<Cell>();
        for (Cell cell : cells) {
            int numberOfNeigh = numberOfNeighbours(cell.getCoordinateX(), cell.getCoordinateY());
            int underPop = underPopLevel(cell.getSpecies(), species);
            int overPop = overPopLevel(cell.getSpecies(), species);
            if (numberOfNeigh < underPop || numberOfNeigh > overPop) {
                cellsToDelete.add(cell);
            }
        }
        for (Cell cell : cellsToDelete) {
            cells.remove(cell);
        }
        for (Cell cell: cellsForNewborns) {
            cells.add(cell);
        }
    }

    private int underPopLevel(char specieSign, ArrayList<Species> species) {

        for (Species spec : species)
            if (specieSign == spec.getSign())
                return spec.getUnderPopulateNumber();

        return 0;
    }

    private int overPopLevel(char specieSign, ArrayList<Species> species) {

        for (Species spec : species)
            if (specieSign == spec.getSign())
                return spec.getOverPopulateNumber();

        return 0;
    }

    public boolean allLiving(ArrayList<Species> species) {

        int numberOfSpecies = 0;

        for (Species spec: species) {
            if (countCellsOfSpecies(spec.getSign()) > 0) {
                numberOfSpecies++;
            }
            System.out.println(spec.getSign() + " - " + countCellsOfSpecies(spec.getSign()));

        }
        System.out.println(numberOfSpecies);
        return numberOfSpecies > 1 ? true :  false;
    }

    public String winnerIs(ArrayList<Species> species) {
        for (Species spec: species) {
            if (countCellsOfSpecies(spec.getSign()) > 0)
                return "" + spec.getSign();
        }
        return "No winner";
    }
}
