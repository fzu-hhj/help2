package fzu.hhj.help2.Util;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class TimeUtil {
    final static private int MILLIS_PER_YEAR = 366*24*60*60;
    final static private int MILLIS_PER_MONTH = 30*24*60*60;
    final static private int MILLIS_PER_DAY = 24*60*60;
    final static private int MILLIS_PER_HOUR = 60*60;
    final static private int MILLIS_PER_MINUTE = 60;

    /**
     * 时间转化
     * @param m 时间
     * @return 10天前
     */
    public static String getTime(Date m){
        long ms = m.getTime();
        long second,minutes, hours, days,month,years;
        long timeNow = System.currentTimeMillis();
        long d = (timeNow - ms)/1000;
        years = Math.round(d / MILLIS_PER_YEAR);
        month = Math.round(d / MILLIS_PER_MONTH);
        days = Math.round(d / MILLIS_PER_DAY);
        hours = Math.round(d / MILLIS_PER_HOUR);
        minutes = Math.round(d / MILLIS_PER_MINUTE);
        second = Math.round(d);
        if (years > 0) {
            return years + "年前";
        } else if (month > 0 && years == 0) {
            return days + "月前";
        } else if (days > 0 && month == 0) {
            return days + "天前";
        } else if (hours > 0 && days == 0) {
            return hours + "小时前";
        } else if (minutes > 0 && hours == 0) {
            return minutes + "分钟前";
        } else if (second >= 0 && minutes == 0) {
            return "刚刚";
        } else {
            return ("数据库时间超过了当前时间！！");
        }

    }



    /**
     * 时间转换
     * @param data
     * @return 2020-12-28 23:56:31
     */
    public static String getTime1(Date data) {
        String sDate = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        try {
            Date date = sdf1.parse(sdf1.format(data));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sDate = sdf.format(date);
        } catch (ParseException e) {
            System.out.println("时间转换失败");
        }
        return sDate;
    }

    /**
     * 增加时间
     * @param m 现在的封禁时间
     * @param time 要增加的时间“1”表示24h，“7”表示7d，“30”表示30d，“-1”表示永久
     * @return 结果
     */
    public static Date addTime(Date m, Integer time){
        long ms = m.getTime();
        if(time > 0){
            ms += MILLIS_PER_DAY*time*1000;
        }
        else {
            ms += MILLIS_PER_YEAR*100*1000;
        }
        m.setTime(ms);
        return m;
    }
}
