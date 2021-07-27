package com.andymur.pg.cubes.domain.facet;

import com.andymur.pg.cubes.helper.FacetHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.andymur.pg.cubes.domain.facet.FacetSide.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FacetTest {

    @Test
    public void testNormalConstruction() {
        String edge = "    []    ";

        Facet facet = new Facet(
                new String[] {
                        "    []    ",
                        "  [][][]  ",
                        "[][][][][]",
                        "  [][][]  ",
                        "    []    ",
                }
        );

        assertEquals(edge, facet.edge(NORTH).toString());
        assertEquals(edge, facet.edge(EAST).toString());
        assertEquals(edge, facet.edge(SOUTH).toString());
        assertEquals(edge, facet.edge(WEST).toString());
    }

    @Test
    public void testRotateClockWise() {

        Facet originalFacet = new Facet(
                new String[] {
                        "  []  []  ",
                        "[][][][]  ",
                        "  [][][][]",
                        "  [][][]  ",
                        "      []  ",
                }
        );

        Facet rotatedFacet = new Facet(
                new String[] {
                        "      []  ",
                        "  [][][][]",
                        "  [][][]  ",
                        "[][][][][]",
                        "    []    ",
                }
        );

        Facet twiceRotatedFacet = new Facet(
                new String[] {
                        "  []      ",
                        "  [][][]  ",
                        "[][][][]  ",
                        "  [][][][]",
                        "  []  []  ",
                }
        );

        Facet thriceRotatedFacet = new Facet(
                new String[] {
                        "    []    ",
                        "[][][][][]",
                        "  [][][]  ",
                        "[][][][]  ",
                        "  []      ",
                }
        );

        System.out.println(FacetHelper.facetRowToString(new Facet[]{rotatedFacet}));
        assertEquals(rotatedFacet, originalFacet.rotate());

        assertEquals(twiceRotatedFacet, originalFacet.rotate(2));
        System.out.println(FacetHelper.facetRowToString(new Facet[]{twiceRotatedFacet}));
        assertEquals(thriceRotatedFacet, originalFacet.rotate(3));
        System.out.println(FacetHelper.facetRowToString(new Facet[]{thriceRotatedFacet}));
        assertEquals(originalFacet, originalFacet.rotate(4));
        System.out.println(FacetHelper.facetRowToString(new Facet[]{originalFacet.rotate(4)}));
        assertEquals(rotatedFacet, originalFacet.rotate(5));
    }

    @Test
    public void testUpTurn() {
        Facet originalFacet = new Facet(
                new String[] {
                        "  []  []  ",
                        "[][][][]  ",
                        "  [][][][]",
                        "  [][][]  ",
                        "      []  ",
                }
        );

        Facet upturnedFacet = new Facet(
                new String[] {
                        "      []  ",
                        "  [][][]  ",
                        "  [][][][]",
                        "[][][][]  ",
                        "  []  []  ",
                }
        );

        assertEquals(upturnedFacet, originalFacet.upturn());
    }

    @Test
    public void testFacetEquality() {
        Facet firstFacet = new Facet(
                new String[] {
                        "    []  []",
                        "[][][][][]",
                        "  [][][]  ",
                        "[][][][][]",
                        "[]  []    ",
                }
        );

        Facet secondFacet = new Facet(
                new String[] {
                        "[]  []    ",
                        "[][][][]  ",
                        "  [][][]  ",
                        "  [][][][]",
                        "    []  []",
                }
        );

        List<Facet> facets = new ArrayList<>(Arrays.asList(firstFacet, secondFacet, firstFacet));

        assertTrue(facets.remove(firstFacet));
        assertEquals(2, facets.size());
    }

}