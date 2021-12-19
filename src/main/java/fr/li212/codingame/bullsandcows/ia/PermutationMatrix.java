package fr.li212.codingame.bullsandcows.ia;

import java.util.*;

public class PermutationMatrix{
    final int maxDepth;
    final int index;
    final char value;
    private final List<PermutationMatrix> successors = new ArrayList<>(10);

    public PermutationMatrix(final int maxDepth, final int index, final char value) {
        this.maxDepth = maxDepth;
        this.index = index;
        this.value = value;
    }

    public void addSuccessor(final PermutationMatrix successor) {
        this.successors.add(successor);
    }

    private PermutationMatrix firstSuccessor(){
        return successors.stream().findAny().orElseThrow(IllegalStateException::new);
    }

    public String getOnePermutation() {
        return (value == 0 ? "" : value) + Optional.ofNullable(successors.isEmpty() ? null : firstSuccessor())
                .map(PermutationMatrix::getOnePermutation).orElse("");
    }

    public void pruneOneBranch(final char[] branchToPrune) {
        successors.stream()
                .filter(successor -> successor.value == branchToPrune[index + 1])
                .findAny()
                .ifPresent(successor -> successor.pruneOneBranch(branchToPrune));

        final Optional<PermutationMatrix> toPrunePotentially = successors.stream()
                .filter(successor -> successor.value == branchToPrune[index + 1]).findFirst();
        if (toPrunePotentially.isPresent() && toPrunePotentially.get().successors.isEmpty()) {
            successors.remove(toPrunePotentially.get());
        }
    }

    private void prune(Character characterToRemove) {
        successors.removeIf(successor -> successor.value == characterToRemove);
        for(final PermutationMatrix successor: successors){
            successor.prune(characterToRemove);
        }
    }

    public void prune(int indexToPrune, Set<Character> charactersToKeep) {
        if (index == indexToPrune - 1) {
            successors.removeIf(permutationMatrix -> !charactersToKeep.contains(permutationMatrix.value));
        }
        if (successors.size() == 1) {
            final char characterToRemoveEverywhere = firstSuccessor().value;
            this.successors.forEach(successor -> successor.prune(characterToRemoveEverywhere));
        }
        successors.forEach(permutationMatrix -> permutationMatrix.prune(indexToPrune, charactersToKeep));
    }
}
