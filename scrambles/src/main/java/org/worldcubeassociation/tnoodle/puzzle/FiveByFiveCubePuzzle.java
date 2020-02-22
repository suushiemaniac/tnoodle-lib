package org.worldcubeassociation.tnoodle.puzzle;

import cs.cube555.Search;
import cs.cube555.Tools;
import org.timepedia.exporter.client.Export;
import org.worldcubeassociation.tnoodle.scrambles.AlgorithmBuilder;
import org.worldcubeassociation.tnoodle.scrambles.AlgorithmBuilder.MergingMode;
import org.worldcubeassociation.tnoodle.scrambles.InvalidMoveException;
import org.worldcubeassociation.tnoodle.scrambles.InvalidScrambleException;
import org.worldcubeassociation.tnoodle.scrambles.PuzzleStateAndGenerator;

import java.util.Random;

@Export
public class FiveByFiveCubePuzzle extends CubePuzzle {
    private ThreadLocal<cs.cube555.Search> cubeFiveFiveFiveSearcher = null;

    public FiveByFiveCubePuzzle() {
        super(5);
        Search.init();

        cubeFiveFiveFiveSearcher = new ThreadLocal<cs.cube555.Search>() {
            protected cs.cube555.Search initialValue() {
                return new cs.cube555.Search();
            };
        };
    }

    @Override
    public PuzzleStateAndGenerator generateRandomMoves(Random r) {
        String randomCube = Tools.randomCube(r);
        // TODO invert
        String solution = cubeFiveFiveFiveSearcher.get().solveReduction(randomCube, 0)[0];
        AlgorithmBuilder ab = new AlgorithmBuilder(this, MergingMode.CANONICALIZE_MOVES);
        try {
            ab.appendAlgorithm(solution.trim());
        } catch (InvalidMoveException e) {
            throw new RuntimeException(new InvalidScrambleException(solution, e));
        }
        return ab.getStateAndGenerator();
    }
}
