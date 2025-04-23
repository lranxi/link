package com.lyranxi.link.common.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @program: fengda
 * 时间戳反序列化时间
 * @author  fengxiaochun
 * @date    2020-05-21 09:56
 **/
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if( isBlank(jsonParser.getValueAsString())) {
            return null;
        }
        long timestamp = jsonParser.getLongValue();
        Instant instant = Instant.ofEpochMilli(timestamp);
        // LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault())
        return LocalDateTime.ofInstant(instant, ZoneOffset.ofHours(8));
    }

    private boolean isBlank(String s) {
        return s == null || "".equals(s);
    }
}
