//,temp,NetScalerControlCenterResource.java,468,482,temp,NetScalerControlCenterResource.java,196,215
//,3
public class xxx {
    private String getLBHealthChecks(long networkid) throws ExecutionException  {
        URI agentUri = null;
        String response = null;
        try {
            agentUri =
                    new URI("https", null, _ip, DEFAULT_PORT,
                            "/cs/adcaas/v1/networks/"+ networkid +"/lbhealthstatus", null, null);
            org.json.JSONObject jsonBody = new JSONObject();
            response = getHttpRequest(jsonBody.toString(), agentUri, _sessionid);
            s_logger.debug("LBHealthcheck Response :" + response);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return response;
    }

};