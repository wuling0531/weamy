package com.waemy.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 上午8:08 和时间相关的方法集合
 */
public class DateUtil {
    
    public static long getAge(Date birthday) {
        if (birthday == null) {
            return 0;
        }
        Date date = new Date();
        long day = (date.getTime() - birthday.getTime()) / (24 * 60 * 60 * 1000) + 1;
        String year = new java.text.DecimalFormat("#.00").format(day / 365f);
        Double d_year = Double.parseDouble(year);
        return Math.round(d_year);
    }
    
    // 获取某个时间是周几
    public static String getDayOfWeek(Date date) {
        if (null == date) {
            return "";
        }
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        String weekDay_str = "";
        int weekDay = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        // 排序是周日，周一，周二，周三，周四，周五，周六，所以需要做一个简单的转换
        switch (weekDay) {
            case 1:
                weekDay_str = "周日";
                break;
            case 2:
                weekDay_str = "周一";
                break;
            case 3:
                weekDay_str = "周二";
                break;
            case 4:
                weekDay_str = "周三";
                break;
            case 5:
                weekDay_str = "周四";
                break;
            case 6:
                weekDay_str = "周五";
                break;
            case 7:
                weekDay_str = "周六";
                break;
        }
        return weekDay_str;
    }
    
    /**
     * 取格式化时间
     * @param day
     * @return
     */
    public static String getDayStr(Date day) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy年MM月dd日");
        return myFormatter.format(day);
    }
    
    /**
     * 取当前时间 格式自定义
     * @param day
     * @param dateFormat
     * @return
     */
    public static String getDayStr(Date day, String dateFormat) {
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat(dateFormat);
            return myFormatter.format(day);
        } catch (Exception e) {
            //
        }
        return "";
    }
    
    /**
     * 取当前时间 格式自定义 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDateStr() {
        Date day = new Date();
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        try {
            
            SimpleDateFormat myFormatter = new SimpleDateFormat(dateFormat);
            return myFormatter.format(day);
        } catch (Exception e) {
            //
        }
        return "";
    }
    
    /**
     * 取当前时间 格式自定义 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateFromString(Date day, String dateFormat) {
        // String dateFormat = "yyyy-MM-dd HH:HH:ss";
        try {
            
            SimpleDateFormat myFormatter = new SimpleDateFormat(dateFormat);
            return myFormatter.format(day);
        } catch (Exception e) {
            //
        }
        return "";
    }
    
    public static Date getDateFromString(String dateValueString, String dataFormatString) {
        Date d = null;
        try {
            DateFormat df = new SimpleDateFormat(dataFormatString);
            d = df.parse(dateValueString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
    
    /**
     * 取当前时间 格式自定义
     * @return
     */
    public static String getCurrentDateStr(String dateFormat) {
        Date day = new Date();
        try {
            
            SimpleDateFormat myFormatter = new SimpleDateFormat(dateFormat);
            return myFormatter.format(day);
        } catch (Exception e) {
            //
        }
        return "";
    }
    
    /**
     * 取当前时间 格式自定义 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getCurrentDate() {
        Date day = new Date();
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        try {
            
            SimpleDateFormat myFormatter = new SimpleDateFormat(dateFormat);
            return myFormatter.parse(myFormatter.format(day));
        } catch (Exception e) {
            //
        }
        return null;
    }
    
    /**
     * 获取日期格式化为yyyy-MM-dd的当前Date值
     * @return
     * @author wuling
     */
    public static Date getCurrentDateOnlyYMD() {
        Date day = new Date();
        String dateFormat = "yyyy-MM-dd";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat(dateFormat);
            return myFormatter.parse(myFormatter.format(day));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 判断时间是否在时间段内
     * @param date 当前时间 yyyy-MM-dd HH:mm:ss
     * @param strDateBegin 开始时间 00:00:00
     * @param strDateEnd 结束时间 00:05:00
     * @return
     */
    public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        
        Long strDateLong = Long.parseLong(strDate.replaceAll("-", "").trim());
        Long strDateBeginLong = Long.parseLong(strDateBegin.replaceAll("-", "").trim());
        Long strDateEndLong = Long.parseLong(strDateEnd.replaceAll("-", "").trim());
        
        if (strDateLong >= strDateBeginLong && strDateLong <= strDateEndLong)
            return true;
        else
            return false;
    }
    
    /**
     * date <= strDateBegin 返回 true else 返回 false
     * @param date
     * @param strDateBegin
     * @return
     */
    public static boolean compareDate(Date date, String strDateBegin) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        
        Long strDateLong = Long.parseLong(strDate.replaceAll("-", "").trim());
        Long strDateBeginLong = Long.parseLong(strDateBegin.replaceAll("-", "").trim());
        
        if (strDateLong <= strDateBeginLong)
            return true;
        else
            return false;
    }
    
    /**
     * 只需要比日期
     * @param date
     * @param strDateBegin
     * @param strDateEnd
     * @return
     */
    public static boolean isInDate(Date date, Date strDateBegin, Date strDateEnd) {
        Long strDateLong = date.getTime();
        Long strDateBeginLong = strDateBegin.getTime();
        Long strDateEndLong = strDateEnd.getTime();
        if (strDateLong >= strDateBeginLong && strDateLong <= strDateEndLong)
            return true;
        else
            return false;
    }
    
    /**
     * @param date
     * @param type 为‘y’|‘M’|‘d’|‘H’|‘m’|‘s’等
     * @return
     * @author wuling
     */
    public static int getDateOfSingleEle(Date date, String type) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int resultStr = 0;
        if ("y".equals(type)) {
            resultStr = cal.get(Calendar.YEAR);// 年
        } else if ("M".equals(type)) {
            resultStr = cal.get(Calendar.MONTH) + 1;// 月
        } else if ("d".equals(type)) {
            resultStr = cal.get(Calendar.DATE);// 日
        } else if ("H".equals(type)) {
            resultStr = cal.get(Calendar.HOUR_OF_DAY);// 24制-小时
        } else if ("m".equals(type)) {
            resultStr = cal.get(Calendar.MINUTE);// 分钟
        } else if ("s".equals(type)) {
            resultStr = cal.get(Calendar.SECOND);// 秒
        }
        return resultStr;
    }
    
    /**
     * @return
     * @author wuling
     */
    public static int getDateIntervalForSecond(Date beginDate, Date targetDate) {
        long intervalLong = beginDate.getTime() - targetDate.getTime();
        return (int) (intervalLong / 1000);
    }
    
    /**
     * 指定时间(Date)，减少指定的几天的时间
     * @param crtDate
     * @param i
     * @return
     * @author wuling
     */
    public static Date reduceDayByDate(Date crtDate, int intervalDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(crtDate);
        calendar.add(Calendar.DAY_OF_YEAR, -intervalDay);
        return calendar.getTime();
    }

    public static Date getDateAddDays(Date crtDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(crtDate);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}
