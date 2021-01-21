package Extras;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraString {
    public static Matcher matcher (String value, String regex){
        return Pattern.compile(regex).matcher(value);
    }

    public static boolean contains (String value, String regex){
        return matcher(value, regex).find();
    }

    public static String replaceFirst (String value, String regex, String replacement){
        return matcher(value,regex).replaceFirst(replacement);
    }
    public static String replaceAll (String value, String regex, String replacement){
        return matcher(value,regex).replaceAll(replacement);
    }
}
