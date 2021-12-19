package fr.li212.codingame.bullsandcows.application.io;

import fr.li212.codingame.bullsandcows.ia.BullsAndCowsStatus;
import fr.li212.codingame.bullsandcows.ia.io.SubmitSequence;

import java.util.Scanner;

public class SubmitCodingameSequence implements SubmitSequence {

    final Scanner in;

    public SubmitCodingameSequence(final Scanner in) {
        this.in = in;
    }

    @Override
    public BullsAndCowsStatus submit(final String sequence) {
        System.out.println(sequence);
        int bulls = in.nextInt();
        int cows = in.nextInt();
        return new BullsAndCowsStatus(sequence, bulls, cows);
    }
}
