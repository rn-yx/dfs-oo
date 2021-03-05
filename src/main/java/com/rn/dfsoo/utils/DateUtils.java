package com.rn.dfsoo.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

/**
 * Description：线程安全日期工具类
 *
 * @author rannuo
 * @date 2019/9/2
 */
public class DateUtils {

    /**
     * 锁对象
     */
    private static final Object LOCK_OBJ = new Object();
    /**
     * 默认日期格式
     */
    private final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 存放不同的日期模板格式的sdf的Map
     */
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf（单例模式：双重检查）
     *
     * @param pattern 匹配模式
     * @return SimpleDateFormat
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> instance = sdfMap.get(pattern);
        // 双重判断和同步：防止sdfMap这个单例被多次put重复的sdf
        if (instance == null) {
            synchronized (LOCK_OBJ) {
                // Map中不存在pattern对应的sdf -->> 生成新的sdf并放入map
                // 关键：使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                instance = sdfMap.computeIfAbsent(pattern, k -> ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern)));
            }
        }
        return instance.get();
    }

    /**
     * 将日期时间格式化为指定格式的日期字符串
     * 用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     *
     * @param date    日期
     * @param pattern 匹配模式
     * @return String
     */
    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    /**
     * 将日期字符串解析为指定格式的日期时间
     *
     * @param dateStr 待格式化字符
     * @param pattern 匹配模式
     * @return Date
     * @throws ParseException ex
     */
    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSdf(pattern).parse(dateStr);
    }

    /**
     * 将日期字符串解析为指定格式的日期时间
     *
     * @param dateStr 待格式化字符
     * @return Date
     * @throws ParseException ex
     */
    public static Date parse(String dateStr) throws ParseException {
        return parse(dateStr, DEFAULT_FORMAT);
    }

    /**
     * 获取当前0时区时间
     *
     * @return Date
     */
    public static Date currentUTCTime() {
        return Date.from(Instant.now());
    }

    /**
     * 获取当前时间（根据系统时区取得）
     *
     * @return Date
     */
    public static Date currentTime() {
        return new Date();
    }

    /**
     * 获取当前时间固定格式的字符串(默认 "yyyy-MM-dd HH:mm:ss")
     *
     * @return 当前时间格式化后的字符串
     */
    public static String currentTimeStr() {
        return format(currentTime(), DEFAULT_FORMAT);
    }

    /**
     * 获取当前日期时间（根据系统时区取得）
     *
     * @param pattern 格式
     * @return 指定格式的日期字符串
     */
    public static String currentTime(String pattern) {
        return format(new Date(), pattern);
    }


    /**
     * 判断时间戳是否在时间限定范围内过期
     *
     * @param timestamp
     * @param limit
     * @return
     */
    public static boolean isAfter(long timestamp, long limit) {
        return Instant.now().isAfter(Instant.ofEpochMilli(timestamp + limit * 1000));
    }

    /**
     * 判断时间戳是否有效
     *
     * @param timestamp
     * @return
     */
    public static boolean isEffective(long timestamp) {
        return Instant.ofEpochMilli(timestamp).isAfter(Instant.now());
    }

    /**
     * 获取最近7天
     */
    public static List<String> getLastWeeks() {
        List<String> result = new ArrayList<>();
        Date now = new Date();
        result.add(DateUtil.format(now, DatePattern.NORM_DATE_PATTERN));
        for (int i = 1; i <= 6; i++) {
            result.add(DateUtil.format(DateUtil.offsetDay(now, -i), DatePattern.NORM_DATE_PATTERN));
        }
        return result;
    }

    /**
     * 获取最近一个月
     */
    public static List<String> getLastMonths() {
        List<String> result = new ArrayList<>();
        Date now = new Date();
        result.add(DateUtil.format(now, DatePattern.NORM_DATE_PATTERN));
        for (int i = 1; i <= 30; i++) {
            result.add(DateUtil.format(DateUtil.offsetDay(now, -i), DatePattern.NORM_DATE_PATTERN));
        }
        return result;
    }

    /**
     * 获取最近12个月
     */
    public static List<String> getLastYearMonths() {
        List<String> result = new ArrayList<>();
        Date now = new Date();
        result.add(DateUtil.format(now, "yyyy-MM"));
        for (int i = 1; i <= 11; i++) {
            result.add(DateUtil.format(DateUtil.offsetMonth(now, -i), "yyyy-MM"));
        }
        return result;
    }

    /**
     * 获取最近12个月
     */
    public static List<String> getLastYearMonthsByTime(Date date) {
        List<String> result = new ArrayList<>();
        result.add(DateUtil.format(date, "yyyy-MM"));
        for (int i = 1; i <= 11; i++) {
            result.add(DateUtil.format(DateUtil.offsetMonth(date, -i), "yyyy-MM"));
        }
        return result;
    }


    /**
     * 获取最近6个月
     */
    public static List<String> getLastYearMonths6() {
        List<String> result = new ArrayList<>();
        Date now = new Date();
        result.add(DateUtil.format(now, "yyyy-MM"));
        for (int i = 1; i <= 5; i++) {
            result.add(DateUtil.format(DateUtil.offsetMonth(now, -i), "yyyy-MM"));
        }
        return result;
    }

    /**
     * 获取最近6个月
     */
    public static List<String> getLastYearMonths6ByTime(Date date) {
        List<String> result = new ArrayList<>();
        result.add(DateUtil.format(date, "yyyy-MM"));
        for (int i = 1; i <= 5; i++) {
            result.add(DateUtil.format(DateUtil.offsetMonth(date, -i), "yyyy-MM"));
        }
        return result;
    }
}
