package com.im.nettychat.util;

import com.im.nettychat.common.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class DateUtil {

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constant.YMD_HMS);

    public static String nowString() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}
