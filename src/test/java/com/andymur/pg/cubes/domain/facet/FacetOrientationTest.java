package com.andymur.pg.cubes.domain.facet;

import org.junit.Test;

import static com.andymur.pg.cubes.domain.facet.FacetSide.*;
import static org.junit.Assert.assertEquals;

public class FacetOrientationTest {

    Facet originalFacet = new Facet(
            new String[] {
                    "    []  []",
                    "  [][][][]",
                    "  [][][]  ",
                    "[][][][][]",
                    "  []  [][]",
            }
    );

    Facet northSideNorthOriented = new Facet(
            new String[] {
                    "    []  []",
                    "  [][][][]",
                    "  [][][]  ",
                    "[][][][][]",
                    "  []  [][]",
            }
    );

    Facet eastSideNorthOriented = new Facet(
            new String[] {
                    "[][]  [][]",
                    "  [][][][]",
                    "[][][][]  ",
                    "  [][][][]",
                    "      []  ",
            }
    );

    Facet southSideNorthOriented = new Facet(
            new String[] {
                    "[][]  []  ",
                    "[][][][][]",
                    "  [][][]  ",
                    "[][][][]  ",
                    "[]  []    ",
            }
    );

    Facet westSideNorthOriented= new Facet(
            new String[] {
                    "  []      ",
                    "[][][][]  ",
                    "  [][][][]",
                    "[][][][]  ",
                    "[][]  [][]",
            }
    );

    Facet upturnedNorthSideNorthOriented = new Facet(
            new String[] {
                    "  []  [][]",
                    "[][][][][]",
                    "  [][][]  ",
                    "  [][][][]",
                    "    []  []",
            }
    );

    Facet upturnedEastSideNorthOriented = new Facet(
            new String[] {
                    "[][]  [][]",
                    "[][][][]  ",
                    "  [][][][]",
                    "[][][][]  ",
                    "  []      ",
            }
    );

    Facet upturnedSouthSideNorthOriented = new Facet(
            new String[] {
                    "[]  []    ",
                    "[][][][]  ",
                    "  [][][]  ",
                    "[][][][][]",
                    "[][]  []  ",
            }
    );

    Facet upturnedWestSideNorthOriented = new Facet(
            new String[] {
                    "      []  ",
                    "  [][][][]",
                    "[][][][]  ",
                    "  [][][][]",
                    "[][]  [][]",
            }
    );

    @Test
    public void testChangeOrientationToNorth() {
        // straight facet sides check
        assertEquals(northSideNorthOriented, createFromOriginal(NORTH, false).facetOrientedTo(NORTH));
        assertEquals(eastSideNorthOriented, createFromOriginal(EAST, false).facetOrientedTo(NORTH));
        assertEquals(southSideNorthOriented, createFromOriginal(SOUTH, false).facetOrientedTo(NORTH));
        assertEquals(westSideNorthOriented, createFromOriginal(WEST, false).facetOrientedTo(NORTH));

        //flipped facet sides check
        assertEquals(upturnedNorthSideNorthOriented, createFromOriginal(NORTH, true).facetOrientedTo(NORTH));
        assertEquals(upturnedEastSideNorthOriented, createFromOriginal(EAST, true).facetOrientedTo(NORTH));
        assertEquals(upturnedSouthSideNorthOriented, createFromOriginal(SOUTH, true).facetOrientedTo(NORTH));
        assertEquals(upturnedWestSideNorthOriented, createFromOriginal(WEST, true).facetOrientedTo(NORTH));
    }

    @Test
    public void testReflectedOrientationChange() {

    }

    @Test
    public void testPointSymmetricalOrientationChange() {

    }

    private FacetOrientation createFromOriginal(FacetSide side, boolean isUpturned) {
        return new FacetOrientation(originalFacet, side, isUpturned);
    }
}