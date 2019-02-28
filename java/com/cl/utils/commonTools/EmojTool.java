package com.cl.utils.commonTools;

import com.vdurmont.emoji.EmojiParser;

public class EmojTool {
    /**
     * 字符串转换成emoj
     * @param value
     * @return
     */
    public static String emoji(String value) {
        return EmojiParser.parseToUnicode(value);
    }
}
