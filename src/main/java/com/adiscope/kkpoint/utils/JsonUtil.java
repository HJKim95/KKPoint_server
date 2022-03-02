package com.adiscope.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * Created by 최경근(keun0912@neowiz.com) on 2020-04-07.
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final ObjectMapper objectMapperCamel = new ObjectMapper();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    static {
        objectMapperCamel.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        objectMapperCamel.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public static String convert(Object message) {

        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }

    }

    public static String convertCamel(Object message) {

        try {
            return objectMapperCamel.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }

    }

    public static <T> T convert(String data, Class<T> valueType) {
        try {
            return objectMapper.readValue(data, valueType);
        } catch (IOException e) {
            log.error("", e);

            return null;
        }
    }

    public static <T> T convertCamel(String data, Class<T> valueType) {
        try {
            return objectMapperCamel.readValue(data, valueType);
        } catch (IOException e) {
            log.error("", e);

            return null;
        }
    }
}
