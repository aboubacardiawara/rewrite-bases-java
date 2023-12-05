package fr.java.bases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class MiscFunctions {

    public static <T> Long count(List<T> originalLst) {
        Long count = 0L;
        for (T elemenT : originalLst) {
            count++;
        }
        return count;
    }

    public static <T> List<T> distinct(List<T> originalLst) {
        Set<T> collected = new HashSet<>();
        List<T> result = new ArrayList<>();
        originalLst.forEach(element -> {
            if (!collected.contains(element)) {
                collected.add(element);
                result.add(element);
            }
        });
        return result;
    }

    public static <T> Optional<T> findAny(List<T> originalLst) {
        if (originalLst.isEmpty())
            return Optional.empty();
        int i = 0; // should be non-deterministic (random ?)
        return Optional.of(originalLst.get(i));
    }

    public static <T> Optional<T> findFirst(List<T> originalLst) {
        if (originalLst.isEmpty())
            return Optional.empty();
        return Optional.of(originalLst.get(0));
    }

}
