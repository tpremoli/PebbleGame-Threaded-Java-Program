package pebbleGame.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Bag {

    /**
     * Enum class for bag types
     */
    public enum BagType {
        WHITE,
        BLACK
    }

    private final HashMap<Character, Bag> bags;
    private final BagType type;
    private String fileLocation;
    // Volatile as we must save all changes to memory directly, ensuring data consistency.
    // pebbles changes when threads draw from it.
    private volatile ArrayList<Integer> pebbles;
    private final char bagName;

    /**
     * Constructor for black bags, is called when an ArrayList of pebble weights is specified as a parameter
     *
     * @param pebbles      the ArrayList of pebble weights
     * @param fileLocation the string of the file location of the pebble weights
     */
    public Bag(char name, ArrayList<Integer> pebbles, String fileLocation, HashMap<Character, Bag> bags) {
        this.bagName = name;
        this.pebbles = pebbles;
        this.type = BagType.BLACK;
        this.fileLocation = fileLocation;
        this.bags = bags;
    }

    /**
     * Constructor for a white bag, is called when no pebble ArrayList is specified (empty bag)
     *
     * @param name the char name of the bag
     */
    public Bag(char name, HashMap<Character, Bag> bags) {
        this.bagName = name;
        this.pebbles = new ArrayList<>();
        this.type = BagType.WHITE;
        this.bags = bags;

        // TODO: Create csv file to store bag contents?
    }

    /**
     * Only on WHITE bags - Adds a pebble of specified weight
     */
    public synchronized void addPebble(int weight) throws PebbleErrors.IllegalBagTypeException {
        if (type == BagType.BLACK) {
            throw new PebbleErrors.IllegalBagTypeException("Tried to run addPebble from BLACK bag");
        }
        this.pebbles.add(weight);
    }

    /**
     * Only on BLACK bags - returns a random pebble from the bag.
     */
    public synchronized int takeRandomPebble() throws PebbleErrors.IllegalBagTypeException, PebbleErrors.NegativePebbleWeightException {
        if (type == BagType.WHITE) {
            throw new PebbleErrors.IllegalBagTypeException("Tried to run takeRandomPebble from WHITE bag");
        }

        if (pebbles.size() == 0) {
            char bagSwap = getCounterpart();
            this.swapContents(bagSwap);
        }

        int newPebbleIndex = new Random().nextInt(pebbles.size());
        int pebble = pebbles.get(newPebbleIndex);
        pebbles.remove(Integer.valueOf(pebble));

        return pebble;
    }

    public char getCounterpart() throws PebbleErrors.IllegalBagTypeException {
        return switch (bagName) {
            case 'X', 't' -> 'A'; // bag 't' is used in testing
            case 'Y' -> 'B';
            case 'Z' -> 'C';
            case 'A' -> 'X';
            case 'B' -> 'Y';
            case 'C' -> 'Z';
            default -> throw new PebbleErrors.IllegalBagTypeException("Error in finding bag counterpart!");
        };

    }

    /**
     * Only on BLACK bags - Swaps contents of this bag with bag b
     *
     * @param b bag to swap content with
     */
    public synchronized void swapContents(char b) throws PebbleErrors.IllegalBagTypeException, PebbleErrors.NegativePebbleWeightException {
        if (type == BagType.WHITE) {
            throw new PebbleErrors.IllegalBagTypeException("Tried to run swapContents from WHITE bag");
        }


        Bag bagToSwap = bags.get(b);

        ArrayList<Integer> emptyPebbles = new ArrayList<>();

        this.setPebbles(bagToSwap.getPebbles());

        bagToSwap.setPebbles(emptyPebbles);
    }

    /**
     * Get/Set methods
     */

    public ArrayList<Integer> getPebbles() {
        return pebbles;
    }

    public void setPebbles(ArrayList<Integer> pebbles) throws PebbleErrors.NegativePebbleWeightException {
        for (int pebble : pebbles){
            if (pebble < 0) {
                throw new PebbleErrors.NegativePebbleWeightException("The pebbbles provided were not formatted " +
                        "correctly, make sure all pebble sizes are strictly positive");
            }
        }
        this.pebbles = pebbles;
    }

    public char getBagName() {
        return bagName;
    }

}
