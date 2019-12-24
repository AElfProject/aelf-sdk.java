package io.aelf.utils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public final class JSONUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper UNKNOWN_PROPERTIES_MAPPER = new ObjectMapper();

    static {
        UNKNOWN_PROPERTIES_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public JSONUtil() {
    }

    public static String toJSONString(Object value) {
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
        return StringUtil.isBlank(value) ? null : (MapEntry)parseObject(value, MapEntry.class);
    }

    public static <T> T parseObject(String value, Class<T> clazz, boolean failOnUnknowProperties) {
        if (StringUtil.isBlank(value)) {
            return null;
        } else {
            try {
                return failOnUnknowProperties ? MAPPER.readValue(value, clazz) : UNKNOWN_PROPERTIES_MAPPER.readValue(value, clazz);
            } catch (IOException var4) {
                throw new RuntimeException(var4);
            }
        }
    }

    public static <T> T parseObject(String value, Class<T> clazz) {
        return parseObject(value, clazz, true);
    }

    public static void main(String[] args) {
        String sign = "{\"area_id\":\"8110101\",\"comments\":\"是的\",\"cust_address\":\"也\",\"cust_attr_list\":[{\"attr_id\":\"16\",\"attr_name\":\"所属行业\",\"attr_value\":\"建筑\",\"attr_value_type\":\"4\",\"state\":\"10A\"},{\"attr_id\":\"17\",\"attr_name\":\"企业规模\",\"attr_value\":\"1~50人\",\"attr_value_type\":\"4\",\"state\":\"10A\"}],\"cust_contact_list\":[{\"contact_name\":\"范海宁\",\"isIndex\":false,\"mobile_phone\":\"18501630341\",\"state\":\"10A\"},{\"contact_name\":\"范海宁\",\"cust_contact_id\":\"70\",\"isIndex\":false,\"mobile_phone\":\"18501630341\",\"state\":\"10X\"}],\"cust_id\":\"6c734fa7-c502-4fc5-b16b-09d1f376465a\",\"cust_name\":\"天源迪科\",\"device_id\":\"00000000-74df-4b63-ffff-ffffcf14b592\",\"key\":\"f3:46:af:df:9d:06:f3:88:57:dd:77:4c:8c:a9:a3:b7\",\"os_type\":\"01\",\"service_code\":\"CUST_DETAILE_MOD\",\"terminal_type\":\"00\",\"ticket\":\"9cd75ea6-c790-4544-b2f2-c034dee91efd\",\"ticket_object\":{\"bss_org_id\":\"eebd3367-d663-4874-823c-f6fda0c8a6e1\",\"bss_org_name\":\"软件三部\",\"company_id\":\"2ae66a71-2f58-11e4-bd6b-00163e000f02\",\"company_name\":\"成都微销科技公司\",\"email_addr\":\"fanhn@tydic.com\",\"head_pic_url\":\"http://115.29.77.35/wesale/head_pic_url/3.jpg\",\"mob_acc_nbr\":\"13600000000\",\"open_fire_id\":\"5ba13edd-9fab-4e58-b3cd-4d09f3f006e8\",\"role_id\":\"f4f51dc8-abf7-4c68-997e-06a81a306749\",\"role_name\":\"普通员工\",\"sex\":\"M\",\"user_id\":\"22e8ea1f-d2e3-425e-b05c-42883d7f27fe\",\"user_name\":\"范海宁\"}}";
        LinkedMapEntry linkedParam = (LinkedMapEntry)parseObject(sign, LinkedMapEntry.class);
        List list = linkedParam.getList("cust_attr_list");
        List list1 = linkedParam.getList("cust_contact_list");
        Object obj = linkedParam.get("ticket_object");
        System.out.println(toJSONString(linkedParam));
    }
}
