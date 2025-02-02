package fr.java.bases;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Filter {

    public static <T> List<T> filter(List<T> originalLst, Predicate<T> predicate) {
        List<T> filteredElements = new ArrayList<>();
        originalLst.forEach(element -> {
            if (predicate.test(element))
                filteredElements.add(element);
        });
        return filteredElements;
    }

}