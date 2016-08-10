package philipp.tools.organizer;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import philipp.tools.organizer.model.ChecklistItem;
import philipp.tools.organizer.model.Termin;
import philipp.tools.organizer.util.DateUtil;
import philipp.tools.organizer.util.DatenManagementOrganizer;

public class TerminfensterController {
	//erstmal die Sachen, die ich mit meinem FXML-file assoziieren m�chte
	
		@FXML
		private Label datumsLabel;
		@FXML
		private Label ortsLabel;
		@FXML
		private Label kollisionsLabel;
		@FXML
		private TextArea beschreibungsArea;
		@FXML
		private TableView<ChecklistItem> checklistenTable;
		@FXML
		private TableColumn<ChecklistItem, String> checkpointSpalte;
		@FXML
		private TableColumn<ChecklistItem, CheckBox> checkboxSpalte; 
	 	@FXML
	    private TableView<Termin> terminTable;
	    @FXML
	    private TableColumn<Termin, String> datumsSpalte;
	    @FXML
	    private TableColumn<Termin, String> ortsSpalte;
	    @FXML 
	    private TableColumn<Termin, String> kurzinfoSpalte;
	    
	
	private MainOrganizer main;
	private DatenManagementOrganizer dataManagement;
	
	//Konstruktoren----------------------------------------------
	//der wird noch vor dem initialize aufgerufen; das passiert �ber den loader in der showTerminfenster-Methode in der Main-Klasse
	public TerminfensterController(){
		
	}
	//-----------------------------------------------------------
	
	
	//Methoden---------------------------------------------------
	
