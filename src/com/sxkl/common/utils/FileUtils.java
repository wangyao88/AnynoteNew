package com.sxkl.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

public class FileUtils extends org.apache.commons.io.FileUtils {
	public static void writeFileToDisk(byte[] data, String filePath)
			throws Exception {
		FileOutputStream fos = null;
		if ((StringUtils.isNotEmpty(filePath)) && (data != null)) {
			fos = new FileOutputStream(filePath);
			fos.write(data);
			fos.close();
		}
	}

	public static byte[] readFileToByteArray(String filePath) throws Exception {
		File file = new File(filePath);
		InputStream in = new FileInputStream(file);
		try {
			return IOUtils.toByteArray(in);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static String getUUIDName(String fileName) {
		String[] split = fileName.split("\\.");
		String extendFile = "." + split[(split.length - 1)].toLowerCase();
		return UUID.randomUUID().toString() + extendFile;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getFileName(String fileName) {
		String[] split = fileName.split("\\.");
		return split[0];
	}

	public static String getUrlFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}

	public static String getFileTypeByName(String fileName) {
		String[] split = fileName.split("\\.");
		String extendFile = split[(split.length - 1)].toLowerCase();
		return extendFile;
	}

	public static void createFileWithEncoder(String filePath, String content,
			String encoder) throws Exception {
		FileOutputStream fos = new FileOutputStream(filePath, false);
		OutputStreamWriter osw = new OutputStreamWriter(fos, encoder);
		osw.write(content);

		osw.close();
		fos.close();
	}

	public static void createDirs(String dirs) throws Exception {
		File file = new File(dirs);
		file.mkdirs();
	}

	public static String save(File f, String path) throws Exception {
		File dest = new File(path);
		if (!dest.getParentFile().exists())
			dest.getParentFile().mkdirs();
		copyFile(f, dest);
		return path;
	}

	public static void delete(String path) throws Exception {
		File dest = new File(path);
		if (dest.exists())
			forceDelete(dest);
	}

	public static void delete(File dest) throws Exception {
		if (dest.exists())
			forceDelete(dest);
	}

	public static void saveFileByUrl(String url, String filePath)
			throws Exception {
		URL u = new URL(url);

		HttpURLConnection httpUrl = (HttpURLConnection) u.openConnection();

		BufferedInputStream bis = null;

		FileOutputStream fos = null;
		try {
			bis = new BufferedInputStream(httpUrl.getInputStream());
			delete(filePath);
			fos = new FileOutputStream(new File(filePath), true);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				fos.write(buff, 0, bytesRead);
			}
		} finally {
			if (bis != null)
				bis.close();
			if (fos != null)
				fos.close();
		}
	}

}