package rakietrak.networkmock.utils;

import java.util.regex.Pattern;

public class MacUtils {

    public static String prepareMacAddress(String mac) {
        return mac.replaceAll("[-/:]", "").toUpperCase();
    }

    public static boolean isValid(String macAddress) {
        return Pattern.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$", macAddress);
    }
}
