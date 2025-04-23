package com.lyranxi.link.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @program: fengda
 * 时间序列化时变为时间戳
 * @author  fengxiaochun
 * @date    2020-05-21 09:54
 **/
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (localDateTime != null && jsonGenerator != null) {
            jsonGenerator.writeNumber(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        }
    }
}
