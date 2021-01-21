package Extras;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class ExtraScanner {
    public static double nextDouble(Scanner scanner){
        while (true){
            try {
                return scanner.nextDouble();
            } catch (Exception e){
                continue;
            }
        }
    }

    public static float nextFloat(Scanner scanner){
        while (true){
            try {
                return scanner.nextFloat();
            } catch (Exception e){
                continue;
            }
        }
    }

    public static long nextLong(Scanner scanner){
        while (true){
            try {
                return scanner.nextLong();
            } catch (Exception e){
                continue;
            }
        }
    }

    public static int nextInt(Scanner scanner){
        while (true){
            try {
                return scanner.nextInt();
            } catch (Exception e){
                continue;
            }
        }
    }

    public static short nextShort(Scanner scanner){
        while (true){
            try {
                return scanner.nextShort();
            } catch (Exception e){
                continue;
            }
        }
    }

    public static char nextChar(Scanner scanner){
        while (true){
            try {
                return scanner.nextLine().charAt(0);
            } catch (Exception e){
                continue;
            }
        }
    }

    public static byte nextByte(Scanner scanner){
        while (true){
            try {
                return scanner.nextByte();
            } catch (Exception e){
                continue;
            }
        }
    }

    public static boolean nextBool(Scanner scanner){
        while (true){
            try {
                return scanner.nextBoolean();
            } catch (Exception e){
                continue;
            }
        }
    }

    public static boolean nextYesNo(Scanner scanner){
        while (true){
            try {
                String r = scanner.nextLine().toLowerCase();
                if (r.equals("y")){
                    return true;
                } else if (r.equals("n")){
                    return false;
                }
            } catch (Exception e){
                continue;
            }
        }
    }

    public static <T>T nextClass(Scanner scanner, Class<T> c) throws Exception {
        if (c.equals(Double.class) || c.equals(double.class)){
            return (T)Double.valueOf(nextDouble(scanner));
        } else if (c.equals(Float.class) || c.equals(float.class)){
            return (T)Float.valueOf(nextFloat(scanner));
        } else if (c.equals(Long.class) || c.equals(long.class)){
            return (T)Long.valueOf(nextLong(scanner));
        } else if (c.equals(Integer.class) || c.equals(int.class)){
            return (T)Integer.valueOf(nextInt(scanner));
        } else if (c.equals(Short.class) || c.equals(short.class)){
            return (T)Short.valueOf(nextShort(scanner));
        } else if (c.equals(Character.class) || c.equals(char.class)){
            return (T)Character.valueOf(nextChar(scanner));
        } else if (c.equals(Byte.class) || c.equals(byte.class)){
            return (T)Byte.valueOf(nextByte(scanner));
        } else if (c.equals(Boolean.class) || c.equals(boolean.class)) {
            return (T) Boolean.valueOf(nextBool(scanner));
        } else if (c.equals(String.class)) {
            return (T)scanner.nextLine();
        } else {
            List<Constructor> constructors = new ArrayList<>();
            Collections.addAll(constructors,c.getDeclaredConstructors());
            if (constructors.stream().anyMatch(x -> Arrays.stream(x.getParameterTypes()).allMatch(y -> y.isPrimitive()))){
                constructors.removeIf(x -> !Arrays.stream(x.getParameterTypes()).allMatch(y -> y.isPrimitive()));
            }
            constructors.sort((x,y) -> x.getParameterCount() - y.getParameterCount());
            Constructor<T> cnstr = constructors.get(0);
            System.out.println("Constructor: "+cnstr);
            return nextConstr(scanner,cnstr);
        }
    }
    public static <T>T nextConstr(Scanner scanner, Constructor<T> c) throws Exception  {
        Parameter[] params = c.getParameters();
        Class[] paramsClass = c.getParameterTypes();
        if (params.length <= 0){
            return null;
        }
        Object[] vals = new Object[params.length];
        for (int i=0;i<params.length;i++){
            System.out.println(params[i]);
            vals[i] = nextClass(scanner,paramsClass[i]);
        }
        return c.newInstance(vals);
    }

    public static <T>T nextOfThese (Scanner scanner, boolean caseSensitive, T... these){
        T r = null;
        do {
            String in = scanner.nextLine();
            if (!caseSensitive){
                in = in.toLowerCase();
            }
            for (int i=0;i<these.length;i++){
                String name = these[i].toString();
                if (!caseSensitive){
                    name = name.toLowerCase();
                }
                if (in.equals(name)){
                    r = these[i];
                    break;
                }
            }
            if (r != null){
                break;
            }
        } while (true);
        return r;
    }
}
