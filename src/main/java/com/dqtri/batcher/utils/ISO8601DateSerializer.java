package com.dqtri.batcher.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date and time in UTC	2024-05-04T15:16:01.152
 * Date and time with the offset	2024-05-04T08:16:01.152−07:00
 */
public class ISO8601DateSerializer extends JsonSerializer<Date> {
    public static final String ISO8601_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat(ISO8601_DATE_PATTERN, Locale.ENGLISH);
        jsonGenerator.writeString(formatter.format(date));
    }
}