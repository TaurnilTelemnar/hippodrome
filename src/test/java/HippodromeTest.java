import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class HippodromeTest {
    String messageHorsesCannotBeNull = "Horses cannot be null.";
    String messageHorsesCannotBeEmpty = "Horses cannot be empty.";

    @Test
    void createHippodromeWithNullHorsesAndCheckThenExceptionThrows(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
    }
    @Test
    void createHippodromeWithNullHorsesAndCheckThenExceptionHasCorrectMessage(){

        IllegalArgumentException horsesCannotBeNullException = null;
        try {
            new Hippodrome(null);
        }catch (IllegalArgumentException illegalArgumentException){
            horsesCannotBeNullException = illegalArgumentException;
        }
        assert horsesCannotBeNullException != null;

        Assertions.assertEquals(messageHorsesCannotBeNull, horsesCannotBeNullException.getMessage());
    }
    @Test
    void createHippodromeWithBlankListOfHorsesAndCheckThenExceptionThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(Collections.emptyList());
        });
    }
    @Test
    void createHippodromeWithBlankListOfHorsesAndCheckThenExceptionHasCorrectMessage(){

        IllegalArgumentException horsesCannotBeEmptyException = null;
        try {
            new Hippodrome(Collections.emptyList());
        }catch (IllegalArgumentException illegalArgumentException){
            horsesCannotBeEmptyException = illegalArgumentException;
        }
        assert horsesCannotBeEmptyException != null;
        Assertions.assertEquals(messageHorsesCannotBeEmpty, horsesCannotBeEmptyException.getMessage());
    }

    @Test
    void checkGetHorsesThenReturnTheCurrentListOfHorses() {
        List<Horse> sourceHorses = getRandomHorses(30, false);
        Hippodrome hippodrome = new Hippodrome(sourceHorses);
        Assertions.assertEquals(sourceHorses, hippodrome.getHorses());
    }
    @Test
    void checkMoveThenCallMoveOnEachHorse() {
        List<Horse> mockedHorses = getRandomHorses(50, true);
        Hippodrome hippodrome = new Hippodrome(mockedHorses);
        hippodrome.move();
        hippodrome.getHorses().forEach(horse -> Mockito.verify(horse).move());
    }

    @Test
    void checkGetWinnerThenReturnTheHorseWithMaxDistance() {
        List<Horse> sourceHorses = getRandomHorses(30, false);
        Hippodrome hippodrome = new Hippodrome(sourceHorses);
        hippodrome.move();
        double maxDistance = hippodrome.getHorses().stream().
                max(Comparator.comparing(Horse::getDistance))
                .get().getDistance();
        Assertions.assertEquals(maxDistance, hippodrome.getWinner().getDistance());
    }

    private Horse getRandomHorse(boolean isMock){
        Horse horseMock = Mockito.mock(Horse.class);
        Mockito.doNothing().when(horseMock).move();
        if(isMock) return horseMock;
        else return new Horse(String.valueOf(Math.random()), Math.random(), Math.random());

    }
    private List<Horse> getRandomHorses(int size, boolean isMock){
        List<Horse> randomHorses = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            randomHorses.add(getRandomHorse(isMock));
        }
        return randomHorses;
    }
}