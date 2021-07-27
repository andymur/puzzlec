package com.andymur.pg.cubes.domain;


import org.junit.Assert;
import org.junit.Test;

public class EdgeTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreationFromEmptyString() {
        Edge.of("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAbnormalCreationFromNull() {
        new Edge(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAbnormalCreationBadArray() {
        new Edge(new int[] {0, 0, 0});
    }

    @Test
    public void testNormalCreationFromString() {
        String stringRepresentation = "[]  []  []";
        Edge edge = Edge.of(stringRepresentation);
        Assert.assertEquals(stringRepresentation, edge.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testCreationFromNullString() {
        String stringRepresentation = null;
        Edge edge = Edge.of(stringRepresentation);
    }

    @Test
    public void testPlugable() {

        //Plugable/Unplugable center combinations (both sides are plugable and don't contain blocks)

        Assert.assertTrue(Edge.of("          ").isPlugable(Edge.of("  [][][]  ")));

        Assert.assertTrue(Edge.of("      []  ").isPlugable(Edge.of("  [][]    ")));

        Assert.assertTrue(Edge.of("    [][]  ").isPlugable(Edge.of("  []      ")));

        Assert.assertTrue(Edge.of("    []    ").isPlugable(Edge.of("  []  []  ")));

        Assert.assertTrue(Edge.of("  [][][]  ").isPlugable(Edge.of("          ")));

        Assert.assertTrue(Edge.of("  [][]    ").isPlugable(Edge.of("      []  ")));

        Assert.assertTrue(Edge.of("  []      ").isPlugable(Edge.of("    [][]  ")));

        Assert.assertTrue(Edge.of("  []  []  ").isPlugable(Edge.of("    []    ")));

        Assert.assertFalse(Edge.of("  []      ").isPlugable(Edge.of("  [][][]  ")));
        Assert.assertFalse(Edge.of("  [][]    ").isPlugable(Edge.of("  [][][]  ")));
        Assert.assertFalse(Edge.of("  [][][]  ").isPlugable(Edge.of("  [][][]  ")));

        Assert.assertFalse(Edge.of("          ").isPlugable(Edge.of("    [][]  ")));
        Assert.assertFalse(Edge.of("          ").isPlugable(Edge.of("      []  ")));
        Assert.assertFalse(Edge.of("          ").isPlugable(Edge.of("          ")));

        Assert.assertFalse(Edge.of("  []  []  ").isPlugable(Edge.of("          ")));

        Assert.assertFalse(Edge.of("  []  []  ").isPlugable(Edge.of("    [][]  ")));

        //Plugable/Unplugable sides

        //Left side is unplugable
        Assert.assertFalse(Edge.of("[][]    []").isPlugable(Edge.of("[]  [][]  ")));
        Assert.assertFalse(Edge.of("[][][]    ").isPlugable(Edge.of("    [][]  ")));

        //Both sides are unplugable
        Assert.assertFalse(Edge.of("[][]    []").isPlugable(Edge.of("[]  [][][]")));
        Assert.assertFalse(Edge.of("[][][]  []").isPlugable(Edge.of("[]  [][][]")));

        //Right side is unplugable
        Assert.assertFalse(Edge.of("  []    []").isPlugable(Edge.of("    [][][]")));
        Assert.assertFalse(Edge.of("  [][]  []").isPlugable(Edge.of("    [][][]")));
    }

    @Test
    public void testUpturn() {
        Assert.assertTrue(Edge.of("  []  [][]").upturn().equals(Edge.of("[][]  []  ")));
        Assert.assertTrue(Edge.of("          ").upturn().equals(Edge.of("          ")));
        Assert.assertTrue(Edge.of("[]        ").upturn().equals(Edge.of("        []")));
        Assert.assertTrue(Edge.of("        []").upturn().equals(Edge.of("[]        ")));

    }

}