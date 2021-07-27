package com.andymur.pg.cubes.domain.facet;

/**
 * Facet's side
 * NORTH is for top, SOUTH is for bottom, WEST is for left and EAS is for right
 *
 * @author andymur
 */
public enum FacetSide {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);

    private final int index;

    private FacetSide(int idx) {
        this.index = idx;
    }

    /**
     * Returns side's number
     * @return side's number
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns side in clock wise direction
     *
     * @return next side in clock wise direction
     */
    public FacetSide clockWiseSide() {
        switch (this) {
            case EAST:
                return SOUTH;
            case WEST:
                return NORTH;
            case NORTH:
                return EAST;
            case SOUTH:
                return WEST;
            default:
                throw new IllegalStateException("No other side");
        }
    }

    /**
     * Returns side in counter clock wise direction
     *
     * @return next side in counter clock wise direction
     */
    public FacetSide counterClockWiseSide() {
        return clockWiseSide().otherSide();
    }

    /**
     * Returns opposite side
     * @return opposite side
     */
    public FacetSide otherSide() {
        switch (this) {
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            default:
                throw new IllegalStateException("No other side");
        }
    }
}