	//initialize wird automatisch aufgerufen, nachdem das FXML file geladen wurde
	public void initialize(){
		//datumsSpalte.setCellValueFactory(new PropertyValueFactory<Termin,LocalDate>("datum"));  //Dinge in diesem Stil funktionieren auch, aber die �nderungen
		//werden nicht direkt angezeigt; warum auch immer...
		datumsSpalte.setCellValueFactory(cellData -> cellData.getValue().getDatumsStringProperty());
		ortsSpalte.setCellValueFactory(cellData -> cellData.getValue().getOrtsProperty());
		kurzinfoSpalte.setCellValueFactory(cellData -> cellData.getValue().getKurzinfoProperty());
		
		checkpointSpalte.setCellValueFactory(cellData -> cellData.getValue().getCheckpoint());
		checkboxSpalte.setCellValueFactory(cellData -> cellData.getValue().getCheckBoxProperty());
		
		//TODO das workt alles noch cniht wies soll siehe FOrum Trennung GUI und Model
//		checkboxSpalte.setCellValueFactory(cellData -> {
//			boolean value = cellData.getValue().getCheckedProperty();
//			CheckBox check = new CheckBox();
//			check.setSelected(value);
//			return (ObservableValue<CheckBox>) check;
//		});
		
		
		// Clear termin details.
	    showTerminDetails(null);
		
		//wir f�gen einen Listener hinzu, der darauf achtet, ob Ver�nderungen an der Selektion in unserem Table vorgenommen wurden.
		//Das ist ein zeimlcih cleveres Teil. Wir machen das nicht f�r die einzelnen Reihen sondern f�r den Table, bzw. sollte man ersteren Satz pr�ziser
//		formulieren und sagen, dass wir listenen ob an unseren ObservableValues was ge�ndert wurde, in diesem Fall unserer Auswahl. Clever wiederum:
//	    Wir bekommen da direkt den zur Zeile dazugehörigen Termin zurück, sodass wir diese praktische Lambda setzen können. Es wäre jedoch besser noch mehr
//	    Beispiele dahingehend zu machen, wegen des Verständnisses.
//	    Das Table ist ja mit einer ObservableList verbunden, deren
//		Inhalte es darstellt. Siehe die ValueFactories und das setItems in der setMain-Methode.
	    terminTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	    	showTerminDetails(newValue);
	    	checklistenTable.setItems(newValue.getCheckliste());
	    });
	}
	/*Im Moment ist das noch so, dass die Daten solche Beispielteile aus der Main sind. im initialize leg ich fest, was jeweils in welche Spalte soll.
	 * mit setItems zieh ich die tats�chlichen Daten. Das ist ne Liste mit Items (Termine in unserem Fall, in denen es datum ort usw. gibt). Es wird also
	 * von einem TerminObjekt jeweils das passende field beutzt um die Spalte zu bef�llen. Sachen, die aus der gleichen Termininstanz stammen stehen dabei in einer
	 * Zeile. Macht Sinn...*/
	
	public void setMain(MainOrganizer main){
		this.main = main;
	}
	
	
	public void setDataManagement(DatenManagementOrganizer dataManagement){
		this.dataManagement= dataManagement;
		terminTable.setItems(dataManagement.getTermine());
	}
	
	/**
	 * Methode zum Bef�llen der Labels und sonstiger Dinge mit den Termindaten und clean der Felder, wenn der Termin Null ist.
	 * @param termin ein Termin oder Null
	 */
	private void showTerminDetails(Termin termin) {
	    if (termin != null) {
	        // bef�llen der Elemente mit infos des Terminobjektes.
	        datumsLabel.setText(DateUtil.format(termin.getDatum()));
	        ortsLabel.setText(termin.getOrt());
//	        kollisionsLabel.setText(termin.getKollisionen());
	        beschreibungsArea.setText(termin.getBeschreibung());	  
	    } else {
	        // Termin is null, remove all the text.
	    	datumsLabel.setText("");
	    	ortsLabel.setText("");
	    	beschreibungsArea.setText("");	        
	    }
	}
	
	/*Als n�chstes folgen Methoden, die regeln sollen, was beim clicken der Buttons so geschehen soll*/
	
	//F�r den Anlegen-Button; wenn im Zuge der Sache OK geklickt wurde (das erhalten wir als Resultat der showTerminEditDialog-Methode aus Main..) f�gen wir den Termin
	//unserer Datenliste hinzu, sonst halt nicht.
	@FXML
	private void handleAnlegenTermin(){
		Termin tempTermin = new Termin();
        boolean okClicked = main.showTerminEditDialog(tempTermin);
        if (okClicked) {
            dataManagement.getTermine().add(tempTermin);
        }
	}
	
	//F�r den Editieren-Button
	// Hier wird halt kein neuer Termin zeitweise angelegt, sondern der angeglegte "geholt" und daf�r EditDialog aufgerufen...clever.
	@FXML
	private void handleEditierenTermin() {
	    Termin selectedTermin = terminTable.getSelectionModel().getSelectedItem();
	    if (selectedTermin != null) {
	        boolean okClicked = main.showTerminEditDialog(selectedTermin);
	        if (okClicked) {
	            showTerminDetails(selectedTermin);
	        }

	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(main.getPrimaryStage());
	        alert.setTitle("Nichts selektiert!");
	        alert.setHeaderText("Es wurde kein Termin selektiert.");
	        alert.setContentText("Bitte einen Termin in der Tabelle ausw�hlen.");

	        alert.showAndWait();
	    }
	}
	/*
	 * F�r den L�schen Butten
	 * TODO: verbesserung: button soll nicht aktiv sein, wenn nichts selektiert ist
	 */
	@FXML
	private void handleDeleteTermin() {
	    int selectedIndex = terminTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	        terminTable.getItems().remove(selectedIndex);
	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(main.getPrimaryStage());
	        alert.setTitle("Warnung");
	        alert.setHeaderText("Kein Termin selektiert oder verf�gbar!");
	        alert.setContentText("Bitte Eintrag in der Tabelle ausw�hlen.");

	        alert.showAndWait();
	    }
	}
	

	//-----------------------------------------------------------
}
