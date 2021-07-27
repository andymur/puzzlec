package com.andymur.pg.cubes.domain;

import com.andymur.pg.cubes.domain.facet.Facet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.andymur.pg.cubes.helper.FacetHelper.rotated;
import static com.andymur.pg.cubes.helper.FacetHelper.upturned;
/**
* Represents four facets compatible with each other, connected to a chain.
* Remained two facets forms a cover.
*
* @see com.andymur.pg.cubes.domain.facet.Facet
* @author andymur
**/

public final class Well {

    public static final int WELL_SIZE = 4;
    public static final int NORMAL_COVER_NUMBER = 2;

    final List<Facet> well;
    final List<Facet> covers;


    public Well(List<Facet> well, List<Facet> covers) {
        this.well = well;
        this.covers = covers;
    }

    public List<Facet> getWellFacets() {
        return well;
    }

    public Facet getFirstCover() {
        checkCoversState();
        return covers.get(0);
    }

    public Facet getSecondCover() {
        checkCoversState();
        return covers.get(1);
    }

    private void checkCoversState() {
        if (covers.size() != NORMAL_COVER_NUMBER) {
            throw new UnsupportedOperationException(
                    String.format("Can't get cover when cover number does not equal to %d", NORMAL_COVER_NUMBER)
            );
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Well well = (Well) o;

        Set<Facet> facetsCompareWith = new HashSet<>(well.getWellFacets());

        Set<Facet> facets = new HashSet<>(this.well);
        Set<Facet> rotatedFacets = rotated(facets);
        Set<Facet> upturnedFacets = upturned(facets);
        Set<Facet> upturnedAndRotatedFacets = rotated(upturnedFacets);

        return facetsCompareWith.equals(facets)
            || facetsCompareWith.equals(rotatedFacets)
            || facetsCompareWith.equals(upturnedFacets)
            || facetsCompareWith.equals(upturnedAndRotatedFacets);
    }

    @Override
    public int hashCode() {
        return well != null ? new HashSet<>(well).hashCode() : 0;
    }
}
