package io.aelf.utils;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

public class ProtoJsonUtil{
    public static String toJson(Message message) throws Exception {
        String json = JsonFormat.printer().print(message);
        return json;
    }

    public static Message toProtoBean(Message.Builder messageBuilder, String json) throws Exception{
        JsonFormat.parser().merge(json, messageBuilder);
        return messageBuilder.build();
    }
}