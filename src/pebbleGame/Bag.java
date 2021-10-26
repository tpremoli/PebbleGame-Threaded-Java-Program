package pebbleGame;

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

    private HashMap<Character, Bag> bags;
    private BagType type;
    private String fileLocation;
    private ArrayList<Integer> pebbles;
    private char bagName;

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
        this.pebbles = new ArrayList<Integer>();
        this.type = BagType.WHITE;
        this.bags = bags;

        // TODO: Create csv file to store bag contents?
    }

    /**
     * Returns how many pebbles are in a bag.
     *
     * @return integer containing bag capacity
     */
    public int checkBagCapacity() {
        int capacity = pebbles.size();
        return capacity;
    }

    /**
     * Only on WHITE bags - Adds a pebble of specified weight
     */
    public void addPebble(int weight) throws PebbleErrors.IllegalBagTypeException {
        if (type == BagType.BLACK) {
            throw new PebbleErrors.IllegalBagTypeException("Tried to run addPebble from BLACK bag");
        }
        this.pebbles.add(weight);
    }

    /**
     * Only on BLACK bags - removes a pebble from the bag at a given index
     */
    public void removePebble(int index) throws PebbleErrors.IllegalBagTypeException {
        if (type == BagType.WHITE) {
            throw new PebbleErrors.IllegalBagTypeException("Tried to run removePebble from WHITE bag");
        }
    }

    /**
     * Only on BLACK bags - removes a pebble from the bag at a given index
     */
    public int takeRandomPebble() throws PebbleErrors.IllegalBagTypeException {
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
        switch (bagName) {
            case 'X':
                return 'A';
            case 'Y':
                return 'B';
            case 'Z':
                return 'C';
            case 'A':
                return 'X';
            case 'B':
                return 'Y';
            case 'C':
                return 'Z';
            default:
                throw new PebbleErrors.IllegalBagTypeException("Error in finding bag counterpart!");
        }

    }

    /**
     * Only on BLACK bags - Swaps contents of this bag with bag b
     *
     * @param b bag to swap content with
     */
    public void swapContents(char b) throws PebbleErrors.IllegalBagTypeException {
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

    public BagType getType() {
        return type;
    }

    public void setType(BagType type) {
        this.type = type;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public ArrayList<Integer> getPebbles() {
        return pebbles;
    }

    public void setPebbles(ArrayList<Integer> pebbles) {
        this.pebbles = pebbles;
    }

    public char getBagName() {
        return bagName;
    }

    public void setBagName(char bagName) {
        this.bagName = bagName;
    }
}
