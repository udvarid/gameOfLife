package com.company;

import java.util.ArrayList;

public class Main {


    public static final int MAX_X = 50;
    public static final int MAX_Y = 50;

    public static void main(String[] args) throws InterruptedException {

        //a fő tábla és a fajok létrehozása
        Board life = new Board(MAX_X, MAX_Y);
        ArrayList<Species> species = new ArrayList<Species>();
        species.add(new Species('X', "X_Species", 3, 2, 3));
        species.add(new Species('O', "O_Species", 3, 2, 3));
        species.add(new Species('A', "A_Species", 3, 2, 3));
        species.add(new Species('B', "B_Species", 3, 2, 3));
        //random cellák életre keltése, a központi cella és az össz darabszám megadásával
        life.generateStart(new Cell(10,10, species.get(0).getSign()), 50);
        life.generateStart(new Cell(30,30, species.get(1).getSign()), 50);
        life.generateStart(new Cell(10,30, species.get(2).getSign()), 50);
        life.generateStart(new Cell(30,10, species.get(3).getSign()), 50);





        while(life.allLiving(species)) {

            life.printMatrix(); // a tábla kirajzolása
            life.warOfSpecies(); // idegen fajok találkozása esetén a harc lefolytatása (nagyobb alakzat megeszi a kisebbett)
            life.placesToNewBorn(species); // lehetséges születési helyek megvizsgálása, azaz a duplikációk kiszűrése
            life.nextGeneration(species); // alul és túlnépesedés kezelése ill. újszülöttek hozzáadása
            Thread.sleep(500);


        }
        life.printMatrix();
        System.out.println("The winner is: " + life.winnerIs(species));
        System.out.println(life.getRound() + " round.");


    }



}