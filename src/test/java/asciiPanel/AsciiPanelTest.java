package asciiPanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.PanelUI;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith( PowerMockRunner.class )
@PrepareForTest({ UIManager.class, ImageIO.class })
@PowerMockIgnore("javax.swing.*")
public class AsciiPanelTest {

  @Before
  public void initMocks() throws IOException {
    PowerMockito.mockStatic(UIManager.class);
    PowerMockito.mockStatic(ImageIO.class);

    BufferedImage mockImage = mock(BufferedImage.class);
    PanelUI mockPanelUi = mock(PanelUI.class);

    when(UIManager.getUI(any(JComponent.class)))
      .thenReturn(mockPanelUi);

    when(ImageIO.read(any(File.class)))
      .thenReturn(mockImage);
  }

  @Test( expected = IllegalArgumentException.class )
  public void testSetNegativeCursorX() {
    AsciiPanel panel = new AsciiPanel();
    panel.setCursorX(-1);

    fail("Should have thrown an IllegalArgumentException.");
  }

  @Test( expected = IllegalArgumentException.class )
  public void testSetCursorXOutsideOfMax() {
    AsciiPanel panel = new AsciiPanel();
    panel.setCursorX(Integer.MAX_VALUE);

    fail("Should have thrown an IllegalArgumentException.");
  }

  @Test( expected = IllegalArgumentException.class )
  public void testSetNegativeCursorY() {
    AsciiPanel panel = new AsciiPanel();
    panel.setCursorY(-1);

    fail("Should have thrown an IllegalArgumentException.");
  }

  @Test( expected = IllegalArgumentException.class )
  public void testSetCursorYOutsideOfMax() {
    AsciiPanel panel = new AsciiPanel();
    panel.setCursorY(Integer.MAX_VALUE);

    fail("Should have thrown an IllegalArgumentException.");
  }

  @Test
  public void testSetCursorX() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.setCursorX(0);
  }

  @Test
  public void testSetCursorY() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.setCursorY(0);
  }

  @Test( expected = NullPointerException.class )
  public void testSetNullDefaultBackgroundColor() {
    AsciiPanel panel = new AsciiPanel();
    panel.setDefaultBackgroundColor(null);

    fail("Should have thrown a NullPointerException.");
  }

  @Test( expected = NullPointerException.class )
  public void testSetNullDefaultForegroundColor() {
    AsciiPanel panel = new AsciiPanel();
    panel.setDefaultForegroundColor(null);

    fail("Should have thrown a NullPointerException.");
  }

  @Test( expected = IllegalArgumentException.class )
  public void testConstructorZeroWidth() {
    new AsciiPanel(0, 24);

    fail("Should have thrown an IllegalArgumentException.");
  }

  @Test( expected = IllegalArgumentException.class )
  public void testConstructorZeroHeight() {
    new AsciiPanel(80, 0);

    fail("Should have thrown an IllegalArgumentException.");
  }

  @Test( expected = NullPointerException.class )
  public void testWriteNullFail() {
    AsciiPanel panel = new AsciiPanel(80, 1);
    panel.write(null);
    fail("Should have thrown a NullPointerException.");
  }

  @Test( expected = IllegalArgumentException.class )
  public void testWriteInvalidLengthFail() {
    AsciiPanel panel = new AsciiPanel(80, 1);
    String superLongString = String.format("%0100d", 1);
    panel.write(superLongString);
    fail("Should have thrown an IllegalArgumentException.");
  }

  @Test
  public void testWriteChar() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(' ');
  }

  @Test
  public void testWriteCharFG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(' ', AsciiPanel.white);
  }

  @Test
  public void testWriteCharFGBG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(' ', AsciiPanel.white, AsciiPanel.black);
  }

  @Test
  public void testWriteCharXY() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(' ', 0, 0);
  }

  @Test
  public void testWriteCharXYFG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(' ', 0, 0, AsciiPanel.white);
  }

  @Test
  public void testWriteCharXYFGBG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(' ', 0, 0, AsciiPanel.white, AsciiPanel.black);
  }

  @Test
  public void testWriteString() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(" ");
  }

  @Test
  public void testWriteStringFG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(" ", AsciiPanel.white);
  }

  @Test
  public void testWriteStringFGBG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(" ", AsciiPanel.white, AsciiPanel.black);
  }

  @Test
  public void testWriteStringXY() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(" ", 0, 0);
  }

  @Test
  public void testWriteStringXYFG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(" ", 0, 0, AsciiPanel.white);
  }

  @Test
  public void testWriteStringXYFGBG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.write(" ", 0, 0, AsciiPanel.white, AsciiPanel.black);
  }

  @Test
  public void testWriteCenter() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.writeCenter(" ", 0);
  }

  @Test
  public void testWriteCenterFG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.writeCenter(" ", 0, AsciiPanel.white);
  }

  @Test
  public void testWriteCenterFGBG() {
    AsciiPanel panel = new AsciiPanel(1, 1);
    panel.writeCenter(" ", 0, AsciiPanel.white, AsciiPanel.black);
  }

  @Test
  public void testSetAsciiFont()
  {
    AsciiPanel panel = new AsciiPanel(1, 1, AsciiFont.CP437_9x16);
    Dimension oldDimensions = panel.getPreferredSize();

    panel.setAsciiFont(AsciiFont.TALRYTH_15_15);
    Dimension newDimensions = panel.getPreferredSize();

    assertNotEquals(oldDimensions, newDimensions);
  }

  @Test
  public void testWrite() {
    int width = 100;
    int height = 100;
    AsciiPanel panel = new AsciiPanel(width, height);

    // write out characters in a specific pattern such that we can verify each is written correctly to the specified
    // position
    Color oddColumnForeground = new Color(255, 255, 255);
    Color evenColumnForeground = new Color(0, 0, 0);
    Color oddRowBackground = new Color(255, 255, 0);
    Color evenRowBackground = new Color(0, 255, 255);

    AsciiCharacterData[][] expectedCharacterData = new AsciiCharacterData[width][height];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < width; y++) {
        AsciiCharacterData characterData = new AsciiCharacterData();
        characterData.character = (char)(x + y);
        if (x % 2 == 0) {
          characterData.foregroundColor = evenColumnForeground;
        } else {
          characterData.foregroundColor = oddColumnForeground;
        }

        if (y % 2 == 0) {
          characterData.backgroundColor = evenRowBackground;
        } else {
          characterData.backgroundColor = oddRowBackground;
        }
        panel.write(characterData, x, y);
        expectedCharacterData[x][y] = characterData;
      }
    }

    // now validate that it was written as expected
    AsciiCharacterData[][] writtenData = panel.getCharacters();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        AsciiCharacterData expectedCharData = expectedCharacterData[x][y];
        AsciiCharacterData writtenCharData = writtenData[x][y];
        assertEquals(expectedCharData.character, writtenCharData.character);
        assertEquals(expectedCharData.foregroundColor, writtenCharData.foregroundColor);
        assertEquals(expectedCharData.backgroundColor, writtenCharData.backgroundColor);
      }
    }
  }
}
