package com.ruimin.ifs.microswitch.test.process;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.service.micro.msgprocess.escape.IEscapeProc;


public class EpccEscapeProc implements IEscapeProc{

	private static final Map<String, String> escapeMap = new LinkedHashMap<String,String>(){
		private static final long serialVersionUID = -474614628105387876L;

		{
			put("\"","&#34;");
			put("'","&#39;");
			put("&","&#38;");
			put("<","&#60;");
			put(">","&#62;");
		}
	};
	
	private static final String[] keys = escapeMap.keySet().toArray(new String[]{});
	private static final String[] values =escapeMap.values().toArray(new String[]{});
	
	private static EpccEscapeProc proc = null;
	
	public synchronized static EpccEscapeProc getProc(){
		if (proc==null) {
			proc = new EpccEscapeProc();
		}
		return proc;
	}
	
	private EpccEscapeProc(){
	}
	
	@Override
	public String escape(String value) {
		if (StringUtils.isNotBlank(value)) {
			value = StringUtils.replaceEach(value, keys, values);
		}
		return value;
	}

	@Override
	public String unEscape(String value) {
		if (StringUtils.isNotBlank(value)) {
			value = StringUtils.replaceEach(value,values,keys);
		}
		return value;
	}

}
