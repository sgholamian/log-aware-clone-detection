//,temp,HypervDirectConnectResource.java,2081,2107,temp,HypervDirectConnectResource.java,1037,1072
//,3
public class xxx {
    protected SetSourceNatAnswer execute(final SetSourceNatCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource SetSourceNatCommand " + s_gson.toJson(cmd));
        }

        final String routerName = cmd.getAccessDetail(NetworkElementCommand.ROUTER_NAME);
        final String routerIp = getRouterSshControlIp(cmd);
        final IpAddressTO pubIp = cmd.getIpAddress();
        try {
            final int ethDeviceNum = findRouterEthDeviceIndex(routerName, routerIp, pubIp.getVifMacAddress());
            String args = "";
            args += " -A ";
            args += " -l ";
            args += pubIp.getPublicIp();

            args += " -c ";
            args += "eth" + ethDeviceNum;

            final String command = String.format("%s%s %s", "/opt/cloud/bin/", VRScripts.VPC_SOURCE_NAT, args);

            final Pair<Boolean, String> result = SshHelper.sshExecute(routerIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);

            if (!result.first()) {
                final String msg = "SetupGuestNetworkCommand on domain router " + routerIp + " failed. message: " + result.second();
                s_logger.error(msg);

                return new SetSourceNatAnswer(cmd, false, msg);
            }

            return new SetSourceNatAnswer(cmd, true, "success");
        } catch (final Exception e) {
            final String msg = "Ip SNAT failure due to " + e.toString();
            s_logger.error(msg, e);
            return new SetSourceNatAnswer(cmd, false, msg);
        }
    }

};