package io.aelf.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public final class JsonUtil {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final ObjectMapper UNKNOWN_PROPERTIES_MAPPER = new ObjectMapper();

  static {
    UNKNOWN_PROPERTIES_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  /**
   * Json conversion string.
   * @param value not blank
   * @return str
   */
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

  /**
   * string conversion MapEntry class.
   * @param value not blank
   * @return MapEntry Obj
   */
  @Nullable
  public static MapEntry<String,?> parseObject(String value) {
    return StringUtil.isBlank(value) ? null : parseObject(value, MapEntry.class);
  }

  /**
   * Generic conversion.
   * @param value not blank
   * @param clazz not blank
   * @param failOnUnknownProperties not blank
   * @param <T> not blank
   * @return T
   */
  public static <T> T parseObject(String value, Class<T> clazz, boolean failOnUnknownProperties) {
    if (StringUtil.isBlank(value)) {
      return null;
    } else {
      try {
        return failOnUnknownProperties ? MAPPER.readValue(value, clazz)
            : UNKNOWN_PROPERTIES_MAPPER.readValue(value, clazz);
      } catch (IOException var4) {
        throw new RuntimeException(var4);
      }
    }
  }

  /**
   * Generic conversion.
   * @param value not blank
   * @param clazz not blank
   * @param <T> not blank
   * @return T
   */
  public static <T> T parseObject(String value, Class<T> clazz) {
    return parseObject(value, clazz, true);
  }
}
