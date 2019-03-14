package com.company;


public class Species {

    private char sign;
    private String name;
    private int newBornNumber;
    private int underPopulateNumber;
    private int overPopulateNumber;

    public Species(char sign, String name, int newBornNumber, int underPopulateNumber, int overPopulateNumber) {
        this.sign = sign;
        this.name = name;
        this.newBornNumber = newBornNumber;
        this.underPopulateNumber = underPopulateNumber;
        this.overPopulateNumber = overPopulateNumber;
    }

    public Species(char sign, String name) {
        this(sign, name, 3, 2, 3);
    }

    public char getSign() {
        return sign;
    }

    public String getName() {
        return name;
    }

    public int getNewBornNumber() {
        return newBornNumber;
    }

    public void setNewBornNumber(int newBornNumber) {
        this.newBornNumber = newBornNumber;
    }

    public int getUnderPopulateNumber() {
        return underPopulateNumber;
    }

    public void setUnderPopulateNumber(int underPopulateNumber) {
        this.underPopulateNumber = underPopulateNumber;
    }

    public int getOverPopulateNumber() {
        return overPopulateNumber;
    }

    public void setOverPopulateNumber(int overPopulateNumber) {
        this.overPopulateNumber = overPopulateNumber;
    }
}
