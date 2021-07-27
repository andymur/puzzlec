package com.andymur.pg.cubes;

import com.andymur.pg.cubes.domain.Well;
import com.andymur.pg.cubes.domain.facet.Facet;
import com.andymur.pg.cubes.domain.facet.FacetOrientation;
import com.andymur.pg.cubes.helper.FacetOrientator;

import java.util.*;
import java.util.stream.Collectors;

import static com.andymur.pg.cubes.domain.Well.WELL_SIZE;
import static com.andymur.pg.cubes.domain.facet.FacetSide.EAST;
import static com.andymur.pg.cubes.domain.facet.FacetSide.WEST;

/**
 * Constructs wells from given facets
 *
 * @see com.andymur.pg.cubes.domain.Well
 * @author andymur
 */
final class WellFinder {

    private boolean unique = false;
    private Map<FacetOrientation, List<FacetOrientation>> mappings;

    public WellFinder(Map<FacetOrientation, List<FacetOrientation>> mappings) {
        this.mappings = mappings;
    }

    /**
     * Find all possible wells from given facets
     * @see com.andymur.pg.cubes.domain.Well
     * @param facets which form well
     * @return all possible wells
     */
    public Collection<Well> findWells(List<Facet> facets) {
        return findWells(facets, false, WELL_SIZE);
    }

    /**
     * Find all possible unique wells from given facets
     * @see com.andymur.pg.cubes.domain.Well
     * @param facets which form well
     * @return all possible unique wells
     */
    public Collection<Well> findUniqueWells(List<Facet> facets) {
        return findWells(facets, true, WELL_SIZE);
    }

    Collection<Well> findWells(List<Facet> facets, boolean unique, int wellSize) {
        Collection<Well> result = unique ? new HashSet<>() : new ArrayList<>();

        final List<FacetOrientation> orientations = new ArrayList<>();

        facets.stream().forEach(facet -> orientations.addAll(FacetOrientator.orientations(facet)));

        for (FacetOrientation start: orientations) {
            List<Facet> remained = new ArrayList<>(facets);
            remained.remove(start.getOriginal());
            result.addAll(findWell(new LinkedList<>(Arrays.asList(start)), remained, wellSize));
        }

        return result;
    }

    static WellFinder of(Map<FacetOrientation, List<FacetOrientation>> mappings) {
        return new WellFinder(mappings);
    }

    private List<Well> findWell(Deque<FacetOrientation> wellPart,
                                    List<Facet> remainedFacets,
                                    int wellSize) {
        List<Well> wells = new ArrayList<>();

        if (wellPart.size() == wellSize) {
            if (lastAndFirstFacetsAreCompatible(wellPart)) {
                return Collections.singletonList(orientedWell(wellPart, remainedFacets));
            }

            return Collections.emptyList();
        }

        FacetOrientation orientationToCheck = orientationToCheck(wellPart);
        List<FacetOrientation> plugable = plugableRemained(orientationToCheck, remainedFacets);

        if (plugable.isEmpty()) {
            return Collections.emptyList();
        }

        for (FacetOrientation plugablePiece: plugable) {

            Deque<FacetOrientation> newWellPart = new LinkedList<>(wellPart);
            newWellPart.addLast(plugablePiece);

            List<Facet> newRemainedFacets = new ArrayList<>(remainedFacets);
            newRemainedFacets.remove(plugablePiece.getOriginal());

            wells.addAll(findWell(newWellPart, newRemainedFacets, wellSize));
        }

        return wells;
    }

    private FacetOrientation orientationToCheck(Deque<FacetOrientation> wellPart) {
        // When we have just the first facet in the well, we we'll check it as is
        if (wellPart.size() == 1) {
            return wellPart.getLast();
        } else {
        // When we have not the first facet in the well, it is compatible with previous one, so we need to check the opposite side
            FacetOrientation last = wellPart.getLast();
            return new FacetOrientation(last.getOriginal(), last.getSide().otherSide(), last.isUpturned());
        }
    }

    private boolean lastAndFirstFacetsAreCompatible(Deque<FacetOrientation> wellPart) {
        FacetOrientation last = wellPart.getLast();
        FacetOrientation first = wellPart.getFirst();

        FacetOrientation lastOrientation
                = new FacetOrientation(last.getOriginal(), last.getSide().otherSide(), last.isUpturned());

        FacetOrientation firstOrientation = new FacetOrientation(first.getOriginal(),
                first.getSide().otherSide(), first.isUpturned());

        return mappings().get(lastOrientation).contains(firstOrientation);
    }

    private Well orientedWell(Deque<FacetOrientation> wellPart,
                                     List<Facet> remainedFacets) {
        List<Facet> well = new ArrayList<>();
        int originalSize = wellPart.size();

        while (!wellPart.isEmpty()) {
            if (wellPart.size() == originalSize) {
                well.add(wellPart.getFirst().facetOrientedTo(EAST));
            } else {
                well.add(wellPart.getFirst().facetOrientedTo(WEST));
            }

            wellPart.removeFirst();
        }
        return new Well(well, remainedFacets);
    }

    private List<FacetOrientation> plugableRemained(FacetOrientation orientation,
                                                    List<Facet> remainedFacets) {
        List<FacetOrientation> orientations = mappings().get(orientation);
        return orientations.stream()
                .filter(o -> remainedFacets.contains(o.getOriginal())).collect(Collectors.toList());
    }

    Map<FacetOrientation, List<FacetOrientation>> mappings() {
        return mappings;
    }

}
