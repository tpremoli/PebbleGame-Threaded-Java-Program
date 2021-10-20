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
        public void writeDataToFile() throws IOException {

        }


        @Override
        public void run() {

        }
    }

}