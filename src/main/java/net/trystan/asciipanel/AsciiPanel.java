package net.trystan.asciipanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This simulates a code page 437 ASCII terminal display.
 *
 * @author Trystan Spangler
 */
public class AsciiPanel extends JPanel {
	private static final long serialVersionUID = -4167851861147593092L;

    /**
     * Represents the color black with RGB values (0, 0, 0).
     */
    public static Color black = new Color(0, 0, 0);

    /**
     * Represents the color red with RGB values (128, 0, 0).
     */
    public static Color red = new Color(128, 0, 0);

    /**
     * Represents the color green with RGB values (0, 128, 0).
     */
    public static Color green = new Color(0, 128, 0);

    /**
     * Represents the color yellow with RGB values (128, 128, 0).
     */
    public static Color yellow = new Color(128, 128, 0);

    /**
     * Represents the color blue with RGB values (0, 0, 128).
     */
    public static Color blue = new Color(0, 0, 128);

    /**
     * Represents the color magenta with RGB values of (128, 0, 128).
     */
    public static Color magenta = new Color(128, 0, 128);

    /**
     * Represents the color cyan with RGB values (0, 128, 128).
     */
    public static Color cyan = new Color(0, 128, 128);

    /**
     * Represents the color cyan with RGB values (0, 128, 128).
     */
    public static Color white = new Color(192, 192, 192);

    /**
     * Represents the color black (dark gray) with RGB values (128, 128, 128).
     */
    public static Color brightBlack = new Color(128, 128, 128);

    /**
     * Represents a brighter red with RGB values (255, 0, 0).
     */
    public static Color brightRed = new Color(255, 0, 0);

    /**
     * Represents a brighter green with RGB values(0, 255, 0).
     */
    public static Color brightGreen = new Color(0, 255, 0);

    /**
     * Represents a brighter yellow with RGB values (255, 255, 0).
     */
    public static Color brightYellow = new Color(255, 255, 0);

    /**
     * Represents a brighter blue with RGB values (0, 0, 255).
     */
    public static Color brightBlue = new Color(0, 0, 255);

    /**
     * Represents a brighter magenta with RGB values (255, 0, 255).
     */
    public static Color brightMagenta = new Color(255, 0, 255);

    /**
     * Represents a brighter cyan with RGB values (0, 255, 255).
     */
    public static Color brightCyan = new Color(0, 255, 255);
    
    /**
     * Represents a brighter white (pure white) with RGB values (255, 255, 255).
     */
    public static Color brightWhite = new Color(255, 255, 255);

    private Image offscreenBuffer;
    private Graphics offscreenGraphics;
    private int widthInCharacters;
    private int heightInCharacters;
    private int charWidth = 9;
    private int charHeight = 16;
    private String terminalFontFile = "cp437_9x16.png";
    private Color defaultBackgroundColor;
    private Color defaultForegroundColor;
    private int cursorX;
    private int cursorY;
    private BufferedImage glyphSprite;
    private BufferedImage[] glyphs;
    private AsciiCharacterData[][] characters;
    private AsciiCharacterData[][] previousCharacters;
    private AsciiFont asciiFont;

    /**
     * Returns the height of a single character in pixels.
     *
     * @return the height of a single character in pixels
     */
    public int getCharHeight() {
        return charHeight;
    }

    /**
     * Returns the width of each character in pixels.
     *
     * @return the width of each character in pixels
     */
    public int getCharWidth() {
        return charWidth;
    }

    /**
     * Returns the height of the terminal measured in characters.
     * A standard terminal is 24 characters high.
     *
     * @return the height of the terminal in characters
     */
    public int getHeightInCharacters() {
        return heightInCharacters;
    }

    /**
     * Returns the width of the terminal measured in characters.
     * A standard terminal is 80 characters wide.
     *
     * @return the width of the terminal in characters
     */
    public int getWidthInCharacters() {
        return widthInCharacters;
    }

    /**
     * Returns the X position of the cursor, which represents the distance from the
     * left edge of the screen where new text will be written.
     *
     * @return the X position of the cursor as an integer.
     */
    public int getCursorX() {
        return cursorX;
    }


    /**
     * Sets the distance from the left new text will be written to.
     * This should be equal to or greater than 0 and less than the width in characters.
     *
     * @param cursorX the distance from the left new text should be written to
     */
    public void setCursorX(int cursorX) {
        if (cursorX < 0 || cursorX >= widthInCharacters)
            throw new IllegalArgumentException("cursorX " + cursorX + " must be within range [0," + widthInCharacters + ")." );

        this.cursorX = cursorX;
    }

