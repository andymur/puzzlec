package com.andymur.pg.cubes.domain.facet;

import com.andymur.pg.cubes.domain.Edge;
import com.andymur.pg.cubes.helper.FacetOrientator;

import java.util.*;

/**
 * Checks all possible compatible facet orientations.
 *
 * @see com.andymur.pg.cubes.domain.facet.Facet
 * @see com.andymur.pg.cubes.domain.facet.FacetSide
 * @author andymur
 */
public final class FacetChecker {

    Map<FacetOrientation, List<FacetOrientation>> combinationsByFacet = new HashMap<>(128 * 36);

    /**
     * Using list of facets, finds all compatible orientations.
     *
     * @param facets to compare
     * @return list of compatible orientations for each facet orientation
     */
    public Map<FacetOrientation, List<FacetOrientation>> findCombinations(List<Facet> facets) {
        combinationsByFacet.clear();

        for (int i = 0; i < facets.size(); i++) {
            for (int j = 0; j < facets.size(); j++) {
                if (i != j) {
                    findCombinations(facets.get(i), facets.get(j));
                }
            }
        }

        return new HashMap<>(combinationsByFacet);
    }

    void findCombinations(Facet firstFacet, Facet secondFacet) {
        Set<FacetOrientation> firstOrientations = FacetOrientator.orientations(firstFacet);
        Set<FacetOrientation> secondOrientations = FacetOrientator.orientations(secondFacet);

        for (FacetOrientation firstOrientation: firstOrientations) {
            Facet facet = firstOrientation.northOriented();

            for (FacetOrientation secondOrientation: secondOrientations) {
                List<FacetOrientation> plugable = combinationsByFacet.get(firstOrientation);

                if (plugable == null) {
                    plugable = new ArrayList<>();
                }

                Edge secondEdge = secondOrientation.northOriented().edge(FacetSide.NORTH);

                if (facet.edge(FacetSide.NORTH).isPlugable(secondEdge.upturn())) {
                    plugable.add(secondOrientation);
                }

                combinationsByFacet.put(firstOrientation, plugable);
            }
        }
    }
}
