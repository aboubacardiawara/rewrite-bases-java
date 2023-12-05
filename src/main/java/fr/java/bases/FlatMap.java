package fr.java.bases;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FlatMap {

    public static <T, D> List<D> flatMap(List<T> originalLst, Function<T, List<D>> function) {
        List<D> results = new ArrayList<>();
        originalLst.forEach(element -> results.addAll(function.apply(element)));
        return results;
    }

}
