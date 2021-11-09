package pebbleGame;

import org.junit.*;
import pebbleGame.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PebbleGameTest {

    PebbleGame pebbleGame;
    Bag testBag;
    PebbleGame.Player testPlayer;
    Main mainGame;

    /**
     * Creates/Resets an example PebbleGame, Bag and Player object to be used for testing
     * Also tests constructors for Bag, PebbleGame and Player, and createBlackBag method
     */
    @Before
    public void createObjects() throws PebbleErrors.IllegalBagTypeException, IOException, PebbleErrors.NotEnoughPebblesInFileException {
        pebbleGame = new PebbleGame();
        testBag = pebbleGame.createBlackBag('t', "./example-input-files/1PlayerAll10s.txt");
        testPlayer = pebbleGame.new Player(-1);
        Main mainGame = new Main();

        // Creating bags
        HashMap<Character, Bag> bags = new HashMap<>();

        pebbleGame.getBags().put('A', new Bag('A', bags));
        pebbleGame.getBags().put('B', new Bag('B', bags));
        pebbleGame.getBags().put('C', new Bag('C', bags));
        pebbleGame.getBags().put('X', testBag);
        pebbleGame.getBags().put('Y', testBag);
        pebbleGame.getBags().put('Z', testBag);

        pebbleGame.setLastBag(testBag);
    }

    @After
    public void deleteFile(){
        File file = new File("./Player -1.txt");
        file.delete();
    }

    @Test
    public void winCheck(){
        testPlayer.setPebbles(new int[]{100, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        pebbleGame.winCheck(testPlayer);
        assert pebbleGame.isFinished() == true;
    }

    /**
     * Tests to see getBags(), getPlayerCount(), getPlayers(), setPlayerCount(int playerCount) are working as  expected
     */
    @Test
    public void pebbbleGameGetSetTests(){
        pebbleGame.getBags().put('t', testBag);
        assert pebbleGame.getBags().get('t') == testBag;

        pebbleGame.setPlayerCount(1);
        assert  pebbleGame.getPlayerCount() == 1;

        assert pebbleGame.getPlayers().size() == 0; // Because no threads have  been  added, just checking an  ArrayList is returned
    }

    // Player class tests
    @Test
    public void getRandomBlackBag(){
        System.out.println(testPlayer.getRandomBlackBag());
        assert testPlayer.getRandomBlackBag() == testBag;
    }

    @Test
    public void getTotalPebbleWeight(){
        assert testPlayer.getTotalPebbleWeight() == 0;
    }

    @Test
    public void swapRandomPebble() throws IOException {
        testPlayer.setPebbles(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        testPlayer.swapRandomPebble();
        assert testPlayer.getTotalPebbleWeight() > 0;
    }

    @Test
    public void writePebblesToFile() throws IOException {
        testPlayer.setPebbles(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        FileWriter writer = new FileWriter(testPlayer.getOutputFile(), true);
        testPlayer.writePebblesToFile(writer);
        List<String> actualContents = Files.readAllLines(Paths.get("./Player -1.txt"));
        List<String> expectedContents = Files.readAllLines(Paths.get("./src/test-files/test_writePebblesToFile.txt"));

        assert actualContents.get(0).equals(expectedContents.get(0));
    }

    @Test
    public void writeDiscardToFile() throws IOException {
        testPlayer.writeDiscardToFile(0, 'A');
        List<String> actualContents = Files.readAllLines(Paths.get("./Player -1.txt"));
        List<String> expectedContents = Files.readAllLines(Paths.get("./src/test-files/test_writeDiscardToFile.txt"));

        assert actualContents.get(0).equals(expectedContents.get(0));
    }

    @Test
    public void initialWrite() throws IOException {
        testPlayer.initialWrite('A');
        List<String> actualContents = Files.readAllLines(Paths.get("./Player -1.txt"));
        List<String> expectedContents = Files.readAllLines(Paths.get("./src/test-files/test_initialWrite.txt"));

        assert actualContents.get(0).equals(expectedContents.get(0));
    }

    @Test
    public void run(){
        testPlayer.run();
        assert testPlayer.getTotalPebbleWeight() > 0; // checking initial draw went as expected
        assert pebbleGame.isFinished();  // checking game is completed as expected
    }

    // Bag class tests
    @Test
    public void addPebble() throws PebbleErrors.IllegalBagTypeException {
        int newPebble = new Random().nextInt(99);

        Boolean IllegalBagTypeExceptionThrown = null;
        try {
            testBag.addPebble(newPebble);
        } catch (PebbleErrors.IllegalBagTypeException e) {
            IllegalBagTypeExceptionThrown = true;
        }
        assert IllegalBagTypeExceptionThrown;

        Bag whiteBag = pebbleGame.getBags().get('A');
        whiteBag.addPebble(newPebble);
        assert whiteBag.getPebbles().get(whiteBag.getPebbles().size() - 1) == newPebble;
    }

    @Test
    public void getCounterpart() throws PebbleErrors.IllegalBagTypeException {
        assert pebbleGame.getBags().get('A').getCounterpart() == 'X';
    }

    @Test
    public void swapContents() throws PebbleErrors.IllegalBagTypeException, PebbleErrors.NotEnoughPebblesInFileException, PebbleErrors.NegativePebbleWeightException {
        // Adding pebbles to be swapped
        Bag newBag = pebbleGame.createBlackBag('T', "./example-input-files/example_file_1.csv");
        pebbleGame.getBags().put('T', newBag);
        ArrayList<Integer> originalNewBagPebbles = newBag.getPebbles();

        testBag.swapContents('T');

        // Checking that contents have  been switched with bag T as expected
        assert testBag.getPebbles().equals(originalNewBagPebbles);
    }

    // Testing main methods
    @Test
    public void IllegalPlayerNumberException() throws PebbleErrors.IllegalBagTypeException, PebbleErrors.IllegalPlayerNumberException, IOException {
        Boolean IllegalPlayerNumberExceptionThrown = false;
        // Checking that IllegalPlayerNumberException  is thrown as expected for invalid  player counts
        try{
            mainGame.generatePlayers(-1);
        } catch (PebbleErrors.IllegalPlayerNumberException e)  {
            IllegalPlayerNumberExceptionThrown = true;
        } catch (PebbleErrors.NotEnoughPebblesInFileException | PebbleErrors.ExitException e){
            e.printStackTrace();
        }
        assert IllegalPlayerNumberExceptionThrown;
    }

    @Test
    public void IllegalBagTypeException() throws PebbleErrors.IllegalBagTypeException {
        Boolean IllegalBagTypeExceptionThrown = null;
        try{
            testBag.addPebble(5);
        } catch (PebbleErrors.IllegalBagTypeException e) {
            IllegalBagTypeExceptionThrown = true;
        }
        assert IllegalBagTypeExceptionThrown;
    }
}