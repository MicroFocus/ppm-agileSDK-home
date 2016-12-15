package com.ppm.integration.agilesdk.connector.sample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class SampleTaskData {
	private Map<String,String> fieldDict= new HashMap<String,String>(15);
	private static final DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

	
	public void AddDataToFieldDict(String key, String value){
		fieldDict.put(key, value);
	}
	public String strValue(String name){
		return fieldDict.get(name);
	}
	public int intValue(String name, int defaultValue){
		int result = defaultValue;
		String str = strValue(name);
		if(!StringUtils.isEmpty(str)){
			try{
				result = Integer.parseInt(str);
			}catch(NumberFormatException e){
				//
			}
		}
		return result;
	}
	public double doubleValue(String name, double defaultValue){
		double result = defaultValue;

		String str = strValue(name);
		if(!StringUtils.isEmpty(str)){
			try{
				result = Double.parseDouble(str);
			}catch(NumberFormatException e){
				//
			}
		}
		return result;
	}
	public Date dateValue(String name, Date defaultValue){
		String strValue = strValue(name);

		if(!StringUtils.isEmpty(strValue)){
			try {
				return dateformat.parse( strValue );
			} catch (ParseException e) {
				return defaultValue;
			}
		}

		return defaultValue;
	}
	public Iterable<String> keys(){
		strValue("");
		return fieldDict.keySet();
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		boolean isFirst = true;
		for(String k : keys()){
			if(isFirst){
				isFirst = false;
			}else{
				sb.append(',');
			}
			sb.append(k);
			sb.append(':');
			sb.append(strValue(k));
		}
		return sb.toString();
	}

}
