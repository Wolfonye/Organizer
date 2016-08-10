package philipp.tools.organizer.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import philipp.tools.organizer.util.DateUtil;

public class Termin {

	//Instanzvariablen-----------------------------
	private ObjectProperty<LocalDate> datum;					  //fuer die Ausgabe hab ich den datumsString, das hier dient zur internen Verrechnung (noch nciht mplementiert)	
//	private ObjectProperty<LocalTime> startzeit;
//	private ObjectProperty<LocalTime> endzeit;
	private ObservableList<ChecklistItem> checkliste;             //observablearraylist besser???

	private StringProperty ort;
	private StringProperty kurzinfo;
	private StringProperty beschreibung;
	private StringProperty datumsString;
	//---------------------------------------------
	
	
	//TODO: die Verarbeitung der Checkliste fehlt noch v�llig
	
	//Konstruktoren--------------------------------
	
	//bisschen Dummydaten im Basiskonstruktor, indem ich den in jedem anderen Konstruktor aufrufe verhindere ich so ein unh�bsches Exceptionhandling einf�hren zu
	//m�ssen, f�r unvollst�ndige Daten
	public Termin(){
		this.datum = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.datumsString = new SimpleStringProperty(DateUtil.format(datum.get()));
//		this.startzeit = new SimpleObjectProperty<LocalTime>(LocalTime.now());
		this.ort = new SimpleStringProperty("");
		this.kurzinfo = new SimpleStringProperty("");
		this.beschreibung = new SimpleStringProperty("");
		this.checkliste = FXCollections.observableArrayList();
	}
	
	public Termin(LocalDate datum){
		this();                    //Standartkonstruktoraufruf
		this.setDatum(datum);
	}
	
	public Termin(LocalDate datum, LocalTime startzeit){
		this();
		this.setDatum(datum);
//		this.setStartzeit(startzeit);
	}
	
	public Termin (LocalDate datum, String ort){
		this();
		this.setDatum(datum);
		this.setOrt(ort);
	}
	
	public Termin (LocalDate datum, LocalTime startzeit, String ort){
		this();
		this.setDatum(datum);
//		this.setStartzeit(startzeit);
		this.setOrt(ort);
	}
	
	public Termin(LocalDate datum, String ort, String kurzinfo){
		this();
		this.setDatum(datum);
		this.setOrt(ort);
		this.setKurzinfo(kurzinfo);
	}
	
	public Termin(LocalDate datum, LocalTime startzeit, String ort, String kurzinfo){
		this();
		this.setDatum(datum);
//		this.setStartzeit(startzeit);
		this.setOrt(ort);
		this.setKurzinfo(kurzinfo);
	}
	//---------------------------------------------
	
	
	
	//Getter und Setter----------------------------
	public LocalDate getDatum() {
		return datum.get();
	}

	public void setDatum(LocalDate datum) {
		this.datum.set(datum);
		this.datumsString = new SimpleStringProperty(DateUtil.format(datum));   //Vorsicht das ist nicht "datum" von oben sondern der Methodenparameter; die ahben unterschiedliche Typen!
	}
	
//	public ObjectProperty<LocalDate> getDatumsProperty(){
//		return datum;
//	}

	public StringProperty getDatumsStringProperty(){
		return datumsString;
	}
//	public LocalTime getStartzeit() {
//		return startzeit.get();
//	}
//
//	public void setStartzeit(LocalTime startzeit) {
//		this.startzeit.set(startzeit);
//	}
//
//	public LocalTime getEndzeit() {
//		return endzeit.get();
//	}
//
//	public void setEndzeit(LocalTime endzeit) {
//		this.endzeit.set(endzeit);
//	}

	public String getOrt() {
		return ort.get();
	}

	public void setOrt(String ort) {
		this.ort.set(ort);
	}

	public StringProperty getOrtsProperty(){
		return ort;
	}
	
	public String getKurzinfo() {
		return kurzinfo.get();
	}

	public void setKurzinfo(String kurzinfo) {
		this.kurzinfo.set(kurzinfo);
	}

	public StringProperty getKurzinfoProperty(){
		return kurzinfo;
	}
	
	public String getBeschreibung() {
		return beschreibung.get();
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung.set(beschreibung);
	}

	public ObservableList<ChecklistItem> getCheckliste() {
		return checkliste;
	}

	public void setCheckliste(ObservableList<ChecklistItem> checkliste) {
		this.checkliste = checkliste;
	}
	
	public void addChecklistItem(ChecklistItem checklistitem){
		this.checkliste.add(checklistitem);
	}
	//----------------------------------------------

}
