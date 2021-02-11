//,temp,BigSwitchBcfApi.java,92,112,temp,BrocadeVcsApi.java,72,92
//,3
public class xxx {
    protected HttpRequestBase createMethod(String type, String uri) throws BrocadeVcsApiException {
        String url;
        try {
            url = new URL(Constants.PROTOCOL, _host, Constants.PORT, uri).toString();
        } catch (final MalformedURLException e) {
            s_logger.error("Unable to build Brocade Switch API URL", e);
            throw new BrocadeVcsApiException("Unable to build Brocade Switch API URL", e);
        }

        if ("post".equalsIgnoreCase(type)) {
            return new HttpPost(url);
        } else if ("get".equalsIgnoreCase(type)) {
            return new HttpGet(url);
        } else if ("delete".equalsIgnoreCase(type)) {
            return new HttpDelete(url);
        } else if ("patch".equalsIgnoreCase(type)) {
            return new HttpPatch(url);
        } else {
            throw new BrocadeVcsApiException("Requesting unknown method type");
        }
    }

};