package com.lyranxi.link.web.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * localDateTime序列化
 * @author ranxi
 * @date 2025-03-13 11:07
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (localDateTime != null && jsonGenerator != null) {
            jsonGenerator.writeNumber(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        }
    }
}
