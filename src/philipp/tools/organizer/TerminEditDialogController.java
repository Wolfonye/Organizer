package philipp.tools.organizer;


import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import philipp.tools.organizer.model.ChecklistItem;
import philipp.tools.organizer.model.Termin;
import philipp.tools.organizer.util.DateUtil;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TerminEditDialogController {
	@FXML
	private TextField datumsTextField;
	@FXML
	private TextField ortsTextField;
	@FXML
	private TextField kurzinfoTextField;
	@FXML
	private TextArea beschreibungsTextArea;
	@FXML 
	private TextField checkpointTextField;
	
	
	private Stage dialogStage;
    private Termin termin;
    private boolean okClicked = false;
	
	
	
	//TODO Methoden implementieren
	//Methoden fuer die Buttons------------------------------------------------------
	@FXML
    public void handleOk(){
		if (isInputValid()) {
            this.termin.setOrt(ortsTextField.getText());
            this.termin.setKurzinfo(kurzinfoTextField.getText());
            this.termin.setDatum(DateUtil.parse(datumsTextField.getText()));
            this.termin.setBeschreibung(beschreibungsTextArea.getText());
            
            okClicked = true;
            this.dialogStage.close();
        }
	}
	@FXML
	public void handleCheckpoint(){
		if(checkpointTextField.getText().length()!=0){
			ChecklistItem tempChecklistItem = new ChecklistItem(new SimpleStringProperty(checkpointTextField.getText()));
			this.termin.addChecklistItem(tempChecklistItem);
		}
	}
	
	@FXML
	public void handleAbbrechen(){
		dialogStage.close();
	}
	//------------------------------------------------------------------------------
	
	//allgemeine Methoden-----------------------------------------------------------
	
	/*
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /*
     * Sets the stage of this dialog.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	
    /*
     * Termindaten setzten
     */
    public void setTermin(Termin termin) {
        this.termin = termin;

        ortsTextField.setText(termin.getOrt());
        kurzinfoTextField.setText(termin.getKurzinfo());
        beschreibungsTextArea.setText(termin.getBeschreibung());
        datumsTextField.setText(DateUtil.format(termin.getDatum()));
        datumsTextField.setPromptText("dd.mm.yyyy");
    }
    
    
    /*
     * Gibt true zurueck, wenn der User OK geklickt hat, ansonsten false
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /*
     * Validierung des Inputs und entsprechende Ma�nahmen
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (ortsTextField.getText() == null || ortsTextField.getText().length() == 0) {
            errorMessage += "Kein Ort angegeben!\n"; 
        }
        if (kurzinfoTextField.getText() == null || kurzinfoTextField.getText().length() == 0) {
            errorMessage += "Keine Kurzinfo angegeben!\n"; 
        }
        if (datumsTextField.getText() == null || datumsTextField.getText().length() == 0) {
            errorMessage += "Kein g�ltiges Datum!\n";
        } else {
            if (!DateUtil.validDate(datumsTextField.getText())) {
                errorMessage += "Kein g�ltiges Datum. Verwenden Sie das Fortmat dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ung�ltige Felder!");
            alert.setHeaderText("Bitte korrigieren!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
	//--------------------------------------------------------------
}
