package Extras;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

public class ExtraObject {
    public static byte[] byteArray(double value){
        return ByteBuffer.allocate(8).putDouble(value).array();
    }
    public static <T extends Double>byte[] byteArray(T value){
        return ByteBuffer.allocate(8).putDouble(value).array();
    }

    public static byte[] byteArray(float value){
        return ByteBuffer.allocate(4).putFloat(value).array();
    }
    public static <T extends Float>byte[] byteArray(T value){
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static byte[] byteArray(long value){
        return ByteBuffer.allocate(8).putLong(value).array();
    }
    public static <T extends Long>byte[] byteArray(T value){
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static byte[] byteArray(int value){
        return ByteBuffer.allocate(4).putInt(value).array();
    }
    public static <T extends Integer>byte[] byteArray(T value){
        return ByteBuffer.allocate(3).putInt(value).array();
    }

    public static byte[] byteArray(short value){
        return ByteBuffer.allocate(2).putShort(value).array();
    }
    public static <T extends Short>byte[] byteArray(T value){
        return ByteBuffer.allocate(2).putShort(value).array();
    }

    public static byte[] byteArray(char value){
        return ByteBuffer.allocate(2).putChar(value).array();
    }
    public static <T extends Character>byte[] byteArray(T value){
        return ByteBuffer.allocate(2).putChar(value).array();
    }

    public static byte[] byteArray(byte value){
        return new byte[]{ value };
    }
    public static <T extends Byte>byte[] byteArray(T value){
        return new byte[]{ value.byteValue() };
    }

    public static byte[] byteArray(boolean value){
        return new byte[]{ value ? (byte)1 : (byte)0 };
    }
    public static <T extends Boolean>byte[] byteArray(T value){
        return new byte[]{ value.booleanValue() ? (byte)1 : (byte)0 };
    }

    public static byte[] byteArray(double[] value){
        byte[] out = new byte[value.length*8];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*8);
        }
        return out;
    }
    public static <T extends Double>byte[] byteArray(T[] value){
        byte[] out = new byte[value.length*8];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*8);
        }
        return out;
    }

    public static byte[] byteArray(float[] value){
        byte[] out = new byte[value.length*4];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*4);
        }
        return out;
    }
    public static <T extends Float>byte[] byteArray(T[] value){
        byte[] out = new byte[value.length*4];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*4);
        }
        return out;
    }

    public static byte[] byteArray(long[] value){
        byte[] out = new byte[value.length*8];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*8);
        }
        return out;
    }
    public static <T extends Long>byte[] byteArray(T[] value){
        byte[] out = new byte[value.length*8];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*8);
        }
        return out;
    }

    public static byte[] byteArray(int[] value){
        byte[] out = new byte[value.length*4];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*4);
        }
        return out;
    }
    public static <T extends Integer>byte[] byteArray(T[] value){
        byte[] out = new byte[value.length*4];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*4);
        }
        return out;
    }

    public static byte[] byteArray(short[] value){
        byte[] out = new byte[value.length*2];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*2);
        }
        return out;
    }
    public static <T extends Short>byte[] byteArray(T[] value){
        byte[] out = new byte[value.length*2];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*2);
        }
        return out;
    }

    public static byte[] byteArray(char[] value){
        byte[] out = new byte[value.length*2];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*2);
        }
        return out;
    }
    public static <T extends Character>byte[] byteArray(T[] value){
        byte[] out = new byte[value.length*4];
        for (int i=0;i<value.length;i++){
            out = ExtraArray.addAll(out,ExtraObject.byteArray(value[i]),i*2);
        }
        return out;
    }

    public static byte[] byteArray(byte[] value){
        return value;
    }
    public static <T extends Byte>byte[] byteArray(T[] value){
        return ExtraArray.toPrimitive(value);
    }

    public static byte[] byteArray(boolean[] value){
        int bytes = (int)Math.ceil(value.length/8d);
        byte[] out = new byte[4+bytes];
        out = ExtraArray.addAll(out,ExtraObject.byteArray(value.length),0);

        int v = 0;
        for (int i=0;i<value.length;i++){
            if (i > 0 && i%8 == 0){
                out[4+i/8] = (byte)v;
                v = 0;
            }
            if (value[i]){
              v += Math.pow(2,i%8);
            }
        }
        out[out.length-1] = (byte)v;
        return out;
    }
    public static <T extends Boolean>byte[] byteArray(T[] value){
        int bytes = (int)Math.ceil(value.length/8d);
        byte[] out = new byte[4+bytes];
        out = ExtraArray.addAll(out,ExtraObject.byteArray(value.length),0);

        int v = 0;
        for (int i=0;i<value.length;i++){
            if (i > 0 && i%8 == 0){
                out[4+i/8] = (byte)v;
                v = 0;
            }
            if (value[i].booleanValue()){
                v += Math.pow(2,i%8);
            }
        }
        out[out.length-1] = (byte)v;
        return out;
    }

    public static double byteArrayDouble(byte[] bytes){
        return ByteBuffer.wrap(bytes).getDouble();
    }
    public static float byteArrayFloat(byte[] bytes){
        return ByteBuffer.wrap(bytes).getFloat();
    }
    public static long byteArrayLong(byte[] bytes){
        return ByteBuffer.wrap(bytes).getLong();
    }
    public static int byteArrayInt(byte[] bytes){
        return ByteBuffer.wrap(bytes).getInt();
    }
    public static short byteArrayShort(byte[] bytes){
        return ByteBuffer.wrap(bytes).getShort();
    }
    public static char byteArrayChar(byte[] bytes){
        return ByteBuffer.wrap(bytes).getChar();
    }
    public static byte byteArrayByte(byte[] bytes){
        return bytes[0];
    }
    public static boolean byteArrayBool(byte[] bytes){
        return bytes[0] == (byte)1 ? true : false;
    }
    public static boolean byteArrayBool(byte bytes){
        return bytes == (byte)1 ? true : false;
    }

    public static double[] fromByteDoubleArray(byte[] bytes){
        double[] v = new double[bytes.length/8];
        for (int i=0;i<v.length;i++){
            v[i] = byteArrayDouble(ExtraArray.subArray(bytes,i*8,8));
        }
        return v;
    }

    public static float[] fromByteFloatArray(byte[] bytes){
        float[] v = new float[bytes.length/4];
        for (int i=0;i<v.length;i++){
            v[i] = byteArrayFloat(ExtraArray.subArray(bytes,i*4,4));
        }
        return v;
    }

    public static long[] fromByteLongArray(byte[] bytes){
        long[] v = new long[bytes.length/8];
        for (int i=0;i<v.length;i++){
            v[i] = byteArrayLong(ExtraArray.subArray(bytes,i*8,8));
        }
        return v;
    }

    public static int[] fromByteIntArray(byte[] bytes){
        int[] v = new int[bytes.length/4];
        for (int i=0;i<v.length;i++){
            v[i] = byteArrayInt(ExtraArray.subArray(bytes,i*4,4));
        }
        return v;
    }

    public static short[] fromByteShortArray(byte[] bytes){
        short[] v = new short[bytes.length/2];
        for (int i=0;i<v.length;i++){
            v[i] = byteArrayShort(ExtraArray.subArray(bytes,i*2,2));
        }
        return v;
    }

    public static char[] fromByteCharArray(byte[] bytes){
        char[] v = new char[bytes.length/2];
        for (int i=0;i<v.length;i++){
            v[i] = byteArrayChar(ExtraArray.subArray(bytes,i*2,2));
        }
        return v;
    }

    public static boolean[] fromByteBoolArray(byte[] bytes){
        int length = ExtraObject.byteArrayInt(ExtraArray.subArray(bytes,0,4));
        boolean[] out = new boolean[length];
        for (int i=0;i<out.length;i++){
            out[i] = (bytes[i/8] >> i % 8) == (byte) 1;
        }
        return out;
    }
}
