//,temp,HypervDirectConnectResource.java,1511,1572,temp,HypervDirectConnectResource.java,1370,1409
//,3
public class xxx {
    protected Answer execute(final SavePasswordCommand cmd) {
        if (s_logger.isInfoEnabled()) {

            s_logger.info("Executing resource SavePasswordCommand. vmName: " + cmd.getVmName() + ", vmIp: " + cmd.getVmIpAddress() + ", password: " +
                    StringUtils.getMaskedPasswordForDisplay(cmd.getPassword()));
        }

        final String controlIp = getRouterSshControlIp(cmd);
        final String password = cmd.getPassword();
        final String vmIpAddress = cmd.getVmIpAddress();

        // Run save_password_to_domr.sh
        final String command = String.format("%s%s %s %s %s %s", "/opt/cloud/bin/", VRScripts.PASSWORD, "-v", vmIpAddress, "-p", password);

        if (s_logger.isDebugEnabled()) {
            final String debugCommand = String.format("%s%s %s %s %s %s", "/opt/cloud/bin/", VRScripts.PASSWORD, "-v", vmIpAddress, "-p", StringUtils.getMaskedPasswordForDisplay(cmd.getPassword()));
            s_logger.debug("Run command on domain router " + controlIp + debugCommand);
        }

        try {

            final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);

            if (!result.first()) {
                s_logger.error("savepassword command on domain router " + controlIp + " failed, message: " + result.second());

                return new Answer(cmd, false, "SavePassword failed due to " + result.second());
            }

            if (s_logger.isInfoEnabled()) {
                s_logger.info("savepassword command on domain router " + controlIp + " completed");
            }

        } catch (final Throwable e) {
            final String msg = "SavePasswordCommand failed due to " + e;
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }
        return new Answer(cmd);
    }

};