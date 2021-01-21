package Extras;

public class ExtraClass {
    public static boolean isImplementOf(Class<?> c, Class<?> imp){
        return imp.isAssignableFrom(c);
    }
    public static <T>boolean isExtendOf(Class<? extends T> c, Class<T> imp){
        return true;
    }
}
