package com.andymur.pg.cubes.data;

import com.andymur.pg.cubes.domain.facet.Facet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * First Set of inputs
 */
public final class SecondSet {
    public static final List<Facet> FACETS = new ArrayList<>(
            Arrays.asList(
                    new Facet(
                        new String[] {
                            "      [][]",
                            "  [][][]  ",
                            "[][][][][]",
                            "  [][][]  ",
                            "  []  [][]"
                        }
                    ),
                    new Facet(
                        new String[] {
                            "  []  []  ",
                            "[][][][]  ",
                            "  [][][][]",
                            "[][][][]  ",
                            "  []      "
                        }
                    ),
                    new Facet(
                        new String[] {
                            "  [][]  []",
                            "[][][][][]",
                            "  [][][]  ",
                            "[][][][][]",
                            "[]    [][]"
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
                            "    [][]  ",
                            "[][][][][]",
                            "  [][][]  ",
                            "[][][][][]",
                            "[]  []    "
                        }
                    ),
                    new Facet(
                        new String[] {
                            "  [][]    ",
                            "  [][][]  ",
                            "[][][][][]",
                            "  [][][]  ",
                            "[][]  [][]"
                        }
                    )
            )
    );
}
