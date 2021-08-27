//,temp,DefaultExchangeHolder.java,421,427,temp,DefaultExchangeHolder.java,413,419
//,2
public class xxx {
    private static void logInvalidHeaderValue(String type, String key, Object value) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(
                    "Exchange {} containing key: {} with object: {} of type: {} is not valid header type, it will be excluded by the holder.",
                    new Object[] { type, key, value, ObjectHelper.classCanonicalName(value) });
        }
    }

};