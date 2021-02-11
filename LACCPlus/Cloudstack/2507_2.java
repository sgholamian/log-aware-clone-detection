//,temp,HypervDirectConnectResource.java,1882,1901,temp,HypervDirectConnectResource.java,1829,1860
//,3
public class xxx {
    protected int getVmNics(final String vmName, String vlanid) {
        final GetVmConfigCommand vmConfig = new GetVmConfigCommand(vmName);
        URI agentUri = null;
        int nicposition = -1;
        if(vlanid.equalsIgnoreCase("untagged")) {
            vlanid = "-1";
        }
        try {
            final String cmdName = GetVmConfigCommand.class.getName();
            agentUri =
                    new URI("https", null, _agentIp, _port,
                            "/api/HypervResource/" + cmdName, null, null);
        } catch (final URISyntaxException e) {
            final String errMsg = "Could not generate URI for Hyper-V agent";
            s_logger.error(errMsg, e);
        }
        final String ansStr = postHttpRequest(s_gson.toJson(vmConfig), agentUri);
        final Answer[] result = s_gson.fromJson(ansStr, Answer[].class);
        s_logger.debug("executeRequest received response "
                + s_gson.toJson(result));
        if (result.length > 0) {
            final GetVmConfigAnswer ans = (GetVmConfigAnswer)result[0];
            final List<NicDetails> nics = ans.getNics();
            for (final NicDetails nic : nics) {
                nicposition++;
                if (nicposition > 1 && nic.getVlanid().equals(vlanid)) {
                    break;
                }
            }
        }
        return nicposition;
    }

};