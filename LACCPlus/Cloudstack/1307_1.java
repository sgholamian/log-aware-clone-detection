//,temp,HypervDirectConnectResource.java,1479,1509,temp,HypervDirectConnectResource.java,1370,1409
//,3
public class xxx {
    protected Answer execute(final VmDataCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource VmDataCommand: " + s_gson.toJson(cmd));
        }
        final String controlIp = getRouterSshControlIp(cmd);
        final Map<String, List<String[]>> data = new HashMap<String, List<String[]>>();
        data.put(cmd.getVmIpAddress(), cmd.getVmData());

        String json = new Gson().toJson(data);
        s_logger.debug("VM data JSON IS:" + json);

        json = Base64.encodeBase64String(json.getBytes(Charset.forName("UTF-8")));
        final String command = String.format("%s%s %s %s", "/opt/cloud/bin/", VRScripts.VMDATA, "-d", json);

        try {
            final Pair<Boolean, String> result = SshHelper.sshExecute(controlIp, DEFAULT_DOMR_SSHPORT, "root", getSystemVMKeyFile(), null, command);
            if (!result.first()) {
                s_logger.error("vm_data command on domain router " + controlIp + " failed. messge: " + result.second());
                return new Answer(cmd, false, "VmDataCommand failed due to " + result.second());
            }

            if (s_logger.isInfoEnabled()) {
                s_logger.info("vm_data command on domain router " + controlIp + " completed");
            }
        } catch (final Throwable e) {
            final String msg = "VmDataCommand failed due to " + e.getMessage();
            s_logger.error(msg, e);
            return new Answer(cmd, false, msg);
        }
        return new Answer(cmd);
    }

};