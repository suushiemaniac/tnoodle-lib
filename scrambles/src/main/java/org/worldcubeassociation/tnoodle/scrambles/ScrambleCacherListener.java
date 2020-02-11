package org.worldcubeassociation.tnoodle.scrambles;

public interface ScrambleCacherListener {
    public void scrambleCacheUpdated(ScrambleCacher src);

    public void scrambleGenerated(ScrambleCacher src, String scramble);

    public void scrambleRetrieved(ScrambleCacher src, String scramble);
}
