package fr.li212.codingame.bullsandcows.application;

import fr.li212.codingame.bullsandcows.application.io.SubmitCodingameSequence;
import fr.li212.codingame.bullsandcows.ia.PredictSequence;

import java.util.Scanner;

class Player {
    public static void main(final String[] args) {
        Scanner in = new Scanner(System.in);
        int sequenceLength = in.nextInt();
        final PredictSequence predictSequence = new PredictSequence(new SubmitCodingameSequence(in), sequenceLength);
        in.nextInt();
        in.nextInt();
        predictSequence.predict();
    }
}
