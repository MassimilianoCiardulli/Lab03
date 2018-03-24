package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {
	List<String> dictionary ;
	
	public Dictionary() {
		dictionary = new ArrayList<String>() ;
	}

	/**
	 * Inserisce nel sistema il dizionario da utilizzare
	 * @param "English" oppure "Italian"
	 */
	public void loadDictionary(String language) {
		
		if(language.equals("English")) {
			
			try {
				BufferedReader br = new BufferedReader(new FileReader("rsc/English.txt"));
				String word ;
				while((word = br.readLine())!=null) {
					dictionary.add(word);
				}
				br.close();
			} catch(IOException e) {
				
			}
			
		}
		
		if(language.equals("Italian")) {
			try {
				BufferedReader br = new BufferedReader(new FileReader("rsc/Italian.txt"));
				String word ;
				while((word = br.readLine())!=null) {
					dictionary.add(word);
				}
				br.close();
			} catch(IOException e) {
				
			}
		}
	}
	
	/**
	 * Legge la stringa da analizzare e la spezza in vari token
	 * @param stringa da analizzare
	 * @return LinkedList di parole inserite
	 */
	public List<String> input(String s){
		StringTokenizer st = new StringTokenizer(s, " ");
		List<String> inputTextList = new LinkedList<String>();
		while(st.hasMoreTokens()) {
			inputTextList.add(st.nextToken().replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", ""));
		}
		
		return inputTextList;
	}
	
	/**
	 * Controlla le parole inserite tramite il metodo contains()
	 * @param inputTextList
	 * @return LinkedList di RichWord errate
	 */
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List<RichWord> ltemp = new LinkedList<RichWord>() ;
		
		/*Analizzo la lista di String
		 Se una parola non è presente nel dizionario la aggiungo nella lista di RichWord*/
		if(dictionary.isEmpty())
			this.loadDictionary("English");
		
		for(String s:inputTextList) {
			if(!dictionary.contains(s.toLowerCase().trim())) {
				RichWord rw = new RichWord();
				rw.setCorretta(false);
				rw.setParolaInserita(s);
				ltemp.add(rw);
			}
		}
		
		return ltemp;
	}
	
	/**
	 * Controlla le parole inserite con una ricerca lineare
	 * @param inputTextList
	 * @return LinkedList di RichWord errate
	 */
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		if(dictionary.isEmpty())
			this.loadDictionary("English");
		
		List<RichWord> ltemp = new LinkedList<RichWord>() ;
		boolean trovato ;
		
		for(String s:inputTextList) {
			trovato = false ;
			
			for(String d:dictionary) {
				if(s.toLowerCase().trim().equals(d.toLowerCase())) {
					
					trovato = true;
				}
			}
			
			if(trovato == false){
				RichWord rw = new RichWord();
				rw.setCorretta(false);
				rw.setParolaInserita(s);
				ltemp.add(rw);
				break;
			}
		}
		return ltemp;
	}
	
	/**
	 * Controlla le parole inserite con una ricerca dicotomica
	 * @param inputTextList
	 * @return LinkedList di RichWord errate
	 */
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		if(dictionary.isEmpty())
			this.loadDictionary("English");
		
		int pos = dictionary.size()/2;
		List<RichWord> ltemp = new LinkedList<RichWord>() ;
		boolean trovato ;
		int count = 0;
		
		for(String s:inputTextList) {
			trovato = false;
			do {
				if(pos <= dictionary.size() && pos >= 0 && s.toLowerCase().trim().compareTo(dictionary.get(pos).toLowerCase())==0) {
					//Ho trovato la parola
					trovato = true;
				}
				
				if(pos <= dictionary.size() && pos >= 0 && s.toLowerCase().trim().compareTo(dictionary.get(pos).toLowerCase())<0) {
					//Viene prima
					pos -= (inputTextList.size()-pos)/2;
					count++;
					
				}
				
				if(pos <= dictionary.size() && pos >= 0 && s.toLowerCase().trim().compareTo(dictionary.get(pos).toLowerCase())>0) {
					//viene dopo
					pos += (inputTextList.size()-pos)/2;
					count++;
				}
				
				if(pos > dictionary.size() || pos < 0)
					break;
				
			} while( !trovato || count==inputTextList.size()/2 );
			
			if(trovato==false) {
				RichWord rw = new RichWord();
				rw.setCorretta(false);
				rw.setParolaInserita(s);
				ltemp.add(rw);
			}
		}
		return ltemp;
	}
}
