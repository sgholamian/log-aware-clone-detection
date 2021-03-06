//,temp,HypervDirectConnectResource.java,1862,1880,temp,HypervDirectConnectResource.java,1799,1827
//,3
public class xxx {
    protected int getVmFreeNicIndex(final String vmName) {
        final GetVmConfigCommand vmConfig = new GetVmConfigCommand(vmName);
        URI agentUri = null;
        int nicposition = -1;
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
        s_logger.debug("GetVmConfigCommand response received "
                + s_gson.toJson(result));
        if (result.length > 0) {
            final GetVmConfigAnswer ans = (GetVmConfigAnswer)result[0];
            final List<NicDetails> nics = ans.getNics();
            for (final NicDetails nic : nics) {
                if (nic.getState() == false) {
                    nicposition = nics.indexOf(nic);
                    break;
                }
            }
        }
        return nicposition;
    }

};