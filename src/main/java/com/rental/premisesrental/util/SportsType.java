package com.rental.premisesrental.util;

/**
 * @author 20179
 */

public enum SportsType {
    badminton("badminton"),
    tableTennis("table tennis"),
    basketball("basketball"),
    football("football");

    public final String typeName;

    SportsType(String typeName) {
        this.typeName = typeName;
    }


    public static Boolean checkSportsType(String type) {
        for (var i : SportsType.values()) {
            if (i.typeName.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
