package com.company;

import java.io.IOException;
import java.util.ArrayList;

public class PebbleGame {


    public static void main(String[] args) {
        System.out.println(
                "Welcome to the PebbleGame!!!!!! :D xD \r\n" +
                "You will be asked to enter the number of players \r\n" +
                "and then you will be asked for the location of three files containing\r\n" +
                "integer values separated by commas, to determine the pebble weights \r\n" +
                "These values must be positive.\r\n" +
                "The game will then be simulated, and output written to files in this directory\r\n");
    }


    /**
     * Arraylist of Players
     */
    public static ArrayList<Player> players;

    /**
     * Arraylist of Bags
     */
    public static ArrayList<Bag> bags;

    /**
     * Last bag drawn from
     */
    public static char lastBag;



    /**
     * generate 3 white (empty, named A, B, C) and 3 black bags (named X, Y, Z),
     * calls createBlackBag method to load values from a bag file and call the black bag constructor.
     */
    public static void generateBags() {

    }

    /**
     * Reads bag file, loads values to a temporary arrayList and calls the
     * bag constructor with the arrayList as an attribute
     *
     * @param fileLocation
     */
    public static void createBlackBag(String fileLocation)
            throws  PebbleErrors.IllegalFileException,
                    PebbleErrors.NotEnoughPebblesInFileException,
                    PebbleErrors.NegativePebbleWeightException,
                    IOException {


    }

    /**
     * Exits game when 'E' is pressed on the keyboard
     */
    public static void exitGame() {
        // TODO: Interrupt when E is pressed and exit program
    }


    class Player implements Runnable {


        /**
         * Array of pebbles the player is holding
         */
        private int[] pebbles = new int[10];


        /**
         * Location of file output for this player
         */
        private String outputFile;


        /**
         * @return Returns total pebble weight
         */
        public int getTotalPebbleWeight() {

            return 0;
        }

        /**
         * Runs a method getting rid of the pebble with the specified weight and picking a new one from a black bag
         * (terminates program if total weight = 100)
         *
         * MUST BE ATOMIC
         *
         * @param pebbleWeight of pebble to discard
         * @return bag where pebble was drawn from
         */
        public char swapPebble(int pebbleWeight) {
            //TODO: swapPebble method
        }

        /**
         * Writes userdata to file
         *
         * @throws IOException
         */
        public void writeDataToFile() throws IOException{

        }

        /**
         * Constructor for player object,
         * <p>
         * Draw 10 pebbles from random bags and put into pebbles arraylist
         */
        public Player() {

        }

        @Override
        public void run() {

        }
    }

}