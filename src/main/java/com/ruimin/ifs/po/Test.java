package com.ruimin.ifs.po;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Test {
	public static void main(String[] args) {
		String s = "test";

		String leftPad = StringUtils.leftPad("", 4,"-");
		System.out.println(leftPad.replace("-", "&nbsp;") +s);
	}
	
	private static void getChildDir(File rootFile, File file,String findDir,List<String> tmpList){
		if (file.isDirectory()) {
			String rootPath = rootFile.getPath().replace("\\", "/");
			String filePath = file.getPath();
			String tpath = filePath.replace("\\", "/");
			String newpath = tpath.replace(rootPath, "");
			if (newpath.startsWith("/")) {
				newpath = newpath.substring(1);
			}
			if (newpath.equalsIgnoreCase(findDir)) {
				tmpList.add(file.getPath().replace("\\", "/"));
			}else{
				File[] fs = file.listFiles();
				for (int i = 0; i < fs.length; i++) {
					getChildDir(file, fs[i], findDir,tmpList);
				}
			}
		}
	}
	
	private static List<String> filterRefPath(String startDir,List<String> confRefList){
		if (StringUtils.isBlank(startDir)) {
			return confRefList;
		}else{
			List<String> tmpList = new ArrayList<String>(); 
			String tmp = startDir.replace("\\", "/").replace("/", ".");
			if (tmp.startsWith(".")) {
				tmp = tmp.substring(1);
			}
			if (tmp.endsWith(".")) {
				tmp = tmp.substring(0,tmp.length()-1);
			}
			for (int i = 0; i < confRefList.size(); i++) {
				String ref = confRefList.get(i).replace("\\", "/").replace("/", ".");
				int idx = ref.indexOf(tmp);
				if (idx>=0) {
					tmpList.add(ref.substring(idx+tmp.length()+1));
				}
			}
			return tmpList;
		}
	}
	
	private static void getDirListByLevel(List<File> startFileList,int start,int max,List<String> resultList){
		if (start<max && startFileList!=null && startFileList.size()>0) {
			List<File> tmpList = new ArrayList<File>();
			for (int i = 0; i < startFileList.size(); i++) {
				File[] fs = startFileList.get(i).listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File pathname) {
						return pathname.isDirectory();
					}
				});
				for (int j = 0; j < fs.length; j++) {
					resultList.add(fs[j].getPath().replace("\\", "/"));
					tmpList.add(fs[j]);
				}
			}
			start++;
			getDirListByLevel(tmpList, start, max, resultList);
		}
	}
	
}
