package com.andymur.pg.cubes.helper;

import com.andymur.pg.cubes.domain.facet.Facet;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author andymur
 */
public final class FacetHelper {

    private static final Facet EMPTY_FACET = new Facet(
            new String[] {
                    "          ",
                    "          ",
                    "          ",
                    "          ",
                    "          ",
            }
    );

    private FacetHelper() {
        throw new IllegalStateException("FacetHelper instantiation is not allowed");
    }

    /**
     * Returns all upturned facets from original set
     * @param original set of facets
     * @return same set of facets but all of them are upturned
     */
    public static Set<Facet> upturned(Set<Facet> original) {
        return original.stream()
                .map(facet -> facet.upturn()).collect(Collectors.toSet());
    }

    /**
     * Returns rotated facets from original set
     * @param original set of facets
     * @return same set of facets but all of them are rotated twice
     */
    public static Set<Facet> rotated(Set<Facet> original) {
        return original.stream()
                .map(facet -> facet.rotate(2)).collect(Collectors.toSet());
    }

    /**
     * Constructs string representation of facets
     *
     * @param facets
     * @return string representation of facets
     */
    public static String facetRowToString(Facet[] facets) {

        if (facets == null || facets.length == 0) {
            throw new IllegalArgumentException("For printing, facets number should be positive integer");
        }

        return Arrays.stream(facetRowToStrings(facets)).collect(Collectors.joining("\n"));
    }


    private static String[] facetRowToStrings(Facet[] facets) {
        String[] result = facets[0] == null ? EMPTY_FACET.toStrings() : facets[0].toStrings();

        for (int i = 1; i < facets.length; i++) {
            if (facets[i] == null) {
                result = combineStrings(result, EMPTY_FACET.toStrings());
            } else {
                result = combineStrings(result, facets[i].toStrings());
            }
        }

        return result;
    }

    private static String[] combineStrings(String[] first, String[] second) {
        String[] result = new String[first.length];

        for (int i = 0; i < first.length; i++) {
            result[i] = first[i].concat(second[i]);
        }

        return result;
    }
}
