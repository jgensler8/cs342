import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class LevelButton extends JButton {
	
	LevelButton(int nLevels) {
		setText("Load Level");
		
		JPopupMenu levelMenu = new JPopupMenu();
		addPopup(this, levelMenu);

		for(int i = 0; i < nLevels; ++i) {
			JMenuItem level = new JMenuItem(Integer.toString(i));
			levelMenu.add(level);
		}
	}
	
	// TODO: Load levels based on the option clicked
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
					showMenu(e);
			}
			public void mouseReleased(MouseEvent e) {
					showMenu(e);
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
