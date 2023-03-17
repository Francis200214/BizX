package com.biz.common.utils;


import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StrUtils {

    private static final String PIC_PREFIX_URL = "http://192.168.1.201:8075";


    public static List<String> setPicPrefixUrl(String pic) {
        if (StrUtil.isBlank(pic)) {
            return Collections.emptyList();
        }
        List<String> resultList = new ArrayList<>();
        for (String s : pic.split(",")) {
            resultList.add(PIC_PREFIX_URL + s);
        }
        return resultList;
    }

}
