package com.test.demo.user.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * TimeUtils
 *
 * @author 519001
 * @date 2021/2/3
 */
public final class TimeUtils {
    private TimeUtils() {
    }

    public static final String PATTERN_DATE_TIME_KEBAB_SECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DATE_TIME_KEBAB = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE_TIME_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_DATE_KEBAB = "yyyy-MM-dd";
    public static final String PATTERN_DATE_SLASH  = "yyyy/MM/dd";
    public static final String PATTERN_DATE_KEBAB_MIL  = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_YEAR_MONTH = "yyyyMM";

    public static final DateTimeFormatter DEFAULT_DATE_PATTERN = ISO_LOCAL_DATE;
    public static final DateTimeFormatter DEFAULT_DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DEFAULT_YEAR_MONTH_PATTERN = DateTimeFormatter.ofPattern(PATTERN_YEAR_MONTH);

    /**
     * 获取今天日期对象
     * @return 今天日期对象{@link LocalDate}
     */
    public static LocalDate getToday() {
        return LocalDate.now();
    }

    /**
     * 获取指定格式的今日日期字符串
     * @param pattern 指定日期格式，缺省格式{@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @return 今日日期字符串
     */
    public static String getToday(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return getToday().format(DEFAULT_DATE_PATTERN);
        }
        return getToday().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取昨日日期对象
     * @return 昨日日期对象
     */
    public static LocalDate getYesterday() {
        return getToday().minusDays(1);
    }

    /**
     * 获取指定日期的昨日日期对象
     * @param date 指定日期 缺省解析格式 {@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @return 指定日期的昨日日期对象
     */
    public static LocalDate getYesterdayOfDate(String date) {
        return parseDate(date).minusDays(1);
    }

    /**
     * 获取指定日期的昨日日期对象
     * @param date 指定日期 缺省解析格式{@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @param pattern 指定日期的解析格式
     * @return 指定日期的昨日日期对象
     */
    public static LocalDate getYesterdayOfDate(String date, String pattern) {
        return parseDate(date, pattern).minusDays(1);
    }

    /**
     * 获取明日日期对象
     * @return 明日日期对象
     */
    public static LocalDate getTomorrow() {
        return getToday().plusDays(1);
    }

    /**
     * 获取指定日期的明日日期对象
     * @param date 指定日期 缺省解析格式{@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @return 指定日期的明日日期对象
     */
    public static LocalDate getTomorrowOfDate(String date) {
        return parseDate(date).plusDays(1);
    }

