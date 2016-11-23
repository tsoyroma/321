package ru.croc.sbkz.adapters.way.java;

import java.util.HashMap;

public class DataContainer {
	
	private static HashMap<String,HashMap<String,MessageHolder>> container = new HashMap<String,HashMap<String,MessageHolder>>();
	
	public static synchronized void addRecord(String day, String file, MessageHolder data){
		HashMap<String,MessageHolder> files = container.get(day);
		if (files==null) {
			files = new HashMap<String,MessageHolder>();
			container.put(day, files);
		}
		files.put(file,data);
	}
	
	public static synchronized MessageHolder getRecord(String day, String file){
		HashMap<String,MessageHolder> files = container.get(day);
		if (files==null) {
			return null;
		}
		MessageHolder data = files.remove(file);
		if (files.size()==0) {
			container.remove(day);
		}
		return data;
	}
	
	
	public static class MessageHolder {
		public byte[] msgId;
		public String rqUid;
	}
}
