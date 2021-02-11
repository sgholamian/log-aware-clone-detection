//,temp,NetScalerControlCenterResource.java,468,482,temp,NetScalerControlCenterResource.java,196,215
//,3
public class xxx {
    public void getServicePackages() throws ExecutionException {
            String result = null;
            try {
                URI agentUri = null;
                agentUri =
                        new URI("https", null, _ip, DEFAULT_PORT,
                                "/admin/v1/servicepackages", null, null);

                org.json.JSONObject jsonBody = new JSONObject();
                org.json.JSONObject jsonCredentials = new JSONObject();
                result = getHttpRequest(jsonBody.toString(), agentUri, _sessionid);
                s_logger.debug("List of Service Packages in NCC:: " + result);
                } catch (URISyntaxException e) {
                    String errMsg = "Could not generate URI for Hyper-V agent";
                    s_logger.error(errMsg, e);

                } catch (Exception e) {
                throw new ExecutionException("Failed to log in to NCC device at " + _ip + " due to " + e.getMessage());
            }
    }

};