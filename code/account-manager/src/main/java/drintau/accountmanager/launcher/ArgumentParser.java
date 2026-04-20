package drintau.accountmanager.launcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {

    private static final Pattern MODE_PATTERN = Pattern.compile("^--am\\.mode=(key|web|gui)$");

    public static String parseMode(String[] args) {
        for (String arg : args) {
            Matcher matcher = MODE_PATTERN.matcher(arg);
            if (matcher.matches()) {
                return matcher.group(1);
            }
        }
        return null;
    }

}
