package com.li.io.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipDemo {
	public static void main(String[] args) throws IOException{
		zipMultiFile();
	}
	
	/**
	 * 压缩单个文件
	 * @throws IOException
	 */
	public static void zipSingleFile() throws IOException {
		File file = new File("d:" + File.separator + "1.txt");
        File zipFile = new File("d:" + File.separator + "hello.zip");
        InputStream input = new FileInputStream(file);
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
                zipFile));
        zipOut.putNextEntry(new ZipEntry(file.getName()));
        // 设置注释
        zipOut.setComment("hello1");
        int temp = 0;
        while((temp = input.read()) != -1){
            zipOut.write(temp);
        }
        input.close();
        zipOut.close();
	}
	
	/**
	 * 压缩多个文件
	 * @throws IOException
	 */
	public static void zipMultiFile() throws IOException {
		File file = new File("d:" + File.separator + "folder");
        File zipFile = new File("d:" + File.separator + "folder.zip");
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
        		zipFile));
        // 设置注释
        zipOut.setComment("hello1");
        if (file.isDirectory()) {
        	File[] files = file.listFiles();
        	for (File f : files) {
        		InputStream input = new FileInputStream(f);
        		zipOut.putNextEntry(new ZipEntry(file.getName()+File.separator+f.getName()));
        		int temp = 0;
        		while((temp = input.read()) != -1){
        			zipOut.write(temp);
        		}
        		input.close();
        	}
        }
        zipOut.close();
	}
	
	/**
	 * 解压单个文件
	 * @throws ZipException
	 * @throws IOException
	 */
	public static void unZipSingleFile() throws ZipException, IOException {
		File file = new File("d:" + File.separator + "hello.zip");
        File outFile = new File("d:" + File.separator + "unZipFile.txt");
        ZipFile zipFile = new ZipFile(file);
        ZipEntry entry = zipFile.getEntry("hello.txt");
        InputStream input = zipFile.getInputStream(entry);
        OutputStream output = new FileOutputStream(outFile);
        int temp = 0;
        while((temp = input.read()) != -1){
            output.write(temp);
        }
        input.close();
        output.close();
	}
	
	/**
	 * 解压多个文件
	 * @throws IOException
	 */
	public static void upZipMultiFile() throws IOException {
		File file = new File("d:" + File.separator + "zipFile.zip");
        File outFile = null;
        ZipFile zipFile = new ZipFile(file);
        ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
        ZipEntry entry = null;
        InputStream input = null;
        OutputStream output = null;
        while((entry = zipInput.getNextEntry()) != null){
            System.out.println("解压缩" + entry.getName() + "文件");
            outFile = new File("d:" + File.separator + entry.getName());
            if(!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdir();
            }
            if(!outFile.exists()){
                outFile.createNewFile();
            }
            input = zipFile.getInputStream(entry);
            output = new FileOutputStream(outFile);
            int temp = 0;
            while((temp = input.read()) != -1){
                output.write(temp);
            }
            input.close();
            output.close();
        }
	}
}
