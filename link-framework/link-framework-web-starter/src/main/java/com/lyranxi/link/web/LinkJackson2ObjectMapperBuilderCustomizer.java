package com.lyranxi.link.web;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lyranxi.link.web.serial.LocalDateTimeDeserializer;
import com.lyranxi.link.web.serial.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义jackson ObjectMapperBuilder
 *
 * @author ranxi
 * @date 2025-03-13 10:56
 */
public class LinkJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer())
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer())
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.BASIC_ISO_DATE))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.BASIC_ISO_DATE))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
