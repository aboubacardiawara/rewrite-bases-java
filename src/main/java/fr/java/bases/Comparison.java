package fr.java.bases;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// peut subir refactoring (duplication. Tu le vois ?) ! 
public class Comparison {

    public static <T> Optional<T> min(List<T> originalLst, Comparator<? super T> comparator) {
        if (originalLst.isEmpty())
            return Optional.empty();
        T currentMin = originalLst.get(0);
        for (T element : originalLst) {
            Boolean newMinFound = comparator.compare(currentMin, element) > 0;
            if (newMinFound) {
                currentMin = element;
            }
        }
        ;
        return Optional.of(currentMin);
    }

    public static <T> Optional<T> max(List<T> originalLst, Comparator<? super T> comparator) {
        if (originalLst.isEmpty())
            return Optional.empty();
        T currentMax = originalLst.get(0);
        for (T element : originalLst) {
            Boolean newMaxFound = comparator.compare(currentMax, element) < 0;
            if (newMaxFound) {
                currentMax = element;
            }
        }
        ;
        return Optional.of(currentMax);
    }
}
