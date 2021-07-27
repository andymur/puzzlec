package com.andymur.pg.cubes;

import com.andymur.pg.cubes.domain.Well;
import com.andymur.pg.cubes.domain.facet.Facet;
import com.andymur.pg.cubes.domain.facet.FacetChecker;
import com.andymur.pg.cubes.helper.FacetHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class WellFinderTest {

    Facet fifth = new Facet(
            new String[] {
                    "    []    ",
                    "  [][][]  ",
                    "[][][][][]",
                    "  [][][]  ",
                    "    []    ",
            }
    );

    Facet sixth = new Facet(
            new String[] {
                    "    []    ",
                    "[][][][]  ",
                    "  [][][][]",
                    "[][][][]  ",
                    "    []    ",
            }
    );

    Facet third = new Facet(
            new String[] {
                    "[]  []  []",
                    "[][][][][]",
                    "  [][][]  ",
                    "[][][][][]",
                    "[]  []  []",
            }
    );

    Facet second = new Facet(
            new String[] {
                    "[]  []    ",
                    "[][][][][]",
                    "  [][][]  ",
                    "[][][][][]",
                    "  []  []  ",
            }
    );

    Facet first = new Facet(
            new String[] {
                    "    []  []",
                    "[][][][][]",
                    "  [][][]  ",
                    "[][][][][]",
                    "  []  [][]",
            }
    );


    Facet fourth = new Facet(
            new String[] {
                    "    []    ",
                    "[][][][][]",
                    "  [][][]  ",
                    "[][][][][]",
                    "  []  [][]",
            }
    );

    @Test
    public void testFindNonUniqueWellOfTwo() {
        List<Facet> facets = Arrays.asList(fifth, third);

        Collection<Well> wells = new WellFinder(new FacetChecker().findCombinations(facets)).findWells(
                facets, false, 2
        );

        Assert.assertEquals(64, wells.size());
    }

    @Test
    public void testFindUniqueWellOfTwo() {
        List<Facet> facets = Arrays.asList(fifth, third);

        Collection<Well> wells = new WellFinder(new FacetChecker().findCombinations(facets)).findWells(
                facets, true, 2
        );

        for (Well well: wells) {
            testPrintWell(well);
        }

        Assert.assertEquals(1, wells.size());
    }

    @Test
    public void testFindUniqueWellOfSix() {
        List<Facet> facets = Arrays.asList(first, second, third, fourth, fifth, third);

        Collection<Well> wells = new WellFinder(new FacetChecker().findCombinations(facets)).findUniqueWells(
                facets
        );

        Assert.assertEquals(89, wells.size());
    }

    //For debug needs only
    private void testPrintWell(Well well) {
        System.out.println(
                FacetHelper.facetRowToString(well.getWellFacets().toArray(new Facet[]{}))
        );
    }
}