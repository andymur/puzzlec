package com.andymur.pg.cubes.domain.facet;

/**
 * Represents facet and one of its side and also the fact was other facet side it or not.
 *
 * @see com.andymur.pg.cubes.domain.facet.Facet
 * @see com.andymur.pg.cubes.domain.facet.FacetSide
 * @author andymur
 */
public class FacetOrientation {
    private final Facet original;
    private final FacetSide side;
    private final boolean isUpturned;

    /**
     * Creates facet orientation from triple of facts
     *
     * @param original facet
     * @param side facet's side
     * @param isUpturned facet is upturned or not
     */
    public FacetOrientation(Facet original, FacetSide side,
                            boolean isUpturned) {
        this.original = original;
        this.side = side;
        this.isUpturned = isUpturned;
    }

    /**
     * Creates facet which has its side oriented to given
     *
     * @param newOrientationSide new side of orientation
     * @return facet (upturned or not) with its side of original oriented to given side
     */
    public Facet facetOrientedTo(FacetSide newOrientationSide) {
        Facet normalizedFacet;

        if (isUpturned) {
            normalizedFacet = original.upturn();
        } else {
            normalizedFacet = original.copy();
        }

        if (newOrientationSide == side.otherSide()) {
            return normalizedFacet.rotate(2);
        } else if (newOrientationSide == side.clockWiseSide()) {
            return normalizedFacet.rotate(1);
        } else if (newOrientationSide == side.counterClockWiseSide()) {
            return normalizedFacet.rotate(3);
        }

        return normalizedFacet;
    }

    /**
     * Creates facet which has its side oriented to North
     *
     * @return facet (upturned or not) with its side of original oriented to North
     */
    public Facet northOriented() {
        return facetOrientedTo(FacetSide.NORTH);
    }

    @Override
    public String toString() {
        return northOriented().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacetOrientation that = (FacetOrientation) o;

        if (isUpturned != that.isUpturned) return false;
        if (original != null ? !original.equals(that.original) : that.original != null) return false;
        if (side != that.side) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = original != null ? original.hashCode() : 0;
        result = 31 * result + (side != null ? side.hashCode() : 0);
        result = 31 * result + (isUpturned ? 1 : 0);
        return result;
    }

    public Facet getOriginal() {
        return original;
    }

    public FacetSide getSide() {
        return side;
    }

    public boolean isUpturned() {
        return isUpturned;
    }
}