    /**
     * Returns the Y position of the cursor, which represents the distance from the
     * top edge of the screen where new text will be written.
     *
     * @return the Y position of the cursor as an integer.
     */
    public int getCursorY() {
        return cursorY;
    }

    /**
     * Sets the distance from the top new text will be written to.
     * This should be equal to or greater than 0 and less than the height in characters.
     *
     * @param cursorY the distance from the top new text should be written to
     */
    public void setCursorY(int cursorY) {
        if (cursorY < 0 || cursorY >= heightInCharacters)
            throw new IllegalArgumentException("cursorY " + cursorY + " must be within range [0," + heightInCharacters + ")." );

        this.cursorY = cursorY;
    }

    /**
     /**
     * Sets the position of the text cursor to the specified x and y coordinates.
     * The position is measured in character cells, with the origin (0,0) in the upper-left corner.
     * The x coordinate should be within the bounds of the screen, where 0 is the leftmost column and
     * the maximum value is one less than the width of the screen in characters.
     * The y coordinate should also be within the bounds of the screen, where 0 is the top row and the
     * maximum value is one less than the height of the screen in characters.
     *
     * @param x the x coordinate of the new cursor position
     * @param y the y coordinate of the new cursor position
     * @throws IllegalArgumentException if the x or y coordinate is outside the bounds of the screen
     */
    public void setCursorPosition(int x, int y) {
        setCursorX(x);
        setCursorY(y);
    }

    /**
     * Returns the default background color that is used when writing new text.
     * If no explicit background color is specified for a character or area of text, this color is used.
     *
     * @return the default background color used for new text
     */
    public Color getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    /**
     * Sets the default background color for this AsciiPanel.
     *
     * @param defaultBackgroundColor the new default background color to set
     * @throws NullPointerException if the given color is null
     */
    public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
        if (defaultBackgroundColor == null)
            throw new NullPointerException("defaultBackgroundColor must not be null.");

        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    /**
     * Returns the default foreground color that is used when writing new text.
     * If no explicit foreground color is specified for a character or area of text, this color is used.
     *
     * @return the default foreground color used for new text
     */
    public Color getDefaultForegroundColor() {
        return defaultForegroundColor;
    }

    /**
     * Sets the default foreground color that is used when writing new text.
     * This color will be used for all new text unless an explicit foreground color is specified for a character or area of text.
     *
     * @param defaultForegroundColor the new default foreground color to use for new text
     * @throws NullPointerException if defaultForegroundColor is null
     */
    public void setDefaultForegroundColor(Color defaultForegroundColor) {
        if (defaultForegroundColor == null)
            throw new NullPointerException("defaultForegroundColor must not be null.");

        this.defaultForegroundColor = defaultForegroundColor;
    }

    /**
     * Gets the currently selected font.
     *
     * @return the AsciiFont object that is currently selected for rendering text
     */
    public AsciiFont getAsciiFont() {
        return asciiFont;
    }

