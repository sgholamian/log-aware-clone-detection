//,temp,ApiResponseSerializer.java,66,73,temp,ApiResponseSerializer.java,57,64
//,3
public class xxx {
    public static String toSerializedString(ResponseObject result, String responseType) {
        s_logger.trace("===Serializing Response===");
        if (HttpUtils.RESPONSE_TYPE_JSON.equalsIgnoreCase(responseType)) {
            return toJSONSerializedString(result, new StringBuilder());
        } else {
            return toXMLSerializedString(result, new StringBuilder());
        }
    }

};