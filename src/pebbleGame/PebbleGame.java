package pebbleGame;

import java.io.*;
import java.util.*;

public class PebbleGame {

    private ArrayList<Player> players;
    private int playerCount;
    private HashMap<Character, Bag> bags;
    private char lastBag;

    /**
     * Reads bag file, loads values to a temporary arrayList and calls the
     * bag constructor with the arrayList as an attribute
     *
     * @param fileLocation
     */
    public Bag createBlackBag(char name, String fileLocation) {

        try {
            FileReader fr = new FileReader(fileLocation);
            BufferedReader br = new BufferedReader(fr);

            ArrayList<String> weights = new ArrayList<String>(Arrays.asList(br.readLine().split(",")));

            if (weights.size() < playerCount * 11) {
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
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public HashMap<Character, Bag> getBags() {
        return bags;
    }

    public void setBags(HashMap<Character, Bag> bags) {
        this.bags = bags;
    }

    public char getLastBag() {
        return lastBag;
    }

    public void setLastBag(char lastBag) {
        this.lastBag = lastBag;
    }

    class Player implements Runnable {

        private int[] pebbles = new int[10];
        private String outputFile;

        /**
         * Constructor for player object,
         * <p>
         * Draw 10 pebbles from random bags and put into pebbles arraylist
         */
        public Player() {

        }


        /**
         * @return Returns total pebble weight
         */
        public int getTotalPebbleWeight() {
            int sum = 0;
            for (int p :
                    pebbles) {
                sum += p;
            }

            return sum;
        }

        /**
         * Runs a method getting rid of the pebble with the specified weight and picking a new one from a black bag
         * (terminates program if total weight = 100)
         * MUST BE ATOMIC
         *
         * @param pebbleWeight of pebble to discard
         */
        public void swapPebble(int pebbleWeight) {
            // Get new pebble
            char lastBagChar = lastBag;

            // Getting random bag to get a new pebble from
            int randomBagSelected = new Random().nextInt(2);
            char newBagChar = ' ';

            if (randomBagSelected == 0) {
                newBagChar = 'X';
            } else if (randomBagSelected == 1) {
                newBagChar = 'Y';
            } else {
                newBagChar = 'Z';
            }

            Bag newBag = bags.get(newBagChar);

            // Get new pebble
            int newPebble = 0;
            try {
                newPebble = newBag.takeRandomPebble();
            } catch (PebbleErrors.IllegalBagTypeException e) {
                e.printStackTrace();
            }

            // Swap old pebble with new pebble
            for (int i = 0; i < pebbles.length - 1; i++) {
                if (pebbles[i] == pebbleWeight) {
                    pebbles[i] = newPebble;
                }
            }

            // Add removed pebble to a white bag, getting corresponding white bag first

            Character whiteBagChar;

            if (lastBagChar == 'X') {
                whiteBagChar = 'A';
            } else if (lastBagChar == 'Y') {
                whiteBagChar = 'B';
            } else if (lastBagChar == 'Z') {
                whiteBagChar = 'C';
            } else {
                throw new IllegalArgumentException("The character stored in newBag attribute does not correspond to a " +
                        "valid black bag.");
            }

            Bag whiteBag = bags.get(whiteBagChar);

            try {
                whiteBag.addPebble(pebbleWeight); //adding weight of discarded pebble to white bag
            } catch (PebbleErrors.IllegalBagTypeException e) {
                e.printStackTrace();
            }

            lastBag = newBag.getBagName();
            // TODO: Method to see if some has won
        }


        /**
         * Writes userdata to file
         *
         * @throws IOException
         */
        public void writePebblesToFile(FileWriter writer) throws IOException {
            String pebblesString = pebbles.toString()
                    .replace("[", "")
                    .replace("]", "");
            writer.write("\tPlayer " + playerID + " hand is " + pebblesString);
            writer.close();
        }

        /**
         * Logs a user's discard to their file.
         *
         * @param discardedPebble The weight of the discarded pebble.
         * @param bag The bag that the pebble was discarded to.
         * @throws IOException
         */
        public void writeDiscardToFile(int discardedPebble, char bag) throws IOException {
            FileWriter writer = new FileWriter(this.outputFile, true);
            writer.write("Player "+this.playerID+" has discarded a "+discardedPebble+" to bag "+bag+"\r\n");
            writePebblesToFile(writer);
        }

        /**
         * Logs a user's draw to their file.
         *
         * @param drawnPebble The weight of the drawn pebble.
         * @param bag The character of the bag that has been drawn from.
         * @throws IOException
         */
        public void writeDrawToFile(int drawnPebble, char bag) throws IOException {
            FileWriter writer = new FileWriter(this.outputFile, true);
            writer.write("Player "+this.playerID+" has drawn a "+drawnPebble+" from bag "+bag+"\r\n");
            writePebblesToFile(writer);
        }

        // TODO: Create file?
        /**
         * Create player file, write initial pebble array to file.
         *
         * @param initialBag The bag which as initially drawn from
         * @throws IOException
         */
        public void initialWrite(char initialBag) throws IOException {
            FileWriter writer = new FileWriter(this.outputFile, true);
            writer.write("Player has drawn 10 pebbles from " + initialBag + "\r\n");
            writePebblesToFile(writer);
        }



        @Override
        public void run() {

        }
    }

}