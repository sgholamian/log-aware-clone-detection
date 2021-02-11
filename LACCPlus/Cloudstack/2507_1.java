//,temp,HypervDirectConnectResource.java,1882,1901,temp,HypervDirectConnectResource.java,1829,1860
//,3
public class xxx {
    protected void modifyNicVlan(final String vmName, final String vlanId, final int pos, final boolean enable, final String switchLabelName) {
        final ModifyVmNicConfigCommand modifyNic = new ModifyVmNicConfigCommand(vmName, vlanId, pos, enable);
        modifyNic.setSwitchLableName(switchLabelName);
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
        final String ansStr = postHttpRequest(s_gson.toJson(modifyNic), agentUri);
        final Answer[] result = s_gson.fromJson(ansStr, Answer[].class);
        s_logger.debug("executeRequest received response "
                + s_gson.toJson(result));
        if (result.length > 0) {
        }
    }

};