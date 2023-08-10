package team.star.score_system.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author patrick_star
 * @version 1.0
 */
public class TimeUtil {
    /**
     * 传入日期类生成格式化的时间字符串
     *
     * @param date 日期类
     * @return 格式化的时间字符串
     */
    public static String getFormattedTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 传入日期类生成格式化的日期字符串
     *
     * @param date 日期类
     * @return 格式化的日期字符串
     */
    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String getISO8601Timestamp(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return format.format(date);
    }

    public static String getISO8601Timestamp(Long timestamp) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return format.format(timestamp);
    }

    /**
     * @Desc 传入日期字符串生成日期类
     * @param dateStr 日期字符串
     * @return 日期类
     * @throws ParseException 日期转化异常
     */
    public static Date tranStringToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateStr);
    }

    public static Date now() {
        return new Date();
    }
}