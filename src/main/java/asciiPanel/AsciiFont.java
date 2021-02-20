package asciiPanel;

/**
 * This class holds provides all available Fonts for the AsciiPanel.
 * Some graphics are from the {@linkplain <a href="https://dwarffortresswiki.org/index.php/Tileset_repository">Dwarf Fortress Tileset Wiki Page</a>}
 * <p>
 * 2021-02-20 cblte: 
 * 
 * added curses based on 24x36 Tileset from the {@linkplain <a href="https://dwarffortresswiki.org/index.php/Tileset_repository#24.C3.9736">Dwarf-Fortress-Wiki</a>}
 * 
 * <li> CURSES_24_36: 1280×600 (80×25 grid size); 1280×720 (80×30 grid size, double for 4k)</li>
 * <li> CURSES_24_36: 1920×900 (80×25 grid size); 1920×1080 (80×30 grid size, double for 4k)</li>
 * </p>
 * 
 * @version 1.3.0.20210220
 * 		
 * @author Trystan Spangler
 * @author cblte
 * 
 */
public class AsciiFont {

	public static final AsciiFont CP437_8x8 = new AsciiFont("cp437_8x8.png", 8, 8);
	public static final AsciiFont CP437_10x10 = new AsciiFont("cp437_10x10.png", 10, 10);
	public static final AsciiFont CP437_12x12 = new AsciiFont("cp437_12x12.png", 12, 12);
	public static final AsciiFont CP437_16x16 = new AsciiFont("cp437_16x16.png", 16, 16);
	public static final AsciiFont CP437_9x16 = new AsciiFont("cp437_9x16.png", 9, 16);
	public static final AsciiFont DRAKE_10x10 = new AsciiFont("drake_10x10.png", 10, 10);
	public static final AsciiFont TAFFER_10x10 = new AsciiFont("taffer_10x10.png", 10, 10);
	public static final AsciiFont QBICFEET_10x10 = new AsciiFont("qbicfeet_10x10.png", 10, 10);
	public static final AsciiFont TALRYTH_15_15 = new AsciiFont("talryth_square_15x15.png", 15, 15);
	public static final AsciiFont CURSES_16_24 = new AsciiFont("curses_16_24.png", 16, 24);
	public static final AsciiFont CURSES_24_36 = new AsciiFont("curses_24_36.png", 24, 36);
	
	private String fontFilename;

	public String getFontFilename() {
		return fontFilename;
	}

	private int width;

	public int getWidth() {
		return width;
	}

	private int height;

	public int getHeight() {
		return height;
	}

	public AsciiFont(String filename, int width, int height) {
		this.fontFilename = filename;
		this.width = width;
		this.height = height;
	}
}
