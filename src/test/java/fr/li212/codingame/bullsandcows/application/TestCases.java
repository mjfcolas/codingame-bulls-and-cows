package fr.li212.codingame.bullsandcows.application;

import fr.li212.codingame.bullsandcows.application.io.SubmitCodingameSequence;
import fr.li212.codingame.bullsandcows.ia.PredictSequence;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Scanner;

public class TestCases {

    @ParameterizedTest
    @ValueSource(strings = {"1234567890"})
    void testOneValue(final String numberToTest) {
        TestApplication.test(numberToTest);
    }

    @ParameterizedTest
    @ValueSource(ints = {6})
    void testInitialization(final int sequenceLength) {
        new PredictSequence(new SubmitCodingameSequence(new Scanner(System.in)), sequenceLength);
    }

}
