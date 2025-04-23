package com.lyranxi.link.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lyranxi.link.common.json.deserializer.LocalDateTimeDeserializer;
import com.lyranxi.link.common.json.serializer.LocalDateTimeSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Jackson ObjectMapper工具，LocalDateTime序列化成时间戳
 * ObjectMapperUtil
 * @author  fengxiaochun
 * @date    2020-07-15 10:45
 **/
public class ObjectMapperUtil {

    public static Jackson2ObjectMapperBuilder getJackson2ObjectMapperBuilder(){
        return new Jackson2ObjectMapperBuilder()
                //.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                // LocalDateTime  时间戳
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer())
                // LocalDate  20111203
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.BASIC_ISO_DATE))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.BASIC_ISO_DATE))
                // LocalTime  10:15:30
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
    }

    public static ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = getJackson2ObjectMapperBuilder().build();

        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 排除json字符串中实体类没有的字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                for (BeanPropertyWriter beanPropertyWriter : beanProperties) {
                    JavaType javaType = beanPropertyWriter.getType();
                    // Array or Collection
                    if (javaType.isArrayType() || javaType.isCollectionLikeType()) {
                        beanPropertyWriter.assignNullSerializer(new EmptyArrayJsonSerializer());
                    }
                    // Map
                    else if (javaType.isMapLikeType()) {
                        beanPropertyWriter.assignNullSerializer(new EmptyMapJsonSerializer());
                    }
                }

                return beanProperties;
            }
        }));
        return objectMapper;
    }

    public static class BlankStringJsonSerializer extends JsonSerializer<Object>{
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            jsonGenerator.writeString("");
            //jsonGenerator.writeObject(null);
        }
    }

    public static class EmptyArrayJsonSerializer extends JsonSerializer<Object>{
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        }
    }
    public static class EmptyMapJsonSerializer extends JsonSerializer<Object>{
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeEndObject();
        }
    }
}
