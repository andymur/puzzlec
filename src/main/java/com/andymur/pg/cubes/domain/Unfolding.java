package com.andymur.pg.cubes.domain;

import com.andymur.pg.cubes.domain.facet.Facet;
import com.andymur.pg.cubes.domain.facet.FacetSide;
import com.andymur.pg.cubes.helper.FacetHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.andymur.pg.cubes.domain.Well.NORMAL_COVER_NUMBER;
import static com.andymur.pg.cubes.domain.Well.WELL_SIZE;
import static com.andymur.pg.cubes.domain.facet.FacetSide.*;

/**
 * Represents unfolded solution, which contains six facets
 *
 * @see com.andymur.pg.cubes.domain.facet.Facet
 * @see com.andymur.pg.cubes.domain.Well
 * @see com.andymur.pg.cubes.domain.facet.FacetOrientation
 * @author andymur
 */

public class Unfolding {

    private static final int FACET_NUMBER = 6;
    private static final int UNFOLDING_LENGTH = 4;
    private static final int UNFOLDING_HEIGHT = 3;
    private static final int UNFOLDING_MID_INDEX = 1;

    private Facet[][] state = new Facet[UNFOLDING_HEIGHT][UNFOLDING_LENGTH];

    /**
     * Creates unfolding from six facets.
     *
     * First four forms Well, and last one two covers (top and bottom)
     *
     * @param facets from which unfolding is created
     * @return Unfolding
     */
    public static Unfolding of(Facet[] facets) {
        List<Facet> well = Arrays.asList(Arrays.copyOfRange(facets, 0, UNFOLDING_LENGTH));
        Facet top = facets[FACET_NUMBER - 2];
        Facet bottom = facets[FACET_NUMBER - 1];
        return new Unfolding(well, top, 0, bottom, 0);
    }

    /**
     * Creates unfolding from well and covers.
     *
     * First four forms Well, and last one two covers (top and bottom)
     *
     * @param well four facets which forms well
     * @param top facet
     * @param topPosition of top facet (0..3)
     * @param bottom facet
     * @param bottomPosition of bottom facet (0..3)
     */
    public Unfolding(List<Facet> well, Facet top, int topPosition, Facet bottom, int bottomPosition) {

        if (bottom == null || top == null) {
            throw new IllegalArgumentException("Unfolding cannot exist without bottom or top cover, null given");
        }

        if (well.size() != WELL_SIZE) {
            throw  new IllegalArgumentException(
                    String.format("Unfolding consists of exactly %d facets %d given", FACET_NUMBER,
                            well.size() + NORMAL_COVER_NUMBER)
            );
        }

        IntStream.range(0, UNFOLDING_LENGTH).forEach(i -> state[1][i] = well.get(i));
        state[0][topPosition] = top;
        state[UNFOLDING_HEIGHT - 1][bottomPosition] = bottom;
    }

    /**
     * Checks whether unfolding can be folded into cube or not
     * @return true when unfolding can be folded into cube
     */
    public boolean canBeFolded() {
        return wellFacetsCanBeFolded()
                && edgeFacetsCanBeFolded()
                && cornersFilled();

    }

    private boolean wellFacetsCanBeFolded() {
        Facet[] wellFacets = wellFacets();

        for (int i = 0; i < UNFOLDING_LENGTH; i++) {
            Facet leftFacet = wellFacets[i];
            Facet rightFacet = i < UNFOLDING_LENGTH - 1 ? wellFacets[i + 1] : wellFacets[0];

            if (!leftFacet.edge(EAST).isPlugable(rightFacet.edge(WEST))) {
                return false;
            }
        }

        return true;
    }

    private Facet[] wellFacets() {
        return state[UNFOLDING_MID_INDEX];
    }

    private Well well() {
        return new Well(Arrays.asList(wellFacets()), Arrays.asList(topFacet(), bottomFacet()));
    }

    private boolean edgeFacetsCanBeFolded() {
        return topEdgeFacetCanBeFolded() && bottomEdgeFacetCanBeFolded();
    }

