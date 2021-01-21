package Extras;

import Optimization.CPU;

import javax.management.InvalidAttributeValueException;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ExtraFile {
    public static byte[] readBytes(InputStream stream) throws IOException {
        return stream.readAllBytes();
    }
    public static byte[] readBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        return readBytes(fis);
    }
    public static byte[] readBytes(String file) throws IOException {
        FileInputStream fis = new FileInputStream(new File(file));
        return readBytes(fis);
    }

    public static String readString(InputStream stream) throws Exception {
        return new String(readBytes(stream), StandardCharsets.UTF_8);
    }
    public static String readString(File file) throws IOException {
        Scanner scn = new Scanner(file);
        String r = "";
        while(scn.hasNextLine()){
            r += scn.nextLine()+"\n";
        }
        return r;
    }
    public static String readString(String file) throws IOException {
        return readString(new File(file));
    }

    public static void writeBytes(OutputStream stream, byte... bytes) throws IOException {
        stream.write(bytes);
    }
    public static void writeBytes(File file, byte... bytes) throws IOException {
        writeBytes(new FileOutputStream(file),bytes);
    }
    public static void writeBytes(String path, byte... bytes) throws IOException {
        writeBytes(new File(path), bytes);
    }

    private static Date unixLastAccessTime(File file) throws IOException {
        String exec = ExtraSystem.exec("stat -r "+file.getCanonicalPath());
        String[] data = exec.split("\s+");
        return new Date(Long.parseLong(data[8])*1000);
    }

    public static Date lastAccessTime(File file) throws Exception {
        if (file.isDirectory()){
            throw new InvalidAttributeValueException("Directory provided, file expected");
        }
        if (ExtraSystem.env.os == ExtraSystem.Enviroment.OS.Unix || ExtraSystem.env.os == ExtraSystem.Enviroment.OS.OSX){
            return unixLastAccessTime(file);
        } else if (ExtraSystem.env.os == ExtraSystem.Enviroment.OS.Windows){
            // Windows (TO DO)
        }
        return null;
    }

    private static Map<File,Date> windowsAccessTimes(File dir) throws IOException {
        Map<File,Date> r = new HashMap<>();
        String absPath = dir.getCanonicalPath();
        if (!ExtraString.contains(absPath,"\\"+File.separator+"$")){
            absPath += File.separator;
        }
        String drive = absPath.substring(0,2);
        String path = absPath.substring(2);
        path = ExtraString.replaceAll(path,"\\.$","");
        path = path.replace("\\","\\\\");

        String data = ExtraSystem.exec("wmic datafile where \"drive='"+drive+"' and path='"+path+"'\" get name,lastaccessed / format:csv");
        String[] lines = data.split((char)13+"+"+(char)10+"+");
        lines = ExtraArray.subArray(lines,1,lines.length-1);
        String[] headers = lines[0].split("\s*,\s*");

        int pathId = ExtraArray.indexOf(headers,"Name");
        int laId = ExtraArray.indexOf(headers,"LastAccessed");
        for (int i=1;i<lines.length;i++){
            String[] vals = lines[i].split("\s*,\s*");
            Time time = new Time.NanoSeconds(Double.parseDouble(vals[laId].split("\\+")[0]));
            r.put(new File(vals[pathId]),ExtraSystem.wmicDate(vals[laId]));
        }
        return r;
    }

    public static Map<File,Date> lastAccessTimes(File dir) throws Exception {
        // UNIX: stat /etc
        if (ExtraSystem.env.os == ExtraSystem.Enviroment.OS.OSX || ExtraSystem.env.os == ExtraSystem.Enviroment.OS.Unix){

        } else if (ExtraSystem.env.os == ExtraSystem.Enviroment.OS.Windows) {
            Map<File, Date> out = new HashMap<>();
            CPU optim = new CPU(() -> {
                try {
                    System.out.println("Error");
                    out.putAll(windowsAccessTimes(dir));
                } catch (IOException e) {
                    System.out.println("ErRoR");
                    e.printStackTrace();
                }
            });
            optim.setCPUPct(0.5f);

            File[] files = dir.listFiles();
            for (File file : files) {
                if (!file.isDirectory()) {
                    continue;
                }
                optim.addRunnable(() -> {
                    try {
                        out.putAll(lastAccessTimes(file));
                    } catch (Exception e) {
                        System.out.println("Error");
                        e.printStackTrace();
                    }
                });
            }
            // TO DO
            optim.start();
            Thread.sleep(5000);
            optim.interrupt();
            return out;
        }
        return null;
    }
}