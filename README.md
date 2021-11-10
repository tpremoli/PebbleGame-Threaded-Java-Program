# PebbleGame Threaded java program

ECM2414 project for 2nd-year Computer Science at the University of Exeter. This program simulates a specified number
of players attempting to reach a total weight of 100 from swapping pebbles between bags.

There are six bags of pebbles in the game, three white bags (A, B, and C), and three black bags (X, Y, and Z). At the start of the game, the white bags are empty, and the black bags are full. Each player takes ten pebbles from a black bag (the black bag each player selects is chosen at random). Each pebble has an integer weight value.

If the weight of pebbles held by a player is exactly 100, then they have won. They should immediately announce this fact to other players, and all other simulated players should stop playing. If a player does not hold a winning hand, they should discard a pebble to a white bag. They should then select one of the three black bags at random and draw a new pebble from this bag. If the black bag is empty, the bag should swap its contents with its corresponding white bag (A -> X, B-> Y, C-> Z).

This process should continue until either the player has won (has 10 pebbles with a total combined weight of 100), or until another player has won, and the game has ended.



## Requirements

This project requires Java 16+ (tested and compiled using OpenJDK 17.0.1) to run and uses JUnit 4.13 for testing (bundled in the lib folder).

## Usage

To run the program directly from the Jar file, open the directory containing PebbleGame.jar (root folder) in your systems terminal/command prompt and run:

#### To  run the compiled pebbleGame.Jar file
```cmd
java -jar PebbleGame.jar
```

To run the unit tests from the java files or to  run the main project from the raw .class files, navigate to the project's root folder (folder containing /src/, /lib/ etc.) in your systems terminal/command prompt and run:

#### To run the main  program
```cmd
java -classpath .\src pebbleGame.main.Main
```

#### To run the program's unit tests
```
java -classpath .\src\;.\lib\junit-4.13.1.jar;.\lib\hamcrest-core-1.3.jar pebbleGame.main.PebbleGameTest
```

It is important that you ensure you are in the project's root folder in your terminal/command prompt for the above to work.

#### Re-compiling .java files
If you run into any issues with compatibility, it may help to recompile the project files. You can do this by navigating to the project's root folder in your systems terminal/command prompt and running:
```
javac -cp ./lib/junit-4.13.1.jar @sources.txt
```

If you are testing the program, it may help if you use the provided example input files as inputs for the bags' pebbles. An example of how the program can be used is shown in the screenshot below.

![PebbleGame demo](https://github.com/tpremoli/software-dev-coursework/blob/1ccf382943dd9ce5a75709a0b5e19a237512d724/lib/pebbleGameDemo.png)


## Authors

- [Lucas Proudhon-Smith](https://github.com/lucas-ps)
- [Tomas Premoli](https://github.com/tpremoli)


## License

[MIT](https://choosealicense.com/licenses/mit/)
