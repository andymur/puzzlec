package com.andymur.pg.cubes.domain;

import com.andymur.pg.cubes.domain.facet.Facet;

public class UnfoldingTestData {

    public static Unfolding FIRST_INCOMPLETE = Unfolding.of(
            new Facet[]{
                    new Facet(
                            new String[]{
                                    "    []    ",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "    []    ",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "[]  []  []",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "[]  []  []",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "    []    ",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "    []    ",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "  []  []  ",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "[][]  []  ",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "  []  []  ",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "[]  []    ",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "  []  []  ",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "[][]  [][]",
                            }
                    ),
            }
    );

    public static Unfolding FIRST_COMPLETE = Unfolding.of(
            new Facet[] {
                    new Facet(
                            new String[] {
                                    "[][]  [][]",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "  []  []  ",
                            }
                    ),
                    new Facet(
                            new String[] {
                                    "  []  []  ",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "[][]  []  ",
                            }
                    ),
                    new Facet(
                            new String[] {
                                    "[][]  [][]",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "[][]  [][]",
                            }
                    ),
                    new Facet(
                            new String[] {
                                    "    []    ",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "  []  [][]",
                            }
                    ),
                    new Facet(
                            new String[] {
                                    "    []    ",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "    []    ",
                            }
                    ),
                    new Facet(
                            new String[] {
                                    "    []    ",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "    []    ",
                            }
                    ),
            }
    );

    public static final Unfolding FIRST_WITH_HOLE = Unfolding.of(
            new Facet[]{
                    new Facet(
                            new String[]{
                                    "[][]  [][]",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "  []  []  ",
                            }
                    ),
                    //With a Hole In The Bottom Left Corner
                    new Facet(
                            new String[]{
                                    "  []  []  ",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "  []  []  ",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "[][]  [][]",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "[][]  [][]",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "    []    ",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "  []  [][]",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "    []    ",
                                    "[][][][]  ",
                                    "  [][][][]",
                                    "[][][][]  ",
                                    "    []    ",
                            }
                    ),
                    new Facet(
                            new String[]{
                                    "    []    ",
                                    "  [][][]  ",
                                    "[][][][][]",
                                    "  [][][]  ",
                                    "    []    ",
                            }
                    ),
            }
    );

    public static final Unfolding WITH_CORNER_CONFLICT = Unfolding.of(
            new Facet[] {
                new Facet(
                        new String[] {
                                "[][]  [][]",
                                "  [][][][]",
                                "[][][][]  ",
                                "  [][][][]",
                                "  []  []  ",
                        }
                ),
                new Facet(
                        new String[] {
                                "  []  []  ",
                                "  [][][][]",
                                "[][][][]  ",
                                "  [][][][]",
                                "[][]  []  ",
                        }
                ),
                new Facet(
                        new String[] {
                                "[][]  [][]",
                                "  [][][]  ",
                                "[][][][][]",
                                "  [][][]  ",
                                "[][]  [][]",
                        }
                ),
                //Conflict in the Right Top Corner
                new Facet(
                        new String[] {
                                "    []  []",
                                "[][][][][]",
                                "  [][][]  ",
                                "[][][][][]",
                                "  []  [][]",
                        }
                ),
                new Facet(
                        new String[] {
                                "    []    ",
                                "[][][][]  ",
                                "  [][][][]",
                                "[][][][]  ",
                                "    []    ",
                        }
                ),
                new Facet(
                        new String[] {
                                "    []    ",
                                "  [][][]  ",
                                "[][][][][]",
                                "  [][][]  ",
                                "    []    ",
                        }
                ),
    }
    );
}
