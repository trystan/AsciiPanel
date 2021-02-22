package asciiPanel;

/**
 *
 * @version 1.3.0.20210220
 * 
 * @author Trystan Spangler
 * @author cblte
 *
 */
public interface TileTransformer {
	public void transformTile(int x, int y, AsciiCharacterData data);
}