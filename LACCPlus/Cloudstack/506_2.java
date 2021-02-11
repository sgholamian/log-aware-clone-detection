//,temp,HypervDirectConnectResource.java,1074,1115,temp,HypervDirectConnectResource.java,883,929
//,3
public class xxx {
    private SetStaticRouteAnswer execute(final SetStaticRouteCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource SetStaticRouteCommand: " + s_gson.toJson(cmd));
        }

        boolean endResult = true;

        final String controlIp = getRouterSshControlIp(cmd);
        String args = "";
        final String[] results = new String[cmd.getStaticRoutes().length];
        int i = 0;

        // Extract and build the arguments for the command to be sent to the VR.
        final String[] rules = cmd.generateSRouteRules();
        final StringBuilder sb = new StringBuilder();

        for (int j = 0; j < rules.length; j++) {
            sb.append(rules[j]).append(',');
        }
        args += " -a " + sb.toString();

        final String command = String.format("%s%s %s", "/opt/cloud/bin/", VRScripts.VPC_STATIC_ROUTE, args);

        // Send over the command for execution, via ssh, to the VR.
        try {
            final Pair<Boolean, String> result =
                    SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);

            if (s_logger.isDebugEnabled()) {
                s_logger.debug("Executing script on domain router " + controlIp + ": /opt/cloud/bin/vpc_staticroute.sh " + args);
            }

            if (!result.first()) {
                s_logger.error("SetStaticRouteCommand failure on setting one rule. args: " + args);
                results[i++] = "Failed";
                endResult = false;
            } else {
                results[i++] = null;
            }
        } catch (final Throwable e) {
            s_logger.error("SetStaticRouteCommand(args: " + args + ") failed on setting one rule due to " + e);
            results[i++] = "Failed";
            endResult = false;
        }
        return new SetStaticRouteAnswer(cmd, endResult, results);

    }

};