//,temp,Action.java,271,288,temp,BigSwitchBcfApi.java,432,448
//,3
public class xxx {
    private String responseToErrorMessage(final HttpMethodBase method) {
        assert method.isRequestSent() : "no use getting an error message unless the request is sent";

        if ("text/html".equals(method.getResponseHeader(CONTENT_TYPE).getValue())) {
            // The error message is the response content
            // Safety margin of 2048 characters, anything longer is probably useless
            // and will clutter the logs
            try {
                return method.getResponseBodyAsString(2048);
            } catch (IOException e) {
                S_LOGGER.debug("Error while loading response body", e);
            }
        }

        // The default
        return method.getStatusText();
    }

};