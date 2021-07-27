package com.andymur.pg.cubes.domain;

import org.junit.Assert;
import org.junit.Test;

public class UnfoldingTest {

    Unfolding firstIncompleteUnfolding = UnfoldingTestData.FIRST_INCOMPLETE;
    Unfolding firstCompleteUnfolding = UnfoldingTestData.FIRST_COMPLETE;
    Unfolding foldingWithHoleInCorner = UnfoldingTestData.FIRST_WITH_HOLE;
    Unfolding foldingWithConflictInCorner = UnfoldingTestData.WITH_CORNER_CONFLICT;

    @Test
    public void testPrintOut() {
        System.out.println(String.format("%-40s", " FIRST_INCOMPLETE_UNFOLDING ").replace(' ', '='));
        firstIncompleteUnfolding.printOut();
        System.out.println(String.format("%-40s", " FIRST_INCOMPLETE_FACETS").replace(' ', '='));
        firstIncompleteUnfolding.printBrokenInOrder();

        System.out.println(String.format("%-40s", " FIRST_COMPLETE_UNFOLDING ").replace(' ', '='));
        firstCompleteUnfolding.printOut();
        System.out.println(String.format("%-40s", " FIRST_COMPLETE_FACETS").replace(' ', '='));
        firstCompleteUnfolding.printBrokenInOrder();

        System.out.println(String.format("%-40s", " CORNER_CONFLICT_UNFOLDING ").replace(' ', '='));
        foldingWithConflictInCorner.printOut();
        System.out.println(String.format("%-40s", " CORNER_CONFLICT_FACETS").replace(' ', '='));
        foldingWithConflictInCorner.printBrokenInOrder();

        System.out.println(String.format("%-40s", " CORNER_HOLE_UNFOLDING ").replace(' ', '='));
        foldingWithHoleInCorner.printOut();
        System.out.println(String.format("%-40s", " CORNER_HOLE_FACETS").replace(' ', '='));
        foldingWithHoleInCorner.printBrokenInOrder();

    }

    @Test
    public void testPositiveFolding() {
        Assert.assertTrue(firstCompleteUnfolding.canBeFolded());
    }

    @Test
    public void testNegativeFolding() {
        Assert.assertFalse(firstIncompleteUnfolding.canBeFolded());
    }

    @Test
    public void testFoldingWithCornerConflict() {
        Assert.assertFalse(foldingWithConflictInCorner.canBeFolded());
    }

    @Test
    public void testFoldingWithHoleInTheCorner() {
        Assert.assertFalse(foldingWithHoleInCorner.canBeFolded());
    }
}