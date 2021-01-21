package Extras;

import javax.management.InvalidAttributeValueException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

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
        /*String path = String.join("\\ ",file.getCanonicalPath().split(" "));
        String exec = ExtraSystem.exec("stat -r "+path);
        String[] data = exec.split("\\s+");
        System.out.println(path+": "+Arrays.toString(data));
        System.exit(1);
        return new Date(Long.parseLong(data[8])*1000);*/
        return null;
    }

    public static Date lastAccessTime(File file) throws Exception {
        if (file.isDirectory()){
            throw new InvalidAttributeValueException("Directory provided, file expected");
        }
        if (ExtraSystem.env.os == ExtraSystem.Enviroment.OS.Unix){
            return unixLastAccessTime(file);
        } else if (ExtraSystem.env.os == ExtraSystem.Enviroment.OS.Windows){
            // Windows (TO DO)
        } else if (ExtraSystem.env.os == ExtraSystem.Enviroment.OS.OSX){
            //return osxLastAccessTime(file);
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
    private static Map<File,Date> osxLastAccessTimes(File file) throws Exception {
        Map<File,Date> r = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd kk:mm:ss yyyy", Locale.ENGLISH);
        String path = String.join("\\ ",file.getCanonicalPath().split(" "));
        String exec = "ls -luaT "+path;
        exec = ExtraSystem.exec(exec);

        String[] lines = exec.split("\\n");
        for (int i=0;i<lines.length;i++){
            String[] cols = lines[i].split("(\\s|\\t)+");
            if (cols[0].equals("total") || cols.length == 0 || cols[0].equals("")){
                continue;
            }
            String name = null;
            if (cols.length > 10) {
                name = String.join(" ", ExtraArray.subArray(cols, 9));
            } else {
                name = cols[9];
            }
            name = name.strip();
            if (name.equals(".") || name.equals("..") || !ExtraString.contains(name,"\\..+$")){
                continue;
            }
            String dateStr = String.join(" ",ExtraArray.subArray(cols,5,4));
            Date date = format.parse(dateStr);
            r.put(new File(name), date);
        }
        return r;
    }

    public static Map<File,Date> lastAccessTimes(File dir, boolean print) throws Exception {
        ExtraSystem.Enviroment.OS os = ExtraSystem.env.os;
        Map<File, Date> out = new HashMap<>();

        if (os == ExtraSystem.Enviroment.OS.Windows) {
            out = windowsAccessTimes(dir);
        } else {
            out = osxLastAccessTimes(dir);
        }

        File[] files = dir.listFiles();
        int i=0;
        for (File file : files) {
            i++;
            if (print) {
                System.out.println(i * 100f / files.length);
            }
            if (!file.isDirectory()) {
                continue;
            }
            out.putAll(lastAccessTimes(file,false));
        }
        return out;
    }

    public static Map<File,Date> lastAccessTimes(File dir) throws Exception {
        return lastAccessTimes(dir,true);
    }
}