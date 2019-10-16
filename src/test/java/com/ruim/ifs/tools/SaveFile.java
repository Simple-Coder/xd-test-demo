package com.ruim.ifs.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.io.IOUtils;

public class SaveFile {

	public static void writeFile(File file, String context) throws Exception {
		OutputStreamWriter write = null;
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		try {
			write.write(context);
			write.flush();
			System.out.println(file.getPath() + " 写入成功!");
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		} finally {
			IOUtils.closeQuietly(write);
		}
	}

}
