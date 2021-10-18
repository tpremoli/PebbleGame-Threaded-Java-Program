package pebbleGame;

import java.io.*;
import java.util.*;

public class PebbleGame {

    public static ArrayList<Player> players;
    public static HashMap<Character, Bag> bags;
    public static char lastBag;

    static Scanner reader = new Scanner(System.in);


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

        Bag a = new Bag('A');
        Bag b = new Bag('B');
        Bag c = new Bag('C');

        System.out.println("Please enter location of bag number 0 to load:");
        String xLoc = reader.nextLine();
        Bag x = createBlackBag('X', xLoc);

        System.out.println("Please enter location of bag number 1 to load:");
        String yLoc = reader.nextLine();
        Bag y = createBlackBag('Y', yLoc);

        System.out.println("Please enter location of bag number 2 to load:");
        String zLoc = reader.nextLine();
        Bag z = createBlackBag('Z', zLoc);

        bags.put('A',a);
        bags.put('B',b);
        bags.put('C',c);
        bags.put('X',x);
        bags.put('Y',y);
        bags.put('Z',z);

    }

    /**
     * Reads bag file, loads values to a temporary arrayList and calls the
     * bag constructor with the arrayList as an attribute
     *
     * @param fileLocation
     */
    public static Bag createBlackBag(char name, String fileLocation) {

        try {
            FileReader fr = new FileReader(fileLocation);
            BufferedReader br = new BufferedReader(fr);

            ArrayList<String> weights = new ArrayList<String>(Arrays.asList(br.readLine().split(",")));

            if (weights.size() < players.size() * 11) {
                throw new PebbleErrors.NotEnoughPebblesInFileException(
                        "File, " + fileLocation + " did not have enough pebbles, please enter a valid file.");
            }

            ArrayList<Integer> pebbles = new ArrayList<Integer>(weights.size());


            for (String w :
                    weights) {
                int pebble = Integer.parseInt(w);
                if (pebble < 1) {
                    throw new PebbleErrors.NegativePebbleWeightException(
                            "File, " + fileLocation + " was not formatted correctly, make sure all pebble sizes are strictly positive.");
                }

                pebbles.add(pebble);
            }


            Bag b = new Bag(name, pebbles, fileLocation);
            return b;

        } catch (IOException e) {
            System.out.println("File, " + fileLocation + " not found, please enter a valid file path.");
            // System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println("File, " + fileLocation + " was not formatted correctly, please enter a valid file " +
                    "format.");
            // System.out.println(e);
        } catch (PebbleErrors.NotEnoughPebblesInFileException e) {
            System.out.println(e);
        } catch (PebbleErrors.NegativePebbleWeightException e) {
            System.out.println(e);
        }

        fileLocation = reader.nextLine();
        return createBlackBag(name, fileLocation);
    }

    /**
     * Exits game when 'E' is pressed on the keyboard
     */
    public static void exitGame() {
        // TODO: Interrupt when E is pressed and exit program
    }


    class Player implements Runnable {

        private int[] pebbles = new int[10];
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