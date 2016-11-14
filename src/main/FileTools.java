/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileTools {

    public static String getExtension(File file) {
        String originalFileName = file.getName();
        if (originalFileName.contains(".")) {
            String extension = originalFileName.substring(originalFileName.indexOf("."));
            return extension;
        }
        return "";
    }

    public static byte[] getFileBytes(File file) {
        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fileInputStream;
        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            System.out.println("Done");
            return bFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FileNameExtensionFilter getSGOFilter() {
        return new FileNameExtensionFilter("SGO", "sgo");
    }

    public static FileNameExtensionFilter getCIPFilter() {
        return new FileNameExtensionFilter("CIP", "cip");
    }
}
