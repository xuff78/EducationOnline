package com.education.online.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12.
 */
public class MapUtil {
    public MapUtil() {
    }

    public static final Map<String, String> sort(Map<String, String> map) {
        Map<String, String> sorted = new LinkedHashMap<String, String>();
        for (String key : getSortedKeys(map)) {
            sorted.put(key, map.get(key));
        }
        return sorted;
    }

    private static List<String> getSortedKeys(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        return keys;
    }

    public static void decodeAndAppendEntries(Map<String, String> source, Map<String, String> target) {
        Iterator var3 = source.keySet().iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            target.put(URLUtil.percentEncode(key), URLUtil.percentEncode((String)source.get(key)));
        }

    }
}
