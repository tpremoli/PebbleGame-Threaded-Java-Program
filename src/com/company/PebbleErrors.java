package com.company;

public class PebbleErrors {

    class IllegalPlayerNumberException extends Exception{
        public IllegalPlayerNumberException(String errorMessage){
            super(errorMessage);
        }
    }

    static class IllegalBagTypeException extends Exception{
        public IllegalBagTypeException(String errorMessage){
            super(errorMessage);
        }
    }

    class IllegalFileException extends Exception{
        public IllegalFileException(String errorMessage){
            super(errorMessage);
        }
    }


    class NotEnoughPebblesInFileException extends Exception{
        public NotEnoughPebblesInFileException(String errorMessage){
            super(errorMessage);
        }
    }

    class NegativePebbleWeightException extends Exception{
        public NegativePebbleWeightException(String errorMessage){
            super(errorMessage);
        }
    }

    class EmptyBagException extends Exception{
        public EmptyBagException(String errorMessage){
            super(errorMessage);
        }
    }
}
