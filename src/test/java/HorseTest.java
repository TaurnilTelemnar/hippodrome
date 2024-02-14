import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


class HorseTest {
    String messageNameCannotBeNull = "Name cannot be null.";
    String messageNameCannotBeBlank = "Name cannot be blank.";
    String messageSpeedCannotBeNegative = "Speed cannot be negative.";
    String messageDistanceCannotBeNegative = "Distance cannot be negative.";
    String horseName = "SomeName";
    double horseSpeed = 15.0;
    double horseDistance = 15.0;
    @Test
    void createHorseWithNullNameAndCheckThenExceptionThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, horseSpeed, horseDistance);
        });
    }
    @Test
    void createHorseWithNullNameAndCheckThenExceptionHasCorrectMessage(){
        IllegalArgumentException nameCannotBeNullException = null;
        try{
            new Horse(null, horseSpeed, horseDistance);
        }catch (IllegalArgumentException illegalArgumentException){
            nameCannotBeNullException = illegalArgumentException;
        }
        assert nameCannotBeNullException != null;

        Assertions.assertEquals(messageNameCannotBeNull, nameCannotBeNullException.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\t", "\n", "\f", "\r"})
    void createHorseWithBlankNameAndCheckThenExceptionThrows(String argument){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse(argument, horseSpeed, horseDistance);
        });
    }
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\t", "\n", "\f", "\r"})
    void createHorseWithBlankNameAndCheckThenExceptionHasCorrectMessage(String argument){
        IllegalArgumentException nameCannotBeBlankException = null;
        try{
            new Horse(argument, horseSpeed, horseDistance);
        }catch (IllegalArgumentException illegalArgumentException){
            nameCannotBeBlankException = illegalArgumentException;
        }
        assert nameCannotBeBlankException != null;

        Assertions.assertEquals(messageNameCannotBeBlank, nameCannotBeBlankException.getMessage());
    }
    @Test
    void createHorseWithNegativeSpeedAndCheckThenExceptionThrows(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse(horseName, -1, horseDistance);
        });
    }
    @Test
    void createHorseWithNegativeSpeedAndCheckThenExceptionHasCorrectMessage(){
        IllegalArgumentException speedCannotBeNegative = null;
        try{
            new Horse(horseName, -1, horseDistance);
        }catch (IllegalArgumentException illegalArgumentException){
            speedCannotBeNegative = illegalArgumentException;
        }
        assert speedCannotBeNegative != null;

        Assertions.assertEquals(messageSpeedCannotBeNegative, speedCannotBeNegative.getMessage());
    }

    @Test
    void createHorseWithNegativeDistanceAndCheckThrowsAnException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse(horseName, horseSpeed, -1);
        });
    }

    @Test
    void createHorseWithNegativeDistanceAndCheckThenExceptionHasCorrectMessage(){
        IllegalArgumentException distanceCannotBeNegative = null;
        try{
            new Horse(horseName, horseSpeed, -1);
        }catch (IllegalArgumentException illegalArgumentException){
            distanceCannotBeNegative = illegalArgumentException;
        }
        assert distanceCannotBeNegative != null;

        Assertions.assertEquals(messageDistanceCannotBeNegative, distanceCannotBeNegative.getMessage());
    }


    @Test
    void checkGetNameThenReturnTheCurrentName() {

        Horse horse = new Horse(horseName, horseSpeed, horseDistance);
        Assertions.assertEquals(horse.getName(), horseName);
    }

    @Test
    void checkGetSpeedThenReturnTheCurrentSpeed() {

        Horse horse = new Horse(horseName, horseSpeed, horseDistance);
        Assertions.assertEquals(horseSpeed, horse.getSpeed());
    }

    @Test
    void checkGetDistanceThenReturnTheCurrentDistance() {

        Horse horseWithDistance = new Horse(horseName, horseSpeed, horseDistance);
        Assertions.assertEquals(horseDistance, horseWithDistance.getDistance());
    }
    @Test
    void checkGetDistanceWhenHorseWithoutDistanceThenReturnZero() {
        Horse horseWithoutDistance = new Horse(horseName, horseSpeed);
        Assertions.assertEquals(0, horseWithoutDistance.getDistance());
    }

    @Test
    void checkGetRandomDoubleInMoveThenItWorks() {
        Horse horse = new Horse(horseName, horseSpeed, horseDistance);
        try(MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)){
            horse.move();
            staticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.4, 0.5, 0.6, 0.7, 0.8})
    void checkMoveThenDistanceAcceptCorrectValue(double randomDouble) {
        Horse horse = new Horse(horseName, horseSpeed, horseDistance);
        try(MockedStatic<Horse> staticHorse = Mockito.mockStatic(Horse.class)){
            staticHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);
            double horseDistance = horse.getDistance();
            double horseSpeed = horse.getSpeed();
            horse.move();
            horseDistance += horseSpeed * randomDouble;
            Assertions.assertEquals(horseDistance, horse.getDistance());
        }
    }

}