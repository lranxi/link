package com.lyranxi.link.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * json序列化时将long转string
 * <p>
 *     使用时将VO字段添加：@JsonSerialize(using = LongToStringSerializer.class)
 * </p>
 *
 * @author Tiger Feng
 * @date 2019年04月02日
 */
public class LongToStringSerializer extends JsonSerializer<Long> {
    public LongToStringSerializer() {
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String text = value == null ? null : String.valueOf(value);
        if (text != null) {
            gen.writeString(text);
        }

    }
}
