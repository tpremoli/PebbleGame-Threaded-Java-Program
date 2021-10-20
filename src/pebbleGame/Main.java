package pebbleGame;

import java.util.Scanner;

public class Main {
    static Scanner reader = new Scanner(System.in);
    public static PebbleGame pg = new PebbleGame();

    public static void main(String[] args) {
        while (true) { // Will change for interrupt
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

        System.out.println("Please enter location of bag number 0 to load:");
        String xLoc = reader.nextLine();
        Bag x = pg.createBlackBag('X', xLoc);

        System.out.println("Please enter location of bag number 1 to load:");
        String yLoc = reader.nextLine();
        Bag y = pg.createBlackBag('Y', yLoc);

        System.out.println("Please enter location of bag number 2 to load:");
        String zLoc = reader.nextLine();
        Bag z = pg.createBlackBag('Z', zLoc);

        pg.getBags().put('A',a);
        pg.getBags().put('B',b);
        pg.getBags().put('C',c);
        pg.getBags().put('X',x);
        pg.getBags().put('Y',y);
        pg.getBags().put('Z',z);

    }

    /**
     * Exits game when 'E' is pressed on the keyboard
     */
    public static void exitGame() {
        // TODO: Interrupt when E is pressed and exit program
    }
}
