package com.li.io.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * Scanner一个可以使用正则表达式来解析基本类型和字符串的简单文本扫描器。
 * @author lizhen
 *
 */
public class ScannerDemo {
	public static void main(String[] args) throws FileNotFoundException {
//		Scanner sc = new Scanner(System.in);
//		Scanner sc = new Scanner(new File("D:/text.txt"));
		Scanner sc = new Scanner("1 fish 2 fish red fish blue fish");
//		sc.useDelimiter("d");
//		sc.useDelimiter("\\s*fish\\s*");
//		while (sc.hasNext()){
//			System.out.println(sc.next());
//		}
		sc.findInLine("(\\d+) fish (\\d+) fish (\\w+) fish (\\w+)");
		MatchResult result = sc.match();
		for (int i = 1; i <= result.groupCount(); i++) {
			System.out.println(result.group(i));
		}
		sc.close();
	}
}