    private boolean topEdgeFacetCanBeFolded() {
        int facetIndex = topFacetIndex();
        Facet topFacet = topFacet();

        Facet topFacetLeftCounterPart = findEdgeFacetCounterPart(facetIndex, WEST);
        Facet topFacetRightCounterPart = findEdgeFacetCounterPart(facetIndex, EAST);
        Facet topFacetTopCounterPart = findEdgeFacetCounterPart(facetIndex, NORTH);
        Facet topFacetBottomCounterPart = findEdgeFacetCounterPart(facetIndex, SOUTH);

        return topFacet.edge(WEST).isPlugable(topFacetLeftCounterPart.edge(NORTH))
                && topFacet.edge(EAST).upturn().isPlugable(topFacetRightCounterPart.edge(NORTH))
                && topFacet.edge(NORTH).upturn().isPlugable(topFacetTopCounterPart.edge(NORTH))
                && topFacet.edge(SOUTH).isPlugable(topFacetBottomCounterPart.edge(NORTH));
    }

    private Facet topFacet() {
        int facetIndex = topFacetIndex();
        return state[0][facetIndex];
    }

    private boolean bottomEdgeFacetCanBeFolded() {
        int facetIndex = bottomFacetIndex();
        Facet bottomFacet = bottomFacet();

        Facet bottomFacetLeftCounterPart = findEdgeFacetCounterPart(facetIndex, WEST);
        Facet bottomFacetRightCounterPart = findEdgeFacetCounterPart(facetIndex, EAST);
        Facet bottomFacetBottomCounterPart = findEdgeFacetCounterPart(facetIndex, SOUTH);
        Facet bottomFacetTopCounterPart = findEdgeFacetCounterPart(facetIndex, NORTH);

        return bottomFacet.edge(WEST).upturn().isPlugable(bottomFacetLeftCounterPart.edge(SOUTH))
                && bottomFacet.edge(EAST).isPlugable(bottomFacetRightCounterPart.edge(SOUTH))
                && bottomFacet.edge(SOUTH).upturn().isPlugable(bottomFacetBottomCounterPart.edge(SOUTH))
                && bottomFacet.edge(NORTH).isPlugable(bottomFacetTopCounterPart.edge(SOUTH));
    }

    private Facet bottomFacet() {
        int facetIndex = bottomFacetIndex();
        return state[UNFOLDING_HEIGHT - 1][facetIndex];
    }

    private Facet findEdgeFacetCounterPart(int facetIndex, FacetSide side) {
        switch (side) {
            case WEST:
                return facetIndex > 0
                        ? wellFacets()[facetIndex - 1]
                        : wellFacets()[UNFOLDING_LENGTH - 1];
            case EAST:
                return facetIndex < UNFOLDING_LENGTH - 1
                        ? wellFacets()[facetIndex + 1]
                        : wellFacets()[0];
            case NORTH:
                return facetIndex > 1
                        ? wellFacets()[facetIndex - 2]
                        : wellFacets()[facetIndex + 2];
            case SOUTH:
                return state[UNFOLDING_MID_INDEX][facetIndex];
            default:
                throw new IllegalStateException("One of the four side value should be given");
        }
    }

    int edgeFacetIndex(boolean isTop) {
        int rowIndex = isTop ? 0 : UNFOLDING_HEIGHT - 1;
        for (int i = 0; i < UNFOLDING_LENGTH; i++) {
            if (state[rowIndex][i] != null) {
                return i;
            }
        }

        throw new IllegalStateException("Unfolding must contain one edge facet for side");
    }

    int topFacetIndex() {
        return edgeFacetIndex(true);
    }

    int bottomFacetIndex() {
        return edgeFacetIndex(false);
    }

    private boolean cornersFilled() {
        return bottomFacetCornersFilled() && topFacetCornersFilled();
    }

