package team.star.score_system.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Patrick_Star
 * @version 1.1
 */
public class IntegerUtil {
    private static final Pattern PATTERN = Pattern.compile("-?[0-9]+(\\\\.[0-9]+)?");

    public static boolean equal(Integer left, Integer right) {
        if (right != null && left != null) {
            return right.intValue() == left.intValue();
        } else {
            return false;
        }
    }

    /**
     * 通过正则表达式判断字符串是否为数字
     */
    public static boolean isNumber(String str) {
        Matcher m = PATTERN.matcher(str);
        return m.matches();
    }

}
