package net.trystan.asciipanel;

import net.trystan.asciipanel.AsciiCharacterData;

public interface TileTransformer {
	public void transformTile(int x, int y, AsciiCharacterData data);
}