    /**
     * Sets the font used for rendering text in this panel. After setting the font, it is recommended to ensure that the
     * parent component is properly sized to fit the new font, as the panel dimensions will most likely change.
     *
     * @param font the AsciiFont object to set as the font for rendering text
     */
    public void setAsciiFont(AsciiFont font) {
        if (this.asciiFont == font) {
            return;
        }

        this.asciiFont = font;

        // Update character size and font file
        this.charHeight = font.getHeight();
        this.charWidth = font.getWidth();
        this.terminalFontFile = font.getFontFilename();

        // Resize panel to fit new font
        Dimension panelSize = new Dimension(charWidth * widthInCharacters, charHeight * heightInCharacters);
        setPreferredSize(panelSize);

        // Reload glyphs and characters
        glyphs = new BufferedImage[256];
        offscreenBuffer = new BufferedImage(panelSize.width, panelSize.height, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = offscreenBuffer.getGraphics();
        loadGlyphs();
        previousCharacters = new AsciiCharacterData[widthInCharacters][heightInCharacters];
    }


    /**
     * Returns the two-dimensional array of AsciiCharacterData objects that are currently written.
     * The array represents the characters that have been written so far.
     *
     * @return the two-dimensional array of AsciiCharacterData objects
     */
    public AsciiCharacterData[][] getCharacters() {
        return characters;
    }

    /**
     * Constructs a new AsciiPanel with a default size of 80x24.
     */
    public AsciiPanel() {
        this(80, 24);
    }

    /**
     * Constructs a new AsciiPanel object with the specified width and height in characters.
     *
     * @param width the width of the AsciiPanel in characters
     * @param height the height of the AsciiPanel in characters
     *
     * @throws IllegalArgumentException if either width or height is less than or equal to 0
     */
    public AsciiPanel(int width, int height) {
    	this(width, height, null);
    }

    /**
     * Constructs a new AsciiPanel object with the specified width and height in characters and an AsciiFont.
     *
     * @param width the width of the AsciiPanel in characters
     * @param height the height of the AsciiPanel in characters
     * @param font the AsciiFont to be used; if null, the standard font CP437_9x16 will be used instead
     *
     * @throws IllegalArgumentException if either width or height is less than or equal to 0
     */
    public AsciiPanel(int width, int height, AsciiFont font) {
        super();

        // Check for invalid values for width and height
        if (width < 1) {
            throw new IllegalArgumentException("width " + width + " must be greater than 0." );
        }

        if (height < 1) {
            throw new IllegalArgumentException("height " + height + " must be greater than 0." );
        }

        // Initialize instance variables
        widthInCharacters = width;
        heightInCharacters = height;
        defaultBackgroundColor = black;
        defaultForegroundColor = white;
        characters = new AsciiCharacterData[widthInCharacters][heightInCharacters];

        // If no font is specified, use the default CP437_9x16 font
        if(font == null) {
        	font = AsciiFont.CP437_9x16;
        }

        // Set the AsciiFot and clear the panel
        setAsciiFont(font);
        clear();
    }

    /**
     * Overrides the update method of the Component class to call the paint method.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    public void update(Graphics g) {
         paint(g); 
    }

    /**
     * Paints the AsciiPanel using the specified Graphics context.
     *
     * @param g the Graphics context in which to paint
     *
     * @throws NullPointerException if the specified Graphics context is null
     */
    @Override
    public void paint(Graphics g) {
        if (g == null)
            throw new NullPointerException();

        // Iterate through all characters in the panel
        for (int x = 0; x < widthInCharacters; x++) {
            for (int y = 0; y < heightInCharacters; y++) {
                // Get the AsciiCharacterData objects for the current character
                AsciiCharacterData previousCharacterData = previousCharacters[x][y];
                AsciiCharacterData newCharacterData = characters[x][y];

                // If the new character has the same colors and character as the previous character, skip it
                if (previousCharacterData != null
                        && newCharacterData.backgroundColor == previousCharacterData.backgroundColor
                        && newCharacterData.foregroundColor == previousCharacterData.foregroundColor
                        && newCharacterData.character == previousCharacterData.character)
                    continue;

                // Create a new LookupOp to set the colors of the glyph for the new character
                LookupOp op = setColors(newCharacterData.backgroundColor, newCharacterData.foregroundColor);

                // Apply the LookupOp to the glyph and draw it onto the offscreen image buffer
                BufferedImage img = op.filter(glyphs[newCharacterData.character], null);
                offscreenGraphics.drawImage(img, x * charWidth, y * charHeight, null);

                // Update the previousCharacters array with the new character data
                previousCharacters[x][y] = newCharacterData;
            }
        }
        // Draw the offscreen buffer onto the specified Graphics context
        g.drawImage(offscreenBuffer,0,0,this);
    }

    /**
     * Loads the glyph images from the terminal font file.
     * If the file cannot be found, prints an error message to standard error.
     * This method is called internally by the constructor.
     */
    private void loadGlyphs() {
        try {
            glyphSprite = ImageIO.read(AsciiPanel.class.getClassLoader().getResource(terminalFontFile));
        } catch (IOException e) {
            System.err.println("loadGlyphs(): " + e.getMessage());
        }

        for (int i = 0; i < 256; i++) {
            int sx = (i % 16) * charWidth;
            int sy = (i / 16) * charHeight;

            glyphs[i] = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
            glyphs[i].getGraphics().drawImage(glyphSprite, 0, 0, charWidth, charHeight, sx, sy, sx + charWidth, sy + charHeight, null);
        }
    }
        
    /**
     * Create a <code>LookupOp</code> object (lookup table) mapping the original 
     * pixels to the background and foreground colors, respectively.
     *
     * @param bgColor the background color
     * @param fgColor the foreground color
     *
     * @return the <code>LookupOp</code> object (lookup table)
     */
    private LookupOp setColors(Color bgColor, Color fgColor) {
        short[] a = new short[256];
        short[] r = new short[256];
        short[] g = new short[256];
        short[] b = new short[256];

        byte bga = (byte) (bgColor.getAlpha());
        byte bgr = (byte) (bgColor.getRed());
        byte bgg = (byte) (bgColor.getGreen());
        byte bgb = (byte) (bgColor.getBlue());

        byte fga = (byte) (fgColor.getAlpha());
        byte fgr = (byte) (fgColor.getRed());
        byte fgg = (byte) (fgColor.getGreen());
        byte fgb = (byte) (fgColor.getBlue());

        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                a[i] = bga;
                r[i] = bgr;
                g[i] = bgg;
                b[i] = bgb;
            } else {
                a[i] = fga;
                r[i] = fgr;
                g[i] = fgg;
                b[i] = fgb;
            }
        }

