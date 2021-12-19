package fr.li212.codingame.bullsandcows.ia;

public class BullsAndCowsStatus {
    private final String sequence;
    private final int bulls;
    private final int cows;

    public BullsAndCowsStatus(final String sequence, final int bulls, final int cows) {
        this.sequence = sequence;
        this.bulls = bulls;
        this.cows = cows;
    }

    public boolean isComplete() {
        return sequence.length() == bulls;
    }

    public String getSequence() {
        return sequence;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

    @Override
    public String toString() {
        return "BullsAndCowsStatus{" +
                "sequence='" + sequence + '\'' +
                ", bulls=" + bulls +
                ", cows=" + cows +
                '}';
    }
}
