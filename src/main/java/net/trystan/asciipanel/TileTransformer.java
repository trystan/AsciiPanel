package net.trystan.asciipanel;

import net.trystan.asciipanel.AsciiCharacterData;

/**
 * The TileTransformer interface represents an object that can transform tiles in an AsciiPanel.
 */
public interface TileTransformer {

	/**
	 * Transforms the given tile at the specified (x, y) coordinates.
	 *
	 * @param x the x-coordinate of the tile to transform
	 * @param y the y-coordinate of the tile to transform
	 * @param data the AsciiCharacterData object representing the tile to transform
	 */
	public void transformTile(int x, int y, AsciiCharacterData data);
}