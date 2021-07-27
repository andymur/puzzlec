package com.andymur.pg.cubes.domain;

import com.andymur.pg.cubes.helper.RowHelper;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Edge represents one of four edges of facet.
 * Edge is a line of five blocks (empty or not)
 *
 * @see com.andymur.pg.cubes.domain.facet.Facet
 * @author andymur
 */
public class Edge {

    public static final int EDGE_LENGTH = 5;

    private final int[] state;

    /**
     * Creates an edge from array of zeroes and ones.
     * Zero means empty block while one non-empty.
     *
     * @param state array of five 1/0 elements.
     */
    public Edge(int[] state) {

        if (state == null) {
            throw new IllegalArgumentException("Edge cannot be null valued");
        }

        final int stateLength = state.length;

        if (stateLength != EDGE_LENGTH) {
            throw new IllegalArgumentException(String.format("Edge cannot consist of %d blocks", stateLength));
        }

        this.state = Arrays.copyOf(state, EDGE_LENGTH);
    }

    /**
     * Checks whether edge is plugable with its counterpart
     * @param edge to check compatibility with
     * @return true if edges are compatible
     */
    public boolean isPlugable(Edge edge) {
        boolean plugableCenter = IntStream.range(1, EDGE_LENGTH - 1)
                .map(i -> state[i] ^ edge.state[i])
                .reduce(1, (l, r) -> l & r) == 1;

        boolean plugableSides = ((state[0] & edge.state[0])
                | (state[EDGE_LENGTH - 1] & edge.state[EDGE_LENGTH - 1])) == 0;

        return plugableCenter && plugableSides;
    }

    /**
     * Turns edge from left to right.
     * For example 10010 -> 01001
     *
     * @return turned edge
     */
    public Edge upturn() {
        return new Edge(IntStream.range(0, EDGE_LENGTH).map(i -> state[EDGE_LENGTH - (i + 1)]).toArray());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return Arrays.equals(state, edge.state);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(state);
    }

    @Override
    public String toString() {
        return RowHelper.rowToString(state);
    }

    /**
     * Creates edge from the string representation.
     * Empty block encoded as two spaces and non-empty as [].
     *
     * @param stringEncodedEdge string representation of edge
     * @return edge of string representation
     */
    public static Edge of(String stringEncodedEdge) {
        return new Edge(RowHelper.encodeRow(stringEncodedEdge));
    }

    /**
     * Checks that at least one of three edges at the cube corner fills the hole
     *
     * @param edgeA the first edge of the corner
     * @param edgeB the second edge of the corner
     * @param edgeC the third edge of the
     * @return true if corner filled
     */
    public static boolean cornerFilled(Edge edgeA, Edge edgeB, Edge edgeC) {
        return (edgeA.state[0] | edgeB.state[0] | edgeC.state[0]) == 1;
    }
}
