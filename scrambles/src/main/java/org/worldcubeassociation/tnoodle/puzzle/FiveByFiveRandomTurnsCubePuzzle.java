package org.worldcubeassociation.tnoodle.puzzle;

import org.timepedia.exporter.client.Export;

/*
 * The 5x5 solver is ridiculously resource intensive, this gives people
 * an option for lighter weight (unofficial!) scrambles.
 */
@Export
public class FiveByFiveRandomTurnsCubePuzzle extends CubePuzzle {
    public FiveByFiveRandomTurnsCubePuzzle() {
        super(5);
    }

    @Override
    public String getShortName() {
        return "555fast";
    }

    @Override
    public String getLongName() {
        return "5x5x5 (fast, unofficial)";
    }
}
