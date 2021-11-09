package pebbleGame.main;

import java.io.IOException;
import java.util.*;

public class Main {
    static Scanner reader = new Scanner(System.in);
    public static PebbleGame pg = new PebbleGame();

    public static void main(String[] args) {
        System.out.println(
                """
                        Welcome to the PebbleGame!!!!!! :D xD \r
                        You will be asked to enter the number of players \r
                        and then you will be asked for the location of three files containing\r
                        integer values separated by commas, to determine the pebble weights \r
                        These values must be positive.\r
                        The game will then be simulated, and output written to files in this directory\r
                        """);

//      this will ensure the input sequence will keep running until all inputs are valid.
        boolean inputValid = false;
        while (!inputValid) { // Will change for interrupt
            try {
                System.out.println("Please input number of players:");
                String input = reader.nextLine();

                if (input.equalsIgnoreCase("e")) {
                    throw new PebbleErrors.ExitException();
                }

                int playerCount = Integer.parseInt(input);

                generatePlayers(playerCount);

                inputValid = true;

            } catch (NumberFormatException e) {
                System.out.println("Input not an integer!");
            } catch (PebbleErrors.IllegalPlayerNumberException | PebbleErrors.IllegalBagTypeException |
                    PebbleErrors.NotEnoughPebblesInFileException | IOException e) {
                e.printStackTrace();
            } catch (PebbleErrors.ExitException e) {
                pg.finish(true);
                inputValid = true;
            }
        }

        if (!pg.isFinished()) {

            for (Thread p : pg.getPlayers()) {
                p.start();
            }

            System.out.println("Game is being simulated...");
            System.out.println();


//      This could be done better? Program doesn't stop at all until e is entered.
            while (!pg.isFinished()) {
                if (reader.nextLine().equalsIgnoreCase("e")) {
                    pg.finish(true);
                }
            }

        }


    }

    public static void generatePlayers(int playerCount) throws PebbleErrors.IllegalPlayerNumberException,
            PebbleErrors.IllegalBagTypeException, IOException, PebbleErrors.NotEnoughPebblesInFileException, PebbleErrors.ExitException {
        pg.setPlayerCount(playerCount);

        if (pg.getPlayerCount() < 1) {
            throw new PebbleErrors.IllegalPlayerNumberException("Number of players must be a positive integer!");
        }

        generateBags();

        for (int i = 0; i < pg.getPlayerCount(); i++) {
            pg.getPlayers().add(pg.new Player(i));
        }
    }

    /**
     * generate 3 white (empty, named A, B, C) and 3 black bags (named X, Y, Z),
     * calls createBlackBag method to load values from a bag file and call the black bag constructor.
     */
    public static void generateBags() throws PebbleErrors.ExitException {
        HashMap<Character, Bag> bags = pg.getBags();

        Bag a = new Bag('A', bags);
        Bag b = new Bag('B', bags);
        Bag c = new Bag('C', bags);
        Bag x = null;
        Bag y = null;
        Bag z = null;

        // will run until createBlackBag executes successfully
        while (x == null) {
            System.out.println("Please enter location of bag number 0 to load:");
            String xLoc = reader.nextLine();
//          Exit line
            if (xLoc.equalsIgnoreCase("e")) {
                throw new PebbleErrors.ExitException();
            }
//          creates bag with specified file
            x = pg.createBlackBag('X', xLoc);
        }

        while (y == null) {
            System.out.println("Please enter location of bag number 1 to load:");
            String yLoc = reader.nextLine();
            if (yLoc.equalsIgnoreCase("e")) {
                throw new PebbleErrors.ExitException();
            }
            y = pg.createBlackBag('Y', yLoc);
        }

        while (z == null) {
            System.out.println("Please enter location of bag number 2 to load:");
            String zLoc = reader.nextLine();
            if (zLoc.equalsIgnoreCase("e")) {
                throw new PebbleErrors.ExitException();
            }
            z = pg.createBlackBag('Z', zLoc);
        }

        bags.put('A', a);
        bags.put('B', b);
        bags.put('C', c);
        bags.put('X', x);
        bags.put('Y', y);
        bags.put('Z', z);

    }
}