    private boolean topFacetCornersFilled() {
        int facetIndex = topFacetIndex();
        Facet facet = topFacet();

        Facet leftCounterPart = findEdgeFacetCounterPart(facetIndex, WEST);
        Facet rightCounterPart = findEdgeFacetCounterPart(facetIndex, EAST);
        Facet bottomCounterPart = findEdgeFacetCounterPart(facetIndex, SOUTH);
        Facet topCounterPart = findEdgeFacetCounterPart(facetIndex, NORTH);

        return Edge.cornerFilled(facet.edge(NORTH).upturn(), topCounterPart.edge(NORTH), rightCounterPart.edge(NORTH).upturn())
                && Edge.cornerFilled(facet.edge(NORTH), topCounterPart.edge(NORTH).upturn(), leftCounterPart.edge(NORTH))
                && Edge.cornerFilled(facet.edge(SOUTH).upturn(), bottomCounterPart.edge(NORTH).upturn(), rightCounterPart.edge(NORTH))
                && Edge.cornerFilled(facet.edge(SOUTH), bottomCounterPart.edge(NORTH), leftCounterPart.edge(NORTH).upturn());
    }

    private boolean bottomFacetCornersFilled() {
        int facetIndex = bottomFacetIndex();
        Facet facet = bottomFacet();

        Facet leftCounterPart = findEdgeFacetCounterPart(facetIndex, WEST);
        Facet rightCounterPart = findEdgeFacetCounterPart(facetIndex, EAST);
        Facet bottomCounterPart = findEdgeFacetCounterPart(facetIndex, SOUTH);
        Facet topCounterPart = findEdgeFacetCounterPart(facetIndex, NORTH);

        return Edge.cornerFilled(facet.edge(SOUTH).upturn(), topCounterPart.edge(SOUTH), rightCounterPart.edge(SOUTH).upturn())
                && Edge.cornerFilled(facet.edge(SOUTH), topCounterPart.edge(SOUTH).upturn(), leftCounterPart.edge(SOUTH))
                && Edge.cornerFilled(facet.edge(NORTH).upturn(), bottomCounterPart.edge(SOUTH).upturn(), rightCounterPart.edge(SOUTH))
                && Edge.cornerFilled(facet.edge(NORTH), bottomCounterPart.edge(SOUTH), leftCounterPart.edge(SOUTH).upturn());
    }

    /**
     * Prints unfolding
     */
    public void printOut() {
        /*for (int i = 0; i < UNFOLDING_HEIGHT; i++) {
            System.out.println(UnfoldingHelper.facetRowToString(state[i]));
        }*/
        System.out.println(toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unfolding unfolding = (Unfolding) o;


        return this.well().equals(unfolding.well())
                && topFacet().equals(unfolding.topFacet())
                && bottomFacet().equals(unfolding.bottomFacet());
    }

    @Override
    public int hashCode() {
        Facet topFacet = topFacet();
        Facet bottomFacet = bottomFacet();
        int hashCode = well().hashCode();

        hashCode = hashCode * 31 + topFacet.hashCode();
        return hashCode * 31 + bottomFacet.hashCode();
    }

    @Override
    public String toString() {
        List<String> stringRows = new ArrayList<>();

        for (int i = 0; i < UNFOLDING_HEIGHT; i++) {
            stringRows.add(FacetHelper.facetRowToString(state[i]));
        }

        return stringRows.stream().collect(Collectors.joining("\n"));
    }

    void printBrokenInOrder() {
        Facet[][] facets = new Facet[2][3];
        IntStream.range(0, 3).forEach(i -> facets[0][i] = state[UNFOLDING_MID_INDEX][i]);

        facets[1][0] = state[UNFOLDING_MID_INDEX][UNFOLDING_LENGTH - 1];

        facets[1][1] = state[0][topFacetIndex()];
        facets[1][2] = state[UNFOLDING_HEIGHT - 1][bottomFacetIndex()];

        for (Facet[] facet : facets) {
            System.out.println(FacetHelper.facetRowToString(facet));
        }
    }
}
