package io.aelf.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;

public final class JsonUtil {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final ObjectMapper UNKNOWN_PROPERTIES_MAPPER = new ObjectMapper();

  static {
    UNKNOWN_PROPERTIES_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static String toJsonString(Object value) {
    if (value == null) {
      return null;
    } else {
      StringWriter writer = new StringWriter(1000);

      try {
        MAPPER.writeValue(writer, value);
      } catch (IOException var3) {
        throw new RuntimeException(var3);
      }

      return writer.toString();
    }
  }

  public static MapEntry parseObject(String value) {
    return StringUtil.isBlank(value) ? null : (MapEntry) parseObject(value, MapEntry.class);
  }

  public static <T> T parseObject(String value, Class<T> clazz, boolean failOnUnknowProperties) {
    if (StringUtil.isBlank(value)) {
      return null;
    } else {
      try {
        return failOnUnknowProperties ? MAPPER.readValue(value, clazz)
            : UNKNOWN_PROPERTIES_MAPPER.readValue(value, clazz);
      } catch (IOException var4) {
        throw new RuntimeException(var4);
      }
    }
  }

  public static <T> T parseObject(String value, Class<T> clazz) {
    return parseObject(value, clazz, true);
  }
}