    /**
     * 获取指定日期的明日日期对象
     * @param date 指定日期 缺省解析格式{@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @param pattern 指定日期的解析格式
     * @return 指定日期的明日日期对象
     */
    public static LocalDate getTomorrowOfDate(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            throw new IllegalArgumentException("Argument [date] should not be null or blank!");
        }
        if (StringUtils.isBlank(pattern)) {
            return getTomorrowOfDate(date);
        }
        return parseDate(date, pattern).plusDays(1);
    }

    /**
     * 获取当前详细时间对象
     * @return 当前时间对象 {@link LocalDateTime}
     */
    public static LocalDateTime getDatetimeNow() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前月第一天日期对象
     * @return 当前月第一天日期对象
     */
    public static LocalDate getFirstDateOfCurrentMonth() {
        return getFirstDateOfMonth(null, null);
    }

    /**
     * 获取指定月第一天日期对象
     * 缺省日期为当前月
     * @param dateStr 日期字符串
     * @param pattern 对应的解析格式
     * @return 结果
     */
    public static LocalDate getFirstDateOfMonth(String dateStr, String pattern) {
        LocalDate ld;
        if (StringUtils.isNotBlank(dateStr)) {
            ld = parseDate(dateStr, pattern);
        } else {
            ld = getToday();
        }
        return ld.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取当前月最后一天日期对象
     * @return 当前月最后一天日期对象
     */
    public static LocalDate getLastDateOfCurrentMonth() {
        return getLastDateOfMonth(null, null);
    }

    /**
     * 获取指定月最后一天日期对象
     * 缺省日期为当前月
     * @param dateStr 日期字符串
     * @param pattern 对应的解析格式
     * @return 结果
     */
    public static LocalDate getLastDateOfMonth(String dateStr, String pattern) {
        LocalDate ld;
        if (StringUtils.isNotBlank(dateStr)) {
            ld = parseDate(dateStr, pattern);
        } else {
            ld = getToday();
        }
        return ld.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 解析年月
     * @param date 待解析时间字符串
     * @param pattern 指定解析的年月时间格式
     * @return {@link YearMonth}
     */
    public static YearMonth parseYearMonth(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            throw new IllegalArgumentException("[parseYearMonth] params [date] is null");
        }
        DateTimeFormatter dtf = StringUtils.isBlank(pattern) || PATTERN_YEAR_MONTH.equals(pattern) ? DEFAULT_YEAR_MONTH_PATTERN : DateTimeFormatter.ofPattern(pattern);
        YearMonth ym;
        // 尝试使用yyyy-MM-dd解析
        try {
            ym = YearMonth.parse(date, dtf);
        } catch (DateTimeParseException e) {
            // 尝试使用yyyy-MM-dd格式解析
            if (date.length() >= 7) {
                ym = YearMonth.parse(date, DEFAULT_DATE_PATTERN);
            } else {
                String message = "Can not parse [%s] to YearMonth with pattern [%s]";
                throw new IllegalArgumentException(String.format(message, date, pattern));
            }
        }
        return ym;
    }

    /**
     * 解析年月
     * @param date 待解析时间字符串 缺省解析格式{@link TimeUtils#DEFAULT_YEAR_MONTH_PATTERN}
     * @return {@link YearMonth}
     */
    public static YearMonth parseYearMonth(String date) {
        return parseYearMonth(date, null);
    }

    /**
     * 解析日期字符串
     * @param date 日期字符串 缺省解析格式{@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @return 日期对象
     */
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DEFAULT_DATE_PATTERN);
    }

    /**
     * 解析指定格式的日期字符串
     * @param date 日期字符串 缺省解析格式{@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @param pattern 自定的解析格式
     * @return 日期对象
     */
    public static LocalDate parseDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析时间字符串
     * @param datetime 时间字符串 缺省解析格式{@link TimeUtils#DEFAULT_DATE_TIME_PATTERN}
     * @return 时间对象 {@link LocalDateTime}
     */
    public static LocalDateTime parseDatetime(String datetime) {
        return LocalDateTime.parse(datetime, DEFAULT_DATE_TIME_PATTERN);
    }

    /**
     * 时间戳转LocalData
     * @param timestamp 时间戳
     * @return {@link LocalDate}
     */
    public static LocalDate parseDate(Long timestamp) {
        LocalDateTime ldt = parseDatetime(timestamp);
        return ldt.toLocalDate();
    }

    /**
     * 时间戳转LocalDateTime
     * @param timestamp 时间戳
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parseDatetime(Long timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("[parseDatetime] params [timestamp] is null");
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }

    /**
     * 时间字符串转LocalDateTime
     * @param datetime 时间字符串
     * @param pattern 指定的解析格式
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parseDatetime(String datetime, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return parseDatetime(datetime);
        }
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDateTime转字符串
     * @param datetime 时间对象,缺省解析格式{@link TimeUtils#DEFAULT_DATE_TIME_PATTERN}
     * @return 时间字符串
     */
    public static String datetimeToStr(LocalDateTime datetime) {
        return datetimeToStr(datetime, null);
    }

    /**
     * LocalDateTime转字符串
     * @param datetime 时间对象,缺省解析格式{@link TimeUtils#DEFAULT_DATE_TIME_PATTERN}
     * @param pattern 指定的时间格式
     * @return 时间字符串
     */
    public static String datetimeToStr(LocalDateTime datetime, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return datetime.format(DEFAULT_DATE_TIME_PATTERN);
        }
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalData转字符串
     * @param date 日期对象,缺省解析格式{@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @return 日期字符串
     */
    public static String dateToStr(LocalDate date) {
        return dateToStr(date, null);
    }

    /**
     * LocalData转字符串
     * @param date 日期对象,缺省解析格式{@link TimeUtils#DEFAULT_DATE_PATTERN}
     * @param pattern 指定的日期格式
     * @return 日期字符串
     */
    public static String dateToStr(LocalDate date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return date.toString();
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 时间戳转字符串
     * @param timestamp 时间戳
     * @param pattern 指定的转换格式,缺省解析格式{@link TimeUtils#DEFAULT_DATE_TIME_PATTERN}
     * @return 字符串
     */
    public static String timestampToStr(Long timestamp, String pattern) {
        if (timestamp == null) {
            throw new IllegalArgumentException("[timestampToStr] params error");
        }
        LocalDateTime ldt = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        return datetimeToStr(ldt, pattern);
    }

    /**
     * 时间戳转字符串
     * @param timestamp 时间戳 缺省解析格式{@link TimeUtils#DEFAULT_DATE_TIME_PATTERN}
     * @return 字符串
     */
    public static String timestampToStr(Long timestamp) {
        return timestampToStr(timestamp, null);
    }

    /**
     * 时间字符串转时间戳
     * @param timeStr 时间字符串
     * @param pattern 指定的解析格式
     * @return 时间戳
     */
    public static Long strToTimestamp(String timeStr, String pattern) {
        if (StringUtils.isBlank(timeStr)) {
            throw new IllegalArgumentException("[strToTimestamp] params [timeStr] is blank");
        }
        LocalDateTime ldt = parseDatetime(timeStr, pattern);
        return ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 时间字符串转时间戳
     * @param timeStr 时间字符串
     * @return 时间戳
     */
    public static Long strToTimestamp(String timeStr) {
        return strToTimestamp(timeStr, null);
    }

    public static String nowTime(String formstr){
        SimpleDateFormat sdf = new SimpleDateFormat(formstr);
      String time =  sdf.format(new Date());
      return time;
    }
}
