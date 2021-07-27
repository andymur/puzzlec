package com.andymur.pg.cubes.helper;

import com.andymur.pg.cubes.domain.facet.Facet;
import org.junit.Assert;
import org.junit.Test;

import static com.andymur.pg.cubes.helper.FacetOrientator.orientations;

public class FacetOrientatorTest {

    Facet superSymmetricalFacet = new Facet(
            new String[] {
                    "    []    ",
                    "  [][][]  ",
                    "[][][][][]",
                    "  [][][]  ",
                    "    []    ",
            }
    );

    Facet horizontallySymmetricalFacet = new Facet(
            new String[] {
                    "    []    ",
                    "[][][][]  ",
                    "  [][][][]",
                    "[][][][]  ",
                    "    []    ",
            }
    );

    Facet verticallySymmetricalFacet = new Facet(
            new String[] {
                    "[]  []  []",
                    "[][][][][]",
                    "  [][][]  ",
                    "[][][][][]",
                    "[]  []  []",
            }
    );

    Facet asymmetricalFacet = new Facet(
            new String[] {
                    "[][]  [][]",
                    "  [][][][]",
                    "[][][][]  ",
                    "  [][][][]",
                    "  []  []  ",
            }
    );

    Facet centralReflectedFacet = new Facet(
            new String[] {
                    "[]  []    ",
                    "[][][][]  ",
                    "  [][][]  ",
                    "  [][][][]",
                    "    []  []",
            }
    );

    @Test
    public void testFacetOrientationGeneration() {
        Assert.assertEquals(8, orientations(centralReflectedFacet).size());
        Assert.assertEquals(8, orientations(verticallySymmetricalFacet).size());
        Assert.assertEquals(8, orientations(horizontallySymmetricalFacet).size());
        Assert.assertEquals(8, orientations(asymmetricalFacet).size());
        Assert.assertEquals(8, orientations(superSymmetricalFacet).size());
    }


}