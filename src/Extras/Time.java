package Extras;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Time {
    public double value;
    public String symbol;
    public double multiplier;

    public static <T extends Time> T sum(T a, double b) {
        Class c = a.getClass();
        try {
            return (T) c.getConstructors()[0].newInstance(a.value + b);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Time sum(Time a, Time b) {
        try {
            if (a.multiplier > b.multiplier) {
                return (Time) a.getClass().getConstructors()[0].newInstance(a.value + (b.value * b.multiplier / a.multiplier));
            } else if (a.multiplier < b.multiplier) {
                return (Time) b.getClass().getConstructors()[0].newInstance(b.value + (a.value * a.multiplier / b.multiplier));
            } else {
                return (Time) a.getClass().getConstructors()[0].newInstance(a.value + b.value);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Time sum(Time... vals) {
        Time r = vals[0].clone();
        for (int i = 1; i < vals.length; i++) {
            r.add(vals[i]);
        }
        return r;
    }

    public Time sum(double b) {
        return sum(this, b);
    }

    public Time sum(Time b) {
        return sum(this, b);
    }

    public void add(double b) {
        this.value += b;
    }

    public void add(Time b) {
        if (this.multiplier == b.multiplier) {
            this.value += b.value;
        } else {
            this.value += b.value * this.multiplier / b.multiplier;
        }
    }

    public static <T extends Time> T minus(T a, double b) {
        Class c = a.getClass();
        try {
            return (T) c.getConstructors()[0].newInstance(a.value - b);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Time> T minus(double a, T b) {
        Class c = b.getClass();
        try {
            return (T) c.getConstructors()[0].newInstance(a - b.value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Time minus(Time a, Time b) {
        try {
            if (a.multiplier > b.multiplier) {
                return (Time) a.getClass().getConstructors()[0].newInstance(a.value - (b.value * b.multiplier / a.multiplier));
            } else if (a.multiplier < b.multiplier) {
                return (Time) b.getClass().getConstructors()[0].newInstance(b.value - (a.value * a.multiplier / b.multiplier));
            } else {
                return (Time) a.getClass().getConstructors()[0].newInstance(a.value - b.value);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public Time minus(double b) {
        return minus(this, b);
    }

    public Time minus(Time b) {
        return minus(this, b);
    }

    public void subtr(double b) {
        this.value -= b;
    }

    public void subtr(Time b) {
        if (this.multiplier == b.multiplier) {
            this.value -= b.value;
        } else {
            this.value -= b.value * b.multiplier / this.multiplier;
        }
    }

    /*public boolean equals(Time o){
        return this.getBytes().value == o.getBytes().value;
    }*/
    public boolean equals(Object o) {
        try {
            return equals((Time) o);
        } catch (Throwable e) {
            return false;
        }
    }

    public Time clone() {
        Class c = this.getClass();
        try {
            return (Time) c.getConstructors()[0].newInstance(this.value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public Time toSize() {
        return this;
    }

    public NanoSeconds getNanoSeconds() {
        return new NanoSeconds(Math.round(value * multiplier / NanoSeconds._multiplier));
    }
    public MicroSeconds getMicroSeconds() {
        return new MicroSeconds(Math.round(value * multiplier / MicroSeconds._multiplier));
    }
    public MilliSeconds getMilliSeconds() {
        return new MilliSeconds(Math.round(value * multiplier / MilliSeconds._multiplier));
    }
    public CentiSeconds getCentiSeconds() {
        return new CentiSeconds(Math.round(value * multiplier / CentiSeconds._multiplier));
    }
    public DeciSeconds getDeciSeconds() {
        return new DeciSeconds(Math.round(value * multiplier / DeciSeconds._multiplier));
    }
    public Seconds getSeconds() {
        return new Seconds(Math.round(value * multiplier / Seconds._multiplier));
    }
    public Minutes getMinutes() {
        return new Minutes(Math.round(value * multiplier / Minutes._multiplier));
    }
    public Hours getHours() {
        return new Hours(Math.round(value * multiplier / Hours._multiplier));
    }
    public Days getDays() {
        return new Days(Math.round(value * multiplier / Days._multiplier));
    }
    public Weeks getWeeks() {
        return new Weeks(Math.round(value * multiplier / Weeks._multiplier));
    }
    public Months getMonths() {
        return new Months(Math.round(value * multiplier / Months._multiplier));
    }
    public Years getYears() {
        return new Years(Math.round(value * multiplier / Years._multiplier));
    }
    ;
    /*
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
    }*/

    public String toString() {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(1);
        try {
            List<Class> cs = new ArrayList<>();
            Collections.addAll(cs,Time.class.getDeclaredClasses());
            cs.sort((x, y) -> {
                try {
                    double r = (double) (x.getDeclaredField("_multiplier").get(null));
                    r /= (double) (y.getDeclaredField("_multiplier").get(null));
                    return (int) Math.round(r);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return 0;
            });
            for (int i = 0; i < cs.size(); i++) {
                Class c = cs.get(i);
                double v = value * multiplier / (double) c.getDeclaredField("_multiplier").get(null);
                if (v < 1) {
                    if (i > 0) {
                        c = cs.get(i - 1);
                        v = value * multiplier / (double) c.getDeclaredField("_multiplier").get(null);
                    }
                    return format.format(v) + " " + c.getDeclaredField("_symbol").get(null);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return format.format(value) + " " + symbol;
    }

    public static class NanoSeconds extends Time {
        final static String _symbol = "ns";
        final static double _multiplier = Math.pow(10,-9);

        public NanoSeconds(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public NanoSeconds getNanoSeconds() {
            return this;
        }
    }

    public static class MicroSeconds extends Time {
        final static String _symbol = "µs";
        final static double _multiplier = Math.pow(10,-6);

        public MicroSeconds(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public MicroSeconds getMicroSeconds() {
            return this;
        }
    }

    public static class MilliSeconds extends Time {
        final static String _symbol = "ms";
        final static double _multiplier = Math.pow(10,-3);

        public MilliSeconds(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public MilliSeconds getMilliSeconds() {
            return this;
        }
    }

    public static class CentiSeconds extends Time {
        final static String _symbol = "cs";
        final static double _multiplier = Math.pow(10,-2);

        public CentiSeconds(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public CentiSeconds getCentiSeconds() {
            return this;
        }
    }

    public static class DeciSeconds extends Time {
        final static String _symbol = "ds";
        final static double _multiplier = Math.pow(10,-1);

        public DeciSeconds(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public DeciSeconds getDeciSeconds() {
            return this;
        }
    }

    public static class Seconds extends Time {
        final static String _symbol = "s";
        final static double _multiplier = 1d;

        public Seconds(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public Seconds getSeconds() {
            return this;
        }
    }

    public static class Minutes extends Time {
        final static String _symbol = "m";
        final static double _multiplier = 60d;

        public Minutes(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public Minutes getMinutes() {
            return this;
        }
    }

    public static class Hours extends Time {
        final static String _symbol = "h";
        final static double _multiplier = Minutes._multiplier*60d;

        public Hours(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public Hours getHours() {
            return this;
        }
    }

    public static class Days extends Time {
        final static String _symbol = "d";
        final static double _multiplier = Hours._multiplier*24d;

        public Days(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public Days getDays() {
            return this;
        }
    }

    public static class Weeks extends Time {
        final static String _symbol = "w";
        final static double _multiplier = Days._multiplier*7d;

        public Weeks(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public Weeks getWeeks() {
            return this;
        }
    }

    public static class Months extends Time {
        final static String _symbol = "mth";
        final static double _multiplier = Years._multiplier/12d;

        public Months(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public Months getMonths() {
            return this;
        }
    }

    public static class Years extends Time {
        final static String _symbol = "y";
        final static double _multiplier = Days._multiplier*365.25d;

        public Years(double val) {
            this.value = val;
            this.symbol = _symbol;
            this.multiplier = _multiplier;
        }

        @Override
        public Years getYears() {
            return this;
        }
    }
}