package com.cl.utils.commonTools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
    private static final ThreadLocal<SimpleDateFormat> dateFormat=new ThreadLocal<>();
    public static SimpleDateFormat getBasicDateFormat(){
        SimpleDateFormat sdf = dateFormat.get();
        if(null==sdf){
            sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.set(sdf);
        }
        return sdf;
    }
/*    public static SimpleDateFormat getHeadDateFormat(){
        SimpleDateFormat sdf = dateFormat.get();
        if(null==sdf){
            sdf=new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.set(sdf);
        }
        return sdf;
    }*/

    /**
     * 日期
     * @return
     */
    public static String getCurDate(){
        return getCurTime().substring(0,10);
    }

    /**
     * 时间
     * @return
     */
    public static String getCurTime(){
        Date nowTime=new Date();
        return getBasicDateFormat().format(nowTime);
    }
}
