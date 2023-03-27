package com.rental.premisesrental.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 20179
 */
public class AvailableTimeUtil {

    public static List<List<Integer>> decimaltoList(String available) {
        List<List<Integer>> ans = new ArrayList<>();
        // 获取到一个长度为7的数组
        // 每一个数字表示每一天的出租情况。
        List<Long> availableList = JSON.parseArray(available).toList(Long.class);
        for (int i = 0;i < 7;i ++) {
            ArrayList<Integer> integers = new ArrayList<>(24);
            for (int z = 0;z < 24;z ++){
                integers.add(0);
            }
            int j = 23;
            Long tem = availableList.get(i);
            for (;j >= 0;j --) {
                integers.set(j,tem % 2 == 1 ? 1 : 0);
                tem >>= 1;
            }
            ans.add(integers);
        }
        return ans;
    }

    public static Timestamp getFinalDate(Integer dayOffSet) {
        DateTime offset = DateUtil.offset(new Date(System.currentTimeMillis()), DateField.DAY_OF_MONTH, dayOffSet);
        return new Timestamp(offset.getTime());
    }
}
