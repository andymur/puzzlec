## How To Build And Run

Build is quite simple

> mvn clean install

After you end up with cubes.jar in your local maven repository (or target dir).

Run using jar

To find solutions for all four sets, type

> java -jar cubes.jar

There will be four files generated in the directory:
first-solution.txt, second-solution.txt, third-solution.txt, fourth-solution.txt

You could also find solution for several problems only, e.g. to find solution for the first and second set please type

> java -jar cubes.jar -first -second

Another way to run it is via tests.

Tests will print out solution to the console not into the file.

For getting first solution please type (for others just change #first to #second and so on)

> mvn clean -Dtest=SolverTest#first test

## Glossary

Here we shortly describe main parts of the puzzle.

### Facet

Facet is a piece of cube, cube consists of six facets.

It could be rotated, flipped.

Example of facet in our text representation

      []  []
      [][][][]
    [][][][][]
      [][][]
    [][]  []

### Orientation

Facet orientation consists of the facet (original facet), one of four sides of orientation and the facet is an original facet flipped or not.

Generally each facet has eight orientations

Four of them are different rotations, for example

      []
  [][][][]
  [][][]
[][][][][]
    []
  []
  [][][]
[][][][]
  [][][][]
  []  []
    []
[][][][][]
  [][][]
[][][][]
  []
  []  []
[][][][]
  [][][][]
  [][][]
      []

And other four is different rotations for the flipped original facet.

### Edge

Edge is one of the four sides of particular facet orientation.

For example for facet

  []  []
[][][][]
  [][][][]
  [][][]
      []

North edge is   []  []

### Well

Well is a concept which means four compatible facest in a row, connected one by one.

Rightmost facet in such row connects to leftmost one.

We call it well, cause it could be folded into something similar to well with four sides and without covers.

It could be presented like this

[]  []    [][]  []      []    [][]
[][][][][]  [][][][]  [][][][]  [][][]
  [][][][]  [][][][]  [][][]  [][][][][]
[][][][]  [][][][]  [][][][][]  [][][]
[]  []    [][]  [][]  []  []  [][]  []

### Unfolding

Unfolding is what we try to find.

It contains a well and two remained facets, one is on the top of the well and another at bottom.

Two remained facets called covers, all their sides should be compatible with corresponding sides of the well.

We also make sure corners of the cube that could be built using unfolding is complete.

Unfolding could be presented like this

                      []  []
                      [][][][]
                    [][][][][]
                      [][][]
                    [][]  []
[]  []    [][]  []      []    [][]
[][][][][]  [][][][]  [][][][]  [][][]
  [][][][]  [][][][]  [][][]  [][][][][]
[][][][]  [][][][]  [][][][][]  [][][]
[]  []    [][]  [][]  []  []  [][]  []
                                  []
                                [][][][]
                              [][][][]
                                [][][][]
                                  []

## Solution High Level Steps

0. First of all we compare all facet edges in all orientations and form special data structure.

It is a dictionary so we could easily find all compatible facet orientations by one facet orientation.

We use it for quick lookup in the future steps.

1. We try to find all possible (unique or not) wells.

2. We try to put remained two facets (covers) on the top and at the bottom of well and check whether such unfolding is complete.

3. After we found first complete unfolding, return it. If we need all unique unfoldings, we need to put them into special set.

## Possible Optimizations

There are a lot of optimizations could be done here.

First of all we could use symmetry at the zero step and reduce number of comparisons between facet orientations.

It would reduce number of possible comparisons in the future steps

Another thing there is more optimal way to build unfolding (final cube).

It might be better to link facets not in one flat but trying build something like a half-cube.

When we build half-cube there are three remained details which should also form half-cube and such half-cubes must be compatible.