package com.wowfilm.wechat.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    public final static String DEFAULT_Time_FORMAT = "HH:mm:ss";

    public final static String SHORT_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public final static String FULL_SEQ_FORMAT = "yyyyMMddHHmmssSSS";

    public final static String[] MULTI_FORMAT = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM" };

    public final static DateFormat DEFAULT_TIME_FORMATER = new SimpleDateFormat(DEFAULT_TIME_FORMAT);

    public final static DateFormat DEFAULT_DATE_FORMATER = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    public final static DateFormat SHORT_TIME_FORMATER = new SimpleDateFormat(SHORT_TIME_FORMAT);

    public final static String FORMAT_YYYY = "yyyy";

    public final static String FORMAT_YYYYMM = "yyyyMM";

    public final static String FORMAT_YYYYMMDD = "yyyyMMdd";

    public final static DateFormat FORMAT_YYYY_FORMATER = new SimpleDateFormat(FORMAT_YYYY);

    public final static DateFormat FORMAT_YYYYMM_FORMATER = new SimpleDateFormat(FORMAT_YYYYMM);

    public final static DateFormat FORMAT_YYYYMMDD_FORMATER = new SimpleDateFormat(FORMAT_YYYYMMDD);

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return DEFAULT_DATE_FORMATER.format(date);
    }
    
    public static String formatDate(Date date,DateFormat df) {
        if (date == null) {
            return "";
        }
        return df.format(date);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Integer formatDateToInt(Date date, String format) {
        if (date == null) {
            return null;
        }
        return Integer.valueOf(new SimpleDateFormat(format).format(date));
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return DEFAULT_TIME_FORMATER.format(date);
    }

    public static String formatShortTime(Date date) {
        if (date == null) {
            return null;
        }
        return SHORT_TIME_FORMATER.format(date);
    }



    public static Date parseDate(String date, DateFormat df) {
        if (date == null) {
            return null;
        }
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseTime(String date, DateFormat df) {
        if (date == null) {
            return null;
        }
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDate(String date) {
        return parseDate(date, DEFAULT_DATE_FORMATER);
    }

    public static Date parseTime(String date) {
        return parseTime(date, DEFAULT_TIME_FORMATER);
    }



    public static String getHumanDisplayForTimediff(Long diffMillis) {
        if (diffMillis == null) {
            return "";
        }
        long day = diffMillis / (24 * 60 * 60 * 1000);
        long hour = (diffMillis / (60 * 60 * 1000) - day * 24);
        long min = ((diffMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long se = (diffMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuilder sb = new StringBuilder();
        if (day > 0) {
            sb.append(day + "D");
        }
        DecimalFormat df = new DecimalFormat("00");
        sb.append(df.format(hour) + ":");
        sb.append(df.format(min) + ":");
        sb.append(df.format(se));
        return sb.toString();
    }

    /**
     *@Title:getDiffDay
     *@Description:获取日期相差天数
     *@param:@param beginDate  字符串类型开始日期
     *@param:@param endDate    字符串类型结束日期
     *@param:@return
     *@return:Long             日期相差天数
     *@author:谢
     *@thorws:
     */
    public static Long getDiffDay(String beginDate, String endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Long checkday = 0l;
        //开始结束相差天数
        try {
            checkday = (formatter.parse(endDate).getTime() - formatter.parse(beginDate).getTime()) / (1000 * 24 * 60 * 60);
        } catch (ParseException e) {

            e.printStackTrace();
            checkday = null;
        }
        return checkday;
    }

    /**
    *@Title:getDiffDay
    *@Description:获取日期相差天数
    *@param:@param beginDate Date类型开始日期
    *@param:@param endDate   Date类型结束日期
    *@param:@return
    *@return:Long            相差天数
    *@author: 谢
    *@thorws:
    */
    public static Long getDiffDay(Date beginDate, Date endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strBeginDate = format.format(beginDate);

        String strEndDate = format.format(endDate);
        return getDiffDay(strBeginDate, strEndDate);
    }

    /**
     * N天之后
     * @param n
     * @param date
     * @return
     */
    public static Date nDaysAfter(Integer n, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + n);
        return cal.getTime();
    }

    /**
     * N天之前
     * @param n
     * @param date
     * @return
     */
    public static Date nDaysAgo(Integer n, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - n);
        return cal.getTime();
    }

    private static Date currentDate;

    public static Date setCurrentDate(Date date) {
        if (date == null) {
            currentDate = null;
        } else {
            Date now = new Date();
            if (date.before(now)) {
                currentDate = now;
            } else {
                currentDate = date;
            }
        }
        return currentDate;
    }

    public static Date getPreviousDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
    
    public static Calendar getPreviousDay2(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar;
	}

    /**
     * 获取当前时间
     * @param date
     * @return
     * @throws ParseException 
     */
    public static Date getCurrentTime(Date date) throws ParseException{
    	return DEFAULT_TIME_FORMATER.parse(DEFAULT_TIME_FORMATER.format(date));
    }
    /**
     * 获取当前天的范围
     * @param date
     * @return
     */
    public static Date[] getCurrentDayOfTime(Date date){
    	Date[] dates = new Date[2];
		String current = DateUtils.formatDate(date);
		dates[0] = DateUtils.parseTime(current+" 00:00:00");
		dates[1] = DateUtils.parseTime(current+" 23:59:59"); 
		return dates;
    }
    /**
     * 获取当前月的范围
     * @param date
     * @return
     */
    public static Date[] getCurrentMonthOfTime(Date date){
    	Date[] dates = new Date[2];
    	Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		dates[0] = cal.getTime();
		
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dates[1] = cal.getTime();
    	return dates;
    }
    /**
     * 获取当前月的范围
     * @param date
     * @return
     */
    public static Date[] getCurrentMonthOfDate(Date date){
    	Date[] dates = new Date[2];
    	Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getMinimum(Calendar.DAY_OF_MONTH));
		dates[0] = cal.getTime();
		
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		dates[1] = cal.getTime();
    	return dates;
    }
    public static Date getCurrentDate(){
        return new Date();
    }

    public static Date getAfterSecondDate(int second) {
        Date date = getCurrentDate();
        long t = date.getTime() + second * 1000;
        return new Date(t);
    }
}
