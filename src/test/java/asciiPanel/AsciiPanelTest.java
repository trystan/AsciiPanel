package asciiPanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
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
    String superLongString = " ";    
    panel.write(superLongString);
    fail("Should have thrown an IllegalArgumentException.");
  }
}
