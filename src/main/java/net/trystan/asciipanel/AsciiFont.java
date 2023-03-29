package net.trystan.asciipanel;

/**
 * This class provides all available fonts for the AsciiPanel.
 * Some graphics are from the Dwarf Fortress Tileset Wiki Page.
 *
 * More oldschool fonts can be found on this website
 * https://int10h.org/oldschool-pc-fonts/fontlist/
 * 
 * @author github.com/cblte
 *
 */
public class AsciiFont {

	/**
	 * The 8x8 CP437 font.
	 */
	public static final AsciiFont CP437_8x8 = new AsciiFont("cp437_8x8.png", 8, 8);

	/**
	 * The 10x10 CP437 font.
	 */
	public static final AsciiFont CP437_10x10 = new AsciiFont("cp437_10x10.png", 10, 10);

	/**
	 * The 12x12 CP437 font.
	 */
	public static final AsciiFont CP437_12x12 = new AsciiFont("cp437_12x12.png", 12, 12);

	/**
	 * The 16x16 CP437 font.
	 */
	public static final AsciiFont CP437_16x16 = new AsciiFont("cp437_16x16.png", 16, 16);

	/**
	 * The 9x16 CP437 font.
	 */
	public static final AsciiFont CP437_9x16 = new AsciiFont("cp437_9x16.png", 9, 16);

	/**
	 * The 10x10 Drake font.
	 */
	public static final AsciiFont DRAKE_10x10 = new AsciiFont("drake_10x10.png", 10, 10);

	/**
	 * The 10x10 Taffer font.
	 */
	public static final AsciiFont TAFFER_10x10 = new AsciiFont("taffer_10x10.png", 10, 10);

	/**
	 * The 10x10 Qbicfeet font.
	 */
	public static final AsciiFont QBICFEET_10x10 = new AsciiFont("qbicfeet_10x10.png", 10, 10);

	/**
	 * The 15x15 Talryth Square font.
	 */
	public static final AsciiFont TALRYTH_15_15 = new AsciiFont("talryth_square_15x15.png", 15, 15);

	/**
	 * The filename of the font image.
	 */
	private String fontFilename;

	/**
	 * Returns the filename of the font image.
	 *
	 * @return The filename of the font image.
	 */
	public String getFontFilename() {
		return fontFilename;
	}

	/**
	 * The width of each character in the font image.
	 */
	private int width;

	/**
	 * Returns the width of each character in the font image.
	 *
	 * @return The width of each character in the font image.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * The height of each character in the font image.
	 */
	private int height;

	/**
	 * Returns the height of each character in the font image.
	 *
	 * @return The height of each character in the font image.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Constructs an {@code AsciiFont} object with the specified font file name, width, and height.
	 *
	 * @param filename the file name of the font
	 * @param width the width of the font
	 * @param height the height of the font
	 */
	public AsciiFont(String filename, int width, int height) {
		this.fontFilename = filename;
		this.width = width;
		this.height = height;
	}
}
