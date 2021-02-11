//,temp,NetScalerControlCenterResource.java,468,482,temp,NetScalerControlCenterResource.java,310,322
//,3
public class xxx {
    private void keepSessionAlive() throws ExecutionException {
        URI agentUri = null;
        try {
            agentUri =
                    new URI("https", null, _ip, DEFAULT_PORT,
                            "/cs/cca/v1/cloudstacks", null, null);
            org.json.JSONObject jsonBody = new JSONObject();
            getHttpRequest(jsonBody.toString(), agentUri, _sessionid);
            s_logger.debug("Keeping Session Alive");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

};