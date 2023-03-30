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
        for (int i = 0; i < 7; i++) {
            ArrayList<Integer> integers = new ArrayList<>(24);
            for (int z = 0; z < 24; z++) {
                integers.add(0);
            }
            int j = 23;
            Long tem = availableList.get(i);
            for (; j >= 0; j--) {
                integers.set(j, tem % 2 == 1 ? 1 : 0);
                tem >>= 1;
            }
            ans.add(integers);
        }
        return ans;
    }

    private static Boolean checkTimeValid(Long time, Integer beginTime, Integer rentTime, Integer changeTo) {
        time >>= (24 - beginTime - rentTime + 1);
        for (int i = 1; i <= rentTime; i++) {
            if ((time & 1) == changeTo) {
                return false;
            }
        }
        return true;
    }

    public static Long changeTime(Long time, Integer beginTime, Integer rentTime, Integer changeTo) {
        if (!checkTimeValid(time, beginTime, rentTime, changeTo)) {
            return -1L;
        }
        int x = 1;
        x <<= (24 - beginTime);
        if (changeTo == 1) {
            for (int i = 1; i <= rentTime; i++) {
                time |= x;
                x >>= 1;
            }
        } else {
            for (int i = 1; i <= rentTime; i++) {
                time ^= x;
                x >>= 1;
            }
        }
        return time;
    }

    public static Timestamp getFinalDate(Integer dayOffSet, Integer beginTime) {
        DateTime offset = DateUtil.offset(new Date(System.currentTimeMillis()), DateField.DAY_OF_MONTH, dayOffSet);
        offset.setField(DateField.HOUR_OF_DAY, beginTime);
        offset.setField(DateField.MINUTE, 1);
        offset.setField(DateField.SECOND, 1);
        return offset.toTimestamp();
    }
    public static Integer getbetweenDay(Timestamp time) {
        DateTime date = DateUtil.date(System.currentTimeMillis());
        date.setField(DateField.HOUR_OF_DAY,DateUtil.hour(time,true));
        date.setField(DateField.MINUTE, 0);
        date.setField(DateField.SECOND, 0);
        return (int) DateUtil.between(time,date, DateUnit.DAY,true);
    }
}
