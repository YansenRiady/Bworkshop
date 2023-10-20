package BWorkshop;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

public class MainForm {

	public Parent getMainForm() {
		VBox root = new VBox();
		try {
			root.setBackground(new Background(
					new BackgroundImage(new Image(getClass().getResourceAsStream("bg.png")), null, null, null,
							new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true))));

		} catch (Exception er) {
			System.out.println(er);
		}
		MenuList menu = new MenuList();
		root.getChildren().addAll(menu.getMenuList());
		return root;
	}

}
