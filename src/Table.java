import Extras.ExtraArray;
import Extras.ExtraFile;
import Extras.ExtraObject;
import Extras.Time;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class Table {
    public static class Directory extends File implements Collection<Directory.TableFile> {
        static class TableFile extends File implements Collection<Long> {
            private long[] logs;

            public TableFile(String path){
                super(path);
                this.logs = new long[0];
            }
            public TableFile(String parent, String child){
                super(parent, child);
                this.logs = new long[0];
            }
            public TableFile(File parent, String child){
                super(parent, child);
                this.logs = new long[0];
            }
            public TableFile(File file){
                super(file.getPath());
                this.logs = new long[0];
            }

            public File getFile(){
                return this;
            }

            public int numLogsLastTime(long millis){
                long current = System.currentTimeMillis();
                int id = 0;
                for (int i=0;i<logs.length;i++){
                    if (current-logs[i] > millis){
                        continue;
                    }
                    id = i;
                    break;
                }
                return logs.length-id;
            }
            public int numLogsLastTime(Time delta){
                return numLogsLastTime(Math.round(delta.getMilliSeconds().value));
            }

            @Override
            public int size() {
                return this.logs.length;
            }

            @Override
            public boolean isEmpty() {
                return this.logs.length <= 0;
            }

            public boolean contains(Long o){
                for (int i=0;i<this.logs.length;i++){
                    if (o.equals(this.logs[i])){
                        return true;
                    }
                }
                return false;
            }
            @Override
            public boolean contains(Object o) {
                return (o.getClass().equals(Long.class) || o.getClass().equals(long.class)) && contains((Long)o);
            }

            public boolean equals(TableFile o){
                return o.equals((File)this) && Arrays.equals(o.logs,this.logs);
            }

            @Override
            public Iterator<Long> iterator() {
                return new Iterator<Long>() {
                    int id = -1;
                    @Override
                    public boolean hasNext() {
                        return (id+1) < logs.length;
                    }

                    @Override
                    public Long next() {
                        id++;
                        return logs[id];
                    }
                };
            }

            public long[] toPrimArray() {
                return this.logs;
            }
            public Long[] toArray() {
                return ExtraArray.toClass(this.logs);
            }
            @Override
            public <T> T[] toArray(T[] a) {
                if (a.length > 0) {
                    for (int i = 0; i < a.length; i++) {
                        if (i >= this.logs.length){
                            break;
                        }
                        a[i] = (T)Long.valueOf(this.logs[i]);
                    }
                } else {
                    for (int i=0;i<this.logs.length;i++){
                        a = ExtraArray.concat(a,(T)Long.valueOf(this.logs[i]));
                    }
                }
                return a;
            }

            public long get(int index){
                return this.logs[index];
            }

            public long lastTime(){
                if (size() <= 0){
                    return 0;
                }
                return this.logs[this.logs.length-1];
            }
            public Date lastDate(){
                return new Date(lastTime());
            }

            @Override
            public boolean add(Long aLong) {
                this.logs = ExtraArray.concat(this.logs,aLong);
                return true;
            }
            public boolean add(Date aDate){
                this.logs = ExtraArray.concat(this.logs, aDate.getTime());
                return true;
            }

            public boolean remove(Long aLong){
                int index = 0;
                while((index=ExtraArray.indexOf(this.logs,aLong)) >= 0){
                    this.logs = ExtraArray.remove(this.logs,index);
                }
                return true;
            }
            @Override
            public boolean remove(Object o) {
                if ((!o.getClass().equals(Long.class) && o.getClass().equals(long.class)) || !contains((Long)o)) {
                    return false;
                }
                remove((Long)o);
                return true;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                Object[] values = c.toArray();
                boolean r = true;
                for (int i=0;i<values.length;i++){
                    r = contains(values[i]);
                    if (!r){ break; }
                }
                return r;
            }

            @Override
            public boolean addAll(Collection<? extends Long> c) {
                Long[] vals = c.toArray(new Long[0]);
                boolean r = true;
                for (int i=0;i<vals.length;i++){
                    r = add(vals[i]);
                    if (!r){ break; }
                }
                return r;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                Long[] vals = c.toArray(new Long[0]);
                boolean r = true;
                for (int i=0;i<vals.length;i++){
                    r = remove(vals[i]);
                    if (!r){ break; }
                }
                return r;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                long[] v = new long[0];
                for (int i=0;i<this.logs.length;i++){
                    if (c.contains(this.logs[i])){
                        v = ExtraArray.concat(v,this.logs[i]);
                    }
                }
                this.logs = v;
                return true;
            }

            @Override
            public void clear() {
                this.logs = new long[0];
            }

            @Override
            public String toString(){
                try {
                    return getCanonicalPath() + ": " + Main.number.format(size()) + " logs (" + lastDate() + ")";
                } catch (Exception e){
                    e.printStackTrace();
                    return "";
                }
            }

            public byte[] byteArray() throws IOException {
                byte[] out = this.getCanonicalPath().getBytes();
                return ExtraArray.concat(ExtraObject.byteArray(out.length), out, ExtraObject.byteArray(logs));
            }

            public static byte[] byteArray(TableFile[] files) throws IOException {
                byte[] out = new byte[0];
                for (int i=0;i<files.length;i++){
                    byte[] bytes = files[i].byteArray();
                    out = ExtraArray.concat(out, ExtraObject.byteArray(bytes.length), bytes);
                }
                return out;
            }

            public static byte[] byteArray(Collection<? extends TableFile> files) throws IOException {
                return byteArray(files.toArray(new TableFile[0]));
            }

            public static TableFile byteArray(byte[] bytes){
                int pathLength = ExtraObject.byteArrayInt(ExtraArray.subArray(bytes,0,4));
                String path = new String(ExtraArray.subArray(bytes,4,pathLength), StandardCharsets.UTF_8);
                TableFile r = new TableFile(path);
                r.logs = ExtraObject.fromByteLongArray(ExtraArray.subArray(bytes,4+pathLength));
                return r;
            }

            public static TableFile[] fromByteArray(byte[] bytes){
                TableFile[] r = new TableFile[0];
                int i = 0;
                while (i < bytes.length){
                    int length = ExtraObject.byteArrayInt(ExtraArray.subArray(bytes,i,4));
                    i += 4;
                    TableFile tf = TableFile.byteArray(ExtraArray.subArray(bytes,i,length));
                    i += length;
                    r = ExtraArray.concat(r,tf);
                }
                return r;
            }
        }

        private TableFile[] history;

        public Directory(File dir) throws IOException {
            super(dir.getCanonicalPath());
            if (!dir.isDirectory()){
                System.out.println("Tried to provide non-directory");
                System.exit(1);
            } else if (!dir.exists()){
                System.out.println("Directory not found");
                System.exit(1);
            }
            this.history = new TableFile[0];
        }
        public Directory(File dir, TableFile[] history) throws IOException {
            super(dir.getCanonicalPath());
            if (!dir.isDirectory()){
                System.out.println("Tried to provide non-directory");
                System.exit(1);
            } else if (!dir.exists()){
                System.out.println("Directory not found");
                System.exit(1);
            }
            this.history = history;
        }

        public File getFile(){
            return this;
        }

        public void renewAccessTimes() throws Exception {
            Map<File,Date> la = ExtraFile.lastAccessTimes(this);
            for (File file: la.keySet()){
                TableFile tf = this.get(file);
                if (tf == null){
                    add(new TableFile(file){{
                        add(la.get(file));
                    }});
                } else if (tf.lastTime() < la.get(file).getTime()){
                    tf.add(la.get(file));
                }
            }
        }

        @Override
        public int size() {
            return this.history.length;
        }

        @Override
        public boolean isEmpty() {
            return this.history.length <= 0 || Arrays.stream(this.history).allMatch(x -> x==null);
        }

        public boolean contains(TableFile o) {
            return ExtraArray.indexOf(this.history,o) >= 0;
        }
        @Override
        public boolean contains(Object o) {
            return o.getClass().equals(TableFile.class) && contains((TableFile)o);
        }

        public boolean equals(Directory o){
            return o.equals((File)this) && Arrays.equals(o.history,this.history);
        }

        public int indexOf(File o){
            return ExtraArray.indexOf(this.history,o);
        }
        public int indexOf(Object o) {
            if (!o.getClass().equals(File.class)){
                return -1;
            }
            return indexOf((TableFile)o);
        }

        @Override
        public Object[] toArray() {
            return this.history;
        }
        @Override
        public <T> T[] toArray(T[] a) {
            if (a.length > 0) {
                for (int i = 0; i < a.length; i++) {
                    if (i >= this.history.length){
                        break;
                    }
                    a[i] = (T)this.history[i];
                }
            } else {
                for (int i=0;i<this.history.length;i++){
                    a = ExtraArray.concat(a,(T)this.history[i]);
                }
            }
            return a;
        }

        public TableFile get(int id){
            return this.history[id];
        }
        public TableFile get(File file){
            for (int i=0;i<this.history.length;i++){
                if (file.equals(this.history[i])){
                    return this.history[i];
                }
            }
            return null;
        }

        @Override
        public boolean add(TableFile tableFile) {
            this.history = ExtraArray.concat(this.history,tableFile);
            return true;
        }

        public boolean remove(TableFile o) {
            this.history = ExtraArray.remove(this.history,o);
            return true;
        }
        @Override
        public boolean remove(Object o) {
            if (!o.getClass().equals(TableFile.class) || ExtraArray.indexOf(this.history,(TableFile)o) < 0){
                return false;
            }
            remove((TableFile)o);
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            TableFile[] vals = c.toArray(new TableFile[0]);
            boolean r = true;
            for (int i=0;i<vals.length;i++){
                r = contains(vals[i]);
                if (!r){ break; }
            }
            return r;
        }

        @Override
        public boolean addAll(Collection<? extends TableFile> c) {
            TableFile[] vals = c.toArray(new TableFile[0]);
            boolean r = true;
            for (int i=0;i<vals.length;i++){
                r = add(vals[i]);
                if (!r){ break; }
            }
            return r;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            TableFile[] vals = c.toArray(new TableFile[0]);
            boolean r = true;
            for (int i=0;i<vals.length;i++){
                r = remove(vals[i]);
                if (!r){ break; }
            }
            return r;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            TableFile[] r = new TableFile[0];
            for (int i=0;i<this.history.length;i++){
                if (c.contains(this.history[i])){
                    r = ExtraArray.concat(r,this.history[i]);
                }
            }
            this.history = r;
            return true;
        }

        @Override
        public void clear() {
            this.history = new TableFile[0];
        }

        @Override
        public Iterator<TableFile> iterator() {
            return new Iterator<TableFile>() {
                int id = -1;
                @Override
                public boolean hasNext() {
                    return (id+1) < history.length;
                }

                @Override
                public TableFile next() {
                    id++;
                    return history[id];
                }
            };
        }

        @Override
        public String toString(){
            return getPath()+": "+Arrays.toString(this.history);
        }

        public byte[] byteArray() throws IOException {
            byte[] out = this.getCanonicalPath().getBytes();
            return ExtraArray.concat(ExtraObject.byteArray(out.length),out,TableFile.byteArray(history));
        }

        public static byte[] byteArray(Directory[] dirs) throws IOException {
            byte[] out = new byte[0];
            for (int i=0;i<dirs.length;i++){
                byte[] bytes = dirs[i].byteArray();
                out = ExtraArray.concat(out,ExtraObject.byteArray(bytes.length),bytes);
            }
            return out;
        }

        public static byte[] byteArray(Collection<? extends Directory> dirs) throws IOException {
            return byteArray(dirs.toArray(new Directory[dirs.size()]));
        }

        public static Directory byteArray(byte[] bytes){
            int pathLength = ExtraObject.byteArrayInt(ExtraArray.subArray(bytes,0,4));
            String path = new String(ExtraArray.subArray(bytes,4,pathLength), StandardCharsets.UTF_8);
            TableFile[] history = TableFile.fromByteArray(ExtraArray.subArray(bytes,4+pathLength));
            try {
                return new Directory(new File(path), history);
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        public static Directory[] fromByteArray(byte[] bytes){
            Directory[] r = new Directory[0];
            int i = 0;
            while (i < bytes.length){
                int length = ExtraObject.byteArrayInt(ExtraArray.subArray(bytes,i,4));
                i += 4;
                r = ExtraArray.concat(r,Directory.byteArray(ExtraArray.subArray(bytes,i,length)));
                i += length;
            }
            return r;
        }
    }
    public static File table = new File("./data/table");
    public static List<Directory> dirs;

    // TO DO
    public static void init() throws IOException {
        dirs = new ArrayList<>();
        Collections.addAll(dirs,Directory.fromByteArray(ExtraFile.readBytes(table)));
    }

    public static void save() throws IOException {
        ExtraFile.writeBytes(table,Directory.byteArray(dirs));
    }

    public static String string(){
        return dirs.toString();
    }

    public static Directory newDir(File d){
        for (Directory dir: dirs){
            if (dir.getFile().equals(d)){
                System.out.println("Directory already in list");
                return null;
            }
        }
        try {
            Directory dir = new Directory(d);
            dirs.add(dir);
            return dir;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void newAccess(File dir, File file, Date date) throws IOException {
        if (file.isDirectory()){
            System.out.println("Directories can't be tracked");
            return;
        } else if (!file.exists()){
            System.out.println("File not found");
            return;
        }
        int indexDir = dirs.indexOf(dir);
        Directory directory = null;
        if (indexDir < 0) {
            directory = new Directory(dir);
            dirs.add(directory);
            indexDir = dirs.size()-1;
        } else {
            directory = dirs.get(indexDir);
        }

        int indexFile = directory.indexOf(file);
        Directory.TableFile tabFile = null;
        if (indexFile < 0){
            tabFile = new Directory.TableFile(file);
            directory.add(tabFile);
            indexFile = directory.size()-1;
        }

        tabFile.add(date);
    }
}
