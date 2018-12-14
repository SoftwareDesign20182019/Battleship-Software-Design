import static org.junit.Assert.*;
import org.junit.Test;
import javafx.stage.Stage;

public class BoardGUITest {
	BoardGUI boardGUI;
	
	@Test
	public void setGridElementTest() {
		boardGUI = new BoardGUI();	
		boardGUI.start(new Stage());
		assertTrue("setGridElement Works!", boardGUI.setGridElement("playerBoard", 7, "Hit"));
	}
}
