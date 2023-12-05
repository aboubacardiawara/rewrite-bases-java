package fr.java.bases;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Map {

    public static <T, D> List<D> map(List<T> originalLst, Function<T, D> function) {
        List<D> results = new ArrayList<>();
        originalLst.forEach(element -> results.add(function.apply(element)));
        return results;
    }
}
