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
		
		List<RichWord> ltemp = new LinkedList<RichWord>();
		int low = 0;
		int high = inputTextList.size()-1;
		boolean trovato ;
		
		for(String s:inputTextList) {
			
			trovato = false;
			
			while (low<=high) {
				int mid = (low+high)/2;
				if(dictionary.get(mid).equals(s)) {
					trovato = true;
					break;//valore trovato nella posizione mid
		        }
				else if (dictionary.get(mid).compareTo(s)<0) {
					low = mid + 1;
				}
				else {
					high = mid - 1;
				}
			}
			if(!trovato) {
				RichWord rw = new RichWord();
				rw.setCorretta(false);
				rw.setParolaInserita(s);
				ltemp.add(rw);
			}
		}
		return ltemp;
	}
}
