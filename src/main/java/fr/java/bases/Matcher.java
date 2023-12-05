package fr.java.bases;

import java.util.List;
import java.util.function.Predicate;

public class Matcher {
    public static <T> Boolean anyMatch(List<T> originalLst, Predicate<T> predicate) {
        for (T element : originalLst) {
            if (predicate.test(element))
                return true;
        }
        ;
        return false;
    }

    public static <T> Boolean allMatch(List<T> originalLst, Predicate<T> predicate) {
        return !anyMatch(originalLst, predicate.negate());
    }

    public static <T> Boolean noneMatch(List<T> originalLst, Predicate<T> predicate) {
        return allMatch(originalLst, predicate.negate());
    }
}
