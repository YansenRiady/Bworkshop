package Model;

import javafx.scene.Parent;

public class Root {
	public String root;
	public Parent parent;
	public boolean isPublic;

	public Root(String root, Parent parent, boolean isPublic) {
		this.root = root;
		this.parent = parent;
		this.isPublic = isPublic;
	}
}
