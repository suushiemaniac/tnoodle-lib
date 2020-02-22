package org.worldcubeassociation.tnoodle.puzzle;

import cs.cube555.Tools;
import cs.min2phase.Search;
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
    private ThreadLocal<cs.min2phase.SearchWCA> reducedSolver = null;

    public FiveByFiveCubePuzzle() {
        super(5);

        cubeFiveFiveFiveSearcher = new ThreadLocal<cs.cube555.Search>() {
            protected cs.cube555.Search initialValue() {
                cs.cube555.Search.init();
                return new cs.cube555.Search();
            };
        };

        reducedSolver = new ThreadLocal<cs.min2phase.SearchWCA>() {
            @Override
            protected cs.min2phase.SearchWCA initialValue() {
                return new cs.min2phase.SearchWCA();
            }
        };
    }

    @Override
    public PuzzleStateAndGenerator generateRandomMoves(Random r) {
        String randomCube = Tools.randomCube(r);
        String[] solutionPhases = cubeFiveFiveFiveSearcher.get().solveReduction(randomCube, 0);

        String reductionRaw = solutionPhases[0];

        StringBuilder inverseBuilder = new StringBuilder();
        String[] reductionSplits = reductionRaw.split("\\s+");

        for (String move : reductionSplits) {
            char baseMove = move.charAt(0);
            String wcaMove = Character.isLowerCase(baseMove)
                ? (Character.toUpperCase(baseMove) + "w")
                : String.valueOf(baseMove);

            String convertedMove = wcaMove + move.substring(1);
            String inverseMove = invertMove(convertedMove);

            inverseBuilder.insert(0, inverseMove);
            inverseBuilder.insert(0, " ");
        }

        String reducedSolution = reducedSolver.get().solution(solutionPhases[1], 21, Integer.MAX_VALUE, 500, Search.INVERSE_SOLUTION);
        inverseBuilder.insert(0, reducedSolution);

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
