//,temp,ApiResponseSerializer.java,368,378,temp,EncodedStringTypeAdapter.java,39,49
//,2
public class xxx {
    private static String encodeParam(String value) {
        if (!ApiServer.isEncodeApiResponse()) {
            return value;
        }
        try {
            return new URLEncoder().encode(value).replaceAll("\\+", "%20");
        } catch (Exception e) {
            s_logger.warn("Unable to encode: " + value, e);
        }
        return value;
    }

};