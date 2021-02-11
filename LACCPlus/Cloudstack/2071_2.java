//,temp,NetScalerControlCenterResource.java,483,530,temp,NetScalerControlCenterResource.java,366,408
//,3
public class xxx {
    private synchronized Answer execute(NetScalerImplementNetworkCommand cmd, int numRetries) {
        String result = null;
        try {
            URI agentUri = null;
            agentUri =
                    new URI("https", null, _ip, DEFAULT_PORT,
                            "/cs/adcaas/v1/networks", null, null);
            org.json.JSONObject jsonBody = new JSONObject(cmd.getDetails());
            s_logger.debug("Sending Network Implement to NCC:: " + jsonBody);
            result = postHttpRequest(jsonBody.toString(), agentUri, _sessionid);
            s_logger.debug("Result of Network Implement to NCC:: " + result);
            result = queryAsyncJob(result);
            s_logger.debug("Done query async of network implement request :: " + result);
            return new Answer(cmd, true, "Successfully allocated device");
            } catch (URISyntaxException e) {
                String errMsg = "Could not generate URI for NetScaler ControlCenter ";
                s_logger.error(errMsg, e);
            } catch (ExecutionException e) {
                if(e.getMessage().equalsIgnoreCase(NccHttpCode.NOT_FOUND)) {
                    return new Answer(cmd, true, "Successfully unallocated the device");
                }else if(e.getMessage().startsWith("ERROR, ROLLBACK") ) {
                    s_logger.error(e.getMessage());
                    return new Answer(cmd, false, e.getMessage());
                }
                else {
                    if (shouldRetry(numRetries)) {
                        s_logger.debug("Retrying the command NetScalerImplementNetworkCommand retry count: " + numRetries, e);
                        return retry(cmd, numRetries);
                    } else {
                        return new Answer(cmd, false, e.getMessage());
                    }
                }
            } catch (Exception e) {
                if (shouldRetry(numRetries)) {
                    s_logger.debug("Retrying the command NetScalerImplementNetworkCommand retry count: " + numRetries, e);
                    return retry(cmd, numRetries);
                } else {
                    return new Answer(cmd, false, e.getMessage());
                }
            }

        return Answer.createUnsupportedCommandAnswer(cmd);
    }

};