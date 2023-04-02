package net.trystan.asciipanel;

import java.awt.*;

/**
 * The AsciiCharacterData class represents a single character in the ASCII character set. It contains the character's
 * glyph (image) data, as well as its foreground and background colors. This class is used by the AsciiPanel class to
 * render text on the console.
 */
public class AsciiCharacterData {

    /**
     * The character represented by this AsciiCharacterData object.
     */
    public char character;
    /**
     * The foreground color of this AsciiCharacterData object.
     */
    public Color foregroundColor;
    /**
     * The background color of this AsciiCharacterData object.
     */
    public Color backgroundColor;

    /**
     * Constructs a new AsciiCharacterData object with default values for all properties.
     */
    public AsciiCharacterData() {
    }

    /**
     * Creates a new AsciiCharacterData object with the specified character, foreground color, and background color.
     *
     * @param character       the ASCII character to display
     * @param foregroundColor the color to use for the character's foreground
     * @param backgroundColor the color to use for the character's background
     */
    public AsciiCharacterData(char character, Color foregroundColor, Color backgroundColor) {
        this.character = character;
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
    }
}
