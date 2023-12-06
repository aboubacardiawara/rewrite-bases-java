package fr.java.bases;

import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class Reduce {

    public static <T> Optional<T> reduce(List<T> originalLst, BinaryOperator<T> accumulator) {
        if (originalLst.isEmpty())
            return Optional.empty();
        T result = originalLst.get(0);
        for (int i = 1; i < originalLst.size(); i++) {
            result = accumulator.apply(result, originalLst.get(i));
        }
        ;
        return Optional.of(result);
    }

}
