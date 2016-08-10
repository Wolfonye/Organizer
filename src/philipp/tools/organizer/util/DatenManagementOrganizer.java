package philipp.tools.organizer.util;

import java.io.File;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import philipp.tools.organizer.MainOrganizer;
import philipp.tools.organizer.model.Termin;

public class DatenManagementOrganizer {
	
	private MainOrganizer main;
	private ObservableList<Termin> terminData = FXCollections.observableArrayList();	
	
	public DatenManagementOrganizer(MainOrganizer main){
		setMain(main);
	}
	
	
	public void setMain(MainOrganizer main){
		this.main = main;
	}
	
	public ObservableList<Termin> getTermine(){
		return terminData;
	}
	
	public void addTermin(Termin termin){
		terminData.add(termin);
	}
	
	void ladeTermineAusDatei(File datei) {
	    try {
	        
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + datei.getPath());

	        alert.showAndWait();
	    }
	}

	public void speichereTermineInDatei(File datei) {
	    try {
	    	
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + datei.getPath());

	        alert.showAndWait();
	    }
	}
	
	/**
	 * Returns the termin file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getTerminFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainOrganizer.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setTerminFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainOrganizer.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        //TODO primaryStage.setTitle("Organizer - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        //TODO primaryStage.setTitle("Organizer");
	    }
	}
	
}
