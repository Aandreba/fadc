import Extras.ExtraFile;
import Extras.ExtraScanner;
import Extras.ExtraSystem;
import Extras.Time;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static NumberFormat number = NumberFormat.getNumberInstance();
    final public static String[] options = new String[]{ "add" };

    public static void main(String[] args) {
        /*if (ExtraSystem.env.os != ExtraSystem.Enviroment.OS.Windows){
            System.out.println("Sorry, we currently only support Windows");
            System.exit(1);
        }*/
        System.out.println("Hello :)");
        if (!Table.table.exists()){
            System.out.println("Table not found. Creating new one");
            Table.table.getParentFile().mkdirs();
            try {
                Table.table.createNewFile();
            } catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
            System.out.println("Table created");
        }
        try {
            Table.init();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        if (Table.dirs.size() <= 0){
            System.out.println("Table is empty");
        }
        /*for (Table.Directory dir: Table.dirs){
            System.out.println(dir.getPath()+":");
            for (Table.Directory.TableFile tf: dir) {
                System.out.println(tf);
            }
            System.out.println();
        }
        System.exit(1);*/
        System.out.println("Path of dir to add:");
        System.out.println();
        //File dir = ExtraScanner.nextClass(in, File.class);
        File dir = new File("/Users/Adebas/Desktop");
        try {
            addDir(dir);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Table.string());
    }

    public static void addDir(File path) throws Exception {
        Table.Directory dir = Table.newDir(path);
        if (dir != null) {
            dir.renewAccessTimes();
            Table.save();
        }
    }
}
