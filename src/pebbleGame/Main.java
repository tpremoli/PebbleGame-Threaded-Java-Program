package pebbleGame;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static Scanner reader = new Scanner(System.in);
    public static PebbleGame pg = new PebbleGame();

    public static void main(String[] args) {
        while (true) { // Will change for interrupt
            //TODO: Add interrupts
            System.out.println(
                    "Welcome to the PebbleGame!!!!!! :D xD \r\n" +
                            "You will be asked to enter the number of players \r\n" +
                            "and then you will be asked for the location of three files containing\r\n" +
                            "integer values separated by commas, to determine the pebble weights \r\n" +
                            "These values must be positive.\r\n" +
                            "The game will then be simulated, and output written to files in this directory\r\n" +
                            "\r\n" +
                            "\r\n" +
                            "Please input number of players:");


            try {
                pg.setPlayerCount(Integer.parseInt(reader.nextLine()));
                if (pg.getPlayerCount() < 1)
                    throw new PebbleErrors.IllegalPlayerNumberException("Number of players must be a positive integer!");

                generateBags();


                for (int i = 0; i < pg.getPlayerCount(); i++) {
                    pg.getPlayers().add(pg.new Player());
                }

                //players must draw from bags

            } catch (NumberFormatException e) {
                System.out.println("Input not an integer!");
            } catch (PebbleErrors.IllegalPlayerNumberException e) {
                System.out.println(e);
            }
        }

    }

    /**
     * generate 3 white (empty, named A, B, C) and 3 black bags (named X, Y, Z),
     * calls createBlackBag method to load values from a bag file and call the black bag constructor.
     */
    public static void generateBags() {

        Bag a = new Bag('A');
        Bag b = new Bag('B');
        Bag c = new Bag('C');
        Bag x = null;
        Bag y = null;
        Bag z = null;

        // will run until createBlackBag executes successfully
        while (x == null) {
            System.out.println("Please enter location of bag number 0 to load:");
            String xLoc = reader.nextLine();
            x = pg.createBlackBag('X', xLoc);
        }

        while (y == null) {
            System.out.println("Please enter location of bag number 1 to load:");
            String yLoc = reader.nextLine();
            y = pg.createBlackBag('Y', yLoc);
        }

        while (z == null) {
            System.out.println("Please enter location of bag number 2 to load:");
            String zLoc = reader.nextLine();
            z = pg.createBlackBag('Z', zLoc);
        }

        HashMap<Character, Bag> bags = pg.getBags();

        bags.put('A', a);
        bags.put('B', b);
        bags.put('C', c);
        bags.put('X', x);
        bags.put('Y', y);
        bags.put('Z', z);

    }

    /**
     * Exits game when 'E' is pressed on the keyboard
     */
    public static void exitGame() {
        // TODO: Interrupt when E is pressed and exit program
    }
}
