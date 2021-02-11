//,temp,VmwareResource.java,1478,1499,temp,HypervDirectConnectResource.java,587,608
//,3
public class xxx {
    @Override
    public ExecutionResult executeInVR(final String routerIP, final String script, final String args, final Duration timeout) {
        Pair<Boolean, String> result;

        //TODO: Password should be masked, cannot output to log directly
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Run command on VR: " + routerIP + ", script: " + script + " with args: " + args);
        }

        try {
            result = SshHelper.sshExecute(routerIP, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, "/opt/cloud/bin/" + script + " " + args, VRScripts.CONNECTION_TIMEOUT,
                    VRScripts.CONNECTION_TIMEOUT, timeout);
        } catch (final Exception e) {
            final String msg = "Command failed due to " + e ;
            s_logger.error(msg);
            result = new Pair<Boolean, String>(false, msg);
        }
        if (s_logger.isDebugEnabled()) {
            s_logger.debug(script + " execution result: " + result.first().toString());
        }
        return new ExecutionResult(result.first(), result.second());
    }

};