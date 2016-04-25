package ca.ulaval.mp.mp.util;

import android.content.Context;

import java.util.Map;

import ca.ulaval.mp.mp.R;

public class Util {
	public static String getFormatedAPIURL(Context inContext,String inMethod,Map<String, String> inParams){
		String API_BASE = inContext.getResources().getString(R.string.API_BASE);
		String API_format = inContext.getResources().getString(R.string.API_FORMAT);
		if(inParams==null){
			return API_BASE+API_format+inMethod;
		}else{
			return API_BASE+API_format+inMethod;
		}
	}
	public static String getFormatedAPIURL(Context inContext,String inMethod){
		return Util.getFormatedAPIURL(inContext, inMethod,null);
	}

}
