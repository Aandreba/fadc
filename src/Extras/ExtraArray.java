package Extras;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ExtraArray {
    public static <T>T[] fromList(List<T> list, T[] base){
        if (list.size() != base.length){
            return base;
        }
        for (int i=0;i<base.length;i++){
            base[i] = list.get(i);
        }
        return base;
    }

    public static int indexOf(long[] array, Long value){
        for (int i=0;i<array.length;i++){
            if (value.equals(array[i])){
                return i;
            }
        }
        return -1;
    }
    public static <T>int indexOf(T[] array, T value){
        for (int i=0;i<array.length;i++){
            if (array[i].equals(value)){
                return i;
            }
        }
        return -1;
    }

    public static byte[] concat(byte[]... arrays){
        int size = 0;
        for (int i=0;i<arrays.length;i++){
            size += arrays[i].length;
        }
        byte[] r = new byte[size];
        int k = 0;
        for (int i=0;i<arrays.length;i++){
            for (int j=0;j<arrays[i].length;j++){
                r[k] = arrays[i][j];
                k++;
            }
        }
        return r;
    }

    public static double[] concat(double[]... arrays){
        int size = 0;
        for (int i=0;i<arrays.length;i++){
            size += arrays[i].length;
        }
        double[] r = new double[size];
        int k = 0;
        for (int i=0;i<arrays.length;i++){
            for (int j=0;j<arrays[i].length;j++){
                r[k] = arrays[i][j];
                k++;
            }
        }
        return r;
    }
    public static double[] concat(double[] a, double... b){
        double[] r = new double[a.length+b.length];
        for (int i=0;i<a.length;i++){
            r[i] = a[i];
        }
        for (int i=0;i<b.length;i++){
            r[i+a.length] = b[i];
        }
        return r;
    }
    public static float[] concat(float[]... arrays){
        int size = 0;
        for (int i=0;i<arrays.length;i++){
            size += arrays[i].length;
        }
        float[] r = new float[size];
        int k = 0;
        for (int i=0;i<arrays.length;i++){
            for (int j=0;j<arrays[i].length;j++){
                r[k] = arrays[i][j];
                k++;
            }
        }
        return r;
    }
    public static float[] concat(float[] a, float... b){
        float[] r = new float[a.length+b.length];
        for (int i=0;i<a.length;i++){
            r[i] = a[i];
        }
        for (int i=0;i<b.length;i++){
            r[i+a.length] = b[i];
        }
        return r;
    }
    public static long[] concat(long[]... arrays){
        int size = 0;
        for (int i=0;i<arrays.length;i++){
            size += arrays[i].length;
        }
        long[] r = new long[size];
        int k = 0;
        for (int i=0;i<arrays.length;i++){
            for (int j=0;j<arrays[i].length;j++){
                r[k] = arrays[i][j];
                k++;
            }
        }
        return r;
    }
    public static long[] concat(long[] a, long... b){
        long[] r = new long[a.length+b.length];
        for (int i=0;i<a.length;i++){
            r[i] = a[i];
        }
        for (int i=0;i<b.length;i++){
            r[i+a.length] = b[i];
        }
        return r;
    }
    public static byte[] concat(byte[] a, byte[] b){
        byte[] r = new byte[a.length+b.length];
        for (int i=0;i<a.length;i++){
            r[i] = a[i];
        }
        for (int i=0;i<b.length;i++){
            r[i+a.length] = b[i];
        }
        return r;
    }
    public static <T>T[] concat (T[]... arrays){
        int size = 0;
        T[] r = null;
        for (int i=0;i<arrays.length;i++){
            size += arrays[i].length;
        }
        for (int i=0;i<arrays.length;i++){
            if (arrays[i].length > 0 && r == null){
                r = (T[]) Array.newInstance(arrays[i][0].getClass(),size);
                break;
            }
        }
        if (r == null){
            return arrays[0];
        }
        int k = 0;
        for (int i=0;i<arrays.length;i++){
            for (int j=0;j<arrays[i].length;j++){
                r[k] = arrays[i][j];
                k++;
            }
        }
        return r;
    }
    public static <T>T[] concat(T[] a, T... b){
        T[] r = null;
        if (a.length == 0 && b.length == 0){
            return a;
        } else if (a.length == 0) {
            r = (T[]) Array.newInstance(b[0].getClass(), a.length + b.length);
        } else {
            r = (T[]) Array.newInstance(a[0].getClass(), a.length + b.length);
        }
        for (int i=0;i<a.length;i++){
            r[i] = a[i];
        }
        for (int i=0;i<b.length;i++){
            r[i+a.length] = b[i];
        }
        return r;
    }

    public static <T>boolean contains(T[] array, T value){
        return indexOf(array,value) >= 0;
    }

    public static <T>T[] subArray (T[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static <T>T[] subArray (T[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }
    public static double[] subArray (double[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static double[] subArray (double[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }
    public static float[] subArray (float[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static float[] subArray (float[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }
    public static long[] subArray (long[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static long[] subArray (long[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }
    public static int[] subArray (int[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static int[] subArray (int[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }
    public static short[] subArray (short[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static short[] subArray (short[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }
    public static char[] subArray (char[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static char[] subArray (char[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }
    public static byte[] subArray (byte[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static byte[] subArray (byte[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }
    public static boolean[] subArray (boolean[] array, int from, int length) {
        return Arrays.copyOfRange(array,from,from+length);
    }
    public static boolean[] subArray (boolean[] array, int from) {
        return Arrays.copyOfRange(array,from,array.length-1);
    }

    public static double[] paddingRight (double[] array, int length, double value){
        if (length <= array.length){
            return array;
        }
        double[] r = new double[length];
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }
    public static float[] paddingRight (float[] array, int length, float value){
        if (length <= array.length){
            return array;
        }
        float[] r = new float[length];
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }
    public static long[] paddingRight (long[] array, int length, long value){
        if (length <= array.length){
            return array;
        }
        long[] r = new long[length];
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }
    public static int[] paddingRight (int[] array, int length, int value){
        if (length <= array.length){
            return array;
        }
        int[] r = new int[length];
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }
    public static short[] paddingRight (short[] array, int length, short value){
        if (length <= array.length){
            return array;
        }
        short[] r = new short[length];
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }
    public static char[] paddingRight (char[] array, int length, char value){
        if (length <= array.length){
            return array;
        }
        char[] r = new char[length];
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }
    public static byte[] paddingRight (byte[] array, int length, byte value){
        if (length <= array.length){
            return array;
        }
        byte[] r = new byte[length];
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }
    public static boolean[] paddingRight (boolean[] array, int length, boolean value){
        if (length <= array.length){
            return array;
        }
        boolean[] r = new boolean[length];
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }
    public static <T>T[] paddingRight (T[] array, int length, T value){
        if (length <= array.length){
            return array;
        }
        T[] r = (T[])Array.newInstance(value.getClass(),length);
        for (int i=array.length;i<r.length;i++){
            r[i] = value;
        }
        return r;
    }

    public static double[] toPrimitive(Double[] array){
        double r[] = new double[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static float[] toPrimitive(Float[] array){
        float r[] = new float[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static long[] toPrimitive(Long[] array){
        long r[] = new long[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static int[] toPrimitive(Integer[] array){
        int r[] = new int[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static short[] toPrimitive(Short[] array){
        short r[] = new short[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static char[] toPrimitive(Character[] array){
        char r[] = new char[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static byte[] toPrimitive(Byte[] array){
        byte r[] = new byte[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static boolean[] toPrimitive(Boolean[] array){
        boolean r[] = new boolean[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }

    public static Double[] toClass(double[] array){
        Double r[] = new Double[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static Float[] toClass(float[] array){
        Float r[] = new Float[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static Long[] toClass(long[] array){
        Long r[] = new Long[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static Integer[] toClass(int[] array){
        Integer r[] = new Integer[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static Short[] toClass(short[] array){
        Short r[] = new Short[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static Character[] toClass(char[] array){
        Character r[] = new Character[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static Byte[] toClass(byte[] array){
        Byte r[] = new Byte[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }
    public static Boolean[] toClass(boolean[] array){
        Boolean r[] = new Boolean[array.length];
        for (int i=0;i<r.length;i++){
            r[i] = array[i];
        }
        return r;
    }

    public static double[] addAll(double[] base, double[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }
    public static float[] addAll(float[] base, float[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }
    public static long[] addAll(long[] base, long[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }
    public static int[] addAll(int[] base, int[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }
    public static short[] addAll(short[] base, short[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }
    public static char[] addAll(char[] base, char[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }
    public static byte[] addAll(byte[] base, byte[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }
    public static boolean[] addAll(boolean[] base, boolean[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }
    public static <T>T[] addAll(T[] base, T[] toAdd, int index){
        for (int i=0;i<toAdd.length;i++){
            base[index+i] = toAdd[i];
        }
        return base;
    }

    public static double[] remove(double[] array, int index){
        double[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static float[] remove(float[] array, int index){
        float[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static long[] remove(long[] array, int index){
        long[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static int[] remove(int[] array, int index){
        int[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static short[] remove(short[] array, int index){
        short[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static char[] remove(char[] array, int index){
        char[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static byte[] remove(byte[] array, int index){
        byte[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static boolean[] remove(boolean[] array, int index){
        boolean[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static <T>T[] remove(T[] array, int index){
        T[] copy = ExtraArray.subArray(array.clone(),0, array.length-1);
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }
    public static <T>T[] remove(T[] array, T value){
        int index;
        while ((index=ExtraArray.indexOf(array,value)) != -1){
            ExtraArray.remove(array,index);
        }
        return array;
    }

    public static double[] fill (double value, int length){
        double[] r = new double[length];
        Arrays.fill(r,value);
        return r;
    }
    public static float[] fill (float value, int length){
        float[] r = new float[length];
        Arrays.fill(r,value);
        return r;
    }
    public static long[] fill (long value, int length){
        long[] r = new long[length];
        Arrays.fill(r,value);
        return r;
    }
    public static int[] fill (int value, int length){
        int[] r = new int[length];
        Arrays.fill(r,value);
        return r;
    }
    public static short[] fill (short value, int length){
        short[] r = new short[length];
        Arrays.fill(r,value);
        return r;
    }
    public static char[] fill (char value, int length){
        char[] r = new char[length];
        Arrays.fill(r,value);
        return r;
    }
    public static byte[] fill (byte value, int length){
        byte[] r = new byte[length];
        Arrays.fill(r,value);
        return r;
    }
    public static boolean[] fill (boolean value, int length){
        boolean[] r = new boolean[length];
        Arrays.fill(r,value);
        return r;
    }
    public static <T>T[] fill (Class value, int length){
        T[] r = (T[])Array.newInstance(value,length);
        Arrays.fill(r,null);
        return r;
    }
    public static <T>T[] fill (T value, int length){
        T[] r = (T[])Array.newInstance(value.getClass(),length);
        Arrays.fill(r,value);
        return r;
    }
}