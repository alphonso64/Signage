package com.thingword.alphonso.util;

import org.apache.jasper.tagplugins.jstl.core.If;

public class CLog {
	private static final boolean DEBUG = true;
	
	public static void Log(String s){
		if(DEBUG){
			System.out.println(s);
		}
	}

}
