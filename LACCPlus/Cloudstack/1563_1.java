//,temp,ApiResponseSerializer.java,66,73,temp,ApiResponseSerializer.java,57,64
//,3
public class xxx {
    public static String toSerializedStringWithSecureLogs(ResponseObject result, String responseType, StringBuilder log) {
        s_logger.trace("===Serializing Response===");
        if (HttpUtils.RESPONSE_TYPE_JSON.equalsIgnoreCase(responseType)) {
            return toJSONSerializedString(result, log);
        } else {
            return toXMLSerializedString(result, log);
        }
    }

};