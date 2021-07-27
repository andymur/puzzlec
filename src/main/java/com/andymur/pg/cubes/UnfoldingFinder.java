package com.andymur.pg.cubes;

import com.andymur.pg.cubes.domain.Unfolding;
import com.andymur.pg.cubes.domain.Well;
import com.andymur.pg.cubes.domain.facet.Facet;
import com.andymur.pg.cubes.domain.facet.FacetChecker;
import com.andymur.pg.cubes.domain.facet.FacetOrientation;
import com.andymur.pg.cubes.domain.facet.FacetSide;
import com.andymur.pg.cubes.helper.FacetOrientator;
import com.andymur.pg.cubes.helper.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static com.andymur.pg.cubes.domain.facet.FacetSide.NORTH;
import static com.andymur.pg.cubes.domain.facet.FacetSide.SOUTH;

/**
 * Finds unfoldings (solutions of the task)
 *
 * @see com.andymur.pg.cubes.domain.Unfolding
 * @author andymur
 */
final class UnfoldingFinder {

    private Map<FacetOrientation, List<FacetOrientation>> mappings;
    private boolean unique = false;

    private UnfoldingFinder() {
    }

    public static UnfoldingFinder of() {
        return new UnfoldingFinder();
    }

    /**
     * Use for finding unique solutions
     * @return UnfoldingFinder which finds unique solutions
     */
    public UnfoldingFinder unique() {
        this.unique = true;
        return this;
    }

    /**
     * Finds one solution only from given facets
     * @param facets which form unfolding
     * @return first solution
     */
    public Optional<Unfolding> findFirst(List<Facet> facets) {
        mappings = new FacetChecker().findCombinations(facets);
        Collection<Well> wells = WellFinder.of(mappings).findWells(facets);

        for (Well well: wells) {
            List<Unfolding> straight = find(well.getWellFacets(), well.getFirstCover(), well.getSecondCover());

            if (!straight.isEmpty()) {
                return Optional.of(straight.get(0));
            }

            List<Unfolding> upsideDown  = find(well.getWellFacets(), well.getSecondCover(), well.getFirstCover());

            if (!upsideDown.isEmpty()) {
                return Optional.of(upsideDown.get(0));
            }
        }

        return Optional.empty();
    }

    /**
     * Finds all possible solutions from given facets
     * @param facets which form unfolding
     * @return all possible solutions
     */
    public Collection<Unfolding> find(List<Facet> facets) {
        Collection<Unfolding> result = unique ? new HashSet<>() : new ArrayList<>();

        mappings = new FacetChecker().findCombinations(facets);

        Collection<Well> wells = unique
                ? WellFinder.of(mappings).findUniqueWells(facets)
                : WellFinder.of(mappings).findWells(facets);

        for (Well well: wells) {
            result.addAll(find(well.getWellFacets(), well.getFirstCover(), well.getSecondCover()));
            result.addAll(find(well.getWellFacets(), well.getSecondCover(), well.getFirstCover()));
        }

        return result;
    }

    private List<Unfolding> find(List<Facet> wellFacets, Facet topCover, Facet bottomCover) {
        List<Unfolding> result = new ArrayList<>();

        Set<FacetOrientation> topOrientations = FacetOrientator.orientations(topCover);
        Set<FacetOrientation> bottomOrientations = FacetOrientator.orientations(bottomCover);
        Set<Pair<FacetOrientation, Integer>> topPositions = new HashSet<>();
        Set<Pair<FacetOrientation, Integer>> bottomPositions = new HashSet<>();

        for (FacetOrientation topOrientation: topOrientations) {
            topPositions.addAll(positions(topOrientation, wellFacets, NORTH));
        }

        if (topPositions.isEmpty()) {
            return Collections.emptyList();
        }

        for (FacetOrientation bottomOrientation: bottomOrientations) {
            bottomPositions.addAll(positions(bottomOrientation, wellFacets, SOUTH));
        }

        if (bottomPositions.isEmpty()) {
            return Collections.emptyList();
        }

        for (Pair<FacetOrientation, Integer> topPosition: topPositions) {
            for (Pair<FacetOrientation, Integer> bottomPosition: bottomPositions) {
                Unfolding unfolding = new Unfolding(wellFacets,
                        topPosition.first.facetOrientedTo(SOUTH), topPosition.second,
                        bottomPosition.first.facetOrientedTo(NORTH),
                        bottomPosition.second);
                if (unfolding.canBeFolded()) {
                    result.add(unfolding);
                }
            }
        }

        return result;
    }

    private Set<Pair<FacetOrientation, Integer>> positions(FacetOrientation cover, List<Facet> wellFacets,
                                                           FacetSide wellComparisonSide) {
        Set<Pair<FacetOrientation, Integer>> result = new HashSet<>();

        IntStream.range(0, Well.WELL_SIZE).forEach(
                index ->
                {
                    if (cover.northOriented().edge(NORTH).isPlugable(wellFacets.get(index).edge(wellComparisonSide))) {
                        result.add(Pair.of(cover, index));
                    }
                }
        );

        return result;
    }


}
