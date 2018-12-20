package com.im.nettychat.util;

import java.util.Collection;

/**
 * @author hejianglong
 * @date 2018/12/20.
 */
public class CollectionUtils {

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(String[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isNotNullOrEmpty(String[] arrays) {
        return arrays != null && arrays.length > 0;
    }
}
