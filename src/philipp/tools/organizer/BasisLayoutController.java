package philipp.tools.organizer;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import philipp.tools.organizer.util.DatenManagementOrganizer;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Marco Jakob, Philipp Bous (modification)
 */

public class BasisLayoutController {
	// Reference to the main application
    private MainOrganizer mainOrganizer;
    private DatenManagementOrganizer dataManagement;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainOrganizer
     */
    public void setMainApp(MainOrganizer mainOrganizer) {
        this.mainOrganizer = mainOrganizer;
    }

    public void setDataManagement(){
    	
    }
    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        dataManagement.getTermine().clear();
        dataManagement.setTerminFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {

    }

    /**
     * Saves the file to the termin file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
       
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
       
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Organizer-App");
        alert.setHeaderText("About");
        alert.setContentText("Author: Philipp Bous, in Anlehnung an Marco Jakob\nWebsite: http://code.makery.ch");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
