package charmie.javafx;

import charmie.Charmie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

/**
 * Controller for the main GUI window.
 *
 * MainWindow manages the JavaFX interface for the Charmie application, handling
 * user input and displaying dialog exchanges between the user and Charmie.
 */
public class MainWindow extends AnchorPane {
    /** Scroll pane that contains the dialog messages and allows scrolling. */
    @FXML
    private ScrollPane scrollPane;

    /** VBox container that holds all dialog boxes (user and Charmie messages). */
    @FXML
    private VBox dialogContainer;

    /** TextField for user input. */
    @FXML
    private TextField userInput;

    /** Button that sends user input to Charmie. */
    @FXML
    private Button sendButton;

    private Charmie charmie;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image charmieImage = new Image(this.getClass().getResourceAsStream("/images/charmie.jpg"));

    /**
     * Initializes the main window layout.
     *
     * Binds the scroll pane's vertical scroll position to the dialog container height
     * to automatically scroll to the latest message.
     */
    @FXML
    public void initialize() {

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Charmie instance and displays the welcome message.
     *
     * @param charmie the Charmie application instance
     */
    public void setCharmie(Charmie charmie) {
        this.charmie = charmie;

        String welcome = charmie.getWelcomeMessage();
        dialogContainer.getChildren()
                .add(DialogBox.getDukeDialog(welcome, charmieImage));
    }

    /**
     * Handles user input and displays the response.
     *
     * Processes the user's input, retrieves Charmie's response, displays both messages
     * in the dialog container, and clears the input field.
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

        if (charmie.isExit()) {
            Platform.exit();
        }
    }
}


