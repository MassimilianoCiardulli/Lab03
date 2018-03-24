package it.polito.tdp.spellchecker.model;

public class RichWord {
	String parolaInserita ;
	boolean corretta ;
	
	public RichWord() {
		
	}

	public String getParolaInserita() {
		return parolaInserita;
	}

	public void setParolaInserita(String parolaInserita) {
		this.parolaInserita = parolaInserita;
	}

	public boolean isCorretta() {
		return corretta;
	}

	public void setCorretta(boolean corretta) {
		this.corretta = corretta;
	}
	
}
