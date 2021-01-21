import Extras.ExtraFile;
import Extras.Time;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class Run {
    public static void main(String[] args){
        try {
            Table.init();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        for (Table.Directory dir: Table.dirs){
            try {
                dir.renewAccessTimes();
            } catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }
            cache(dir,new Time.Days(1));
        }
    }

    private static void cache(Table.Directory dir, Time delta){
        Table.Directory.TableFile[] files = (Table.Directory.TableFile[]) dir.toArray();
        Arrays.sort(files,(x,y) -> x.numLogsLastTime(delta) - y.numLogsLastTime(delta));
        for (Table.Directory.TableFile file: files){
            System.out.println(file.getPath()+": "+file.numLogsLastTime(delta));
        }
    }
}
