package com.cl.utils.commonTools;

import com.cl.constant.BlogConstant;

import java.util.Random;
import java.util.UUID;

public class RandTool {
    private static final Random random=new Random();

    /**
     * 图片随机id
     * @return
     */
    public static int getRandPicId(){
        return random.nextInt(BlogConstant.RANDOM_PIC_NUM)+1;
    }

    /**
     * 上传图片时的图片名，防止重名
     * @return
     */
    public static String randPicName(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
