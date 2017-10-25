/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sogrey.sogreyframe.utils;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 名称：AbDateUtil.java 描述：日期处理类.
 *
 * @author Sogrey
 * @date 2015年6月18日
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /** 时间日期格式化到年月日时分秒. */
    public static final String dateFormatYMDHMS="yyyy-MM-dd HH:mm:ss";

    /** 时间日期格式化到年月日. */
    public static final String dateFormatYMD="yyyy-MM-dd";

    /** 时间日期格式化到年月. */
    public static final String dateFormatYM="yyyy-MM";

    /** 时间日期格式化到年月日时分. */
    public static final String dateFormatYMDHM="yyyy-MM-dd HH:mm";

    /** 时间日期格式化到月日. */
    public static final String dateFormatMD="MM/dd";

    /** 时分秒. */
    public static final String dateFormatHMS="HH:mm:ss";

    /** 时分. */
    public static final String dateFormatHM="HH:mm";

    /** 上午. */
    public static final String AM="AM";

    /** 下午. */
    public static final String PM="PM";

    /**
     * Base ISO 8601 Date format yyyyMMdd i.e., 20021225 for the 25th day of
     * December in the year 2002
     */
    public static final String ISO_DATE_FORMAT="yyyyMMdd";

    /**
     * Expanded ISO 8601 Date format yyyy-MM-dd i.e., 2002-12-25 for the 25th
     * day of December in the year 2002
     */
    public static final String ISO_EXPANDED_DATE_FORMAT="yyyy-MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String DATETIME_PATTERN         ="yyyy-MM-dd HH:mm:ss";
    /**
     * HH:mm:ss
     */
    public static String ISO_EXPANDED_TIME_FORMAT ="HH:mm:ss";
    /**
     * HH:mm
     */
    public static String ISO_EXPANDED_TIME_FORMAT2="HH:mm";

    /**
     * yyyy年MM月dd日
     */
    public static final String CHINESE_EXPANDED_DATE_FORMAT="yyyy年MM月dd日";

    /**
     * Default lenient setting for getDate.
     */
    private static boolean LENIENT_DATE=false;

    /**
     * 暂时不用
     *
     * @param JD
     *
     * @return
     */
    protected static final float normalizedJulian(float JD) {

        float f=Math.round(JD+0.5f)-0.5f;

        return f;
    }

    /**
     * 浮点值转换成日期格式<br>
     * 暂时不用 Returns the Date from a julian. The Julian date will be converted to
     * noon GMT, such that it matches the nearest half-Long (i.e., a julian date
     * of 1.4 gets changed to 1.5, and 0.9 gets changed to 0.5.)
     *
     * @param JD
     *         the Julian date
     *
     * @return the Gregorian date
     */
    public static final Date toDate(float JD) {

		/*
         * To convert a Julian Day Number to a Gregorian date, assume that it is
		 * for 0 hours, Greenwich time (so that it ends in 0.5). Do the
		 * following calculations, again dropping the fractional part of all
		 * multiplicatons and divisions. Note: This method will not give dates
		 * accurately on the Gregorian Proleptic Calendar, i.e., the calendar
		 * you get by extending the Gregorian calendar backwards to years
		 * earlier than 1582. using the Gregorian leap year rules. In
		 * particular, the method fails if Y<400.
		 */
        float Z    =(normalizedJulian(JD))+0.5f;
        float W    =(int)((Z-1867216.25f)/36524.25f);
        float X    =(int)(W/4f);
        float A    =Z+1+W-X;
        float B    =A+1524;
        float C    =(int)((B-122.1)/365.25);
        float D    =(int)(365.25f*C);
        float E    =(int)((B-D)/30.6001);
        float F    =(int)(30.6001f*E);
        int   day  =(int)(B-D-F);
        int   month=(int)(E-1);

        if (month>12) {
            month=month-12;
        }

        int year=(int)(C-4715); // (if Month is January or February) or
        // C-4716 (otherwise)

        if (month>2) {
            year--;
        }

        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1); // damn 0 offsets
        c.set(Calendar.DATE,day);

        return c.getTime();
    }

    /**
     * Returns the days between two dates. Positive values indicate that the
     * second date is after the first, and negative values indicate, well, the
     * opposite. Relying on specific times is problematic.
     *
     * @param early
     *         the "first date"
     * @param later
     *         the "second date"
     *
     * @return the days between the two dates
     */
    public static final int daysBetween(Date early,Date later) {
        GregorianCalendar calst=new GregorianCalendar();
        GregorianCalendar caled=new GregorianCalendar();
        calst.setTime(early);
        caled.setTime(later);
        // 设置时间为0时
        calst.set(GregorianCalendar.HOUR_OF_DAY,0);
        calst.set(GregorianCalendar.MINUTE,0);
        calst.set(GregorianCalendar.SECOND,0);
        caled.set(GregorianCalendar.HOUR_OF_DAY,0);
        caled.set(GregorianCalendar.MINUTE,0);
        caled.set(GregorianCalendar.SECOND,0);

        // 得到两个日期相差的天数
        int days=(
                         (int)(caled.getTime().getTime()/1000)-(int)(
                                 calst
                                         .getTime().getTime()/1000
                         )
                 )/3600/24;
        return days;
    }

    /**
     * Returns the days between two dates. Positive values indicate that the
     * second date is after the first, and negative values indicate, well, the
     * opposite.
     *
     * @param early
     * @param later
     *
     * @return the days between two dates.
     */
    public static final int daysBetween(Calendar early,Calendar later) {

        return (int)(toJulian(later)-toJulian(early));
    }

    /**
     * Return a Julian date based on the input parameter. This is based from
     * calculations found at <a
     * href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day
     * Calculations (Gregorian Calendar)</a>, provided by Bill Jeffrys.
     *
     * @param c
     *         a calendar instance
     *
     * @return the julian day number
     */
    public static final float toJulian(Calendar c) {

        int   Y =c.get(Calendar.YEAR);
        int   M =c.get(Calendar.MONTH);
        int   D =c.get(Calendar.DATE);
        int   A =Y/100;
        int   B =A/4;
        int   C =2-A+B;
        float E =(int)(365.25f*(Y+4716));
        float F =(int)(30.6001f*(M+1));
        float JD=C+D+E+F-1524.5f;

        return JD;
    }

    /**
     * 暂时不用 Return a Julian date based on the input parameter. This is based
     * from calculations found at <a
     * href="http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html">Julian Day
     * Calculations (Gregorian Calendar)</a>, provided by Bill Jeffrys.
     *
     * @param date
     *
     * @return the julian day number
     */
    public static final float toJulian(Date date) {

        Calendar c=Calendar.getInstance();
        c.setTime(date);

        return toJulian(c);
    }

    /**
     * 日期增加
     *
     * @param isoString
     *         日期字符串
     * @param fmt
     *         格式
     * @param field
     *         年/月/日 Calendar.YEAR/Calendar.MONTH/Calendar.DATE
     * @param amount
     *         增加数量
     *
     * @return
     *
     * @throws ParseException
     */
    public static final String dateIncrease(
            String isoString,String fmt,
            int field,int amount
    ) {

        try {
            Calendar cal=GregorianCalendar.getInstance();
            cal.setTime(stringToDate(isoString,fmt,true));
            cal.add(field,amount);

            return dateToString(cal.getTime(),fmt);

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Time Field Rolling function. Rolls (up/down) a single unit of time on the
     * given time field.
     *
     * @param isoString
     * @param field
     *         the time field.
     * @param up
     *         Indicates if rolling up or rolling down the field value.
     * @param fmt
     *         use formating char's
     *
     * @throws ParseException
     *         if an unknown field value is given.
     */
    public static final String roll(
            String isoString,String fmt,int field,
            boolean up
    ) throws ParseException {

        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(stringToDate(isoString,fmt));
        cal.roll(field,up);

        return dateToString(cal.getTime(),fmt);
    }

    /**
     * Time Field Rolling function. Rolls (up/down) a single unit of time on the
     * given time field.
     *
     * @param isoString
     * @param field
     *         the time field.
     * @param up
     *         Indicates if rolling up or rolling down the field value.
     *
     * @throws ParseException
     *         if an unknown field value is given.
     */
    public static final String roll(String isoString,int field,boolean up)
    throws ParseException {

        return roll(isoString,DATETIME_PATTERN,field,up);
    }

    /**
     * 字符串转换为日期java.util.Date
     *
     * @param dateText
     *         字符串
     * @param format
     *         日期格式
     * @param lenient
     *         日期越界标志
     *
     * @return
     */
    public static Date stringToDate(
            String dateText,String format,
            boolean lenient
    ) {

        if (dateText==null) {

            return null;
        }

        DateFormat df=null;

        try {

            if (format==null) {
                df=new SimpleDateFormat();
            } else {
                df=new SimpleDateFormat(format);
            }

            // setLenient avoids allowing dates like 9/32/2001
            // which would otherwise parse to 10/2/2001
            df.setLenient(false);

            return df.parse(dateText);
        } catch (ParseException e) {

            return null;
        }
    }

    /**
     * 字符串转换为日期java.util.Date
     *
     * @param dateString
     *         字符串
     * @param format
     *         日期格式
     *
     * @return
     */
    public static Date stringToDate(String dateString,String format) {

        return stringToDate(dateString,format,LENIENT_DATE);
    }

    /**
     * 字符串转换为日期java.util.Date
     *
     * @param dateString
     *         字符串
     */
    public static Date stringToDate(String dateString) {

        return stringToDate(dateString,ISO_EXPANDED_DATE_FORMAT,LENIENT_DATE);
    }

    // 获得本季度
    public static String getThisSeasonTime(int month) {
        int array[][]={{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
        int season   =1;
        if (month>=1&&month<=3) {
            season=1;
        }
        if (month>=4&&month<=6) {
            season=2;
        }
        if (month>=7&&month<=9) {
            season=3;
        }
        if (month>=10&&month<=12) {
            season=4;
        }
        int              start_month=array[season-1][0];
        int              end_month  =array[season-1][2];
        Date             date       =new Date();
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy");
        String           years      =dateFormat.format(date);
        int              years_value=Integer.parseInt(years);
        int              start_days =1;
        int              end_days   =getLastDayOfMonth(years_value,end_month);
        String seasonDate=years_value+"-"+start_month+"-"+start_days
                          +" ";
        return seasonDate;
    }

    private static int getLastDayOfMonth(int year,int month) {
        if (month==1||month==3||month==5||month==7||month==8
            ||month==10||month==12) {
            return 31;
        }
        if (month==4||month==6||month==9||month==11) {
            return 30;
        }
        if (month==2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 根据时间变量返回时间字符串
     *
     * @param pattern
     *         时间字符串样式
     * @param date
     *         时间变量
     *
     * @return 返回时间字符串
     */
    public static String dateToString(Date date,String pattern) {
        if (date==null) {
            return null;
        }
        try {
            SimpleDateFormat sfDate=new SimpleDateFormat(pattern);
            sfDate.setLenient(false);
            return sfDate.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    // 倒计时;
    public static String dateToString1(long time,String pattern) {

        try {
            Date date=new Date();
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            Date date2=new Date(date.getTime()+time);
            date=date2;

            SimpleDateFormat sfDate=new SimpleDateFormat(pattern);
            sfDate.setLenient(false);
            return sfDate.format(date);
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * String 日期格式化;
     *
     * @param date
     * @param pattern
     *
     * @return
     *
     * @author Sogrey 2015年3月19日
     */
    public static String dateFormat(String date,String pattern) {
        String _date="";
        try {
            Long date3=Long.parseLong(date);
            _date=dateFormat(date3,pattern);
        } catch (Exception e) {
            _date="";
        }
        return _date;
    }

    /**
     * long 日期格式化;
     *
     * @param date
     * @param pattern
     *
     * @return
     *
     * @author Sogrey 2015年3月19日
     */
    public static String dateFormat(long date,String pattern) {
        String _date="";
        try {
            Date             date2     =new Date(date);
            SimpleDateFormat dateFormat=new SimpleDateFormat(pattern);
            _date=dateFormat.format(date2);
        } catch (Exception e) {
            _date="";
        }
        return _date;
    }

    /**
     * @param date
     * @param pattern
     *
     * @return
     *
     * @deprecated
     */
    public static String dateToStringEnd(Date date,String pattern) {
        return DateUtil.dateToString(date,DateUtil.ISO_EXPANDED_DATE_FORMAT)
               +" 23:59:59.000";
    }

    /**
     * @param date
     * @param pattern
     *
     * @return
     *
     * @deprecated
     */
    public static String dateToStringBegin(Date date,String pattern) {
        return DateUtil.dateToString(date,DateUtil.ISO_EXPANDED_DATE_FORMAT)
               +" 00:00:00.000";
    }

    /**
     * 根据时间变量返回时间字符串 yyyy-MM-dd
     *
     * @param date
     *
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date,ISO_EXPANDED_DATE_FORMAT);
    }

    /**
     * 返回当前时间
     *
     * @return 返回当前时间
     */
    public static Date getCurrentDateTime() {
        Calendar calNow=Calendar.getInstance();
        Date     dtNow =calNow.getTime();

        return dtNow;
    }

    /**
     * 返回当前日期字符串
     *
     * @param pattern
     *         日期字符串样式
     *
     * @return
     */
    public static String getCurrentDateString(String pattern) {
        return dateToString(getCurrentDateTime(),pattern);
    }

    /**
     * 返回当前日期字符串 yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDateString() {
        return dateToString(getCurrentDateTime(),ISO_EXPANDED_DATE_FORMAT);
    }

    /**
     * 返回当前日期+时间字符串 yyyy-MM-dd hh:mm:ss
     *
     * @param date
     *
     * @return
     */
    public static String dateToStringWithTime(Date date) {

        return dateToString(date,DATETIME_PATTERN);
    }

    /**
     * 日期增加-按日增加
     *
     * @param date
     * @param days
     *
     * @return java.util.Date
     */
    public static Date dateIncreaseByDay(Date date,int days) {

        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,days);

        return cal.getTime();
    }

    /**
     * 日期增加-按月增加
     *
     * @param date
     * @param mnt
     *
     * @return java.util.Date
     */
    public static Date dateIncreaseByMonth(Date date,int mnt) {

        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,mnt);

        return cal.getTime();
    }

    /**
     * 日期增加-按年增加
     *
     * @param date
     * @param mnt
     *
     * @return java.util.Date
     */
    public static Date dateIncreaseByYear(Date date,int mnt) {

        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR,mnt);

        return cal.getTime();
    }

    /**
     * 日期增加
     *
     * @param date
     *         日期字符串 yyyy-MM-dd
     * @param days
     *
     * @return 日期字符串 yyyy-MM-dd
     */
    public static String dateIncreaseByDay(String date,int days) {
        return dateIncreaseByDay(date,ISO_DATE_FORMAT,days);
    }

    /**
     * 日期增加
     *
     * @param date
     *         日期字符串
     * @param fmt
     *         日期格式
     * @param days
     *
     * @return
     */
    public static String dateIncreaseByDay(String date,String fmt,int days) {
        return dateIncrease(date,fmt,Calendar.DATE,days);
    }

    /**
     * 取得当前日期所在月的最后一天
     *
     * @param yearM
     *
     * @return
     */
    public static Date getLastDayInMonth(String yearM) {
        String   yymmdd=yearM+"01";
        Date     date  =DateUtil.stringToDate(yymmdd,ISO_DATE_FORMAT);
        Calendar cal   =GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,1);
        cal.add(Calendar.DATE,-1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,23);
        cal.set(GregorianCalendar.MINUTE,59);
        cal.set(GregorianCalendar.SECOND,59);
        cal.set(GregorianCalendar.MILLISECOND,0);
        return cal.getTime();
    }

    /**
     * 取得当前日期所在月的最后一天
     *
     * @param date
     *
     * @return
     */
    public static Date getLastDayInMonth(Date date) {
        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(stringToDate(dateToString(date,"yyyy-MM")+"-01"));
        cal.add(Calendar.MONTH,1);
        cal.add(Calendar.DATE,-1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,23);
        cal.set(GregorianCalendar.MINUTE,59);
        cal.set(GregorianCalendar.SECOND,59);
        cal.set(GregorianCalendar.MILLISECOND,0);
        return cal.getTime();
    }

    /**
     * 取得当前日期在一个月的第几天
     *
     * @param date
     *
     * @return
     */
    public static int getDaysInMonth(Date date) {
        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(date);

        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得当前日期在月的最后一天。即总天数
     *
     * @param date
     *
     * @return
     */
    public static int getAllDaysInMonth(Date date) {
        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(date);

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得当前日期所在月的第一天
     *
     * @param yearM
     *
     * @return
     */
    public static Date getFirstDayInMonth(String yearM) {
        String   yymmdd=yearM+"01";
        Date     date  =DateUtil.stringToDate(yymmdd,ISO_DATE_FORMAT);
        Calendar cal   =GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE,1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        return cal.getTime();
    }

    /**
     * 取得当前日期所在月的第一天
     *
     * @param date
     *
     * @return
     */
    public static Date getFirstDayInMonth(Date date) {
        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE,1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        cal.set(GregorianCalendar.MILLISECOND,0);
        return cal.getTime();
    }

    /**
     * 取得当前日期所在月的第一天。
     *
     * @param year
     *         年份
     * @param month
     *         月份
     *
     * @return
     *
     * @user : WuZheng
     * @date : 2012-7-18
     */
    public static Date getFirstDayInMonth(Integer year,Integer month) {
        Calendar cal=Calendar.getInstance();

        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        return cal.getTime();
    }

    /**
     * 取得当前日期所在月的第一天
     *
     * @param date
     *
     * @return
     */
    public static String getYearMonth(Date date) {
        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(date);

        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);

        String lastMonth=dateToString(cal.getTime(),ISO_DATE_FORMAT);

        return lastMonth.substring(0,lastMonth.length()-2);
    }

    /**
     * 取得当前月的上个月。
     *
     * @return
     */
    public static String getLastMonthInToday() {
        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DATE,1);
        cal.add(Calendar.MONTH,-1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);

        String lastMonth=dateToString(cal.getTime(),ISO_DATE_FORMAT);

        return lastMonth.substring(0,lastMonth.length()-2);
    }

    /**
     * 取得指定月的上个月。格式为：yyyyMM
     *
     * @return
     */
    public static String getLastMonth(String yearM) {
        String   yymmdd=yearM+"01";
        Date     date  =DateUtil.stringToDate(yymmdd,ISO_DATE_FORMAT);
        Calendar cal   =GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE,1);
        cal.add(Calendar.MONTH,-1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);

        String lastMonth=dateToString(cal.getTime(),ISO_DATE_FORMAT);

        return lastMonth.substring(0,lastMonth.length()-2);
    }

    // 计算当月;
    public static int getDefaultDay() {
        Calendar lastDate=Calendar.getInstance();
        int      month   =(lastDate.get(Calendar.MONTH))+1;
        return month;
    }

    // 返回年初;
    public static String getDefaultYear() {

        Calendar lastDate=Calendar.getInstance();
        int      year    =lastDate.get(Calendar.YEAR);
        return ""+year+"-01-01";
    }

    // 返回年初;
    public static int getDefaultYears() {

        Calendar lastDate=Calendar.getInstance();
        int      year    =lastDate.get(Calendar.YEAR);
        return year;
    }

    // 获取本周周一;
    public static String getMondayOfThisWeek() {
        Calendar c          =Calendar.getInstance();
        int      day_of_week=c.get(Calendar.DAY_OF_WEEK)-1;
        if (day_of_week==0)
            day_of_week=7;
        c.add(Calendar.DATE,-day_of_week+1);
        SimpleDateFormat df2=new SimpleDateFormat(ISO_EXPANDED_DATE_FORMAT);
        return df2.format(c.getTime());
    }

    /**
     * 取得指定月的下个月。 格式为：yyyy-MM
     *
     * @return
     */
    public static String getAfter_Month(String year_M) {
        String   yymmdd=year_M+"-01";
        Date     date  =DateUtil.stringToDate(yymmdd,ISO_EXPANDED_DATE_FORMAT);
        Calendar cal   =GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE,1);
        cal.add(Calendar.MONTH,1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);

        String lastMonth=dateToString(cal.getTime(),ISO_EXPANDED_DATE_FORMAT);

        return lastMonth.substring(0,lastMonth.length()-3);
    }

    /**
     * 取得指定月的上个月。格式为：yyyy-MM
     *
     * @return
     */
    public static String getLast_Month(String year_M) {
        String   yymmdd=year_M+"-01";
        Date     date  =DateUtil.stringToDate(yymmdd,ISO_EXPANDED_DATE_FORMAT);
        Calendar cal   =GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE,1);
        cal.add(Calendar.MONTH,-1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);

        String lastMonth=dateToString(cal.getTime(),ISO_EXPANDED_DATE_FORMAT);

        return lastMonth.substring(0,lastMonth.length()-3);
    }

    /**
     * 取得指定月的前第十二个月。
     *
     * @return
     */
    public static String getBeforTwelveMonth(String yearM) {
        String   yymmdd=yearM+"01";
        Date     date  =DateUtil.stringToDate(yymmdd,ISO_DATE_FORMAT);
        Calendar cal   =GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE,1);
        cal.add(Calendar.MONTH,-12);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);

        String lastMonth=dateToString(cal.getTime(),ISO_DATE_FORMAT);

        return lastMonth.substring(0,lastMonth.length()-2);
    }

    /**
     * 取得指定月的前第六个月(前半年)。
     *
     * @return
     */
    public static String getBeforSixMonth(String yearM) {
        String   yymmdd=yearM+"01";
        Date     date  =DateUtil.stringToDate(yymmdd,ISO_DATE_FORMAT);
        Calendar cal   =GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE,1);
        cal.add(Calendar.MONTH,-6);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);
        cal.set(GregorianCalendar.HOUR_OF_DAY,0);
        cal.set(GregorianCalendar.MINUTE,0);
        cal.set(GregorianCalendar.SECOND,0);

        String lastMonth=dateToString(cal.getTime(),ISO_DATE_FORMAT);

        return lastMonth.substring(0,lastMonth.length()-2);
    }

    /**
     * return the current month and last month.
     *
     * @return the List is never null.
     */
    public static List<String> getCurrentAndLastMonth() {
        List<String> yearMs=new ArrayList<String>();
        Calendar     cal   =GregorianCalendar.getInstance();
        cal.setTime(new Date());

        String yearM=dateToString(cal.getTime(),ISO_DATE_FORMAT);
        yearM=yearM.substring(0,yearM.length()-2);
        yearMs.add(yearM);

        cal.set(Calendar.DATE,1);
        cal.add(Calendar.MONTH,-1);
        yearM=dateToString(cal.getTime(),ISO_DATE_FORMAT);
        yearM=yearM.substring(0,yearM.length()-2);
        yearMs.add(yearM);

        return yearMs;
    }

    public static String getCurentMonth() {
        Calendar cal=GregorianCalendar.getInstance();
        cal.setTime(new Date());

        String yearM=dateToString(cal.getTime(),ISO_DATE_FORMAT);
        yearM=yearM.substring(0,yearM.length()-2);

        return yearM;
    }

    /**
     * 日期字符串格式转换
     *
     * @param src
     *         日期字符串
     * @param srcfmt
     *         源日期格式
     * @param desfmt
     *         目标日期格式
     *
     * @return
     */
    public static String stringToString(String src,String srcfmt,String desfmt) {
        return dateToString(stringToDate(src,srcfmt),desfmt);
    }

    public static String getCurrentYearWeek() {
        Calendar cal=Calendar.getInstance();
        String rtn=String.format("%4d%2d",cal.get(Calendar.YEAR),
                                 cal.get(Calendar.WEEK_OF_YEAR)
        );
        return rtn;
    }

    /**
     * 两个时间对象的比较
     *
     * @param first
     *         the "first date"
     * @param second
     *         the "second date"
     *
     * @return 0: first = second <0: first < second >0: first > second
     */
    public static final long compare(Date first,Date second) {
        if (first==null&&second==null) {
            return 0;
        } else if (first==null) {
            return -1;
        } else if (second==null) {
            return 1;
        }

        GregorianCalendar calst=new GregorianCalendar();
        GregorianCalendar caled=new GregorianCalendar();
        calst.setTime(first);
        caled.setTime(second);
        // 设置时间为0时
        calst.set(GregorianCalendar.HOUR_OF_DAY,0);
        calst.set(GregorianCalendar.MINUTE,0);
        calst.set(GregorianCalendar.SECOND,0);
        caled.set(GregorianCalendar.HOUR_OF_DAY,0);
        caled.set(GregorianCalendar.MINUTE,0);
        caled.set(GregorianCalendar.SECOND,0);

        long start=calst.get(GregorianCalendar.YEAR)*10000
                   +calst.get(GregorianCalendar.MONTH)*100
                   +calst.get(GregorianCalendar.DAY_OF_MONTH);
        long end=caled.get(GregorianCalendar.YEAR)*10000
                 +caled.get(GregorianCalendar.MONTH)*100
                 +caled.get(GregorianCalendar.DAY_OF_MONTH);
        return start-end;
    }

    /**
     * 两个日期的小时差
     *
     * @param startDate
     * @param endDate
     *
     * @return
     */
    public static final BigDecimal hoursBetween(
            Date startDate,Date endDate,
            int scale
    ) {
        Calendar sc=Calendar.getInstance();
        sc.setTime(startDate);

        Calendar ec=Calendar.getInstance();
        ec.setTime(endDate);

        Long       date  =ec.getTimeInMillis()-sc.getTimeInMillis();
        BigDecimal result=new BigDecimal(date.toString());
        BigDecimal unit  =new BigDecimal("3600000");

        result=result.divide(unit,scale,BigDecimal.ROUND_HALF_EVEN);

        return result;
    }

    /**
     * 日期是否为空
     *
     * @param date
     *
     * @return
     */

    public static final boolean isEmpty(Date date) {
        return (date==null) ? true : date.toString().length()<1;
    }

    /**
     * 夏令时判断
     *
     * @return
     */
    public static boolean isDaylightSavingTime() {
        Date nowDate=new Date();

        Calendar c=Calendar.getInstance();
        c.set(c.get(Calendar.YEAR),c.get(Calendar.OCTOBER),
              c.getActualMinimum(Calendar.DAY_OF_MONTH)
        );

        Calendar c2=Calendar.getInstance();
        c2.set(c2.get(Calendar.YEAR),c2.get(Calendar.MAY),
               c2.getActualMinimum(Calendar.DAY_OF_MONTH)
        );

        return nowDate.after(c2.getTime())&&nowDate.before(c.getTime());
    }

    /**
     * 夏令时/非夏令时 中午休息开始时间
     *
     * @param date
     *
     * @return
     */
    public static Date getNoonRestStartTime(Date date) {
        Calendar c=Calendar.getInstance();
        c.setTime(date);

        if (isDaylightSavingTime()) {
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                  c.get(Calendar.DAY_OF_MONTH),12,00,00
            );
        } else {
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                  c.get(Calendar.DAY_OF_MONTH),12,00,00
            );
        }

        return c.getTime();
    }

    /**
     * 夏令时/非夏令时 下午上班开始时间
     *
     * @param date
     *
     * @return
     */
    public static Date getAfterNoonWorkStartTime(Date date) {
        Calendar c=Calendar.getInstance();
        c.setTime(date);

        if (isDaylightSavingTime()) {
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                  c.get(Calendar.DAY_OF_MONTH),14,00,00
            );
        } else {
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                  c.get(Calendar.DAY_OF_MONTH),13,30,00
            );
        }

        return c.getTime();
    }

    /**
     * 夏令时/非夏令时 午休小时数
     *
     * @return
     */
    public static BigDecimal getNoonRestHours() {
        if (isDaylightSavingTime()) {
            return new BigDecimal("2");
        } else {
            return new BigDecimal("1.5");
        }
    }

    /**
     * 夏令时/非夏令时 下午下班时间
     *
     * @param date
     *
     * @return
     */
    public static Date getAfterNoonWorkEndTime(Date date) {
        Calendar c=Calendar.getInstance();
        c.setTime(date);

        if (isDaylightSavingTime()) {
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                  c.get(Calendar.DAY_OF_MONTH),18,00,00
            );
        } else {
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                  c.get(Calendar.DAY_OF_MONTH),17,30,00
            );
        }

        return c.getTime();
    }

    /**
     * 夏令时/非夏令时 上午上班开始时间
     *
     * @param date
     *
     * @return
     */
    public static Date getNoonWorkStartTime(Date date) {
        Calendar c=Calendar.getInstance();
        c.setTime(date);

        if (isDaylightSavingTime()) {
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                  c.get(Calendar.DAY_OF_MONTH),8,00,00
            );
        } else {
            c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),
                  c.get(Calendar.DAY_OF_MONTH),8,00,00
            );
        }

        return c.getTime();
    }

    /**
     * 时间比较 (yyyy-MM-dd) 等于0:时间相等，小于0:src日期在tgt之前,大于于0:src日期在tgt之后
     *
     * @param srcDate
     * @param tgtDate
     *
     * @return
     */
    public static long cmp(Date srcDate,Date tgtDate) {
        if (srcDate==null&&tgtDate==null) {
            return 0;
        } else if (srcDate==null) {
            return -1;
        } else if (tgtDate==null) {
            return 1;
        }

        Calendar lhs=Calendar.getInstance();
        lhs.setTime(srcDate);
        Calendar rhs=Calendar.getInstance();
        rhs.setTime(tgtDate);

        long start=lhs.get(Calendar.YEAR)*10000+lhs.get(Calendar.MONTH)
                                                *100+lhs.get(Calendar.DAY_OF_MONTH);
        long end=rhs.get(Calendar.YEAR)*10000+rhs.get(Calendar.MONTH)
                                              *100+rhs.get(Calendar.DAY_OF_MONTH);
        return start-end;

    }

    /**
     * 计算计算两个日期之间的年数
     *
     * @param early
     * @param late
     *
     * @return
     */
    public static final int yearBetween(Date early,Date late) {
        if (early==null||late==null) {
            return 0;
        }
        int years=0;
        // 计算两个日期之间的年数
        Calendar cEarly=Calendar.getInstance();
        cEarly.setTime(early);
        Calendar cLate=Calendar.getInstance();
        cLate.setTime(late);
        years=cLate.get(Calendar.YEAR)-cEarly.get(Calendar.YEAR);
        // 矫正月、日
        cEarly.add(Calendar.YEAR,years);
        if (cEarly.after(cLate)) {
            years--;
        }
        return years;
    }

    /**
     * 获得入司月份
     *
     * @param enterDate
     *
     * @return
     */
    public static Integer calculateMonthEnterInComp(Date enterDate) {
        if (enterDate==null) {
            return 0;
        }
        Calendar calEnterDate=Calendar.getInstance();
        calEnterDate.setTime(enterDate);

        Date     currentDate=new Date();
        Calendar paybaseDate=Calendar.getInstance();
        paybaseDate.setTime(currentDate);
        paybaseDate.set(Calendar.MONTH,Calendar.APRIL);

        int years=calEnterDate.get(Calendar.YEAR)
                  -paybaseDate.get(Calendar.YEAR);

        int number=12
                   *years
                   +(
                           calEnterDate.get(Calendar.MONTH)-paybaseDate
                                   .get(Calendar.MONTH)
                   );

        return number;
    }

    /**
     * 计算中间的月份
     */
    public static int monthBetween(Date from,Date to) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(to);
        int months=cal.get(Calendar.YEAR)*12+cal.get(Calendar.MONTH);

        cal.setTime(from);
        months-=cal.get(Calendar.YEAR)*12+cal.get(Calendar.MONTH);

        return months;
    }

    public static boolean isSomeMonth(Date startDate,Date endDate) {
        Calendar cal=Calendar.getInstance();

        cal.setTime(startDate);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1,0,0,0);

        Date sd=cal.getTime();

        cal.setTime(endDate);
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1,0,0,0);

        Date ed=cal.getTime();

        return cmp(sd,ed)==0;

    }

    /**
     * 返回当前月的前N个月
     *
     * @param n
     *
     * @return List<String>
     *
     * @user : wangyadong
     * @date : 2012-6-6
     */
    public static List<String> getFirstNMonths(Integer n) {
        List<String> monthList=new ArrayList<String>();
        int          i        =0;
        while (i<n) {
            if (i==0) {
                monthList.add(DateUtil.getLastMonth(DateUtil.dateToString(
                        new Date(),"yyyyMM")));
            } else {
                monthList.add(DateUtil.getLastMonth(monthList.get(i-1)));
            }
            i++;
        }
        return monthList;
    }

    /**
     * 返回指定月的前N个月
     *
     * @param date
     *         格式为yyyyMM
     * @param n
     *
     * @return List<String>
     *
     * @user : Administrator
     * @date : 2012-6-7
     */
    public static List<String> getSpecifiedFirstNMonths(String date,Integer n) {
        List<String> monthList=new ArrayList<String>();
        int          i        =0;
        while (i<n) {
            if (i==0) {
                monthList.add(DateUtil.getLastMonth(date));
            } else {
                monthList.add(DateUtil.getLastMonth(monthList.get(i-1)));
            }
            i++;
        }
        return monthList;
    }

    /**
     * 比较两个日期大小
     *
     * @param format
     *         格式
     * @param date2
     *         日期2
     * @param date1
     *         日期1
     *
     * @throws Exception
     * @user : jerry
     * @date : 2012-6-15
     */
    public static int compareDate(String date1,String date2,String format)
    throws Exception {
        try {
            DateFormat df =new SimpleDateFormat(format);
            Date       dt1=df.parse(date1);
            Date       dt2=df.parse(date2);
            if (dt1.getTime()>dt2.getTime()) {
                return 1;
            } else if (dt1.getTime()<dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    /**
     * 返回两个Date对象中表示时间较大的Date对象。
     *
     * @param dt1
     *         Date对象
     * @param dt2
     *         Date对象
     *
     * @return 较大的Date对象
     *
     * @user : WuZheng
     * @date : 2012-7-3
     */
    public static Date max(Date dt1,Date dt2) {
        return compare(dt1,dt2)>=0 ? dt1 : dt2;
    }

    /**
     * 得到Date对象所在的季度
     *
     * @param date
     *         日期
     *
     * @return
     *
     * @user : jerry
     * @date : 2012-7-30
     */
    public static int getQuarter(Date date) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int month=cal.get(Calendar.MONTH);
        return (month+3)/3;
    }

    public static String getFirstDayByMonth(String syear,String smonth) {
        Integer year =new Integer(syear);
        Integer month=new Integer(smonth);
        return year+"-"+month+"-1";
    }

    public static String getNextMonthFirstDayByMonth(String syear,String smonth) {
        Integer year     =new Integer(syear);
        Integer month    =new Integer(smonth);
        Integer nextYear =year;
        Integer nextMonth=month+1;
        if (month==12) {
            nextYear=year+1;
            nextMonth=1;
        }
        return nextYear+"-"+nextMonth+"-1";
    }

    /**
     * 取得当前日期所在月的最后一天
     *
     * @param syear
     * @param smonth
     *
     * @return
     */
    public static Calendar getLastDayByMonth(String syear,String smonth) {
        String   sdate=syear+"-"+smonth+"-1";
        Date     date =DateUtil.stringToDate(sdate,ISO_EXPANDED_DATE_FORMAT);
        Calendar cal  =GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,1);
        cal.add(Calendar.DATE,-1);
        // 设置时间为0时
        cal.set(GregorianCalendar.HOUR_OF_DAY,23);
        cal.set(GregorianCalendar.MINUTE,59);
        cal.set(GregorianCalendar.SECOND,59);
        cal.set(GregorianCalendar.MILLISECOND,0);
        return cal;
    }

    /**
     * 取得两个日期之间的双休天数
     *
     * @param beginDate
     * @param endDate
     *
     * @return
     */
    public static long getNonWorkingDays(String beginDate,String endDate) {
        Date st=DateUtil.stringToDate(beginDate,ISO_EXPANDED_DATE_FORMAT);
        Date et=DateUtil.stringToDate(endDate,ISO_EXPANDED_DATE_FORMAT);

        Calendar cal=Calendar.getInstance();
        cal.setTime(st);
        long mod=daysBetween(st,et);
        int  n  =cal.get(Calendar.DAY_OF_WEEK);// 开始日期是周几
        long cnt=mod/7;// 几个整周
        cnt=cnt*2; // 整周的双休天数
        long yushu=mod%7;
        if (n+yushu>7)
            cnt=cnt+2;// 过了周六
        if (n+yushu==7)
            cnt++;// 正好是周六
        return cnt;
    }

    /**
     * 取得两个日期之间的双休天数
     *
     * @param stdt
     * @param etdt
     *
     * @return
     */
    public static long getNonWorkingDays(Date stdt,Date etdt) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(stdt);
        long mod=daysBetween(stdt,etdt);
        int  n  =cal.get(Calendar.DAY_OF_WEEK);// 开始日期是周几
        long cnt=mod/7;// 几个整周
        cnt=cnt*2; // 整周的双休天数
        long yushu=mod%7;
        if (n+yushu>7)
            cnt=cnt+2;// 过了周六
        if (n+yushu==7)
            cnt++;// 正好是周六
        return cnt;
    }

    /**
     * 取得指定月份的所有日期
     *
     * @param year
     * @param month
     *
     * @return
     *
     * @user : jerry
     * @date : 2013-7-5
     */
    public static List<Date> getDaysOfMonth(Integer year,Integer month) {
        List<Date> result=new ArrayList<Date>();
        Calendar   cal   =Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        int days=getAllDaysInMonth(cal.getTime());

        for (int i=1;i<=days;i++) {
            cal.set(Calendar.DAY_OF_MONTH,i);
            result.add(cal.getTime());
        }

        return result;
    }

    /**
     * 根据时间格式化时间
     *
     * @param date
     *
     * @return
     *
     * @user : wangyd
     * @date : 2014-8-14
     */
    public static String format(String date) {
        if (date==null) {
            return "";
        }
        String newdate="";
        Date   now    =new Date();
        BigDecimal c=DateUtil.hoursBetween(DateUtil.stringToDate(
                date,
                "yyyy-MM-dd HH:mm:ss"
        ),DateUtil.stringToDate(
                DateUtil.dateToString(now,"yyyy-MM-dd HH:mm:ss"),
                "yyyy-MM-dd HH:mm:ss"
        ),2);
        double minute=c.doubleValue()*60;
        if (minute==0) {
            newdate="刚刚";
        } else if (minute>=0&&minute<=1) {
            newdate="1分钟前";
        } else if (minute>1&&minute<=2) {
            newdate="2分钟前";
        } else if (minute>2&&minute<=3) {
            newdate="3分钟前";
        } else if (minute>3&&minute<=4) {
            newdate="4分钟前";
        } else if (minute>4&&minute<=5) {
            newdate="5分钟前";
        } else if (minute>5&&minute<=10) {
            newdate="10分钟前";
        } else if (minute>10&&minute<=15) {
            newdate="15分钟前";
        } else if (minute>15&&minute<=20) {
            newdate="20分钟前";
        } else if (minute>20&&minute<=30) {
            newdate="半小时前";
        } else if (minute>30&&minute<=60) {
            newdate="1小时前";
        } else if (minute>60&&minute<=360) {
            newdate=DateUtil
                    .dateToString(
                            DateUtil.stringToDate(date,"yyyy-MM-dd HH:mm:ss"),
                            "HH:mm"
                    );
        } else {
            newdate=DateUtil.dateToString(
                    DateUtil.stringToDate(date,"yyyy-MM-dd HH:mm:ss"),
                    "MM-dd HH:mm"
            );
        }
        return newdate;
    }

    /**
     * 转换时间-具体到上午下午几点几分
     *
     * @param date
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年6月18日
     */
    public static String getTimeDiffDesc(Date date) {

        if (date==null) {
            return null;
        }

        String   strDesc    =null;
        Calendar curCalendar=Calendar.getInstance();
        Date     curDate    =new Date();
        curCalendar.setTime(curDate);
        Calendar thenCalendar=Calendar.getInstance();
        thenCalendar.setTime(date);

        String[] weekDays={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        int      w       =thenCalendar.get(Calendar.DAY_OF_WEEK)-1;
        if (w<0)
            w=0;
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar         current=Calendar.getInstance();
        Calendar         today  =Calendar.getInstance(); // 今天
        today.set(Calendar.YEAR,current.get(Calendar.YEAR));
        today.set(Calendar.MONTH,current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE,0);
        today.set(Calendar.SECOND,0);
        Date datetoday=today.getTime();
        System.out.println(format.format(datetoday));

        Calendar yesterday=Calendar.getInstance(); // 昨天
        yesterday.setTime(curDate);
        yesterday.add(Calendar.DATE,-1);
        yesterday.set(Calendar.HOUR_OF_DAY,0);
        yesterday.set(Calendar.MINUTE,0);
        yesterday.set(Calendar.SECOND,0);
        Date dateyestoday=yesterday.getTime();
        System.out.println(format.format(dateyestoday));

        Calendar sevendaysago=Calendar.getInstance(); // 7天
        sevendaysago.setTime(curDate);
        sevendaysago.add(Calendar.DATE,-7);
        sevendaysago.set(Calendar.HOUR_OF_DAY,0);
        sevendaysago.set(Calendar.MINUTE,0);
        sevendaysago.set(Calendar.SECOND,0);
        Date datesevenago=sevendaysago.getTime();
        System.out.println(format.format(datesevenago));
        /*
         * Date tasktime = yesterday.getTime(); SimpleDateFormat df=new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 * System.out.println(df.format(tasktime));
		 */

        int    thenMonth=thenCalendar.get(Calendar.MONTH);
        int    thenDay  =thenCalendar.get(Calendar.DAY_OF_MONTH);
        int    h        =thenCalendar.get(Calendar.HOUR_OF_DAY);
        int    m        =thenCalendar.get(Calendar.MINUTE);
        String sh       ="", sm="";
        if (h<10)
            sh="0";

        if (m<10)
            sm="0";
        if (thenCalendar.after(today))// today
        {
            if (h<6) {
                strDesc="凌晨 "+sh+h+" : "+sm+m;
            } else if (h<12) {
                strDesc="上午 "+sh+h+" : "+sm+m;
            } else if (h<13) {
                strDesc="下午 "+h+" : "+sm+m;
            } else if (h<19) {
                strDesc="下午 "+(h-12)+" : "+sm+m;
            } else {
                strDesc="晚上 "+(h-12)+" : "+sm+m;
            }
        } else if (thenCalendar.before(today)&&thenCalendar.after(yesterday)) {// yestoday
            System.out.println("yestoday");
            if (h<6) {
                strDesc="昨天凌晨 "+sh+h+" : "+sm+m;
            } else if (h<12) {
                strDesc="昨天上午 "+sh+h+" : "+sm+m;
            } else if (h<13) {
                strDesc="昨天下午 "+h+" : "+sm+m;
            } else if (h<19) {
                strDesc="昨天下午 "+(h-12)+" : "+sm+m;
            } else {
                strDesc="昨天晚上 "+(h-12)+" : "+sm+m;
            }
        } else if (thenCalendar.before(yesterday)
                   &&thenCalendar.after(sevendaysago)) {// 2 ~ 7days ago
            System.out.println("2~7");
            if (h<6) {
                strDesc=weekDays[w]+"凌晨 "+sh+h+" : "+sm+m;
            } else if (h<12) {
                strDesc=weekDays[w]+"上午 "+sh+h+" : "+sm+m;
            } else if (h<13) {
                strDesc=weekDays[w]+"下午 "+h+" : "+sm+m;
            } else if (h<19) {
                strDesc=weekDays[w]+"下午 "+(h-12)+" : "+sm+m;
            } else {
                strDesc=weekDays[w]+"晚上 "+(h-12)+" : "+sm+m;
            }
        } else {
            System.out.println("7~");
            if (h<6) {
                strDesc=(thenMonth+1)+"月"+thenDay+"日"+"凌晨 "+sh
                        +h+" : "+sm+m;
            } else if (h<12) {
                strDesc=(thenMonth+1)+"月"+thenDay+"日"+"上午 "+sh
                        +h+" : "+sm+m;
            } else if (h<13) {
                strDesc=(thenMonth+1)+"月"+thenDay+"日"+"下午 "+h
                        +" : "+sm+m;
            } else if (h<19) {
                strDesc=(thenMonth+1)+"月"+thenDay+"日"+"下午 "
                        +(h-12)+" : "+sm+m;
            } else {
                strDesc=(thenMonth+1)+"月"+thenDay+"日"+"晚上 "
                        +(h-12)+" : "+sm+m;
            }
        }
        System.out.println(strDesc);
        return strDesc;
    }

    /**
     * 计算相隔时间-适用于发送动态微博等显示时间
     *
     * @param date
     *
     * @return
     *
     * @author Sogrey
     * @date 2015年6月18日
     */
    public static String getTimeDisplay(Date date) {

        if (date==null) {
            return null;
        }

        String   strDesc    =null;
        Calendar curCalendar=Calendar.getInstance();
        Date     curDate    =new Date();
        curCalendar.setTime(curDate);
        Calendar thenCalendar=Calendar.getInstance();
        thenCalendar.setTime(date);

        String[] weekDays={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        int      w       =thenCalendar.get(Calendar.DAY_OF_WEEK)-1;
        if (w<0)
            w=0;
        SimpleDateFormat format=new
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time=dateToString(date,dateFormatHM);//mm:ss

        Calendar current=Calendar.getInstance();
        Calendar today  =Calendar.getInstance(); // 今天
        today.set(Calendar.YEAR,current.get(Calendar.YEAR));
        today.set(Calendar.MONTH,current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE,0);
        today.set(Calendar.SECOND,0);

        Calendar yesterday=Calendar.getInstance(); // 昨天
        yesterday.setTime(curDate);
        yesterday.add(Calendar.DATE,-1);
        yesterday.set(Calendar.HOUR_OF_DAY,0);
        yesterday.set(Calendar.MINUTE,0);
        yesterday.set(Calendar.SECOND,0);

        Calendar sevendaysago=Calendar.getInstance(); // 7天
        sevendaysago.setTime(curDate);
        sevendaysago.add(Calendar.DATE,-7);
        sevendaysago.set(Calendar.HOUR_OF_DAY,0);
        sevendaysago.set(Calendar.MINUTE,0);
        sevendaysago.set(Calendar.SECOND,0);

        long timediff =(curDate.getTime()-date.getTime());
        int  theYear  =thenCalendar.get(Calendar.YEAR);
        int  thenMonth=thenCalendar.get(Calendar.MONTH);
        int  thenDay  =thenCalendar.get(Calendar.DAY_OF_MONTH);
        if (thenCalendar.after(today))// today
        {
            if (timediff<1*60*1000) {
                strDesc="刚刚";
            } else if (timediff<60*60*1000) {
                strDesc=(timediff/60/1000)+"分钟之前";
            } else {
                strDesc=(timediff/3600/1000)+"小时之前";
            }
        } else if (thenCalendar.before(today)&&thenCalendar.after(yesterday)) {// yestoday
            strDesc="昨天 "+time;
        } else if (thenCalendar.before(yesterday)
                   &&thenCalendar.after(sevendaysago)) {// 2 ~ 7days ago
            strDesc=weekDays[w]+" "+time;
        } else {
            strDesc=""+(theYear-2000)+"-"+(thenMonth+1)+"-"
                    +thenDay+" "+time;
        }
        return strDesc;
    }

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate
     *         String形式的日期时间
     * @param format
     *         格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     *
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate,String format) {
        SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
        Date             date             =null;
        try {
            date=mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date
     *         日期时间
     * @param calendarField
     *         Calendar属性，对应offset的值，
     *         如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset
     *         偏移(值大于0,表示+,值小于0,表示－)
     *
     * @return Date 偏移之后的日期时间
     */
    public Date getDateByOffset(Date date,int calendarField,int offset) {
        Calendar c=new GregorianCalendar();
        try {
            c.setTime(date);
            c.add(calendarField,offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 描述：获取指定日期时间的字符串(可偏移).
     *
     * @param strDate
     *         String形式的日期时间
     * @param format
     *         格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField
     *         Calendar属性，对应offset的值，
     *         如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset
     *         偏移(值大于0,表示+,值小于0,表示－)
     *
     * @return String String类型的日期时间
     */
    public static String getStringByOffset(
            String strDate,String format,
            int calendarField,int offset
    ) {
        String mDateTime=null;
        try {
            Calendar         c                =new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            c.add(calendarField,offset);
            mDateTime=mSimpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：Date类型转化为String类型(可偏移).
     *
     * @param date
     *         the date
     * @param format
     *         the format
     * @param calendarField
     *         the calendar field
     * @param offset
     *         the offset
     *
     * @return String String类型日期时间
     */
    public static String getStringByOffset(
            Date date,String format,
            int calendarField,int offset
    ) {
        String strDate=null;
        try {
            Calendar         c                =new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
            c.setTime(date);
            c.add(calendarField,offset);
            strDate=mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date
     *         the date
     * @param format
     *         the format
     *
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date,String format) {
        SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
        String           strDate          =null;
        try {
            strDate=mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate
     *         String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format
     *         输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     *
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate,String format) {
        String mDateTime=null;
        try {
            Calendar c=new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(
                    dateFormatYMDHMS);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2=new SimpleDateFormat(format);
            mDateTime=mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：获取milliseconds表示的日期时间的字符串.
     *
     * @param milliseconds
     *         the milliseconds
     * @param format
     *         格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     *
     * @return String 日期时间字符串
     */
    public static String getStringByFormat(long milliseconds,String format) {
        String thisDateTime=null;
        try {
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
            thisDateTime=mSimpleDateFormat.format(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format
     *         格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     *
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDate(String format) {
        LogUtil.d(DateUtil.class,"getCurrentDate:"+format);
        String curDateTime=null;
        try {
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
            Calendar         c                =new GregorianCalendar();
            curDateTime=mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;

    }

    /**
     * 描述：获取表示当前日期时间的字符串(可偏移).
     *
     * @param format
     *         格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField
     *         Calendar属性，对应offset的值，
     *         如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset
     *         偏移(值大于0,表示+,值小于0,表示－)
     *
     * @return String String类型的日期时间
     */
    public static String getCurrentDateByOffset(
            String format,
            int calendarField,int offset
    ) {
        String mDateTime=null;
        try {
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
            Calendar         c                =new GregorianCalendar();
            c.add(calendarField,offset);
            mDateTime=mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;

    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param milliseconds1
     *         the milliseconds1
     * @param milliseconds2
     *         the milliseconds2
     *
     * @return int 所差的天数
     */
    public static int getOffectDay(long milliseconds1,long milliseconds2) {
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTimeInMillis(milliseconds1);
        Calendar calendar2=Calendar.getInstance();
        calendar2.setTimeInMillis(milliseconds2);
        // 先判断是否同年
        int y1     =calendar1.get(Calendar.YEAR);
        int y2     =calendar2.get(Calendar.YEAR);
        int d1     =calendar1.get(Calendar.DAY_OF_YEAR);
        int d2     =calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays=0;
        int day    =0;
        if (y1-y2>0) {
            maxDays=calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day=d1-d2+maxDays;
        } else if (y1-y2<0) {
            maxDays=calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day=d1-d2-maxDays;
        } else {
            day=d1-d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1
     *         第一个时间的毫秒表示
     * @param date2
     *         第二个时间的毫秒表示
     *
     * @return int 所差的小时数
     */
    public static int getOffectHour(long date1,long date2) {
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2=Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 =calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 =calendar2.get(Calendar.HOUR_OF_DAY);
        int h  =0;
        int day=getOffectDay(date1,date2);
        h=h1-h2+day*24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1
     *         第一个时间的毫秒表示
     * @param date2
     *         第二个时间的毫秒表示
     *
     * @return int 所差的分钟数
     */
    public static int getOffectMinutes(long date1,long date2) {
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2=Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1=calendar1.get(Calendar.MINUTE);
        int m2=calendar2.get(Calendar.MINUTE);
        int h =getOffectHour(date1,date2);
        int m =0;
        m=m1-m2+h*60;
        return m;
    }

    /**
     * 描述：获取本周一.
     *
     * @param format
     *         the format
     *
     * @return String String类型日期时间
     */
    public static String getFirstDayOfWeek(String format) {
        return getDayOfWeek(format,Calendar.MONDAY);
    }

    /**
     * 描述：获取本周日.
     *
     * @param format
     *         the format
     *
     * @return String String类型日期时间
     */
    public static String getLastDayOfWeek(String format) {
        return getDayOfWeek(format,Calendar.SUNDAY);
    }

    /**
     * 描述：获取本周的某一天.
     *
     * @param format
     *         the format
     * @param calendarField
     *         the calendar field
     *
     * @return String String类型日期时间
     */
    private static String getDayOfWeek(String format,int calendarField) {
        String strDate=null;
        try {
            Calendar         c                =new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
            int              week             =c.get(Calendar.DAY_OF_WEEK);
            if (week==calendarField) {
                strDate=mSimpleDateFormat.format(c.getTime());
            } else {
                int offectDay=calendarField-week;
                if (calendarField==Calendar.SUNDAY) {
                    offectDay=7-Math.abs(offectDay);
                }
                c.add(Calendar.DATE,offectDay);
                strDate=mSimpleDateFormat.format(c.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format
     *         the format
     *
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format) {
        String strDate=null;
        try {
            Calendar         c                =new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
            // 当前月的第一天
            c.set(GregorianCalendar.DAY_OF_MONTH,1);
            strDate=mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;

    }

    /**
     * 描述：获取本月最后一天.
     *
     * @param format
     *         the format
     *
     * @return String String类型日期时间
     */
    public static String getLastDayOfMonth(String format) {
        String strDate=null;
        try {
            Calendar         c                =new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat(format);
            // 当前月的最后一天
            c.set(Calendar.DATE,1);
            c.roll(Calendar.DATE,-1);
            strDate=mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取表示当前日期的0点时间毫秒数.
     *
     * @return the first time of day
     */
    public static long getFirstTimeOfDay() {
        Date date=null;
        try {
            String currentDate=getCurrentDate(dateFormatYMD);
            date=getDateByFormat(currentDate+" 00:00:00",dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 描述：获取表示当前日期24点时间毫秒数.
     *
     * @return the last time of day
     */
    public static long getLastTimeOfDay() {
        Date date=null;
        try {
            String currentDate=getCurrentDate(dateFormatYMD);
            date=getDateByFormat(currentDate+" 24:00:00",dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
        }
        return -1;
    }

    /**
     * 描述：判断是否是闰年()
     * <p/>
     * (year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year
     *         年代（如2012）
     *
     * @return boolean 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        return (year%4==0&&year%100!=0)||(year%400==0);
    }

    /**
     * 描述：根据时间返回格式化后的时间的描述. 小于1小时显示多少分钟前 大于1小时显示今天＋实际日期，大于今天全部显示实际时间
     *
     * @param strDate
     *         the str date
     * @param outFormat
     *         the out format
     *
     * @return the string
     */
    public static String formatDateStr2Desc(String strDate,String outFormat) {

        DateFormat df=new SimpleDateFormat(dateFormatYMDHMS);
        Calendar   c1=Calendar.getInstance();
        Calendar   c2=Calendar.getInstance();
        try {
            c2.setTime(df.parse(strDate));
            c1.setTime(new Date());
            int d=getOffectDay(c1.getTimeInMillis(),c2.getTimeInMillis());
            if (d==0) {
                int h=getOffectHour(
                        c1.getTimeInMillis(),
                        c2.getTimeInMillis()
                );
                if (h>0) {
                    return "今天"+getStringByFormat(strDate,dateFormatHM);
                    // return h + "小时前";
                } else if (h<0) {
                    // return Math.abs(h) + "小时后";
                } else if (h==0) {
                    int m=getOffectMinutes(
                            c1.getTimeInMillis(),
                            c2.getTimeInMillis()
                    );
                    if (m>0) {
                        return m+"分钟前";
                    } else if (m<0) {
                        // return Math.abs(m) + "分钟后";
                    } else {
                        return "刚刚";
                    }
                }

            } else if (d>0) {
                if (d==1) {
                    // return "昨天"+getStringByFormat(strDate,outFormat);
                } else if (d==2) {
                    // return "前天"+getStringByFormat(strDate,outFormat);
                }
            } else if (d<0) {
                if (d==-1) {
                    // return "明天"+getStringByFormat(strDate,outFormat);
                } else if (d==-2) {
                    // return "后天"+getStringByFormat(strDate,outFormat);
                } else {
                    // return Math.abs(d) +
                    // "天后"+getStringByFormat(strDate,outFormat);
                }
            }

            String out=getStringByFormat(strDate,outFormat);
            if (!StrUtil.isEmpty(out)) {
                return out;
            }
        } catch (Exception e) {
        }

        return strDate;
    }

    /**
     * 取指定日期为星期几.
     *
     * @param strDate
     *         指定日期
     * @param inFormat
     *         指定日期格式
     *
     * @return String 星期几
     */
    public static String getWeekNumber(String strDate,String inFormat) {
        String     week    ="星期日";
        Calendar   calendar=new GregorianCalendar();
        DateFormat df      =new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp=calendar.get(Calendar.DAY_OF_WEEK)-1;
        switch (intTemp) {
            case 0:
                week="星期日";
                break;
            case 1:
                week="星期一";
                break;
            case 2:
                week="星期二";
                break;
            case 3:
                week="星期三";
                break;
            case 4:
                week="星期四";
                break;
            case 5:
                week="星期五";
                break;
            case 6:
                week="星期六";
                break;
        }
        return week;
    }

    /**
     * 根据给定的日期判断是否为上下午.
     *
     * @param strDate
     *         the str date
     * @param format
     *         the format
     *
     * @return the time quantum
     */
    public static String getTimeQuantum(String strDate,String format) {
        Date mDate=getDateByFormat(strDate,format);
        int  hour =mDate.getHours();
        if (hour>=12)
            return "PM";
        else
            return "AM";
    }

    /**
     * 根据给定的毫秒数算得时间的描述.
     *
     * @param milliseconds
     *         the milliseconds
     *
     * @return the time description
     */
    public static String getTimeDescription(long milliseconds) {
        if (milliseconds>1000) {
            // 大于一分
            if (milliseconds/1000/60>1) {
                long minute=milliseconds/1000/60;
                long second=milliseconds/1000%60;
                return minute+"分"+second+"秒";
            } else {
                // 显示秒
                return milliseconds/1000+"秒";
            }
        } else {
            return milliseconds+"毫秒";
        }
    }

    public static String getTimeDescription2(long milliseconds) {
        if (milliseconds>1000) {
            if (milliseconds/1000f/60/60/24>1) {
                if (milliseconds/1000f/60/60/24>2) {
                    return "";
                }
                long day=milliseconds/1000/60/60/24;
                return day+"天前";
            } else if (milliseconds/1000f/60/60>1) {
                long hour  =milliseconds/1000/60/60;
                long minute=(milliseconds-hour*3600000)/1000/60;
                return hour+"小时"+minute+"分钟前";
            } else if (milliseconds/1000f/60>1) {// 大于一分
                long minute=milliseconds/1000/60;
                return minute+"分钟前";
            } else {
                // 显示秒
                return milliseconds/1000+"秒前";
            }
        } else {
            return "1秒前";
        }
    }

    public static String getTimeDescription3(String time,String format) {
        SimpleDateFormat sim=new SimpleDateFormat(format);
        try {
            Date d1          =sim.parse(time);
            Date d2          =new Date();
            long           milliseconds=d2.getTime()-d1.getTime();
            return getTimeDescription2(milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * The main method.
     *
     * @param args
     *         the arguments
     */
    public static void main(String[] args) {
        System.out.println(formatDateStr2Desc(
                "2012-3-2 12:2:20",
                "MM月dd日  HH:mm"
        ));
    }

}
