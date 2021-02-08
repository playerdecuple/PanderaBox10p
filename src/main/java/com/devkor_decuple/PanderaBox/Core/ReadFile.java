package com.devkor_decuple.PanderaBox.Core;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;

public class ReadFile {

    public ReadFile() {

    }

    public static long readLong(String filePath) {
        try {
            FileInputStream fs = new FileInputStream(filePath);
            byte[] rB = new byte[fs.available()];
            while (fs.read(rB) != -1) {
            }

            return Long.parseLong(new String(rB));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long readLong(File file) {
        try {
            FileInputStream fs = new FileInputStream(file.getPath());
            byte[] rB = new byte[fs.available()];
            while (fs.read(rB) != -1) {
            }

            return Long.parseLong(new String(rB));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int readInt(String path) {
        try {
            FileInputStream fs = new FileInputStream(path);
            byte[] rB = new byte[fs.available()];
            while (fs.read(rB) != -1) {
            }

            return Integer.parseInt(new String(rB));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int readInt(File f) {
        try {
            FileInputStream fs = new FileInputStream(f.getPath());
            byte[] rB = new byte[fs.available()];
            while (fs.read(rB) != -1) {
            }

            return Integer.parseInt(new String(rB));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Nullable
    public static String readString(String path) {
        try {
            FileInputStream fs = new FileInputStream(path);
            byte[] rB = new byte[fs.available()];
            while (fs.read(rB) != -1) {
            }

            return new String(rB);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String readString(File f) {
        try {
            FileInputStream fs = new FileInputStream(f.getPath());
            byte[] rB = new byte[fs.available()];
            while (fs.read(rB) != -1) {
            }

            return new String(rB);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
