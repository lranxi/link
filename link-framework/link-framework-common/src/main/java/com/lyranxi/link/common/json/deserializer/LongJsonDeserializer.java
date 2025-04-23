package com.lyranxi.link.common.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * json解析时将string转long
 * <p>
 *     使用时将VO字段添加：@JsonDeserialize(using = LongJsonDeserializer.class)
 * </p>
 *
 * @author Tiger Feng
 * @date 2019年04月23日
 */
@Slf4j
public class LongJsonDeserializer extends JsonDeserializer<Long> {
    public LongJsonDeserializer() { }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        try {
            return value == null ? null : Long.parseLong(value);
        } catch (NumberFormatException var5) {
            log.error("解析长整形错误", var5);
            return null;
        }
    }
}
