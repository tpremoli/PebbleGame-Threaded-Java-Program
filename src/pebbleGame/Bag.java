package pebbleGame;

import java.util.ArrayList;

public class Bag {

    /**
     * Enum class for bag types
     */
    public enum BagType{
        WHITE,
        BLACK
    }

    private BagType type;
    private String fileLocation;
    private ArrayList<Integer> pebbles;
    private char bagName;

    /**
     * Constructor for black bags, is called when an ArrayList of pebble weights is specified as a parameter
     * @param pebbles the ArrayList of pebble weights
     * @param fileLocation the string of the file location of the pebble weights
     */
    public Bag(char name, ArrayList<Integer> pebbles, String fileLocation){
        this.bagName = name;
        this.pebbles = pebbles;
        this.type = BagType.BLACK;
        this.fileLocation = fileLocation;
    }

    /**
     * Constructor for a white bag, is called when no pebble ArrayList is specified (empty bag)
     * @param name the char name of the bag
     */
    public Bag(char name){
        this.bagName = name;
        this.pebbles = new ArrayList<Integer>();
        this.type =  BagType.WHITE;
        // TODO: Create csv file to store bag contents?
    }

    /**
     * Returns how many pebbles are in a bag.
     * @return integer containing bag capacity
     */
    public int checkBagCapacity(){
        int capacity = pebbles.size();
        return capacity;
    }

    /**
     * Only on WHITE bags - Adds a pebble of specified weight
     */
    public void addPebble() throws PebbleErrors.IllegalBagTypeException {
        if(type == BagType.BLACK){
            throw new PebbleErrors.IllegalBagTypeException("Tried to run addPebble from BLACK bag");
        }

    }

    /**
     * Only on BLACK bags - removes a pebble from the bag with a specified weight
     */
    public void removePebble(int weight) throws PebbleErrors.IllegalBagTypeException {
        if(type == BagType.WHITE){
            throw new PebbleErrors.IllegalBagTypeException("Tried to run removePebble from WHITE bag");
        }

    }

    /**
     * Only on BLACK bags - Swaps contents of this bag with bag b
     * @param b bag to swap content with
     */
    public void swapContents(Bag b) throws PebbleErrors.IllegalBagTypeException {
        if(type == BagType.WHITE){
            throw new PebbleErrors.IllegalBagTypeException("Tried to run swapContents from WHITE bag");
        }

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
