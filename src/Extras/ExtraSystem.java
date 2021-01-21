package Extras;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ExtraSystem {
    public static class MemoryBank {
        enum Type {
            DDR,
            DDR2,
            DDR3,
            DDR4,
            unknown
        }
        public Size size;
        public Type type;
        public int speed;

        public MemoryBank(Size size, int speed, Type type){
            this.size = size;
            this.speed = speed;
            this.type = type;
        }
        public MemoryBank(long bytes, int speed, int code){
            this.size = new Size.Bytes(bytes);
            this.speed = speed;
            switch (code){
                case (22):
                    this.type = Type.DDR2;
                    break;
                case (24):
                    this.type = Type.DDR2;
                    break;
                case (25):
                    this.type = Type.DDR3;
                    break;
                case (26):
                    this.type = Type.DDR4;
                    break;
                default:
                    this.type = Type.unknown;
                    break;
            }
        }

        public boolean equals(MemoryBank o){
            return this.size.equals(o.size) && this.speed == o.speed && this.type == o.type;
        }
        public boolean equals(Object o){
            try {
                return equals((MemoryBank)o);
            } catch (Exception e){
                return false;
            }
        }
    }
    public static class SystemMemory extends ArrayList<MemoryBank> {
        public Size.Bytes total(){
            Size.Bytes bytes = new Size.Bytes(0);
            for (MemoryBank mem: this){
                bytes.add(mem.size);
            }
            return bytes;
        }

        public String toString(){
            Map<MemoryBank,Integer> types = new HashMap<>();
            for (MemoryBank mem: this){
                boolean existed = false;
                for (MemoryBank mem2: types.keySet()){
                    if (mem.equals(mem2)){
                        existed = true;
                        types.put(mem2,types.get(mem2)+1);
                        break;
                    }
                }
                if (!existed){
                    types.put(mem,1);
                }
            }
            if (types.size() <= 0){
                return "";
            }
            if (types.size() == 1){
                MemoryBank mem = this.get(0);
                return "x"+types.get(mem)+" "+mem.size+" "+(mem.type == MemoryBank.Type.unknown ? "" : mem.type)+"@"+mem.speed;
            } else {
                String r = "";
                for (MemoryBank mem: types.keySet()){
                    r += "x"+types.get(mem)+" "+mem.size+" "+(mem.type == MemoryBank.Type.unknown ? "" : mem.type)+"@"+mem.speed+"\n";
                }
                return r.substring(0,r.length()-1);
            }
        }
    }
    public static class Enviroment {
        public enum Architecture {
            x86_64,
            x86_32,
            arm64,
            arm32,
            i386,
            i686,
            ppc,
            unknown
        }
        public enum OS {
            Windows,
            OSX,
            Unix,
            Solaris,
            unknown
        }
        private Runtime runtime;
        final public Architecture arch;
        final public OS os;
        final public String name;
        final public int cores;
        final public int threads;
        final public int availableThreads;
        final public Size.Bytes maxMemory;
        final public SystemMemory systemMemory;

        public Enviroment(){
            this.runtime = Runtime.getRuntime();
            switch (System.getProperty("os.arch")){
                case ("x86_64"):
                    this.arch = Architecture.x86_64;
                    break;
                case ("amd64"):
                    this.arch = Architecture.x86_64;
                    break;
                case ("x86_32"):
                    this.arch = Architecture.x86_32;
                    break;
                case ("x86"):
                    this.arch = Architecture.x86_32;
                    break;
                case ("armv5tejl"):
                    this.arch = Architecture.arm32;
                    break;
                case ("armv6l"):
                    this.arch = Architecture.arm32;
                    break;
                case ("armv7l"):
                    this.arch = Architecture.arm32;
                    break;
                case ("armv8l"):
                    this.arch = Architecture.arm64;
                    break;
                case ("i386"):
                    this.arch = Architecture.i386;
                    break;
                case ("i686"):
                    this.arch = Architecture.i686;
                    break;
                case ("ppc"):
                    this.arch = Architecture.ppc;
                    break;
                case ("PowerPC"):
                    this.arch = Architecture.ppc;
                    break;
                default:
                    this.arch = Architecture.unknown;
                    break;
            }
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("win") >= 0){
                this.os = OS.Windows;
            } else if (os.indexOf("mac") >= 0){
                this.os = OS.OSX;
            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0){
                this.os = OS.Unix;
            } else if (os.indexOf("sunos") >= 0){
                this.os = OS.Solaris;
            } else {
                this.os = OS.unknown;
            }
            this.availableThreads = this.runtime.availableProcessors();
            this.maxMemory = Size.minus(this.runtime.maxMemory(),allocatedMemory());
            //this.maxMemory = new Extras.Size.Bytes(this.runtime.maxMemory());

            String name = "";
            SystemMemory sysMem = new SystemMemory();
            int cores = this.runtime.availableProcessors();
            int threads = cores;
            if (this.os == OS.OSX){
                try {
                    name = ExtraFile.readString(this.runtime.exec("sysctl -n machdep.cpu.brand_string").getInputStream());
                    cores = Integer.parseInt(ExtraFile.readString(this.runtime.exec("sysctl -n machdep.cpu.core_count").getInputStream()).strip());
                    threads = Integer.parseInt(ExtraFile.readString(this.runtime.exec("sysctl -n machdep.cpu.thread_count").getInputStream()).strip());
                    long memSize = Long.parseLong(ExtraFile.readString(this.runtime.exec("sysctl -n hw.memsize").getInputStream()).strip());
                    sysMem.add(new MemoryBank(memSize,0,0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (this.os == OS.Windows){
                try {
                    name = ExtraFile.readString(this.runtime.exec("wmic cpu get name").getInputStream());
                    name = name.split(System.lineSeparator())[1];
                    cores = Integer.parseInt(ExtraFile.readString(this.runtime.exec("wmic cpu get numberofcores").getInputStream()).split(System.lineSeparator())[1].strip());
                    threads = Integer.parseInt(ExtraFile.readString(this.runtime.exec("wmic cpu get threadcount").getInputStream()).split(System.lineSeparator())[1].strip());
                    String[] mems = ExtraFile.readString(this.runtime.exec("wmic memorychip get capacity,memorytype,speed").getInputStream()).strip().split(System.lineSeparator());
                    for (int i=1;i<mems.length;i++){
                        String[] info = mems[i].strip().split("(\s|\t){2,}");
                        sysMem.add(new MemoryBank(Long.parseLong(info[0]),Integer.parseInt(info[2]),Integer.parseInt(info[1])));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.name = name.strip();
            this.cores = cores;
            this.threads = threads;
            this.systemMemory = sysMem;
        }

        public String toString(){
            return "OS\n\t"+this.arch+" "+this.os+"\nCPU\n\t"+this.name+" ("+this.cores+" "+(this.cores > 1 ? "cores" : "core")+", "+this.threads+" "+(this.threads > 1 ? "threads" : "thread")+")\nSystem Memory\n\t"+this.systemMemory+"\nMemory\n\tFree: "+freeMemory()+"\n\tAllocated: "+allocatedMemory()+"\n\tTotal: "+totalMemory()+"\n\tMax: "+maxMemory;
        }

        public Size.Bytes freeMemory(){
            return new Size.Bytes(this.runtime.freeMemory());
        }

        public Size.Bytes totalMemory(){
            return new Size.Bytes(this.runtime.totalMemory());
        }

        public Size.Bytes allocatedMemory(){
            return totalMemory().minus(freeMemory()).getBytes();
        }
    }
    public static Enviroment env = new Enviroment();

    public static String exec(String command) throws IOException {
        return new String(Runtime.getRuntime().exec(command).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public static Date wmicDate(String date){
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(4,6));
        int day = Integer.parseInt(date.substring(6,8));
        int hours = Integer.parseInt(date.substring(8,10));
        int minutes = Integer.parseInt(date.substring(10,12));
        int seconds = Math.round(Float.parseFloat(date.substring(12,date.length()-4)));

        return ExtraDate.init(year,month,day,hours,minutes,seconds);
    }
}
