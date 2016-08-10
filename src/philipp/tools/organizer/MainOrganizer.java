package philipp.tools.organizer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import philipp.tools.organizer.model.Termin;
import philipp.tools.organizer.util.DatenManagementOrganizer;

public class MainOrganizer extends Application {

	private Stage primaryStage;
    private BorderPane basisLayout;   
    
    //private ObservableList<Termin> terminData = FXCollections.observableArrayList();
    private DatenManagementOrganizer dataManagement;
    
    //Konstruktor (wenigstens einer mit bissl Testdaten fuer den Anfang)---------------
    public MainOrganizer(){
    	dataManagement = new DatenManagementOrganizer(this);
    	dataManagement.addTermin(new Termin(LocalDate.of(2016,5,6),"Siebenburg","nix"));
    	dataManagement.addTermin(new Termin(LocalDate.of(2016,4,20),"Narnia","Sachen machen"));
    	
    }
    //--------------------------------------------------------------------------------
    
    
	//Methoden; erst bissl was an Basis-Faehigkeiten, dann GUI-bezogenes---------------------
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Organizer");

        initBasisLayout();
     
        // Set the application icon. Fancy...
        this.primaryStage.getIcons().add(new Image("file:resources/images/iconOrganizer.png"));
        
        showTerminfenster();
	}
	
//	public ObservableList<Termin> getTermine(){
//		return terminData;
//	}
	
    /*
     * Gibt die primaryStage zurueck, das brauchen wir zum Beispiel innerhalb des TerminfensterControllers
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    
    
    /*
     * Oeffnet den Dialog um die Termindetails zu aendern(neu anzulegen). Wenn der User OK klickt
     * werden die Aenderungen in das zur Verf�gung gestellte Terminobjekt gespeichert und true 
     * zurueckgegeben.
     * @param termin Das zu editierende Termin-Objekt. 
     * @return true wenn ok geklickt wurde, sonst false.
     */
    public boolean showTerminEditDialog(Termin termin) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainOrganizer.class.getResource("view/TerminEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Termin anlegen/editieren");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the termin into the controller.
            TerminEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTermin(termin);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
	public void initBasisLayout() {
	    try {
	    	// Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainOrganizer.class.getResource("view/BasisLayout.fxml"));
	        basisLayout = (BorderPane) loader.load();   //damit das workt muss vorher die Location gesettet worden sein, von wo er das loaden soll

	        // Show the scene containing the root layout.
	        Scene scene = new Scene(basisLayout);
	        primaryStage.setScene(scene);       //wenn ich das wegkommentieren wuerde, haette ich einfach nur ein "leeres" Fenster
	        
	        // Give the controller access to the main.
	        BasisLayoutController controller = loader.getController();
	        controller.setMainApp(this);
	        
	        primaryStage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    
	}
	 
	public void showTerminfenster() {
		try {
			// Load termin overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainOrganizer.class.getResource("view/Terminfenster.fxml"));
			AnchorPane terminOverview = (AnchorPane) loader.load();

			// Set termin overview into the center of root layout.
			basisLayout.setCenter(terminOverview);

			// Give the controller access to the main app.
			TerminfensterController controller = loader.getController();   //bzgl. getController() look in die Doc und DANN sieh mal in Termin
	                                                   //-Overview rein, da steht, welches der zugehoerige Controller sein soll. Hier wird also der Controller zu der
	             									   //Ressource geladen, die wir vorhin fuer "loader" gesetzt haben mit setLocation...tricky, aber sinnig
			controller.setMain(this);
			controller.setDataManagement(dataManagement);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
