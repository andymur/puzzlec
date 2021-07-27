package com.andymur.pg.cubes.helper;

import com.andymur.pg.cubes.domain.facet.Facet;
import com.andymur.pg.cubes.domain.facet.FacetOrientation;
import com.andymur.pg.cubes.domain.facet.FacetSide;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Main idea is to return all possible orientations for facet (there are eight of them, four for each plane).
 * </p>
 * <p>
 * Facet orientations is triple of original facet, its side (direction) and the fact it is flipped or not.
 * </p>
 * @see com.andymur.pg.cubes.domain.facet.FacetOrientation
 * @see com.andymur.pg.cubes.domain.facet.Facet
 *
 * @author andymur
 */
public final class FacetOrientator {

    private FacetOrientator() {
        throw new IllegalStateException("FacetOrientator instantiation is not allowed");
    }

    /**
     * Generates all possible orientations for facet
     *
     * @param facet original facet for which orientations are generated
     * @return All possible orientations for facet
     */
    public static Set<FacetOrientation> orientations(Facet facet) {
        final Set<FacetOrientation> orientations = new HashSet<>();

        Arrays.asList(FacetSide.NORTH, FacetSide.EAST, FacetSide.SOUTH, FacetSide.WEST).stream()
                .forEach(side -> orientations.add(new FacetOrientation(facet, side, false)));

        Arrays.asList(FacetSide.NORTH, FacetSide.EAST, FacetSide.SOUTH, FacetSide.WEST).stream()
                .forEach(side -> orientations.add(new FacetOrientation(facet, side, true)));

        return orientations;
    }
}
