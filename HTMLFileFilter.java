package com.codegym.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {

        String fileName = f.getName().toLowerCase();

        if (f.isDirectory() || fileName.endsWith(".htm") || fileName.endsWith(".html")) return true;
        return false;
    }

    @Override
    public String getDescription() {
        return "HTML and HTM files";
    }
}
