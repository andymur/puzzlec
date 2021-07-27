package com.andymur.pg.cubes.domain.facet;

import com.andymur.pg.cubes.helper.FacetOrientator;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static com.andymur.pg.cubes.domain.facet.FacetSide.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FacetCheckerTest {

    //TODO: add more tests here
    @Test
    public void testPlugableFacets() {
        Facet first = new Facet(
                new String[] {
                        "    []    ",
                        "  [][][]  ",
                        "[][][][][]",
                        "  [][][]  ",
                        "    []    ",
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

        Map<FacetOrientation, List<FacetOrientation>> mappings = getMappings(first, second);

        // All possible orientations are keys (4 sides * 2 (flipped) * 2 facets)
        assertEquals(16, mappings.size());

        // The second facet North (and upturned South) orientation has no counterpart from the first one
        assertTrue(mappings.get(new FacetOrientation(second, NORTH, false)).isEmpty());
        assertTrue(mappings.get(new FacetOrientation(second, SOUTH, true)).isEmpty());

        // All orientations from the second facet which are compatible with any of orientations from the first one
        Set<FacetOrientation> secondFacetCompatibleOrientations = orientations(
                        second.orientation(WEST, false),
                        second.orientation(EAST, false),
                        second.orientation(SOUTH, false),
                        second.orientation(WEST, true),
                        second.orientation(EAST, true),
                        second.orientation(NORTH, true)
        );

        // All orientations from the first facet which are compatible with any of orientations from the second one
        Set<FacetOrientation> firstFacetCompatibleOrientations = FacetOrientator.orientations(first);

        //Check all keys from the first

        assertPlugableOrientations(first.orientation(NORTH, false), secondFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(first.orientation(EAST, false), secondFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(first.orientation(SOUTH, false), secondFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(first.orientation(WEST, false), secondFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(first.orientation(NORTH, true), secondFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(first.orientation(EAST, true), secondFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(first.orientation(SOUTH, true), secondFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(first.orientation(WEST, true), secondFacetCompatibleOrientations, mappings);

        //Check all keys from the second

        assertPlugableOrientations(second.orientation(EAST, false), firstFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(second.orientation(SOUTH, false), firstFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(second.orientation(WEST, false), firstFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(second.orientation(NORTH, true), firstFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(second.orientation(EAST, true), firstFacetCompatibleOrientations, mappings);
        assertPlugableOrientations(second.orientation(WEST, true), firstFacetCompatibleOrientations, mappings);
    }

    @Test
    public void testSecondPlugableFacets() {
        Facet first = new Facet(
                new String[] {
                        "    []    ",
                        "[][][][][]",
                        "  [][][]  ",
                        "[][][][][]",
                        "  []  [][]",
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

        Map<FacetOrientation, List<FacetOrientation>> mappings = getMappings(first, second);

        // All possible orientations are keys (4 sides * 2 (flipped) * 2 facets)
        assertEquals(16, mappings.size());


        Set<FacetOrientation> firstBumpedOrientations = orientations(
                first.orientation(NORTH, false), first.orientation(SOUTH, true)
        );

        Set<FacetOrientation> secondHoledOrientations = orientations(
                second.orientation(WEST, false),
                second.orientation(EAST, false),
                second.orientation(SOUTH, false),
                second.orientation(WEST, true),
                second.orientation(EAST, true),
                second.orientation(NORTH, true)
        );

        Set<FacetOrientation> secondBumpedOrientations = orientations(
                second.orientation(NORTH, false), second.orientation(SOUTH, true)
        );

        //Check all keys from the first

        assertPlugableOrientations(first.orientation(NORTH, false), secondHoledOrientations, mappings);
        assertPlugableOrientations(first.orientation(EAST, false), orientations(second.orientation(SOUTH, true)), mappings);
        assertPlugableOrientations(first.orientation(SOUTH, false), orientations(second.orientation(NORTH, false)), mappings);
        assertPlugableOrientations(first.orientation(WEST, false), secondBumpedOrientations, mappings);
        assertPlugableOrientations(first.orientation(NORTH, true), orientations(second.orientation(SOUTH, true)), mappings);
        assertPlugableOrientations(first.orientation(EAST, true), orientations(second.orientation(NORTH, false)), mappings);
        assertPlugableOrientations(first.orientation(SOUTH, true), secondHoledOrientations, mappings);
        assertPlugableOrientations(first.orientation(WEST, true), secondBumpedOrientations, mappings);

        //Check all keys from the second

        assertPlugableOrientations(second.orientation(NORTH, false),
                orientations(
                        first.orientation(WEST, false), first.orientation(WEST, true),
                        first.orientation(SOUTH, false), first.orientation(EAST, true)
                ),
                mappings);

        assertPlugableOrientations(second.orientation(SOUTH, true),
                orientations(
                        first.orientation(WEST, false), first.orientation(WEST, true),
                        first.orientation(NORTH, true), first.orientation(EAST, false)
                ),
                mappings);

        assertPlugableOrientations(second.orientation(EAST, false), firstBumpedOrientations, mappings);
        assertPlugableOrientations(second.orientation(EAST, false), firstBumpedOrientations, mappings);
        assertPlugableOrientations(second.orientation(WEST, false), firstBumpedOrientations, mappings);
        assertPlugableOrientations(second.orientation(WEST, false), firstBumpedOrientations, mappings);
        assertPlugableOrientations(second.orientation(SOUTH, false), firstBumpedOrientations, mappings);
        assertPlugableOrientations(second.orientation(NORTH, true), firstBumpedOrientations, mappings);
    }

    @Test
    public void testThirdPlugableFacets() {
        Facet first = new Facet(
                new String[] {
                        "    []    ",
                        "[][][][]  ",
                        "  [][][]  ",
                        "  [][][][]",
                        "  []  [][]",
                }
        );

        Facet second = new Facet(
                new String[] {
                        "[]  []    ",
                        "[][][][]  ",
                        "[][][][]  ",
                        "[][][][][]",
                        "  []    []",
                }
        );

        Map<FacetOrientation, List<FacetOrientation>> mappings = getMappings(first, second);

        // All possible orientations are keys (4 sides * 2 (flipped) * 2 facets)
        assertEquals(16, mappings.size());

        //Check all keys from the first

        assertPlugableOrientations(second.orientation(SOUTH, false), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(NORTH, true), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(EAST, false), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(EAST, true), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(WEST, false), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(WEST, true), Collections.emptySet(), mappings);

        assertPlugableOrientations(first.orientation(SOUTH, false),
                orientations(second.orientation(NORTH, false)),
                mappings);

        assertPlugableOrientations(first.orientation(NORTH, true),
                orientations(second.orientation(SOUTH, true)), mappings);

        //Check all keys from the second

        assertPlugableOrientations(second.orientation(SOUTH, false), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(NORTH, true), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(EAST, false), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(EAST, true), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(WEST, false), Collections.emptySet(), mappings);
        assertPlugableOrientations(second.orientation(WEST, true), Collections.emptySet(), mappings);

        assertPlugableOrientations(second.orientation(NORTH, false),
                orientations(first.orientation(SOUTH, false)),
                mappings);

        assertPlugableOrientations(second.orientation(SOUTH, true),
                orientations(first.orientation(NORTH, true)), mappings);

    }

    @Test
    public void testFullyIncompatibleFacetsCheck() {
        Facet first = new Facet(
                new String[] {
                        "    []    ",
                        "[][][][]  ",
                        "  [][][]  ",
                        "  [][][][]",
                        "  []    []",
                }
        );

        Facet second = new Facet(
                new String[] {
                        "[]  []    ",
                        "[][][][]  ",
                        "[][][][]  ",
                        "[][][][][]",
                        "  []    []",
                }
        );

        Map<FacetOrientation, List<FacetOrientation>> mappings = getMappings(first, second);

        mappings.values().stream().forEach(
                value -> Assert.assertTrue(value.isEmpty())
        );
    }

    private void assertPlugableOrientations(FacetOrientation key, Set<FacetOrientation> expectedSet,
                                            Map<FacetOrientation, List<FacetOrientation>> mapping) {
        assertEquals(expectedSet, new HashSet<>(mapping.get(key)));
    }

    private Set<FacetOrientation> orientations(FacetOrientation... orientations) {
        return new HashSet<>(Arrays.asList(orientations));
    }

    private Map<FacetOrientation, List<FacetOrientation>> getMappings(Facet first, Facet second) {
        FacetChecker checker = new FacetChecker();
        return checker.findCombinations(Arrays.asList(first, second));
    }

    // for debug needs only
    private void testPrint(FacetOrientation key, Map<FacetOrientation, List<FacetOrientation>> mapping) {
        for (FacetOrientation orientation: mapping.get(key)) {
            System.out.println(String.format("Side: %s, upturned: %b", orientation.getSide(), orientation.isUpturned()));
        }
    }
}