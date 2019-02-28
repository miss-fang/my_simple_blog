package com.cl.utils.commonTools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ToJson {

    public static String listToJson(List<?> list) {
        ObjectMapper om = new ObjectMapper();
        String result = "";
        try {
            result = om.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            om = null;
        }
        return result;
    }
}
