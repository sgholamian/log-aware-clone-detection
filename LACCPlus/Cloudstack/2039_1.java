//,temp,HypervDirectConnectResource.java,1148,1198,temp,HypervDirectConnectResource.java,1074,1115
//,3
public class xxx {
    protected Answer execute(final SetStaticNatRulesCommand cmd) {

        if (cmd.getVpcId() != null) {
            //return SetVPCStaticNatRules(cmd);
        }

        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource SetFirewallRuleCommand: " + s_gson.toJson(cmd));
        }

        String args = null;
        final String[] results = new String[cmd.getRules().length];
        int i = 0;
        boolean endResult = true;
        for (final StaticNatRuleTO rule : cmd.getRules()) {
            // 1:1 NAT needs instanceip;publicip;domrip;op
            args = rule.revoked() ? " -D " : " -A ";

            args += " -l " + rule.getSrcIp();
            args += " -r " + rule.getDstIp();

            if (rule.getProtocol() != null) {
                args += " -P " + rule.getProtocol().toLowerCase();
            }

            args += " -d " + rule.getStringSrcPortRange();
            args += " -G ";

            try {
                final String controlIp = getRouterSshControlIp(cmd);
                final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, "/root/firewall.sh " + args);

                if (s_logger.isDebugEnabled()) {
                    s_logger.debug("Executing script on domain router " + controlIp + ": /root/firewall.sh " + args);
                }

                if (!result.first()) {
                    s_logger.error("SetStaticNatRulesCommand failure on setting one rule. args: " + args);
                    results[i++] = "Failed";
                    endResult = false;
                } else {
                    results[i++] = null;
                }
            } catch (final Throwable e) {
                s_logger.error("SetStaticNatRulesCommand (args: " + args + ") failed on setting one rule due to " + e.getMessage());
                results[i++] = "Failed";
                endResult = false;
            }
        }
        return new SetStaticNatRulesAnswer(cmd, results, endResult);
    }

};