import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.stream.Stream;

class HorseTest {

    @Test
    void createHorseWithNullNameAndCheckThenExceptionThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 1, 1);
        });
    }
    @Test
    void createHorseWithNullNameAndCheckThenExceptionHasCorrectMessage(){
        IllegalArgumentException nameCannotBeNullException = null;
        try{
            new Horse(null, 1, 1);
        }catch (IllegalArgumentException illegalArgumentException){
            nameCannotBeNullException = illegalArgumentException;
        }
        assert nameCannotBeNullException != null;
        Assertions.assertEquals(nameCannotBeNullException.getMessage(), "Name cannot be null.");
    }
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\t", "\n", "\f", "\r"})
    void createHorseWithBlankNameAndCheckThenExceptionThrows(String argument){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse(argument, 1, 1);
        });
    }
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\t", "\n", "\f", "\r"})
    void createHorseWithBlankNameAndCheckThenExceptionHasCorrectMessage(String argument){
        IllegalArgumentException nameCannotBeBlankException = null;
        try{
            new Horse(argument, 1, 1);
        }catch (IllegalArgumentException illegalArgumentException){
            nameCannotBeBlankException = illegalArgumentException;
        }
        assert nameCannotBeBlankException != null;
        Assertions.assertEquals(nameCannotBeBlankException.getMessage(), "Name cannot be blank.");
    }
    @Test
    void createHorseWithNegativeSpeedAndCheckThenExceptionThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Some Horse", -1, 1);
        });
    }
    @Test
    void createHorseWithNegativeSpeedAndCheckThenExceptionHasCorrectMessage(){
        IllegalArgumentException speedCannotBeNegative = null;
        try{
            new Horse("Some Horse", -1, 1);
        }catch (IllegalArgumentException illegalArgumentException){
            speedCannotBeNegative = illegalArgumentException;
        }
        assert speedCannotBeNegative != null;
        Assertions.assertEquals(speedCannotBeNegative.getMessage(), "Speed cannot be negative.");
    }

    @Test
    void createHorseWithNegativeDistanceAndCheckThrowsAnException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Some Horse", 1, -1);
        });
    }

    @Test
    void createHorseWithNegativeDistanceAndCheckThenExceptionHasCorrectMessage(){
        IllegalArgumentException distanceCannotBeNegative = null;
        try{
            new Horse("Some Horse", 1, -1);
        }catch (IllegalArgumentException illegalArgumentException){
            distanceCannotBeNegative = illegalArgumentException;
        }
        assert distanceCannotBeNegative != null;
        Assertions.assertEquals(distanceCannotBeNegative.getMessage(), "Distance cannot be negative.");
    }


    @Test
    void checkGetNameThenReturnTheCurrentName() {
        String horseName = "Horse Name";
        Horse horse = new Horse(horseName, 1, 1);
        Assertions.assertEquals(horse.getName(), horseName);
    }

    @Test
    void checkGetSpeedThenReturnTheCurrentSpeed() {
        double horseSpeed = 15.0;
        Horse horse = new Horse("SomeName", horseSpeed, 1);
        Assertions.assertEquals(horse.getSpeed(), horseSpeed);
    }

    @Test
    void checkGetDistanceThenReturnTheCurrentDistance() {
        double horseDistance = 15.0;
        Horse horseWithDistance = new Horse("SomeName", 1, horseDistance);
        Assertions.assertEquals(horseWithDistance.getDistance(), horseDistance);
    }
    @Test
    void checkGetDistanceWhenHorseWithoutDistanceThenReturnZero() {
        Horse horseWithoutDistance = new Horse("SomeName", 1);
        Assertions.assertEquals(horseWithoutDistance.getDistance(), 0);
    }

    @Test
    void checkGetRandomDoubleInMoveThenItWorks() {
        Horse horse = new Horse("SomeName", 1, 1);
        try(MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)){
            horse.move();
            staticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.4, 0.5, 0.6, 0.7, 0.8})
    void checkMoveThenDistanceAcceptCorrectValue(double randomDouble) {
        Horse horse = new Horse("SomeName", 1, 1);
        try(MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)){
            staticHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);
            double horseDistance = horse.getDistance();
            double horseSpeed = horse.getSpeed();
            horse.move();
            horseDistance += horseSpeed * randomDouble;
            Assertions.assertEquals(horse.getDistance(), horseDistance);
        }
    }

}