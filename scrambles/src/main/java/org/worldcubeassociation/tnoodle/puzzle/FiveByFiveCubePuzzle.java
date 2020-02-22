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
        String solutionRaw = cubeFiveFiveFiveSearcher.get().solveReduction(randomCube, 0)[0];

        StringBuilder inverseBuilder = new StringBuilder();
        String[] solutionSplits = solutionRaw.split("\\s+");

        for (String move : solutionSplits) {
            char baseMove = move.charAt(0);
            String wcaMove = Character.isLowerCase(baseMove)
                ? (Character.toUpperCase(baseMove) + "w")
                : String.valueOf(baseMove);

            String convertedMove = wcaMove + move.substring(1);
            String inverseMove = invertMove(convertedMove);

            inverseBuilder.insert(0, inverseMove);
            inverseBuilder.insert(0, " ");
        }

        String solution = inverseBuilder.toString().trim();
        AlgorithmBuilder ab = new AlgorithmBuilder(this, MergingMode.CANONICALIZE_MOVES);

        try {
            ab.appendAlgorithm(solution.trim());
        } catch (InvalidMoveException e) {
            throw new RuntimeException(new InvalidScrambleException(solution, e));
        }
        return ab.getStateAndGenerator();
    }

    protected static String invertMove(String baseMove) {
        if (baseMove.endsWith("2")) {
            return baseMove;
        } else if (baseMove.endsWith("'")) {
            return baseMove.substring(0, baseMove.length() - 1);
        } else {
            return baseMove + "'";
        }
    }
}
