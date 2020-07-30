package de.alpmann.youtube.geceroland;

import java.util.Random;

public class MathUtils {
	public static float randomRange(float min, float max) {
	    Random rand = new Random();
	    return rand.nextFloat() * (max - min) + min;
	}
}
