//,temp,HypervDirectConnectResource.java,1148,1198,temp,HypervDirectConnectResource.java,1074,1115
//,3
public class xxx {
    protected Answer execute(final SetPortForwardingRulesCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource SetPortForwardingRulesCommand: " + s_gson.toJson(cmd));
        }

        final String controlIp = getRouterSshControlIp(cmd);
        String args = "";
        final String[] results = new String[cmd.getRules().length];
        int i = 0;

        boolean endResult = true;
        for (final PortForwardingRuleTO rule : cmd.getRules()) {
            args += rule.revoked() ? " -D " : " -A ";
            args += " -P " + rule.getProtocol().toLowerCase();
            args += " -l " + rule.getSrcIp();
            args += " -p " + rule.getStringSrcPortRange();
            args += " -r " + rule.getDstIp();
            args += " -d " + rule.getStringDstPortRange();

            try {
                final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, "/root/firewall.sh " + args);

                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Executing script on domain router " + controlIp + ": /root/firewall.sh " + args);
                }

                if (!result.first()) {
                    s_logger.error("SetPortForwardingRulesCommand failure on setting one rule. args: " + args);
                    results[i++] = "Failed";
                    endResult = false;
                } else {
                    results[i++] = null;
                }
            } catch (final Throwable e) {
                s_logger.error("SetPortForwardingRulesCommand(args: " + args + ") failed on setting one rule due to " + e.getMessage());
                results[i++] = "Failed";
                endResult = false;
            }
        }

        return new SetPortForwardingRulesAnswer(cmd, results, endResult);
    }

};