import static org.junit.Assert.*;
import org.junit.Test;
import javafx.stage.Stage;

public class BoardGUITest {
	BoardGUI boardGUI;
	
	@Test
	public void setGridElementTest() {
		boardGUI = new BoardGUI();
		/**
		 * Struggling to test GUI because we need to call launch (below)
		 * But, the code will not continue to the next line until the 
		 * window has been closed...
		 */
		boardGUI.launch(BoardGUI.class);
		assertTrue("setGridElement Works!", boardGUI.setGridElement("playerBoard", 7, "Hit"));
	}
}
