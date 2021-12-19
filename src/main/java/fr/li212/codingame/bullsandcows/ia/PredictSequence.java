package fr.li212.codingame.bullsandcows.ia;

import fr.li212.codingame.bullsandcows.ia.io.SubmitSequence;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PredictSequence {

    private final SubmitSequence submitSequence;
    private final int sequenceLength;
    private final Map<Integer, Character> finalResult;
    final PermutationMatrix permutationMatrix;
    final Map<Integer, Set<Character>> potentialBulls;

    public PredictSequence(final SubmitSequence submitSequence, final int sequenceLength) {
        final long timeCounter = System.currentTimeMillis();
        this.submitSequence = submitSequence;
        this.sequenceLength = sequenceLength;
        this.finalResult = new HashMap<>();
        permutationMatrix = new PermutationMatrix(sequenceLength - 1, -1, (char) 0);
        findAllPermutations(permutationMatrix, GlobalParameters.ALL_CHARACTERS);
        potentialBulls = new HashMap<>();
        IntStream.range(0, sequenceLength).forEach(index -> {
            potentialBulls.put(index, new HashSet<>());
            potentialBulls.get(index).addAll(Arrays.asList(GlobalParameters.ALL_CHARACTERS));
        });
        potentialBulls.get(0).remove('0');
        System.err.println("TIME: " + (System.currentTimeMillis() - timeCounter));
    }

    public void predict() {
        while (true) {
            final BullsAndCowsStatus status = getStatusForCurrentTurn();
            //System.err.println(status);
            this.completeFinalResult(status);
            //System.err.println(finalResult);
            potentialBulls.forEach(permutationMatrix::prune);
            permutationMatrix.pruneOneBranch(status.getSequence().toCharArray());

            if (finalResult.size() == sequenceLength) {
                return;
            }
        }
    }

    BullsAndCowsStatus getStatusForCurrentTurn() {
        final String currentPermutation = permutationMatrix.getOnePermutation();
        System.err.println(currentPermutation);
        return this.submitSequence.submit(currentPermutation);
    }


    void completeFinalResult(final BullsAndCowsStatus status) {
        final char[] sequence = status.getSequence().toCharArray();

        if (status.getBulls() == sequence.length) {
            for (int i = sequence.length - 1; i >= 0; i--) {
                final Set<Character> foundBull = new HashSet<>();
                foundBull.add(sequence[i]);
                potentialBulls.put(i, foundBull);
            }
        }

        if (status.getBulls() > finalResult.size()) {
            final Set<Integer> charsIndexes = new HashSet<>();
            for (int j = 0; j < sequence.length; j++) {
                if (!finalResult.containsKey(j)) {
                    charsIndexes.add(j);
                }
            }
            for (Integer firstIndexToSwitch : charsIndexes) {
                for (Integer secondIndexToSwitch : charsIndexes.stream().filter(index -> !index.equals(firstIndexToSwitch)).collect(Collectors.toSet())) {
                    final char[] switchedSequence = copyArray(sequence);
                    switchedSequence[firstIndexToSwitch] = sequence[secondIndexToSwitch];
                    switchedSequence[secondIndexToSwitch] = sequence[firstIndexToSwitch];
                    if(switchedSequence[0] == '0'){
                        continue;
                    }
                    final BullsAndCowsStatus newStatus = this.submitSequence.submit(String.copyValueOf(switchedSequence));
                    if(newStatus.getBulls() == status.getBulls()){
                        //No changes, neither is a bull
                        potentialBulls.get(firstIndexToSwitch).remove(sequence[firstIndexToSwitch]);
                        potentialBulls.get(secondIndexToSwitch).remove(sequence[secondIndexToSwitch]);
                    }
                }
            }
        }


        for (int i = sequence.length - 1; i >= 0; i--) {
            if (status.getBulls() <= finalResult.size() && (finalResult.get(i) == null || sequence[i] != finalResult.get(i))) {
                potentialBulls.get(i).remove(sequence[i]);
            }
        }

        potentialBulls.forEach((integer, characters) -> {
            if (characters.size() == 1) {
                finalResult.put(integer, characters.stream().findFirst().orElseThrow(IllegalStateException::new));
            }
        });

    }

    private void findAllPermutations(
            final PermutationMatrix permutationMatrix,
            final Character[] allElementsToPermute
    ) {
        if (permutationMatrix.index > sequenceLength - 2) {
            return;
        }
        for (int i = 0; i < allElementsToPermute.length; i++) {
            if (permutationMatrix.index == -1 && allElementsToPermute[i] == '0') {
                continue;
            }
            final PermutationMatrix nextPermutationMatrix = new PermutationMatrix(permutationMatrix.maxDepth, permutationMatrix.index + 1, allElementsToPermute[i]);
            permutationMatrix.addSuccessor(nextPermutationMatrix);
            findAllPermutations(nextPermutationMatrix, copyArrayExceptOneElement(allElementsToPermute, allElementsToPermute[i]));
        }
    }

    private Character[] copyArrayExceptOneElement(final Character[] toCopy, final char toAvoid) {
        final Character[] result = new Character[toCopy.length - 1];
        int j = 0;
        for (int i = 0; i < toCopy.length; i++) {
            if (toCopy[i] != toAvoid) {
                result[j] = toCopy[i];
                j++;
            }
        }
        return result;
    }

    private char[] copyArray(final char[] toCopy) {
        final char[] result = new char[toCopy.length];
        int j = 0;
        for (int i = 0; i < toCopy.length; i++) {
            result[j] = toCopy[i];
            j++;
        }
        return result;
    }
}
