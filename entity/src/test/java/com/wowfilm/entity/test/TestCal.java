package com.wowfilm.entity.test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wen on 2016/7/21 11:42.
 */
public class TestCal {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.DATE));

        System.out.println(cal.get(Calendar.MONTH)+1);
        System.out.println(cal.get(Calendar.YEAR));
        long l = new Date().getTime()-10000;
        System.out.println(new Date(l).before(new Date()));
    }
}
