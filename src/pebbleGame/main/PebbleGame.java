package pebbleGame.main;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PebbleGame {

    private ArrayList<Thread> players;
    private int playerCount;
    private final HashMap<Character, Bag> bags;

    // Volatile as we must save all changes to memory directly, ensuring data consistency.
    // lastBag is changed every time a bag is drawn from
    private volatile Bag lastBag;

    // Only one player can modify game finished state at once. This stops the game from running if it becomes true.
    // replace with listener? This works for now regardless.
    private AtomicBoolean gameFinished = new AtomicBoolean(false);

    public PebbleGame() {
        bags = new HashMap<>();
        players = new ArrayList<>();
    }

    /**
     * Reads bag file, loads values to a temporary arrayList and calls the
     * bag constructor with the arrayList as an attribute
     *
     * @param fileLocation Location where file will be written
     */
    public Bag createBlackBag(char name, String fileLocation) {

        try {
            FileReader fr = new FileReader(fileLocation);
            BufferedReader br = new BufferedReader(fr);

            String lineWithoutSpaces = br.readLine().replaceAll(" ", "");
            ArrayList<String> weights = new ArrayList<>
                    (Arrays.asList(lineWithoutSpaces.split(",")));

            if (weights.size() < playerCount * 11) {
                throw new PebbleErrors.NotEnoughPebblesInFileException(
                        "File, " + fileLocation + " did not have enough pebbles, please enter a valid file.");
            }

            ArrayList<Integer> pebbles = new ArrayList<>(weights.size());

            for (String w :
                    weights) {
                int pebble = Integer.parseInt(w);
                if (pebble < 1) {
                    throw new PebbleErrors.NegativePebbleWeightException(
                            "File, " + fileLocation + " was not formatted correctly, make sure all pebble sizes are strictly positive.");
                }

                pebbles.add(pebble);
            }

            return new Bag(name, pebbles, fileLocation, bags);

        } catch (IOException e) {
            System.out.println("File, " + fileLocation + " not found, please enter a valid file path.");
            // System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println("File, " + fileLocation + " was not formatted correctly, please enter a valid file " +
                               "format.");
            // System.out.println(e);
        } catch (PebbleErrors.NotEnoughPebblesInFileException | PebbleErrors.NegativePebbleWeightException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Thread> getPlayers() {
        return players;
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

    public boolean isFinished() {
        return gameFinished.get();
    }

    public void finish(boolean isExit) {
        gameFinished.set(true);

        if (isExit) {
            System.out.println("Game has been exited. Thanks for playing!");
        } else {
            System.out.println("Simulation has concluded. Enter e to terminate program.");
        }
    }

    public synchronized void winCheck(Player player) {
        if (player.getTotalPebbleWeight() == 100 && !isFinished()) {
            System.out.println("Player " + player.playerID + " has won the game! Their pebble weight is 100.");
            finish(false);
        }
    }

    public void setLastBag(Bag lastBag) {
        this.lastBag = lastBag;
    }

    public class Player extends Thread {

        private final int playerID;
        private int[] pebbles;
        private final String outputFile;

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
        }

        /**
         * Returns a random black bag
         *
         * @return chosenBag - The chosen black bag.
         */
        public Bag getRandomBlackBag() {
            int index = new Random().nextInt(3);
            char bagChar = switch (index) {
                case 0 -> 'X';
                case 1 -> 'Y';
                case 2 -> 'Z';
                default -> ' ';
            };
            return bags.get(bagChar);
        }

        public void setPebbles(int[] pebble){
            this.pebbles = pebble;
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
         * Runs a method getting rid of the pebble with a random weight and picking a new one from a black bag
         * (terminates program if total weight = 100)
         * MUST BE ATOMIC
         */
        public void swapRandomPebble() throws IOException {

            Bag newBag = getRandomBlackBag();

            int discardedPebbleIndex = new Random().nextInt(pebbles.length);
            int discardedPebble = pebbles[discardedPebbleIndex];

            // Get new pebble
            int newPebble = 0;
            try {
                newPebble = newBag.takeRandomPebble();
            } catch (PebbleErrors.IllegalBagTypeException | PebbleErrors.NegativePebbleWeightException e) {
                e.printStackTrace();
            }

            pebbles[discardedPebbleIndex] = newPebble;


            // Add removed pebble to a white bag, getting corresponding white bag first

            Bag whiteBag;

            try {
                whiteBag = bags.get(lastBag.getCounterpart());

                whiteBag.addPebble(discardedPebble); //adding weight of discarded pebble to white bag

            } catch (PebbleErrors.IllegalBagTypeException e) {
                e.printStackTrace();
            }

            lastBag = newBag;

            this.writeDiscardToFile(discardedPebble, lastBag.getBagName());
            this.writeDrawToFile(newPebble, lastBag.getBagName());

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
         * @param bag             The bag that the pebble was discarded to.
         * @throws IOException
         */
        public void writeDiscardToFile(int discardedPebble, char bag) throws IOException {
            FileWriter writer = new FileWriter(this.outputFile, true);
            writer.write("Player " + this.playerID + " has discarded a " + discardedPebble + " to bag " + bag + "\r\n");
            writePebblesToFile(writer);
        }

        /**
         * Logs a user's draw to their file.
         *
         * @param drawnPebble The weight of the drawn pebble.
         * @param bag         The character of the bag that has been drawn from.
         * @throws IOException
         */
        public void writeDrawToFile(int drawnPebble, char bag) throws IOException {
            FileWriter writer = new FileWriter(this.outputFile, true);
            writer.write("Player " + this.playerID + " has drawn a " + drawnPebble + " from bag " + bag + "\r\n");
            writePebblesToFile(writer);
        }

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

        public String getOutputFile() {
            return outputFile;
        }

        public int[] getPebbles() {
            return pebbles;
        }

        @Override
        public void run() {

            // changed initial draw to happen in thread as dictated by spec sheet
            try {
                // Getting 10 pebbles from  a random bag
                Bag bagToDrawFrom = getRandomBlackBag();
                for (int i = 0; i < 10; i++) {
                    int newPebble = bagToDrawFrom.takeRandomPebble();
                    pebbles[i] = newPebble;
                }

                this.initialWrite(bagToDrawFrom.getBagName());

                lastBag = bagToDrawFrom;

            } catch (IOException | PebbleErrors.IllegalBagTypeException | PebbleErrors.NegativePebbleWeightException e) {
                //TODO: Handle these exception
                e.printStackTrace();
            }


//          isFinished is the game finish flag
            while (!isFinished()) {
//                TODO: This code works, however idk if it's better to use wait/sleep

                winCheck(this);


//                if (!isFinished())
//                    System.out.println("Player " + this.playerID + " weight " + this.getTotalPebbleWeight());
//                above print statement is helpful for figuring out issues.


                try {
                    if (!isFinished())
                        this.swapRandomPebble();
                } catch (IOException e) {
                    //TODO: Handle this IOException

                    e.printStackTrace();
                }


            }
        }
    }

}