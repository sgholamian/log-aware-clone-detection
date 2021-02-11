//,temp,Action.java,271,288,temp,BigSwitchBcfApi.java,432,448
//,3
public class xxx {
    private String responseToErrorMessage(final HttpMethodBase method) {
        assert method.isRequestSent() : "no use getting an error message unless the request is sent";

        final Header contentTypeHeader = method.getResponseHeader(CONTENT_TYPE);
        if (contentTypeHeader != null && TEXT_HTML_CONTENT_TYPE.equals(contentTypeHeader.getValue())) {
            // The error message is the response content
            // Safety margin of 1024 characters, anything longer is probably
            // useless and will clutter the logs
            try {
                return method.getResponseBodyAsString(BODY_RESP_MAX_LEN);
            } catch (IOException e) {
                s_logger.debug("Error while loading response body", e);
            }
        }

        // The default
        return method.getStatusText();
    }

};