/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;

public class FileTools {

    public static String getExtension(File file) {
        String originalFileName = file.getName();
        if (originalFileName.contains(".")) {
            String extension = originalFileName.substring(originalFileName.indexOf("."));
            return extension;
        }
        return "";
    }

}
