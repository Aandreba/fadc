package Extras;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Size {
    public double value;
    public String symbol;
    public double multiplier;

    public static <T extends Size>T sum(T a, double b){
        Class c = a.getClass();
        try {
            return (T)c.getConstructors()[0].newInstance(a.value+b);
        } catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    public static Size sum(Size a, Size b){
        try {
            if (a.multiplier > b.multiplier) {
                return (Size)a.getClass().getConstructors()[0].newInstance(a.value+(b.value*a.multiplier/b.multiplier));
            } else if (a.multiplier < b.multiplier) {
                return (Size)b.getClass().getConstructors()[0].newInstance(b.value+(a.value*b.multiplier/a.multiplier));
            } else {
                return (Size)a.getClass().getConstructors()[0].newInstance(a.value+b.value);
            }
        } catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    public static Size sum(Size... vals){
        Size r = vals[0].clone();
        for (int i=1;i<vals.length;i++){
            r.add(vals[i]);
        }
        return r;
    }
    public Size sum(double b){
        return sum(this,b);
    }
    public Size sum(Size b){
        return sum(this,b);
    }
    public void add(double b){
        this.value += b;
    }
    public void add(Size b){
        if (this.multiplier == b.multiplier){
            this.value += b.value;
        } else {
            this.value += b.value*this.multiplier/b.multiplier;
        }
    }

    public static <T extends Size>T minus(T a, double b){
        Class c = a.getClass();
        try {
            return (T)c.getConstructors()[0].newInstance(a.value-b);
        } catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    public static <T extends Size>T minus(double a, T b){
        Class c = b.getClass();
        try {
            return (T)c.getConstructors()[0].newInstance(a-b.value);
        } catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    public static Size minus(Size a, Size b){
        try {
            if (a.multiplier > b.multiplier) {
                return (Size)a.getClass().getConstructors()[0].newInstance(a.value-(b.value*a.multiplier/b.multiplier));
            } else if (a.multiplier < b.multiplier) {
                return (Size)b.getClass().getConstructors()[0].newInstance(b.value-(a.value*b.multiplier/a.multiplier));
            } else {
                return (Size)a.getClass().getConstructors()[0].newInstance(a.value-b.value);
            }
        } catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    public Size minus(double b){
        return minus(this,b);
    }
    public Size minus(Size b){
        return minus(this,b);
    }
    public void subtr(double b){
        this.value -= b;
    }
    public void subtr(Size b){
        if (this.multiplier == b.multiplier){
            this.value -= b.value;
        } else {
            this.value -= b.value*this.multiplier/b.multiplier;
        }
    }

    public boolean equals(Size o){
        return this.getBytes().value == o.getBytes().value;
    }
    public boolean equals(Object o){
        try {
            return equals((Size)o);
        } catch (Throwable e){
            return false;
        }
    }

    public Size clone(){
        Class c = this.getClass();
        try {
            return (Size)c.getConstructors()[0].newInstance(this.value);
        } catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    public Size toSize(){
        return this;
    }
    public Bits getBits(){
        return new Bits(Math.round(value * multiplier / Bits._multiplier));
    };
    public Bytes getBytes(){
        return new Bytes(value * multiplier / Bytes._multiplier);
    };
    public KiloBytes getKiloBytes(){
        return new KiloBytes(value * multiplier / KiloBytes._multiplier);
    };
    public KibiBytes getKibiBytes(){
        return new KibiBytes(value * multiplier / KibiBytes._multiplier);
    };
    public MegaBytes getMegaBytes(){
        return new MegaBytes(value * multiplier / MegaBytes._multiplier);
    };
    public MebiBytes getMebiBytes(){
        return new MebiBytes(value * multiplier / MebiBytes._multiplier);
    };
    public GigaBytes getGigaBytes(){
        return new GigaBytes(value * multiplier / GigaBytes._multiplier);
    };
    public GigiBytes getGigiBytes(){
        return new GigiBytes(value * multiplier / GigiBytes._multiplier);
    };
    public TeraBytes getTeraBytes(){
        return new TeraBytes(value * multiplier / TeraBytes._multiplier);
    };
    public TeriBytes getTeriBytes(){
        return new TeriBytes(value * multiplier / TeriBytes._multiplier);
    };
    public <T extends Size>T getType(Class<T> type){
        if (type.equals(Bits.class)){
            return (T)this.getBits();
        } else if (type.equals(Bytes.class)){
            return (T)this.getBytes();
        } else if (type.equals(KiloBytes.class)){
            return (T)this.getKiloBytes();
        } else if (type.equals(KibiBytes.class)){
            return (T)this.getKibiBytes();
        } else if (type.equals(MegaBytes.class)){
            return (T)this.getMegaBytes();
        } else if (type.equals(MebiBytes.class)){
            return (T)this.getMebiBytes();
        } else if (type.equals(GigaBytes.class)){
            return (T)this.getGigaBytes();
        } else if (type.equals(GigiBytes.class)){
            return (T)this.getGigiBytes();
        } else if (type.equals(TeraBytes.class)){
            return (T)this.getTeraBytes();
        } else if (type.equals(TeriBytes.class)){
            return (T)this.getTeriBytes();
        } else {
            return null;
        }
    }

    public String toString(){
        try {
            List<Class> cs = new ArrayList<>();
            Collections.addAll(cs,Size.class.getDeclaredClasses());
            cs.sort((x,y) -> {
                try {
                    double r = (double)(x.getDeclaredField("_multiplier").get(null));
                    r /= (double)(y.getDeclaredField("_multiplier").get(null));
                    return (int)Math.round(r);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return 0;
            });
            for (int i=0;i<cs.size();i++){
                Class c = cs.get(i);
                double v = value * multiplier / (double)c.getDeclaredField("_multiplier").get(null);
                if (v < 1){
                    if (i > 0){
                        c = cs.get(i-1);
                        v = value * multiplier / (double)c.getDeclaredField("_multiplier").get(null);
                    }
                    return NumberFormat.getNumberInstance().format(v)+" "+c.getDeclaredField("_symbol").get(null);
                }
            }
        } catch (Throwable e){
            e.printStackTrace();
        }
        return NumberFormat.getNumberInstance().format(value)+" "+symbol;
    };

    public static class Bits extends Size {
        final static String _symbol = "b";
        final static double _multiplier = 1d/8d;
        public Bits(long val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public Bits getBits() {
            return this;
        }
    }
    public static class Bytes extends Size {
        final static String _symbol = "B";
        final static double _multiplier = 1d;

        public Bytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public Bytes getBytes() {
            return this;
        }
    }
    public static class KiloBytes extends Size {
        final static String _symbol = "kB";
        final static double _multiplier = Math.pow(10,3);
        public KiloBytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public KiloBytes getKiloBytes() {
            return this;
        }
    }
    public static class KibiBytes extends Size {
        final static String _symbol = "KiB";
        final static double _multiplier = Math.pow(2,10);
        public KibiBytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public KibiBytes getKibiBytes() {
            return this;
        }
    }
    public static class MegaBytes extends Size {
        final static String _symbol = "MB";
        final static double _multiplier = Math.pow(10,6);
        public MegaBytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public MegaBytes getMegaBytes() {
            return this;
        }
    }
    public static class MebiBytes extends Size {
        final static String _symbol = "MiB";
        final static double _multiplier = Math.pow(2,20);
        public MebiBytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public MebiBytes getMebiBytes() {
            return this;
        }
    }
    public static class GigaBytes extends Size {
        final static String _symbol = "GB";
        final static double _multiplier = Math.pow(10,9);
        public GigaBytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public GigaBytes getGigaBytes() {
            return this;
        }
    }
    public static class GigiBytes extends Size {
        final static String _symbol = "GiB";
        final static double _multiplier = Math.pow(2,30);
        public GigiBytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public GigiBytes getGigiBytes() {
            return this;
        }
    }
    public static class TeraBytes extends Size {
        final static String _symbol = "TB";
        final static double _multiplier = Math.pow(10,12);
        public TeraBytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public TeraBytes getTeraBytes() {
            return this;
        }
    }
    public static class TeriBytes extends Size {
        final static String _symbol = "TiB";
        final static double _multiplier = Math.pow(2,40);
        public TeriBytes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }
        @Override
        public TeriBytes getTeriBytes() {
            return this;
        }
    }
}