package edu.tjrac.swant.baselib.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wpc on 2016/10/11.
 */

public class TimeUtils {

    public static final String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public static final String[] week_abb = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static final String[] week_CN = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final String[] week_CN_abb = {"日", "一", "二", "三", "四", "五", "六"};

    public static final String[] week_cn = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};


//    static Time t = new Time();
//    static Calendar c = getInstance();

    public static final String NO_BLANK = "yyyyMMddHHmmss";
    public static final String CHINESE_COLON = "yyyy年MM月dd日 HH:mm:ss";
    public static final String BLANK_COLON = "yyyy-MM-dd HH:mm:ss";
    public static final String MONTH_DAY_NO_ZERO = "yyyy-M-d HH:mm:ss";

    public static final String HOUR_MIN = "HH:mm";
    public static final String HOUR_MIN_SEC = "HH:mm:ss";

    public static String getTimeWithFormat(String str) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(date);
    }

    public static String getTimeWithFormat(Long date, String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(new Date(date));
    }

    public static String getTimeWithFormat(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(BLANK_COLON);
        return sdf.format(new Date(date));
    }

    public static Date getCNDate(String time) {
        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat(BLANK_COLON);
//            ParsePosition pos = new ParsePosition(8);
            date = format.parse(time);
        } catch (Exception e) {
e.printStackTrace();
        }
//        SimpleDateFormat sdf = new SimpleDateFormat(BLANK_COLON);
        return date;
    }

    public static String getTimeWithFormat(Date date, String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(date);
    }
//    public static void LogTime() {
//        t.setToNow();
//        Log.i("Time", t.year + "-" + t.month + "-" + t.monthDay);
//        Log.i("Calendar", c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH));
//        Log.i("Date", getTimeWithFormat(NO_BLANK));
//    }

    public static String getSelectDaysString(boolean[] select) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < select.length; i++) {
            if (select[i]) {
                if (buffer.length() > 0) {
                    buffer.append(",");
                }
                buffer.append(week_cn[i]);

            }
        }
        return buffer.toString();
    }
}
