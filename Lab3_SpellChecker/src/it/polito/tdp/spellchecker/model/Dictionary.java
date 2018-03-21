package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {
	List<String> dictionary ;
	
	public Dictionary() {
		dictionary = new ArrayList<String>() ;
	}

	
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
	
	public List<String> input(String s){
		StringTokenizer st = new StringTokenizer(s, " ");
		List<String> inputTextList = new LinkedList<String>();
		while(st.hasMoreTokens()) {
			inputTextList.add(st.nextToken().replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", ""));
		}
		
		return inputTextList;
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List<RichWord> ltemp = new LinkedList<RichWord>() ;
		
		/*Analizzo la lista di String
		 Se una parola non è presente nel dizionario la aggiungo nella lista di RichWord*/
		
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
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		List<RichWord> ltemp = new LinkedList<RichWord>() ;
		boolean trovato = false;
		String parola = "";
		
		for(String s:inputTextList) {
			parola = s;
			for(String d:dictionary) 
				if(s.toLowerCase().trim().equals(d)) {
					trovato = true;
					break;
				}
			if(trovato == true)
				break;
		}
			
		if(trovato == false) {
			RichWord rw = new RichWord();
			rw.setCorretta(false);
			rw.setParolaInserita(parola);
			ltemp.add(rw);
		}
		return ltemp;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		int pos = dictionary.size()/2;
		List<RichWord> ltemp = new LinkedList<RichWord>() ;
		boolean trovato = false;
		
		for(String s:inputTextList) {
			do {
				if(s.toLowerCase().trim().compareTo(dictionary.get(pos))==0) {
					//Ho trovato la parola
					RichWord rw = new RichWord();
					rw.setCorretta(false);
					rw.setParolaInserita(s);
					ltemp.add(rw);
					trovato = true;
				}
				
				if(s.toLowerCase().trim().compareTo(dictionary.get(pos))<0) {
					//Viene prima
					pos = pos/2;
					
				}
				
				if(s.toLowerCase().trim().compareTo(dictionary.get(pos))>0) {
					//viene dopo
					pos += pos/2;
				}
			} while(trovato = false || dictionary.get(pos)!=null);
		}
		return ltemp;
	}
}
