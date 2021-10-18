package pebbleGame;

public class PebbleErrors {

    /**
     * thrown when the provided player number is not positive
     */
    static class IllegalPlayerNumberException extends Exception{
        public IllegalPlayerNumberException(String errorMessage){
            super(errorMessage);
        }
    }

    /**
     * Thrown when the a method is used on a bagType that the method cannot be used on
     */
    static class IllegalBagTypeException extends Exception{
        public IllegalBagTypeException(String errorMessage){
            super(errorMessage);
        }
    }

    /**
     * Thrown when there aren't at least 11x the amount of valid pebbles as number of players.
     */
    static class NotEnoughPebblesInFileException extends Exception{
        public NotEnoughPebblesInFileException(String errorMessage){
            super(errorMessage);
        }
    }

    /**
     * Thrown when a pebble weight in a file is negative.
     */
    static class NegativePebbleWeightException extends Exception{
        public NegativePebbleWeightException(String errorMessage){
            super(errorMessage);
        }
    }

    /**
     * Thrown when a player attempts to draw from an empty bag, player should be asked to draw from another bag with
     * pebbles in it.
     */
    class EmptyBagException extends Exception{
        public EmptyBagException(String errorMessage){
            super(errorMessage);
        }
    }
}
