//,temp,HypervDirectConnectResource.java,1862,1880,temp,HypervDirectConnectResource.java,1799,1827
//,3
public class xxx {
    protected void modifyNicVlan(final String vmName, final String vlanId, final String macAddress) {
        final ModifyVmNicConfigCommand modifynic = new ModifyVmNicConfigCommand(vmName, vlanId, macAddress);
        URI agentUri = null;
        try {
            final String cmdName = ModifyVmNicConfigCommand.class.getName();
            agentUri =
                    new URI("https", null, _agentIp, _port,
                            "/api/HypervResource/" + cmdName, null, null);
        } catch (final URISyntaxException e) {
            final String errMsg = "Could not generate URI for Hyper-V agent";
            s_logger.error(errMsg, e);
        }
        final String ansStr = postHttpRequest(s_gson.toJson(modifynic), agentUri);
        final Answer[] result = s_gson.fromJson(ansStr, Answer[].class);
        s_logger.debug("executeRequest received response "
                + s_gson.toJson(result));
        if (result.length > 0) {
        }
    }

};