package rbtree;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RBTAnimation<E extends Comparable<E>> extends Application {
	
	public static void main(String[] args) {
		Application.launch();
	}
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		VBox header = new VBox();
		HBox center = new HBox();
		HBox footer = new HBox();
		VBox vbox = new VBox(); // Holds the header HBox
		
		/* ***************** MENU *****************/
		Button btnInteger = new Button("Integers");
		Button btnCharacter = new Button("Characters");
		Button btnString = new Button("Strings"); 
		Label menuTitle = new Label("Red-Black Tree GUI");
		Label menuText = new Label("Create a Red-Black Tree of :");
		
		center.getChildren().addAll(btnInteger, btnCharacter, btnString);
		vbox.getChildren().addAll(menuTitle, menuText, center);
		pane.setCenter(vbox);
		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("rbtree/style.css");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setMaximized(true);
		
		/* ************ RED-BLACK TREE GUI ************/
		
		// Header
		Label title = new Label();
		Label status = new Label("The tree is empty");
		header.getChildren().addAll(title, status);
		
		// Footer
		Label enterText = new Label("Enter a key ");
		TextField textField = new TextField();
		textField.setPrefColumnCount(3);
		textField.setAlignment(Pos.BASELINE_RIGHT);
		Button btnInsert = new Button("Insert");
		Button btnDelete = new Button("Delete");
		Button btnReturn = new Button("Return to Menu");
		footer.getChildren().addAll(enterText, textField, btnInsert, btnDelete, btnReturn);
		footer.setAlignment(Pos.CENTER);
		
		// Return to menu button
		btnReturn.setOnAction(e -> {
			pane.setTop(null);
			pane.setCenter(vbox);
			pane.setBottom(null);
		});
		
		// Integer button
		btnInteger.setOnAction(e -> {
			title.setText("Red-Black Tree of Integers");
			RedBlackTree<Integer> tree = new RedBlackTree<>();
			RBTView<Integer> view = new RBTView<>(tree);
			view.getStyleClass().add("view");
			pane.setTop(header);
			pane.setCenter(view);
			pane.setBottom(footer);
			btnInsert.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					try {
						int key = Integer.parseInt(textField.getText());
						if (tree.isEmpty()) {
							tree.insert(key);
							view.displayTree();
							status.setText(key + " is inserted into the tree");
						}
						else if (tree.find(key)) {
							view.displayTree();
							status.setText(key + " is already in the tree");
						}
						else {
							tree.insert(key);
							view.displayTree();
							status.setText(key + " is inserted into the tree");
						}
					} catch (NumberFormatException e) {
						status.setText("You must enter an integer");
					}
				}
			});
			btnDelete.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					try {
						int key = Integer.parseInt(textField.getText());
						if (tree.isEmpty()) {
							view.displayTree();
							status.setText("The tree is empty");
						}
						else if (!tree.find(key)) {
							view.displayTree();
							status.setText(key + " is not in the tree");
						}
						else {
							tree.delete(key);
							view.displayTree();
							status.setText(key + " is deleted from the tree");
						}
					} catch (NumberFormatException e) {
						status.setText("You must enter an integer");
					}
				}
			});
		});
		
		// Character button
		btnCharacter.setOnAction(e -> {
			title.setText("Red-Black Tree of Characters");
			RedBlackTree<Character> tree = new RedBlackTree<>();
			RBTView<Character> view = new RBTView<>(tree);
			view.getStyleClass().add("view");
			pane.setTop(header);
			pane.setCenter(view);
			pane.setBottom(footer);
			btnInsert.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					if (textField.getText().length() != 1) {
						status.setText("You must enter a character");
					}
					else {
						char key = textField.getText().charAt(0);
						if (tree.isEmpty()) {
							tree.insert(key);
							view.displayTree();
							status.setText(key + " is inserted into the tree");
						}
						else if (tree.find(key)) {
							view.displayTree();
							status.setText(key + " is already in the tree");
						}
						else {
							tree.insert(key);
							view.displayTree();
							status.setText(key + " is inserted into the tree");
						}
					}
				}
			});
			btnDelete.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					if (textField.getText().length() != 1) {
						status.setText("You must enter a character");
					}
					else {
						char key = textField.getText().charAt(0);
						if (tree.isEmpty()) {
							view.displayTree();
							status.setText("The tree is empty");
						}
						else if (!tree.find(key)) {
							view.displayTree();
							status.setText(key + " is not in the tree");
						}
						else {
							tree.delete(key);
							view.displayTree();
							status.setText(key + " is deleted from the tree");
						}
					}
				}
			});
		});		
		
		// String button
		btnString.setOnAction(e -> {
			title.setText("Red-Black Tree of Strings");
			RedBlackTree<String> tree = new RedBlackTree<>();
			RBTView<String> view = new RBTView<>(tree);
			view.getStyleClass().add("view");
			pane.setTop(header);
			pane.setCenter(view);
			pane.setBottom(footer);
			btnInsert.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					String key = textField.getText();
					if (tree.isEmpty()) {
						tree.insert(key);
						view.displayTree();
						status.setText(key + " is inserted into the tree");
					}
					else if (tree.find(key)) {
						view.displayTree();
						status.setText(key + " is already in the tree");
					}
					else {
						tree.insert(key);
						view.displayTree();
						status.setText(key + " is inserted into the tree");
					}
				}
			});
			btnDelete.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					String key = textField.getText();
					if (tree.isEmpty()) {
						view.displayTree();
						status.setText("The tree is empty");
					}
					else if (!tree.find(key)) {
						view.displayTree();
						status.setText(key + " is not in the tree");
					}
					else {
						tree.delete(key);
						view.displayTree();
						status.setText(key + " is deleted from the tree");
					}
				}
			});
		});		
		
		// Menu styling
		vbox.getStyleClass().add("vbox");
		center.getStyleClass().add("center");
		menuTitle.getStyleClass().add("title");
		title.getStyleClass().add("title");
		menuText.getStyleClass().add("menu-text");
		btnInteger.getStyleClass().add("button");
		btnCharacter.getStyleClass().add("button");
		btnString.getStyleClass().add("button");
		pane.getStyleClass().add("pane");
		
		// Red-Black Tree styling
		status.getStyleClass().add("status");
		header.getStyleClass().add("header");
		enterText.getStyleClass().add("enter-text");
	}
}