        short[][] table = {r, g, b, a};
        return new LookupOp(new ShortLookupTable(0, table), null);
    }

    /**
     * Clears the entire screen with the default background color, using the ASCII character specified.
     *
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel clear() {
        return clear(' ', 0, 0, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Clears the entire screen with the specified character and whatever the default foreground and background colors are.
     *
     * @param character the character to fill the screen with
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel clear(char character) {
        return clear(character, 0, 0, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Clear the entire screen with the specified character and specified foreground and background colors.
     * This method will clear the entire screen with the specified character and foreground and background colors.
     * If the foreground or background colors are null, the default colors will be used instead.
     *
     * @param character The character to write.
     * @param foreground The foreground color or null to use the default.
     * @param background The background color or null to use the default.
     *
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, Color foreground, Color background) {
        return clear(character, 0, 0, widthInCharacters, heightInCharacters, foreground, background);
    }

    /**
     * Clears a rectangular section of the screen with the specified character and whatever the default foreground and background colors are.
     * The cursor position will not be modified.
     *
     * @param character the character to write
     * @param x         the x coordinate of the top left corner of the section to clear
     * @param y         the y coordinate of the top left corner of the section to clear
     * @param width     the width of the section to clear
     * @param height    the height of the section to clear
     *
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, int x, int y, int width, int height) {
        return clear(new AsciiCharacterData(character, defaultForegroundColor, defaultBackgroundColor), x, y, width, height);
    }

    /**
     * Clear the section of the screen with the specified character and the specified foreground and background colors.
     * The cursor position will not be modified.
     *
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param width      the width of the section to clear
     * @param height     the height of the section to clear
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     *
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, int x, int y, int width, int height, Color foreground, Color background) {
        return clear(new AsciiCharacterData(character, foreground, background), x, y, width, height);
    }

     /**
     * Clears a rectangular section of the screen with the specified AsciiCharacterData and whatever the default foreground and background colors are.
     * The cursor position will not be modified.
     *
     * @param characterData the AsciiCharacterData to write
     * @param x the distance from the left to begin writing from
     * @param y the distance from the top to begin writing from
     * @param width the width of the section to clear
     * @param height the height of the section to clear
     *
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     *
     * @throws IllegalArgumentException if any of the arguments are outside the allowed range or invalid
     */
    public AsciiPanel clear(AsciiCharacterData characterData, int x, int y, int width, int height) {
        if (characterData.character >= glyphs.length)
            throw new IllegalArgumentException("character " + characterData.character + " must be within range [0," + glyphs.length + "]." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        if (width < 1)
            throw new IllegalArgumentException("width " + width + " must be greater than 0." );

        if (height < 1)
            throw new IllegalArgumentException("height " + height + " must be greater than 0." );

        if (x + width > widthInCharacters)
            throw new IllegalArgumentException("x + width " + (x + width) + " must be less than " + (widthInCharacters + 1) + "." );

        if (y + height > heightInCharacters)
            throw new IllegalArgumentException("y + height " + (y + height) + " must be less than " + (heightInCharacters + 1) + "." );

        int originalCursorX = cursorX;
        int originalCursorY = cursorY;
        for (int xo = x; xo < x + width; xo++) {
            for (int yo = y; yo < y + height; yo++) {
                write(characterData, xo, yo);
            }
        }
        cursorX = originalCursorX;
        cursorY = originalCursorY;

        return this;
    }

    /**
     * Writes a character to the screen at the cursor's position, using the default foreground and background colors.
     * <p>
     * This method updates the cursor's position after writing the character. To write a character at a specific position
     * on the screen, use the overloaded write method that takes in the x and y coordinates.
     *
     * @param character the character to write
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel write(char character) {
        return write(character, cursorX, cursorY, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Writes a character to the screen at the cursor's position, using the default foreground and background colors.
     * <p>
     * This method updates the cursor's position after writing the character. To write a character at a specific position
     * on the screen, use the overloaded write method that takes in the x and y coordinates.
     *
     * @param character the character to write
     * @param foreground the foreground color or null to use the default
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel write(char character, Color foreground) {
        return write(character, cursorX, cursorY, foreground, defaultBackgroundColor);
    }

    /**
     * Writes a character to the screen at the cursor's position with the specified foreground and background colors.
     * This method updates the cursor's position but does not modify the default foreground or background colors.
     *
     * @param character  the character to write
     * @param foreground the foreground color to use or null to use the default
     * @param background the background color to use or null to use the default
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel write(char character, Color foreground, Color background) {
        return write(character, cursorX, cursorY, foreground, background);
    }

    /**
     * Writes a character to the screen at the specified position.
     * This method updates the cursor's position.
     *
     * @param character the character to write
     * @param x         the distance from the left to begin writing from
     * @param y         the distance from the top to begin writing from
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y) {
        return write(character, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a character to the specified position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     *
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y, Color foreground) {
        return write(character, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Write a character to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y, Color foreground, Color background) {
        return write(new AsciiCharacterData(character, foreground, background), x, y);
    }

    /**
     * Writes an AsciiCharacterData object to the specified position on the AsciiPanel.
     * The cursor's position is updated accordingly, but the default foreground or background colors are not.
     * @param characterData  the AsciiCharacterData object to write
     * @param x          the distance from the left to begin writing from, must be within [0, {@link #getWidthInCharacters()})
     * @param y          the distance from the top to begin writing from, must be within [0, {@link #getHeightInCharacters()})
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     * @throws IllegalArgumentException if the character value of the AsciiCharacterData is outside the range, or if x or y is outside the valid range
     */
    public AsciiPanel write(AsciiCharacterData characterData, int x, int y) {
        if (characterData.character >= glyphs.length)
            throw new IllegalArgumentException("character " + characterData.character + " must be within range [0," + glyphs.length + "]." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        if (characterData.foregroundColor == null) {
            characterData.foregroundColor = defaultForegroundColor;
        }

        if (characterData.backgroundColor == null) {
            characterData.backgroundColor = defaultBackgroundColor;
        }

        characters[x][y] = characterData;

        // update the cursor's position
        cursorX = x + 1;
        cursorY = y;

        return this;
    }

    /**
     * Write a string to the cursor's position.
     * This updates the cursor's position to the end of the written string.
     *
     * @param string the string to write
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     * @throws NullPointerException     if the given string is null
     * @throws IllegalArgumentException if the written string exceeds the panel's width
     */
    public AsciiPanel write(String string) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + ".");

        return write(string, cursorX, cursorY, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a string to the cursor's position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     *
     * @param string     the string to write
     * @param foreground the foreground color or null to use the default
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     * @throws NullPointerException      if string is null
     * @throws IllegalArgumentException  if the length of the string exceeds the available space to write to
     *                                    based on the current cursor position
     */
    public AsciiPanel write(String string, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + "." );

        return write(string, cursorX, cursorY, foreground, defaultBackgroundColor);
    }

    /**
     * Writes a string at the current cursor position with the specified foreground and background colors.
     * This updates the cursor's position to the end of the string.
     *
     * @param string the string to write (must not be null)
     * @param foreground the foreground color, or null to use the default
     * @param background the background color, or null to use the default
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     * @throws NullPointerException if the string argument is null
     * @throws IllegalArgumentException if the specified string extends beyond the right edge of the panel
     */
    public AsciiPanel write(String string, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + "." );

        return write(string, cursorX, cursorY, foreground, background);
    }

    /**
     * Writes the specified string to the specified position.
     * The method updates the cursor's position to the end of the written string.
     *
     * @param string     the string to write, must not be null
     * @param x          the horizontal position to begin writing from
     * @param y          the vertical position to begin writing from
     *
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     *
     * @throws NullPointerException if string is null
     * @throws IllegalArgumentException if x or y are out of bounds
     * @see #getWidthInCharacters()
     * @see #getHeightInCharacters()
     */
    public AsciiPanel write(String string, int x, int y) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + "." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Writes the specified string to the specified position with the specified foreground color.
     * This method updates the cursor's position but not the default foreground color.
     *
     * @param string     the string to write (must not be null)
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color, or null to use the default
     *
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     *
     * @throws NullPointerException      if the string argument is null
     * @throws IllegalArgumentException  if x or y is out of range, or if the specified string extends beyond the right edge of the panel.
     *
     * @see #getWidthInCharacters()
     * @see #getHeightInCharacters()
     */
    public AsciiPanel write(String string, int x, int y, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + "." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(string, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Write a string to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     *
     * @param string     the string to write (must not be null)
     * @param x          the distance from the left to begin writing from (must be within range [0, {@link #getWidthInCharacters()})
     * @param y          the distance from the top to begin writing from (must be within range [0, {@link #getHeightInCharacters()})
     * @param foreground the foreground color, or null to use the default (see {@link #getDefaultForegroundColor()})
     * @param background the background color, or null to use the default (see {@link #getDefaultBackgroundColor()})
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     *
     * @throws NullPointerException     if the string argument is null
     * @throws IllegalArgumentException if x or y is out of range, or if the specified string extends beyond the right edge of the panel
     *
     * @see #getDefaultForegroundColor()
     * @see #getDefaultBackgroundColor()
     * @see #getWidthInCharacters()
     * @see #getHeightInCharacters()
     */
    public AsciiPanel write(String string, int x, int y, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null." );
        
        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + "." );

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")." );

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")." );

        if (foreground == null)
            foreground = defaultForegroundColor;

        if (background == null)
            background = defaultBackgroundColor;

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foreground, background);
        }
        return this;
    }

    /**
     * Writes a string to the center of the panel at the specified row, using the default foreground and background colors.
     *
     * @param string the string to write (must not be null)
     * @param y the row to write the string in, with 0 being the topmost row
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     * @throws NullPointerException if the string argument is null
     * @throws IllegalArgumentException if the specified string is wider than the panel
     *                                  or if the specified row is outside the panel's height
     */
    public AsciiPanel writeCenter(String string, int y) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + "." );

        int x = (widthInCharacters - string.length()) / 2;

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Writes a string to the center of the panel at the specified y position with the specified foreground color.
     * This method updates the cursor's position but not the default foreground color.
     *
     * @param string the string to write (must not be null)
     * @param y the distance from the top to begin writing from (must be within the range [0, heightInCharacters))
     * @param foreground the foreground color, or null to use the default
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     * @throws NullPointerException if the string argument is null
     * @throws IllegalArgumentException if the specified string is too long to fit within the panel, or if y is out of range
     */
    public AsciiPanel writeCenter(String string, int y, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null" );

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + "." );

        int x = (widthInCharacters - string.length()) / 2;

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );

        return write(string, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Writes a string to the center of the panel at the specified y position with the specified foreground and background colors.
     * This updates the cursor's position but not the default colors.
     *
     * @param string the string to write (must not be null)
     * @param y the distance from the top to begin writing from
     * @param foreground the foreground color, or null to use the default
     * @param background the background color, or null to use the default
     * @return {@code this} AsciiPanel object for convenient chaining of method calls
     * @throws NullPointerException if the string argument is null
     * @throws IllegalArgumentException if the length of the string is greater than the width of the panel,
     * or if the y argument is out of range.
     */
    public AsciiPanel writeCenter(String string, int y, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null." );

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + "." );

        int x = (widthInCharacters - string.length()) / 2;
        
        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")." );

        if (foreground == null)
            foreground = defaultForegroundColor;

        if (background == null)
            background = defaultBackgroundColor;

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foreground, background);
        }
        return this;
    }
    
    public void withEachTile(TileTransformer transformer){
        withEachTile(0, 0, widthInCharacters, heightInCharacters, transformer);
    }
    
    public void withEachTile(int left, int top, int width, int height, TileTransformer transformer){
        for (int x0 = 0; x0 < width; x0++) {
            for (int y0 = 0; y0 < height; y0++) {
                int x = left + x0;
                int y = top + y0;

                if (x < 0 || y < 0 || x >= widthInCharacters || y >= heightInCharacters)
                    continue;

                transformer.transformTile(x, y, characters[x][y]);
            }
        }
    }
}
