package com.lyranxi.link.web.serial;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author ranxi
 * @date 2025-03-13 11:10
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        if (StrUtil.isBlank(jsonParser.getValueAsString())) {
            return null;
        }
        long timestamp = jsonParser.getLongValue();
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(8));
    }
}
