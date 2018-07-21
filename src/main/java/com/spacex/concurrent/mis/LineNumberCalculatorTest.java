package com.spacex.concurrent.mis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class LineNumberCalculatorTest {

    private static Logger logger = LoggerFactory.getLogger(LineNumberCalculatorTest.class);

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        final String path = "/Users/lucas/projects/spring-boot-shane";
        final String suffix = ".java";
        final File file = new File(path);
        long totalLine = calculateLineNumber(file, suffix);
        System.out.println("Total:" + totalLine);
    }

    public static long calculateLineNumber(File file, String suffix) {
        long lineNumber = 0;

        if (file == null || !file.exists()) {
            return lineNumber;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (File childFile : childFiles) {
                long lineNumberOfChildFile = calculateLineNumber(childFile, suffix);//recursive
                lineNumber = lineNumberOfChildFile + lineNumber;
            }

        } else {
            try {

                String fileName = file.getName();
                if (!fileName.toLowerCase().endsWith(suffix)) {
                    return 0;
                }

                FileReader fileReader = new FileReader(file);
                LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

                while (lineNumberReader.readLine() != null) {
                    lineNumber++;
                }

                logger.info(String.format("%s ,total number of lines:%s", file.getPath(), lineNumber));
                lineNumberReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lineNumber;
    }
}
