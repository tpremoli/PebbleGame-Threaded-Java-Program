package pebbleGame;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class PebbleGame {

    private ArrayList<Thread> players;
    private int playerCount;
    private HashMap<Character, Bag> bags;
    private Bag lastBag;

    public PebbleGame(){
        bags = new HashMap<>();
        players = new ArrayList<>();
    }

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

            String lineWithoutSpaces = br.readLine().replaceAll(" ", "");
            ArrayList<String> weights = new ArrayList<String>
                    (Arrays.asList(lineWithoutSpaces.split(",")));

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

            Bag b = new Bag(name, pebbles, fileLocation, bags);
            return b;

        } catch (IOException e) {
            System.out.println("File, " + fileLocation + " not found, please enter a valid file path.");
            // System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println("File, " + fileLocation + " was not formatted correctly, please enter a valid file " +
                    "format.");
            // System.out.println(e);
        } catch (PebbleErrors.NotEnoughPebblesInFileException e) {
            System.out.println(e.getMessage());
        } catch (PebbleErrors.NegativePebbleWeightException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Thread> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Thread> players) {
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

    public Bag getLastBag() {
        return lastBag;
    }

    public void setLastBag(Bag lastBag) {
        this.lastBag = lastBag;
    }

    class Player extends Thread {

        private int playerID;
        private int[] pebbles;
        private String outputFile;

        /**
         * Constructor for player object,
         * <p>
         * Draw 10 pebbles from random bags and put into pebbles arraylist
         */
        public Player(int playerID) throws PebbleErrors.IllegalBagTypeException, IOException {
            this.playerID = playerID;
            this.pebbles = new int[10];

            // Creating output file, writing pebbles to it using writeDataToFile()
            this.outputFile = "Player " + playerID + ".txt";
            //TODO: verify location of output file

            // Getting 10 pebbles from  a random bag
            Bag bagToDrawFrom = getRandomBlackBag();
            for (int i = 0; i < 10; i++) {
                int newPebble = bagToDrawFrom.takeRandomPebble();
                pebbles[i] = newPebble;
            }

            this.initialWrite(bagToDrawFrom.getBagName());



        }

        /**
         * Returns a random black bag
         *
         * @return chosenBag - The chosen black bag.
         */
        public Bag getRandomBlackBag() {
            int index = new Random().nextInt(3);
            char bagChar = ' ';
            switch (index) {
                case 0:
                    bagChar = 'X';
                    break;
                case 1:
                    bagChar = 'Y';
                    break;
                case 2:
                    bagChar = 'Z';
                    break;
            }
            Bag chosenBag = bags.get(bagChar);
            return chosenBag;
        }

        /**
         * @return Returns total pebble weight
         */
        public int getTotalPebbleWeight() {
            int sum = 0;
            for (int p : pebbles) {
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
        public void swapPebble(int pebbleWeight) throws IOException {
            this.writeDiscardToFile(pebbleWeight, lastBag.getBagName());

            Bag newBag = getRandomBlackBag();

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

            Bag whiteBag = null;

            try {
                whiteBag = bags.get(lastBag.getCounterpart());

                whiteBag.addPebble(pebbleWeight); //adding weight of discarded pebble to white bag

            } catch (PebbleErrors.IllegalBagTypeException e) {
                e.printStackTrace();
            }

            lastBag = newBag;
            // TODO: Method to see if some has won

            this.writeDrawToFile(pebbleWeight, lastBag.getBagName());

            /*
                player1 has drawn a 17 from bag Y
                    player1 hand is 1, 2, 45, 6, 7, 8, 56, 23, 12, 17

                player1 has discarded a 45 to bag B
                    player1 hand is 1, 2, 6, 7, 8, 56, 23, 12, 17
           */

        }

        /**
         * Writes a user's pebble array to file.
         *
         * @throws IOException
         */
        public void writePebblesToFile(FileWriter writer) throws IOException {
            String pebblesString = Arrays.toString(pebbles)
                    .replace("[", "")
                    .replace("]", "");
            writer.write("\tPlayer " + playerID + " hand is " + pebblesString + "\r\n");
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

        public int[] getPebbles() {
            return pebbles;
        }

        public void setPebbles(int[] pebbles) {
            this.pebbles = pebbles;
        }

    @Override
    public void run() {

    }
}

}