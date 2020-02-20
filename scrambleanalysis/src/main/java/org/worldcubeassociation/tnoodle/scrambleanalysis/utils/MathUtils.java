package org.worldcubeassociation.tnoodle.scrambleanalysis.utils;

public class MathUtils {

	public static long nCp(long n, long p) {
		return factorial(n) / factorial(p) / factorial(n - p);
	}

	public static long factorial(long n) {
		if (n < 2) {
			return 1;
		}
		return n * factorial(n - 1);
	}

}
