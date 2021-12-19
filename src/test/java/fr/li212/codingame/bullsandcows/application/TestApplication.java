package fr.li212.codingame.bullsandcows.application;

import fr.li212.codingame.bullsandcows.application.io.SubmitCodingameSequence;
import fr.li212.codingame.bullsandcows.ia.BullsAndCowsStatus;
import fr.li212.codingame.bullsandcows.ia.PredictSequence;
import fr.li212.codingame.bullsandcows.ia.io.SubmitSequence;

import java.util.Arrays;
import java.util.Scanner;

public class TestApplication {

    private static class SubmitTestSequence implements SubmitSequence {

        private static int COUNTER = 0;

        private enum BullsOrCows {
            BULLS,
            COWS
        }

        final String numberToGuess;

        public SubmitTestSequence(final String numberToGuess) {
            this.numberToGuess = numberToGuess;
        }

        @Override
        public BullsAndCowsStatus submit(final String sequence) {
            COUNTER++;
            final char[] sequenceArray = sequence.toCharArray();
            final char[] numberToGuessArray = numberToGuess.toCharArray();
            final BullsOrCows[] result = new BullsOrCows[sequenceArray.length];

            for (int i = 0; i < result.length; i++) {
                result[i] = numberToGuessArray[i] == sequenceArray[i] ? BullsOrCows.BULLS : null;
            }
            for (int i = 0; i < result.length; i++) {
                if (result[i] != BullsOrCows.BULLS) {
                    result[i] = numberToGuess.indexOf(sequenceArray[i]) > -1 ? BullsOrCows.COWS : null;
                }
            }

            final long bulls = Arrays.stream(result).filter(BullsOrCows.BULLS::equals).count();
            final long cows = Arrays.stream(result).filter(BullsOrCows.COWS::equals).count();
            if (bulls == numberToGuessArray.length) {
                System.err.println("TRY " + COUNTER);
            }
            return new BullsAndCowsStatus(sequence, (int) bulls, (int) cows);

        }
    }

    public static void test(final String number) {
        int sequenceLength = number.length();
        final long initTime = System.currentTimeMillis();
        final PredictSequence predictSequence = new PredictSequence(new SubmitTestSequence(number), sequenceLength);
        predictSequence.predict();
        System.err.println(System.currentTimeMillis() - initTime);
    }

    public static void main(final String[] args){
        //TestApplication.test("27469");
        new PredictSequence(new SubmitCodingameSequence(new Scanner(System.in)), 10);
    }
}
