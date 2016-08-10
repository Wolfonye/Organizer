package philipp.tools.organizer.model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

public class ChecklistItem {
	private StringProperty checkpoint;
	private SimpleBooleanProperty checked;
	private ObjectProperty<CheckBox> checkbox;
	
	//Konstruktoren----------------------------------------
	public ChecklistItem(StringProperty checkpoint) {
		this.checkpoint = checkpoint;
		this.checked = new SimpleBooleanProperty(false);
		this.checkbox = new SimpleObjectProperty<CheckBox>(new CheckBox());
		this.checkbox.get().setSelected(false);
	}
	//-----------------------------------------------------


	//Methoden---------------------------------------------
	public StringProperty getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(StringProperty checkpoint) {
		this.checkpoint = checkpoint;
	}
	
	public void setChecked(boolean value){
		this.checked.setValue(value);
	}
	
	public boolean getCheckedProperty(){
		return this.checked.getValue();
	}
	
	public SimpleBooleanProperty getChecked(){
		return checked;
	}
	
	public ObjectProperty<CheckBox> getCheckBoxProperty(){
		return checkbox;
	}
	
	public CheckBox getCheckBox(){
		return checkbox.get();
	}
	//-----------------------------------------------------
	
}
