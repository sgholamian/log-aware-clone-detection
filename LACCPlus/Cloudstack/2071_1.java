//,temp,NetScalerControlCenterResource.java,483,530,temp,NetScalerControlCenterResource.java,366,408
//,3
public class xxx {
    private synchronized Answer execute(LoadBalancerConfigCommand cmd, int numRetries) {
        try {
            LoadBalancerTO[] loadBalancers = cmd.getLoadBalancers();
            if (loadBalancers == null) {
                return new Answer(cmd);
            }
            JSONObject lbConfigPaylod = new JSONObject(cmd);
            String gsonLBConfig =  _gson.toJson(cmd);
            URI agentUri = null;
            agentUri =
                    new URI("https", null, _ip, DEFAULT_PORT,
                            "/cs/adcaas/v1/loadbalancerCmds", null, null);
            JSONObject lbConfigCmd = new JSONObject();
            JSONObject lbcmd = new JSONObject(gsonLBConfig);
            s_logger.debug("LB config from gsonstring to JSONObject : " +  lbcmd.toString() + "\n" + "gson cmd is :: \t" + gsonLBConfig);
            lbConfigCmd.put("LoadBalancerConfigCommand",  lbcmd.getJSONArray("loadBalancers"));
            s_logger.debug("LB config paylod : " +  lbConfigCmd.toString());

            String result = postHttpRequest(lbConfigCmd.toString(), agentUri, _sessionid);
            s_logger.debug("Result of lbconfigcmg is "+ result);
            result = queryAsyncJob(result);
            s_logger.debug("Done query async of LB ConfigCmd implement request and result:: " + result);
            return new Answer(cmd);
        } catch (ExecutionException e) {
            s_logger.error("Failed to execute LoadBalancerConfigCommand due to ", e);
            if(e.getMessage().equalsIgnoreCase(NccHttpCode.NOT_FOUND)) {
                return new Answer(cmd, true, "LB Rule is not present in NS device. So returning as removed the LB Rule");
            } else  if(e.getMessage().startsWith("ERROR, ROLLBACK COMPLETED") || e.getMessage().startsWith("ERROR, ROLLBACK FAILED")) {
                s_logger.error("Failed to execute LoadBalancerConfigCommand due to : " + e.getMessage());
                return new Answer(cmd, false, e.getMessage());
            } else if (e.getMessage().startsWith(NccHttpCode.INTERNAL_ERROR)) {
                s_logger.error("Failed to execute LoadBalancerConfigCommand as Internal Error returning Internal error ::" + e.getMessage() );
                return new Answer(cmd, false, e.getMessage());
            }
            if (shouldRetry(numRetries)) {
                return retry(cmd, numRetries);
            } else {
                return new Answer(cmd, false, e.getMessage());
            }
        } catch (Exception e) {
            s_logger.error("Failed to execute LoadBalancerConfigCommand due to ", e);
            if (shouldRetry(numRetries)) {
                return retry(cmd, numRetries);
            } else {
                return new Answer(cmd, false, e.getMessage());
            }
        }
    }

};