/**
 * Sample Skeleton for 'SpellChecker.fxml' Controller Class
 */

package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.*;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SpellCheckerController {

	private Dictionary model ;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboBox"
    private ComboBox<String> comboBox; // Value injected by FXMLLoader

    @FXML // fx:id="btnSpellCheck"
    private Button btnSpellCheck; // Value injected by FXMLLoader

    @FXML // fx:id="btnClearText"
    private Button btnClearText; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtField"
    private TextField txtField; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtArea"
    private TextArea txtArea; // Value injected by FXMLLoader
   

    @FXML
    void doChoiceLang(ActionEvent event) {
    	String lang = comboBox.getValue();
    	model.loadDictionary(lang);
    }
    

    @FXML
    void doClearText(ActionEvent event) {

    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	String stemp = txtField.getText();
    	List <String> ltemp = model.input(stemp);
    	List<RichWord> lrw = this.model.spellCheckTextDichotomic(ltemp);
    	this.aggiornaTxtArea(lrw);
    }
    
    public void aggiornaTxtArea(List<RichWord> ltemp) {
    	
    	for(RichWord rw:ltemp)
    		txtArea.appendText(rw.getParolaInserita() + "\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        comboBox.setItems(FXCollections.observableArrayList("English","Italian"));
    }

	public void setModel(Dictionary model) {
		// TODO Auto-generated method stub
		this.model = model;
	}
}

