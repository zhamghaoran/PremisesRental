package com.rental.premisesrental.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author 20179
 */
public class AvailableTimeUtil {
    public static List<Integer> decimaltoList(Long available) {
        List<Integer> availableTimeList = new ArrayList<>();
        while (available > 0) {
            availableTimeList.add(available % 2 == 1 ? 1 : 0);
            available >>= 1;
        }
        Collections.reverse(availableTimeList);
        return availableTimeList;
    }
}
