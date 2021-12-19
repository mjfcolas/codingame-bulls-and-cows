package fr.li212.codingame.bullsandcows.ia.io;

import fr.li212.codingame.bullsandcows.ia.BullsAndCowsStatus;

public interface SubmitSequence {

    BullsAndCowsStatus submit(final String sequence);
}
