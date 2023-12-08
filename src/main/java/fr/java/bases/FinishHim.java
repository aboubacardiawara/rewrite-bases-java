package fr.java.bases;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FinishHim {

    public static <P, C, S> List<S> fatality(List<P> originalLst,
            Predicate<P> filterTopLevel,
            Function<P, List<C>> mapTopLevel,
            Function<List<C>, List<? extends C>> flatMap,
            Function<C, S> mapSecondLevel,
            Comparator<S> comparator) {
        List<P> filteredElements = Filter.filter(originalLst, filterTopLevel);
        List<List<C>> visitedCitiesGroupbyperson = Map.map(filteredElements, mapTopLevel);
        List<C> allVisitedCities = FlatMap.flatMap(filteredElements, mapTopLevel);
        List<S> countries = Map.map(allVisitedCities, mapSecondLevel);
        List<S> uniqueCountries = MiscFunctions.distinct(countries);
        uniqueCountries.sort(comparator);
        return uniqueCountries;
    }
}
