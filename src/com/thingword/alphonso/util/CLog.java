package com.thingword.alphonso.util;

import org.apache.jasper.tagplugins.jstl.core.If;

public class CLog {
	private static final boolean DEBUG = false;
	
	public static void Log(String s){
		if(DEBUG){
			System.out.println(s);
		}
	}

}
