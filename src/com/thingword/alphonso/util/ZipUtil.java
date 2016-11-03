package com.thingword.alphonso.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {
	public static boolean unzip(InputStream input, String parentDir) {
		ZipInputStream zis = null;
		BufferedOutputStream bos = null;
		try {
			zis = new ZipInputStream(input,Charset.forName("gbk"));
			ZipEntry entry = null;
			File parent = new File(parentDir);
			FileUtil.deleteAllFilesOfDir(parent);
			if(!parent.exists()){
				parent.mkdirs();
			}
			while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
				File target = new File(parentDir, entry.getName());
				bos = new BufferedOutputStream(new FileOutputStream(target));
				int read = 0;
				byte[] buffer = new byte[1024 * 10];
				while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
					bos.write(buffer, 0, read);
				}
				bos.flush();
				bos.close();
			}
			zis.closeEntry();
			return true;
		} catch (IOException e) {
		}
		return false;
	}

}
