package io.github.vampirestudios.vampirelib;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ProceduralStoneTexture {

	private static final int TEXTURE_SIZE = 256;
	private static final int TILE_SIZE = 128;
	private static final String OUTPUT_FILENAME = "high_contrast_stone_texture.png";

	// Noise frequencies
	private static final float BASE_NOISE_FREQUENCY = 0.01f;
	private static final float WARP_NOISE_FREQUENCY = 0.02f;
	private static final float VEIN_NOISE_FREQUENCY = 0.05f;

	// Noise weights
	private static final float BASE_NOISE_WEIGHT = 0.5f;
	private static final float WARP_NOISE_WEIGHT = 0.3f;
	private static final float VEIN_NOISE_WEIGHT = 0.2f;

	// Number of layers and colors per layer
	private static final int NUM_LAYERS = 8;
	private static final int COLORS_PER_LAYER = 8; // Increased from 8

	// Thresholds for layers
	private static final float[] THRESHOLDS = {0.3f, 0.5f, 0.7f, 0.9f};

	public static void main(String[] args) {
		BufferedImage texture = generateProceduralTexture(TEXTURE_SIZE, TEXTURE_SIZE);

		if (saveTextureToFile(texture, OUTPUT_FILENAME)) {
			System.out.println("Texture saved to: " + OUTPUT_FILENAME);
		} else {
			System.err.println("Failed to save texture.");
		}
	}

	/**
	 * Generates a procedural stone texture image.
	 *
	 * @param width  The width of the image.
	 * @param height The height of the image.
	 * @return A BufferedImage containing the generated texture.
	 */
	private static BufferedImage generateProceduralTexture(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Initialize noise generators
		FastNoiseLite baseNoise = createNoiseGenerator(FastNoiseLite.NoiseType.OpenSimplex2S, BASE_NOISE_FREQUENCY);
		FastNoiseLite warpNoise = createNoiseGenerator(FastNoiseLite.NoiseType.OpenSimplex2S, WARP_NOISE_FREQUENCY);
		FastNoiseLite veinNoise = createNoiseGenerator(FastNoiseLite.NoiseType.OpenSimplex2S, VEIN_NOISE_FREQUENCY);

		// Save base noise layer
		saveNoiseLayer(baseNoise, "base_noise.png");
// Save warp noise layer
		saveNoiseLayer(warpNoise, "warp_noise.png");
// Save vein noise layer
		saveNoiseLayer(veinNoise, "vein_noise.png");

		// Generate the color palette
		Color[] stonePalette = generateExpandedPalette(NUM_LAYERS, COLORS_PER_LAYER);

		// Process each pixel
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float combinedNoise = generateCombinedNoise(x, y, baseNoise, warpNoise, veinNoise);
				Color finalColor = mapNoiseToColor(combinedNoise, stonePalette);
				image.setRGB(x, y, finalColor.getRGB());
			}
		}

		return image;
	}

	private static FastNoiseLite createNoiseGenerator(FastNoiseLite.NoiseType type, float frequency) {
		FastNoiseLite noise = new FastNoiseLite();
		noise.SetNoiseType(type);
		noise.SetFrequency(frequency);
		return noise;
	}

	private static float generateCombinedNoise(int x, int y, FastNoiseLite baseNoise, FastNoiseLite warpNoise, FastNoiseLite veinNoise) {
		// Adjust for seamless tiling
		float adjustedX = (float) (x % TILE_SIZE) / TILE_SIZE;
		float adjustedY = (float) (y % TILE_SIZE) / TILE_SIZE;

		float baseValue = baseNoise.GetNoise(adjustedX * TILE_SIZE, adjustedY * TILE_SIZE);
		float warpValue = warpNoise.GetNoise(adjustedX * TILE_SIZE + baseValue * 50, adjustedY * TILE_SIZE + baseValue * 50);
		float veinValue = veinNoise.GetNoise(adjustedX * TILE_SIZE + warpValue * 30, adjustedY * TILE_SIZE + warpValue * 30);

		float combinedNoise = baseValue * BASE_NOISE_WEIGHT + warpValue * WARP_NOISE_WEIGHT + veinValue * VEIN_NOISE_WEIGHT;
		combinedNoise = (combinedNoise + 1) / 2.0f; // Normalize to [0, 1]

		// Clamp to [0, 1]
		combinedNoise = softClamp(combinedNoise, 0.0f, 1.0f);

		return combinedNoise;
	}

	private static float softClamp(float value, float min, float max) {
		if (value < min) {
			return min + (value - min) / (1 + Math.abs(value - min));
		} else if (value > max) {
			return max - (value - max) / (1 + Math.abs(value - max));
		}
		return value;
	}

	private static Color mapNoiseToColor(float combinedNoise, Color[] palette) {
		int layerIndex = getLayerIndex(combinedNoise, THRESHOLDS);
		float layerT = getLayerInterpolation(combinedNoise, layerIndex, THRESHOLDS);

		int colorsPerLayer = palette.length / NUM_LAYERS;
		int startIndex = layerIndex * colorsPerLayer;

		// Ensure indices are within bounds
		int lowerColorIndex = startIndex + (int) (layerT * (colorsPerLayer - 1));
		lowerColorIndex = Math.min(palette.length - 1, lowerColorIndex);

		int upperColorIndex = lowerColorIndex + 1;
		upperColorIndex = Math.min(startIndex + colorsPerLayer - 1, upperColorIndex);
		upperColorIndex = Math.min(palette.length - 1, upperColorIndex);

		Color lowerColor = palette[lowerColorIndex];
		Color upperColor = palette[upperColorIndex];

		float blend = (layerT * (colorsPerLayer - 1)) - (int) (layerT * (colorsPerLayer - 1));
		blend = Math.max(0.0f, Math.min(1.0f, blend));

		return blendColors(lowerColor, upperColor, blend);
	}

	private static int getLayerIndex(float noiseValue, float[] thresholds) {
		for (int i = 0; i < thresholds.length; i++) {
			if (noiseValue <= thresholds[i]) {
				return i;
			}
		}
		return thresholds.length - 1;
	}

	private static float smoothstep(float edge0, float edge1, float x) {
		// Ensure x is clamped between 0 and 1
		x = Math.max(0.0f, Math.min(1.0f, (x - edge0) / (edge1 - edge0)));
		return x * x * (3 - 2 * x);
	}

	private static float getLayerInterpolation(float noiseValue, int layerIndex, float[] thresholds) {
		float lowerThreshold = layerIndex == 0 ? 0 : thresholds[layerIndex - 1];
		float upperThreshold = thresholds[layerIndex];
		float t = (noiseValue - lowerThreshold) / (upperThreshold - lowerThreshold);

		return smoothstep(0.0f, 1.0f, t);
	}

	private static Color blendColors(Color c1, Color c2, float blend) {
		int r = (int) (c1.getRed() * (1 - blend) + c2.getRed() * blend);
		int g = (int) (c1.getGreen() * (1 - blend) + c2.getGreen() * blend);
		int b = (int) (c1.getBlue() * (1 - blend) + c2.getBlue() * blend);
		return new Color(r, g, b);
	}

	/**
	 * Generates an expanded color palette for the stone texture.
	 *
	 * @param numLayers      The number of layers.
	 * @param colorsPerLayer The number of colors per layer.
	 * @return An array of Colors representing the palette.
	 */
	private static Color[] generateExpandedPalette(int numLayers, int colorsPerLayer) {
		Color[] palette = new Color[numLayers * colorsPerLayer];

		// Define color ranges for each layer
		float[][] hueRanges = {
			{0.0f, 0.05f},   // Layer 1
			{0.04f, 0.08f},  // Layer 2
			{0.07f, 0.12f},  // Layer 3
			{0.1f, 0.15f}    // Layer 4
		};

		float[][] saturationRanges = {
			{0.3f, 0.4f},
			{0.35f, 0.45f},
			{0.4f, 0.5f},
			{0.45f, 0.55f}
		};

		float[][] brightnessRanges = {
			{0.4f, 0.5f},
			{0.45f, 0.55f},
			{0.5f, 0.6f},
			{0.55f, 0.65f}
		};

		int index = 0;
		for (int layer = 0; layer < numLayers; layer++) {
			for (int i = 0; i < colorsPerLayer; i++) {
				float t = (float) i / (colorsPerLayer - 1);
				float hue = lerp(hueRanges[layer][0], hueRanges[layer][1], t);
				float saturation = lerp(saturationRanges[layer][0], saturationRanges[layer][1], t);
				float brightness = lerp(brightnessRanges[layer][0], brightnessRanges[layer][1], t);

				palette[index++] = Color.getHSBColor(hue, saturation, brightness);
			}
		}

		return palette;
	}

	private static float lerp(float start, float end, float t) {
		return start + t * (end - start);
	}

	private static boolean saveTextureToFile(BufferedImage texture, String filename) {
		try {
			File output = new File(filename);
			ImageIO.write(texture, "png", output);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving texture: " + e.getMessage());
			return false;
		}
	}

	private static void saveNoiseLayer(FastNoiseLite noise, String filename) {
		BufferedImage noiseImage = new BufferedImage(TEXTURE_SIZE, TEXTURE_SIZE, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < TEXTURE_SIZE; y++) {
			for (int x = 0; x < TEXTURE_SIZE; x++) {
				float value = noise.GetNoise(x, y);
				int rgb = Color.HSBtoRGB(0, 0, (value + 1) / 2.0f);
				noiseImage.setRGB(x, y, rgb);
			}
		}
		try {
			ImageIO.write(noiseImage, "png", new File(filename));
		} catch (IOException e) {
			System.err.println("Error saving noise layer: " + e.getMessage());
		}
	}
}
