package Charmie.Javafx;

import Charmie.Charmie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Charmie charmie;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image charmieImage = new Image(this.getClass().getResourceAsStream("/images/charmie.jpg"));

    @FXML
    public void initialize() {

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Charmie instance */
    public void setCharmie(Charmie charmie) {
        this.charmie = charmie;

        String welcome = charmie.getWelcomeMessage();
        dialogContainer.getChildren()
                .add(DialogBox.getDukeDialog(welcome, charmieImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = charmie.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, charmieImage)
        );
        userInput.clear();
    }
}


