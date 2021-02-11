//,temp,BigSwitchBcfApi.java,92,112,temp,BrocadeVcsApi.java,72,92
//,3
public class xxx {
    protected HttpMethod createMethod(final String type, final String uri, final int port) throws BigSwitchBcfApiException {
        String url;
        try {
            url = new URL(S_PROTOCOL, host, port, uri).toString();
        } catch (MalformedURLException e) {
            S_LOGGER.error("Unable to build Big Switch API URL", e);
            throw new BigSwitchBcfApiException("Unable to build Big Switch API URL", e);
        }

        if ("post".equalsIgnoreCase(type)) {
            return new PostMethod(url);
        } else if ("get".equalsIgnoreCase(type)) {
            return new GetMethod(url);
        } else if ("delete".equalsIgnoreCase(type)) {
            return new DeleteMethod(url);
        } else if ("put".equalsIgnoreCase(type)) {
            return new PutMethod(url);
        } else {
            throw new BigSwitchBcfApiException("Requesting unknown method type");
        }
    }

};