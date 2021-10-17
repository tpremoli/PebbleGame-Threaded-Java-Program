package com.company;

import java.util.ArrayList;

public class Bag {
    /**
     * Type of Bag
     */
    public enum BagType{
        WHITE,
        BLACK
    }

    /**
     * This bag's type
     */
    private BagType type;

    /**
     * The bag's provided fileLocation
     */
    private String fileLocation;

    /**
     * Arraylist of weights of pebbles
     */
    private ArrayList<Integer> pebbles;

    /**
     * Bag name (A,B,C; X,Y,Z)
     */
    private char bagName;

    /**
     * Checks bag capacity
     *
     * @return integer containing bag capacity
     */
    public int checkBagCapacity(){

        return 0;
    }

    /**
     * Only on WHITE bags
     *
     * Adds a pebble of specified weight
     */
    public void addPebble() throws PebbleErrors.IllegalBagTypeException {
        if(type == BagType.BLACK){
            throw new PebbleErrors.IllegalBagTypeException("Tried to run addPebble from BLACK bag");
        }

    }

    /**
     * Only on BLACK bags
     *
     * removes a pebble from the bag with a specified weight
     */
    public void removePebble(int weight) throws PebbleErrors.IllegalBagTypeException {
        if(type == BagType.WHITE){
            throw new PebbleErrors.IllegalBagTypeException("Tried to run removePebble from WHITE bag");
        }

    }

    /**
     * Only on BLACK bags
     *
     * Swaps contents of this bag with bag b
     * @param b bag to swap content with
     */
    public void swapContents(Bag b) throws PebbleErrors.IllegalBagTypeException {
        if(type == BagType.WHITE){
            throw new PebbleErrors.IllegalBagTypeException("Tried to run swapContents from WHITE bag");
        }

    }



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
