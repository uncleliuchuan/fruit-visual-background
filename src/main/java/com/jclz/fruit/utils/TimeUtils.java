package com.jclz.fruit.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 */
public class TimeUtils {
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat yMdHms = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 获取yyyyMMddHHmmss格式
     *
     * @return
     */
    public static String getTime() {
        return yMdHms.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取指定年-月的开始结束时间
     *
     * @param year
     * @param month
     * @return
     */
    public static String getYearMonthBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
        Date date = Date.from(zonedDateTime.toInstant());
        return yMdHms.format(date);
    }

    /**
     * 获取指定年-月的结束时间
     *
     * @param year
     * @param month
     * @return
     */
    public static String getYearMonthEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        Date date = Date.from(zonedDateTime.toInstant());
        return yMdHms.format(date);
    }

    /**
     * 日期格式转换
     *
     * @param time
     * @return
     */
    public static String dateFormatConversion(String time) {
        Date date = null;
        try {
            date = yMdHms.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar day = Calendar.getInstance();
        day.setTime(date);

        return sdfTime.format(day.getTime());
    }

    /**
     * 获取传入时间的当天开始时间
     *
     * @param time
     * @return
     */
    public static String getDayStartTime(String time) {
        Date date = null;
        try {
            date = sdfDay.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return yMdHms.format(day.getTime());
    }

    /**
     * 获取传入时间的当天结束时间
     *
     * @param time
     * @return
     */
    public static String getDayEndTime(String time) {
        Date date = null;
        try {
            date = sdfDay.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        day.set(Calendar.MILLISECOND, 999);
        return yMdHms.format(day.getTime());
    }

    /**
     * yyyyMMddHHmmss
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidYMDHMSDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * yyyy-MM-dd
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidyyyyMMddDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    //JAVA获取某段时间内每天的日期（String类型，格式为："2018-06-16"）
    public static List<String> findDaysStr(String begintTime, String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(begintTime);
            dEnd = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //存放每一天日期String对象的daysStrList
        List<String> daysStrList = new ArrayList<String>();
        //放入开始的那一天日期String
        daysStrList.add(sdf.format(dBegin));

        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);

        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);

        // 判断循环此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，给定的日历字段增加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            String dayStr = sdf.format(calBegin.getTime());
            daysStrList.add(dayStr);
        }

        //打印测试数据
        //System.out.println("#####################："+daysStrList);

        return daysStrList;
    }

    /**
     * 获取本月的第一天
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        //设置年份
//        cal.set(Calendar.YEAR, year);
        //设置月份
//        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
