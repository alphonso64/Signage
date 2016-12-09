package com.thingword.alphonso.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.jdt.internal.compiler.util.Sorting;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.thingword.alphonso.bean.DispatchFile;

public class FileUtil {
	
	public static byte[] getFile(String path) {
		FileInputStream fis = null;
		byte[] bytes = new byte[0];
		if (path == null)
			return bytes;
		try {
			File file = new File(path);
			fis = new FileInputStream(file);
			bytes = new byte[fis.available()];
			fis.read(bytes);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public static void deleteAllFilesOfDir(File path) {
		if (!path.exists())
			return;
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAllFilesOfDir(files[i]);
		}
		// path.delete();
	}

	public static List<File> getXlsFileList(String parent) {
		List<File> ls = new ArrayList<>();
		File root = new File(parent);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.getName().toLowerCase().endsWith(".xls") && file.isFile()) {
				ls.add(file);
			}
		}
		return ls;
	}
	
	public static List<DispatchFile> getPdfFileList(String parent) {
		List<DispatchFile> ls = new ArrayList<>();
		File root = new File(parent);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.getName().toLowerCase().endsWith(".pdf") && file.isFile()) {
				DispatchFile dispatchFile = new DispatchFile();
				dispatchFile.setName(file.getName());
				dispatchFile.setPath(file.getAbsolutePath());
				ls.add(dispatchFile);
			}
		}
		return ls;
	}
	
	public static List<DispatchFile> getVideoFileList(String parent) {
		List<DispatchFile> ls = new ArrayList<>();
		File root = new File(parent);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.getName().toLowerCase().endsWith(".webm") && file.isFile()) {
				DispatchFile dispatchFile = new DispatchFile();
				dispatchFile.setName(file.getName());
				dispatchFile.setPath(file.getAbsolutePath());
				ls.add(dispatchFile);
			}
		}
		return ls;
	}
	
	public static List<File> getpdfFileList(String parent) {
		List<File> ls = new ArrayList<>();
		File root = new File(parent);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.getName().toLowerCase().endsWith(".pdf") && file.isFile()) {
				ls.add(file);
			}
		}
		return ls;
	}
	
	public static String xlsToPdf(String inFilePath, String outFilePath) {
		ComThread.InitSTA(true);
		ActiveXComponent ax = new ActiveXComponent("Excel.Application");
		try {

			ax.setProperty("Visible", new Variant(false));
			ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
			Dispatch excels = ax.getProperty("Workbooks").toDispatch();

			Dispatch excel = Dispatch
					.invoke(excels, "Open", Dispatch.Method,
							new Object[] { inFilePath, new Variant(false), new Variant(false) }, new int[9])
					.toDispatch();
			// 转换格式
			Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[] { new Variant(0), // PDF格式=0
					outFilePath, new Variant(0) // 0=标准 (生成的PDF图片不会变模糊) 1=最小文件
												// (生成的PDF图片糊的一塌糊涂)
			}, new int[1]);

			Dispatch.call(excel, "Close", new Variant(false));

			if (ax != null) {
				ax.invoke("Quit", new Variant[] {});
				ax = null;
			}
			ComThread.Release();
			return "";
		} catch (Exception es) {
			return es.toString();
		}
	}

	public static String xlsToPdf(List<String> inList, List<String> outList) {
		ComThread.InitSTA(true);
		ActiveXComponent ax = new ActiveXComponent("Excel.Application");
		try {
			ax.setProperty("Visible", new Variant(false));
			ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
			Dispatch excels = ax.getProperty("Workbooks").toDispatch();
			for (int i = 0; i < inList.size(); i++) {
				Dispatch excel = Dispatch
						.invoke(excels, "Open", Dispatch.Method,
								new Object[] { inList.get(i), new Variant(false), new Variant(false) }, new int[9])
						.toDispatch();
				// 转换格式
				Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[] { new Variant(0), // PDF格式=0
						outList.get(i), new Variant(0) // 0=标准 (生成的PDF图片不会变模糊)
														// 1=最小文件
														// (生成的PDF图片糊的一塌糊涂)
				}, new int[1]);

				Dispatch.call(excel, "Close", new Variant(false));
			}
			if (ax != null) {
				ax.invoke("Quit", new Variant[] {});
				ax = null;
			}
			ComThread.Release();
			return "";
		} catch (Exception es) {
			return es.toString();
		}
	}

	public static void pdfSplitter(String path) {
		File file = new File(path);
		String prename = path.substring(0, path.length()-4);
		PDDocument document;
		try {
			document = PDDocument.load(file);
			// Create a Splitter object
			Splitter splitter = new Splitter();

			// We need this as split method returns a list
			List<PDDocument> listOfSplitPages;

			// We are receiving the split pages as a list of PDFs
			listOfSplitPages = splitter.split(document);

			// We need an iterator to iterate through them
			Iterator<PDDocument> iterator = listOfSplitPages.listIterator();

			// I am using variable i to denote page numbers.
			int i = 1;
			while (iterator.hasNext()) {
				PDDocument pd = iterator.next();
				pd.save(prename + i++ + ".pdf");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String,List<String>> pdfSplitter(HashMap<String, String> map) {
		HashMap<String,List<String>> outmap = new HashMap<>(); 

		for (String key : map.keySet()) {
			String path = map.get(key);
			File file = new File(path);
			String prename = path.substring(0, path.length()-4);
			PDDocument document;
			try {
				List<String> value = new ArrayList<>();
				document = PDDocument.load(file);
				// Create a Splitter object
				Splitter splitter = new Splitter();

				// We need this as split method returns a list
				List<PDDocument> listOfSplitPages;

				// We are receiving the split pages as a list of PDFs
				listOfSplitPages = splitter.split(document);

				// We need an iterator to iterate through them
				Iterator<PDDocument> iterator = listOfSplitPages.listIterator();

				// I am using variable i to denote page numbers.
				int i = 1;
				while (iterator.hasNext()) {
					PDDocument pd = iterator.next();
					String pdfPath = prename + i++ + ".pdf";
					value.add(pdfPath);
					pd.save(pdfPath);
				}
				outmap.put(key, value);
				document.close();
				file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outmap;
	}
	
	public static void saveToFile(String fileName, InputStream in) throws IOException {   
		File file = new File(fileName);
		if(file.exists())
			file.delete();
        FileOutputStream fos = null;      
        BufferedInputStream bis = null;      
        int BUFFER_SIZE = 1024;   
        byte[] buf = new byte[BUFFER_SIZE];      
        int size = 0;      
        bis = new BufferedInputStream(in);      
        fos = new FileOutputStream(fileName);     
        while ( (size = bis.read(buf)) != -1)       
          fos.write(buf, 0, size);                    
        fos.close();      
        bis.close();      
      }  
}
